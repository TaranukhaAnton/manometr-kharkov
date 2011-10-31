package ua.com.manometr.model;

import org.hibernate.annotations.Type;
import ua.com.manometr.model.invoice.InvoiceFilter;

import javax.persistence.*;
import java.util.Date;

@Entity


public class User {
    public static Integer LIVEL_USER = 1;
    public static Integer LIVEL_MANAGER = 2;
    public static Integer LIVEL_ECONOMIST = 3;
    public static Integer LIVEL_ADMINISTRATOR = 4;


    private Long id;
    private String name; // имя
    private String patronymic; // отчество
    private String lastName; // фамилия
    private String position; // должность
    private Date receptionoOnWorkDate; // дата приёма на работу
    private Date dischargingDate; // дата увольнения
    private String tel;// телефон
    private String telMob; // телефон мобильный
    private String powersLivel; // уровень полномочий
    private String login; // логин
    private String pass;// пароль

    private InvoiceFilter invoiceFilter;

    @OneToOne
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


    @Id
    @GeneratedValue

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


    public Date getReceptionoOnWorkDate() {
        return receptionoOnWorkDate;
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


    public String getPowersLivel() {
        return powersLivel;
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


    public void setReceptionoOnWorkDate(Date receptionoOnWorkDate) {
        this.receptionoOnWorkDate = receptionoOnWorkDate;
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


    public void setPowersLivel(String powersLivel) {
        this.powersLivel = powersLivel;
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

