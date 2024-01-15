package br.com.vgx.json.debt;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtInvoice {

	private String number;

	private String amount;

	private List<ResDebtInvoiceItem> invoiceItems;
}
