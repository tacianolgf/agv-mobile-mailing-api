package br.com.vgx.json.eligible;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResEligibleData {

	private Boolean isCustomerEligible;

	private List<ResEligibleReason> reasons;
}
