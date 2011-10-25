package ua.com.manometr.webbeans;


import java.text.ParseException;
import java.text.SimpleDateFormat;

public class User {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private Long id;
    private String name; // имя
    private String patronymic; // отчество
    private String lastName; // фамилия
    private String position; // должность
    private String receptionOnWorkDate; // дата приёма на работу
    private String dischargingDate; // дата увольнения
    private String tel;// телефон
    private String telMob; // телефон мобильный
    private String powersLevel; // уровень полномочий
    private String login; // логин
    private String pass;// пароль

    public ua.com.manometr.model.User toDBUser(ua.com.manometr.model.User user) throws ParseException {
        user.setName(name);
        user.setPatronymic(patronymic);
        user.setLastName(lastName);
        user.setPosition(position);
        user.setReceptionoOnWorkDate(sdf.parse(receptionOnWorkDate));
        user.setDischargingDate(sdf.parse(dischargingDate));
        user.setTel(tel);
        user.setTelMob(telMob);
        user.setPowersLivel(powersLevel);
        user.setLogin(login);
        user.setPass(pass);
        return user;
    }

    public void fromDBUser(ua.com.manometr.model.User user) throws ParseException {
        id = user.getId();
        name = user.getName();
        patronymic = user.getPatronymic();
        lastName = user.getLastName();
        position = user.getPosition();
        receptionOnWorkDate = sdf.format(user.getReceptionoOnWorkDate());
        dischargingDate = sdf.format(user.getDischargingDate());
        tel = user.getTel();
        telMob = user.getTelMob();
        powersLevel = user.getPowersLivel();
        login = user.getLogin();
        pass = user.getPass();
    }


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

    public String getReceptionOnWorkDate() {
        return receptionOnWorkDate;
    }

    public String getDischargingDate() {
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

    public void setReceptionOnWorkDate(String receptionoOnWorkDate) {
        this.receptionOnWorkDate = receptionoOnWorkDate;
    }

    public void setDischargingDate(String dischargingDate) {
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

