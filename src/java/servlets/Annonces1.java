/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Annonces;
import classes.Categories;
import classes.ListeCommunes;
import classes.ListeDepartements;
import classes.ListeRegions;
import classes.Membre;
import classes.SousCategories;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Annonces1 extends HttpServlet {
    private Categories categories;
    private ListeRegions listeRegions;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.categories=new Categories();
        this.categories.initListe();
        this.listeRegions=new ListeRegions();
        this.listeRegions.initListe();
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Membre mbr=new Membre();
            mbr.testConnecte(request);
            if(request.getParameter("kermitCnx")!=null) {
                mbr.getPostsCon(request);
                mbr.verifPostsCon(request, response);
            }
            request.setAttribute("mbr", mbr);
            HttpSession session=mbr.initSession(request, response);
            session.setAttribute("uriRetour", "./annonces1.html#form");
            Annonces annonces=new Annonces();
            if(request.getParameter("kermit")!=null) {
                annonces.getPostsRecherche(request);
                annonces.verifPostsRecherche(request);
            } else if(request.getParameter("reset")!=null) {
                annonces.resetRecherche(request);
            }
            annonces.initValuesRecherche(request);
            annonces.initCondition();
            annonces.initListeRecherche();
            SousCategories sousCategories=new SousCategories(annonces.getIdCategorie());
            sousCategories.initListe();
            ListeDepartements listeDepartements=new ListeDepartements(annonces.getIdRegion());
            listeDepartements.initListe();
            ListeCommunes listeCommunes=new ListeCommunes(annonces.getIdRegion(), annonces.getIdDepartement());
            listeCommunes.initListe();
            request.setAttribute("annonces", annonces);
            request.setAttribute("categories", this.categories);
            request.setAttribute("sousCategories", sousCategories);
            request.setAttribute("listeRegions", this.listeRegions);
            request.setAttribute("listeDepartements", listeDepartements);
            request.setAttribute("listeCommunes", listeCommunes);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/annonces.jsp");
            dispatch.forward(request, response);
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Membre mbr=new Membre();
            mbr.testConnecte(request);
            if(request.getParameter("kermitCnx")!=null) {
                mbr.getPostsCon(request);
                mbr.verifPostsCon(request, response);
            }
            request.setAttribute("mbr", mbr);
            HttpSession session=mbr.initSession(request, response);
            session.setAttribute("uriRetour", "./annonces1.html#form");
            Annonces annonces=new Annonces();
            if(request.getParameter("reset")!=null)
                annonces.resetRecherche(request);
            annonces.getGetsRecherche(request);
            annonces.initValuesRecherche(request);
            annonces.initCondition();
            annonces.initListeRecherche();
            SousCategories sousCategories=new SousCategories(annonces.getIdCategorie());
            sousCategories.initListe();
            ListeDepartements listeDepartements=new ListeDepartements(annonces.getIdRegion());
            listeDepartements.initListe();
            ListeCommunes listeCommunes=new ListeCommunes(annonces.getIdRegion(), annonces.getIdDepartement());
            listeCommunes.initListe();
            request.setAttribute("annonces", annonces);
            request.setAttribute("categories", this.categories);
            request.setAttribute("sousCategories", sousCategories);
            request.setAttribute("listeRegions", this.listeRegions);
            request.setAttribute("listeDepartements", listeDepartements);
            request.setAttribute("listeCommunes", listeCommunes);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/annonces.jsp");
            dispatch.forward(request, response);
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally {            
            out.close();
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
