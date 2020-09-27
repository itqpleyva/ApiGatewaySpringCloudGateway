package Main.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/micro2")
public class micro2Controller {


	@GetMapping("/message")
	public String test() {
		
		return "Hello from microservice 2";
	}


}
