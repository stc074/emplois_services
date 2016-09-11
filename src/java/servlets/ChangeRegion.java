/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Objet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class ChangeRegion extends HttpServlet {
    private Objet obj;

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
        if(request.getParameter("idRegion")!=null) {
        PrintWriter out = response.getWriter();
        try {
                out.println("<label for=\"idDepartement\">Votre DÉPARTEMENT :</label>");
                out.println("<select name=\"idDepartement\" id=\"idDepartement\" onchange=\"javascript:changeDepartement(this.value);\">");
                out.println("<option value=\"0\" selected=\"selected\">Choisissez</option>");
                this.obj=new Objet();
                String idRegion=this.obj.codeHTML(request.getParameter("idRegion"));
                this.obj.getConnection();
                if(!idRegion.equals("0")) {
                String query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement ASC";
                PreparedStatement prepare=obj.getCon().prepareStatement(query);
                prepare.setString(1, idRegion);
                ResultSet result=prepare.executeQuery();
                while(result.next()) {
                    String idDepartement=result.getString("id_departement");
                    String departement=result.getString("departement");
                    out.println("<option value=\""+idDepartement+"\">"+idDepartement+"-"+departement+"</option>");
                }
                result.close();
                prepare.close();
                }
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally {
                try {
                    this.obj.closeConnection();
                    out.println("</select>");
                    out.println("<br/>");
                    out.println("<div id=\"idCommunes\">");
                    out.println("<label for=\"idCommune\">Votre COMMUNE :</label>");
                    out.println("<select name=\"idCommune\" id=\"idCommune\">");
                    out.println("<option value=\"0\" selected=\"selected\">Choisissez</option>");
                    out.println("</select>");
                    out.println("</div>");
                    out.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ChangeRegion.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
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
