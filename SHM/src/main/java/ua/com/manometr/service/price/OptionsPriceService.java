package ua.com.manometr.service.price;

import ua.com.manometr.model.price.OptionsPrice;

import java.util.List;

public interface OptionsPriceService {

	public void addOptionsPrice(OptionsPrice optionsprice);

	public List<OptionsPrice> listOptionsPrice();

	public void removeOptionsPrice(Long id);

}
