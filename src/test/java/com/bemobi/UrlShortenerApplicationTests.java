package com.bemobi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlShortenerApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
		String body = this.restTemplate.getForObject("/u/vaga", String.class);
		assertThat(body).isEqualTo("Hello World");
	}

	@Test
	public void testError002() {
		String body = this.restTemplate.getForObject("/u/vagaErrada", String.class);
		assertThat(body).isEqualTo("{\"errorCode\":\"002\",\"description\":\"SHORTENED URL NOT FOUND\",\"alias\":\"vagaErrada\"}");
	}

}