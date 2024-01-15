package br.com.vgx.json.output;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class MobileOutput implements Serializable {

	private String transactionId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	private String errorMessage;

	private MobileDataOutput data;

	public void initData() {
		this.data = new MobileDataOutput();
		this.data.setCustomer(new MobileCustomerOutput());
	}

	public MobileOutput() {
		this.transactionId = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
	}

}
