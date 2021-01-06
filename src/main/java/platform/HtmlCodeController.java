package platform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
public class HtmlCodeController {

    @Resource
    private CodeRepository codeRepository;

    @GetMapping(path = "/code/{uuid}")
    public String test(@PathVariable UUID uuid, ModelMap model) {
        CodeSnippet codeSnippet = codeRepository.findById(uuid).orElseThrow();
        model.addAttribute("code", codeSnippet.getCode());
        model.addAttribute("date", codeSnippet.getFormattedDate());
        return "singlecode";
    }

    @GetMapping(path = "/code/new")
    public String getCodeNew() {
        return "newcode";
    }

    @GetMapping(path = "/code/latest")
    public String getTenLatest(ModelMap model) {
        model.addAttribute("codes", codeRepository.findTop10ByOrderByDateDesc());
        return "listofcodes";
    }
}
