package cn.tuyucheng.taketoday;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockWebServerApplication implements CommandLineRunner {
	public static final Logger LOGGER = LoggerFactory.getLogger(MockWebServerApplication.class);

	private final UsersClient usersClient;
	private final ObjectMapper objectMapper;

	public MockWebServerApplication(UsersClient usersClient, ObjectMapper objectMapper) {
		this.usersClient = usersClient;
		this.objectMapper = objectMapper;
	}


	public static void main(String[] args) {
		SpringApplication.run(MockWebServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("name: {}", usersClient.getUserById(1L).get("name"));
		System.out.println(usersClient.getUserById(1L));

		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("name", "duke");
		objectNode.put("email", "duke@java.io");
		objectNode.set("address",
			objectMapper.createObjectNode()
				.put("street", "main")
				.put("postalCode", "91074"));
		objectNode.set("hobbies", objectMapper.createArrayNode().add("sports").add("bowling"));

		System.out.println(usersClient.createNewUser(objectNode));
	}
}