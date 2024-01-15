package br.com.vgx.json.output;

import java.io.Serializable;

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

}
