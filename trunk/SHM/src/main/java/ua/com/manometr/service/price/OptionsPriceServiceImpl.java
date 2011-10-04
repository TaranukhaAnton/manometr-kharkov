package ua.com.manometr.service.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.price.OptionsPriceDAO;
import ua.com.manometr.model.price.OptionsPrice;

import java.util.List;

@Service
public class OptionsPriceServiceImpl implements OptionsPriceService {

	@Autowired
	private OptionsPriceDAO optionspriceDAO;

	@Override
	@Transactional
	public void addOptionsPrice(OptionsPrice optionsprice) {
		optionspriceDAO.addOptionsPrice(optionsprice);
	}

	@Override
	@Transactional
	public List<OptionsPrice> listOptionsPrice() {
		return optionspriceDAO.listOptionsPrice();
	}

	@Override
	@Transactional
	public void removeOptionsPrice(Long id) {
		optionspriceDAO.removeOptionsPrice(id);
	}

}
