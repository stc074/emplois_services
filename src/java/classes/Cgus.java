/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class Cgus extends Objet {
    
    private long id;
    private long timestamp;
    private String contenu;
    private String dateTxt;
    
    public Cgus() {
        super();
        this.timestamp=0;
        this.contenu="";
    }
    
    public void getPosts(HttpServletRequest request) {
        try {
            this.contenu=this.codeHTML2(request.getParameter("contenu"));
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }
    
    public void initInfos() {
        try {
            this.getConnection();
            String query="SELECT id,contenu,timestamp FROM table_cgus LIMIT 0,1";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.id=result.getLong("id");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            result.close();
            state.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.dateTxt="le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
        } catch (NamingException ex) {
            Logger.getLogger(Cgus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Cgus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Cgus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void verifPosts() {
        if(this.contenu.length()==0)
            this.setErrorMsg("<div>CONTNU DES CGUS vide.</div>");
        else if(this.contenu.length()>50000)
            this.setErrorMsg("<div>CONTENU DES CGUS trop long.</div>");
    }

    public void enreg() {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                Calendar cal=Calendar.getInstance();
                this.timestamp=cal.getTimeInMillis();
                this.dateTxt="le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
                String query="UPDATE table_cgus SET contenu=?,timestamp=? WHERE id=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.contenu);
                prepare.setLong(2, this.timestamp);
                prepare.setLong(3, this.id);
                prepare.executeUpdate();
                prepare.close();
            } catch (NamingException ex) {
                Logger.getLogger(Cgus.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Cgus.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Cgus.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @return the dateTxt
     */
    public String getDateTxt() {
        return dateTxt;
    }

}
