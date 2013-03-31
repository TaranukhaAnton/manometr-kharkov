package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.InvoiceItemDAO;
import ua.com.manometer.dao.price.PriceFirstPartDAO;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.model.invoice.PressureSensor;
import ua.com.manometer.model.invoice.Production;
import ua.com.manometer.model.price.IdPrice;
import ua.com.manometer.model.price.OptionsPrice;
import ua.com.manometer.model.price.PriceFirstPart;
import ua.com.manometer.service.price.OptionsPriceService;
import ua.com.manometer.service.price.PriceFirstPartService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

	@Autowired
	private InvoiceItemDAO invoiceitemDAO;

    @Autowired
	private PriceFirstPartService priceFirstPartService;

    @Autowired
    private OptionsPriceService optionsPriceService;


    @Override
    @Transactional
    public InvoiceItem getInvoiceItem(Integer id) {
        return   invoiceitemDAO.getInvoiceItem(id);
    }

    @Override
	@Transactional
	public void saveInvoiceItem(InvoiceItem invoiceitem) {
		invoiceitemDAO.saveInvoiceItem(invoiceitem);
	}

	@Override
	@Transactional
	public List<InvoiceItem> listInvoiceItem() {
		return invoiceitemDAO.listInvoiceItem();
	}

	@Override
	@Transactional
	public void removeInvoiceItem(Integer id) {
		invoiceitemDAO.removeInvoiceItem(id);
	}



    //todo
    @Override

    public void setupMoneyFields(PressureSensor item ,BigDecimal koef ) {
        PriceFirstPart priceFirstPart = priceFirstPartService.getItem(new IdPrice(new Integer(item.getModel()),item.getIsp(),item.getMat(),item.getKlim(), item.getError()));

        BigDecimal cost = priceFirstPart.getCost();
        BigDecimal price = priceFirstPart.getPrice();

        Integer type = (item.getModel().charAt(0) == '3') ? 2 : (item.getModel().charAt(0) == '2') ? 1 : 0;
        Integer isp = item.getIsp();

        OptionsPrice op = optionsPriceService.getOptionsPrice(type, isp, "ou" + item.getOutType());
        cost = cost.add(op.getCost());
        price = price.add(op.getPrice());


        if (item.getKmch() != 0) {
            op = optionsPriceService.getOptionsPrice(type, isp, "H" + item.getKmch());
//            System.out.println(" kmch " + op);
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());


        }

        if (item.getDu() != 0) {
            op = optionsPriceService.getOptionsPrice(type, isp, "du" + item.getDu());
//            System.out.println("du " + op);
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());
        }
        if (item.isI()) {
            op = optionsPriceService.getOptionsPrice(type, isp, "I");
           // System.out.println("I " + op);
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());
        }
        if (item.isPI()) {
            op = optionsPriceService.getOptionsPrice(type, isp, "PI");
//            System.out.println("PI " + op);
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());
        }

        if (item.isVM()) {
            op = optionsPriceService.getOptionsPrice(type, isp, "VM");
//            System.out.println("VM " + op);
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());
        }

        if (item.isHIM()) {
            op = optionsPriceService.getOptionsPrice(type, isp, "HIM");
//            System.out.println("HIM " + op);
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());
        }
        if (item.isR()) {
            op = optionsPriceService.getOptionsPrice(type, isp, "R");
            cost = cost.add(op.getCost());
            price = price.add(op.getPrice());
        }

        op = optionsPriceService.getOptionsPrice(type, isp, "GP");
        cost = cost.add(op.getCost());
        price = price.add(op.getPrice());
        item.setCost(cost.setScale(2, RoundingMode.HALF_UP));
        item.setPrice(price.setScale(2, RoundingMode.HALF_UP));

        BigDecimal  p1 = price.divide(item.getInvoice().getExchangeRate(), 2, RoundingMode.HALF_UP).add(item.getAdditionalCost());
        BigDecimal transportationCost = item.getTransportationCost();
        item.setSellingPrice(koef.multiply(p1).add(transportationCost).setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public void setupMoneyFields(Production item, BigDecimal koef) {
        BigDecimal p1 = item.getPrice().divide(item.getInvoice().getExchangeRate(), 2, RoundingMode.HALF_UP).add(item.getAdditionalCost());
        BigDecimal transportationCost = item.getTransportationCost();
        item.setSellingPrice(koef.multiply(p1).add(transportationCost).setScale(2, RoundingMode.HALF_UP));
    }


}
