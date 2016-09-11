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
import java.sql.Statement;
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
public class Annonces extends Objet {
    
    private String tagTitle;
    private String tagDescription;
    private String motsCles;
    private int type;
    private long idCategorie;
    private long idSousCategorie;
    private String tarifHoraireMin;
    private String tarifHoraireMax;
    private double tarifHoraireMinDouble;
    private double tarifHoraireMaxDouble;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private String condition;
    private int nbAnnonces;
    private int nbAnnoncesPage;
    private int nbPages;
    private int page;
    private String[] urls;
    private String[] titres;
    private String[] imgs;
    private String[] lignes1;
    private String[] lignes2;
    private String[] lignes3;
    private int prem;
    private int der;
    private int nb;
    private int nbMembres;
    private String[] lignes4;
    private Membre membre;
    private String[] comments;
    private long[] ids;
    private int nbAnnoncesPage2;
    
    public Annonces() {
        super();
        this.tagTitle="Annonces d'emplois - Emploi service";
        this.tagDescription="Emploi services - Toutes les annonces d'emplois service dans votre région.";
        this.motsCles="";
        this.type=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.tarifHoraireMin="";
        this.tarifHoraireMax="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.page=0;
    }

    public Annonces(Membre membre) {
        super();
        this.membre=membre;
    }
    
    public Annonces(String idRegion) {
        super();
        this.idRegion=idRegion;
        this.page=0;
    }

    public Annonces(String idRegion, String idDepartement) {
        super();
        this.idRegion=idRegion;
        this.idDepartement=idDepartement;
        this.page=0;
    }
    
    public Annonces(long idCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.page=0;
    }
    
    public Annonces(long idCategorie, long idSousCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.idSousCategorie=idSousCategorie;
        this.page=0;
    }
    
