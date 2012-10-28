package ua.com.manometer.service.price;

import ua.com.manometer.model.price.ProductionPrice;

import java.util.List;

public interface ProductionPriceService {

	public void addProductionPrice(ProductionPrice productionprice);

	public List<ProductionPrice> listProductionPriceByType(Integer type);

	public void removeProductionPrice(Long id);



}
