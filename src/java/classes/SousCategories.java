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
public class SousCategories extends Objet {
    
    private int nb;
    private long idCategorie;
    private long[] ids;
    private String[] sousCategories;
    private String categorie;
    private String[] urls;
    private long idSousCategorie;
    
    public SousCategories(long idCategorie) {
        super();
        this.nb=0;
        this.idCategorie=idCategorie;
    }

    public SousCategories(long idCategorie, long idSousCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.idSousCategorie=idSousCategorie;
    }

    public void initListe() {
        this.nb=0;
        if(this.idCategorie!=0) {
        try {
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            prepare.close();
            if(this.nb>0) {
                this.ids=new long[this.nb];
                this.sousCategories=new String[this.nb];
                query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC LIMIT 0,"+this.nb;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    this.ids[i]=result.getLong("id");
                    this.sousCategories[i]=result.getString("sous_categorie");
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        }
        }
    }
    
    public void initCategorieInfo() {
        if(this.idCategorie!=0) {
            try {
                this.getConnection();
                String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.categorie=result.getString("categorie");
                result.close();
                prepare.close();
            } catch (NamingException ex) {
                Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            } catch (SQLException ex) {
                Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
                    this.idCategorie=0;
                }
            }
        }
    }

    public void initListe2() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.categorie=result.getString("categorie");
            result.close();
            prepare.close();
            query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            result=prepare.executeQuery();
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            prepare.close();
            if(this.nb>0) {
                this.urls=new String[this.nb];
                this.ids=new long[this.nb];
                this.sousCategories=new String[this.nb];
                query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC LIMIT 0,"+this.nb;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    long idSousCat=result.getLong("id");
                    String sousCat=result.getString("sous_categorie");
                    this.ids[i]=idSousCat;
                    this.urls[i]="./emplois-sous-categorie-"+this.idCategorie+"-"+idSousCat+"-"+this.encodeTitre(this.categorie)+"-"+this.encodeTitre(sousCat)+".html";
                    this.sousCategories[i]=sousCat;
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(SousCategories.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the sousCategories
     */
    public String[] getSousCategories() {
        return sousCategories;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

}
