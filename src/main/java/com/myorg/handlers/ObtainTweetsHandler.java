package com.myorg.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.myorg.domain.Tweet;
import com.myorg.domain.User;

public class ObtainTweetsHandler {

    public static Map<String, Object> handler(Map<String, Object> event) {
        System.out.println(event.toString());
        Map<String, Object> path_parameters = (Map<String, Object>) event.get("pathParameters");
        String user_id = path_parameters.get("id").toString();

        List<Tweet> tweets = new ArrayList<>();
        User user = new User(user_id, "Pepe", "Pepe", "Bogotá");

        tweets.add(new Tweet("El clima...", user));
        tweets.add(new Tweet("Política...", user));

        Gson gson = new Gson();

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("body", gson.toJson(tweets));
        response.put("headers", new HashMap<>() {
            {
                put("Content-Type", "application/json");
            }
        });
        response.put("isBase64Encoded", false);
        return response;
    }

}
