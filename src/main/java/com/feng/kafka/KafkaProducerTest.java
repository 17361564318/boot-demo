package com.feng.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author fhn
 * @create 2021/8/28
 * @software idea
 */
public class KafkaProducerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerTest.class);

    public static void main(String[] args) throws Exception {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", "192.168.40.129:9092");
        kafkaProperties.put("acks", "1");
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProperties);
        for (int i = 1; i <= 5; i++) {
            // 在生成生产者记录的时候不指定key的话，就会存放在不同的分区中，一旦指定了key，那儿就会存放在 key.hashcode() % 分区数量的分区位置
            kafkaProducer.send(new ProducerRecord<>("topic1", "value" + i), (recordMetadata, e) -> {
                // 发送成功
                if (e == null) {
                    // 偏移量
                    long offset = recordMetadata.offset();
                    // 分区
                    int partition = recordMetadata.partition();
                    // 主题
                    String topic = recordMetadata.topic();
                    LOGGER.info("生产者 主题 = {} , 偏移量= {} , 分区 = {}", topic, offset, partition);
                } else {
                    LOGGER.error("出错了，错误信息：{} ", e.getMessage());
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
        kafkaProducer.close();
    }

}
