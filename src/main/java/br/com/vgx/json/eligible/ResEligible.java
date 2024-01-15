package br.com.vgx.json.eligible;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResEligible {

	private String apiVersion;

	private String transactionId;

	private ResEligibleData data;
}
