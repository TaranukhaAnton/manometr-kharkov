package ua.com.manometer.service.price;

import ua.com.manometer.model.price.PriceFirstPart;

import java.util.List;

public interface PriceFirstPartService {

	public void addPriceFirstPart(PriceFirstPart pricefirstpart);

	public List<PriceFirstPart> listPriceFirstPart();

	public void removePriceFirstPart(Long id);

}
