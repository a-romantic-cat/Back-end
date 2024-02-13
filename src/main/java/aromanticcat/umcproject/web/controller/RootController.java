package aromanticcat.umcproject.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://dev.nangmancat.shop")
@RestController
public class RootController {

    @GetMapping("/health")
    public String healthCheck(){
        return "I'm healthy!";
    }
}
