package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.OrgFormDAO;
import ua.com.manometr.model.OrgForm;

import java.util.List;

@Service
public class OrgFormServiceImpl implements OrgFormService {

	@Autowired
	private OrgFormDAO orgformDAO;


    @Override
    public OrgForm getOrgForm(Long id) {
       return orgformDAO.getOrgForm( id);
    }

    @Override
	@Transactional
	public void addOrgForm(OrgForm orgform) {
		orgformDAO.addOrgForm(orgform);
	}

	@Override
	@Transactional
	public List<OrgForm> listOrgForm() {
		return orgformDAO.listOrgForm();
	}

	@Override
	@Transactional
	public void removeOrgForm(Long id) {
		orgformDAO.removeOrgForm(id);
	}

}
