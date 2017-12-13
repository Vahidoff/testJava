package ru.job4j.testservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
/**
 * DeleteServlet.
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("login");
        try {
            UsersDeposit.getInstance().deleteUserCS(login);
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
