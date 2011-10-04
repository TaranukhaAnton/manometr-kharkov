package ua.com.manometr.service;

import ua.com.manometr.model.OrgForm;

import java.util.List;

public interface OrgFormService {

	public void addOrgForm(OrgForm orgform);

	public List<OrgForm> listOrgForm();

	public void removeOrgForm(Long id);

}
