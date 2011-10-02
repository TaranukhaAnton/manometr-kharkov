package ua.com.manometr.model;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;


@Entity

public class Customer {
    public static String[] localityTypeAlias = {"г.", "пгт", "пос.","с."};


    @Override
    public String toString() {

        return name;
    }

    private Long id;
    private OrgForm orgForm;
    private String name;
    private String shortName;
    private String stateProperty;
    private Branch branch;

    private boolean purposeForItself;
    private boolean purposeIntermediary;
    private boolean purposeSuply;
    private boolean purposeInstalation;

    private boolean applicationProcess;
    private boolean applicationProduction;
    private boolean applicationProject;
    private boolean applicationEngineering;

    private Integer nomList;
    private boolean New;
    private Prospect prospect;

    private Long country;
    private Long city;
    private Long area;
    private Long region;
    private Long localityType;


    private String address1;
    private String address2;
    private String address3;

    private Customer headCustomer;
    private Date mergeData;
    private String questionnaire;
    private boolean status;
    private Customer oldRecord;
    private User person;
    private Date dateOfRecord;
    private Date dateLastCorrrection;
    private String codeOKPO;
    private String requisite;
    private String registrationNumber;
    private String site;
    private String moreInformation;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Column(columnDefinition = "integer", nullable = true)
    @Type(type = "application.hibernate.GenericEnumUserType", parameters = {
            @Parameter(name = "enumClass", value = "application.data.Customer$Branch"),
            @Parameter(name = "identifierMethod", value = "toInt"),
            @Parameter(name = "valueOfMethod", value = "fromInt")})
    public Branch getBranch() {
        return branch;
    }


    @ManyToOne(fetch = FetchType.EAGER)

    public OrgForm getOrgForm() {
        return orgForm;
    }

    public String getStateProperty() {

        return stateProperty;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isPurposeForItself() {
        return purposeForItself;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isPurposeIntermediary() {
        return purposeIntermediary;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isPurposeSuply() {
        return purposeSuply;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isPurposeInstalation() {
        return purposeInstalation;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isApplicationProcess() {
        return applicationProcess;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isApplicationProduction() {
        return applicationProduction;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isApplicationProject() {
        return applicationProject;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isApplicationEngineering() {
        return applicationEngineering;
    }

    public Integer getNomList() {
        return nomList;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isNew() {
        return New;
    }

    @Column(columnDefinition = "integer", nullable = true)
    @Type(type = "application.hibernate.GenericEnumUserType", parameters = {
            @Parameter(name = "enumClass", value = "application.data.Customer$Prospect"),
            @Parameter(name = "identifierMethod", value = "toInt"),
            @Parameter(name = "valueOfMethod", value = "fromInt")})
    public Prospect getProspect() {
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

    @OneToOne(cascade = CascadeType.PERSIST)
    public Customer getHeadCustomer() {
        return headCustomer;
    }

    public Date getMergeData() {
        return mergeData;
    }

    public String getQuestionnaire() {
        return questionnaire;
    }

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    public boolean isStatus() {
        return status;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public Customer getOldRecord() {
        return oldRecord;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})

    public User getPerson() {
        return person;
    }

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public Date getDateLastCorrrection() {
        return dateLastCorrrection;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setOrgForm(OrgForm orgForm) {
        this.orgForm = orgForm;
    }

    public void setStateProperty(String stateProperty) {
        this.stateProperty = stateProperty;
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

    public void setProspect(Prospect prospect) {
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

    public void setHeadCustomer(Customer headCustomer) {
        this.headCustomer = headCustomer;
    }

    public void setMergeData(Date mergeData) {
        this.mergeData = mergeData;
    }

    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setOldRecord(Customer oldRecord) {
        this.oldRecord = oldRecord;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public void setDateOfRecord(Date dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public void setDateLastCorrrection(Date dateLastCorrrection) {
        this.dateLastCorrrection = dateLastCorrrection;
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

    public Long getLocalityType() {
        return localityType;
    }

    public void setLocalityType(Long localityType) {
        this.localityType = localityType;
    }

    public enum Branch {
        про(1), аэс(2), энр(3), мет(4), доб(5), нгу(6), хим(7), маш(8), стр(9), ком(
                10);
        private int value;

        Branch(int value) {
            this.value = value;
        }

        // the identifierMethod
        public int toInt() {
            return value;
        }

        // the valueOfMethod
        public static Branch fromInt(int value) {
            switch (value) {
                case 1:
                    return про;
                case 2:
                    return аэс;
                case 3:
                    return энр;
                case 4:
                    return мет;
                case 5:
                    return доб;
                case 6:
                    return нгу;
                case 7:
                    return хим;
                case 8:
                    return маш;
                case 9:
                    return стр;
                case 10:
                    return ком;

                default:
                    return про;
            }
        }

        public String toString() {
            switch (this) {
                case про:
                    return "про";
                case аэс:
                    return "аэс";
                case энр:
                    return "энр";
                case мет:
                    return "мет";
                case доб:
                    return "доб";
                case нгу:
                    return "нгу";
                case хим:
                    return "хим";
                case маш:
                    return "маш";
                case стр:
                    return "стр";
                case ком:
                    return "ком";

                default:
                    return "про";
            }

        }
    }


    public enum Prospect {
        A(1), B(2), C(3);

        private int value;

        Prospect(int value) {
            this.value = value;
        }

        // the identifierMethod
        public int toInt() {
            return value;
        }

        // the valueOfMethod
        public static Prospect fromInt(int value) {
            switch (value) {
                case 1:
                    return A;
                case 2:
                    return B;
                case 3:
                    return C;
                default:
                    return A;
            }
        }

        public String toString() {
            switch (this) {
                case A:
                    return "A";
                case B:
                    return "B";
                case C:
                    return "C";
                default:
                    return "A";
            }

        }
    }

}
