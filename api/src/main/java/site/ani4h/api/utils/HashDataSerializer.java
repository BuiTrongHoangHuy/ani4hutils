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
        String hashedValue = applyCustomHash(value);
        gen.writeString(hashedValue);
    }

    private String applyCustomHash(Integer input) {
        if (1 == (input)) {
            // TODO: implement mask algorithm here
            return "asdasd";
        }
        return input.toString();
    }
}