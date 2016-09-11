/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Annonce;
import classes.Categories;
import classes.Membre;
import classes.SousCategories;
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
public class DeposerAnnonce2 extends HttpServlet {
    private Categories categories;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.categories=new Categories();
        this.categories.initListe();
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
            Membre membre=new Membre();
            membre.testConnecte(request);
            if(membre.getId()==0)
                request.setAttribute("info", 1);
            else {
                membre.initInfosPersos();
                if(membre.getId()==0)
                    request.setAttribute("info", 1);
                else {
                    Annonce annonce=new Annonce(membre);
                    if(request.getParameter("kermit")!=null) {
                        annonce.getPostsDepot(request);
                        annonce.verifPostsDepot(request);
                        annonce.enregAnnonceDepot(request);
                    }
                    if(annonce.getTest()==1)
                        response.sendRedirect("./deposer-annonce-photo.html");
                    SousCategories sousCategories=new SousCategories(annonce.getIdCategorie());
                    sousCategories.initListe();
                    request.setAttribute("annonce", annonce);
                    request.setAttribute("sousCategories", sousCategories);
                    request.setAttribute("categories", this.categories);
                }
            }
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/deposerannonce2.jsp");
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
            mbr.initSession(request, response);
            Membre membre=new Membre();
            membre.testConnecte(request);
            if(membre.getId()==0)
                request.setAttribute("info", 1);
            else {
                membre.initInfosPersos();
                if(membre.getId()==0)
                    request.setAttribute("info", 1);
                else {
                    Annonce annonce=new Annonce(membre);
                    annonce.getGetsDepot(request);
                    SousCategories sousCategories=new SousCategories(annonce.getIdCategorie());
                    sousCategories.initListe();
                    request.setAttribute("annonce", annonce);
                    request.setAttribute("categories", this.categories);
                    request.setAttribute("sousCategories", sousCategories);
                }
            }
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/deposerannonce2.jsp");
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
