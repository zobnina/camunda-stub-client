package com.example.camundaclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.client.ExternalTaskClient;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class StubServiceWorker {
    private static final Logger LOG = LogManager.getLogger(StubServiceWorker.class);
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String PROPERTIES_PATH = USER_DIR + "/camunda-client/src/main/resources/work.properties";

    private ExternalTaskClient externalTaskClient;
    private final Random random = new Random();

    public StubServiceWorker() {
        File file = new File(PROPERTIES_PATH);
        try (FileReader reader = new FileReader(file)) {
            Properties properties = new Properties();
            properties.load(reader);
            String baseUrl = properties.getProperty("camunda.base-url");
            int timeout = Integer.parseInt(properties.getProperty("camunda.timeout"));
            externalTaskClient = ExternalTaskClient.create()
                    .baseUrl(baseUrl)
                    .asyncResponseTimeout(timeout)
                    .build();
            LOG.debug("ExternalTaskClient created");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void work() {
        getAppointmentSubscribe();
        checkClientSubscribe();
        createClientSubscribe();
        countSaleSubscribe();
    }

    private void countSaleSubscribe() {
        externalTaskClient.subscribe("count-sale").handler((externalTask, externalTaskService) -> {
            Map<String, Object> variables = new HashMap<>();
            variables.put("sale", 15);
            externalTaskService.complete(externalTask, variables);
            String message = String.format("The External Task %s has been completed!", externalTask.getActivityId());
            LOG.debug(message);
        }).open();
    }

    private void createClientSubscribe() {
        externalTaskClient.subscribe("create-client").handler((externalTask, externalTaskService) -> {
            Map<String, Object> variables = new HashMap<>();
            variables.put("clientId", random.nextInt());
            externalTaskService.complete(externalTask, variables);
            String message = String.format("The External Task %s has been completed!", externalTask.getActivityId());
            LOG.debug(message);
        }).open();
    }

    private void checkClientSubscribe() {
        externalTaskClient.subscribe("check-client").handler((externalTask, externalTaskService) -> {
            Map<String, Object> variables = new HashMap<>();
            variables.put("isClient", random.nextBoolean());
            externalTaskService.complete(externalTask, variables);
            String message = String.format("The External Task %s has been completed!", externalTask.getActivityId());
            LOG.debug(message);
        }).open();
    }

    private void getAppointmentSubscribe() {
        externalTaskClient.subscribe("get-appointment").handler((externalTask, externalTaskService) -> {
            Map<String, Object> variables = new HashMap<>();
            variables.put("ok", random.nextBoolean());
            externalTaskService.complete(externalTask, variables);
            String message = String.format("The External Task %s has been completed!", externalTask.getActivityId());
            LOG.debug(message);
        }).open();
    }
}
