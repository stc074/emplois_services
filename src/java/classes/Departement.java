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
public class Departement extends Objet {
    
    private String idRegion;
    private String id;
    private String departement;
    private String region;
    private String tagTitle;
    private String tagDescription;
    private String encodedDepartement;

    public Departement(String idRegion, String idDepartement) {
        super();
        this.idRegion=idRegion;
        this.id=idDepartement;
    }

    public void initInfosDepartement(HttpServletRequest request) {
        try {
            this.tagTitle="Emploi service - Emplois à domicile";
            this.tagDescription="Emploi services - Emplois à domicile - offres et demandes en France.";
            HttpSession session=request.getSession(true);
            this.getConnection();
            String query="SELECT t1.departement,t2.region FROM table_departements AS t1,table_regions AS t2 WHERE t1.id_departement=? AND t2.id_region=? AND t1.id_region=t2.id_region LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.id);
            prepare.setString(2, this.idRegion);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.departement=result.getString("departement");
            this.region=result.getString("region");
            result.close();
            prepare.close();
            this.encodedDepartement=this.encodeTitre(this.departement);
            String uri="./emplois-departement3-"+this.idRegion+"-"+this.id+"-"+this.encodeTitre(this.departement)+".html";
            session.setAttribute("uriRetour", uri);
            this.tagTitle="Emplois services - "+this.departement;
            this.tagDescription="Emplois services - Services à domicile - offres et demandes département : "+this.departement+".";
        } catch (NamingException ex) {
            Logger.getLogger(Departement.class.getName()).log(Level.SEVERE, null, ex);
            this.id="0";
        } catch (SQLException ex) {
            Logger.getLogger(Departement.class.getName()).log(Level.SEVERE, null, ex);
            this.id="0";
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Departement.class.getName()).log(Level.SEVERE, null, ex);
                this.id="0";
            }
        }
    }

    /**
     * @return the idRegion
     */
    public String getIdRegion() {
        return idRegion;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
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
     * @return the encodedDepartement
     */
    public String getEncodedDepartement() {
        return encodedDepartement;
    }

}
