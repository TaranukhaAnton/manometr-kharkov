package application.actions;

import application.data.Supplier;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class SupplierAction extends DispatchAction {
    public ActionForward viewSuppliers(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("suppliers");
    }


    public ActionForward editSupplier(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("edit");
    }


    public ActionForward saveSupplier(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //  printProps(request);
        Supplier s;//= ;
//        GenericHibernateDAO<Supplier> dao = Factory.getSuplierDAO();
        try {
            String param = request.getParameter("id");
//            if (param != null) if (!param.isEmpty())
//                s.setId(new Long(param));
            s = ((param != null) && (!param.isEmpty())) ? Factory.getSupplierDAO().findById(new Long(param)) : new Supplier();


            s.setBankBeneficiaryASS(request.getParameter("bankBeneficiaryASS"));
            s.setName(request.getParameter("name"));
            s.setTs(request.getParameter("ts"));
            s.setInnkpp(request.getParameter("innkpp"));
            s.setMfoBank(request.getParameter("mfoBank"));
            s.setBankBeneficiarySWIFT(request.getParameter("bankBeneficiarySWIFT"));
            s.setFIOChief(request.getParameter("FIOchief"));
            s.setBankMediatorName(request.getParameter("bankMediatorName"));
            s.setInn(request.getParameter("inn"));
            s.setAddress(request.getParameter("address"));
            s.setCurrency(Factory.getCurrencyDAO().findById(new Long(request.getParameter("currency"))));
            s.setErdpou(request.getParameter("edrpou"));
            s.setPhone(request.getParameter("phone"));
            s.setBankBeneficiaryAddress(request.getParameter("bankBeneficiaryAddress"));
            s.setRs(request.getParameter("rs"));
            s.setBankMediatorSWIFT(request.getParameter("bankMediatorSWIFT"));
            s.setBank(request.getParameter("bank"));
            s.setBankMediatorAddress(request.getParameter("bankMediatorAddress"));
            s.setTaxationScheme(request.getParameter("taxationScheme"));
            s.setOkpo(request.getParameter("okpo"));
            s.setAlias(request.getParameter("alias"));
            s.setChief(request.getParameter("chief"));
            s.setBankBeneficiaryName(request.getParameter("bankBeneficiaryName"));
            s.setBIC(request.getParameter("BIC"));
            s.setNDSPayerNom(request.getParameter("NDSPayerNom"));
            s.setKs(request.getParameter("ks"));
            s.setOgrn(request.getParameter("ogrn"));
            s.setLogo(request.getParameter("logo"));
            s.setNDSPayerNom(request.getParameter("NDSPayerNom"));
            s.setLanguage(request.getParameter("language"));
            s.setDefault_row(true);
            Factory.getSupplierDAO().makePersistent(s);

        } catch (Exception e) {
            e.printStackTrace();

        }


//        request.getMethod();
//            mapping.setInclude("sad");
//       mapping.setParameter("dfsd");
//        response.addHeader("method","viewSuplier");
        return mapping.findForward("ok");
    }


    public ActionForward deleteSupplier(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        GenericHibernateDAO<Supplier> dao = Factory.getSupplierDAO();
        try {
            Supplier s = dao.findById(new Long(request.getParameter("id")));
            dao.delete(s);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mapping.findForward("ok");
    }
}
