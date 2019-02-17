package com.springboot.repository;




import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.springboot.model.Enquiry;

@Repository
public class DynamoDBRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBRepository.class);
	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insertIntoDynamoDB(Enquiry enquiry) {
		mapper.save(enquiry);
	}
	
	public Enquiry getOneEnquiryDetails(String enquiryId, String lastName) {
		return mapper.load(Enquiry.class, enquiryId, lastName);
	}
	
	public void updateEnquiryDetails(Enquiry enquiry) {
		try {
			mapper.save(enquiry, buildDynamoDBSaveExpression(enquiry));
		} catch (ConditionalCheckFailedException exception) {
			LOGGER.error("Invalid Enquiry - " + exception.getMessage());
		}
	}
	
	public void deleteEnquiryDetails(Enquiry enquiry) {
		mapper.delete(enquiry);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(Enquiry enquiry) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("enquiryId", new ExpectedAttributeValue(new AttributeValue(enquiry.getEnquiryId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
		
	}
	

}
