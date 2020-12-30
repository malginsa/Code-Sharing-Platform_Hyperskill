package platform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class RestControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void getCodeInHtml() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/code").accept(TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("<html><head><meta charset=\"utf-8\">" +
						"<title>Code</title></head>" +
						"<body><pre id=\"code_snippet\">Wonder Code!</pre><span id=\"load_date\">" +
						" 2020-11-11T11:11:11</span></body></html>")));
	}

	@Test
	void getCodeInJson() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/code").accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(
						"{\"code\":\"Wonder Code!\",\"date\":\"2020-11-11T11:11:11\"}")));
	}

	@Test
	void postNewCodeTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/code/new")
				.contentType(APPLICATION_JSON)
				.content("{\"code\":\"Perfect code!\"}"))
				.andExpect(status().isOk())
				.andExpect(content().json("{}"));
	}

	@Test
	void getNewCode() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/code/new").accept(TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(HtmlFormStorage.getNewCodeForm())));
	}
}
