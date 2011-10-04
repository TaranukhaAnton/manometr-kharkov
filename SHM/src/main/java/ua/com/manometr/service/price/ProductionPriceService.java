package ua.com.manometr.service.price;

import ua.com.manometr.model.price.ProductionPrice;

import java.util.List;

public interface ProductionPriceService {

	public void addProductionPrice(ProductionPrice productionprice);

	public List<ProductionPrice> listProductionPrice();

	public void removeProductionPrice(Long id);

}
