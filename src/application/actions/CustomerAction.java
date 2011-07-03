package application.actions;

import application.actionForms.CustomerForm;
import application.data.Customer;
import application.data.OrgForm;
import application.data.User;
import application.data.address.Area;
import application.data.address.City;
import application.data.address.Country;
import application.data.address.Region;
import application.hibernate.Factory;
import application.hibernate.generic.CustomerDAO;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerAction extends DispatchAction {

    private Log logger = LogFactory.getLog(this.getClass());

    CustomerDAO customerDAO = Factory.getCustomerDAO();

    public ActionForward getCustomers(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null)
//            return mapping.findForward("enter");

        logger.debug("getUsers");
        populateCustomers(request);
        return mapping.findForward("customers");
    }//

    public ActionForward changeCustomer(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null)
//            return mapping.findForward("enter");

        CustomerForm customerForm = (CustomerForm) form;

        Long id = customerForm.getId();

        Customer customer = customerDAO.findById(id);
        if (customer != null)
            copyProperties(customerForm, customer);


        populateParams(request, customerForm);
        customerForm.setId(null);
        customerForm.setStatus(true);
        customerForm.setOldRecord(customerForm.getShortName());


        return mapping.findForward("customerForm");
    }//


    public ActionForward addOrgForm(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        Long id = customerForm.getId();
        if (id == null || id == 0) customerForm.setId(null);

        String newOrgForm = customerForm.getNewOrgForm();
        if (newOrgForm != null && newOrgForm.length() > 1) {
            OrgForm orgForm = new OrgForm(newOrgForm);
            customerForm.setNewOrgForm(null);

            List result = Factory.getOrgFormDAO().findByExample(orgForm, new LinkedList());
            if (result.isEmpty())
                Factory.getOrgFormDAO().makePersistent(orgForm);
        }


        populateParams(request, (CustomerForm) form);
        return mapping.findForward("customerForm");

    }//

    public ActionForward addPressureSensoruntry(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        Long id = customerForm.getId();
        if (id == null || id == 0) customerForm.setId(null);

        String newCountryName = customerForm.getNewCountry();
        if (newCountryName != null && newCountryName.length() > 1) {
            Country country = new Country(newCountryName);
            customerForm.setNewCountry(null);
            List result = Factory.getCountryDAO().findByExample(country, new LinkedList());
            if (result.isEmpty())

                Factory.getCountryDAO().makePersistent(country);
        }


        populateParams(request, (CustomerForm) form);
        return mapping.findForward("customerForm");

    }//

    public ActionForward addRegion(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        Long id = customerForm.getId();
        if (id == null || id == 0) customerForm.setId(null);

        //  Long parentId = customerForm.getCountry();
        //  if (parentId != null && parentId != 0) {
        //     Country country = Factory.getCountryDAO().findById(parentId);
        String newRegionName = customerForm.getNewRegion();
        if (newRegionName != null & newRegionName.length() > 1) {
            Region region = new Region(newRegionName);
            // region.setCountry(country);
            List result = Factory.getRegionDAO().findByExample(region, new LinkedList());
            if (result.isEmpty())
                Factory.getRegionDAO().makePersistent(region);
        }


        // }
        customerForm.setNewRegion(null);
        populateParams(request, (CustomerForm) form);
        return mapping.findForward("customerForm");
    }//
     public ActionForward addCountry(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        Long id = customerForm.getId();
        if (id == null || id == 0)
            customerForm.setId(null);

        //  Long parentId = customerForm.getCountry();
        //  if (parentId != null && parentId != 0) {
        //     Country country = Factory.getCountryDAO().findById(parentId);
        String newCountryName = customerForm.getNewCountry();
        if (StringUtils.isNotEmpty(newCountryName) ) {
            Country country = new Country(newCountryName);
            List result = Factory.getCountryDAO().findByExample(country, new LinkedList());
            if (result.isEmpty())
                Factory.getCountryDAO().makePersistent(country);
        }


        // }
        customerForm.setNewCountry(null);
        populateParams(request, (CustomerForm) form);
        return mapping.findForward("customerForm");
    }//

    public ActionForward addArea(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        Long id = customerForm.getId();
        if (id == null || id == 0) customerForm.setId(null);

        Long parentId = customerForm.getCountry();
        if (parentId != null && parentId != 0) {

            Country country = Factory.getCountryDAO().findById(parentId);
            String newAreaName = customerForm.getNewArea();
            if (country != null && newAreaName != null & newAreaName.length() > 1) {

                // Country country = new Country()
                Area area = new Area(newAreaName);
                area.setCountry(country);
                List result = Factory.getAreaDAO().findByExample(area, new LinkedList());
                if (result.isEmpty())
                    Factory.getAreaDAO().makePersistent(area);
            }


        }
        customerForm.setNewArea(null);
        populateParams(request, (CustomerForm) form);

        return mapping.findForward("customerForm");
    }//

    public ActionForward addCity(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        Long id = customerForm.getId();
        if (id == null || id == 0) customerForm.setId(null);

        Long parentId = customerForm.getArea();
        if (parentId != null && parentId != 0) {
            Area area = Factory.getAreaDAO().findById(parentId);
            String newCityName = customerForm.getNewCity();
            if (area != null && newCityName != null & newCityName.length() > 1) {
                City city = new City(newCityName);
                city.setArea(area);
                List result = Factory.getCityDAO().findByExample(city, new LinkedList());
                if (result.isEmpty())
                    Factory.getCityDAO().makePersistent(city);
            }


        }
        customerForm.setNewCity(null);
        populateParams(request, (CustomerForm) form);
        return mapping.findForward("customerForm");

    }//


    public ActionForward findContacts(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null)
