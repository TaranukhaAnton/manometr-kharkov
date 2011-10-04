package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.ProfessionDAO;
import ua.com.manometr.model.Profession;

import java.util.List;

@Service
public class ProfessionServiceImpl implements ProfessionService {

	@Autowired
	private ProfessionDAO professionDAO;

	@Override
	@Transactional
	public void addProfession(Profession profession) {
		professionDAO.addProfession(profession);
	}

	@Override
	@Transactional
	public List<Profession> listProfession() {
		return professionDAO.listProfession();
	}

	@Override
	@Transactional
	public void removeProfession(Long id) {
		professionDAO.removeProfession(id);
	}

}
