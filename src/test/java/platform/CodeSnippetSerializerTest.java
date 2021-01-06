package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class CodeSnippetSerializerTest {

	@Resource
	private ObjectMapper objectMapper;

	@Test
	public void getCustomSerializedCodeTest() throws Exception {
		CodeSnippet codeSnippet = new CodeSnippet("Wonder Code!", LocalDateTime.parse("2020-11-11T11:11:11.1111111"));
		String string = objectMapper.writeValueAsString(codeSnippet);
		assertThat(string, equalTo("{\"code\":\"Wonder Code!\",\"date\":\"2020-11-11 11:11:11\"}"));
	}
}