//            return mapping.findForward("enter");
        logger.debug("findContacts");
        CustomerForm customerForm = (CustomerForm) form;
        Customer customer = customerDAO.findById(customerForm.getId());
        if (customer != null)

            request.getSession().setAttribute("customer", customer.getShortName());

       
        return mapping.findForward("findContacts");
    }//


    public ActionForward findCustomers(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null)
//            return mapping.findForward("enter");

        logger.debug("getUsers");
        CustomerForm customerForm = (CustomerForm) form;


//        String q = decode(request.getParameter("q"));
        String q = request.getParameter("q");
        if (q != null && q != "") {
            List<Customer> result = customerDAO.findBySortNameExample(q);
            if (result != null)
                response.setContentType("text/html; charset=UTF-8");
            for (Customer customer : result) {
                String s = customer.getShortName() + "|" + customer.getId()
                        + "\n";

                response.getWriter().write(s);
            }

        }
        return null;
    }

    /*   public ActionForward deleteCustomer(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        logger.debug("delete user");
        if (request.getParameter("id") != null
                && !request.getParameter("id").equals("")) {

            Long id = new Long(request.getParameter("id"));
            Customer customer = customerDAO.findById(id, false);
            if (customer != null)
                customerDAO.delete(customer);
        }
        populateCustomers(request);
        return mapping.findForward("customers");
    }*/

    private void populateCustomers(HttpServletRequest request) {
        request.setAttribute("customers", customerDAO.findAll());

    }

    public ActionForward insertOrUpdate(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //printProps(request);


        CustomerForm customerForm = (CustomerForm) form;
        Boolean update = isUpdate(request, customerForm);

        if (isCancelled(request)) {
            populateCustomers(request);
            return mapping.findForward("customers");
        }

        if (validationSuccessful(request, customerForm, update)) {
            if (update) {
                Customer customer = customerDAO.findById(customerForm.getId());

                copyProperties(customer, customerForm, update);
                customerDAO.makePersistent(customer);
            } else {
                Customer customer = new Customer();
                if (request.getParameter("id").equals(""))
                    customer.setId(null);

                copyProperties(customer, customerForm, update);

//                Customer example = new Customer();
//                example.setShortName(customer.getShortName());
//                example.setStatus(true);
//
//                List<String> params = Customer.params;
//                params.remove("shortName");
//                params.remove("status");


                  Map<String, Object> ex = new HashMap();
                            ex.put("shortName", customer.getShortName());
                            ex.put("status", true);

                List<Customer> list = customerDAO.findByExample(ex);
                for (Customer entity : list) {

                    entity.setStatus(false);
                    customerDAO.makePersistent(entity);

                }


                customerDAO.makePersistent(customer);
            }

            populateCustomers(request);
            return mapping.findForward("customers");
        }

        populateParams(request, customerForm);


        return mapping.findForward("customerForm");
    }

    public ActionForward setUpForInsertOrUpdate(ActionMapping mapping,
                                                ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
//        Integer livel = (Integer) request.getSession().getAttribute("livel");
//        if (livel == null)
//            return mapping.findForward("enter");

        logger.debug("setUpForInsertOrUpdate");
        CustomerForm customerForm = (CustomerForm) form;
        if (isUpdate(request, customerForm)) {
            Long id = customerForm.getId();

            Customer customer = customerDAO.findById(id);
            copyProperties(customerForm, customer);
        } else {
            customerForm.setStatus(true);
        }

        populateParams(request, customerForm);


        return mapping.findForward("customerForm");
    }

    private void populateParams(HttpServletRequest request,
                                CustomerForm customerForm) {
        GenericHibernateDAO<Country> countryDAO = Factory.getCountryDAO();
        GenericHibernateDAO<Area> areaDAO = Factory.getAreaDAO();
        GenericHibernateDAO<City> cityDAO = Factory.getCityDAO();

        Long countryId = customerForm.getCountry();
        Long areaId = customerForm.getArea();


        if (countryId != null && countryId != 0) {

            //Area sample = new Area();
            //  sample.setCountry(countryDAO.findById(countryId, false));
            Country country = countryDAO.findById(countryId);
            // List<Area> areas = areaDAO.findByExample(sample, new LinkedList<String>());
            List<Area> areas = areaDAO.findListByParameter("country", country);
            request.setAttribute("areas", areas);
        } else {
            request.setAttribute("areas", new LinkedList<Area>());
        }

        if (areaId != null && areaId != 0) {
            Area area = areaDAO.findById(areaId);
            List<City> cities = cityDAO.findListByParameter("area", area);
            request.setAttribute("cities", cities);
        } else {
            request.setAttribute("cities", new LinkedList<City>());
        }


    }

    private boolean isUpdate(HttpServletRequest request,
                             CustomerForm customerForm) {
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
                                         CustomerForm customerForm, boolean update) {

        boolean isOk = true;
        ActionMessages errors = new ActionMessages();

        if (customerForm.getNomList() == null || customerForm.getNomList() == 0) {

            errors.add("nomList", new ActionMessage("error.field.requires"));
        }
        if (!update) {
            if (customerForm.getShortName() == null
                    || customerForm.getShortName().length() < 1) {
                errors.add("shortName", new ActionMessage(
                        "error.field.requires"));
            }


            if (customerForm.getOrgForm() == null
                    || customerForm.getOrgForm() == 0) {
                errors
                        .add("orgForm", new ActionMessage(
                                "error.field.requires"));
            }

            if (customerForm.getName() == null
                    || customerForm.getName().length() < 1) {
                errors.add("name", new ActionMessage("error.field.requires"));
            }

            if (customerForm.getCountry() == null
                    || customerForm.getCountry() == 0) {
                errors
                        .add("country", new ActionMessage(
                                "error.field.requires"));
            }
            if (customerForm.getRegion() == null
                    || customerForm.getRegion() == 0) {
                errors.add("region", new ActionMessage("error.field.requires"));
            }
            if (customerForm.getArea() == null || customerForm.getArea() == 0) {
                errors.add("area", new ActionMessage("error.field.requires"));
            }

            if (customerForm.getCity() == null || customerForm.getCity() == 0) {
                errors.add("city", new ActionMessage("error.field.requires"));
            }

            if (customerForm.getHeadCustomer() != null
                    && customerForm.getHeadCustomer() != "") {

                Customer c = Factory.getCustomerDAO()
                        .findByShortName(customerForm.getHeadCustomer());
                if (c == null) {

                    errors.add("headCustomer", new ActionMessage(
                            "error.Customer.notFound"));
                }
            }


            Customer oldRecord = null;
            if (customerForm.getOldRecord() != null
                    && !customerForm.getOldRecord().equals("")) {
                oldRecord = Factory.getCustomerDAO()
                        .findByShortName(customerForm.getOldRecord());
                if (oldRecord == null) {
                    errors.add("oldRecord", new ActionMessage(
                            "error.Customer.notFound"));
                }
            }

            if (oldRecord == null) {
                if (customerForm.getShortName() != null
                        && customerForm.getShortName() != "") {

                    Customer c = Factory.getCustomerDAO()
                            .findByShortName(customerForm.getShortName());

                    if (c != null)
                        errors.add("shortName", new ActionMessage(
                                "error.shortName.notUnique"));
                }

            }


        }


        if ((customerForm.getAddress1() == null || customerForm.getAddress1()
                .length() < 1)
                && (customerForm.getAddress2() == null || customerForm
                .getAddress2().length() < 1)
                && (customerForm.getAddress3() == null || customerForm
                .getAddress3().length() < 1)) {
            errors.add("address", new ActionMessage(
                    "error.oneOfFields.requires"));
        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            isOk = false;
        }

        return isOk;
    }

    private void copyProperties(Customer customer, CustomerForm customerForm,
                                Boolean update) {
        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        if (!update) {

            GenericHibernateDAO<OrgForm> orgFormDAO = Factory.getOrgFormDAO();

            customer.setOrgForm(orgFormDAO.findById(customerForm.getOrgForm()));
            customer.setName(customerForm.getName());
            customer.setShortName(customerForm.getShortName());

            customer.setCountry(customerForm.getCountry());
            customer.setCity(customerForm.getCity());
            customer.setArea(customerForm.getArea());
            customer.setLocalityType(customerForm.getLocalityType());
            customer.setDateOfRecord(new Date());


        }
        customer.setNew(customerForm.getNew());
        customer.setNomList(customerForm.getNomList());
        if (customerForm.getHeadCustomer() != null
                && customerForm.getHeadCustomer() != "") {
            Customer c = Factory.getCustomerDAO()
                    .findByShortName(customerForm.getHeadCustomer());
            customer.setHeadCustomer(c);
        }
        if (customerForm.getOldRecord() != null
                && customerForm.getOldRecord() != "") {

            Customer oc = Factory.getCustomerDAO()
                    .findByShortName(customerForm.getOldRecord());
            customer.setOldRecord(oc);
        }

        customer.setCodeOKPO(customerForm.getCodeOKPO());
        try {
            if (customerForm.getMergeData() != null
                    && customerForm.getMergeData() != "")
                customer.setMergeData(f.parse(customerForm.getMergeData()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        customer.setRegion(customerForm.getRegion());
        customer.setRequisite(customerForm.getRequisite());
        customer.setStateProperty(customerForm.getStateProperty());
        customer.setBranch(Customer.Branch.valueOf(customerForm.getBranch()));

        // if (customerForm.getPurposeForItself() != null)
        customer.setPurposeForItself(customerForm.getPurposeForItself());
        // if (customerForm.getPurposeInstalation() != null)
        customer.setPurposeInstalation(customerForm.getPurposeInstalation());
        // if (customerForm.getPurposeIntermediary() != null)
        customer.setPurposeIntermediary(customerForm.getPurposeIntermediary());
        // if (customerForm.getPurposeSuply() != null)
        customer.setPurposeSuply(customerForm.getPurposeSuply());

        // if (customerForm.getApplicationEngineering() != null)
        customer.setApplicationEngineering(customerForm
                .getApplicationEngineering());
        // if (customerForm.getApplicationProcess() != null)
        customer.setApplicationProcess(customerForm.getApplicationProcess());
        // if (customerForm.getApplicationProduction() != null)
        customer.setApplicationProduction(customerForm
                .getApplicationProduction());
        // if (customerForm.getApplicationProject() != null)
        customer.setApplicationProject(customerForm.getApplicationProject());

        customer.setNomList(customerForm.getNomList());
        // if (customerForm.getNew() != null)
        // customer.setNew(customerForm.getNew());

        customer.setProspect(Customer.Prospect.valueOf(customerForm.getProspect()));

        customer.setAddress1(customerForm.getAddress1());
        customer.setAddress2(customerForm.getAddress2());
        customer.setAddress3(customerForm.getAddress3());

        // customer.setQuestionnaire(customerForm.getQuestionnaire());???


        // if (customerForm.getStatus() != null)
        customer.setStatus(customerForm.getStatus());

        Long id = customerForm.getPerson();
        if (id != null) {
            User u = Factory.getUserDAO().findById(id);
            customer.setPerson(u);
        }

        // customer.setRequisite(customerForm.getRequisite());???
        // customer.setRegistrationNumber(customerForm.getRegistrationNumber());???

        customer.setSite(customerForm.getSite());
        customer.setMoreInformation(customerForm.getMoreInformation());
        customer.setDateLastCorrrection(new Date());

    }

    private void copyProperties(CustomerForm customerForm, Customer customer) {
        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        customerForm.setId(customer.getId());
        customerForm.setOrgForm(customer.getOrgForm().getId());
        customerForm.setName(customer.getName());
        customerForm.setShortName(customer.getShortName());
        customerForm.setNew(customer.isNew());
        customerForm.setStateProperty(customer.getStateProperty());
        customerForm.setBranch(customer.getBranch().toString());

        customerForm.setPurposeForItself(customer.isPurposeForItself());
        customerForm.setPurposeInstalation(customer.isPurposeInstalation());
        customerForm.setPurposeIntermediary(customer.isPurposeIntermediary());
        customerForm.setPurposeSuply(customer.isPurposeSuply());

        customerForm.setApplicationEngineering(customer
                .isApplicationEngineering());
        customerForm.setApplicationProcess(customer.isApplicationProcess());
        customerForm.setApplicationProduction(customer
                .isApplicationProduction());
        customerForm.setApplicationProject(customer.isApplicationProject());

        customerForm.setNomList(customer.getNomList());
        customerForm.setNew(customer.isNew());
        customerForm.setProspect(customer.getProspect().toString());
        customerForm.setCountry(customer.getCountry());
        customerForm.setCity(customer.getCity());
        customerForm.setArea(customer.getArea());
        customerForm.setRegion(customer.getRegion());
        customerForm.setLocalityType(customer.getLocalityType());

        customerForm.setAddress1(customer.getAddress1());
        customerForm.setAddress2(customer.getAddress2());
        customerForm.setAddress3(customer.getAddress3());
        Customer headCustomer = customer.getHeadCustomer();
        if (headCustomer != null)
            customerForm.setHeadCustomer(headCustomer.getShortName());
        customerForm.setQuestionnaire(customer.getQuestionnaire());
        customerForm.setStatus(customer.isStatus());

        Customer oldRecord = customer.getOldRecord();
        if (oldRecord != null)
            customerForm.setOldRecord(oldRecord.getShortName());

        User user = customer.getPerson();
        if (user != null)
            customerForm.setPerson(user.getId());


        customerForm.setCodeOKPO(customer.getCodeOKPO());
        customerForm.setRequisite(customer.getRequisite());
        customerForm.setRegistrationNumber(customer.getRegistrationNumber());
        customerForm.setSite(customer.getSite());
        customerForm.setMoreInformation(customer.getMoreInformation());

        if (customer.getMergeData() != null)
            customerForm.setMergeData(f.format(customer.getMergeData()));

        if (customer.getDateOfRecord() != null)
            customerForm.setDateOfRecord(f.format(customer.getDateOfRecord()));

        if (customer.getDateLastCorrrection() != null)
            customerForm.setDateLastCorrection(f.format(customer
                    .getDateLastCorrrection()));

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

    public ActionForward address(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GenericHibernateDAO<Area> AreaDAO = Factory.getAreaDAO();
        GenericHibernateDAO<City> CityDAO = Factory.getCityDAO();
        GenericHibernateDAO<Country> CountryDAO = Factory.getCountryDAO();

//               printProps(request);
        String countryId = request.getParameter("country");
        String areaId = request.getParameter("area");


        StringBuffer sb = new StringBuffer();
        response.setContentType("text/html; charset=UTF-8");
        sb.append("[");
        if (countryId != null && !countryId.equals("")) {

            if (countryId.equals("")) {
                sb.append("\"\"");
            } else {
                Country country = CountryDAO.findById(new Long(countryId));
                List<Area> list = AreaDAO.findListByParameter("country", country);
                sb.append("\"\"");
                if (!list.isEmpty())
                    sb.append(",");

                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString());
                    if (i != list.size() - 1)
                        sb.append(",");
                }
            }

        } else if (areaId != null && !areaId.equals("")) {


            Area area = AreaDAO.findById(new Long(areaId));

            List<City> list = CityDAO.findListByParameter("area", area);


            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString());
                if (i != list.size() - 1)
                    sb.append(",");
            }

        }

        sb.append("]");
        String res = sb.toString();

        response.getWriter().write(res);

        return null;
    }

}
