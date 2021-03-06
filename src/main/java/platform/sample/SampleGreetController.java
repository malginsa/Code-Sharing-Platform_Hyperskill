package platform.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SampleGreetController {

    public static void main(String[] args) {
        SpringApplication.run(SampleGreetController.class, args);
    }

    @PostMapping(value = "/sample/greet", consumes = "application/json")
    public String greet(@RequestBody UserInfo userInfo) {
        if (userInfo.isEnabled()) {
            return String.format("Hello! Nice to see you, %s!", userInfo.getName());
        } else {
            return String.format("Hello! Nice to see you, %s! Your account is disabled", userInfo.getName());
        }
    }

    @PostMapping(value = "/sample/greet/users",consumes = "application/json")
    public String greetToUsers(@RequestBody List<UserInfo> userInfoList) {
        return String.format("OK, %d of users have been logged out!", userInfoList.size());
    }
}
