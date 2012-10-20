package application.data;


import application.data.invoice.InvoiceFilter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity


public class User {
    public static Integer LEVEL_USER = 1;
    public static Integer LEVEL_MANAGER = 2;
    public static Integer LEVEL_ECONOMIST = 3;
    public static Integer LEVEL_ADMINISTRATOR = 4;


    private Long id;
    private String name; // имя
    private String patronymic; // отчество
    private String lastName; // фамилия
    private String position; // должность
    private Date receptionOnWorkDate; // дата приёма на работу
    private Date dischargingDate; // дата увольнения
    private String tel;// телефон
    private String telMob; // телефон мобильный
    private Integer powersLevel; // уровень полномочий
    private String login; // логин
    private String pass;// пароль
    private String fioUkr;


    private InvoiceFilter invoiceFilter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoicefilter_fk", nullable = true)
    public InvoiceFilter getInvoiceFilter() {
        return invoiceFilter;
    }

    public void setInvoiceFilter(InvoiceFilter invoiceFilter) {
        this.invoiceFilter = invoiceFilter;
    }


    @Override
    public String toString() {
        return login;
    }


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


    //    @Column(columnDefinition = "integer", nullable = true)
//    @Type(type = "application.hibernate.GenericEnumUserType", parameters = {
//            @Parameter(name = "enumClass", value = "application.data.User$PowersLivel"),
//            @Parameter(name = "identifierMethod", value = "toInt"),
//            @Parameter(name = "valueOfMethod", value = "fromInt")})
    public Integer getPowersLevel() {
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


    public void setPowersLevel(Integer powersLevel) {
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

    public String getFioUkr() {
        return fioUkr;
    }

    public void setFioUkr(String fioUkr) {
        this.fioUkr = fioUkr;
    }

    @Transient
    public String getPowLevStr() {

        if (powersLevel == 1) {
            return "Пользователь";
        }
        if (powersLevel == 2) {
            return "Менеджер";
        }
        if (powersLevel == 3) {
            return "Экономист";
        }
        return "Администратор";
    }

    @Transient
    public boolean isUser() {
        return 1 == powersLevel;
    }

    @Transient
    public boolean isManager() {
        return 2 == powersLevel;
    }

    @Transient
    public boolean isEconomist() {
        return 3 == powersLevel;
    }

    @Transient
    public boolean isAdmin() {
        return 4 == powersLevel;
    }


}

