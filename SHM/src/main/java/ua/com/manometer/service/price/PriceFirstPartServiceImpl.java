package ua.com.manometer.service.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.price.PriceFirstPartDAO;
import ua.com.manometer.model.price.IdPrice;
import ua.com.manometer.model.price.PriceFirstPart;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceFirstPartServiceImpl implements PriceFirstPartService {

    @Autowired
    private PriceFirstPartDAO pricefirstpartDAO;


    @Override
    @Transactional
    public List<PriceFirstPart> getItems(List<Integer> models, List<Integer> err, List<Integer> mat, List<Integer> clime, List<Integer> isp) {
        return pricefirstpartDAO.getItems(models, err, mat, clime, isp);

    }

    @Override
    @Transactional
    public void setPrice(BigDecimal cost, BigDecimal prise, List<Integer> models, List<Integer> err, List<Integer> mat, List<Integer> klim, List<Integer> isp) {
        pricefirstpartDAO.setPrice(cost, prise, models, err, mat, klim, isp);
    }

    @Override
    @Transactional
    public void applyTmpValues() {
        pricefirstpartDAO.applyTmpValues();
    }

    @Override
    @Transactional
    public void resetTmpValues() {
        pricefirstpartDAO.resetTmpValues();
    }

    @Override
    @Transactional
    public void priceValuesToTmp() {
        pricefirstpartDAO.priceValuesToTmp();
    }

    @Override
    @Transactional
    public PriceFirstPart getItem(IdPrice id) {
        int klim = id.getKlim();
        id.setKlim((klim < 3) ? 0 : (klim < 6) ? 1 : 2);
        PriceFirstPart item = pricefirstpartDAO.getItem(id);
        return item;
    }

    @Override
    public Boolean modelAvailableToSell(IdPrice id) {
        PriceFirstPart item = getItem(id);
        // проверяет что прайс больше нуля
        return item.getPrice().compareTo(new BigDecimal("0")) == 1;
    }
}
