package ru.job4j.testservlet;

import java.sql.Timestamp;

/**
 * UserCS.
 * @author Aleksundrr Vahheedofv (mailto:arbuzz333@hotmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserCS {
    private String name;
    private String login;
    private String email;
    private final Timestamp createDate;

    public UserCS(String name, String login, String email, Timestamp createDate) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.name.hashCode();
        result = result * 17 + this.login.hashCode();
        result = result * 17 + this.email.hashCode();
        result = result * 17 + this.createDate.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        UserCS user = (UserCS) obj;
        return this.name.equals(user.getName())
                && this.login.equals(user.getLogin())
                && this.email.equals(user.getEmail())
                && this.createDate.equals(user.getCreateDate());
    }

    @Override
    public String toString() {
        return String.format("Name: %s Login: %s, Email: %s, Create date: %s",
                this.name, this.login, this.email, this.createDate.toString());
    }
}
