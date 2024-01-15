package br.com.vgx.json.debt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtInvoiceItem {

	private Integer number;

	private String type;

	private String amount;
}
