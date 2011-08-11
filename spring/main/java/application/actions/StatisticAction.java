package application.actions;

import ua.com.manometer.model.invoice.Invoice;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static application.utils.CommonUtils.getCurrentDate;
import static application.utils.InvoiceUtils.filterInvoicesWithDebts;


public class StatisticAction extends DispatchAction {
    public ActionForward viewInvoicesWithDebts(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GenericHibernateDAO<Invoice> factory = Factory.getInvoiceDAO();
        Map<String, List> query = new HashMap();
        List<Integer> values = new LinkedList<Integer>();
        values.add(Invoice.STATE_ZAK);
        query.put("currentState", values);
        List<Invoice> result = factory.findByQuery(new HashMap(), new HashMap(), new HashMap(), query);
        result = filterInvoicesWithDebts(result, getCurrentDate());
        request.setAttribute("invoices", result);
        return mapping.findForward("paymentArrears");
    }

}
