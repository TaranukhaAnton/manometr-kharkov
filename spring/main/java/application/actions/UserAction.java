/*
package application.actions;

import application.actionForms.UserForm;
import ua.com.manometr.model.User;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction extends DispatchAction {
    private Log logger = LogFactory.getLog(this.getClass());

    // private static UserService userService = new UserService();
    GenericHibernateDAO<User> userDAO = Factory.getUserDAO();

    public ActionForward getUsers(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null || livel != 4)
//            return mapping.findForward("enter");

        logger.debug("getUsers");
        populateUsers(request);
        return mapping.findForward("users");
    }

    public ActionForward deleteUser(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null || livel != 4)
//            return mapping.findForward("enter");

        logger.debug("delete user");
        if (request.getParameter("id") != null
                && !request.getParameter("id").equals("")) {

            Long id = new Long(request.getParameter("id"));
            User user = userDAO.findById(id);
            if (user != null)
                try {
                    userDAO.delete(user);
                } catch (Exception e) {

                }

        }
        populateUsers(request);
        return mapping.findForward("users");
    }

    private void populateUsers(HttpServletRequest request) {

        request.setAttribute("users", userDAO.findAll());
        // request.setAttribute("users", new LinkedList<User>());
    }

    public ActionForward insertOrUpdate(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        UserForm userForm = (UserForm) form;
        boolean update = isUpdate(request, userForm);

        if (isCancelled(request)) {
            populateUsers(request);
            return mapping.findForward("users");
        }

        if (validationSuccessful(request, userForm, update)) {


            User user = (update) ? (userDAO.findById(new Long(userForm.getId()))) : (new User());

            copyProperties(user, userForm);


            userDAO.makePersistent(user);


            populateUsers(request);
            return mapping.findForward("users");
        }

        return mapping.findForward("userForm");
    }

    public ActionForward setUpForInsertOrUpdate(ActionMapping mapping,
                                                ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null || livel != 4)
//            return mapping.findForward("enter");
//        logger.debug("setUpForInsertOrUpdate");
        //  System.out.println("UserAction.setUpForInsertOrUpdate");
        UserForm userForm = (UserForm) form;
        if (isUpdate(request, userForm)) {
            Long id = userForm.getId();
            // User user = userService.getUser(id);
            User user = userDAO.findById(id);
            copyProperties(userForm, user);
        }


        return mapping.findForward("userForm");
    }

    private boolean isUpdate(HttpServletRequest request, UserForm userForm) {
        boolean updateFlag = true;

        */
/*
           * if ID is null or 0 we know we are doing an insert. You could check
           * other things to decide, like a dispatch param It's annoying that
           * BeanUtils will convert nulls to 0 so have to do 0 check also, or you
           * could register a converter, which is the preferred way to handle it,
           * but goes beyond this demo
           *//*

        Long id = userForm.getId();
        // Integer id = 100;
        if (id == null || id.intValue() == 0) {
            updateFlag = false;
        } else if (request.getParameter("id") != null
                && !request.getParameter("id").equals("")) {
            userForm.setId(new Long(request.getParameter("id")));
        }
        // request.setAttribute("updateFlag", Boolean.valueOf(updateFlag));
        return updateFlag;
    }

    private boolean validationSuccessful(HttpServletRequest request,
                                         UserForm userForm, boolean update) {

        boolean isOk = true;
        ActionMessages errors = new ActionMessages();

        if (userForm.getLastName() == null || userForm.getLastName().trim().length() == 0)
            errors.add("lastName", new ActionMessage("error.field.requires"));

        if (userForm.getTel() == null || userForm.getTel().trim().length() == 0)
            errors.add("tel", new ActionMessage("error.field.requires"));

        if (userForm.getLogin() == null || userForm.getLogin().trim().length() == 0)
            errors.add("login", new ActionMessage("error.field.requires"));

        if (userForm.getPass() == null || userForm.getPass().trim().length() == 0)
            errors.add("pass", new ActionMessage("error.field.requires"));


        if (!update && userForm.getLogin() != null && userForm.getLogin().trim().length() != 0) {

            //   User user = new User();
            //  user.setLogin();
            //   List<String> param = User.params;
            //  param.remove("login");

            Map<String, Object> exam = new HashMap();
            exam.put("login", userForm.getLogin());
            //
            List<User> result = userDAO.findByExample(exam);

            /// List<User> result = Factory.getUserDAO().findByExample(user,param);
            if (!result.isEmpty())
                errors.add("login", new ActionMessage(
                        "error.shortName.notUnique"));
        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            isOk = false;
        }

        return isOk;
    }

    private void copyProperties(User user, UserForm userForm) {
        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        // user.setId(userForm.getId());
        user.setLastName(userForm.getLastName());
        user.setName(userForm.getName());
        user.setPatronymic(userForm.getPatronymic());
        user.setPosition(userForm.getPosition());

        try {
            if (userForm.getReceptionOnWorkDate() != "")
                user.setReceptionOnWorkDate(f.parse(userForm
                        .getReceptionOnWorkDate()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            if (userForm.getDischargingDate() != "")
                user.setDischargingDate(f.parse(userForm.getDischargingDate()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        user.setTel(userForm.getTel());
        user.setTelMob(userForm.getTelMob());
        //   System.out.println(userForm.getPowersLivel());

        user
                .setPowersLivel(User.PowersLivel.valueOf(userForm
                        .getPowersLivel()));
        //   user.setNickName(userForm.getNickName());
        user.setLogin(userForm.getLogin());
        user.setPass(userForm.getPass());

    }

    private void copyProperties(UserForm userForm, User user) {
        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        userForm.setId(user.getId());
        userForm.setLastName(user.getLastName());
        userForm.setName(user.getName());
        userForm.setPatronymic(user.getPatronymic());
        userForm.setPosition(user.getPosition());
        if (user.getReceptionOnWorkDate() != null)
            userForm.setReceptionOnWorkDate(f.format(user
                    .getReceptionOnWorkDate()));
        if (user.getDischargingDate() != null)
            userForm.setDischargingDate(f.format(user.getDischargingDate()));
        userForm.setTel(user.getTel());
        userForm.setTelMob(user.getTelMob());
        userForm.setPowersLivel(user.getPowersLivel().toString());
        //  userForm.setNickName(user.getNickName());
        userForm.setLogin(user.getLogin());
        userForm.setPass(user.getPass());

    }



}
*/
