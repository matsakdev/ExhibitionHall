package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RegistrationCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(RegistrationCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        if (request.getRequestURI().substring(request.getContextPath().length()).equals("/register/check")) {
            processRegistration();
            return;
        }
        forward("registration.jsp");
    }

    private void processRegistration() {
        String login = request.getParameter("userLogin");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("userPassword");
        String repeatedPassword = request.getParameter("repeatedPassword");
        try {
            validateFields(login, email, phone, firstName,
                    lastName, password, repeatedPassword);
        } catch (IOException e) {
            logger.error("Cannot validate and create user");
        }
    }

    private void validateFields(String login, String email, String phone, String firstName,
                                String lastName, String password, String repeatedPassword) throws IOException {
        HttpSession session = request.getSession();
        boolean validRegistration = true;
        if (login == null || login.length() < 4 || login.length() > 20) {
            session.setAttribute("loginFieldError", true);
            validRegistration = false;
        }
        if (email == null || email.length() < 5 || !email.contains("@")) {
            session.setAttribute("emailFieldError", true);
            validRegistration = false;
        }
        if (firstName == null || firstName.length() < 2) {
            session.setAttribute("firstNameFieldError", true);
            validRegistration = false;
        }
        if (lastName == null || lastName.length() < 2) {
            session.setAttribute("lastNameFieldError", true);
            validRegistration = false;
        }
        if (password == null || password.length() < 6 || !password.equals(repeatedPassword)) {
            session.setAttribute("passwordFieldError", true);
            validRegistration = false;
        }
        if (phone == null || phone.length() != 12 || !phone.matches("(\\b380[0-9]{9}\\b)")) {
            session.setAttribute("phoneFieldError", true);
            validRegistration = false;
        }
        if (!validRegistration || !databaseValidation(login, email, phone)) {
            try {
                setFieldValues(session, login, email, firstName, lastName, phone);
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            } catch (IOException e) {
             logger.error("Cannot send redirect to /register page");
            }
        }
        else try {
            if (DAOFactory.getInstance().getUserDAO().create(login, password, firstName, lastName, email, phone)) {
                response.sendRedirect("/registration-successful");
            }
        } catch (Exception ex) {
            logger.error("Strange behaviour. Cannot create validated user.");
            response.sendRedirect("/error");
            return;
        }

    }

    private void setFieldValues(HttpSession session, String login, String email, String firstName, String lastName, String phone) {
        session.setAttribute("loginFieldValue", login);
        session.setAttribute("emailFieldValue", email);
        session.setAttribute("firstNameFieldValue", firstName);
        session.setAttribute("lastNameFieldValue", lastName);
        session.setAttribute("phoneFieldValue", phone);
    }

    private boolean databaseValidation(String login, String email, String phone) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        HttpSession session = request.getSession();
        boolean isDBValidated = true;
        if (userDAO.getByLogin(login) != null) {
            session.setAttribute("loginFieldDBError", true);
            session.setAttribute("loginFieldValue", login);
            isDBValidated = false;
        }
        if (userDAO.getByEmail(email)) {
            session.setAttribute("emailFieldDBError", true);
            session.setAttribute("emailFieldValue", email);
            isDBValidated = false;
        }
        if (userDAO.getByPhoneNumber(phone)) {
            session.setAttribute("phoneFieldDBError", true);
            session.setAttribute("phoneFieldValue", phone);
            isDBValidated = false;
        }
        return isDBValidated;
    }
}
