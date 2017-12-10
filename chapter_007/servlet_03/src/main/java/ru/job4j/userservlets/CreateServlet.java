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
 * CreateServlet.
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
public class CreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        Timestamp date = Timestamp.valueOf(req.getParameter("date"));
        try {
            if (UsersDeposit.getInstance().addUserCS(name, login, email, date)) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                writer.append("Login already in use. Try again!");
                writer.append("<br/><a <form><input type='button' value='Here all users' ");
                writer.append("onclick=\"window.location.href='UsersView.jsp'\"/></form></a>");
                writer.flush();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
