package br.com.vgx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vgx.json.input.MailingInput;
import br.com.vgx.json.output.MobileOutput;
import br.com.vgx.service.EnrichService;

@RestController
@RequestMapping(value = "mobile")
public class MobileController {

	@Autowired
	EnrichService enrichService;

	@PostMapping(value = "get-data")
	@ResponseStatus(HttpStatus.OK)
	public MobileOutput getData(@Validated @RequestBody MailingInput input) {
		final var response = enrichService.getData(input);
		return response;
	}

}
