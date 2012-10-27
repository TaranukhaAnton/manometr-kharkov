package ua.com.manometer.service.price;

import ua.com.manometer.model.price.PriceFirstPart;

import java.math.BigDecimal;
import java.util.List;

public interface PriceFirstPartService {

    public List<PriceFirstPart> getItems(List<Integer> models, List<Integer> err, List<Integer> mat, List<Integer> clime, List<Integer> isp);

    public void setPrice(BigDecimal cost, BigDecimal prise, List<Integer> models, List<Integer> err, List<Integer> mat, List<Integer> klim, List<Integer> isp);

    public void applyTmpValues();

    public void resetTmpValues();

    public void priceValuesToTmp();

}
