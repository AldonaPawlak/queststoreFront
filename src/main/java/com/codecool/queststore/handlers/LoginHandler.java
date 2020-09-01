package com.codecool.queststore.handlers;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.models.Credentials;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    private HttpExchange exchange;
    private String response;
    private PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    private UserService userService = new UserService(new UserPostgreSQLDAO(postgreSQLJDBC), new SessionPostgreSQLDAO(postgreSQLJDBC));

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.response = "";
        String method = this.exchange.getRequestMethod();

        if(method.equals("GET")) {
            sendLoginPage();
        }
        if (method.equals("POST")) {
            tryUserLogin(exchange);
        }
     }

    private void tryUserLogin(HttpExchange exchange) throws IOException {
        String formData = transformBodyToString();
        Map<String, String> inputs = parseFormData(formData);
        Credentials credentials = getLoginData(inputs);
        User user = userService.login(credentials);
        if (user.getSession() != null && user.getSession().getUuid() != null) {
            HttpCookie httpCookie = new HttpCookie("SessionID", user.getSession().getUuid());
            exchange.getResponseHeaders().add("Set-Cookie", httpCookie.toString());
            String redirectURL = "";
            switch (user.getRole()) {
                case STUDENT -> redirectURL = "/student";
                case MENTOR -> redirectURL = "/mentor";
                case ADMIN -> redirectURL = "/admin";
            }
            exchange.getResponseHeaders().add("Location", redirectURL);
            sendResponse(301);
        } else {
            sendLoginPage();
        }
    }

    private void sendLoginPage() throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login-page.twig");
        response = template.render(new JtwigModel());
        sendResponse(200);
    }

    private void sendResponse(int rCode) throws IOException {
        exchange.sendResponseHeaders(rCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String transformBodyToString() throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private Credentials getLoginData(Map<String, String> inputs) {
        String email = inputs.get("email");
        String password = inputs.get("password");
        return new Credentials(email, password);
    }
}
