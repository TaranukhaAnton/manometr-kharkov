package ua.com.manometer.dao;

import ua.com.manometer.model.Customer;

import java.util.List;
public interface CustomerDAO {

	public void addCustomer(Customer customer);

	public List<Customer> listCustomer();

	public void removeCustomer(Integer id);

    public Customer getCustomer(Integer id);

    public List<String> findByShortNameExample(String customerTemplate);

    public Customer getCustomerByShortName(String shortName);

    public Boolean isCustomerPresent(String customer);
}