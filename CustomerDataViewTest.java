package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerDataViewTest {

	@Autowired
	CustomerDataView customerDataView;
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Test
	public void findByFirstNameTest() {
		AddCustomer customer1=new AddCustomer(1,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234");
		testEntityManager.persist(customer1);		
		List<AddCustomer> getFirstNameofcustomer=customerDataView.findByFirstName(customer1.getFirstName());
		assert(getFirstNameofcustomer!=null);
		
	}
	@Test
	public void findByLastName() {
		AddCustomer customer1=new AddCustomer(1,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234");
		testEntityManager.persist(customer1);		
		List<AddCustomer> getLastNameofcustomer=customerDataView.findByLastName(customer1.getLastName());
		assert(getLastNameofcustomer!=null);
		
	}
	@Test
	public void getAllCustomerTest() {
		AddCustomer customer1=new AddCustomer(1,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234");

		AddCustomer customer2=new AddCustomer(2,"Bruce","Wayne","BruceWaynek@gmail.com",
				"235 marvelStreet newYork USA","johnwick235");

		AddCustomer customer3=new AddCustomer(3,"Chris","Hemsworth","ChrisHemsworth@gmail.com",
				"236 marvelStreet newYork USA","ChrisHemsworth236");

		AddCustomer customer4=new AddCustomer(4,"Robert","Downey","RobertDowney@gmail.com",
				"237 marvelStreet newYork USA","RobertDowney237");
		Iterable<AddCustomer> customerData=customerDataView.findAll();
		assertThat(customerData).hasSize(4).contains(customer1,customer2,customer3,customer4);
		
	}
	@Test
	public void findCustomerByIdTest() {
			AddCustomer customer1=new AddCustomer(1,"John","Wick","johnwick@gmail.com",
					"234 marvelStreet newYork USA","johnwick234");
			testEntityManager.persist(customer1);

			AddCustomer customer2=new AddCustomer(2,"Bruce","Wayne","BruceWaynek@gmail.com",
					"235 marvelStreet newYork USA","johnwick235");
			testEntityManager.persist(customer2);
			
			AddCustomer getCustomertoUpdate=customerDataView.findById((long) customer2.getId()).get();
			assertThat(getCustomertoUpdate.equals(customer2));
		
	}
	@Test
	public void updateCustomerTest() {
		AddCustomer customer1=new AddCustomer(1,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234");
		testEntityManager.persist(customer1);

		AddCustomer customer2=new AddCustomer(2,"Bruce","Wayne","BruceWaynek@gmail.com",
				"235 marvelStreet newYork USA","johnwick235");
		testEntityManager.persist(customer2);
		
		AddCustomer getCustomertoUpdate=customerDataView.findById((long) customer2.getId()).get();
		getCustomertoUpdate.setFirstName("Sachin");
		getCustomertoUpdate.setLastName("Shinde");
		customerDataView.save(getCustomertoUpdate);
		assertThat(customer2.getFirstName().equalsIgnoreCase("Sachin"));
		assertThat(customer2.getLastName().equalsIgnoreCase("Shinde"));
		
	}
	@Test
	public void createCustomerTest() {
		AddCustomer customer=customerDataView.save(new AddCustomer(1,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234"));
		assertThat(customer).hasFieldOrPropertyWithValue("id", 1);
		assertThat(customer).hasFieldOrPropertyWithValue("firstName", "John");
		assertThat(customer).hasFieldOrPropertyWithValue("lastname", "Wick");
		assertThat(customer).hasFieldOrPropertyWithValue("Email", "johnwick@gmail.com");
		assertThat(customer).hasFieldOrPropertyWithValue("Address", "234 marvelStreet newYork USA");
		assertThat(customer).hasFieldOrPropertyWithValue("password","johnwick234");
		
	}
	@Test
	public void testifCustomerDataViewisEmpty() {
		Iterable<AddCustomer> customerData=customerDataView.findAll();
		assertThat(customerData).isEmpty();
	}
	
	
	
}
