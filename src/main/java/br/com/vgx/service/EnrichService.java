package br.com.vgx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vgx.json.input.MailingInput;
import br.com.vgx.json.output.MobileAccountOutput;
import br.com.vgx.json.output.MobileInvoiceOutput;
import br.com.vgx.json.output.MobileOutput;

@Service
public class EnrichService {

	@Autowired
	TokenService tokenService;

	@Autowired
	MobileEligibilityService eligibilityService;

	@Autowired
	MobileDebtorService debtorService;

	@Autowired
	MobileDebtService debtService;

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
			if (debtorRes.getCustomer().getMobileClient() != null) {
				output.getData().getCustomer().setId(debtorRes.getCustomer().getMobileClient().getMobileClientId());
			}
			output.getData().getCustomer().setName(debtorRes.getCustomer().getName());
			output.getData().getCustomer().setCpf(debtorRes.getCustomer().getCpf());
			output.getData().getCustomer().setPhone(debtorRes.getCustomer().getContactPhone());
		}

		final var debtRes = debtService.getDebts(token, input);
		if (debtRes == null) {
			output.setErrorMessage("Erro api mobile/v1/customers/bills/agreements/eligibleitems");
			return output;
		}

		final var account = new MobileAccountOutput();

		account.setId(debtRes.getBillingAccountId());
		account.setDebtValue(debtRes.getTotalInvoicesAmount());
		for (var mobileInvoice : debtRes.getInvoices()) {
			for (var mobileItens : mobileInvoice.getInvoiceItems()) {
				final var invoice = new MobileInvoiceOutput();
				invoice.setId(mobileItens.getNumber());
				invoice.setValue(mobileItens.getAmount());
				account.getInvoices().add(invoice);
			}
		}
		output.getData().getAccounts().add(account);

		final List<Integer> invoiceItems = new ArrayList<>();
		for (var invoice : debtRes.getInvoices()) {
			for (var invoiceItem : invoice.getInvoiceItems()) {
				invoiceItems.add(invoiceItem.getNumber());
			}
		}

		return output;
	}

}
