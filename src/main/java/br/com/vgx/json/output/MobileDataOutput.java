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
public class MobileDataOutput implements Serializable {

	private Boolean isCustomerEligible;

	private MobileCustomerOutput customer;

	private List<MobileAccountOutput> accounts;

	public MobileDataOutput() {
		this.customer = new MobileCustomerOutput();
		this.accounts = new ArrayList<MobileAccountOutput>();
	}

}
