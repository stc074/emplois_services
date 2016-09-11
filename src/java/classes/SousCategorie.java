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
public class SousCategorie extends Objet {
    
    private long idCategorie;
    private String sousCategorie;
    private long id;
    private String categorie;
    private String tagTitle;
    private String tagDescription;
    private String encodedSousCategorie;
    
    public SousCategorie() {
        super();
        this.idCategorie=0;
    }

    public SousCategorie(long idCategorie, long idSousCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.id=idSousCategorie;
    }

    public void getGets(HttpServletRequest request) {
        if(request.getParameter("idCategorie")!=null)
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
    }

    public void getPostsAjout(HttpServletRequest request) {
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.sousCategorie=this.codeHTML(request.getParameter("sousCategorie"));
    }
    
    public void verifPostsAjout() {
        if(this.idCategorie==0)
            this.setErrorMsg("<div>Veuillez choisir une CATÉGORIE SVP.</div>");
        if(this.sousCategorie.length()==0)
            this.setErrorMsg("<div>Champ SOUS-CATÉGORIE vide.</div>");
        else if(this.sousCategorie.length()>80)
            this.setErrorMsg("<div>Champ SOUS-CATÉGORIE trop long.</div>");
        else {
            try {
                this.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=? AND sous_categorie=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb>0)
                    this.setErrorMsg("<div>Cette SOUS-CATÉGORIE existe déjà.</div>");
            } catch (NamingException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void enregAjout() {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="INSERT INTO table_sous_categories (id_categorie,sous_categorie) VALUES (?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initInfosAnnoncesSousCategorie(HttpServletRequest request) {
        try {
            this.tagTitle="Emplois service - Services à domicile";
            this.tagDescription="Emplois services - Emplois à domicile - Offres et demandes d'emplois en France.";
            this.getConnection();
            String query="SELECT t1.sous_categorie,t2.categorie FROM table_sous_categories AS t1,table_categories AS t2 WHERE t1.id=? AND t1.id_categorie=? AND t2.id=t1.id_categorie LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.sousCategorie=result.getString("sous_categorie");
            this.categorie=result.getString("categorie");
            result.close();
            prepare.close();
            this.encodedSousCategorie=this.encodeTitre(this.categorie+"-"+this.sousCategorie);
            String uriRetour="./emplois-sous-categorie3-"+this.idCategorie+"-"+this.id+"-"+this.encodeTitre(this.categorie)+"-"+this.encodeTitre(this.sousCategorie)+".html";
            HttpSession session=request.getSession(true);
            session.setAttribute("uriRetour", uriRetour);
            this.tagTitle="Annonces d'emplois - "+this.categorie+" - "+this.sousCategorie;
            this.tagDescription="Emplois services - Servicess à domicile - Offre et demandes d'emplois - "+this.categorie+" - "+this.sousCategorie+".";
        } catch (NamingException ex) {
            Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
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

    /**
     * @return the encodedSousCategorie
     */
    public String getEncodedSousCategorie() {
        return encodedSousCategorie;
    }

}
