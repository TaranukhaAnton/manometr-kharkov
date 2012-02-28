package ua.com.manometr.model;

import ua.com.manometr.model.invoice.InvoiceFilter;

import javax.persistence.*;
import java.util.Date;

@Entity


public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name; // имя
    private String patronymic; // отчество
    private String lastName; // фамилия
    private String position; // должность
    private Date receptionOnWorkDate; // дата приёма на работу
    private Date dischargingDate; // дата увольнения
    private String tel;// телефон
    private String telMob; // телефон мобильный
    private String powersLevel; // уровень полномочий
    private String login; // логин
    private String pass;// пароль
    @OneToOne
    private InvoiceFilter invoiceFilter;


    public InvoiceFilter getInvoiceFilter() {
        return invoiceFilter;
    }

    public void setInvoiceFilter(InvoiceFilter invoiceFilter) {
        this.invoiceFilter = invoiceFilter;
    }

    //    @OneToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    private InvoiceFilter invoiceFilter;
//
//    public InvoiceFilter getInvoiceFilter() {
//        return invoiceFilter;
//    }
//
//    public void setInvoiceFilter(InvoiceFilter invoiceFilter) {
//        this.invoiceFilter = invoiceFilter;
//    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getPatronymic() {
        return patronymic;
    }


    public String getLastName() {
        return lastName;
    }


    public String getPosition() {
        return position;
    }


    public Date getReceptionOnWorkDate() {
        return receptionOnWorkDate;
    }


    public Date getDischargingDate() {
        return dischargingDate;
    }


    public String getTel() {
        return tel;
    }


    public String getTelMob() {
        return telMob;
    }


    public String getPowersLevel() {
        return powersLevel;
    }


//	public String getNickName() {
//		return nickName;
//	}


    public String getLogin() {
        return login;
    }


    public String getPass() {
        return pass;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setPosition(String position) {
        this.position = position;
    }


    public void setReceptionOnWorkDate(Date receptionOnWorkDate) {
        this.receptionOnWorkDate = receptionOnWorkDate;
    }


    public void setDischargingDate(Date dischargingDate) {
        this.dischargingDate = dischargingDate;
    }


    public void setTel(String tel) {
        this.tel = tel;
    }


    public void setTelMob(String telMob) {
        this.telMob = telMob;
    }


    public void setPowersLevel(String powersLevel) {
        this.powersLevel = powersLevel;
    }


//	public void setNickName(String nickName) {
//		this.nickName = nickName;
//	}


    public void setLogin(String login) {
        this.login = login;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }
}

