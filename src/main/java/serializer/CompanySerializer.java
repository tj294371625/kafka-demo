package serializer;

import bean.Company;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
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

    @SuppressWarnings("unchecked")
    public byte[] serialize(String topic, Company data) {
        if (Objects.isNull(data)) {
            return null;
        }

        Schema schema = RuntimeSchema.getSchema(data.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        return ProtostuffIOUtil.toByteArray(data, schema, buffer);
    }

    public void close() {

    }
}
