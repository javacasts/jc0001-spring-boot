package javacasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class BootApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(BootApplication.class, args);
	}

	@RequestMapping("/")
	public String index()
	{
		return "hello world";
	}
}
