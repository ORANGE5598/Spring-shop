package com.shop.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("verifyIamport")
public class VerifyController {
	
	private final IamportClient iamportClient;
	private static final String API_KEY = "4556500950256242";
    private static final String API_SECRET = "f98fe01fd3806dd7443fd41074fc28edd1fae5244061228e33fc899e669ce106faece3f995cd6bf1";

	public VerifyController() {
		this.iamportClient = new IamportClient(API_KEY, API_SECRET);
	}
	
	@PostMapping("/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException {
		log.info("paymentByImpUid 진입");
		return iamportClient.paymentByImpUid(imp_uid);
	}

}
