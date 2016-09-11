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
public class ListeCommunes extends Objet {
    
    private String idRegion;
    private String idDepartement;
    private int nbCommunes;
    private int[] ids;
    private String[] communes;
    
    public ListeCommunes(String idRegion, String idDepartement) {
        super();
        this.idRegion=idRegion;
        this.idDepartement=idDepartement;
        this.nbCommunes=0;
    }

    public void initListe() {
        this.nbCommunes=0;
        if(!this.idRegion.equals("0")&&!this.idDepartement.equals("0")) {
            try {
                this.getConnection();
                String query="SELECT COUNT(id) AS nbCommunes FROM table_communes WHERE id_region=? AND id_departement=?";
                PreparedStatement prepare=this.getCon().prepareStatement(query);
                prepare.setString(1, this.idRegion);
                prepare.setString(2, this.idDepartement);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.nbCommunes=result.getInt("nbCommunes");
                result.close();
                prepare.close();
                if(this.getNbCommunes()>0) {
                    this.ids=new int[this.nbCommunes];
                    this.communes=new String[this.nbCommunes];
                    query="SELECT id,commune,code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune ASC LIMIT 0,"+this.nbCommunes;
                    prepare=this.getCon().prepareStatement(query);
                    prepare.setString(1, this.idRegion);
                    prepare.setString(2, this.idDepartement);
                    result=prepare.executeQuery();
                    int i=0;
                    while(result.next()) {
                        int idCommune=result.getInt("id");
                        String commune=result.getString("commune");
                        String codePostal=result.getString("code_postal");
                        this.ids[i]=idCommune;
                        this.communes[i]=codePostal+"-"+commune;
                        i++;
                    }
                    result.close();
                    prepare.close();
                }
            } catch (NamingException ex) {
                Logger.getLogger(ListeCommunes.class.getName()).log(Level.SEVERE, null, ex);
                this.nbCommunes=0;
            } catch (SQLException ex) {
                Logger.getLogger(ListeCommunes.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.nbCommunes=0;
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(ListeCommunes.class.getName()).log(Level.SEVERE, null, ex);
                    this.nbCommunes=0;
                }
            }
        }
    }

    /**
     * @return the nbCommunes
     */
    public int getNbCommunes() {
        return nbCommunes;
    }

    /**
     * @return the ids
     */
    public int[] getIds() {
        return ids;
    }

    /**
     * @return the communes
     */
    public String[] getCommunes() {
        return communes;
    }
}
