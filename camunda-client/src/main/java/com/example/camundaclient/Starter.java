package com.example.camundaclient;

import org.camunda.bpm.client.ExternalTaskClient;

import java.util.HashMap;
import java.util.Map;

public class Starter {
    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(1000)
                .build();
    }
}
