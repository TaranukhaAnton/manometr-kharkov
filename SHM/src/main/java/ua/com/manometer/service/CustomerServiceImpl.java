package ua.com.manometer.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.CustomerDAO;
import ua.com.manometer.dao.OrgFormDAO;
import ua.com.manometer.model.Customer;
import ua.com.manometer.model.OrgForm;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public void addCustomer(Customer customer) {

        if ((customer.getHeadCustomer() != null) && (StringUtils.isNotEmpty(customer.getHeadCustomer().getShortName()))) {
            Customer headCustomer = customerDAO.getCustomerByShortName(customer.getHeadCustomer().getShortName());
            customer.setHeadCustomer(headCustomer);
        } else {
            customer.setHeadCustomer(null);
        }
        if ((customer.getOldRecord() != null) && (StringUtils.isNotEmpty(customer.getOldRecord().getShortName()))) {
            Customer oldRecord = customerDAO.getCustomerByShortName(customer.getOldRecord().getShortName());
            customer.setOldRecord(oldRecord);
        } else {
            customer.setOldRecord(null);
        }




        customerDAO.addCustomer(customer);
    }

    @Override
    @Transactional
    public List<Customer> listCustomer() {
        return customerDAO.listCustomer();
    }

    @Override
    @Transactional
    public void removeCustomer(Integer id) {
        customerDAO.removeCustomer(id);
    }

    @Override
    @Transactional
    public Customer getCustomer(Integer id) {
        return customerDAO.getCustomer(id);
    }

    @Override
    @Transactional
    public List<String> findByShortNameExample(String customerTemplate) {
        return customerDAO.findByShortNameExample(customerTemplate);

    }

    @Override
    @Transactional
    public Customer getCustomerByShortName(String shortName) {
        return customerDAO.getCustomerByShortName(shortName);
    }


    @Override
    @Transactional
    public Boolean isCustomerPresent(String customer) {
        if (StringUtils.isBlank(customer)) {
            return false;
        }
        return customerDAO.isCustomerPresent(customer);
    }


}
