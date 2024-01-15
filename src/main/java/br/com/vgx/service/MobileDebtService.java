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

import br.com.vgx.json.debt.ResDebt;
import br.com.vgx.json.debt.ResDebtData;
import br.com.vgx.json.input.MailingInput;

@Service
public class MobileDebtService {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Value("${mobile_api_url}")
	private String mobileApiUrl;

	public static final String DEBT_URL = "/mobile/v1/customers/bills/agreements/eligibleitems";

	@Autowired
	private RestTemplate restTemplate;

	public ResDebtData getDebts(String token, MailingInput input) {
		var numTries = 0;
		return getDebts(numTries, token, input);
	}

	private ResDebtData getDebts(Integer numTries, String token, MailingInput input) {
		final var headers = getHeaders(token);
		final var params = getParams(input);
		final var uri = getUri();
		final var body = new HttpEntity<>(headers);

		try {
			numTries++;
			ResponseEntity<ResDebt> response = restTemplate.exchange(uri, HttpMethod.GET, body, ResDebt.class, params);
			return response.getBody().getData();
		} catch (Exception exception) {
			if (numTries < 5 && exception.getMessage().contains("Too Many Requests")) {
				System.err.println(LocalTime.now().format(formatter) + " - " + uri + " - Too Many Requests");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				return getDebts(numTries, token, input);
			}
			return null;
		}
	}

	private HttpHeaders getHeaders(String token) {
		final var headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.set("accept", "application/json");
		return headers;
	}

	private HashMap<String, String> getParams(MailingInput input) {
		final var params = new HashMap<String, String>();
		params.put("billingAccountId", input.getBan());
		params.put("includeLongDistance", "true");
		return params;
	}

	private String getUri() {
		final var url = String.format("%s%s", mobileApiUrl, DEBT_URL);
		System.out.println(LocalTime.now().format(formatter) + " - " + url);
		final var builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("billingAccountId", "{billingAccountId}")
				.queryParam("includeLongDistance", "{includeLongDistance}");

		String uri = builder.encode().toUriString();
		return uri;
	}

}
