/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Membre;
import classes.Message;
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
public class ContactAnnonceur extends HttpServlet {

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
                if(request.getParameter("idAnnonce")==null)
                    request.setAttribute("info", 2);
                else {
                    long idAnnonce=Long.parseLong(request.getParameter("idAnnonce"));
                    Message message=new Message(membre, idAnnonce);
                    message.initInfos1();
                    if(message.getIdAnnonce()==0||message.getMembreExpediteur().getId()==0||message.getMembreDestinataire().getId()==0)
                        request.setAttribute("info", 2);
                    else {
                        if(request.getParameter("kermit")!=null) {
                            message.getPostsContact(request);
                            message.verifPostsContact(request);
                            message.enregContact1(request);
                        }
                        request.setAttribute("message", message);
                    }
                }
            }
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/contactannonceur.jsp");
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
