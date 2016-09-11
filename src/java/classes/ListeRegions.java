/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class ListeRegions extends Objet {
    private String[] idsRegion;
    private String[] regions;
    private int nbRegions;
    private String[] urls;
    
    public ListeRegions() {
        super();
    }

    public void initListe() {
        try {
            this.nbRegions=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbRegions FROM table_regions";
            Statement state=this.getCon().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbRegions=result.getInt("nbRegions");
            result.close();
            state.close();
            if(this.getNbRegions()>0) {
                this.idsRegion=new String[this.getNbRegions()];
                this.regions=new String[this.getNbRegions()];
                query="SELECT id_region,region FROM table_regions ORDER BY region ASC LIMIT 0,"+this.getNbRegions();
                state=this.getCon().createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    this.idsRegion[i]=result.getString("id_region");
                    this.regions[i]=result.getString("region");
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(ListeRegions.class.getName()).log(Level.SEVERE, null, ex);
            this.nbRegions=0;
        } catch (SQLException ex) {
            Logger.getLogger(ListeRegions.class.getName()).log(Level.SEVERE, null, ex);
            this.nbRegions=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ListeRegions.class.getName()).log(Level.SEVERE, null, ex);
                this.nbRegions=0;
            }
        }
    }
    
    public void initListeIndex() {
        try {
            this.nbRegions=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbRegions FROM table_regions";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbRegions=result.getInt("nbRegions");
            result.close();
            state.close();
            if(this.nbRegions>0) {
                this.urls=new String[this.nbRegions];
                this.regions=new String[this.nbRegions];
                query="SELECT id_region,region FROM table_regions ORDER BY region ASC LIMIT 0,"+this.nbRegions;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    String idRegion=result.getString("id_region");
                    String region=result.getString("region");
                    this.urls[i]="./emplois-region-"+idRegion+"-"+this.encodeTitre(region)+".html";
                    this.regions[i]=region;
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(ListeRegions.class.getName()).log(Level.SEVERE, null, ex);
            this.nbRegions=0;
        } catch (SQLException ex) {
            Logger.getLogger(ListeRegions.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.nbRegions=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ListeRegions.class.getName()).log(Level.SEVERE, null, ex);
                this.nbRegions=0;
            }
        }
    }

    /**
     * @return the idsRegion
     */
    public String[] getIdsRegion() {
        return idsRegion;
    }

    /**
     * @return the regions
     */
    public String[] getRegions() {
        return regions;
    }

    /**
     * @return the nbRegions
     */
    public int getNbRegions() {
        return nbRegions;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }
}
