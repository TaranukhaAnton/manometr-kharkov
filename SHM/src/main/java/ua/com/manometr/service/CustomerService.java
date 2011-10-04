package ua.com.manometr.service;

import ua.com.manometr.model.Customer;

import java.util.List;

public interface CustomerService {

	public void addCustomer(Customer customer);

	public List<Customer> listCustomer();

	public void removeCustomer(Long id);

}
