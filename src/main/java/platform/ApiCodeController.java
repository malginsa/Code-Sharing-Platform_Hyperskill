package platform;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiCodeController {

    @GetMapping(path = "/api/code")
    public CodeRepository getCodeInJson() {
        return CodeRepository.getCurrentCode();
    }

    @PostMapping(path = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> postNewCode(@RequestBody CodeSnippet codeSnippet) {
        CodeRepository.updateCurrentCode(codeSnippet);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return ResponseEntity.ok().headers(httpHeaders).body("{}");
    }
}
