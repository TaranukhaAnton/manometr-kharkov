package ua.com.manometr.service.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.price.ProductionPriceDAO;
import ua.com.manometr.model.price.ProductionPrice;

import java.util.List;

@Service
public class ProductionPriceServiceImpl implements ProductionPriceService {

	@Autowired
	private ProductionPriceDAO productionpriceDAO;

	@Override
	@Transactional
	public void addProductionPrice(ProductionPrice productionprice) {
		productionpriceDAO.addProductionPrice(productionprice);
	}

	@Override
	@Transactional
	public List<ProductionPrice> listProductionPrice() {
		return productionpriceDAO.listProductionPrice();
	}

	@Override
	@Transactional
	public void removeProductionPrice(Long id) {
		productionpriceDAO.removeProductionPrice(id);
	}

}
