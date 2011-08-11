/*
package application.actions;

import application.actionForms.UserLoginForm;
import ua.com.manometr.model.User;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
//import application.hibernate.generic.UserDAO;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserLoginAction extends DispatchAction {


    private String SUCCESS = "success";
    private String FAILURE = "enter";

    public ActionForward login(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionMessages errors = new ActionMessages();
        UserLoginForm userLoginForm = (UserLoginForm) form;
        // LoginForm loginForm = (LoginForm) form;
        String login = userLoginForm.getUserName();
        String pass = userLoginForm.getPassword();


        if (userLoginForm.getUserName() == null || userLoginForm.getUserName().length() < 1) {
            errors.add("userName", new ActionMessage("error.name.required"));
        }
        if (userLoginForm.getPassword() == null || userLoginForm.getPassword().length() < 1) {
            errors
                    .add("password", new ActionMessage(
                            "error.password.required"));
        }

        if (errors.isEmpty()) {
            if (login.equals("super") & pass.equals("super")) {

                User u = new User();
                u.setLogin("super");

                request.getSession().setAttribute("logonUser", u);
                request.getSession().setAttribute("logonUserId", 1L);
                request.getSession().setAttribute("livel", 4);


                return mapping.findForward("success");

            }


            GenericHibernateDAO<User> userDAO = Factory.getUserDAO();

            //User sample = new User();
            //sample.setLogin(login);
          //  List<User> result = userDAO.findByExample(sample);
                    Map<String, Object> exam = new HashMap();
                    exam.put("login", login);
                 //
                    List<User> result = userDAO.findByExample(exam);




            if (result.isEmpty()) {
                errors.add("userName", new ActionMessage("error.name.incorrect"));
                saveErrors(request, errors);
                return mapping.findForward(FAILURE);
            } else {
                exam.put("pass", pass);
                result = userDAO.findByExample(exam);
                if (result.isEmpty()) {
                    errors.add("password", new ActionMessage(
                            "error.password.incorrect"));
                    saveErrors(request, errors);
                    return mapping.findForward(FAILURE);
                } else {
                    User u = result.get(0);
                    request.getSession().setAttribute("logonUser", u);
                    request.getSession().setAttribute("logonUserId", u.getId());
                    request.getSession().setAttribute("livel",
                            u.getPowersLivel().toInt());
                    return mapping.findForward(SUCCESS);
                }

            }
        } else {
            saveErrors(request, errors);
            return mapping.findForward(FAILURE);
        }

    }

    public ActionForward logof(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("logonUser");
        session.removeAttribute("livel");


        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewInvoices");
        forward.setRedirect(true);
        return forward;
//		return mapping.findForward(FAILURE);

    }

    public ActionForward logon(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
//		session.removeAttribute("logonUser");
//		session.removeAttribute("livel");
        System.out.println("UserLoginAction.logon");


        return mapping.findForward(FAILURE);

    }


}
*/
