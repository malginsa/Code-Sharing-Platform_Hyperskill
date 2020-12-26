package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

	private static final String SAMPLE_PIECE_OF_CODE = "Wonder code!";

	public static void main(String[] args) {
		SpringApplication.run(CodeSharingPlatform.class, args);
	}

	@GetMapping(path = "/api/code")
	public JsonWrapper getCodeInJson() {
		return new JsonWrapper(SAMPLE_PIECE_OF_CODE);
	}

	@GetMapping(path = "/code")
	public ResponseEntity<String> codeInHtml() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.TEXT_HTML);
		return ResponseEntity.ok().headers(httpHeaders)
				.body(new HtmlWrapper(SAMPLE_PIECE_OF_CODE).getAsHtml());
	}
}
