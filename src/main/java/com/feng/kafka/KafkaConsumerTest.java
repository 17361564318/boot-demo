package com.feng.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author fhn
 * @create 2021/8/28
 * @software idea
 */
public class KafkaConsumerTest {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerTest.class);

    public static void main(String[] args) {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", "192.168.40.129:9092");

        kafkaProperties.put("group.id", "test");
        // 自动提交offset
        kafkaProperties.put("enable.auto.commit", "true");
        // 自动提交offset的时间间隔
        kafkaProperties.put("auto.commit.interval.ms", "1000");
        kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);
        // 消费者订阅的主题
        consumer.subscribe(Collections.singletonList("topic1"));

        while (true) {
            // kafka一次拉取一批的数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();
                long offset = record.offset();
                String value = record.value();
                int partition = record.partition();
                logger.info("消费者拿到的数据 topic = {}, offset = {}, value = {}, 分区 = {}", topic, offset, value, partition);
            }
        }
    }

}
