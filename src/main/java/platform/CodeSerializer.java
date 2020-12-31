package platform;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CodeSerializer extends StdSerializer<Code> {

    public CodeSerializer() { this(null); }

    public CodeSerializer(Class<Code> t) { super(t); }

    @Override
    public void serialize(Code code, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("code", code.getCode());
        gen.writeStringField("date", code.getFormattedDate());
        gen.writeEndObject();
    }
}