    public void getGetsRecherche(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("type")!=null) {
            this.type=Integer.parseInt(request.getParameter("type"));
            if(this.type==1||this.type==2)
                session.setAttribute("type", this.type);
            else {
                this.type=0;
                session.setAttribute("type", null);
            }
        }
        else if(request.getParameter("idCategorie")!=null) {
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            this.idSousCategorie=0;
            session.setAttribute("idSousCategorie", null);
            if(this.idCategorie==0)
                session.setAttribute("idCategorie", null);
            else {
                session.setAttribute("idCategorie", this.idCategorie);
           }
        }
        else if(request.getParameter("idSousCategorie")!=null) {
            this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
            if(this.idSousCategorie==0)
                session.setAttribute("idSousCategorie", null);
            else
                session.setAttribute("idSousCategorie", this.idSousCategorie);
        }
        else if(request.getParameter("idRegion")!=null) {
            this.idDepartement="0";
            session.setAttribute("idDepartement", null);
            this.idCommune=0;
            session.setAttribute("idCommune", null);
            this.idRegion=this.codeHTML(request.getParameter("idRegion"));
            if(this.idRegion.equals("0"))
                session.setAttribute("idRegion", null);
            else
                session.setAttribute("idRegion", this.idRegion);
        }
        else if(request.getParameter("idDepartement")!=null) {
            this.idCommune=0;
            session.setAttribute("idCommune", null);
            this.idDepartement=this.codeHTML(request.getParameter("idDepartement"));
            if(this.idDepartement.equals("0"))
                session.setAttribute("idDepartement", null);
            else
                session.setAttribute("idDepartement", this.idDepartement);
        } else if(request.getParameter("idCommune")!=null) {
            this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
            if(this.idCommune==0)
                session.setAttribute("idCommune", null);
            else
                session.setAttribute("idCommune", this.idCommune);
        } else if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", this.page);
        }
    }

    public void initValuesRecherche(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.motsCles="";
        if(session.getAttribute("motsCles")!=null)
            this.motsCles=this.codeHTML(session.getAttribute("motsCles").toString());
        this.type=0;
        if(session.getAttribute("type")!=null)
            this.type=Integer.parseInt(session.getAttribute("type").toString());
        this.idCategorie=0;
        if(session.getAttribute("idCategorie")!=null)
            this.idCategorie=Long.parseLong(session.getAttribute("idCategorie").toString());
        this.idSousCategorie=0;
        if(session.getAttribute("idSousCategorie")!=null)
            this.idSousCategorie=Long.parseLong(session.getAttribute("idSousCategorie").toString());
        this.tarifHoraireMin="";
        if(session.getAttribute("tarifHoraireMin")!=null)
            this.tarifHoraireMin=this.codeHTML(session.getAttribute("tarifHoraireMin").toString());
        this.tarifHoraireMax="";
        if(session.getAttribute("tarifHoraireMax")!=null)
            this.tarifHoraireMax=this.codeHTML(session.getAttribute("tarifHoraireMax").toString());
        this.idRegion="0";
        if(session.getAttribute("idRegion")!=null)
            this.idRegion=this.codeHTML(session.getAttribute("idRegion").toString());
        this.idDepartement="0";
        if(session.getAttribute("idDepartement")!=null)
            this.idDepartement=this.codeHTML(session.getAttribute("idDepartement").toString());
        this.idCommune=0;
        if(session.getAttribute("idCommune")!=null)
            this.idCommune=Integer.parseInt(session.getAttribute("idCommune").toString());
        this.page=0;
        if(session.getAttribute("page")!=null)
            this.page=Integer.parseInt(session.getAttribute("page").toString());
    }

    public void getPostsRecherche(HttpServletRequest request) {
        this.motsCles=this.codeHTML(request.getParameter("motsCles"));
        if(request.getParameter("type")!=null) {
            this.type=Integer.parseInt(request.getParameter("type"));
        }
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.tarifHoraireMin=this.codeHTML(request.getParameter("tarifHoraireMin"));
        this.tarifHoraireMax=this.codeHTML(request.getParameter("tarifHoraireMax"));
        this.idRegion=this.codeHTML(request.getParameter("idRegion"));
        this.idDepartement=this.codeHTML(request.getParameter("idDepartement"));
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
    }

    public void resetRecherche(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.motsCles="";
        session.setAttribute("motsCles", null);
        this.type=0;
        session.setAttribute("type", null);
        this.idCategorie=0;
        session.setAttribute("idCategorie", null);
        this.idSousCategorie=0;
        session.setAttribute("idSousCategorie", null);
        this.tarifHoraireMin="";
        session.setAttribute("tarifHoraireMin", null);
        this.tarifHoraireMax="";
        session.setAttribute("tarifHoraireMax", null);
        this.idRegion="0";
        session.setAttribute("idRegion", null);
        this.idDepartement="0";
        session.setAttribute("idDepartement", null);
        this.idCommune=0;
        session.setAttribute("idCommune", null);
        this.page=0;
        session.setAttribute("page", null);
    }

    public void verifPostsRecherche(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(this.motsCles.length()==0||this.motsCles.length()>300) {
            this.motsCles="";
            session.setAttribute("motsCles", null);
        } else 
            session.setAttribute("motsCles", this.motsCles);
        if(!(this.type==1||this.type==2)) {
            this.type=0;
            session.setAttribute("type", null);
        } else
            session.setAttribute("type", this.type);
        if(this.idCategorie==0) {
            session.setAttribute("idCategorie", null);
            this.idSousCategorie=0;
            session.setAttribute("idSousCategorie", null);
        }
        else
            session.setAttribute("idCategorie", this.idCategorie);
        if(this.idSousCategorie==0)
            session.setAttribute("idSousCategorie", null);
        else
            session.setAttribute("idSousCategorie", this.idSousCategorie);
        if(this.tarifHoraireMin.length()==0||this.tarifHoraireMin.length()>10) {
            this.tarifHoraireMin="";
            session.setAttribute("tarifHoraireMin", null);
        } else {
            try {
                this.tarifHoraireMinDouble=Double.parseDouble(this.tarifHoraireMin.replaceAll(",", "."));
                session.setAttribute("tarifHoraireMin", this.tarifHoraireMin);
            } catch(Exception ex) {
                this.tarifHoraireMin="";
                session.setAttribute("tarifHoraireMin", null);
            }
        }
        if(this.tarifHoraireMax.length()==0||this.tarifHoraireMax.length()>10) {
            this.tarifHoraireMax="";
            session.setAttribute("tarifHoraireMax", null);
        } else {
            try {
                this.tarifHoraireMaxDouble=Double.parseDouble(this.tarifHoraireMax.replaceAll(",", "."));
                session.setAttribute("tarifHoraireMax", this.tarifHoraireMax);
            } catch(Exception ex) {
                this.tarifHoraireMax="";
                session.setAttribute("tarifHoraireMax", null);
            }
        }
        if(this.idRegion.equals("0"))
            session.setAttribute("idRegion", null);
        else
            session.setAttribute("idRegion", this.idRegion);
        if(this.idDepartement.equals("0"))
            session.setAttribute("idDepartement", null);
        else
            session.setAttribute("idDepartement", this.idDepartement);
        if(this.idCommune==0)
            session.setAttribute("idCommune", null);
        else
            session.setAttribute("idCommune", this.idCommune);
    }

    public void initCondition() {
        this.condition=" WHERE t1.etat='1'";
        if(this.motsCles.length()>0) {
            String motsCles2=this.encodeRecherche(this.motsCles);
            String[] arrayMots=motsCles2.split(" ");
            for(String mot:arrayMots)
                this.condition+=" AND (t1.titre LIKE '%"+mot+"%' OR t1.description LIKE '%"+mot+"%')";
        }
        if(this.type==1||this.type==2)
            this.condition+=" AND t1.type='"+this.type+"'";
        if(this.idSousCategorie!=0)
            this.condition+=" AND t1.id_sous_categorie='"+this.idSousCategorie+"'";
        else if(this.idCategorie!=0)
            this.condition+=" AND t1.id_categorie='"+this.idCategorie+"'";
        if(this.tarifHoraireMin.length()>0)
            this.condition+=" AND t1.tarif_horaire>='"+this.tarifHoraireMinDouble+"'";
        if(this.tarifHoraireMax.length()>0)
            this.condition+=" AND tarif_horaire<='"+this.tarifHoraireMaxDouble+"'";
        if(this.idCommune!=0)
            this.condition+=" AND t2.id_commune='"+this.idCommune+"'";
        else if(!this.idDepartement.equals("0"))
            this.condition+=" AND t2.id_departement='"+this.idDepartement+"'";
        else if(!this.idRegion.equals("0"))
            this.condition+=" AND t2.id_region='"+this.idRegion+"'";
    }

    public void initListeRecherche() {
        try {
            this.nbAnnonces=0;
            this.nbAnnoncesPage=0;
            this.getConnection();
            String query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces AS t1,table_membres AS t2"+this.condition+" AND t2.id=t1.id_membre";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            state.close();
            if(this.nbAnnonces>0) {
                this.nbPages=(int)Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE);
                if(this.nbAnnonces<=Datas.NBANNONCESPAGE)
                    this.nbAnnoncesPage=this.nbAnnonces;
                else if(this.page<(this.nbPages-1))
                    this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
                else if(this.page==(this.nbPages-1))
                    this.nbAnnoncesPage=this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
                if(this.nbAnnoncesPage>0) {
                    this.nbAnnoncesPage2=(int)(Math.floor((double)this.nbAnnoncesPage/(double)2));
                    this.prem=this.page-5;
                    if(this.getPrem()<0)
                        this.prem=0;
                    this.der=this.page+5;
                    if(this.getDer()>(this.nbPages-1))
                        this.der=this.nbPages-1;
                    this.urls=new String[this.nbAnnoncesPage];
                    this.titres=new String[this.nbAnnoncesPage];
                    this.imgs=new String[this.nbAnnoncesPage];
                    this.lignes1=new String[this.nbAnnoncesPage];
                    this.lignes2=new String[this.nbAnnoncesPage];
                    this.lignes3=new String[this.nbAnnoncesPage];
                    this.lignes4=new String[this.nbAnnoncesPage];
                    query="SELECT t1.id,t1.type,t1.tarif_horaire,t1.titre,t1.extension,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1, table_membres AS t2, table_categories AS t3, table_sous_categories AS t4, table_regions AS t5, table_departements AS t6, table_communes AS t7"+this.condition+" AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                    state=this.con.createStatement();
                    result=state.executeQuery(query);
                    int i=0;
                    Calendar cal=Calendar.getInstance();
                    Img img=new Img();
                    File fileMini2=null;
                    while(result.next()) {
                        long idAnnonce=result.getLong("id");
                        int type2=result.getInt("type");
                        String tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.",",");
                        String titre=result.getString("titre");
                        String extension=result.getString("extension");
                        long timestamp=result.getLong("timestamp");
                        String pseudo=result.getString("pseudo");
                        String categorie=result.getString("categorie");
                        String sousCategorie=result.getString("sous_categorie");
                        String region=result.getString("region");
                        String departement=result.getString("departement");
                        String commune=result.getString("commune");
                        String codePostal=result.getString("code_postal");
                        cal.setTimeInMillis(timestamp);
                        if(extension.length()>0) {
                            String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+""+extension;
                            fileMini2=new File(filenameMini2);
                            if(fileMini2.exists()) {
                                try {
                                    img.getSize(fileMini2);
                                    int largeur=img.getWidth();
                                    int hauteur=img.getHeight();
                                    this.imgs[i]="<img src=\"./photo-mini-2-"+idAnnonce+""+extension+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\"/>";
                                } catch (IOException ex) {
                                    Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                                    this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                                }
                            } else {
                                this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                            }
                        } else {
                            this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                        }
                        this.urls[i]="./annonce-emploi-"+idAnnonce+"-"+this.encodeTitre(titre)+".html#annonce";
                        this.titres[i]=titre;
                        this.lignes1[i]="Annonce déposée par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                        this.lignes2[i]="";
                        switch(type2) {
                            case 1:
                                this.lignes2[i]+="<span>Demande d'emploi</span>&nbsp;";
                                break;
                            case 2:
                                this.lignes2[i]+="<span>Offre d'emploi</span>&nbsp;";
                                break;
                        }
                        this.lignes2[i]+="<span>"+categorie+"&rarr;"+sousCategorie+".</span>";
                        switch(type2) {
                            case 1:
                                this.lignes4[i]="<span>Tarif horaire souhaité de "+tarifHoraire+" &euro; NET de l'heure.</span>";
                                break;
                            case 2:
                                this.lignes4[i]="<span>Payé "+tarifHoraire+" &euro; NET de l'heure.</span>";
                                break;
                        }
                        this.lignes3[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                        i++;
                    }
                    result.close();
                    state.close();
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.nbAnnoncesPage=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.nbAnnoncesPage=0;
            }
        }
    }

    public void initListeIndex() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbMembres FROM table_membres";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbMembres=result.getInt("nbMembres");
            result.close();
            state.close();
            query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces";
            state=this.con.createStatement();
            result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nbAnnonces");
            result.close();
            state.close();
            query="UPDATE table_flag SET flag=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 0);
            prepare.setLong(2, 1);
            prepare.executeUpdate();
            prepare.close();
            this.nbAnnonces=this.nb;
            if(this.nb>Datas.NBANNONCESINDEX)
                this.nb=Datas.NBANNONCESINDEX;
            if(this.nb>0) {
                this.urls=new String[this.nb];
                this.titres=new String[this.nb];
                this.imgs=new String[this.nb];
                this.lignes1=new String[this.nb];
                this.lignes2=new String[this.nb];
                this.lignes3=new String[this.nb];
                this.lignes4=new String[this.nb];
                query="SELECT t1.id,t1.type,t1.tarif_horaire,t1.titre,t1.extension,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1, table_membres AS t2, table_categories AS t3, table_sous_categories AS t4, table_regions AS t5, table_departements AS t6, table_communes AS t7 WHERE t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                File fileMini2=null;
                Calendar cal=Calendar.getInstance();
                Img img=new Img();
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    int type2=result.getInt("type");
                    String tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.", ",");
                    String titre=result.getString("titre");
                    String extension=result.getString("extension");
                    long timestamp=result.getLong("timestamp");
                    String pseudo=result.getString("pseudo");
                    String categorie=result.getString("categorie");
                    String sousCategorie=result.getString("sous_categorie");
                    String region=result.getString("region");
                    String departement=result.getString("departement");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    cal.setTimeInMillis(timestamp);
                    if(extension.length()>0) {
                        String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+""+extension;
                        fileMini2=new File(filenameMini2);
                        if(fileMini2.exists()) {
                            try {
                                img.getSize(fileMini2);
                                int largeur=img.getWidth();
                                int hauteur=img.getHeight();
                                this.imgs[i]="<img src=\"./photo-mini-2-"+idAnnonce+""+extension+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\"/>";
                            } catch (IOException ex) {
                                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                                this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                            }
                        } else {
                            this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                        }
                    } else {
                        this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                    }
                    this.urls[i]="./annonce-emploi-"+idAnnonce+"-"+this.encodeTitre(titre)+".html#annonce";
                    this.titres[i]=titre;
                    this.lignes1[i]="";
                    switch(type2) {
                        case 1:
                            this.lignes1[i]+="<span>Demande d'emploi&nbsp</span>";
                            break;
                        case 2:
                            this.lignes1[i]+="<span>Offre d'emploi&nbsp;</span>";
                            break;
                    }
                    this.lignes1[i]+="<span>déposée par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".</span>";
                    this.lignes2[i]="<span>"+categorie.toUpperCase()+"&rarr;"+sousCategorie.toUpperCase()+".</span>";
                    this.lignes3[i]="";
                    switch(type2) {
                        case 1:
                            this.lignes3[i]+="<span>Tarif horaire souhaité de "+tarifHoraire+" &euro; NET de l'heure.</span>";
                            break;
                        case 2:
                            this.lignes3[i]+="<span>Payé "+tarifHoraire+" &euro; NET de l'heure.</span>";
                            break;
                    }
                    this.lignes4[i]="<span>Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".</span>";
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
            this.nbAnnonces=0;
            this.nbMembres=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
            this.nbAnnonces=0;
            this.nbMembres=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
                this.nbAnnonces=0;
                this.nbMembres=0;
            }
        }
    }

    public boolean testFlag() {
        boolean flag=false;
        try {
            this.getConnection();
            String query="SELECT flag FROM table_flag WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, 1);
            ResultSet result=prepare.executeQuery();
            result.next();
            int flag2=result.getInt("flag");
            result.close();
            prepare.close();
            if(flag2==1)
                flag=true;
            else
                flag=false;
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        }
        return flag;
    }

    public void initListeAnnonces() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces WHERE id_membre=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nb=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            if(this.nb>0) {
                this.titres=new String[this.nb];
                this.comments=new String[this.nb];
                this.ids=new long[this.nb];
                query="SELECT id,titre,timestamp FROM table_annonces WHERE id_membre=? ORDER BY timestamp DESC";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membre.getId());
                result=prepare.executeQuery();
                int i=0;
                Calendar cal=Calendar.getInstance();
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    String titre=result.getString("titre");
                    long timestamp=result.getLong("timestamp");
                    cal.setTimeInMillis(timestamp);
                    this.ids[i]=idAnnonce;
                    this.titres[i]=titre;
                    this.comments[i]="Dernière modification, le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    public void initListeRegion() {
        try {
            this.getConnection();
            String query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces AS t1,table_membres AS t2 WHERE t2.id=t1.id_membre AND t2.id_region=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.idRegion);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            this.nbPages=(int)(Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE));
            this.nbAnnoncesPage=0;
            if(this.nbAnnonces<=Datas.NBANNONCESPAGE)
                this.nbAnnoncesPage=this.nbAnnonces;
            else if(this.page<(this.nbPages-1))
                this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
            else if(this.page==(this.nbPages-1))
                this.nbAnnoncesPage=this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
            if(this.nbAnnoncesPage>0) {
                this.nbAnnoncesPage2=(int)(Math.floor((double)this.nbAnnoncesPage/(double)2));
                this.prem=this.page-5;
                if(this.prem<0)
                    this.prem=0;
                this.der=this.page+5;
                if(this.der>(this.nbPages-1))
                    this.der=this.nbPages-1;
                this.urls=new String[this.nbAnnoncesPage];
                this.titres=new String[this.nbAnnoncesPage];
                this.imgs=new String[this.nbAnnoncesPage];
                this.lignes1=new String[this.nbAnnoncesPage];
                this.lignes2=new String[this.nbAnnoncesPage];
                this.lignes3=new String[this.nbAnnoncesPage];
                this.lignes4=new String[this.nbAnnoncesPage];
                query="SELECT t1.id,t1.type,t1.tarif_horaire,t1.titre,t1.extension,t1.timestamp, t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7 WHERE t2.id_region=? AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.idRegion);
                result=prepare.executeQuery();
                int i=0;
                Calendar cal=Calendar.getInstance();
                Img img=new Img();
                File fileMini2=null;
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    int type2=result.getInt("type");
                    String tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.", ",");
                    String titre=result.getString("titre");
                    String extension=result.getString("extension");
                    long timestamp=result.getLong("timestamp");
                    String pseudo=result.getString("pseudo");
                    String categorie=result.getString("categorie");
                    String sousCategorie=result.getString("sous_categorie");
                    String region=result.getString("region");
                    String departement=result.getString("departement");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    cal.setTimeInMillis(timestamp);
                    if(extension.length()>0) {
                        String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+""+extension;
                        fileMini2=new File(filenameMini2);
                        if(fileMini2.exists()) {
                            try {
                                img.getSize(fileMini2);
                                int largeur=img.getWidth();
                                int hauteur=img.getHeight();
                                this.imgs[i]="<img src=\"./photo-mini-2-"+idAnnonce+""+extension+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\" />";
                            } catch (IOException ex) {
                                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                                this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\" />";
                            }
                        } else {
                            this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\" />";
                        }
                    } else {
                        this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\" />";
                    }
                    this.urls[i]="./annonce-emploi-"+idAnnonce+"-"+this.encodeTitre(titre)+".html#annonce";
                    this.titres[i]=titre;
                    this.lignes1[i]="Annonce déposée par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.lignes2[i]=categorie.toUpperCase()+"&rarr;"+sousCategorie.toUpperCase()+".";
                    this.lignes3[i]="";
                    switch(type2) {
                        case 1:
                            this.lignes3[i]="Demande d'emploi - Tarif horaire souhaité de "+tarifHoraire+" &euro; NET de l'heure.";
                            break;
                        case 2:
                            this.lignes3[i]="Offre d'emploi - Payé "+tarifHoraire+" &euro; NET de l'heure.";
                            break;
                    }
                    this.lignes4[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.idRegion="0";
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idRegion="0";
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.idRegion="0";
            }
        }
    }

    public void getGetsRegion(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            if(this.page==0)
                session.setAttribute("page", null);
            else
                session.setAttribute("page", this.page);
        }
    }
    public void resetRegion(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        session.setAttribute("page", null);
    }
    
    public void initValuesRegion(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        if(session.getAttribute("page")!=null)
            this.page=Integer.parseInt(session.getAttribute("page").toString());
    }

    public void initListeDepartement() {
        try {
            this.nbAnnoncesPage=0;
            this.getConnection();
            String query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces AS t1, table_membres AS t2 WHERE t2.id=t1.id_membre AND t2.id_region=? AND t2.id_departement=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.idRegion);
            prepare.setString(2, this.idDepartement);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            if(this.nbAnnonces>0) {
                this.nbPages=(int)(Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE));
                if(this.nbAnnonces<=Datas.NBANNONCESPAGE)
                    this.nbAnnoncesPage=this.nbAnnonces;
                else if(this.page<(this.nbPages-1))
                    this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
                else if(this.page==(this.nbPages-1))
                    this.nbAnnoncesPage=this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
                if(this.nbAnnoncesPage>0) {
                    this.prem=this.page-5;
                    if(this.prem<0)
                        this.prem=0;
                    this.der=this.page+5;
                    if(this.der>(this.nbPages-1))
                        this.der=this.nbPages-1;
                    this.nbAnnoncesPage2=(int)(Math.floor((double)this.nbAnnoncesPage)/(double)2);
                    this.urls=new String[this.nbAnnoncesPage];
                    this.titres=new String[this.nbAnnoncesPage];
                    this.imgs=new String[this.nbAnnoncesPage];
                    this.lignes1=new String[this.nbAnnoncesPage];
                    this.lignes2=new String[this.nbAnnoncesPage];
                    this.lignes3=new String[this.nbAnnoncesPage];
                    this.lignes4=new String[this.nbAnnoncesPage];
                    query="SELECT t1.id,t1.type,t1.tarif_horaire,t1.titre,t1.extension,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7 WHERE t2.id_region=? AND t2.id_departement=? AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                    prepare=this.con.prepareStatement(query);
                    prepare.setString(1, this.idRegion);
                    prepare.setString(2, this.idDepartement);
                    result=prepare.executeQuery();
                    this.initAnnonce(result);
                    result.close();
                    prepare.close();
                }
            }
         } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.nbAnnoncesPage=0;
            }
        }
        
    }

    private void initAnnonce(ResultSet result) throws SQLException {
        int i=0;
        Calendar cal=Calendar.getInstance();
        Img img=new Img();
        File fileMini2=null;
        while(result.next()) {
            long idAnnonce=result.getLong("id");
            int type2=result.getInt("type");
            String tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.", ",");
            String titre=result.getString("titre");
            String extension=result.getString("extension");
            long timestamp=result.getLong("timestamp");
            String pseudo=result.getString("pseudo");
            String categorie=result.getString("categorie");
            String sousCategorie=result.getString("sous_categorie");
            String region=result.getString("region");
            String departement=result.getString("departement");
            String commune=result.getString("commune");
            String codePostal=result.getString("code_postal");
            cal.setTimeInMillis(timestamp);
            if(extension.length()>0) {
                String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+""+extension;
                fileMini2=new File(filenameMini2);
                if(fileMini2.exists()) {
                    try {
                        img.getSize(fileMini2);
                        int largeur=img.getWidth();
                        int hauteur=img.getHeight();
                        this.imgs[i]="<img src=\"./photo-mini-2-"+idAnnonce+""+extension+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\" />";
                    } catch (IOException ex) {
                        Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                        this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\" />";
                    }
                } else {
                    this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\" />";
                }
            } else {
                this.imgs[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\" />";
            }
            this.urls[i]="./annonce-emploi-"+idAnnonce+"-"+this.encodeTitre(titre)+".html#annonce";
            this.titres[i]=titre;
            this.lignes1[i]="Annonce déposée par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
            this.lignes2[i]=categorie.toUpperCase()+"&rarr;"+sousCategorie.toUpperCase()+".";
            this.lignes3[i]="";
            switch(type2) {
                case 1:
                    this.lignes3[i]="Demande d'emploi - Tarif horaire souhaité de "+tarifHoraire+" &euro; NET de l'heure.";
                    break;
                case 2:
                    this.lignes3[i]="Offre d'emploi payé "+tarifHoraire+" &euro; NET de l'heure.";
                    break;
            }
            this.lignes4[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
            i++;
        }
    }

    public void resetDepartement(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        session.setAttribute("page", null);
    }

    public void getGetsDepartement(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            if(this.page==0)
                session.setAttribute("page", null);
            else
                session.setAttribute("page", this.page);
        }
    }

    public void initValuesDepartement(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        if(session.getAttribute("page")!=null)
            this.page=Integer.parseInt(session.getAttribute("page").toString());
    }

    public void initListeCategorie() {
        try {
            this.nbAnnoncesPage=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbAnnonces FROM  table_annonces WHERE id_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            if(this.nbAnnonces>0) {
                this.nbAnnoncesPage2=(int)(Math.floor((double)this.nbAnnoncesPage/(double)2));
                this.nbPages=(int)(Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE));
                if(this.nbAnnonces<=Datas.NBANNONCESPAGE)
                    this.nbAnnoncesPage=this.nbAnnonces;
                else if(this.page<(this.nbPages-1))
                    this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
                else if(this.page==(this.nbPages-1))
                    this.nbAnnoncesPage=this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
                if(this.nbAnnoncesPage>0) {
                    this.prem=this.page-5;
                    if(this.prem<0)
                        this.prem=0;
                    this.der=this.page+5;
                    if(this.der>(this.nbPages-1))
                        this.der=this.nbPages-1;
                    this.urls=new String[this.nbAnnoncesPage];
                    this.titres=new String[this.nbAnnoncesPage];
                    this.imgs=new String[this.nbAnnoncesPage];
                    this.lignes1=new String[this.nbAnnoncesPage];
                    this.lignes2=new String[this.nbAnnoncesPage];
                    this.lignes3=new String[this.nbAnnoncesPage];
                    this.lignes4=new String[this.nbAnnoncesPage];
                    query="SELECT t1.id,t1.type,t1.tarif_horaire,t1.titre,t1.extension,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2, table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7 WHERE t1.id_categorie=? AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                    prepare=this.con.prepareStatement(query);
                    prepare.setLong(1, this.idCategorie);
                    result=prepare.executeQuery();
                    this.initAnnonce(result);
                    result.close();
                    prepare.close();
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.nbAnnoncesPage=0;
            }
        }
    }

    public void resetPage(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        session.setAttribute("page", null);
    }

    public void getGetsPage(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("page")!=null) {
            try {
            this.page=Integer.parseInt(request.getParameter("page"));
            if(this.page==0)
                session.setAttribute("page", null);
            else
                session.setAttribute("page", this.page);
            } catch(Exception ex) {
                this.page=0;
                session.setAttribute("page", null);
            }
        }
    }

    public void initValuesPage(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        if(session.getAttribute("page")!=null) {
            try {
                this.page=Integer.parseInt(session.getAttribute("page").toString());
            } catch(Exception ex) {
                this.page=0;
                session.setAttribute("page", null);
            }
        }
    }


    public void initListeSousCategorie() {
        try {
            this.getConnection();
            String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces WHERE id_categorie=? AND id_sous_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            prepare.setLong(2, this.idSousCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            this.nbAnnoncesPage=0;
            if(this.nbAnnonces>0) {
                this.nbPages=(int)(Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE));
                if(this.nbAnnonces<=Datas.NBANNONCESPAGE)
                    this.nbAnnoncesPage=this.nbAnnonces;
                else if(this.page<(this.nbPages-1))
                    this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
                else if(this.page==(this.nbPages-1))
                    this.nbAnnoncesPage=this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
                if(this.nbAnnoncesPage>0) {
                    this.nbAnnoncesPage2=(int)(Math.floor((double)this.nbAnnoncesPage/(double)2));
                    this.prem=this.page-5;
                    if(this.prem<0)
                        this.prem=0;
                    this.der=this.page+5;
                    if(this.der>(this.nbPages-1))
                        this.der=this.nbPages-1;
                    this.urls=new String[this.nbAnnoncesPage];
                    this.titres=new String[this.nbAnnoncesPage];
                    this.imgs=new String[this.nbAnnoncesPage];
                    this.lignes1=new String[this.nbAnnoncesPage];
                    this.lignes2=new String[this.nbAnnoncesPage];
                    this.lignes3=new String[this.nbAnnoncesPage];
                    this.lignes4=new String[this.nbAnnoncesPage];
                    query="SELECT t1.id,t1.type,t1.tarif_horaire,t1.titre,t1.extension,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7 WHERE t1.id_categorie=? AND t1.id_sous_categorie=? AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                    prepare=this.con.prepareStatement(query);
                    prepare.setLong(1, this.idCategorie);
                    prepare.setLong(2, this.idSousCategorie);
                    result=prepare.executeQuery();
                    this.initAnnonce(result);
                    result.close();
                    prepare.close();
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.nbAnnoncesPage=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.nbAnnoncesPage=0;
            }
        }
    }

    public void testOlds() {
        try {
            this.getConnection();
            Calendar cal=Calendar.getInstance();
            long ts=(long) (cal.getTimeInMillis()-(Datas.getNBMOISLIMITE()*1000l*60l*60l*24l*30l));
            String query="SELECT t1.id,t1.id_membre,t1.titre,t2.pseudo,t2.email FROM table_annonces AS t1, table_membres AS t2 WHERE t1.timestamp<? AND t1.flag_limite=? AND t2.id=t1.id_membre";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, ts);
            prepare.setInt(2, 0);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                long idMembre=result.getLong("id_membre");
                String titre=result.getString("titre");
                String pseudo=result.getString("pseudo");
                String email=result.getString("email");
                String query2="UPDATE table_annonces SET flag_limite=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare2=this.con.prepareStatement(query2);
                prepare2.setInt(1, 1);
                prepare2.setLong(2, idAnnonce);
                prepare2.setLong(3, idMembre);
                prepare2.executeUpdate();
                prepare2.close();
                Mail mail=new Mail(email, pseudo, "Votre annonce d'emploi !");
                mail.initMailLimite(pseudo, titre);
                mail.send();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void effaceOlds() {
        try {
            this.getConnection();
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis()-(Datas.getNBMOISEFFACE()*1000l*60l*60l*24l*30l);
            String query="SELECT id,id_membre FROM table_annonces WHERE timestamp<?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, ts);
            ResultSet result=prepare.executeQuery();
            Annonce annonce=new Annonce();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                long idMembre=result.getLong("id_membre");
                annonce.effaceAnnonce3(idMembre, idAnnonce);
            }
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
     * @return the motsCles
     */
    public String getMotsCles() {
        return motsCles;
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
     * @return the tarifHoraireMin
     */
    public String getTarifHoraireMin() {
        return tarifHoraireMin;
    }

    /**
     * @return the tarifHoraireMax
     */
    public String getTarifHoraireMax() {
        return tarifHoraireMax;
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
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @return the nbAnnonces
     */
    public int getNbAnnonces() {
        return nbAnnonces;
    }

    /**
     * @return the nbAnnoncesPage
     */
    public int getNbAnnoncesPage() {
        return nbAnnoncesPage;
    }

    /**
     * @return the nbPages
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the imgs
     */
    public String[] getImgs() {
        return imgs;
    }

    /**
     * @return the lignes1
     */
    public String[] getLignes1() {
        return lignes1;
    }

    /**
     * @return the lignes2
     */
    public String[] getLignes2() {
        return lignes2;
    }

    /**
     * @return the lignes3
     */
    public String[] getLignes3() {
        return lignes3;
    }

    /**
     * @return the prem
     */
    public int getPrem() {
        return prem;
    }

    /**
     * @return the der
     */
    public int getDer() {
        return der;
    }

    /**
     * @return the nb
     */
    public int getNb() {
        return nb;
    }

    /**
     * @return the nbMembres
     */
    public int getNbMembres() {
        return nbMembres;
    }

    /**
     * @return the lignes4
     */
    public String[] getLignes4() {
        return lignes4;
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

    /**
     * @return the nbAnnoncesPage2
     */
    public int getNbAnnoncesPage2() {
        return nbAnnoncesPage2;
    }

}
