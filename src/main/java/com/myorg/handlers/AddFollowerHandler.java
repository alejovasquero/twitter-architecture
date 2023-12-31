package com.myorg.handlers;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import com.myorg.domain.User;

public class AddFollowerHandler {
    public static Map<String, Object> handler(APIGatewayProxyRequestEvent input, Context context) {
        System.out.println(input.toString());
        Gson gson = new Gson();

        Map<String, String> path_parameters = input.getPathParameters();
        String body = input.getBody();
        User user = gson.fromJson(body, User.class);

        String user_id = path_parameters.get("id").toString();

        User root_user = new User(user_id, "Pepe", "Pepe", "Bogotá");
        root_user.addFollower(user);

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("body", gson.toJson(root_user));
        response.put("headers", new HashMap<>() {
            {
                put("Content-Type", "application/json");
            }
        });
        response.put("isBase64Encoded", false);
        return response;
    }
}
