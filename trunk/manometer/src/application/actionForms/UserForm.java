package application.actionForms;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class UserForm extends ActionForm{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 4071352331663276829L;
	private Long id;
	private String name;
	private String patronymic;
	private String lastName;
	private String position;
	private String receptionOnWorkDate;
	private String dischargingDate;
	private String tel;
	private String telMob;
	private Integer powersLevel;
	private String nickName;
	private String login;
	private String pass;
    private String fioUKR;
	
	
	
	
	
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        return errors;
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
	public Integer getPowersLevel() {
		return powersLevel;
	}
	public String getNickName() {
		return nickName;
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
	public void setReceptionOnWorkDate(String receptionOnWorkDate) {
		this.receptionOnWorkDate = receptionOnWorkDate;
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
	public void setPowersLevel(Integer powersLevel) {
		this.powersLevel = powersLevel;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

    public String getFioUKR() {
        return fioUKR;
    }

    public void setFioUKR(String fioUKR) {
        this.fioUKR = fioUKR;
    }

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		  id =null;
		  name=null; // ���
		  patronymic=null; // ��������
		  lastName=null; // �������
		  position=null; // ���������
		  receptionOnWorkDate =null; // ���� ����� �� ������
		  dischargingDate=null; // ���� ����������
		  tel=null;// �������
		  telMob=null; // ������� ���������
		  powersLevel=null; // ������� ����������
		  nickName=null; // �������� ���
		  login=null; // �����
		  pass=null;// ������
	
	}


}
