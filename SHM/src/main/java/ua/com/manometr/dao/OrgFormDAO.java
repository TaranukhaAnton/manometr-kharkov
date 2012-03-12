package ua.com.manometr.dao;

import ua.com.manometr.model.OrgForm;

import java.util.List;

public interface OrgFormDAO {

	public void addOrgForm(OrgForm orgForm);

	public List<OrgForm> listOrgForm();

	public void removeOrgForm(Long id);

    public OrgForm getOrgForm(Long id);
}