package site.ani4h.share.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UnmaskDataDeserializer extends StdDeserializer<Integer> {
    public UnmaskDataDeserializer() {
        super(Integer.class);
    }

    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String data  = jsonParser.getValueAsString();
        return DataMasker.unmask(data);
    }

}
