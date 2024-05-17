package id.thedev.tumit.sbdemoapi.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    private JwtResponseDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
        return JwtResponseDTO.builder().build();
    }
}
