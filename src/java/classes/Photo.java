/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author pj
 */
public class Photo extends Objet {
    
    private long idAnnonce;
    private Membre membre;
    private String extension;
    private String titre;
    private String urlPhoto;
    private String urlMini;
    private int largeur;
    private int hauteur;
  
    public Photo() {
        super();
        this.idAnnonce=0;
    }

    public Photo(long idAnnonce, Membre membre) {
        super();
        this.idAnnonce=idAnnonce;
        this.membre=membre;
        this.extension="";
        this.urlPhoto="";
        this.urlMini="";
        this.largeur=0;
        this.hauteur=0;
    }

    public void initInfosAnnonce() {
        try {
            this.getConnection();
            String query="SELECT titre,extension FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            prepare.setLong(2, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            this.extension=result.getString("extension");
            result.close();
            prepare.close();
            if(this.extension.length()>0) {
                this.urlPhoto="./photo-"+this.idAnnonce+""+this.extension;
                this.urlMini="./photo-mini-1-"+this.idAnnonce+""+this.extension;
                String filename=Datas.DIR+"photos/"+idAnnonce+""+extension;
                String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+""+extension;
                File file=new File(filename);
                File fileMini1=new File(filenameMini1);
                if(file.exists()&&fileMini1.exists()) {
                    Img img=new Img();
                    try {
                        img.getSize(fileMini1);
                        this.largeur=img.getWidth();
                        this.hauteur=img.getHeight();
                    } catch (IOException ex) {
                        Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
                        this.extension="";
                    }
                } else {
                    this.extension="";
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
            this.idAnnonce=0;
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            this.idAnnonce=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                this.idAnnonce=0;
            }
        }
    }

    public void getPosts(HttpServletRequest request) {
        try {
            this.getConnection();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(Datas.MAXUPLOADSIZE);
            factory.setRepository(new File("home/temp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(Datas.MAXUPLOADSIZE);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while(iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if(!item.isFormField()) {
                    String name=item.getFieldName();
                    if(name.equals("fichier")&&item.getSize()>0) {
                        int index=item.getName().lastIndexOf(".");
                        if(index>0) {
                        String ext=(item.getName().substring(index)).toLowerCase();
                        if(!this.testExtension(ext))
                            this.setErrorMsg("<div>Mauvais format de fichier (uniquement PNG, GIF, JPG, JPEG).</div>");
                        if(this.getErrorMsg().length()==0) {
                            String query="SELECT extension FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
                            PreparedStatement prepare=this.con.prepareStatement(query);
                            prepare.setLong(1, this.idAnnonce);
                            prepare.setLong(2, this.membre.getId());
                            ResultSet result=prepare.executeQuery();
                            result.next();
                            String ext2=result.getString("extension");
                            result.close();
                            prepare.close();
                            if(ext2.length()>0) {
                            String filename=Datas.DIR+"photos/"+this.idAnnonce+""+ext2;
                            String filenameMini1=Datas.DIR+"photos/mini1_"+this.idAnnonce+""+ext2;
                            String filenameMini2=Datas.DIR+"photos/mini2_"+this.idAnnonce+""+ext2;
                            File file=new File(filename);
                            File fileMini1=new File(filenameMini1);
                            File fileMini2=new File(filenameMini2);
                            if(file.exists())
                                file.delete();
                            if(fileMini1.exists())
                                fileMini1.delete();
                            if(fileMini2.exists())
                                fileMini2.delete();
                            }
                        String filename=Datas.DIR+"photos/"+this.idAnnonce+""+ext;
                        String filenameMini1=Datas.DIR+"photos/mini1_"+this.idAnnonce+""+ext;
                        String filenameMini2=Datas.DIR+"photos/mini2_"+this.idAnnonce+""+ext;
                        File file=new File(filename);
                        File fileMini1=new File(filenameMini1);
                        File fileMini2=new File(filenameMini2);
                        try {
                            item.write(file);
                        } catch (Exception ex) {
                            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
                            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+", Veuillez recommencer SVP.</div>");
                        }
                        Img img=new Img();
                        img.getSize(file);
                        if(img.getWidth()>Datas.getMAX_WIDTH())
                            img.resizeWidth(file, file, Datas.getMAX_WIDTH());
                        img.getSize(file);
                        if(img.getHeight()>Datas.getMAX_HEIGHT())
                            img.resizeHeight(file, file, Datas.getMAX_HEIGHT());
                        img.resizeHeight(file, fileMini1, Datas.HAUT1);
                        img.resizeWidth(file, fileMini2, Datas.LARG2);
                        if(file.exists()&&fileMini1.exists()&&fileMini2.exists()) {
                            this.extension=ext;
                            query="UPDATE table_annonces SET extension=? WHERE id=? AND id_membre=?";
                            prepare=this.con.prepareStatement(query);
                            prepare.setString(1, this.extension);
                            prepare.setLong(2, this.idAnnonce);
                            prepare.setLong(3, this.membre.getId());
                            prepare.executeUpdate();
                            prepare.close();
                            img.getSize(fileMini1);
                            this.largeur=img.getWidth();
                            this.hauteur=img.getHeight();
                            this.urlPhoto="./photo-"+this.idAnnonce+""+this.extension;
                            this.urlMini="./photo-mini-1-"+this.idAnnonce+""+this.extension;
                        } else {
                            this.setErrorMsg("<div>Erreur lors de la sauvegarde, veuillez réessayer SVP.</div>");
                        }
                        }
                        }
                        else {
                            this.setErrorMsg("<div>Mauvais format de fichier (uniquement PNG, GIF, JPG, JPEG).</div>");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

     /**
     * @return the idAnnonce
     */
    public long getIdAnnonce() {
        return idAnnonce;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the urlPhoto
     */
    public String getUrlPhoto() {
        return urlPhoto;
    }

    /**
     * @return the urlMini
     */
    public String getUrlMini() {
        return urlMini;
    }

    /**
     * @return the largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * @return the hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

}
