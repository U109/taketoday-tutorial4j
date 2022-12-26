package cn.tuyucheng.taketoday.integrationtesting;

import cn.tuyucheng.taketoday.jacoco.exclude.annotations.ExcludeFromJacocoGeneratedReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class SecuredController {

	@GetMapping("/public/hello")
	@ExcludeFromJacocoGeneratedReport
	public List<String> publicHello() {
		return Arrays.asList("Hello", "World", "from", "Public");
	}

	@GetMapping("/private/hello")
	public List<String> privateHello() {
		return Arrays.asList("Hello", "World", "from", "Private");
	}
}