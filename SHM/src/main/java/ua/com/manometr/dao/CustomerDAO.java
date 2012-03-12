package ua.com.manometr.dao;

import ua.com.manometr.model.Customer;

import java.util.List;
public interface CustomerDAO {

	public void addCustomer(Customer customer);

	public List<Customer> listCustomer();

	public void removeCustomer(Long id);

    public Customer getCustomer(Long id);

    public List<Customer> findByShortNameExample(String customerTemplate);

    public Customer getCustomerByShortName(String shortName);
}