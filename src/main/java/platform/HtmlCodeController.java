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
        CodeSnippet codeSnippet = codeRepository.findById(uuid).orElseThrow(NotFoundStatusException::new);
        if (codeSnippet.isAvailable()) {
            codeSnippet = codeRepository.decreaseViews(codeSnippet);
            model = populateModel(model, codeSnippet);
            return "singlecode";
        } else {
            throw new NotFoundStatusException();
        }
    }

    @GetMapping(path = "/code/new")
    public String getCodeNew() {
        return "newcode";
    }

    @GetMapping(path = "/code/latest")
    public String getTenLatest(ModelMap model) {
        model.addAttribute("codes",
                codeRepository.findTop10ByIsRestrictedByTimeFalseAndIsRestrictedByViewsFalseOrderByDateDesc());
        return "listofcodes";
    }

    private ModelMap populateModel(ModelMap model, CodeSnippet codeSnippet) {
        model.addAttribute("code", codeSnippet.getCode());
        model.addAttribute("date", codeSnippet.getFormattedDate());
        model.addAttribute("isRestrictedByTime", codeSnippet.isRestrictedByTime());
        model.addAttribute("time", codeSnippet.getRemainingSeconds());
        model.addAttribute("isRestrictedByViews", codeSnippet.isRestrictedByViews());
        model.addAttribute("views", codeSnippet.getViews());
        return model;
    }

}
