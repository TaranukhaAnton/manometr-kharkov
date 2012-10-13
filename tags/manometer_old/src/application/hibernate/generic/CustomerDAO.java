package application.hibernate.generic;

import application.data.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.List;

public class CustomerDAO extends GenericHibernateDAO<Customer> {

	public List<Customer> findBySortNameExample(String exampleInstance) {

		Session session = null;
		List<Customer> list = null;
		try {
			session = getSession();
			Criteria crit = session.createCriteria(getPersistentClass());

			crit.add(Expression.like("shortName", exampleInstance + "%"));
            crit.add(Expression.eq("status",true));
                    // Expression.
			list = crit.list();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}

	public Customer findByShortName(String exampleInstance) {

		Session session = null;
		Customer customer = null;
		try {
			session = getSession();

			customer = (Customer) session.createCriteria(Customer.class).add(
					Expression.eq("shortName", exampleInstance)).add(Expression.eq("status", true)).uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

}
