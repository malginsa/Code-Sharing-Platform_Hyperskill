package platform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class HtmlCodeController {

    @Resource
    private CodeRepository codeRepository;

    @GetMapping(path = "/code/{id}")
    public String test(@PathVariable int id, ModelMap model) {
        Code code = codeRepository.get(id).orElseThrow();
        model.addAttribute("code", code.getCode());
        model.addAttribute("date", code.getFormattedDate());
        return "singlecode";
    }

    @GetMapping(path = "/code/new")
    public String getCodeNew() {
        return "newcode";
    }

    @GetMapping(path = "/code/latest")
    public String getTenLatest(ModelMap model) {
        model.addAttribute("codes", codeRepository.getTenLatest());
        return "listofcodes";
    }
}
