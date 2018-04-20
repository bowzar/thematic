package com.yulintu.thematic.data.kafka.test.common;

public class ApplicationConsumer {

    public static void main(String[] args) {

        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic, "g0", 0);
        consumerThread.start();
        consumerThread = new KafkaConsumer(KafkaProperties.topic, "g0", 1);
        consumerThread.start();


    }
}
