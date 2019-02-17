package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Enquiry;
import com.springboot.repository.DynamoDBRepository;

@RestController
@RequestMapping("/dynamoDB")
public class DynamoDBController {
	
	@Autowired
	private DynamoDBRepository repository;
	
	@PostMapping
	public String insertIntoDynamoDB(@RequestBody Enquiry enquiry) {
		repository.insertIntoDynamoDB(enquiry);
		return "Record Inserted";
	}
	
	@GetMapping
	public ResponseEntity<Enquiry> getOneEnquiryDetails(@RequestParam String enquiryId, 
			@RequestParam String lastName){
		Enquiry enquiry = repository.getOneEnquiryDetails(enquiryId, lastName);
		return new ResponseEntity<Enquiry>(enquiry, HttpStatus.OK);
		
	}
	@PutMapping
	public void updateEnquiryDetails(@RequestBody Enquiry enquiry) {
		repository.updateEnquiryDetails(enquiry);
	}
	
	@DeleteMapping(value = "{enquiryId}/{lastName}")
	public void deleteEnquiryDetails(@PathVariable("enquiryId") String enquiryId, 
			@PathVariable("lastName") String lastName) {
		Enquiry enquiry = new Enquiry();
		enquiry.setEnquiryId(enquiryId);
		enquiry.setLastName(lastName);
		repository.deleteEnquiryDetails(enquiry);
	}
	

}
