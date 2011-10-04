package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.CustomerDAO;
import ua.com.manometr.model.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	@Transactional
	public void addCustomer(Customer customer) {
		customerDAO.addCustomer(customer);
	}

	@Override
	@Transactional
	public List<Customer> listCustomer() {
		return customerDAO.listCustomer();
	}

	@Override
	@Transactional
	public void removeCustomer(Long id) {
		customerDAO.removeCustomer(id);
	}

}
