package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.services.RewardService;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;

public class RewardHandler implements HttpHandler {

    private final ServiceFactory serviceFactory;
    private final Helpers helpers;

    public RewardHandler(ServiceFactory serviceFactory, Helpers helpers) {
        this.serviceFactory = serviceFactory;
        this.helpers = helpers;
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);

        if (method.equals("GET")) {
            try {
                initializeGet(httpExchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (method.equals("POST")) {
            initializePost(httpExchange);
        } else if (requestURI.contains("logout")){
            redirectToLogin(httpExchange);
        }

    }

    private void redirectToLogin(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", "/rewards");
        httpExchange.sendResponseHeaders(302, 0);
    }


    private void initializePost(HttpExchange httpExchange) {
    }


    private void initializeGet(HttpExchange httpExchange) throws Exception {
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/rewards_mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Reward> rewards = serviceFactory.getRewardService().getRewards();
        model.with("rewards", rewards);
        response = template.render(model);
        helpers.getHttpHelper().sendResponse(httpExchange, response, HttpHelper.OK);
    }
}
