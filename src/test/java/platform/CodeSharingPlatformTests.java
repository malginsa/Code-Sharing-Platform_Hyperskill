package platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class CodeSharingPlatformTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void getCodeInHtml() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/code").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("<html><head><title>Code</title></head>" +
						"<body><pre>Wonder code!</pre></body></html>")));
	}

	@Test
	void getCodeInJson() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/code").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"code\":\"Wonder code!\"}")));
	}
}
