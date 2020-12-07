package com.example.camundaclient;

public class Starter {
    public static void main(String[] args) {
        StubServiceWorker mockServiceWorker = new StubServiceWorker();
        mockServiceWorker.work();
    }
}
