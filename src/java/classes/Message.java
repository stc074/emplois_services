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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Message extends Objet {
    
    private long idAnnonce;
    private Membre membreExpediteur;
    private String titreAnnonce;
    private long idMembreDestinataire;
    private Membre membreDestinataire;
    private String titreMsg;
    private String contenuMsg;
    private String captcha;
    private long timestamp;
    private long id;
    private long idPrec;
    private long idMembreExpediteur;
    private String titre;
    private String contenu;
    private int etat;
    private String dateTxt;
    private String titrePrec;
    private String contenuPrec;
    private long timestampPrec;
    private String dateTxtPrec;
    
    public Message(Membre membre, long idAnnonce) {
        super();
        this.membreExpediteur=membre;
        this.idAnnonce=idAnnonce;
        this.titreMsg="";
        this.contenuMsg="";
    }

    public Message() {
        super();
    }
    
    public Message(long idMessage) {
        super();
        this.id=idMessage;
        this.titreMsg="";
        this.contenuMsg="";
    }

    public void initInfos1() {
        try {
            this.getMembreExpediteur().initInfosPersos();
            this.getConnection();
            String query="SELECT id_membre,titre FROM table_annonces WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idMembreDestinataire=result.getLong("id_membre");
            this.titreAnnonce=result.getString("titre");
            result.close();
            prepare.close();
            this.membreDestinataire=new Membre(this.idMembreDestinataire);
            this.membreDestinataire.initInfosPersos();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.idAnnonce=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.idAnnonce=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.idAnnonce=0;
            }
        }
    }
    
    public void getPostsContact(HttpServletRequest request) {
        try {
            this.titreMsg=this.codeHTML(request.getParameter("titreMsg"));
            this.contenuMsg=this.codeHTML2(request.getParameter("contenuMsg"));
            this.captcha=this.codeHTML(request.getParameter("captcha").toLowerCase());
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void verifPostsContact(HttpServletRequest request) {
        try {
            if(this.titreMsg.length()==0)
                this.setErrorMsg("<div>Champ OBJET DU MESSAGE vide.</div>");
            else if(this.titreMsg.length()>80)
                this.setErrorMsg("<div>Champ OBJET DU MESSAGE trop long.</div>");
            if(this.contenuMsg.length()==0)
                this.setErrorMsg("<div>Champ CONTENU DU MESSAGE vide.</div>");
            else if(this.contenuMsg.length()>3000)
                this.setErrorMsg("<div>Champ CONTENU DU MESSAGE trop long.</div>");
            HttpSession session=request.getSession(true);
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").toString().equals(this.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (Exception ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void enregContact1(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="INSERT INTO table_messages (id_annonce,id_membre_expediteur,id_membre_destinataire,titre,contenu,timestamp) VALUES (?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1,this.idAnnonce);
                prepare.setLong(2, this.membreExpediteur.getId());
                prepare.setLong(3, this.membreDestinataire.getId());
                prepare.setString(4, this.titreMsg);
                prepare.setString(5, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                this.timestamp=cal.getTimeInMillis();
                prepare.setLong(6, this.timestamp);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membreExpediteur.getId());
                prepare.setLong(2, this.membreDestinataire.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idMessage");
                result.close();
                prepare.close();
                Mail mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, this.getId());
                mail.send();
                mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Un nouveau message !");
                mail.initMailMessage2(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, this.getId());
                mail.send();
                this.blank();
                this.setTest(1);
                HttpSession session=request.getSession(true);
                session.setAttribute("captcha", null);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initRecu(Membre membre) {
        try {
            this.membreDestinataire=membre;
            this.membreDestinataire.initInfosPersos();
            this.getConnection();
            String query="SELECT t1.id,t1.titre FROM table_annonces AS t1,table_messages AS t2 WHERE t2.id=? AND t2.id_membre_destinataire=? AND t1.id=t2.id_annonce LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreDestinataire.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id");
            this.titreAnnonce=result.getString("titre");
            result.close();
            prepare.close();
            query="SELECT id_prec,id_membre_expediteur,titre,contenu,timestamp,etat FROM table_messages WHERE id=? AND id_membre_destinataire=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.membreDestinataire.getId());
            result=prepare.executeQuery();
            result.next();
            this.idPrec=result.getLong("id_prec");
            this.idMembreExpediteur=result.getLong("id_membre_expediteur");
            this.titre=result.getString("titre");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            this.etat=result.getInt("etat");
            result.close();
            prepare.close();
            if(this.etat==0) {
                query="UPDATE table_messages SET etat='1' WHERE id=? AND id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.getId());
                prepare.setLong(2, this.idMembreExpediteur);
                prepare.setLong(3, this.membreDestinataire.getId());
                prepare.executeUpdate();
                prepare.close();
            }
            this.membreExpediteur=new Membre(this.idMembreExpediteur);
            this.membreExpediteur.initInfosPersos();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.dateTxt="le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            if(this.idPrec!=0) {
                query="SELECT titre,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_expediteur=? AND id_membre_destinataire=? LIMIT 0,1";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idPrec);
                prepare.setLong(2, this.membreDestinataire.getId());
                prepare.setLong(3, this.membreExpediteur.getId());
                result=prepare.executeQuery();
                result.next();
                this.titrePrec=result.getString("titre");
                this.contenuPrec=result.getString("contenu");
                this.timestampPrec=result.getLong("timestamp");
                result.close();
                prepare.close();
                cal.setTimeInMillis(this.timestampPrec);
                this.dateTxtPrec="le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            }
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void enregContact2(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                HttpSession session=request.getSession(true);
                String query="INSERT INTO table_messages (id_annonce,id_prec,id_membre_expediteur,id_membre_destinataire,titre,contenu,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                prepare.setLong(2, this.id);
                prepare.setLong(3, this.membreDestinataire.getId());
                prepare.setLong(4, this.membreExpediteur.getId());
                prepare.setString(5, this.titreMsg);
                prepare.setString(6, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idmessage FROM table_messages WHERE id_prec=? AND id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.id);
                prepare.setLong(2, this.membreDestinataire.getId());
                prepare.setLong(3, this.membreExpediteur.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                long idMessage=result.getLong("idMessage");
                result.close();
                prepare.close();
                Mail mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreDestinataire.getPseudo(), this.membreExpediteur.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Un nouveau message !");
                mail.initMailMessage2(this.membreDestinataire.getPseudo(), this.membreExpediteur.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                session.setAttribute("captcha", null);
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void initInfosEnvoye(Membre membre) {
        try {
            this.membreExpediteur=membre;
            this.membreExpediteur.initInfosPersos();
            this.getConnection();
            String query="SELECT t1.id,t1.titre FROM table_annonces AS t1,table_messages AS t2 WHERE t2.id=? AND t2.id_membre_expediteur=? AND t1.id=t2.id_annonce LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreExpediteur.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id");
            this.titreAnnonce=result.getString("titre");
            result.close();
            prepare.close();
            query="SELECT id_prec,id_membre_destinataire,titre,contenu,timestamp,etat FROM table_messages WHERE id=? AND id_membre_expediteur=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreExpediteur.getId());
            result=prepare.executeQuery();
            result.next();
            this.idPrec=result.getLong("id_prec");
            this.idMembreDestinataire=result.getLong("id_membre_destinataire");
            this.titre=result.getString("titre");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            this.etat=result.getInt("etat");
            result.close();
            prepare.close();
            this.membreDestinataire=new Membre(this.idMembreDestinataire);
            this.membreDestinataire.initInfosPersos();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.dateTxt="le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            if(this.idPrec!=0) {
                query="SELECT titre,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_expediteur=? AND id_membre_destinataire=? LIMIT 0,1";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idPrec);
                prepare.setLong(2, this.membreDestinataire.getId());
                prepare.setLong(3, this.membreExpediteur.getId());
                result=prepare.executeQuery();
                result.next();
                this.titrePrec=result.getString("titre");
                this.contenuPrec=result.getString("contenu");
                this.timestampPrec=result.getLong("timestamp");
                result.close();
                prepare.close();
                cal.setTimeInMillis(this.timestampPrec);
                this.dateTxtPrec="le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            }
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void enregContactEnv(HttpServletRequest request) {
        if(this.idPrec!=0&&this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="INSERT INTO table_messages (id_annonce,id_prec,id_membre_expediteur,id_membre_destinataire,titre,contenu,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                prepare.setLong(2, this.idPrec);
                prepare.setLong(3, this.membreExpediteur.getId());
                prepare.setLong(4, this.membreDestinataire.getId());
                prepare.setString(5, this.titreMsg);
                prepare.setString(6, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_prec=? AND id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idPrec);
                prepare.setLong(2, this.membreExpediteur.getId());
                prepare.setLong(3, this.membreDestinataire.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                long idMessage=result.getLong("idMessage");
                result.close();
                prepare.close();
                Mail mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Un nouveau message !");
                mail.initMailMessage2(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                HttpSession session=request.getSession(true);
                session.setAttribute("captcha", null);
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("ERREUR : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void effaceRecu(Membre membre) {
        try {
            this.membreDestinataire=membre;
            this.getConnection();
            String query="UPDATE table_messages SET id_prec=? WHERE id_prec=? AND id_membre_expediteur=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, 0);
            prepare.setLong(2, this.id);
            prepare.setLong(3, this.membreDestinataire.getId());
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_messages WHERE id=? AND id_membre_destinataire=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreDestinataire.getId());
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void effaceEnvoye(Membre membre) {
        try {
            this.membreExpediteur=membre;
            this.getConnection();
            String query="UPDATE table_messages SET id_prec=? WHERE id_prec=? AND id_membre_destinataire=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, 0);
            prepare.setLong(2, this.id);
            prepare.setLong(3, this.membreExpediteur.getId());
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_messages WHERE id=? AND id_membre_expediteur=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreExpediteur.getId());
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void blank() {
        super.blank();
        this.membreExpediteur=null;
        this.membreDestinataire=null;
        this.titreMsg="";
        this.contenuMsg="";
    }
    /**
     * @return the idAnnonce
     */
    public long getIdAnnonce() {
        return idAnnonce;
    }

    /**
     * @return the membreExpediteur
     */
    public Membre getMembreExpediteur() {
        return membreExpediteur;
    }

    /**
     * @return the titreAnnonce
     */
    public String getTitreAnnonce() {
        return titreAnnonce;
    }

    /**
     * @return the membreDestinataire
     */
    public Membre getMembreDestinataire() {
        return membreDestinataire;
    }

    /**
     * @return the titreMsg
     */
    public String getTitreMsg() {
        return titreMsg;
    }

    /**
     * @return the contenuMsg
     */
    public String getContenuMsg() {
        return contenuMsg;
    }

    /**
     * @return the idPrec
     */
    public long getIdPrec() {
        return idPrec;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @return the dateTxt
     */
    public String getDateTxt() {
        return dateTxt;
    }

    /**
     * @return the titrePrec
     */
    public String getTitrePrec() {
        return titrePrec;
    }

    /**
     * @return the contenuPrec
     */
    public String getContenuPrec() {
        return contenuPrec;
    }

    /**
     * @return the dateTxtPrec
     */
    public String getDateTxtPrec() {
        return dateTxtPrec;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

}
