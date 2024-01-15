package br.com.vgx.json.debtor;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDebtorCustomer {

	private String name;

	private LocalDate birthDate;

	private String motherName;

	private String gender;

	private String cpf;

	private String rg;

	private String rgEmitter;

	private String passportCountry;

	private String contactName;

	private String contactPhone;

	private String contactEmail;

	private String occupation;

	private String salary;

	private Boolean contributorIndicator;

	private Boolean fomeZeroContributorIndicator;

	private Boolean fundoPobrezaContributorIndicator;

	private List<ResDebtorAccount> accounts;

	private String attendancePassword;

	private ResDebtorMobileClient mobileClient;
}
