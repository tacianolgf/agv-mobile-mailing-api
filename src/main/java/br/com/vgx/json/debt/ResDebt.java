package br.com.vgx.json.debt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebt {

	private String apiVersion;

	private String transactionId;

	private ResDebtData data;
}
