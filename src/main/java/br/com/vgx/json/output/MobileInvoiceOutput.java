package br.com.vgx.json.output;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class MobileInvoiceOutput implements Serializable {

	private Integer id;

	private Double value;

}
