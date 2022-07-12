package com.example.demo;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class CustomerControllerTest {
	@MockBean
	private CustomerDataView customerDataView;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createCustomerTest() throws Exception {
		AddCustomer newCustomer=new AddCustomer(1,"King","Henry","kingHenry@google.com",
				"01 A KingsRoad,KingsValley","1kingHenry");
		mockMvc.perform(post("/api/addcustomers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newCustomer)))
				.andExpect(status().isCreated())
				.andDo(print());
				
		
	}
	@Test
	public void findCustomerbyIdtest() {
		long id=1L;
		AddCustomer newCustomer=new AddCustomer(id,"King","Henry","kingHenry@google.com",
				"01 A KingsRoad,KingsValley","1kingHenry");
		when(customerDataView.findById(id)).thenReturn(Optional.of(newCustomer));
	    try {
			mockMvc.perform(get("/api/customers/{id}", id)).andExpect(status().isOk())
			    .andExpect(jsonPath("$.id").value(id))
			    .andExpect(jsonPath("$.firstName").value(newCustomer.getFirstName()))
			    .andExpect(jsonPath("$.lastName").value(newCustomer.getLastName()))
			    .andExpect(jsonPath("$.email").value(newCustomer.getEmail()))
			    .andExpect(jsonPath("$.email").value(newCustomer.getAddress()))
			    .andExpect(jsonPath("$.password").value(newCustomer.getPassword()))
			    .andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	}
	@Test
	public void retrieveAllCustomerTest() {
		AddCustomer customer1=new AddCustomer(1,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234");

		AddCustomer customer2=new AddCustomer(2,"Bruce","Wayne","BruceWaynek@gmail.com",
				"235 marvelStreet newYork USA","johnwick235");

		AddCustomer customer3=new AddCustomer(3,"Chris","Hemsworth","ChrisHemsworth@gmail.com",
				"236 marvelStreet newYork USA","ChrisHemsworth236");
		List<AddCustomer> customerList=new LinkedList<AddCustomer>();
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);
		when(customerDataView.findAll()).thenReturn(customerList);
		try {
			mockMvc.perform(get("/api/retrieveallcustomers"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(customerList.size()))
			.andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void updatecustomerData() throws Exception{
		long id=1L;
		AddCustomer customer=new AddCustomer(id,"John","Wick","johnwick@gmail.com",
				"234 marvelStreet newYork USA","johnwick234");
		AddCustomer updatedCustomer=new AddCustomer(id,"Sachin","Shinde",
				"sachinshinde@gmail.com","123 ABCRoad KingsVilla","abc123");
		when(customerDataView.findById(id)).thenReturn(Optional.of(customer));
		when(customerDataView.save(AddCustomer.class)).thenReturn(updatedCustomer);
		   mockMvc.perform(put("api/updatecustomers/{id}", id).contentType(MediaType.APPLICATION_JSON)
			        .content(objectMapper.writeValueAsString(updatedCustomer)))
			        .andExpect(status().isOk())
			        .andExpect(jsonPath("$.firstName").value(updatedCustomer.getFirstName()))
			        .andExpect(jsonPath("$.lastName").value(updatedCustomer.getLastName()))
			        .andExpect(jsonPath("$.email").value(updatedCustomer.getEmail()))
			        .andExpect(jsonPath("$.Address").value(updatedCustomer.getAddress()))
			        .andExpect(jsonPath("$.password").value(updatedCustomer.getPassword()))
			        .andDo(print());
}




 
}