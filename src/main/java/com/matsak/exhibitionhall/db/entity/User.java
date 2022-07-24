package com.matsak.exhibitionhall.db.entity;

import java.util.Objects;

public class User {
    private String userLogin;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        if (!userRole.equals("administrator") && !userRole.equals("user"))
            throw new IllegalArgumentException(userRole + " role is not allowed in the system (allowed: <administrator>, <user>");
        this.userRole = userRole;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String user_login) {
        this.userLogin = user_login;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String user_password) {
        this.userPassword = user_password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.contains("@")) throw new IllegalArgumentException("Email must contains the '@' symbol");
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_login='" + userLogin + '\'' +
                ", user_password='" + userPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userLogin, user.userLogin) && Objects.equals(userPassword, user.userPassword) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(userRole, user.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLogin, userPassword, firstName, lastName, email, phone, userRole);
    }
}
