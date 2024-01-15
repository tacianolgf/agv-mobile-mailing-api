package br.com.vgx.json.debtor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtorData {

	private String personType;

	private String customerSubtype;

	private ResDebtorCustomer customer;
}
