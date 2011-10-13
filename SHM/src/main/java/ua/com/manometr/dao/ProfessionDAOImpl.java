package ua.com.manometr.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometr.model.Profession;

import java.util.List;

@Repository
public class ProfessionDAOImpl implements ProfessionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addProfession(Profession profession) {
		sessionFactory.getCurrentSession().save(profession);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Profession> listProfession() {
		return sessionFactory.getCurrentSession().createQuery("from Profession")
				.list();
	}

	@Override
	public void removeProfession(Long id) {
		Profession profession = (Profession) sessionFactory.getCurrentSession().load(
				Profession.class, id);
		if (profession != null) {
			sessionFactory.getCurrentSession().delete(profession);
		}
	}

}
