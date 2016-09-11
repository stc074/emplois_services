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

/**
 *
 * @author pj
 */
public class ListeDepartements extends Objet {
    
    private String idRegion;
    private int nbDepartements;
    private String[] ids;
    private String[] departements;
    private String[] urls;
    
    public ListeDepartements(String idRegion) {
        super();
        this.idRegion=idRegion;
        this.nbDepartements=0;
    }

    public void initListe() {
        this.nbDepartements=0;
        if(!this.idRegion.equals("0")) {
        try {
            this.getConnection();
            String query="SELECT COUNT(id) AS nbDepartements FROM table_departements WHERE id_region=?";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setString(1, this.getIdRegion());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbDepartements=result.getInt("nbDepartements");
            result.close();
            prepare.close();
            if(this.nbDepartements>0) {
                this.ids=new String[this.nbDepartements];
                this.departements=new String[this.nbDepartements];
                query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement LIMIT 0,"+this.nbDepartements;
                prepare=this.getCon().prepareStatement(query);
                prepare.setString(1, this.getIdRegion());
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    String idDepartement=result.getString("id_departement");
                    String departement=result.getString("departement");
                    this.ids[i]=idDepartement;
                    this.departements[i]=idDepartement+"-"+departement;
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
            this.nbDepartements=0;
        } catch (SQLException ex) {
            Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.nbDepartements=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
                this.nbDepartements=0;
            }
        }
        }
    }

    public void initListeRegion() {
        try {
            this.nbDepartements=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbDepartements FROM table_departements WHERE id_region=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.idRegion);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbDepartements=result.getInt("nbDepartements");
            result.close();
            prepare.close();
            if(this.nbDepartements>0) {
                this.urls=new String[this.nbDepartements];
                this.departements=new String[this.nbDepartements];
                query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY departement ASC LIMIT 0,"+this.nbDepartements;
                prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.idRegion);
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    String idDepartement=result.getString("id_departement");
                    String departement=result.getString("departement");
                    this.urls[i]="./emplois-departement-"+this.idRegion+"-"+idDepartement+"-"+this.encodeTitre(departement)+".html";
                    this.departements[i]=departement;
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
            this.nbDepartements=0;
        } catch (SQLException ex) {
            Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
            this.nbDepartements=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
                this.nbDepartements=0;
            }
        }
    }

    public void initListeDepartement() {
        try {
            this.nbDepartements=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbDepartements FROM table_departements WHERE id_region=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.idRegion);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbDepartements=result.getInt("nbDepartements");
            result.close();
            prepare.close();
            if(this.nbDepartements>0) {
                this.urls=new String[this.nbDepartements];
                this.departements=new String[this.nbDepartements];
                this.ids=new String[this.nbDepartements];
                query="SELECT id_departement, departement FROM table_departements WHERE id_region=? ORDER BY departement ASC LIMIT 0,"+this.nbDepartements;
                prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.idRegion);
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    String idDepartement=result.getString("id_departement");
                    String departement=result.getString("departement");
                    this.urls[i]="./emplois-departement-"+this.idRegion+"-"+idDepartement+"-"+this.encodeTitre(departement)+".html";
                    this.departements[i]=departement;
                    this.ids[i]=idDepartement;
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
            this.nbDepartements=0;
        } catch (SQLException ex) {
            Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
            this.nbDepartements=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ListeDepartements.class.getName()).log(Level.SEVERE, null, ex);
                this.nbDepartements=0;
            }
        }
    }

    /**
     * @return the nbDepartements
     */
    public int getNbDepartements() {
        return nbDepartements;
    }

    /**
     * @return the ids
     */
    public String[] getIds() {
        return ids;
    }

    /**
     * @return the departements
     */
    public String[] getDepartements() {
        return departements;
    }

    /**
     * @return the idRegion
     */
    public String getIdRegion() {
        return idRegion;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

}
