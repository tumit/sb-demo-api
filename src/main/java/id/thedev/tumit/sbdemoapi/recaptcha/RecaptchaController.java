package id.thedev.tumit.sbdemoapi.recaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recaptcha")
public class RecaptchaController {

    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping("/verify")
    public RecaptchaResponse verify(@RequestBody RecaptchaRequest recaptchaRequest) {
        return recaptchaService.verify(recaptchaRequest);
    }
}
