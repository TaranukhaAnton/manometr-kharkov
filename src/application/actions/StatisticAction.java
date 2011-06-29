package application.actions;

import application.data.invoice.Invoice;
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


public class StatisticAction extends DispatchAction {
    public ActionForward viewPaymentArrears(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GenericHibernateDAO<Invoice> factory = Factory.getInvoiceDAO();
        Map<String, List> query = new HashMap();
        List<Integer> values = new LinkedList<Integer>();
        values.add(Invoice.STATE_ACT);
        query.put("currentState",values);
        List<Invoice> result = factory.findByQuery(new HashMap(), new HashMap(), new HashMap(), query);
        request.setAttribute("invoices", result);
        return mapping.findForward("paymentArrears");
    }

}
