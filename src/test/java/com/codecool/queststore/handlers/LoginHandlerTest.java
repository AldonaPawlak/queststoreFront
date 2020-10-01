package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.HandlerHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.Admin;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.services.ServiceFactory;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginHandlerTest {

    HttpExchange exchange = mock(HttpExchange.class);
    CookieHelper cookieHelper = mock(CookieHelper.class);
    HttpHelper helper = mock(HttpHelper.class);
    ServiceFactory serviceFactory = mock(ServiceFactory.class);
    UserService userService = mock(UserService.class);
    HandlerHelper handlerHelper = mock(HandlerHelper.class);
    Helpers helpers = new Helpers(helper, cookieHelper, handlerHelper);

    @Test
    void loginPageCheck() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("GET");
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        Helpers helpers = new Helpers(helper, cookieHelper, null);
        LoginHandler handler = new LoginHandler(null, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        verify(exchange).getRequestMethod();
    }

    @Test
    void loginPageCheckUserStudent() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(serviceFactory.getUserService()).thenReturn(userService);
        Student student = new Student(1, "Albus", "Dumbledore", Role.STUDENT, true,
                "dumbledore@coolschool.com", "dumbledore", "salt");
        Session session = new Session("session", student.getId());
        student.setSession(session);
        when(userService.login(null, null)).thenReturn(student);
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        assertAll(
                ()-> verify(exchange, times(2)).getResponseHeaders(),
                ()-> assertEquals(headers.get("Location").get(0), "/student")
        );
    }

    @Test
    void loginPageCheckUserAdmin() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(serviceFactory.getUserService()).thenReturn(userService);
        Admin admin = new Admin(1, "Harry", "Potter", Role.ADMIN, true,
                "harry.p@coolschool.com", "harry", "salt");
        Session session = new Session("session", admin.getId());
        admin.setSession(session);
        when(userService.login(null, null)).thenReturn(admin);
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        assertAll(
                ()-> verify(exchange, times(2)).getResponseHeaders(),
                ()-> assertEquals(headers.get("Location").get(0), "/admin")
        );
    }

    @Test
    void loginPageCheckUserMentor() throws IOException {

        //Arrange
        when(exchange.getRequestMethod()).thenReturn("POST");
        Headers headers = new Headers();
        when(exchange.getResponseHeaders()).thenReturn(headers);
        when(serviceFactory.getUserService()).thenReturn(userService);
        Mentor mentor = new Mentor(1, "Severus", "Snape", Role.MENTOR, true,
                "severus@coolschool.com", "severus", "salt");
        Session session = new Session("session", mentor.getId());
        mentor.setSession(session);
        when(userService.login(null, null)).thenReturn(mentor);
        when(cookieHelper.getSessionIdCookie(exchange)).thenReturn(Optional.empty());
        doNothing().when(helper).sendResponse(exchange, "Default response", 200);
        LoginHandler handler = new LoginHandler(serviceFactory, helpers);

        //Act
        handler.handle(exchange);

        //Assert
        assertAll(
                ()-> verify(exchange, times(2)).getResponseHeaders(),
                ()-> assertEquals(headers.get("Location").get(0), "/mentor")
        );
    }

}