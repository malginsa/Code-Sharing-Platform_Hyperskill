package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestControllerTests {

	public static final String API_CODE_NEW_PATH = "/api/code/new";
	public static final String CODE_NEW_PATH = "/code/new";
	@Autowired
	private MockMvc mvc;

	@Test
	void postNewCodeTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(API_CODE_NEW_PATH)
				.contentType(APPLICATION_JSON)
				.content("{\"code\":\"Perfect code!\"}"))
				.andExpect(status().isOk())
				.andExpect(result ->
						assertThat(getIdDTO(result.getResponse().getContentAsString(), IdDto.class).isIdValid())
								.isTrue());
	}

	@Test
	void getNewCode() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(CODE_NEW_PATH).accept(TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(readTemplate("/templates/newcode.ftlh"))));
	}

	private String readTemplate(String fileName) throws URISyntaxException, IOException {
		return Files.readString(Paths.get(getClass().getResource(fileName).toURI()));
	}

	private <T> T getIdDTO(String text, Class<T> classValue) throws JsonProcessingException {
		return new TimeAwareObjectMapper().readValue(text, classValue);
	}

	class TimeAwareObjectMapper extends ObjectMapper {
		public TimeAwareObjectMapper() {
			registerModule(new JavaTimeModule());
		}
	}
}
