package serializer;

import bean.Company;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * @author lawliet
 * @version 1.0.0
 * @description
 * @createTime 2021.09.17
 */
public class CompanyDeserializer implements Deserializer<Company> {

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public Company deserialize(String topic, byte[] data) {
        return null;
    }

    public void close() {

    }
}
