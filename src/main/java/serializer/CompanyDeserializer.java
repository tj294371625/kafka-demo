package serializer;

import bean.Company;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
import java.util.Objects;

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
        if (Objects.isNull(data)) {
            return null;
        }

        Schema<Company> schema = RuntimeSchema.getSchema(Company.class);
        Company ans = new Company();
        ProtostuffIOUtil.mergeFrom(data, ans, schema);
        return ans;
    }

    public void close() {

    }
}
