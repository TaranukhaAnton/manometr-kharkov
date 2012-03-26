package ua.com.manometer.dao.price;
import ua.com.manometer.model.price.OptionsPrice;

import java.util.List;

public interface OptionsPriceDAO {

	public void addOptionsPrice(OptionsPrice optionsprice);

	public List<OptionsPrice> listOptionsPrice();

	public void removeOptionsPrice(Long id);

}
