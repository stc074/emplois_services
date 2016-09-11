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
public class Categories extends Objet {
    
    private int nb;
    private long[] ids;
    private String[] categories;
    private String[] urls;
    
    public Categories() {
        super();
        this.nb=0;
    }

    public void initListe() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(this.getNb()>0) {
                this.ids=new long[this.getNb()];
                this.categories=new String[this.getNb()];
                query="SELECT id,categorie FROM table_categories ORDER BY categorie LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    this.ids[i]=result.getLong("id");
                    this.categories[i]=result.getString("categorie");
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    public void initListeIndex() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbCategories FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nbCategories");
            result.close();
            state.close();
            if(this.nb>0) {
                this.urls=new String[this.nb];
                this.categories=new String[this.nb];
                query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    long idCategorie=result.getLong("id");
                    String categorie=result.getString("categorie");
                    this.urls[i]="./emplois-categorie-"+idCategorie+"-"+this.encodeTitre(categorie)+".html";
                    this.categories[i]=categorie;
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the categories
     */
    public String[] getCategories() {
        return categories;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

}
