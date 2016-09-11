/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.ListeCommunes;
import classes.ListeDepartements;
import classes.ListeRegions;
import classes.Membre;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class DeposerAnnonce1 extends HttpServlet {
    private ListeRegions listeRegions;
    
    @Override
    public void init() throws ServletException {
        super.init();
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
            mbr.initSession(request, response);
            Membre membre1=new Membre();
            Membre membre2=new Membre();
            if(request.getParameter("kermit1")!=null) {
                membre1.getPostsCon(request);
                membre1.verifPostsCon2(request);
                membre1.conDepot(request, response);
            }
            if(request.getParameter("kermit2")!=null) {
                membre2.getPostsInsc(request);
                membre2.verifPostsInsc(request);
                membre2.enregInsc(request, response);
            }
            ListeDepartements listeDepartements=new ListeDepartements(membre2.getIdRegion());
            listeDepartements.initListe();
            ListeCommunes listeCommunes=new ListeCommunes(membre2.getIdRegion(), membre2.getIdDepartement());
            listeCommunes.initListe();
            if(membre1.getTest()==1||membre2.getTest()==1)
                response.sendRedirect("./deposer-annonce-2.html");
            request.setAttribute("membre1", membre1);
            request.setAttribute("membre2", membre2);
            request.setAttribute("listeRegions", this.listeRegions);
            request.setAttribute("listeDepartements", listeDepartements);
            request.setAttribute("listeCommunes", listeCommunes);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/deposerannonce1.jsp");
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
        processRequest(request, response);
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
