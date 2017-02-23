package id.co.pegadaian.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class GadaiAuthserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GadaiAuthserverApplication.class, args);
	}
	
	@GetMapping("/protected")
    public void protectedPage() {}
}
