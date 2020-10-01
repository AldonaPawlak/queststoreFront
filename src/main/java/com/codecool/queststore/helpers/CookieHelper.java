package com.codecool.queststore.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookieHelper {

    public String getSessionIdFromCookie(HttpCookie cookie) {
        String value = cookie.getValue().replace("\"", "");
        return value;
    }

    public List<HttpCookie> parseCookies(String cookieString){
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieString == null || cookieString.isEmpty()){ // what happens if cookieString = null?
            return cookies;
        }
        for(String cookie : cookieString.split(";")){
            int indexOfEq = cookie.indexOf('=');
             String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1);
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    public Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies){
        for(HttpCookie cookie : cookies){
            if(cookie.getName().equals(name))
                return Optional.ofNullable(cookie);
        }
        return Optional.empty();
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        return findCookieByName(HttpHelper.SESSION_COOKIE_NAME, cookies);
    }

    public String findSessionFromCookie(List<HttpCookie> cookies) {
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals(HttpHelper.SESSION_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}

