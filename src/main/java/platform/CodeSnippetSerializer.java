package platform;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CodeSnippetSerializer extends StdSerializer<CodeSnippet> {

    public CodeSnippetSerializer() { this(null); }

    public CodeSnippetSerializer(Class<CodeSnippet> t) { super(t); }

    @Override
    public void serialize(CodeSnippet codeSnippet, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("code", codeSnippet.getCode());
        gen.writeStringField("date", codeSnippet.getFormattedDate());
        gen.writeNumberField("time", Long.max(codeSnippet.getRemainingSeconds(), 0));
        gen.writeNumberField("views", codeSnippet.getViews());
        gen.writeEndObject();
    }
}
