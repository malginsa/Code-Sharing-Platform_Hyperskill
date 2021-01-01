package platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void postNewCodeTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/code/new")
				.contentType(APPLICATION_JSON)
				.content("{\"code\":\"Perfect code!\"}"))
				.andExpect(status().isOk())
				.andExpect(content().json("{ \"id\" : \"1\" }"));
	}

	@Test
	void getNewCode() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/code/new").accept(TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(readTemplate("/templates/newcode.ftlh"))));
	}

	private String readTemplate(String fileName) throws URISyntaxException, IOException {
		return Files.readString(Paths.get(getClass().getResource(fileName).toURI()));
	}
}
