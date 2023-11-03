package com.myorg.handlers;

import java.util.Map;

public class AddFollowerHandler {
    public static void handler(Map<String, Object> event) {
        System.err.println(event.toString());
    }
}
