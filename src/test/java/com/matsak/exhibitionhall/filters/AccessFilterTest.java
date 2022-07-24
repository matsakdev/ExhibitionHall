package com.matsak.exhibitionhall.filters;

import com.matsak.exhibitionhall.db.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccessFilterTest {
    private static Map<String, User> users;
    private static Map<String, List<Object>> data;

    @BeforeAll
    public static void initialize() {
        users = getUsersMap();
        data = getRequestsData();
        ResourceBundle logs = ResourceBundle.getBundle("logpath");

        System.setProperty("log4jPath", logs.getString("path"));
    }




    @Test
    public void adminRoleFilterTest() throws ServletException, IOException {
        AccessFilter instance = new AccessFilter();
        HttpServletRequest request = (HttpServletRequest) data.get("admin").get(0);
        when(request.getRequestURI()).thenReturn("/admin");
        when(request.getContextPath()).thenReturn("testmachine");
        HttpServletResponse response = (HttpServletResponse) data.get("admin").get(1);
        FilterChain chain = (FilterChain) data.get("admin").get(2);
        instance.doFilter(request,
                response,
                chain);
        assertEquals(request.getRequestURI(), "/admin");

    }

    @Test
    public void userRoleFilterTest() throws ServletException, IOException {
        AccessFilter instance = new AccessFilter();
        HttpServletRequest request = (HttpServletRequest) data.get("user").get(0);
        when(request.getRequestURI()).thenReturn("testmachine/admin");
        when(request.getContextPath()).thenReturn("testmachine");
        HttpServletResponse response = (HttpServletResponse) data.get("user").get(1);
        FilterChain chain = (FilterChain) data.get("user").get(2);
        FilterConfig cfg = mock(FilterConfig.class);

//        doCallRealMethod().when(response.sendRedirect(request.getContextPath() + "/admin"));
//        doAnswer(invocation -> {
//            when(request.getRequestURI()).thenReturn("testmachine/error");
//            return null;
//        }).when(response).sendRedirect(request.getContextPath() + "/error");
//        doNothing().when(response).sendRedirect(request.getContextPath() + "/error");
//        verify(request, times(1)).getRequestURI();
        instance.init(cfg);
        instance.doFilter(request,
                response,
                chain);
        assertEquals(request.getRequestURI(), request.getContextPath() + "/error");

    }


    private static Map<String, User> getUsersMap() {
        Map<String, User> users = new HashMap<>();
        User client = new User();
        client.setUserRole("user");
        users.put("client", client);
        User admin = new User();
        admin.setUserRole("administrator");
        users.put("administrator", admin);
        User nullUser = new User();
        users.put("nullUser", nullUser);
        return users;
    }

    private static Map<String, List<Object>> getRequestsData() {
        Map<String, List<Object>> requests = new HashMap<>();
        //user
        HttpServletRequest userRequest = mock(HttpServletRequest.class);
        HttpSession userSession = mock(HttpSession.class);
        HttpServletResponse userResponse = mock(HttpServletResponse.class);
        FilterChain userChain = mock(FilterChain.class);
        when(userRequest.getSession()).thenReturn(userSession);
        when(userSession.getAttribute("currentRole")).thenReturn("user");
        List<Object> userReqParams = new ArrayList<>();
        userReqParams.add(userRequest);
        userReqParams.add(userResponse);
        userReqParams.add(userChain);
        requests.put("user", userReqParams);

        //admin
        HttpServletRequest adminRequest = mock(HttpServletRequest.class);
        HttpSession adminSession = mock(HttpSession.class);
        HttpServletResponse adminResponse = mock(HttpServletResponse.class);
        FilterChain adminChain = mock(FilterChain.class);
        when(adminRequest.getSession()).thenReturn(userSession);
        when(adminSession.getAttribute("currentRole")).thenReturn("administrator");
        List<Object> adminReqParams = new ArrayList<>();
        adminReqParams.add(adminRequest);
        adminReqParams.add(adminResponse);
        adminReqParams.add(adminChain);
        requests.put("admin", adminReqParams);

        //nullUser
        HttpServletRequest clearRequest = mock(HttpServletRequest.class);
        HttpSession clearSession = mock(HttpSession.class);
        HttpServletResponse clearResponse = mock(HttpServletResponse.class);
        FilterChain clearChain = mock(FilterChain.class);
        when(adminRequest.getSession()).thenReturn(clearSession);
        when(adminSession.getAttribute("currentRole")).thenReturn(null);
        List<Object> clearReqParams = new ArrayList<>();
        clearReqParams.add(clearRequest);
        clearReqParams.add(clearResponse);
        clearReqParams.add(clearChain);
        requests.put("admin", clearReqParams);

        return requests;
    }
}
