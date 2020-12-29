package platform;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HtmlFormStorage {

    public static String getCodeForm(CodeRepository codeRepository) {
        return "<html>" +
                    "<head>" +
                        "<meta charset=\"utf-8\">" +
                        "<title>Code</title>" +
                    "</head>" +
                    "<body>" +
                        "<pre id=\"code_snippet\">" + codeRepository.getCode() + "</pre>" +
                        "<span id=\"load_date\"> " +
                            codeRepository.getDate().toString().replaceFirst("T"," ") +
                        "</span>" +
                    "</body>" +
                "</html>";
    }

    public static String getNewCodeForm() {
        Resource resource = new DefaultResourceLoader().getResource("classpath:NewCode.html");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
