/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Annonces;
import classes.Membre;
import classes.SousCategorie;
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
public class AnnoncesSousCategorie extends HttpServlet {

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
            if(request.getParameter("idCategorie")==null||request.getParameter("idSousCategorie")==null)
                request.setAttribute("info", 1);
            else {
                long idCategorie=Long.parseLong(request.getParameter("idCategorie"));
                long idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
                SousCategorie sousCategorie=new SousCategorie(idCategorie, idSousCategorie);
                sousCategorie.initInfosAnnoncesSousCategorie(request);
                if(sousCategorie.getId()==0)
                    request.setAttribute("info", 1);
                else {
                    SousCategories sousCategories=new SousCategories(idCategorie, idSousCategorie);
                    sousCategories.initListe2();
                    Annonces annonces=new Annonces(idCategorie, idSousCategorie);
                    if(request.getParameter("reset")!=null) {
                        annonces.resetPage(request);
                    }
                    annonces.getGetsPage(request);
                    annonces.initValuesPage(request);
                    annonces.initListeSousCategorie();
                    //out.println(annonces.getErrorMsg());
                    request.setAttribute("annonces", annonces);
                    request.setAttribute("sousCategories", sousCategories);
                    request.setAttribute("sousCategorie", sousCategorie);
                }
            }
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/annoncessouscategorie.jsp");
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
