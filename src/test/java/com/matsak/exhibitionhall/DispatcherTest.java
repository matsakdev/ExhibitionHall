package com.matsak.exhibitionhall;

import com.matsak.exhibitionhall.commands.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatcherTest {
    private static Map<String, HttpServletRequest> correctRequests;
    private static Map<String, HttpServletRequest> wrongRequests;
    private static Map<String, FrontCommand> frontCommands;


    @BeforeAll
    public static void setRequests() {
        correctRequests = getMockCorrectRequests();
//        wrongRequests = getMockWrongRequests();
        frontCommands = getFrontCommands();
    }

    @Test
    public void assertionEquals() throws ServletException, IOException {
        assertEquals(5, 5);
    }

    @Test
    public void dispatchCorrectMainPagePath() {
        assertEquals(frontCommands.get("main").getClass(),
                Dispatcher.dispatch(correctRequests.get("main")).getClass());
    }

    @Test
    public void dispatchCorrectAdminPagePath() {
        assertEquals(
                Dispatcher.dispatch(correctRequests.get("admin")).getClass(),
                frontCommands.get("admin").getClass());
    }

    @Test
    public void dispatchCorrectExhibitionPagePath() {
        assertEquals(
                Dispatcher.dispatch(correctRequests.get("exhibition")).getClass(),
                frontCommands.get("exhibitionDetails").getClass());
    }

    @Test
    public void dispatchCorrectEditPagePath() {
        assertEquals(
                Dispatcher.dispatch(correctRequests.get("edit")).getClass(),
                frontCommands.get("expositions").getClass());
    }

    @Test
    public void dispatchCorrectLoginPagePath() {
        assertEquals(
                Dispatcher.dispatch(correctRequests.get("login")).getClass(),
                frontCommands.get("login").getClass());
    }

    public static Map<String, HttpServletRequest> getMockCorrectRequests() {
        String contextPath = "testmachine/";
        Map<String, HttpServletRequest> mockedRequests = new HashMap<>();
        HttpServletRequest requestMainPage = mock(HttpServletRequest.class);
        when(requestMainPage.getRequestURI()).thenReturn(contextPath + "/main");
        mockedRequests.put("main", requestMainPage);
        final HttpServletRequest requestAdminPage = mock(HttpServletRequest.class);
        when(requestAdminPage.getRequestURI()).thenReturn(contextPath + "/admin");
        mockedRequests.put("admin", requestAdminPage);
        final HttpServletRequest requestUserExhibitionPage = mock(HttpServletRequest.class);
        when(requestUserExhibitionPage.getRequestURI()).thenReturn(contextPath + "/exhibition/1");
        mockedRequests.put("exhibition", requestUserExhibitionPage);
        final HttpServletRequest requestEditExpositionAdminPage = mock(HttpServletRequest.class);
        when(requestEditExpositionAdminPage.getRequestURI()).thenReturn(contextPath + "/admin/expositions/2");
        mockedRequests.put("edit", requestEditExpositionAdminPage);
        final HttpServletRequest requestLoginPage = mock(HttpServletRequest.class);
        when(requestLoginPage.getRequestURI()).thenReturn(contextPath + "/login");
        mockedRequests.put("login", requestLoginPage);
        for (HttpServletRequest req : mockedRequests.values()) {
            when(req.getContextPath()).thenReturn(contextPath);
        }
        return mockedRequests;
    }

//    private Map<String, HttpServletRequest> getMockWrongRequests() {
//    }

    private static Map<String, FrontCommand> getFrontCommands() {
        return Dispatcher.getCommands();
    }
}
