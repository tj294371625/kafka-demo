package client;

import bean.Company;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import serializer.CompanyDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author lawliet
 * @version 1.0.0
 * @description
 * @createTime 2021.09.15
 */
public class Consumer {
    public static final String brokerList = "http://39.107.103.43:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = initConfig();

        KafkaConsumer<String, Company> consumer = new KafkaConsumer<>(properties);

        /*List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        List<TopicPartition> topicPartitions = new ArrayList<>();
        for (PartitionInfo partitionInfo : partitionInfos) {
            topicPartitions.add(
                    new TopicPartition(partitionInfo.topic(), partitionInfo.partition())
            );
        }

        consumer.assign(topicPartitions);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
        }*/


        consumer.subscribe(Collections.singleton(topic));

        while (true) {
            ConsumerRecords<String, Company> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, Company> record : records) {
                System.out.println(record.value());
            }
        }

    }

    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CompanyDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return properties;
    }
}
