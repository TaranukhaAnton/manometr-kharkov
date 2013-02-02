package ua.com.manometer.service.price;

import ua.com.manometer.model.price.OptionsPrice;

import java.util.List;

public interface OptionsPriceService {

	public OptionsPrice getOptionsPrice(Integer type, Integer isp, String param );

	public List<OptionsPrice> listOptionsPrice();

	public void updateOptionsPrice(OptionsPrice optionsPrice );

}
