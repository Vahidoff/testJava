package ru.job4j.models;

public class User {

    private int id;
    private String name;
    private String password;
    private boolean created;

    public boolean getCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * equals.
     * by id User
     * @param o - Object
     * @return boolean result of object comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return this.id == user.id && this.name.equals(user.name)
                && this.password.equals(user.password);
    }

    /**
     * hashCode.
     * override
     * @return id
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        return result;
    }
}