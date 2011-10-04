package ua.com.manometr.dao.modeldescription;
import ua.com.manometr.model.modeldescription.ModelDescription;

import java.util.List;

public interface ModelDescriptionDAO {

	public void addModelDescription(ModelDescription modeldescription);

	public List<ModelDescription> listModelDescription();

	public void removeModelDescription(Long id);

}
