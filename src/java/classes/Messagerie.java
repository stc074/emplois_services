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
public class Messagerie extends Objet {
    
    private Membre membre;
    private int nbRecus;
    private int nbEnvoyes;

    public Messagerie(Membre membre) {
        super();
        this.membre=membre;
    }

    public void calculNbMsg() {
        try {
            this.nbRecus=0;
            this.nbEnvoyes=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbRecus FROM table_messages WHERE id_membre_destinataire=? AND etat='0'";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbRecus=result.getInt("nbRecus");
            result.close();
            prepare.close();
            query="SELECT COUNT(id) AS nbEnvoyes FROM table_messages WHERE id_membre_expediteur=? AND etat='0'";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            result=prepare.executeQuery();
            result.next();
            this.nbEnvoyes=result.getInt("nbEnvoyes");
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            this.nbRecus=0;
            this.nbEnvoyes=0;
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            this.nbRecus=0;
            this.nbEnvoyes=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
                this.nbRecus=0;
                this.nbEnvoyes=0;
            }
        }
    }

    /**
     * @return the membre
     */
    public Membre getMembre() {
        return membre;
    }

    /**
     * @return the nbRecus
     */
    public int getNbRecus() {
        return nbRecus;
    }

    /**
     * @return the nbEnvoyes
     */
    public int getNbEnvoyes() {
        return nbEnvoyes;
    }
    
}
