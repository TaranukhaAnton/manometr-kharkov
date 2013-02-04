package ua.com.manometer.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
public class Contact {
	private Integer id;
	private String customer;
	private String name;
	private String patronymic;
	private String lastName;
	private Profession profession;
	private String subdivision;
	private boolean group1;
	private boolean group2;
	private boolean group3;
	private String tel;
	private String fax;
	private String mobTel;
	private String eMail;
	private boolean purchaseRole1;
	private boolean purchaseRole2;
	private boolean purchaseRole3;
	private boolean purchaseRole4;
	private boolean purchaseRole5;
	private boolean purchaseRole6;
	private boolean purchaseRole7;
	private boolean status;
	private Contact oldRecord;
	private String method;

	public Contact() {
	}

	public Contact(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	

	public String getCustomer() {
		return customer;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public String getLastName() {
		return lastName;
	}
	
	@ManyToOne(fetch= FetchType.EAGER)
	
	public Profession getProfession() {
		return profession;
	}

	public String getSubdivision() {
		return subdivision;
	}
	
	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isGroup1() {
		return group1;
	}
	
	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isGroup2() {
		return group2;
	}
	
	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isGroup3() {
		return group3;
	}
	
	
	public String getTel() {
		return tel;
	}
	

	public String getFax() {
		return fax;
	}

	public String getMobTel() {
		return mobTel;
	}

	public String getEMail() {
		return eMail;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole1() {
		return purchaseRole1;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole2() {
		return purchaseRole2;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole3() {
		return purchaseRole3;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole4() {
		return purchaseRole4;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole5() {
		return purchaseRole5;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole6() {
		return purchaseRole6;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isPurchaseRole7() {
		return purchaseRole7;
	}

	@Column(nullable = false, length = 1)
	@Type(type = "yes_no")
	public boolean isStatus() {
		return status;
	}


	@OneToOne(cascade = CascadeType.PERSIST)
	public Contact getOldRecord() {
		return oldRecord;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public void setGroup1(boolean group1) {
		this.group1 = group1;
	}

	public void setGroup2(boolean group2) {
		this.group2 = group2;
	}

	public void setGroup3(boolean group3) {
		this.group3 = group3;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setMobTel(String mobTel) {
		this.mobTel = mobTel;
	}

	public void setEMail(String mail) {
		eMail = mail;
	}

	public void setPurchaseRole1(boolean purchaseRole1) {
		this.purchaseRole1 = purchaseRole1;
	}

	public void setPurchaseRole2(boolean purchaseRole2) {
		this.purchaseRole2 = purchaseRole2;
	}

	public void setPurchaseRole3(boolean purchaseRole3) {
		this.purchaseRole3 = purchaseRole3;
	}

	public void setPurchaseRole4(boolean purchaseRole4) {
		this.purchaseRole4 = purchaseRole4;
	}

	public void setPurchaseRole5(boolean purchaseRole5) {
		this.purchaseRole5 = purchaseRole5;
	}

	public void setPurchaseRole6(boolean purchaseRole6) {
		this.purchaseRole6 = purchaseRole6;
	}

	public void setPurchaseRole7(boolean purchaseRole7) {
		this.purchaseRole7 = purchaseRole7;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public void setOldRecord(Contact oldRecord) {
		this.oldRecord = oldRecord;
	}

	@Override
	public String toString() {
		return name;
	}

}