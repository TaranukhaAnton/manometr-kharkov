package ua.com.manometer.dao.price;
import ua.com.manometer.model.price.OptionsPrice;

import java.util.List;

public interface OptionsPriceDAO {

	public OptionsPrice getOptionsPrice(Integer type, Integer isp, String param);

	public List<OptionsPrice> listOptionsPrice();

    public void updateOptionsPrice(OptionsPrice optionsPrice);

}
