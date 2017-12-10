package ru.job4j.crudservlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * UsersServlet. Servlet.
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UsersServlet extends HttpServlet {
    /**
     * Logger for database errors.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);
    /**
     * Specified database manager instance.
     */
    private final UserStore userCS = UserStore.getInstance();

    public UsersServlet() throws IOException, SQLException {
    }
    /**
     * Returns user specified by login.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String userLogin = req.getParameter("login");
        UserCS user = null;
        try {
            user = this.userCS.getUserCS(userLogin);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (user != null) {
            writer.append(user.toString());
        } else {
            writer.append(String.format("There is no user with login %s.", userLogin));
        }
        writer.flush();
    }

    /**
     * Edits user's information.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String newName = req.getParameter("name");
        String login = req.getParameter("login");
        String newEmail = req.getParameter("email");
        try {
            this.userCS.updateUserCS(login, newName, newEmail);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }
    /**
     * Creates new user.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String userName = req.getParameter("name");
        String userLogin = req.getParameter("login");
        String userEmail = req.getParameter("email");
        try {
            this.userCS.addUserCS(userName, userLogin, userEmail);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }
    /**
     * Deletes user specified by name and login.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String userLogin = req.getParameter("login");
        try {
            this.userCS.deleteUserCS(userLogin);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        this.userCS.disconnectDb();
    }
}