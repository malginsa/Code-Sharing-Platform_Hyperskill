package platform;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
public class ApiCodeController {

    private static final HttpHeaders HTML_HTTP_HEADER = new HttpHeaders();

    static {
        HTML_HTTP_HEADER.setContentType(MediaType.TEXT_HTML);
    }

    @Resource
    private CodeRepository codeRepository;

    @GetMapping(path = "/api/code/{uuid}")
    public CodeSnippet getCodeInJson(@PathVariable UUID uuid) {
        CodeSnippet codeSnippet = codeRepository.findById(uuid).orElseThrow(NotFoundStatusException::new);
        if (codeSnippet.isAvailable()) {
            return codeRepository.decreaseViews(codeSnippet);
        } else {
            throw new NotFoundStatusException();
        }
    }

    @PostMapping(path = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> postNewCode(@RequestBody CodeDTO codeDTO) {
        String uuid = codeRepository.save(new CodeSnippet(codeDTO)).getUuid().toString();
        return ResponseEntity.ok().headers(HTML_HTTP_HEADER).body("{ \"uuid\" : \"" + uuid + "\" }");
    }

    @GetMapping(path = "/api/code/latest")
    public List<CodeSnippet> getTenLatest() {
        return codeRepository.findTop10ByIsRestrictedByTimeFalseAndIsRestrictedByViewsFalseOrderByDateDesc();
    }
}
