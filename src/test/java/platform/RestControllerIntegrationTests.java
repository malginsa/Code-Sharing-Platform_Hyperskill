package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestControllerIntegrationTests {

	@LocalServerPort
	private int port;

	private String baseUrl;

	@BeforeEach
	public void setUp() throws Exception {
		this.baseUrl = new URL("http://localhost:" + port + "/").toString();
	}

	@Test
	public void getSampleCodeSnippet() throws Exception {
		CodeRepository.resetCurrentCodeToSample();
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + "api/code"))
				.GET()
				.header("Accept", "application/json")
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		CodeRepository codeRepository = getCodeRepository(response);
		assertThat(codeRepository.getCode()).isEqualTo("Wonder Code!");
		assertThat(codeRepository.getDate()).isEqualTo("2020-11-11T11:11:11");
	}

	@Test
	public void getUpdatedCodeSnippet() throws Exception {
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + "api/code/new"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString("{\"code\":\"Perfect Code!\"}"))
				.build();
		client.send(request, HttpResponse.BodyHandlers.discarding());
		LocalDateTime expectedDate = CodeRepository.getCurrentCode().getDate();
		request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + "api/code"))
				.GET()
				.header("Accept", "application/json")
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		CodeRepository codeRepository = getCodeRepository(response);
		assertThat(codeRepository.getCode()).isEqualTo("Perfect Code!");
		assertThat(codeRepository.getDate()).isEqualTo(expectedDate.toString());
	}

	private CodeRepository getCodeRepository(HttpResponse<String> response) throws JsonProcessingException {
		ObjectMapper objectMapper = new TimeAwareObjectMapper();
		return objectMapper.readValue(response.body(), CodeRepository.class);
	}

	class TimeAwareObjectMapper extends ObjectMapper {
		public TimeAwareObjectMapper() {
			registerModule(new JavaTimeModule());
		}
	}
}
