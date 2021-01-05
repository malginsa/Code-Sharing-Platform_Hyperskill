package platform;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ApiCodeController {

    private static final HttpHeaders HTML_HTTP_HEADER = new HttpHeaders();

    static {
        HTML_HTTP_HEADER.setContentType(MediaType.TEXT_HTML);
    }

    @Resource
    private CodeRepository codeRepository;

    @GetMapping(path = "/api/code/{id}")
    public CodeSnippet getCodeInJson(@PathVariable int id) {
        return codeRepository.get(id).orElseThrow();
    }

    @PostMapping(path = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> postNewCode(@RequestBody CodeDTO codeDTO) {
        int id = codeRepository.add(codeDTO);
        return ResponseEntity.ok().headers(HTML_HTTP_HEADER).body("{ \"id\" : \"" + id + "\" }");
    }

    @GetMapping(path = "/api/code/latest")
    public List<CodeSnippet> getTenLatest() {
        return codeRepository.getTenLatest();
    }
}
