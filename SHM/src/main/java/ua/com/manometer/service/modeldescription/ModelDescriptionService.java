package ua.com.manometer.service.modeldescription;

import ua.com.manometer.model.modeldescription.ModelDescription;

import java.util.List;

public interface ModelDescriptionService {

	public void addModelDescription(ModelDescription modeldescription);

	public List<ModelDescription> listModelDescription();

	public void removeModelDescription(Long id);

}
