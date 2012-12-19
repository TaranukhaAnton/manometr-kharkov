package ua.com.manometer.service.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.price.OptionsPriceDAO;
import ua.com.manometer.model.price.OptionsPrice;

import java.util.List;

@Service
public class OptionsPriceServiceImpl implements OptionsPriceService {

	@Autowired
	private OptionsPriceDAO optionspriceDAO;


    @Override
    @Transactional
    public OptionsPrice getOptionsPrice(Integer type, Integer isp, String param) {
        return   optionspriceDAO.getOptionsPrice(type, isp, param);
    }

    @Override
	@Transactional
	public List<OptionsPrice> listOptionsPrice() {
		return optionspriceDAO.listOptionsPrice();
	}

	@Override
	@Transactional
	public void updateOptionsPrice(OptionsPrice optionsPrice ) {
		optionspriceDAO.updateOptionsPrice(optionsPrice);
	}

}
