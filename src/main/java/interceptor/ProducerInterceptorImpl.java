package interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author lawliet
 * @version 1.0.0
 * @description
 * @createTime 2021.09.16
 */
public class ProducerInterceptorImpl implements ProducerInterceptor {

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        System.out.println(record.toString());
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        // zs: 这个方法会占用Producer的IO，所以逻辑不要太重，否则会影响效率
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
