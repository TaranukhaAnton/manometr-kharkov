package ua.com.manometr.service.price;

import ua.com.manometr.model.price.PriceFirstPart;

import java.util.List;

public interface PriceFirstPartService {

	public void addPriceFirstPart(PriceFirstPart pricefirstpart);

	public List<PriceFirstPart> listPriceFirstPart();

	public void removePriceFirstPart(Long id);

}
