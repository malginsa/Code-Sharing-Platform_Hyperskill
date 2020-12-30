package platform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlCodeController {

    @GetMapping(path = "/code")
    public String test(ModelMap model) {
        model.addAttribute("code", CodeRepository.currentCode.getCode());
        model.addAttribute("date", CodeRepository.currentCode.getDate());
        return "currentcode";
    }

    @GetMapping(path = "/code/new")
    public String getCodeNew() {
        return "newcode";
    }

}
