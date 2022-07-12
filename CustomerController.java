package com.example.demo;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;

/*This is Controller class*/

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CustomerController {
	@Autowired
	CustomerDataView customerDataview;
	@GetMapping("/customersfindbyname/{fisrtName}")
	public ResponseEntity<List<AddCustomer>> findByFirstName( @PathVariable("firstName")String firstName) {
		try {
			List<AddCustomer> customerList=new LinkedList<AddCustomer>();
			if (firstName == null)
				customerDataview.findAll().forEach(customerList::add);
			else
				customerDataview.findByFirstName(firstName).forEach(customerList::add);
			if (customerDataview.equals(null)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customerList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/customers/{id}")
	public ResponseEntity<AddCustomer> findCustomerById(@PathVariable("Id") long id) {
		java.util.Optional<AddCustomer> customers = customerDataview.findById(id);
		if (customers.isPresent()) {
			return new ResponseEntity<>(customers.get(),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/addcustomers")
	public ResponseEntity<AddCustomer> createCustomer(@RequestBody AddCustomer customer) {
		try {
			AddCustomer newcustomer = customerDataview.
					save(new AddCustomer(customer.getId(), customer.getFirstName(),customer.getLastName(),customer.getEmail(),
							customer.getAddress(),customer.getPassword()));
					return new ResponseEntity<>(newcustomer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/updatecustomers/{id}")
	public ResponseEntity<AddCustomer> updateCustomer(@PathVariable("id") long id, @RequestBody AddCustomer customer) {
		java.util.Optional<AddCustomer> customerdata = customerDataview.findById(id);
		if (customerdata.isPresent()) {
			AddCustomer updatecust = customerdata.get();
			updatecust.setAddress(customer.getAddress());
			updatecust.setEmail(customer.getEmail());
			updatecust.setPassword(customer.getPassword());
			return new ResponseEntity<>(customerDataview.save(updatecust), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/allcustomers")
	public ResponseEntity<List<AddCustomer>> getAllCustomers() {
		try {
			List<AddCustomer> customers=new LinkedList<>();
			if(customerDataview.count()>0) {
				customerDataview.findAll().forEach(customers::add);
			}else
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}