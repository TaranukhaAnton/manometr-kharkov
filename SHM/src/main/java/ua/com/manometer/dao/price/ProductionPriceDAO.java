package ua.com.manometer.dao.price;
import ua.com.manometer.model.price.ProductionPrice;

import java.util.List;

public interface ProductionPriceDAO {

	public void addProductionPrice(ProductionPrice productionprice);

	public List<ProductionPrice> listProductionPriceByType(Integer type);

	public void removeProductionPrice(Integer id);

}
