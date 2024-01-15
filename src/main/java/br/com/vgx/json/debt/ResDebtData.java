package br.com.vgx.json.debt;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtData {

	private String billingAccountId;

	private Double totalInvoicesAmount;

	private List<ResDebtInvoice> invoices;
}
