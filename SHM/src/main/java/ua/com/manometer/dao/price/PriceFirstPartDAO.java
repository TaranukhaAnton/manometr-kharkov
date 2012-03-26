package ua.com.manometer.dao.price;
import ua.com.manometer.model.price.PriceFirstPart;

import java.util.List;

public interface PriceFirstPartDAO {

	public void addPriceFirstPart(PriceFirstPart pricefirstpart);

	public List<PriceFirstPart> listPriceFirstPart();

	public void removePriceFirstPart(Long id);

}
