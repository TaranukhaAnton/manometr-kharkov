package ua.com.manometer.dao;

import ua.com.manometer.model.OrgForm;

import java.util.List;

public interface OrgFormDAO {

	public void addOrgForm(OrgForm orgForm);

	public List<OrgForm> listOrgForm();

	public void removeOrgForm(Integer id);

    public OrgForm getOrgForm(Integer id);
}