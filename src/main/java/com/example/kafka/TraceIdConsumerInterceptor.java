package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.Configurable;
import org.apache.kafka.common.header.Headers;
import org.slf4j.MDC;

import java.util.Map;

public class TraceIdConsumerInterceptor<K, V> implements ConsumerInterceptor<K, V>, Configurable {

    private static final String TRACE_ID_HEADER = "traceId";

    @Override
    public ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> records) {
        records.forEach(record -> {
            Headers headers = record.headers();
            if (headers.lastHeader(TRACE_ID_HEADER) != null) {
                String traceId = new String(headers.lastHeader(TRACE_ID_HEADER).value());
                MDC.put(TRACE_ID_HEADER, traceId);
            }
        });
        return records;
    }

    @Override
    public void onCommit(Map offsets) {
        // No action needed on commit
    }

    @Override
    public void close() {
        // No action needed on close
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // No configuration needed
    }
}
