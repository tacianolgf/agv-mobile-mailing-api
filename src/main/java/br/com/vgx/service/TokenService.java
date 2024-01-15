package br.com.vgx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${token_url}")
	private String tokenUrl;

	public String getTokenMobile() {

		String tokenResponse = null;
		final var headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		final var uri = tokenUrl;
		var tokenRequest = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, tokenRequest, String.class);
			tokenResponse = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenResponse;
	}

}
