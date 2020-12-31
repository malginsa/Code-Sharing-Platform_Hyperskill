package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class CodeSerializerTest {

	@Resource
	private ObjectMapper objectMapper;

	@Test
	public void getCustomSerializedCodeTest() throws Exception {
		Code code = new Code(1, "Wonder Code!", LocalDateTime.parse("2020-11-11T11:11:11.1111111"));
		String string = objectMapper.writeValueAsString(code);
		assertThat(string, equalTo("{\"code\":\"Wonder Code!\",\"date\":\"2020-11-11 11:11:11\"}"));
	}
}
