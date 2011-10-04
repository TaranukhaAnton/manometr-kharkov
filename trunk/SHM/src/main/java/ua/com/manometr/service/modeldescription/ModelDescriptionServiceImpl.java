package ua.com.manometr.service.modeldescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.modeldescription.ModelDescriptionDAO;
import ua.com.manometr.model.modeldescription.ModelDescription;

import java.util.List;

@Service
public class ModelDescriptionServiceImpl implements ModelDescriptionService {

	@Autowired
	private ModelDescriptionDAO modeldescriptionDAO;

	@Override
	@Transactional
	public void addModelDescription(ModelDescription modeldescription) {
		modeldescriptionDAO.addModelDescription(modeldescription);
	}

	@Override
	@Transactional
	public List<ModelDescription> listModelDescription() {
		return modeldescriptionDAO.listModelDescription();
	}

	@Override
	@Transactional
	public void removeModelDescription(Long id) {
		modeldescriptionDAO.removeModelDescription(id);
	}

}
