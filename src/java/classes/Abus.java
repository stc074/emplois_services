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

/**
 *
 * @author pj
 */
public class Abus extends Objet {
    
    private long idAnnonce;
    private long id;
    private long timestamp;
    private int nb;
    private String[] urls;
    private String[] titres;
    private String[] comments;
    private String titreAnnonce;
    private String descriptionAnnonce;
    private String dateTxt;
    
    public Abus() {
        super();
    }

    public Abus(long idAbus) {
        super();
        this.id=idAbus;
    }

    public String signalerAbus(long idAnnonce) {
        try {
            String stringReturn="";
            this.idAnnonce=idAnnonce;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbAbus FROM table_abus WHERE id_annonce=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nbAbus=result.getInt("nbAbus");
            result.close();
            prepare.close();
            if(nbAbus==0) {
                query="INSERT INTO table_abus (id_annonce,timestamp) VALUES (?,?)";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                Calendar cal=Calendar.getInstance();
                this.timestamp=cal.getTimeInMillis();
                prepare.setLong(2, this.timestamp);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idAbus FROM table_abus WHERE id_annonce=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idAbus");
                result.close();
                prepare.close();
                Mail mail=new Mail(Datas.getEMAIL_ADMINISTRATOR(), "ADMIN", "Un nouvel abus");
                mail.initMailAbus(this.getId());
                mail.send();
                stringReturn="Merci d'avoir signalé cette annonce !";
            } else {
                stringReturn="Merci d'avoir signalé cette annonce !!";
            }
            return stringReturn;
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            return("Erreur : "+ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            return("Erreur : "+ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
                return("Erreur : "+ex.getMessage());
            }
        }
    }

    public void initListe() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbAbus FROM table_abus";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nbAbus");
            result.close();
            state.close();
            if(this.getNb()>0) {
                this.urls=new String[this.getNb()];
                this.titres=new String[this.getNb()];
                this.comments=new String[this.getNb()];
                query="SELECT t1.id,t1.timestamp,t2.titre FROM table_abus AS t1,table_annonces AS t2 WHERE t2.id=t1.id_annonce ORDER BY t1.timestamp DESC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                Calendar cal=Calendar.getInstance();
                while(result.next()) {
                    long idAbus=result.getLong("id");
                    long ts=result.getLong("timestamp");
                    String titre2=result.getString("titre");
                    this.urls[i]="./abus-"+idAbus+".html";
                    this.titres[i]=titre2;
                    this.comments[i]="Abus signalé le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    public void initAbus() {
        try {
            this.getConnection();
            String query="SELECT t1.timestamp,t2.titre,t2.description FROM table_abus AS t1,table_annonces AS t2 WHERE t1.id=? AND t2.id=t1.id_annonce LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.timestamp=result.getLong("timestamp");
            this.titreAnnonce=result.getString("titre");
            this.descriptionAnnonce=result.getString("description");
            result.close();
            prepare.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.dateTxt="Abus signalé le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void ignoreAbus() {
        try {
            this.getConnection();
            String query="DELETE FROM table_abus WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void effaceAnnonce() {
        try {
            this.getConnection();
            String query="SELECT t1.id,t1.id_membre FROM table_annonces AS t1,table_abus AS t2 WHERE t2.id=? AND t1.id=t2.id_annonce LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            long idA=result.getLong("id");
            long idM=result.getLong("id_membre");
            result.close();
            prepare.close();
            Annonce annonce=new Annonce();
            annonce.effaceAnnonce3(idM, idA);
            this.ignoreAbus();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    /**
     * @return the nb
     */
    public int getNb() {
        return nb;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the comments
     */
    public String[] getComments() {
        return comments;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the titreAnnonce
     */
    public String getTitreAnnonce() {
        return titreAnnonce;
    }

    /**
     * @return the descriptionAnnonce
     */
    public String getDescriptionAnnonce() {
        return descriptionAnnonce;
    }

    /**
     * @return the dateTxt
     */
    public String getDateTxt() {
        return dateTxt;
    }

}
