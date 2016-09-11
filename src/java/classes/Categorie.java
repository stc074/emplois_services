/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Categorie extends Objet {
    private String categorie;
    private long id;
    private String encodedCategorie;
    private String tagTitle;
    private String tagDescription;
    
    public Categorie() {
        super();
        this.categorie="";
    }

    public Categorie(long idCategorie) {
        super();
        this.categorie="";
        this.id=idCategorie;
    }

    public void getPostsAjout(HttpServletRequest request) {
        this.categorie=this.codeHTML(request.getParameter("categorie"));
    }
    
    public void verifPostsAjout(HttpServletRequest request) {
        if(this.categorie.length()==0)
            this.setErrorMsg("<div>Champ CATÉGORIE vide.</div>");
        else if(this.categorie.length()>80)
            this.setErrorMsg("<div>Champ CATÉGORIE trop long.</div>");
        else {
            try {
                this.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_categories WHERE categorie=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.categorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb>0)
                    this.setErrorMsg("<div>Cette CATÉGORIE existe déjà.</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void enregCategorie() {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="INSERT INTO table_categories (categorie) VALUES (?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.categorie);
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initInfosCategorie(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.tagTitle="Emplois Services -Services à domicile";
        this.tagDescription="Emplois services - Services à domicile - offres et demende d'emplois en France.";
        try {
            this.getConnection();
            String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.categorie=result.getString("categorie");
            result.close();
            prepare.close();
            this.encodedCategorie=this.encodeTitre(this.categorie);
            String uriRetour="./emplois-categorie3-"+this.id+"-"+this.encodedCategorie+".html";
            session.setAttribute("uriRetour", uriRetour);
            this.tagTitle="Emplois services - "+this.categorie;
            this.tagDescription="Emplois services - Services à domicile - "+this.categorie+".";
        } catch (NamingException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the encodedCategorie
     */
    public String getEncodedCategorie() {
        return encodedCategorie;
    }

    /**
     * @return the tagTitle
     */
    public String getTagTitle() {
        return tagTitle;
    }

    /**
     * @return the tagDescription
     */
    public String getTagDescription() {
        return tagDescription;
    }

}
