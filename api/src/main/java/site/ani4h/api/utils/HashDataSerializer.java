package site.ani4h.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HashDataSerializer extends StdSerializer<Integer> {

    public HashDataSerializer() {
        super(Integer.class);
    }

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String hashedValue = DataMasker.mask(value);
        gen.writeString(hashedValue);
    }


}