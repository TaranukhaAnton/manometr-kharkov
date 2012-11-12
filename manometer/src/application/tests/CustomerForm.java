package application.tests;

import application.data.Customer;
import application.hibernate.Factory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerForm extends ActionForm {


    Customer customer;
    private String newCountry;
	private String newCity;
	private String newArea;
	private String newRegion;
    private String newOrgForm;

    public String getHeadCustomer() {
        //return headCustomer;
        Customer headCustomer = customer.getHeadCustomer();
        return (headCustomer != null) ? headCustomer.getShortName() : null;
    }

    public void setHeadCustomer(String headCustomer) {
        // this.headCustomer = headCustomer;
        if (!headCustomer.isEmpty()) {
            Map<String, Object> map = new HashMap();
            map.put("shortName", headCustomer);
            map.put("status", true);
            List<Customer> res = Factory.getCustomerDAO().findByExample(map);
            if (!res.isEmpty())
                customer.setHeadCustomer(res.get(0));
        }


    }


    public String getOldRecord() {
        return null;
    }


    public void setOldRecord(String oldRecord) {
//        this.oldRecord = oldRecord;
    }

    public Long getId() {
        return customer.getId();
    }

    public Long getOrgForm() {
        return customer.getOrgForm().getId();
    }

    public String getName() {
        return customer.getName();
    }

    public String getShortName() {
        return customer.getShortName();
    }

    public String getStateProperty() {
        return customer.getStateProperty();
    }

    public String getBranch() {
        return customer.getBranch().toString();
    }

    public boolean getPurposeForItself() {
        return customer.isPurposeForItself();
    }

    public boolean getPurposeIntermediary() {
        return customer.isPurposeIntermediary();
    }

    public boolean getPurposeSuply() {
        return customer.isPurposeSupply();
    }

    public boolean getPurposeInstalation() {
        return customer.isPurposeInstallation();
    }

    public boolean getApplicationProcess() {
        return customer.isApplicationProcess();
    }

    public boolean getApplicationProduction() {
        return customer.isApplicationProduction();
    }

    public boolean getApplicationProject() {
        return customer.isApplicationProject();
    }

    public boolean getApplicationEngineering() {
        return customer.isApplicationEngineering();
    }

    public Integer getNomList() {
        return customer.getNomList();
    }

    public boolean getNew() {
        return customer.isNew();
    }

    public String getProspect() {
        return customer.getProspect().toString();
    }

    public Long getCountry() {
        return customer.getCountry();
    }

    public Long getCity() {
        return customer.getCity();
    }

    public Long getArea() {
        return customer.getArea();
    }

    public Long getRegion() {
        return customer.getRegion();
    }

    public String getAddress1() {
        return customer.getAddress1();
    }

    public String getAddress2() {
        return customer.getAddress2();
    }

    public String getAddress3() {
        return customer.getAddress3();
    }

    public String getMergeData() {
        return (new SimpleDateFormat("dd.MM.yyyy")).format(customer.getMergeData());
    }

    public String getQuestionnaire() {
        return customer.getQuestionnaire();
    }

    public boolean getStatus() {
        return customer.isStatus();
    }

    public Long getPerson() {
        return customer.getPerson().getId();
    }

    public String getDateOfRecord() {
        return (new SimpleDateFormat("dd.MM.yyyy")).format(customer.getDateOfRecord());
    }

    public String getDateLastCorrection() {
        return (new SimpleDateFormat("dd.MM.yyyy")).format(customer.getDateLastCorrrection());
    }

    public String getCodeOKPO() {
        return customer.getCodeOKPO();
    }

    public String getRequisite() {
        return customer.getRequisite();
    }

    public String getRegistrationNumber() {
        return customer.getRegistrationNumber();
    }

    public String getSite() {
        return customer.getSite();
    }

    public String getMoreInformation() {
        return customer.getMoreInformation();
    }

    public void setId(Long id) {
        customer.setId(id);
    }

    public void setOrgForm(Long orgForm) {
        customer.setOrgForm(Factory.getOrgFormDAO().findById(orgForm));
    }

    public void setName(String name) {
        customer.setName(name);
    }

    public void setShortName(String shortName) {
        customer.setShortName(shortName);
    }

    public void setStateProperty(String stateProperty) {
        customer.setStateProperty(stateProperty);
    }

    public void setBranch(String branch) {
        customer.setBranch(Customer.Branch.valueOf(branch));
    }

    public void setPurposeForItself(boolean purposeForItself) {
        customer.setPurposeForItself(purposeForItself);
    }

    public void setPurposeIntermediary(boolean purposeIntermediary) {
        customer.setPurposeIntermediary(purposeIntermediary);
    }

    public void setPurposeSuply(boolean purposeSuply) {
        setPurposeSuply(purposeSuply);
    }

    public void setPurposeInstalation(boolean purposeInstalation) {
        customer.setPurposeInstallation(purposeInstalation);
    }

    public void setApplicationProcess(boolean applicationProcess) {
        customer.setApplicationProcess(applicationProcess);
    }

    public void setApplicationProduction(boolean applicationProduction) {
        customer.setApplicationProduction(applicationProduction);
    }

    public void setApplicationProject(boolean applicationProject) {
        customer.setApplicationProject(applicationProject);
    }

    public void setApplicationEngineering(boolean applicationEngineering) {
        customer.setApplicationEngineering(applicationEngineering);
    }

    public void setNomList(Integer nomList) {
        customer.setNomList(nomList);
    }

    public void setNew(boolean new1) {
        customer.setNew(new1);
    }

    public void setProspect(String prospect) {
        customer.setProspect(Customer.Prospect.valueOf(prospect));
    }

    public void setCountry(Long country) {
        customer.setCountry(country);
    }

    public void setCity(Long city) {
        customer.setCity(city);
    }

    public void setArea(Long area) {
        customer.setArea(area);
    }

    public void setRegion(Long region) {
        customer.setRegion(region);
    }

    public void setAddress1(String address1) {
        customer.setAddress1(address1);
    }

    public void setAddress2(String address2) {
        customer.setAddress2(address2);
    }

    public void setAddress3(String address3) {
        customer.setAddress3(address3);
    }

    public void setMergeData(String mergeData) {
        try {
            customer.setMergeData((new SimpleDateFormat("dd.MM.yyyy")).parse(mergeData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setQuestionnaire(String questionnaire) {
        customer.setQuestionnaire(questionnaire);
    }

    public void setStatus(boolean status) {
        customer.setStatus(status);
    }

    public void setPerson(Long person) {
        customer.setPerson(Factory.getUserDAO().findById(person));
    }

    public void setDateOfRecord(String dateOfRecord) {
        try {
            customer.setDateOfRecord((new SimpleDateFormat("dd.MM.yyyy")).parse(dateOfRecord));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setDateLastCorrection(String dateLastCorrection) {
        try {
            customer.setDateLastCorrrection((new SimpleDateFormat("dd.MM.yyyy")).parse(dateLastCorrection));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCodeOKPO(String codeOKPO) {
        customer.setCodeOKPO(codeOKPO);
    }

    public void setRequisite(String requisite) {
        customer.setRequisite(requisite);
    }

    public void setRegistrationNumber(String registrationNumber) {
        customer.setRegistrationNumber(registrationNumber);
    }

    public void setSite(String site) {
        customer.setSite(site);
    }

    public void setMoreInformation(String moreInformation) {
        customer.setMoreInformation(moreInformation);
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return new ActionErrors();
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        customer = new Customer();

    }



    public Long getLocalityType() {
        return customer.getLocalityType();
    }

    public void setLocalityType(Long localityType) {
        customer.setLocalityType(localityType);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(String newCountry) {
        this.newCountry = newCountry;
    }

    public String getNewCity() {
        return newCity;
    }

    public void setNewCity(String newCity) {
        this.newCity = newCity;
    }

    public String getNewArea() {
        return newArea;
    }

    public void setNewArea(String newArea) {
        this.newArea = newArea;
    }

    public String getNewRegion() {
        return newRegion;
    }

    public void setNewRegion(String newRegion) {
        this.newRegion = newRegion;
    }

    public String getNewOrgForm() {
        return newOrgForm;
    }

    public void setNewOrgForm(String newOrgForm) {
        this.newOrgForm = newOrgForm;
    }
}