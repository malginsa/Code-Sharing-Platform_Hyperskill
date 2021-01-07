package platform;

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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestControllerIntegrationTests {

	private static final Pattern CODE_SNIPPET = Pattern.compile("<pre id=\"code_snippet\"><code>(.+)</code></pre>");
	private static final Pattern DATE_SNIPPET = Pattern.compile("<span id=\"load_date\">(.+)</span>");
	public static final String API_CODE_NEW_PATH = "api/code/new";
	public static final String APPLICATION_JSON = "application/json";
	public static final String API_CODE_PATH = "api/code/";
	public static final String CODE_LATEST_PATH = "code/latest";
	public static final String API_CODE_LATEST_PATH = "api/code/latest";
	public static final String CODE_SAMPLE_1 = "Code sample 1";
	public static final String CODE_SAMPLE_2 = "Code sample 2";

	@LocalServerPort
	private int port;

	private String baseUrl;

	private HttpClient client;

	@BeforeEach
	public void setUp() throws Exception {
		this.baseUrl = new URL("http://localhost:" + port + "/").toString();
		codeRepository.deleteAll();
		client = HttpClient.newBuilder().build();
	}

	@Resource
	private CodeRepository codeRepository;

	@Test
	public void postCodeSnippetAndGetTheSameViaApiAndHttp() throws Exception {
		HttpResponse<String> response = postCodeSnippet(API_CODE_NEW_PATH, APPLICATION_JSON, CODE_SAMPLE_1);
		String uuid = getIdFromResponse(response);
		String expectedDate = codeRepository.findById(UUID.fromString(uuid)).orElseThrow().getFormattedDate();
		response = sendGetRequest(API_CODE_PATH + uuid, APPLICATION_JSON);
		assertThat(response.body()).isEqualTo(
				"{\"code\":\"Code sample 1\",\"date\":\"" + expectedDate + "\",\"time\":0,\"views\":0}");
		response = sendGetRequest("code/" + uuid, "text/html;charset=UTF-8");
		String body = response.body();
		checkPatternMatch(CODE_SNIPPET, body, CODE_SAMPLE_1);
		checkPatternMatch(DATE_SNIPPET, body, expectedDate);
	}

	@Test
	public void postTwoCodeSnippetAndCheckHtmlTwoLatest() throws Exception {
		postCodeSnippet(API_CODE_NEW_PATH, APPLICATION_JSON, CODE_SAMPLE_1);
		postCodeSnippet(API_CODE_NEW_PATH, APPLICATION_JSON, CODE_SAMPLE_2);
		HttpResponse<String> response = sendGetRequest(CODE_LATEST_PATH, APPLICATION_JSON);
		List<String> load_date = getElementsByClassFromHtml(response, "code_snippet");
		assertThat(load_date.get(0)).isEqualTo(CODE_SAMPLE_2);
		assertThat(load_date.get(1)).isEqualTo(CODE_SAMPLE_1);
	}

	@Test
	public void postTwoCodeSnippetAndCheckApiTwoLatest() throws Exception {
		postCodeSnippet(API_CODE_NEW_PATH, APPLICATION_JSON, CODE_SAMPLE_1);
		postCodeSnippet(API_CODE_NEW_PATH, APPLICATION_JSON, CODE_SAMPLE_2);
		HttpResponse<String> response = sendGetRequest(API_CODE_LATEST_PATH, APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject("{\"body\":" + response.body() + "}");
		JSONArray codeSnippets = jsonObject.getJSONArray("body");
		assertThat(codeSnippets.getJSONObject(0).getString("code")).isEqualTo(CODE_SAMPLE_2);
		assertThat(codeSnippets.getJSONObject(1).getString("code")).isEqualTo(CODE_SAMPLE_1);
	}

	private String getIdFromResponse(HttpResponse<String> response) {
		JSONObject jsonObject = new JSONObject(response.body());
		return jsonObject.getString("uuid");
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

	private HttpResponse<String> postCodeSnippet(String suffixUrl, String contentType, String code)
			throws java.io.IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + suffixUrl))
				.header("Content-Type", contentType)
				.POST(HttpRequest.BodyPublishers.ofString("{\"code\":\"" + code + "\"}"))
				.build();
		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
