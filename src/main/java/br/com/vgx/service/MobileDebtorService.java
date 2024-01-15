package br.com.vgx.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.vgx.json.debtor.ResDebtor;
import br.com.vgx.json.debtor.ResDebtorData;
import br.com.vgx.json.input.MailingInput;

@Service
public class MobileDebtorService {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Value("${mobile_api_url}")
	private String mobileApiUrl;

	public static final String DEBTOR_URL = "/mobile/v1/customers/billing/details";

	@Autowired
	private RestTemplate restTemplate;

	public ResDebtorData getDebtor(String token, MailingInput input) {
		var numTries = 0;
		return getDebtor(numTries, token, input);
	}

	private ResDebtorData getDebtor(Integer numTries, String token, MailingInput input) {
		final var headers = getHeaders(token, input);
		final var uri = getUri();
		final var body = new HttpEntity<>(headers);

		try {
			numTries++;
			ResponseEntity<ResDebtor> response = restTemplate.exchange(uri, HttpMethod.GET, body, ResDebtor.class);
			return response.getBody().getData();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			if (numTries < 5 && exception.getMessage().contains("Too Many Requests")) {
				System.err.println(LocalTime.now().format(formatter) + " - " + uri + " - Too Many Requests");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				return getDebtor(numTries, token, input);
			}
			input.setMobileErrorMessage(exception.getMessage());
			return null;
		}
	}

	private HttpHeaders getHeaders(String token, MailingInput input) {
		final var headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "application/json");
		headers.set("X-QueryString", String.format("mobileBan=%s", input.getBan()));
		return headers;
	}

	private String getUri() {
		final var url = String.format("%s%s", mobileApiUrl, DEBTOR_URL);
		System.out.println(LocalTime.now().format(formatter) + " - " + url);
		return url;
	}

}
