package br.com.vgx.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.vgx.json.eligible.ResEligible;
import br.com.vgx.json.eligible.ResEligibleData;
import br.com.vgx.json.input.MailingInput;

@Service
public class MobileEligibilityService {

private static final DateTimeFormatter formatterHourMinuteSecond = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Value("${mobile_api_url}")
	private String mobileApiUrl;

	public static final String ELIGIBILITY_URL = "/mobile/v1/subscriberseligibilities";

	@Autowired
	private RestTemplate restTemplate;

	public ResEligibleData getEligibility(String token, MailingInput mailing) {
		var numTries = 0;
		return getEligibility(numTries, token, mailing);
	}

	private ResEligibleData getEligibility(Integer numTries, String token, MailingInput mailing) {
		final var headers = getHeaders(token, mailing);
		final var params = getParams();
		final var uri = getUri();
		final var body = new HttpEntity<>(headers);

		try {
			numTries++;
			ResponseEntity<ResEligible> response = restTemplate.exchange(uri, HttpMethod.GET, body, ResEligible.class, params);
			return response.getBody().getData();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			if (numTries < 5 && exception.getMessage().contains("Too Many Requests")) {
				System.err.println(LocalTime.now().format(formatterHourMinuteSecond) + " - " + uri + " - Too Many Requests");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				return getEligibility(numTries, token, mailing);
			}
			mailing.setMobileErrorMessage(exception.getMessage());
			return null;
		}
	}

	private HttpHeaders getHeaders(String token, MailingInput mailing) {
		final var headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.set("Accept", "application/json");
		headers.set("X-QueryString", String.format("msisdn=%s", mailing.getTelefone()));
		return headers;
	}

	private HashMap<String, String> getParams() {
		final var params = new HashMap<String, String>();
		params.put("eligibilityCategory", "PARCELAR_DIVIDA");
		params.put("subscriptionType", "CLARO_CONTA");
		return params;
	}

	private String getUri() {
		final var url = String.format("%s%s", mobileApiUrl, ELIGIBILITY_URL);
		System.out.println(LocalTime.now().format(formatterHourMinuteSecond) + " - " + url);
		final var builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("eligibilityCategory", "{eligibilityCategory}")
				.queryParam("subscriptionType", "{subscriptionType}");

		String uri = builder.encode().toUriString();
		return uri;
	}
	
}
