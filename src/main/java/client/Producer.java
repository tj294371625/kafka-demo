package client;

import bean.Company;
import interceptor.ProducerInterceptorImpl;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import serializer.CompanySerializer;

import java.util.Objects;
import java.util.Properties;

/**
 * @author lawliet
 * @version 1.0.0
 * @description
 * @createTime 2021.09.15
 */
public class Producer {
    public static final String brokerList = "http://39.107.103.43:9092";
    public static final String topic = "topic-demo";

    public static void main(String[] args) {
        Properties properties = initConfig();

        // 同步方式
        /*try (
                KafkaProducer<String, String> producer = new KafkaProducer<>(properties)
        ) {
            ProducerRecord<String, String> msg = new ProducerRecord<>(topic, "凡人修仙传");
            RecordMetadata recordMetadata = producer.send(msg).get();
            System.out.printf("topic: %s, partition: %s, offset: %s, timestamp: %s",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp());
        } catch (Exception e) {
            // 记录发送失败的消息
        }*/


        // 异步方式
        try (
                KafkaProducer<String, Company> producer = new KafkaProducer<>(properties)
        ) {

            // zs：回调函数可以保证是有序的
            Company company = new Company();
            company.setName("凡人修仙传传媒公司");
            company.setAddress("北京海淀区中关村");
            ProducerRecord<String, Company> msg = new ProducerRecord<>(topic, 1, null, company);
            producer.send(
                    msg,
                    (metadata, exception) -> {
                        if (Objects.nonNull(exception)) {
                            exception.printStackTrace();
                            // 记录发送失败消息
                        } else {
                            System.out.printf("topic: %s, partition: %s, offset: %s, timestamp: %s",
                                    metadata.topic(),
                                    metadata.partition(),
                                    metadata.offset(),
                                    metadata.timestamp());
                        }
                    }
            );

        }

    }

    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        // 重试3次，重试期间不抛出异常，但如果三次过后，消息仍未发送成功，则抛出异常，需要我们在代码中处理
        // zs：这是一个联动参数，谨慎使用
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 重试间隔时间
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 500);
        // 配置拦截器，多个拦截器的全限定类名使用逗号分隔
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorImpl.class.getName());
        // 消息确认机制
        // zs：这是一个联动参数，谨慎使用
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        // 请求最大size
        // zs：这是一个联动参数，谨慎使用
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1048576);
        // 请求超时时间
        // zs：这是一个联动参数，谨慎使用
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 60000);
        return properties;
    }
}
