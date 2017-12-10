package ru.job4j.userservlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 * UpdateServlet.
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        try {
            req.setAttribute("user", UsersDeposit.getInstance().getUserCS(login));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Timestamp date = Timestamp.valueOf(req.getParameter("date"));
        try {
            if (UsersDeposit.getInstance().updateUserCS(name, login, email, date)) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                writer.append("Error. login does not exist!");
                writer.append("<br/><a <form><input type='button' value='Here all users' ");
                writer.append("onclick=\"window.location.href='UsersView.jsp'\"/></form></a>");
                writer.flush();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
