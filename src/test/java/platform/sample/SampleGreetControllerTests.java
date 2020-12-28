package platform.sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class SampleGreetControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void postUserInfo() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/sample/greet")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"FooName\"}"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Hello! Nice to see you, FooName! Your account is disabled")));
	}
}
