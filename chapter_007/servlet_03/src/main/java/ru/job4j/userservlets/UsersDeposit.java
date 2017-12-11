package ru.job4j.userservlets;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crudservlet.UserCS;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
/**
 * UsersDeposit.
 * work in the PostgreSQL.
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class UsersDeposit {

    private String tableName;
    private static final Logger LOG = LoggerFactory.getLogger(UsersDeposit.class);

    private String url;
    private String user;
    private String password;

    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;
    private Connection conn;
    private static UsersDeposit instance;

    private UsersDeposit() throws IOException, SQLException {
        this.getProperties();
        this.connectDb();
    }

    @GuardedBy("UsersDeposit.class")
    public static UsersDeposit getInstance() throws IOException, SQLException {
        if (instance == null) {
            synchronized (UsersDeposit.class) {
                if (instance == null) {
                    instance = new UsersDeposit();
                }
            }
        }
        return instance;
    }

    public UserCS getUserCS(String login) throws SQLException, IOException {
        this.connectDb();
        UserCS user = null;
        String request = String.format("SELECT name, login, email, date FROM %s WHERE login = ?",
                this.tableName);
        this.ps = conn.prepareStatement(request);
        try {
            this.ps.setString(1, login);
            this.rs = this.ps.executeQuery();
            while (rs.next()) {
                user = new UserCS(rs.getString("name"),
                        login,
                        this.rs.getString("email"),
                        this.rs.getTimestamp("date")
                );
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        this.disconnectDb(conn);
        return user;
    }

    public boolean updateUserCS(String modifyName, String login, String newEmail, Timestamp date)
            throws SQLException, IOException {
        this.connectDb();
        boolean result = false;
        UserCS user = getUserCS(login);
        conn.setAutoCommit(false);
        if (user != null) {
            String request = String.format("UPDATE %s SET name = '%s', email = '%s', date = '%s' "
                    + "WHERE login = '%s'", this.tableName, modifyName, newEmail, date, login);
            this.st = conn.createStatement();
            try {
                result = this.st.executeUpdate(request) > 0;
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                LOG.error(e.getMessage(), e);
            }
        }
        this.disconnectDb(conn);
        return result;
    }

    public boolean addUserCS(String userName, String userLogin, String userEmail, Timestamp date)
            throws SQLException, IOException {
        this.connectDb();
        boolean result = false;
        conn.setAutoCommit(false);
        String request = String.format("INSERT INTO %s (name, login, email, date) VALUES(?,?,?,?)",
                this.tableName);
        this.ps = conn.prepareStatement(request);
        try {
            this.ps.setString(1, userName);
            this.ps.setString(2, userLogin);
            this.ps.setString(3, userEmail);
            this.ps.setTimestamp(4, date);
            result = this.ps.executeUpdate() > 0;
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            LOG.error(e.getMessage(), e);
        }
        this.disconnectDb(conn);
        return result;
    }

    public boolean deleteUserCS(String login) throws SQLException, IOException {
        boolean result = false;
        this.connectDb();
        conn.setAutoCommit(false);
        String request = String.format("DELETE FROM %s WHERE login = '%s'", this.tableName, login);
        this.st = conn.createStatement();
        try {
            result = this.st.executeUpdate(request) > 0;
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            LOG.error("deleting has failed", e.getMessage(), e);
        }
        this.disconnectDb(conn);
        return result;
    }

    private List<String> getAllLogin() throws IOException, SQLException {
        this.connectDb();
        List<String> allLogin = new ArrayList<>();
        try {
            this.st = conn.createStatement();
            this.rs = this.st.executeQuery(String.format("SELECT login FROM %s", this.tableName));
            while (this.rs.next()) {
                allLogin.add(rs.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.disconnectDb(conn);
        return allLogin;
    }

    public List<UserCS> getAllUserCS() throws IOException, SQLException {
        List<UserCS> users = new ArrayList<>();
        List<String> login = this.getAllLogin();
        for (String temp : login) {
            try {
                users.add(this.getUserCS(temp));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
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


    private void disconnectDb(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
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
    }
}
