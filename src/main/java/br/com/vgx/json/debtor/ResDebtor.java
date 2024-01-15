package br.com.vgx.json.debtor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtor {

	private String apiVersion;

	private String transactionId;

	private ResDebtorData data;
}
