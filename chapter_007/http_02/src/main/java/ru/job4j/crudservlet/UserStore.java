package ru.job4j.crudservlet;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * UsersServlet. Database.
 * Saving UserCS data in the PostgreSQL
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class UserStore {

    private static final Logger LOG = LoggerFactory.getLogger(UserStore.class);
    private String url;
    private String user;
    private String password;
    private String tableName;

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;

    private static UserStore instance;

    private UserStore() throws SQLException, IOException {
        this.getProperties();
        this.connectDb();
    }
    @GuardedBy("UserStore.class")
    public static UserStore getInstance() throws IOException, SQLException {
        if (instance == null) {
            synchronized (UserStore.class) {
                if (instance == null) {
                    instance = new UserStore();
                }
            }
        }
        return instance;
    }

    private void getProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream in = this.getClass().getClassLoader().
                getResourceAsStream("connection.properties")) {
            properties.load(in);
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            this.tableName = properties.getProperty("tableName");
        } catch (IOException  e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void connectDb() throws SQLException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            this.rs = this.conn.getMetaData().
                    getTables(null, null, this.tableName, null);
            if (!this.rs.next()) {
                this.createTable();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void disconnectDb() {
        try {
            assert conn != null;
            this.conn.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            if (this.ps != null) {
                this.ps.close();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            if (this.st != null) {
                this.st.close();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            if (this.rs != null) {
                this.rs.close();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void createTable() throws SQLException, IOException {
        this.st = this.conn.createStatement();
                try (InputStream in = this.getClass().getClassLoader().
                        getResourceAsStream("createTableCS.sql")) {
                Scanner sc = new Scanner(in);
                String line;
                StringBuilder command = new StringBuilder();
                while (sc.hasNext()) {
                    line = sc.nextLine();
                    if (!line.endsWith(";")) {
                        command.append(" ").append(line);
                    } else {
                        command.append(line);
                        this.st.execute(command.toString());
                    }
                }
        } catch (SQLException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public boolean addUserCS(String userName, String userLogin, String userEmail)
            throws SQLException {
        boolean result = false;
        String request = String.format("INSERT INTO %s (name, login, email, date) VALUES(?,?,?,?)",
                this.tableName);
        this.ps = this.conn.prepareStatement(request);
        try {
            this.ps.setString(1, userName);
            this.ps.setString(2, userLogin);
            this.ps.setString(3, userEmail);
            this.ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            result = this.ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public UserCS getUserCS(String login) throws SQLException {
        UserCS user = null;
        String request = String.format("SELECT name, login, email, date FROM %s WHERE login = ?",
                this.tableName);
        this.ps = this.conn.prepareStatement(request);
        try {
            this.ps.setString(1, login);
            this.rs = this.ps.executeQuery();
            while (rs.next()) {
                user = new UserCS(rs.getString("name"),
                        this.rs.getString("login"),
                        this.rs.getString("email"),
                        this.rs.getTimestamp("date")
                );
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    public boolean updateUserCS(String login, String modifyName, String newEmail)
            throws SQLException {
        boolean result = false;
        UserCS user = getUserCS(login);
        if (user != null) {
            String request = String.format("UPDATE %s SET name = '%s', email = '%s' "
                    + "WHERE login = '%s'", this.tableName, modifyName, newEmail, login);
            this.st = this.conn.createStatement();
            try {
                result = this.st.executeUpdate(request) > 0;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return result;
    }

    public boolean deleteUserCS(String login) throws SQLException {
        boolean result = false;
        String request = String.format("DELETE FROM %s WHERE login = '%s'", this.tableName, login);
        this.st = this.conn.createStatement();
        try {
            result = this.st.executeUpdate(request) > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}
