package ua.com.manometer.service.price;

import ua.com.manometer.model.price.OptionsPrice;

import java.util.List;

public interface OptionsPriceService {

	public void addOptionsPrice(OptionsPrice optionsprice);

	public List<OptionsPrice> listOptionsPrice();

	public void removeOptionsPrice(Long id);

}
