/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Categories;
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
public class AjouterSousCategorie extends HttpServlet {

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
            SousCategorie sousCategorie=new SousCategorie();
            if(request.getParameter("kermit")!=null) {
                sousCategorie.getPostsAjout(request);
                sousCategorie.verifPostsAjout();
                sousCategorie.enregAjout();
            }
            Categories categories=new Categories();
            categories.initListe();
            SousCategories sousCategories=new SousCategories(sousCategorie.getIdCategorie());
            sousCategories.initCategorieInfo();
            sousCategories.initListe();
            request.setAttribute("sousCategorie", sousCategorie);
            request.setAttribute("categories", categories);
            request.setAttribute("sousCategories", sousCategories);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/nimdo/scripts/ajoutersouscategorie.jsp");
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
            SousCategorie sousCategorie=new SousCategorie();
            sousCategorie.getGets(request);
            Categories categories=new Categories();
            categories.initListe();
            SousCategories sousCategories=new SousCategories(sousCategorie.getIdCategorie());
            sousCategories.initCategorieInfo();
            sousCategories.initListe();
            request.setAttribute("sousCategorie", sousCategorie);
            request.setAttribute("categories", categories);
            request.setAttribute("sousCategories", sousCategories);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/nimdo/scripts/ajoutersouscategorie.jsp");
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
