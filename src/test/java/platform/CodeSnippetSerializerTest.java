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
		CodeSnippet codeSnippet = new CodeSnippet(new CodeDTO("Wonder Code!", 0, 0));
		codeSnippet.setDate(LocalDateTime.parse("2020-11-11T11:11:11.111"));
		String string = objectMapper.writeValueAsString(codeSnippet);
		assertThat(string, equalTo(
				"{\"code\":\"Wonder Code!\",\"date\":\"2020-11-11 11:11:11\",\"time\":0,\"views\":0}"));
	}
}
