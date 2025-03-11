package site.ani4h.share.common;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UidSerializer extends JsonSerializer<Uid> {
    @Override
    public void serialize(Uid value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}