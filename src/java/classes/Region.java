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
public class Region extends Objet {
    
    private String id;
    private String region;
    private String tagTitle;
    private String tagDescription;
    private String codedRegion;

    public Region(String idRegion) {
        super();
        this.id=idRegion;
    }

    public void initInfos(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            this.tagTitle="Emplois services - Emplois à domicile";
            this.tagDescription="Emplois services et emplois à domicile - offres et demandes en France.";
            this.getConnection();
            String query="SELECT region FROM table_regions WHERE id_region=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.region=result.getString("region");
            this.codedRegion=this.encodeTitre(this.region);
            result.close();
            prepare.close();
            this.tagTitle="Annonces d'emplois services en "+this.region;
            this.tagDescription="Emplois services - Services à domicile - offres et demandes en "+this.region+".";
            String uri="./emplois-region3-"+this.id+"-"+this.encodeTitre(this.region)+".html";
            session.setAttribute("uriRetour", uri);
        } catch (NamingException ex) {
            Logger.getLogger(Region.class.getName()).log(Level.SEVERE, null, ex);
            this.id="0";
        } catch (SQLException ex) {
            Logger.getLogger(Region.class.getName()).log(Level.SEVERE, null, ex);
            this.id="0";
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Region.class.getName()).log(Level.SEVERE, null, ex);
                this.id="0";
            }
        }
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the codedRegion
     */
    public String getCodedRegion() {
        return codedRegion;
    }

    
}
