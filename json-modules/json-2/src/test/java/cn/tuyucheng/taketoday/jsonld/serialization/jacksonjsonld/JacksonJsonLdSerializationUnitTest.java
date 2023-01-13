package cn.tuyucheng.taketoday.jsonld.serialization.jacksonjsonld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonJsonLdSerializationUnitTest {
	@Test
	void givenAJacksonJsonldAnnotatedObject_whenJsonldModuleIsUsed_thenAJsonLdDocumentIsGenerated() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JsonldModule());

		Person person = new Person("http://example.com/person/1234", "Example Name");
		String personJsonLd = objectMapper.writeValueAsString(person);

		assertEquals("{"
			+ "\"@type\":\"s:Person\","
			+ "\"@context\":{"
			+ "\"s\":\"http://schema.org/\","
			+ "\"name\":\"s:name\","
			+ "\"knows\":{\"@id\":\"s:knows\",\"@type\":\"@id\"}"
			+ "},"
			+ "\"name\":\"Example Name\","
			+ "\"@id\":\"http://example.com/person/1234\","
			+ "\"knows\":\"http://example.com/person/2345\""
			+ "}", personJsonLd);
	}
}
