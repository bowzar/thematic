package com.yulintu.thematic.data.kafka.test.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumer extends Thread {
    private final Consumer<String, String> consumer;
    private final String topic;
    private final String group;
    private final int id;

    public KafkaConsumer(String topic, String group, int id) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.20.10:9092,192.168.20.10:9093,192.168.20.10:9094");//服务器ip:端口号，集群用逗号分隔
        props.put("group.id", group);
        props.put("enable.auto.commit", "false");
        props.put("max.poll.records", 10);
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        this.topic = topic;
        this.group = group;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
//                    JSONObject jsonAlarmMsg = JSON.parseObject(record.value());
//                    IpranAlarm alarmMsg = JSONObject.toJavaObject(jsonAlarmMsg, IpranAlarm.class);

                    System.out.println(group + ", " + id + " 从kafka接收到的消息是：" + record.key() + " # " + record.value());
                }
            }

            consumer.commitSync();
        }
    }
}