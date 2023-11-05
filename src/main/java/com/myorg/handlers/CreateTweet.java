package com.myorg.handlers;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import com.myorg.domain.Tweet;
import com.myorg.domain.User;

public class CreateTweet {

    public static Map<String, Object> handler(APIGatewayProxyRequestEvent input, Context context) {
        System.out.println(input.toString());
        Gson gson = new Gson();

        String body = input.getBody();
        Tweet tweet = gson.fromJson(body, Tweet.class);

        User root_user = new User("3", "Pepe", "Pepe", "Bogotá");
        tweet.setUser(root_user);


        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("body", gson.toJson(tweet));
        response.put("headers", new HashMap<>() {
            {
                put("Content-Type", "application/json");
            }
        });
        response.put("isBase64Encoded", false);
        return response;
    }

}
