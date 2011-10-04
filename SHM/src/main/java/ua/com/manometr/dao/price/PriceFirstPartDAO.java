package ua.com.manometr.dao.price;
import ua.com.manometr.model.price.PriceFirstPart;

import java.util.List;

public interface PriceFirstPartDAO {

	public void addPriceFirstPart(PriceFirstPart pricefirstpart);

	public List<PriceFirstPart> listPriceFirstPart();

	public void removePriceFirstPart(Long id);

}
