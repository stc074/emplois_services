/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Membre extends Objet {
    
    private long id;
    private String pseudo;
    private String email;
    private String motDePasse;
    private String motDePasse2;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private String captcha;
    private long timestamp;
    private String date;
    
    public Membre() {
        super();
        this.pseudo="";
        this.email="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.id=0;
    }

    Membre(long idMembre) {
        super();
        this.id=idMembre;
    }

    public void getPostsInsc(HttpServletRequest request) {
        this.pseudo=this.codeHTML(request.getParameter("pseudo"));
        this.email=this.codeHTML(request.getParameter("email")).toLowerCase();
        this.motDePasse=this.codeHTML(request.getParameter("motDePasse"));
        this.motDePasse2=this.codeHTML(request.getParameter("motDePasse2"));
        this.idRegion=this.codeHTML(request.getParameter("idRegion"));
        this.idDepartement=this.codeHTML(request.getParameter("idDepartement"));
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
        this.captcha=this.codeHTML(request.getParameter("captcha")).toLowerCase();
    }
    public void verifPostsInsc(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            this.getConnection();
            Pattern p=Pattern.compile("^[0-9a-zA-Z]+$");
            Matcher m=p.matcher(this.pseudo);
            if(this.pseudo.length()==0)
                this.setErrorMsg("<div>Champ PSEUDONYME vide.</div>");
            else if(this.pseudo.length()<2)
                this.setErrorMsg("<div>Champ PSEUDONYME trop court.</div>");
            else if(this.pseudo.length()>30)
                this.setErrorMsg("<div>Champ PSEUDONYME trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ PSEUDONYME non valide (Caractères alphanumériques uniquement).</div>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb>0)
                    this.setErrorMsg("<div>Désolé, ce PSEUDONYME est déjà pris.</div>");
            }
            p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$");
            m=p.matcher(this.email);
            if(this.email.length()==0)
                this.setErrorMsg("<div>Champ ADRESSE EMAIL vide.</div>");
            else if(this.email.length()>100)
                this.setErrorMsg("<div>Champ ADRESSE EMAIL trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ ADRESSE EMAIL non-valide.</div>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb>0)
                    this.setErrorMsg("<div>Désolé, cette ADRESSE EMAIL est déjà prise.</div>");
            }
            p=Pattern.compile("^[a-zA-Z0-9]+$");
            m=p.matcher(this.motDePasse);
            Matcher m2=p.matcher(this.motDePasse2);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("<div>Champ MOT DE PASSE vide.</div>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop court.</div>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ MOT DE PASSE non-valide (Caractères alphanumériques seulement).</div>");
            else if(this.motDePasse2.length()==0)
                this.setErrorMsg("<div>Champ CONFIRMATION DU MOT DE PASSE vide.</div>");
            else if(this.motDePasse2.length()<3)
                this.setErrorMsg("<div>Champ CONFIRMATION DU MOT DE PASSE trop court.</div>");
            else if(this.motDePasse2.length()>15)
                this.setErrorMsg("<div>Champ CONFIRMATION DU MOT DE PASSE trop long.</div>");
            else if(m2.matches()==false)
                this.setErrorMsg("<div>Champ CONFIRMATION DU MOT DE PASSE non-valide (Caractères alphanumériques seulement).</div>");
            else if(!this.motDePasse.equals(this.motDePasse2))
                this.setErrorMsg("<div>Vos 2 MOTS DE PASSE sont différents.</div>");
            if(this.idRegion.equals("0"))
                this.setErrorMsg("<div>Veuillez choisir votre RÉGION SVP.</div>");
            if(this.idDepartement.equals("0"))
                this.setErrorMsg("<div>Veuillez choisir votre DÉPARTEMENT SVP.</div>");
            if(this.idCommune==0)
                this.setErrorMsg("<div>Veuillez choisir votre COMMUNE SVP.</div>");
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").equals(this.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void enregInsc(HttpServletRequest request, HttpServletResponse response) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.getConnection();
                String query="INSERT INTO table_membres (pseudo,email,mot_de_passe,id_region,id_departement,id_commune,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.email);
                prepare.setString(3, this.getEncoded(this.motDePasse));
                prepare.setString(4, this.idRegion);
                prepare.setString(5, this.idDepartement);
                prepare.setInt(6, this.idCommune);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMembre FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idMembre");
                result.close();
                prepare.close();
                session.setAttribute("idMembre", this.getId());
                session.setAttribute("pseudo", this.pseudo);
                this.setCookie(this.getId(), response);
                session.setAttribute("captcha", null);
                Mail mail=new Mail(this.email, this.pseudo, "Inscription !");
                mail.initMailInscription(this.pseudo, this.email, this.motDePasse);
                mail.send();
                this.blank();
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void testConnecte(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.id=0;
        if(session.getAttribute("idMembre")!=null&&session.getAttribute("pseudo")!=null) {
            this.pseudo=this.codeHTML(session.getAttribute("pseudo").toString());
            this.id=Long.parseLong(session.getAttribute("idMembre").toString());
            session.setAttribute("idMembre", this.getId());
            session.setAttribute("pseudo", this.pseudo);
        } else {
            Cookie[] cookies=request.getCookies();
            Cookie realCook=null;
            if(cookies!=null) {
                for(Cookie cookie:cookies) {
                    if(cookie.getName().equals("emploisercook")) {
                        realCook=cookie;
                        break;
                    }
                }
            }
            if(realCook!=null) {
                try {
                    String cookieCode=realCook.getValue();
                    this.getConnection();
                    String query="SELECT id,pseudo FROM table_membres WHERE cookie_code=? LIMIT 0,1";
                    PreparedStatement prepare=this.con.prepareStatement(query);
                    prepare.setString(1, cookieCode);
                    ResultSet result=prepare.executeQuery();
                    boolean flag=result.next();
                    if(flag) {
                        this.id=result.getLong("id");
                        this.pseudo=result.getString("pseudo");
                        session.setAttribute("idMembre", this.getId());
                        session.setAttribute("pseudo", this.pseudo);
                    }
                    result.close();
                    prepare.close();
                } catch (NamingException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.id=0;
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.id=0;
                } finally {
                    try {
                        this.closeConnection();
                    } catch (SQLException ex) {
                        Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                        this.id=0;
                    }
                }
            }
        }
    }

    public void getPostsCon(HttpServletRequest request) {
        this.pseudo=this.codeHTML(request.getParameter("pseudo"));
        this.motDePasse=this.codeHTML(request.getParameter("motDePasse"));
    }

    public void verifPostsCon(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.getConnection();
            Pattern p=Pattern.compile("^[a-zA-Z-0-9]+$");
            Matcher m=p.matcher(this.pseudo);
            if(this.pseudo.length()==0)
                this.setErrorMsg("<div>Champ PSEUDO vide.</div>");
            else if(this.pseudo.length()<2)
                this.setErrorMsg("<div>Champ PSEUDO trop court.</div>");
            else if(this.pseudo.length()>30)
                this.setErrorMsg("<div>Champ PSEUDO trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ PSEUDO non-valide.</div>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb==0)
                    this.setErrorMsg("<div>PSEUDO inconnu.</div>");
            }
            m=p.matcher(this.motDePasse);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("<div>Champ MOT DE PASSE vide.</div>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop court.</div>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ MOT DE PASSE non-valide.</div>");
            else if(this.getErrorMsg().length()==0) {
                String query="SELECT id FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                boolean flag=result.next();
                if(flag) {
                    this.id=result.getLong("id");
                    this.setCookie(getId(), response);
                    HttpSession session=request.getSession(true);
                    session.setAttribute("pseudo", this.pseudo);
                    session.setAttribute("idMembre", this.getId());
                } else if(!flag) {
                    this.setErrorMsg("<div>Mauvais MOT DE PASSE.</div>");
                }
                result.close();
                prepare.close();
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void getPostsMdpOublie(HttpServletRequest request) {
        this.email=this.codeHTML(request.getParameter("email")).toLowerCase();
        this.captcha=this.codeHTML(request.getParameter("captcha").toLowerCase());
    }

    public void verifPostsMdpOublie(HttpServletRequest request) {
        try {
            Pattern p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$");
            Matcher m=p.matcher(this.email);
            if(this.email.length()==0)
                this.setErrorMsg("<div>Champ ADRESSE-EMAIL vide.</div>");
            else if(this.email.length()>100)
                this.setErrorMsg("<div>Champ ADRESSE-EMAIL trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ ADRESSE-EMAIL non-valide.</div>");
            else {
                try {
                    this.getConnection();
                    String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=?";
                    PreparedStatement prepare=this.con.prepareStatement(query);
                    prepare.setString(1, this.email);
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    int nb=result.getInt("nb");
                    result.close();
                    prepare.close();
                    if(nb==0)
                        this.setErrorMsg("<div>Désolé, cette ADRESSE-EMAIL nous est inconnue.</div>");
                } catch (NamingException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                } finally {
                    try {
                        this.closeConnection();
                    } catch (SQLException ex) {
                        Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                        this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                    }
                }
            }
            HttpSession session=request.getSession(true);
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").toString().equals(this.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void sendNouveauMdp(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.motDePasse="";
                for(int i=0; i<=5; i++)
                    this.motDePasse+=Datas.ARRAYCHARS[(int)(Math.random()*Datas.ARRAYCHARS.length)];
                this.getConnection();
                String query="SELECT id,pseudo FROM table_membres WHERE email=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("id");
                this.pseudo=result.getString("pseudo");
                result.close();
                prepare.close();
                query="UPDATE table_membres SET mot_de_passe=? WHERE id=?";
                prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.getEncoded(this.motDePasse));
                prepare.setLong(2, this.id);
                prepare.executeUpdate();
                prepare.close();
                session.setAttribute("captcha", null);
                Mail mail=new Mail(this.email, this.pseudo, "Nouveau mot de passe !");
                mail.initMailNouveauMdp(this.pseudo, this.email, this.motDePasse);
                mail.send();
                this.blank();
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initInfosPersos() {
        try {
            this.getConnection();
            String query="SELECT pseudo,email,mot_de_passe,id_region,id_departement,id_commune,timestamp FROM table_membres WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.pseudo=result.getString("pseudo");
            this.email=result.getString("email");
            this.motDePasse=result.getString("mot_de_passe");
            this.idRegion=result.getString("id_region");
            this.idDepartement=result.getString("id_departement");
            this.idCommune=result.getInt("id_commune");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.date="Dernières modifications : le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void getPostsInfosPersos(HttpServletRequest request) {
        this.motDePasse=this.codeHTML(request.getParameter("motDePasse"));
        this.motDePasse2=this.codeHTML(request.getParameter("motDePasse2"));
        this.idRegion=this.codeHTML(request.getParameter("idRegion"));
        this.idDepartement=this.codeHTML(request.getParameter("idDepartement"));
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
        this.captcha=this.codeHTML(request.getParameter("captcha")).toLowerCase();
    }

    public void verifPostsInfosPersos(HttpServletRequest request) {
        try {
            if(this.motDePasse.length()>0) {
                Pattern p=Pattern.compile("^[a-zA-Z0-9]+$");
                Matcher m=p.matcher(this.motDePasse);
                Matcher m2=p.matcher(this.motDePasse2);
                if(this.motDePasse.length()<3)
                    this.setErrorMsg("<div>Champ NOUVEAU MOT DE PASSE trop court.</div>");
                else if(this.motDePasse.length()>15)
                    this.setErrorMsg("<div>Champ NOUVEAU MOT DE PASSE trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ NOUVEAU MOT DE PASSE non-valide.</div>");
                else if(this.motDePasse2.length()==0)
                    this.setErrorMsg("<div>Champ CONFIRMATION DU NOUVEAU MOT DE PASSE vide.</div>");
                else if(this.motDePasse2.length()<3)
                    this.setErrorMsg("<div>Champ CONFIRMATION DU NOUVEAU MOT DE PASSE trop court.</div>");
                else if(this.motDePasse2.length()>15)
                    this.setErrorMsg("<div>Champ CONFIRMATION DU NOUVEAU MOT DE PASSE trop long.</div>");
                else if(m2.matches()==false)
                    this.setErrorMsg("<div>Champ CONFIRMATION DU NOUVEAU MOT DE PASSE non-valide.</div>");
                else if(!this.motDePasse.equals(this.motDePasse2))
                    this.setErrorMsg("<div>Vos 2 MOTS DE PASSE sont différents.</div>");
            }
            if(this.idRegion.equals("0"))
                this.setErrorMsg("<div>Veuillez choisir votre RÉGION SVP.</div>");
            if(this.idDepartement.equals("0"))
                this.setErrorMsg("<div>Veuillez choisir votre DÉPARTEMENT SVP.</div>");
            if(this.idCommune==0)
                this.setErrorMsg("<div>Veuilles choisir votre COMMUNE SVP.</div>");
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
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void enregInfosPersos(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            this.getConnection();
            String query="";
            Calendar cal=Calendar.getInstance();
            this.timestamp=cal.getTimeInMillis();
            if(this.motDePasse.length()>0)
                query="UPDATE table_membres SET mot_de_passe=?,id_region=?,id_departement=?,id_commune=?,timestamp=? WHERE id=?";
            else if(this.motDePasse.length()==0)
                query="UPDATE table_membres SET id_region=?,id_departement=?,id_commune=?,timestamp=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            if(this.motDePasse.length()>0) {
                try {
                    prepare.setString(1, this.getEncoded(this.motDePasse));
                    prepare.setString(2, this.idRegion);
                    prepare.setString(3, this.idDepartement);
                    prepare.setInt(4, this.idCommune);
                    prepare.setLong(5, this.timestamp);
                    prepare.setLong(6, this.id);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            } else if(this.motDePasse.length()==0) {
                prepare.setString(1, this.idRegion);
                prepare.setString(2, this.idDepartement);
                prepare.setInt(3, this.idCommune);
                prepare.setLong(4, this.timestamp);
                prepare.setLong(5, this.id);
            }
            prepare.executeUpdate();
            prepare.close();
            session.setAttribute("captcha", null);
            this.setTest(1);
            if(this.motDePasse.length()>0) {
                Mail mail=new Mail(this.email, this.pseudo, "Mot de passe modifié !");
                mail.initMailNouveauMdp2(this.pseudo, this.email, this.motDePasse);
                mail.send();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void verifPostsCon2(HttpServletRequest request) {
        Pattern p=Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher m=p.matcher(this.pseudo);
        if(this.pseudo.length()==0)
            this.setErrorMsg("<div>Champ PSEUDONYME vide.</div>");
        else if(this.pseudo.length()<2)
            this.setErrorMsg("<div>Champ PSEUDONYME trop court.</div>");
        else if(this.pseudo.length()>30)
            this.setErrorMsg("<div>Champ PSEUDONYME trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ PSEUDONYME non-valide (Caractères alphanumériques seulement).</div>");
        else {
            try {
                this.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb==0)
                    this.setErrorMsg("<div>PSEUDONYME inconnu.</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
        m=p.matcher(this.motDePasse);
        if(this.motDePasse.length()==0)
            this.setErrorMsg("<div>Champ MOT DE PASSE vide.</div>");
        else if(this.motDePasse.length()<3)
            this.setErrorMsg("<div>Champ MOT DE PASSE trop court.</div>");
        else if(this.motDePasse.length()>15)
            this.setErrorMsg("<div>Champ MOT DE PASSE trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ MOT DE PASSE non-valide (Caractères alphanumériques uniquement).</div>");
        else if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb==0)
                    this.setErrorMsg("<div>Mauvais MOT DE PASSE.</div>");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void conDepot(HttpServletRequest request, HttpServletResponse response) {
        if(this.getErrorMsg().length()==0) {
        try {
            this.getConnection();
            String query="SELECT id FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.pseudo);
            prepare.setString(2, this.getEncoded(this.motDePasse));
            ResultSet result=prepare.executeQuery();
            result.next();
            this.id=result.getLong("id");
            result.close();
            prepare.close();
            HttpSession session=request.getSession(true);
            session.setAttribute("idMembre", this.id);
            session.setAttribute("pseudo", this.pseudo);
            this.setCookie(this.id, response);
            this.setTest(1);
        }   catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
        }
        }
    }

   @Override
    public void blank() {
        super.blank();
        this.pseudo="";
        this.email="";
        this.motDePasse="";
        this.motDePasse2="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
    }


    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the idRegion
     */
    public String getIdRegion() {
        return idRegion;
    }

    /**
     * @return the idDepartement
     */
    public String getIdDepartement() {
        return idDepartement;
    }

    /**
     * @return the idCommune
     */
    public int getIdCommune() {
        return idCommune;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

}
