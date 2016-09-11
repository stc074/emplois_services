/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Annonces;
import classes.Categories;
import classes.ListeRegions;
import classes.Membre;
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
public class Index extends HttpServlet {
    private Annonces annonces;
    private ListeRegions listeRegions;
    private Categories categories;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.annonces=new Annonces();
        this.annonces.initListeIndex();
        this.listeRegions=new ListeRegions();
        this.listeRegions.initListeIndex();
        this.categories=new Categories();
        this.categories.initListeIndex();
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
            session.setAttribute("uriRetour", "./");
            boolean flag=this.annonces.testFlag();
            if(flag) {
                this.annonces=new Annonces();
                this.annonces.initListeIndex();
            }
            request.setAttribute("annonces", this.annonces);
            //out.println(this.listeRegions.getErrorMsg());
            request.setAttribute("listeRegions", this.listeRegions);
            request.setAttribute("categories", this.categories);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/index.jsp");
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
