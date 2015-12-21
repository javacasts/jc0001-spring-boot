package javacasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@SpringBootApplication
public class BootApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(BootApplication.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam(name = "to", defaultValue = "world") String recipient)
	{
		ModelAndView result = new ModelAndView("index");
		result.getModel().put("target", recipient);
		return result;
	}
}
