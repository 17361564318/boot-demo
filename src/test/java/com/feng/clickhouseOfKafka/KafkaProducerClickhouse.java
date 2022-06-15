package com.feng.clickhouseOfKafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 此测试类主要做kafka数据被clickhouse消费并且存储而建
 */
public class KafkaProducerClickhouse {

    @Test
    public void sendMsg() throws Exception {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.40.129:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        final String topic = "clickhouse";
        for (int i = 0; i < 10; i++) {
            MsgJsonSource source = new MsgJsonSource();
            source.setBiz("biz_" + i);
            source.setMsgId(ThreadLocalRandom.current().nextLong(100000000L));
            source.setSenderId("sender_id_" + i);
            source.setStatus("ok");
            Map<String, Object> contents = new HashMap<>(8);
            contents.put("hello", "world");
            contents.put("你好", "世界");
            source.setContents(contents);
            source.setStatusTime(System.currentTimeMillis());
            JSON.toJSONString(source);
            producer.send(new ProducerRecord<>(topic, JSON.toJSONString(source)), (resp, e) -> {
                if (e == null) {
                    long offset = resp.offset();
                    int partition = resp.partition();
                    System.out.printf("发送成功，偏移量%s, 分区%s%n", offset, partition);
                } else {
                    System.out.println(e.getMessage());
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
        producer.close();
    }


}

class MsgJsonSource {
    private String biz;

    @JSONField(name = "sender_id")
    private String senderId;

    @JSONField(name = "msg_id")
    private Long msgId;

    private String status;

    private Long statusTime;

    private Map<String, Object> contents;

    public Long getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Long statusTime) {
        this.statusTime = statusTime;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getContents() {
        return contents;
    }

    public void setContents(Map<String, Object> contents) {
        this.contents = contents;
    }
}
