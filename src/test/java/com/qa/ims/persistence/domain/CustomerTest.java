package com.qa.ims.persistence.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Customer.class).verify();
	}

	@Test
	public void testSetters() {
		Customer customer = new Customer("adam", "tan");
		customer.setFirstName("sean");
		customer.setSurname("flynn");

		assertEquals("sean", customer.getFirstName());
		assertEquals("flynn", customer.getSurname());
	}

}
