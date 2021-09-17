package serializer;

import bean.Company;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.util.Objects;

/**
 * @author lawliet
 * @version 1.0.0
 * @description
 * @createTime 2021.09.17
 */
public class CompanySerializer implements Serializer<Company> {


    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public byte[] serialize(String topic, Company data) {
        if (Objects.isNull(data)) {
            return null;
        }
        return new byte[0];
    }

    public void close() {

    }
}
