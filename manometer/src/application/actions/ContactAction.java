package application.actions;

import application.actionForms.ContactForm;
import application.data.Contact;
import application.data.Customer;
import application.data.Profession;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAction extends DispatchAction {

    private Log logger = LogFactory.getLog(this.getClass());

    GenericHibernateDAO<Contact> contactDAO = Factory.getContacDAO();

    public ActionForward getContacts(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer level = (Integer) request.getSession().getAttribute("level");
//        if (level == null)
//            return mapping.findForward("enter");


        logger.debug("getContacts");
        String param = request.getParameter("show");
        if (param != null && param.equals("all"))
            request.getSession().removeAttribute("customer");



        String customerShortName = (String) request.getSession().getAttribute("customer");

     //   System.out.println(customerShortName);

        if (customerShortName != null) {
     //             System.out.println("not null");
            populateContacts(request, customerShortName);

        } else {
     //       System.out.println("null");
            populateContacts(request);
        }

        // }
        return mapping.findForward("contacts");
    }


    public ActionForward addProfession(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ContactForm contactForm = (ContactForm) form;
        Long id = contactForm.getId();
        if (id == null || id == 0) contactForm.setId(null);


        Profession profession = new Profession(contactForm.getNewProfession());
        contactForm.setNewProfession(null);
        List result = Factory.getProfessionDAO().findListByParameter("name",contactForm.getNewProfession());
                //findByExample(profession, new LinkedList());
        if (result.isEmpty()) {
            Factory.getProfessionDAO().makePersistent(profession);}

        return mapping.findForward("contactForm");
    }



    private void populateContacts(HttpServletRequest request, String customerShortName) {
//        List<String> params = Customer.params;
//        params.remove("shortName");
//        params.remove("status");


//        Customer example = new Customer();
//        example.setShortName(customerShortName);
//        example.setStatus(true);
        Map<String, Object> ex = new HashMap();
                            ex.put("shortName", customerShortName);
                            ex.put("status", true);



        List<Customer> list = Factory.getCustomerDAO().findByExample(ex);
        if (!list.isEmpty())
            request.setAttribute("customer", list.get(0));


      //  params = Contact.params;
      //  params.remove("customer");

       // Contact contact = new Contact();
        //contact.setCustomer(customerShortName);

        List<Contact> result = contactDAO.findListByParameter("customer",customerShortName);


        request.setAttribute("contacts", result);


    }

    private void populateContacts(HttpServletRequest request) {
        request.setAttribute("contacts", contactDAO.findAll());

    }

    public ActionForward insertOrUpdate(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer level = (Integer) request.getSession().getAttribute("level");
//        if (level == null)
//            return mapping.findForward("enter");

        ContactForm contactForm = (ContactForm) form;
        Boolean update = isUpdate(request, contactForm);
        String customerShortName = (String) request.getSession().getAttribute("customer");


        Contact contact;
        if (isCancelled(request)) {

            return mapping.findForward("contacts");
        }

        if (validationSuccessful(request, contactForm, update)) {

            if (update) {
                contact = contactDAO.findById(contactForm.getId());
            } else {
                contact = new Contact();
                if (request.getParameter("id").equals(""))
                    contact.setId(null);
            }
            copyProperties(contact, contactForm, update);
            contactDAO.makePersistent(contact);

            String customer = contact.getCustomer();
            if (customerShortName != null && customer != null) {

                request.getSession().setAttribute("customer", customer);

            }

            return mapping.findForward("contacts");
        }

        if (update) {
            contact = contactDAO.findById(contactForm.getId());
            contactForm.setName(contact.getName());
            contactForm.setLastName(contact.getLastName());
            contactForm.setPatronymic(contact.getPatronymic());
        } else {
            contactForm.setId(null);
        }

        return mapping.findForward("contactForm");
    }

    public ActionForward setUpForInsertOrUpdate(ActionMapping mapping,
                                                ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
//        Integer level = (Integer) request.getSession().getAttribute("level");
//        if (level == null)
//            return mapping.findForward("enter");

        logger.debug("setUpForInsertOrUpdate");
        ContactForm contactForm = (ContactForm) form;
        boolean update = isUpdate(request, contactForm);
        if (update) {
            Long id = contactForm.getId();

            Contact contact = contactDAO.findById(id);
            copyProperties(contactForm, contact, update);
        } else {
            String customerShortName = (String) request.getSession().getAttribute("customer");

            if (customerShortName != null) {

//                Customer customer = Factory.getCustomerDAO()
//                        .findById(id);

//                if (customer != null)
                    contactForm.setCustomer(customerShortName);

            }
            contactForm.setStatus(true);
        }
        //   populateParams(request, contactForm);

        return mapping.findForward("contactForm");
    }

    private void populateParams(HttpServletRequest request,
                                ContactForm customerForm) {
//		GenericHibernateDAO<Profession> professionDAO = new GenericHibernateDAO<Profession>() {
//		};
//		request.setAttribute("professions", professionDAO.findAll());

    }

    private boolean isUpdate(HttpServletRequest request,
                             ContactForm customerForm) {
        boolean updateFlag = true;

        /*
           * if ID is null or 0 we know we are doing an insert. You could check
           * other things to decide, like a dispatch param It's annoying that
           * BeanUtils will convert nulls to 0 so have to do 0 check also, or you
           * could register a converter, which is the preferred way to handle it,
           * but goes beyond this demo
           */
        Long id = customerForm.getId();

        if (id == null || id.intValue() == 0) {
            updateFlag = false;
        } else if (request.getParameter("id") != null
                && !request.getParameter("id").equals("")) {
            customerForm.setId(new Long(request.getParameter("id")));
        }
        // request.setAttribute("updateFlag", Boolean.valueOf(updateFlag));
        return updateFlag;
    }

    private boolean validationSuccessful(HttpServletRequest request,
                                         ContactForm contactForm, boolean update) {

        boolean isOk = true;
        ActionMessages errors = new ActionMessages();
        if (!update) {
            if (contactForm.getPatronymic() == null || contactForm.getPatronymic().trim().length() == 0)
                errors.add("patronymic", new ActionMessage("error.field.requires"));

            if (contactForm.getName() == null || contactForm.getName().trim().length() == 0)
                errors.add("name", new ActionMessage("error.field.requires"));

            if (contactForm.getLastName() == null || contactForm.getLastName().trim().length() == 0)
                errors.add("lastName", new ActionMessage("error.field.requires"));
        }


        if (contactForm.getTel() == null || contactForm.getTel().trim().length() == 0)
            errors.add("tel", new ActionMessage("error.field.requires"));
        if (contactForm.getSubdivision() == null || contactForm.getSubdivision().trim().length() == 0)
            errors.add("subdivision", new ActionMessage("error.field.requires"));
        if (contactForm.getProfession() == null || contactForm.getProfession() == 0)
            errors.add("profession", new ActionMessage("error.field.requires"));
        if (contactForm.getCustomer() == null || contactForm.getCustomer().trim().length() == 0)
            errors.add("customer", new ActionMessage("error.field.requires"));
        if (contactForm.getCustomer() != null && contactForm.getCustomer().trim().length() > 0) {
//            List<String> params = Customer.params;
//            params.remove("shortName");
//            params.remove("status");


             Map<String, Object> ex = new HashMap();
                            ex.put("shortName", contactForm.getCustomer());
                            ex.put("status", true);

//            Customer example = new Customer();
//            example.setShortName(contactForm.getCustomer());
//            example.setStatus(true);
            List<Customer> list = Factory.getCustomerDAO().findByExample(ex);


            if (list.isEmpty())
                errors.add("customer", new ActionMessage("error.Customer.notFound"));
        }


        if (!contactForm.isGroup1() && !contactForm.isGroup2() && !contactForm.isGroup3())
            errors.add("group", new ActionMessage("error.oneOfFields.requires"));

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            isOk = false;
        }

        return isOk;
    }

    private void copyProperties(Contact contact, ContactForm contactForm,
                                Boolean update) {
        if (!update) { 
            contact.setName(contactForm.getName());
            contact.setLastName(contactForm.getLastName());
            contact.setPatronymic(contactForm.getPatronymic());
        }

        contact.setSubdivision(contactForm.getSubdivision());
        contact.setTel(contactForm.getTel());
        contact.setFax(contactForm.getFax());
        contact.setMobTel(contactForm.getMobTel());
        contact.setEMail(contactForm.getEMail());
        contact.setStatus(contactForm.isStatus());
        contact.setGroup1(contactForm.isGroup1());
        contact.setGroup2(contactForm.isGroup2());
        contact.setGroup3(contactForm.isGroup3());
        contact.setPurchaseRole1(contactForm.isPurchaseRole1());
        contact.setPurchaseRole2(contactForm.isPurchaseRole2());
        contact.setPurchaseRole3(contactForm.isPurchaseRole3());
        contact.setPurchaseRole4(contactForm.isPurchaseRole4());
        contact.setPurchaseRole5(contactForm.isPurchaseRole5());
        contact.setPurchaseRole6(contactForm.isPurchaseRole6());
        contact.setPurchaseRole7(contactForm.isPurchaseRole7());


        contact.setCustomer(contactForm.getCustomer());


        Profession profession = Factory.getProfessionDAO()
                .findById(contactForm.getProfession());

        contact.setProfession(profession);

    }

    private void copyProperties(ContactForm contactForm, Contact contact, boolean Update) {

        contactForm.setId(contact.getId());
        contactForm.setName(contact.getName());
        contactForm.setLastName(contact.getLastName());
        contactForm.setPatronymic(contact.getPatronymic());

        contactForm.setSubdivision(contact.getSubdivision());
        contactForm.setTel(contact.getTel());
        contactForm.setFax(contact.getFax());
        contactForm.setMobTel(contact.getMobTel());
        contactForm.setEMail(contact.getEMail());
        contactForm.setStatus(contact.isStatus());
        contactForm.setGroup1(contact.isGroup1());
        contactForm.setGroup2(contact.isGroup2());
        contactForm.setGroup3(contact.isGroup3());
        contactForm.setPurchaseRole1(contact.isPurchaseRole1());
        contactForm.setPurchaseRole2(contact.isPurchaseRole2());
        contactForm.setPurchaseRole3(contact.isPurchaseRole3());
        contactForm.setPurchaseRole4(contact.isPurchaseRole4());
        contactForm.setPurchaseRole5(contact.isPurchaseRole5());
        contactForm.setPurchaseRole6(contact.isPurchaseRole6());
        contactForm.setPurchaseRole7(contact.isPurchaseRole7());


        contactForm.setCustomer(contact.getCustomer());
        Profession profession = contact.getProfession();
        if (profession != null)
            contactForm.setProfession(profession.getId());

    }



    private String decode(String s) {
        String result = null;
        String ENCODING_iso_8859_1 = "ISO-8859-1";
        String ENCODING_UTF8 = "UTF-8";

        try {
            result = new String(s.getBytes(ENCODING_iso_8859_1), ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return result;
    }

}
