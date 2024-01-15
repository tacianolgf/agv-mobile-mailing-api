package br.com.vgx.json.output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class MobileAccountOutput implements Serializable {

	private String id;

	private Double debtValue;

	private List<MobileInvoiceOutput> invoices;

	public MobileAccountOutput() {
		this.invoices = new ArrayList<MobileInvoiceOutput>();
	}

}
