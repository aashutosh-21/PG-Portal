package inert.me;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowAllPropertyForCustomer extends HttpServlet {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {
            con = inert.me.Util.connect();
            String qr = "select * from property where status='vacant' and permission='granted'";
            ps = con.prepareStatement(qr);
            rs = ps.executeQuery();
            boolean b = rs.next();
            rs.beforeFirst();
            HttpSession session = request.getSession();
            String id = (String) session.getAttribute("userid");
            if (id == null) {
                response.sendRedirect("index.jsp");
            }
            if (b == false) {
                out.println("<html><body>");
                out.println("<center><font size=7>Paying Guest Portal</font></center>");
                out.println("<hr>");
                out.println("<h2>No Such Property Found!!!</h2>");
                out.println("<hr>");
                out.println("<a href=customerDashboard.jsp>Home</a>");
                out.println("</body></html>");
            } else {

                out.println("<html>");
                out.println("<body>");
                out.println("<center><h2><font face=\"algerian\" color=\"red\"><u>Paying Guest Portal</u></font></h2></center>");
                out.println("<center><hr><a href=customerDashboard.jsp>Customer-Dashboard</a><hr></center>");
                out.println("<center><h2><font face=algerian>All Property Details</font></h2></center>");
                out.println("<hr>");
                out.println("<center><table border=2>");
                out.println("<tr>");
                out.println("<th width=70>Code</th>");
                //    out.println("<th>email</th>");
                out.println("<th width=70>Type</th>");
                out.println("<th width=70>Food</th>");
                out.println("<th width=170>Description</th>");
                out.println("<th width=70>Rent Price</th>");
                out.println("<th width=90>Customer Type</th>");
                out.println("<th width=270>Address</th>");
                out.println("<th width=70>Status</th>");
                out.println("<th width=90>Photo</th>");
                out.println("</tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td><center>" + rs.getString("pcode") + "</center></td>");
                    //        out.println("<td>"+rs.getString("email")+"</td>");
                    out.println("<td><center>" + rs.getString("ptype") + "</center></td>");
                    out.println("<td><center>" + rs.getString("ftype") + "</center></td>");
                    out.println("<td><center>" + rs.getString("fdesc") + "</center></td>");
                    out.println("<td><center>" + rs.getString("rent") + "</center></td>");
                    out.println("<td><center>" + rs.getString("ctype") + "</center></td>");
                    out.println("<td><center>" + rs.getString("hno") + "," + rs.getString("street") + "," + rs.getString("city") + "," + rs.getString("state") + "," + rs.getString("country") + "</center></td>");
                    out.println("<td><center>" + rs.getString("status") + "</center></td>");
                    out.println("<td><center><img height=100 width=150 src=\"imageloader?id=" + rs.getString("pcode") + "\"></center></td>");
                    out.println("<td><center><a href='ShowOwnerDetails?id=" + rs.getString("pcode") + "'>owner</a></center></td>");
                    out.println("</tr>");
                }
                out.println("</table></center>");
                out.println("<hr>");
                out.println("<center><a href=customerDashboard.jsp>Customer-Dashboard</a></center>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (Exception e) {
            out.println(e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
