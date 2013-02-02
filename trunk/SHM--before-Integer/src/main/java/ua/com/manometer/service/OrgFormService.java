package ua.com.manometer.service;

import ua.com.manometer.model.OrgForm;

import java.util.List;

public interface OrgFormService {

    public OrgForm getOrgForm(Long id);

	public void addOrgForm(OrgForm orgform);

	public List<OrgForm> listOrgForm();

	public void removeOrgForm(Long id);

}
