package com.yulintu.thematic.data.kafka.test.common;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducer extends Thread {
    private final Producer<String, String> producer;
    private final String topic;
    private final Properties props = new Properties();

    public KafkaProducer(String topic) {

        props.put("bootstrap.servers", "192.168.20.10:9092,192.168.20.10:9093,192.168.20.10:9094");//服务器ip:端口号，集群用逗号分隔
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void run() {
        int messageNo = 1;
        while (true) {

            String messageStr = "Message_" + messageNo;
            System.out.println("Send:" + messageStr);
            producer.send(new ProducerRecord<>(topic, ((Object) messageNo).toString(), messageStr));
            messageNo++;
            messageStr = "Message_" + messageNo;
            System.out.println("Send:" + messageStr);
            producer.send(new ProducerRecord<>(topic, ((Object) messageNo).toString(), messageStr));
            messageNo++;
            messageStr = "Message_" + messageNo;
            System.out.println("Send:" + messageStr);
            producer.send(new ProducerRecord<>(topic, ((Object) messageNo).toString(), messageStr));
            messageNo++;
            messageStr = "Message_" + messageNo;
            System.out.println("Send:" + messageStr);
            producer.send(new ProducerRecord<>(topic, ((Object) messageNo).toString(), messageStr));
            messageNo++;
            messageStr = "Message_" + messageNo;
            System.out.println("Send:" + messageStr);
            producer.send(new ProducerRecord<>(topic, ((Object) messageNo).toString(), messageStr));
            messageNo++;
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}