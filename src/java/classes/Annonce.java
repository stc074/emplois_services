/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Annonce extends Objet {
    
    private Membre membre;
    private int type;
    private long idCategorie;
    private long idSousCategorie;
    private String tarifHoraire;
    private String titre;
    private String description;
    private String captcha;
    private double tarifHoraire2;
    private long id;
    private String uri;
    private String tagTitle;
    private String tagDescription;
    private String url;
    private String ligne1;
    private String ligne2;
    private String ligne3;
    private String ligne4;
    private String imgCode;
    private String imgCodeMini1;
    private String pseudo;
    private String adresse;
    private long timestamp;
    private String typeDesc;
    private String dateDesc;
    
    public Annonce() {
        super();
    }
    public Annonce(Membre membre) {
        this.membre=membre;
        this.type=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.tarifHoraire="";
        this.titre="";
        this.description="";
    }
    public Annonce(Membre membre, long idAnnonce) {
        super();
        this.membre=membre;
        this.id=idAnnonce;
        this.membre.initInfosPersos();
    }
    
    public Annonce(long idAnnonce) {
        super();
        this.id=idAnnonce;
    }

    public void getGetsDepot(HttpServletRequest request) {
        if(request.getParameter("type")!=null)
            this.type=Integer.parseInt(request.getParameter("type"));
    }
    
    public void getPostsDepot(HttpServletRequest request) {
        try {
        this.type=Integer.parseInt(request.getParameter("type"));
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.tarifHoraire=this.codeHTML(request.getParameter("tarifHoraire"));
        this.titre=this.codeHTML(request.getParameter("titre"));
        this.description=this.codeHTML2(request.getParameter("description"));
        this.captcha=this.codeHTML(request.getParameter("captcha")).toLowerCase();
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void verifPostsDepot(HttpServletRequest request) {
        try {
            if(this.idCategorie==0)
                this.setErrorMsg("<div>Veuillez choisir la CATÉGORIE de votre annonce SVP.</div>");
            if(this.idSousCategorie==0)
                this.setErrorMsg("<div>Veuillez choisir la SOUS-CATÉGORIE de votre annonce SVP.</div>");
            if(this.tarifHoraire.length()==0)
                this.setErrorMsg("<div>Champ TARIF HORAIRE vide.</div>");
            else if(this.tarifHoraire.length()>10)
                this.setErrorMsg("<div>Champ TARIF HORAIRE trop long");
            else {
                try {
                    this.tarifHoraire2=Double.parseDouble(this.tarifHoraire.replaceAll(",", "."));
                } catch(Exception ex) {
                    this.setErrorMsg("<div>Champ TARIF HORAIRE non-valide.</div>");
                }
            }
            if(this.titre.length()==0)
                this.setErrorMsg("<div>Champ TITRE vide.</div>");
            else if(this.titre.length()>80)
                this.setErrorMsg("<div>Champ TITRE trop long.</div>");
            if(this.description.length()==0)
                this.setErrorMsg("<div>Champ DESCRIPTION vide.</div>");
            else if(this.description.length()>5000)
                this.setErrorMsg("<div>Champ DESCRIPTION trop long.</div>");
            HttpSession session=request.getSession(true);
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").toString().equals(this.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void enregAnnonceDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.getConnection();
                String query="INSERT INTO table_annonces (id_membre,type,id_categorie,id_sous_categorie,tarif_horaire,titre,description,timestamp) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.getMembre().getId());
                prepare.setInt(2, this.type);
                prepare.setLong(3, this.idCategorie);
                prepare.setLong(4, this.idSousCategorie);
                prepare.setDouble(5, this.tarifHoraire2);
                prepare.setString(6, this.titre);
                prepare.setString(7, this.description);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(8, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idAnnonce FROM table_annonces WHERE id_membre=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.getMembre().getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idAnnonce");
                session.setAttribute("idAnnonce", this.id);
                result.close();
                prepare.close();
                this.uri="./annonce-emploi-"+this.getId()+"-"+this.encodeTitre(this.titre)+".html";
                Mail mail=new Mail(this.getMembre().getEmail(), this.getMembre().getPseudo(), "Annonce publiée !");
                mail.initMailAnnonce1(this.getMembre().getPseudo(), this.titre, this.getUri(), this.getId());
                mail.send();
                session.setAttribute("captcha", null);
                this.blank();
                query="UPDATE table_flag SET flag=? WHERE id=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, 1);
                prepare.setLong(2, 1);
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initInfo() {
        try {
            this.tagTitle="Emploi services - Annonces d'emploi";
            this.tagDescription="Emploi Services - Annonces d'emploi - Emploi à domicile - Sur toute la France !";
            this.getConnection();
            String query="SELECT t1.type,t1.tarif_horaire,t1.titre,t1.description,t1.extension,t1.timestamp, t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1, table_membres AS t2, table_categories AS t3, table_sous_categories AS t4, table_regions AS t5, table_departements AS t6, table_communes AS t7 WHERE t1.id=? AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.type=result.getInt("type");
            this.tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.", ",");
            this.titre=result.getString("titre");
            this.description=result.getString("description");
            String extension=result.getString("extension");
            this.timestamp=result.getLong("timestamp");
            this.pseudo=result.getString("pseudo");
            String categorie=result.getString("categorie");
            String sousCategorie=result.getString("sous_categorie");
            String region=result.getString("region");
            String departement=result.getString("departement");
            String commune=result.getString("commune");
            String codePostal=result.getString("code_postal");
            result.close();
            prepare.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.url=Datas.URLROOT+"/annonce-emploi-"+this.id+"-"+this.encodeTitre(this.titre)+".html";
            this.ligne1="Annonce déposée par "+this.getPseudo()+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
            this.ligne2=categorie.toUpperCase()+"&rarr;"+sousCategorie.toUpperCase()+".";
            this.ligne3="";
            switch(this.type) {
                case 1:
                    this.ligne3+="Demande d'emploi - Tarif souhaité de "+this.tarifHoraire+" &euro; NET de l'heure.";
                    break;
                case 2:
                    this.ligne3+="Offre d'emploi - Payé "+this.tarifHoraire+" &euro; NET de l'heure.";
                    break;
            }
            this.ligne4="Localisation de l'annonceur : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
            this.adresse=codePostal+"-"+commune+" France";
            this.imgCode="";
            this.imgCodeMini1="";
            if(extension.length()>0) {
                String filename=Datas.DIR+"photos/"+this.id+""+extension;
                String filenameMini1=Datas.DIR+"photos/mini1_"+this.id+""+extension;
                File file=new File(filename);
                File fileMini1=new File(filenameMini1);
                if(file.exists()&&fileMini1.exists()) {
                    Img img=new Img();
                    try {
                        img.getSize(fileMini1);
                        int largeur=img.getWidth();
                        int hauteur=img.getHeight();
                        this.imgCode="./photo-"+this.id+""+extension;
                        this.imgCodeMini1="<img src=\"./photo-mini-1-"+this.id+""+extension+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\" />"; 
                    } catch (IOException ex) {
                        Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                        this.imgCode="";
                    }
                } else {
                    this.imgCode="";
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void putTop() {
        try {
            this.getConnection();
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            String query="UPDATE table_annonces SET timestamp=? WHERE id=? AND id_membre=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, ts);
            prepare.setLong(2, this.id);
            prepare.setLong(3, this.getMembre().getId());
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_flag SET flag=? WHERE id=?";
            prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 1);
            prepare.setLong(2, 1);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initInfosEdit() {
        try {
            this.getConnection();
            String query="SELECT type,id_categorie,id_sous_categorie,tarif_horaire,titre,description,timestamp FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.type=result.getInt("type");
            this.idCategorie=result.getLong("id_categorie");
            this.idSousCategorie=result.getLong("id_sous_categorie");
            this.tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.", ",");
            this.titre=result.getString("titre");
            this.description=result.getString("description");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.typeDesc="";
            switch(this.type) {
                case 1:
                    this.typeDesc="Demande d'emploi.";
                    break;
                case 2:
                    this.typeDesc="Offre d'emploi";
                    break;
            }
            this.dateDesc="Dernières modifications effectuées le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void getPostsEdit(HttpServletRequest request) {
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.tarifHoraire=this.codeHTML(request.getParameter("tarifHoraire"));
        this.titre=this.codeHTML(request.getParameter("titre"));
        this.description=this.codeHTML2(request.getParameter("description"));
        this.captcha=this.codeHTML(request.getParameter("captcha")).toLowerCase();
    }

    public void verifPostsEdit(HttpServletRequest request) {
        try {
            if(this.idCategorie==0)
                this.setErrorMsg("<div>Veuillez choisir la CATÉGORIE DE L'EMPLOI SVP.</div>");
            if(this.idSousCategorie==0)
                this.setErrorMsg("<div>Veuillez choisir la SOUS-CATÉGORIE DE L'EMPLOI SVP.</div>");
            if(this.tarifHoraire.length()==0)
                this.setErrorMsg("<div>Champ TARIF HORAIRE vide.</div>");
            else if(this.tarifHoraire.length()>10)
                this.setErrorMsg("<div>Champ TARIF HORAIRE trop long.</div>");
            else {
                try {
                    this.tarifHoraire2=Double.parseDouble(this.tarifHoraire.replaceAll(",", "\\."));
                } catch(Exception ex) {
                    this.setErrorMsg("<div>Champ TARIF HORAIRE non-valide.</div>");
                }
            }
            if(this.titre.length()==0)
                this.setErrorMsg("<div>Champ TITRE vide.</div>");
            else if(this.titre.length()>80)
                this.setErrorMsg("<div>Champ TITRE trop long.</div>");
            if(this.description.length()==0)
                this.setErrorMsg("<div>Champ DESCRIPTION vide.</div>");
            else if(this.description.length()>5000)
                this.setErrorMsg("<div>Champ DESCRIPTION trop long.</div>");
            HttpSession session=request.getSession(true);
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").toString().equals(this.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void enregAnnonceEdit(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="UPDATE table_annonces SET id_categorie=?,id_sous_categorie=?,tarif_horaire=?,titre=?,description=?,timestamp=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.idSousCategorie);
                prepare.setDouble(3, this.tarifHoraire2);
                prepare.setString(4, this.titre);
                prepare.setString(5, this.description);
                Calendar cal=Calendar.getInstance();
                this.timestamp=cal.getTimeInMillis();
                prepare.setLong(6, this.timestamp);
                prepare.setLong(7, this.id);
                prepare.setLong(8, this.getMembre().getId());
                prepare.executeUpdate();
                prepare.close();
                query="UPDATE table_flag SET flag=? WHERE id=?";
                prepare=this.con.prepareStatement(query);
                prepare.setInt(1, 1);
                prepare.setLong(2, 1);
                prepare.executeUpdate();
                prepare.close();
                this.uri="annonce-emploi-"+this.id+"-"+this.encodeTitre(this.titre)+".html";
                Mail mail=new Mail(this.getMembre().getEmail(), this.getMembre().getPseudo(), "Annonce modifiée !");
                mail.initMailAnnonceModif1(this.getMembre().getPseudo(), this.titre, this.uri, this.id);
                mail.send();
                HttpSession session=request.getSession(true);
                session.setAttribute("idAnnonce", this.id);
                session.setAttribute("captcha", null);
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }
    public void effaceAnnonce() {
        try {
            this.getConnection();
            this.effaceAnnonce2(membre.getId(), this.id);
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public void effaceAnnonce2(long idMembre, long idAnnonce) throws SQLException {
        String query="SELECT extension FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
        PreparedStatement prepare=this.con.prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        ResultSet result=prepare.executeQuery();
        result.next();
        String ext=result.getString("extension");
        result.close();
        prepare.close();
        if(ext.length()>0) {
            String filename=Datas.DIR+"photos/"+idAnnonce+""+ext;
            String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+""+ext;
            String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+""+ext;
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
        query="DELETE FROM table_messages WHERE id_annonce=? AND (id_membre_expediteur=? OR id_membre_destinataire=?)";
        prepare=this.con.prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        prepare.setLong(3, idMembre);
        prepare.executeUpdate();
        prepare.close();
        query="DELETE FROM table_annonces WHERE id=? AND id_membre=?";
        prepare=this.con.prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        prepare.executeUpdate();
        prepare.close();
        query="UPDATE table_flag SET flag=? WHERE id=?";
        prepare=this.con.prepareStatement(query);
        prepare.setInt(1, 1);
        prepare.setLong(2, 1);
        prepare.executeUpdate();
        prepare.close();
    }

    void effaceAnnonce3(long idMembre, long idAnnonce) throws NamingException, SQLException {
        this.getConnection();
        String query="SELECT extension FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
        PreparedStatement prepare=this.con.prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        ResultSet result=prepare.executeQuery();
        result.next();
        String ext=result.getString("extension");
        result.close();
        prepare.close();
        if(ext.length()>0) {
            String filename=Datas.DIR+"photos/"+idAnnonce+""+ext;
            String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+""+ext;
            String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+""+ext;
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
        query="DELETE FROM table_messages WHERE id_annonce=? AND (id_membre_expediteur=? OR id_membre_destinataire=?)";
        prepare=this.con.prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        prepare.setLong(3, idMembre);
        prepare.executeUpdate();
        prepare.close();
        query="DELETE FROM table_annonces WHERE id=? AND id_membre=?";
        prepare=this.con.prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        prepare.executeUpdate();
        prepare.close();
        query="UPDATE table_flag SET flag=? WHERE id=?";
        prepare=this.con.prepareStatement(query);
        prepare.setInt(1, 1);
        prepare.setLong(2, 1);
        prepare.executeUpdate();
        prepare.close();
        this.closeConnection();
    }

    @Override
    public void blank() {
        super.blank();
        this.membre=null;
        this.type=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.tarifHoraire="";
        this.titre="";
        this.description="";
        this.captcha="";
    }
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @return the tarifHoraire
     */
    public String getTarifHoraire() {
        return tarifHoraire;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the ligne1
     */
    public String getLigne1() {
        return ligne1;
    }

    /**
     * @return the ligne2
     */
    public String getLigne2() {
        return ligne2;
    }

    /**
     * @return the ligne3
     */
    public String getLigne3() {
        return ligne3;
    }

    /**
     * @return the ligne4
     */
    public String getLigne4() {
        return ligne4;
    }

    /**
     * @return the imgCode
     */
    public String getImgCode() {
        return imgCode;
    }

    /**
     * @return the imgCodeMini1
     */
    public String getImgCodeMini1() {
        return imgCodeMini1;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @return the typeDesc
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * @return the dateDesc
     */
    public String getDateDesc() {
        return dateDesc;
    }

    /**
     * @return the membre
     */
    public Membre getMembre() {
        return membre;
    }

}
