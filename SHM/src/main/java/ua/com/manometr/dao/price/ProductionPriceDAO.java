package ua.com.manometr.dao.price;
import ua.com.manometr.model.price.ProductionPrice;

import java.util.List;

public interface ProductionPriceDAO {

	public void addProductionPrice(ProductionPrice productionprice);

	public List<ProductionPrice> listProductionPrice();

	public void removeProductionPrice(Long id);

}
