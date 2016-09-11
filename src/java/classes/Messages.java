/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Messages extends Objet {
    private Membre membreDestinataire;
    private int nb;
    private String[] titres;
    private String[] comments;
    private long[] ids;
    private Membre membreExpediteur;

    public void initListeRecus(Membre membre) {
        try {
            this.membreDestinataire=membre;
            this.getConnection();
            this.nb=0;
            String query="SELECT COUNT(id) AS nb FROM table_messages WHERE id_membre_destinataire=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membreDestinataire.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            prepare.close();
            if(this.getNb()>0) {
                this.ids=new long[this.getNb()];
                this.titres=new String[this.nb];
                this.comments=new String[this.nb];
                query="SELECT t1.id,t1.titre,t1.timestamp,t1.etat,t2.pseudo FROM table_messages AS t1,table_membres AS t2 WHERE t1.id_membre_destinataire=? AND t2.id=t1.id_membre_expediteur ORDER BY t1.timestamp DESC LIMIT 0,"+this.nb;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membreDestinataire.getId());
                result=prepare.executeQuery();
                Calendar cal=Calendar.getInstance();
                int i=0;
                while(result.next()) {
                    long idMessage=result.getLong("id");
                    String titre=result.getString("titre");
                    long timestamp=result.getLong("timestamp");
                    int etat=result.getInt("etat");
                    String pseudo=result.getString("pseudo");
                    cal.setTimeInMillis(timestamp);
                    this.ids[i]=idMessage;
                    this.titres[i]=titre;
                    this.comments[i]="";
                    if(etat==0)
                        this.comments[i]+="<span>[<span class=\"clign\">Non lu</span>]</span>&nbsp;";
                    this.comments[i]+="&nbsp;<span>message envoyé par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".</span>";
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    public void initListeEnvoyes(Membre membre) {
        try {
            this.membreExpediteur=membre;
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_messages WHERE id_membre_expediteur=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membreExpediteur.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            prepare.close();
            if(this.nb>0) {
                this.ids=new long[this.nb];
                this.titres=new String[this.nb];
                this.comments=new String[this.nb];
                query="SELECT t1.id,t1.titre,t1.timestamp,t1.etat,t2.pseudo FROM table_messages AS t1, table_membres AS t2 WHERE t1.id_membre_expediteur=? AND t2.id=t1.id_membre_destinataire ORDER BY t1.timestamp DESC LIMIT 0,"+this.nb;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membreExpediteur.getId());
                result=prepare.executeQuery();
                Calendar cal=Calendar.getInstance();
                int i=0;
                while(result.next()) {
                    long idMessage=result.getLong("id");
                    String titre=result.getString("titre");
                    long timestamp=result.getLong("timestamp");
                    int etat=result.getInt("etat");
                    String pseudo=result.getString("pseudo");
                    cal.setTimeInMillis(timestamp);
                    this.ids[i]=idMessage;
                    this.titres[i]=titre;
                    this.comments[i]="";
                    if(etat==0)
                        this.comments[i]+="<span>[<span class=\"clign\">Non consulté</span>]</span>&nbsp;";
                    this.comments[i]+="<span>&nbsp;Message envoyé à "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".</span>";
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
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
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

}
