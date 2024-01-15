package br.com.vgx.json.debtor;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtorAccount {

	private String mobileBan;

	private String mobileMarketId;

	private String regional;

	private String ufMarket;

	private String accountSituation;

	private LocalDate accountSituationDate;

	private String accountSituationReason;

	private Boolean compliantIndicator;

	private Boolean judicialProcessIndicator;

	private String creditClass;

	private LocalDate creditClassChangeDate;

	private String usageLimitValue;

	private String useValue;

	private String suggestedPaymentLimitUsage;

	private String notBilledValues;

	private String valuesWithoutPayment;

	private String amountPayments;

	private String totalPaid;

	private String franchiseControlConcessionDate;

	private LocalDate activationDate;

	private String invoiceIssueCategory;
}
