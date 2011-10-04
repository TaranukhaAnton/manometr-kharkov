package ua.com.manometr.service.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.price.PriceFirstPartDAO;
import ua.com.manometr.model.price.PriceFirstPart;

import java.util.List;

@Service
public class PriceFirstPartServiceImpl implements PriceFirstPartService {

	@Autowired
	private PriceFirstPartDAO pricefirstpartDAO;

	@Override
	@Transactional
	public void addPriceFirstPart(PriceFirstPart pricefirstpart) {
		pricefirstpartDAO.addPriceFirstPart(pricefirstpart);
	}

	@Override
	@Transactional
	public List<PriceFirstPart> listPriceFirstPart() {
		return pricefirstpartDAO.listPriceFirstPart();
	}

	@Override
	@Transactional
	public void removePriceFirstPart(Long id) {
		pricefirstpartDAO.removePriceFirstPart(id);
	}

}
