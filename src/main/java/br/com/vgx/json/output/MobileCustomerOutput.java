package br.com.vgx.json.output;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class MobileCustomerOutput implements Serializable {

	private String id;

	private String name;

	private String cpf;

	private String phone;

}
