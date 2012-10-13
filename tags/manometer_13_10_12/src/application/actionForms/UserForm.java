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
	private String receptionoOnWorkDate;
	private String dischargingDate;
	private String tel;
	private String telMob;
	private String powersLivel;
	private String nickName;
	private String login;
	private String pass;
	
	
	
	
	
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
//        if (userName == null || userName.length() < 1) {
//            errors.add("userName", new ActionMessage("error.name.required"));
//            // TODO: add 'error.name.required' key to your resourceserrors.name.required
//        }
//        if (password == null || password.length() < 1) {
//            errors.add("password", new ActionMessage("error.password.required"));
//            // TODO: add 'error.name.required' key to your resources
//        }
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
	public String getReceptionoOnWorkDate() {
		return receptionoOnWorkDate;
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
	public String getPowersLivel() {
		return powersLivel;
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
	public void setReceptionoOnWorkDate(String receptionoOnWorkDate) {
		this.receptionoOnWorkDate = receptionoOnWorkDate;
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
	public void setPowersLivel(String powersLivel) {
		this.powersLivel = powersLivel;
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
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		  id =null;
		  name=null; // ���
		  patronymic=null; // ��������
		  lastName=null; // �������
		  position=null; // ���������
		  receptionoOnWorkDate=null; // ���� ����� �� ������
		  dischargingDate=null; // ���� ����������
		  tel=null;// �������
		  telMob=null; // ������� ���������
		  powersLivel=null; // ������� ����������
		  nickName=null; // �������� ���
		  login=null; // �����
		  pass=null;// ������
	
	}


}
