package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestControllerIntegrationTests {

	private static final int FIRST_ID = 1;

	private static final Pattern CODE_SNIPPET = Pattern.compile("<pre id=\"code_snippet\"><code>(.+)</code></pre>");
	private static final Pattern DATE_SNIPPET = Pattern.compile("<span id=\"load_date\">(.+)</span>");

	@LocalServerPort
	private int port;

	private String baseUrl;

	private HttpClient client;

	@BeforeEach
	public void setUp() throws Exception {
		this.baseUrl = new URL("http://localhost:" + port + "/").toString();
		codeRepository.clear();
		client = HttpClient.newBuilder().build();
	}

	@Resource
	private CodeRepository codeRepository;

	@Test
	public void postCodeSnippetAndGetTheSameViaApiAndHttp() throws Exception {
		postCodeSnippet("api/code/new", "application/json", "Perfect Code!");
		String expectedDate = codeRepository.get(FIRST_ID).orElseThrow().getFormattedDate();
		HttpResponse<String> response = sendGetRequest("api/code/" + FIRST_ID, "application/json");
		assertThat(response.body()).isEqualTo("{\"code\":\"Perfect Code!\",\"date\":\"" + expectedDate + "\"}");
		response = sendGetRequest("code/" + FIRST_ID, "text/html;charset=UTF-8");
		String body = response.body();
		checkPatternMatch(CODE_SNIPPET, body, "Perfect Code!");
		checkPatternMatch(DATE_SNIPPET, body, expectedDate);
	}

	@Test
	public void postTwoCodeSnippetAndCheckHtmlTwoLatest() throws Exception {
		postCodeSnippet("api/code/new", "application/json", "Perfect Code!");
		postCodeSnippet("api/code/new", "application/json", "Wonder Code!");
		HttpResponse<String> response = sendGetRequest("code/latest", "application/json");
		List<String> load_date = getElementsByClassFromHtml(response, "code_snippet");
		assertThat(load_date.get(0)).isEqualTo("Wonder Code!");
		assertThat(load_date.get(1)).isEqualTo("Perfect Code!");
	}

	@Test
	public void postTwoCodeSnippetAndCheckApiTwoLatest() throws Exception {
		postCodeSnippet("api/code/new", "application/json", "Perfect Code!");
		postCodeSnippet("api/code/new", "application/json", "Wonder Code!");
		HttpResponse<String> response = sendGetRequest("api/code/latest", "application/json");
		JSONObject jsonObject = new JSONObject("{\"body\":" + response.body() + "}");
		JSONArray codeSnippets = jsonObject.getJSONArray("body");
		codeSnippets.getJSONObject(0).getString("code");
		assertThat(codeSnippets.getJSONObject(0).getString("code")).isEqualTo("Wonder Code!");
		assertThat(codeSnippets.getJSONObject(1).getString("code")).isEqualTo("Perfect Code!");
	}

	private List<String> getElementsByClassFromHtml(HttpResponse<String> response, String className) {
		return Jsoup.parse(response.body()).getElementsByClass(className).eachText();
	}

	private void checkPatternMatch(Pattern pattern, String body, String expected) {
		Matcher matcher = pattern.matcher(body);
		matcher.find();
		String code = matcher.group(1);
		assertThat(code).isEqualTo(expected);
	}

	private HttpResponse<String> sendGetRequest(String suffixUri, String acceptContentType)
			throws java.io.IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + suffixUri))
				.GET()
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}

	private void postCodeSnippet(String suffixUrl, String contentType, String code)
			throws java.io.IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + suffixUrl))
				.header("Content-Type", contentType)
				.POST(HttpRequest.BodyPublishers.ofString("{\"code\":\"" + code + "\"}"))
				.build();
		client.send(request, HttpResponse.BodyHandlers.discarding());
	}
}
