package com.example.demo;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
 

	public interface CustomerDataView extends JpaRepository<AddCustomer, Long> {
	  List<AddCustomer> findByFirstName(String firstName);
	  List<AddCustomer> findByLastName(String lastName);
	  Object save(Class<AddCustomer> class1);

	}

	

