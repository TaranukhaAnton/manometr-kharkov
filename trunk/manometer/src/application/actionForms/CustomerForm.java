package application.actionForms;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class CustomerForm extends ActionForm {
	public CustomerForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long id;
	private Long orgForm;
	private Long orgFormUkr;
    private String nameUkr;
	private String name;
	private String shortName;
	private String stateProperty; // ������� ���������������
	private String branch; // �������

	private boolean purposeForItself;
	private boolean purposeIntermediary;
	private boolean purposeSuply;
	private boolean purposeInstalation;

	private boolean applicationProcess;
	private boolean applicationProduction;
	private boolean applicationProject;
	private boolean applicationEngineering;

	private Integer nomList;

	private String prospect;

	private Long country;
	private Long city;
	private Long area;
	private Long region;
	private Long localityType;


    private String newCountry;
	private String newCity;
	private String newArea;
	private String newRegion;
    private String newOrgForm;


	private String address1;
	private String address2;
	private String address3;

	private String headCustomer; // �������� �����������

	private String mergeData; // ���� �������
	private String questionnaire; // ������ �����������
	private boolean status; // �������� /����������
	private String oldRecord; // ����� ������, � ����� ������ ������ ... :)

	private Long person;
	private String dateOfRecord;
	private String dateLastCorrection;
	private String codeOKPO;
	private String requisite;
	private String registrationNumber;
	private String site;
	private String moreInformation;
    private boolean New;

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public Long getOrgFormUkr() {
        return orgFormUkr;
    }

    public void setOrgFormUkr(Long orgFormUkr) {
        this.orgFormUkr = orgFormUkr;
    }

    public String getHeadCustomer() {
		return headCustomer;
	}

	public String getOldRecord() {
		return oldRecord;
	}

	public void setHeadCustomer(String headCustomer) {
		this.headCustomer = headCustomer;
	}

	public void setOldRecord(String oldRecord) {
		this.oldRecord = oldRecord;
	}

	public Long getId() {
		return id;
	}

	public Long getOrgForm() {
		return orgForm;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getStateProperty() {
		return stateProperty;
	}

	public String getBranch() {
		return branch;
	}

	public boolean getPurposeForItself() {
		return purposeForItself;
	}

	public boolean getPurposeIntermediary() {
		return purposeIntermediary;
	}

	public boolean getPurposeSuply() {
		return purposeSuply;
	}

	public boolean getPurposeInstalation() {
		return purposeInstalation;
	}

	public boolean getApplicationProcess() {
		return applicationProcess;
	}

	public boolean getApplicationProduction() {
		return applicationProduction;
	}

	public boolean getApplicationProject() {
		return applicationProject;
	}

	public boolean getApplicationEngineering() {
		return applicationEngineering;
	}

	public Integer getNomList() {
		return nomList;
	}

	public boolean getNew() {
		return New;
	}

	public String getProspect() {
		return prospect;
	}

	public Long getCountry() {
		return country;
	}

	public Long getCity() {
		return city;
	}

	public Long getArea() {
		return area;
	}

	public Long getRegion() {
		return region;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getAddress3() {
		return address3;
	}

	public String getMergeData() {
		return mergeData;
	}

	public String getQuestionnaire() {
		return questionnaire;
	}

	public boolean getStatus() {
		return status;
	}

	public Long getPerson() {
		return person;
	}

	public String getDateOfRecord() {
		return dateOfRecord;
	}

	public String getDateLastCorrection() {
		return dateLastCorrection;
	}

	public String getCodeOKPO() {
		return codeOKPO;
	}

	public String getRequisite() {
		return requisite;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getSite() {
		return site;
	}

	public String getMoreInformation() {
		return moreInformation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrgForm(Long orgForm) {
		this.orgForm = orgForm;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setStateProperty(String stateProperty) {
		this.stateProperty = stateProperty;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public void setPurposeForItself(boolean purposeForItself) {
		this.purposeForItself = purposeForItself;
	}

	public void setPurposeIntermediary(boolean purposeIntermediary) {
		this.purposeIntermediary = purposeIntermediary;
	}

	public void setPurposeSuply(boolean purposeSuply) {
		this.purposeSuply = purposeSuply;
	}

	public void setPurposeInstalation(boolean purposeInstalation) {
		this.purposeInstalation = purposeInstalation;
	}

	public void setApplicationProcess(boolean applicationProcess) {
		this.applicationProcess = applicationProcess;
	}

	public void setApplicationProduction(boolean applicationProduction) {
		this.applicationProduction = applicationProduction;
	}

	public void setApplicationProject(boolean applicationProject) {
		this.applicationProject = applicationProject;
	}

	public void setApplicationEngineering(boolean applicationEngineering) {
		this.applicationEngineering = applicationEngineering;
	}

	public void setNomList(Integer nomList) {
		this.nomList = nomList;
	}

	public void setNew(boolean new1) {
		New = new1;
	}

	public void setProspect(String prospect) {
		this.prospect = prospect;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public void setRegion(Long region) {
		this.region = region;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public void setMergeData(String mergeData) {
		this.mergeData = mergeData;
	}

	public void setQuestionnaire(String questionnaire) {
		this.questionnaire = questionnaire;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setPerson(Long person) {
		this.person = person;
	}

	public void setDateOfRecord(String dateOfRecord) {
		this.dateOfRecord = dateOfRecord;
	}

	public void setDateLastCorrection(String dateLastCorrection) {
		this.dateLastCorrection = dateLastCorrection;
	}

	public void setCodeOKPO(String codeOKPO) {
		this.codeOKPO = codeOKPO;
	}

	public void setRequisite(String requisite) {
		this.requisite = requisite;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setMoreInformation(String moreInformation) {
		this.moreInformation = moreInformation;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return new ActionErrors();
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {



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

    public String getNewRegion() {
        return newRegion;
    }

    public void setNewRegion(String newRegion) {
        this.newRegion = newRegion;
    }

    public String getNewArea() {
        return newArea;
    }

    public void setNewArea(String newArea) {
        this.newArea = newArea;
    }

    public String getNewOrgForm() {
        return newOrgForm;
    }

    public void setNewOrgForm(String newOrgForm) {
        this.newOrgForm = newOrgForm;
    }

    public Long getLocalityType() {
        return localityType;
    }

    public void setLocalityType(Long localityType) {
        this.localityType = localityType;
    }
}
