package com.yulintu.thematic.data.kafka.test.common;

public class ApplicationProducer {

    public static void main(String[] args) {

        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
        producerThread.start();
    }
}
