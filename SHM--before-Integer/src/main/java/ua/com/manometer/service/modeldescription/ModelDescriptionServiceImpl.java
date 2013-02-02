package ua.com.manometer.service.modeldescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.BaseDAO;
import ua.com.manometer.dao.modeldescription.ModelDescriptionDAO;
import ua.com.manometer.model.modeldescription.ModelDescription;

import java.util.List;

@Service
public class ModelDescriptionServiceImpl implements ModelDescriptionService {

	@Autowired
	private ModelDescriptionDAO modeldescriptionDAO;

    @Autowired
    private BaseDAO baseDAO;

	@Override
	@Transactional
	public void updateDescription(ModelDescription modeldescription) {
		modeldescriptionDAO.updateDescription(modeldescription);
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

    @Override
    @Transactional
    public List<ModelDescription> findListByIds(List<Long> modelIds) {
        return  modeldescriptionDAO.findListByIds(modelIds);
    }

    @Override
    @Transactional
    public ModelDescription getModelDescription(Long model) {
        return (ModelDescription) baseDAO.getById(model,ModelDescription.class);
    }


}
