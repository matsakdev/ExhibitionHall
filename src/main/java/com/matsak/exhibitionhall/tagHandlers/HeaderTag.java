package com.matsak.exhibitionhall.tagHandlers;


import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class HeaderTag extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
                "    <div class=\"container-fluid\">\n" +
                "        <a class=\"navbar-brand\" href=\"#\">Navbar</a>\n" +
                "        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\"\n" +
                "                data-bs-target=\"#navbarSupportedContent\"\n" +
                "                aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                "            <span class=\"navbar-toggler-icon\"></span>\n" +
                "        </button>\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" +
                "            <ul class=\"navbar-nav me-auto mb-2 mb-lg-0 navButtons\">\n" +
                "                <li class=\"nav-item\">\n" +
                "                    <a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Home</a>\n" +
                "                </li>\n" +
                "                <li class=\"nav-item\">\n" +
                "                    <a class=\"nav-link\" href=\"#\">Link</a>\n" +
                "                </li>\n" +
                "                <li class=\"nav-item dropdown\">\n" +
                "                    <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\"\n" +
                "                       data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                "                        Dropdown\n" +
                "                    </a>\n" +
                "                    <ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
                "                        <li><a class=\"dropdown-item\" href=\"#\">Action</a></li>\n" +
                "                        <li><a class=\"dropdown-item\" href=\"#\">Another action</a></li>\n" +
                "                        <li>\n" +
                "                            <hr class=\"dropdown-divider\">\n" +
                "                        </li>\n" +
                "                        <li><a class=\"dropdown-item\" href=\"#\">Something else here</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li class=\"nav-item\">\n" +
                "                    <a class=\"nav-link disabled\" href=\"#\" tabindex=\"-1\" aria-disabled=\"true\">Disabled</a>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "\n" +
                "            <div class=\"languageRadioButton\">\n" +
                "                <div class=\"btn-group\" role=\"group\" aria-label=\"Basic radio toggle button group\">\n" +
                "                    <input type=\"radio\" class=\"btn-check\" name=\"btnradio\" id=\"btnradio1\" autocomplete=\"off\" checked>\n" +
                "                    <label class=\"btn btn-outline-primary\" for=\"btnradio1\">UKR</label>\n" +
                "\n" +
                "                    <input type=\"radio\" class=\"btn-check\" name=\"btnradio\" id=\"btnradio3\" autocomplete=\"off\">\n" +
                "                    <label class=\"btn btn-outline-primary\" for=\"btnradio3\">ENG</label>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <button type=\"button\" class=\"btn btn-outline-success me-2 loginButton\" data-bs-toggle=\"modal\" data-bs-target=\"#loginModal\">\n" +
                "                Login/Register\n" +
                "                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\"\n" +
                "                     class=\"bi bi-door-open\" viewBox=\"0 0 16 16\">\n" +
                "                    <path d=\"M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z\"/>\n" +
                "                    <path d=\"M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z\"/>\n" +
                "                </svg>\n" +
                "            </button>\n" +
                "\n" +
                "            <div class=\"modal fade\" id=\"loginModal\" tabindex=\"-1\" aria-labelledby=\"ModalLabel\" aria-hidden=\"true\">\n" +
                "                <div class=\"modal-dialog modal-dialog-centered\">\n" +
                "                    <div class=\"modal-content\">\n" +
                "                        <div class=\"modal-header\">\n" +
                "                            <h5 class=\"modal-title\" id=\"exampleModalLabel\">Modal title</h5>\n" +
                "                            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n" +
                "                        </div>\n" +
                "                        <div class=\"modal-body\">\n" +
                "                            <div>\n" +
                "                                <div class=\"col-md-10\">\n" +
                "                                    <label for=\"validationCustomUsername\" class=\"form-label\">Login</label>\n" +
                "                                    <div class=\"input-group\">\n" +
                "                                        <input type=\"text\" placeholder=\"Login | Email\" class=\"form-control\" id=\"validationCustomUsername\" aria-describedby=\"inputGroupPrepend\">\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                                <div class=\"col-md-10\">\n" +
                "                                    <label for=\"validationCustomUsername\" class=\"form-label\">Password</label>\n" +
                "                                    <div class=\"input-group\">\n" +
                "                                        <input type=\"text\" placeholder=\"Password\" class=\"form-control\" id=\"j\" aria-describedby=\"inputGroupPrepend\">\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                                <hr>\n" +
                "                            </div>\n" +
                "\n" +
                "                            <h5>OR</h5>\n" +
                "                            <form action=\"${pageContext.request.requestURL}registration\">\n" +
                "                                <button class=\"btn btn-outline-success me-2\" type=\"submit\" data-toggle=\"modal\"\n" +
                "                                        data-target=\"#loginModalForm\">Register\n" +
                "                                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\"\n" +
                "                                         class=\"bi bi-door-open\" viewBox=\"0 0 16 16\">\n" +
                "                                        <path d=\"M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z\"/>\n" +
                "                                        <path d=\"M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z\"/>\n" +
                "                                    </svg>\n" +
                "                                </button>\n" +
                "                            </form>\n" +
                "                        </div>\n" +
                "                        <div class=\"modal-footer\">\n" +
                "                            <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n" +
                "                            <button type=\"button\" class=\"btn btn-primary\">Save changes</button>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</nav>");
    }
}
