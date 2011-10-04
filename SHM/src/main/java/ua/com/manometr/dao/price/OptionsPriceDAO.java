package ua.com.manometr.dao.price;
import ua.com.manometr.model.price.OptionsPrice;

import java.util.List;

public interface OptionsPriceDAO {

	public void addOptionsPrice(OptionsPrice optionsprice);

	public List<OptionsPrice> listOptionsPrice();

	public void removeOptionsPrice(Long id);

}
