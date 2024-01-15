package br.com.vgx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vgx.json.input.MailingInput;
import br.com.vgx.json.output.MobileOutput;

@Service
public class EnrichService {

	@Autowired
	TokenService tokenService;

	@Autowired
	MobileEligibilityService eligibilityService;

	@Autowired
	MobileDebtorService debtorService;

	public MobileOutput getData(MailingInput input) {
		final var output = new MobileOutput();
		final var token = tokenService.getTokenMobile();

		final var eligibleRes = eligibilityService.getEligibility(token, input);
		if (eligibleRes == null) {
			output.setErrorMessage("Erro api mobile/v1/subscriberseligibilities");
			return output;
		}

		if (Boolean.FALSE.equals(eligibleRes.getIsCustomerEligible())) {
			output.setErrorMessage("NAO ELEGIVEL");
			if (eligibleRes.getReasons() != null && !eligibleRes.getReasons().isEmpty()) {
				output.setErrorMessage(eligibleRes.getReasons().toString());
			}
			return output;
		}

		final var debtorRes = debtorService.getDebtor(token, input);
		if (debtorRes == null) {
			output.setErrorMessage("Erro api mobile/v1/customers/billing/details");
			return output;
		}

		if (debtorRes.getCustomer() != null) {
			output.initData();
			if (debtorRes.getCustomer().getMobileClient() != null) {
				output.getData().getCustomer().setId(debtorRes.getCustomer().getMobileClient().getMobileClientId());
			}
			output.getData().getCustomer().setName(debtorRes.getCustomer().getName());
			output.getData().getCustomer().setCpf(debtorRes.getCustomer().getCpf());
			output.getData().getCustomer().setPhone(debtorRes.getCustomer().getContactPhone());
		}
		return output;
	}

}
