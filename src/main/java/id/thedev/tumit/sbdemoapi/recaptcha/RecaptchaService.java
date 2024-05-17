package id.thedev.tumit.sbdemoapi.recaptcha;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET = "6LcFZdYpAAAAAJ2Fl27oop9vW1MrNZLWMXM9DYaO";

    //    @Autowired
    private RestTemplate restClient = new RestTemplateBuilder().build();

    public RecaptchaResponse verify(RecaptchaRequest request) {

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var params = new LinkedMultiValueMap<>();
        params.add("secret", SECRET);
        params.add("response", request.token());

        var entity = new HttpEntity<>(params, headers);
        var res = restClient.exchange(URL, HttpMethod.POST, entity, RecaptchaResponse.class);

        return res.getBody();
    }
}
