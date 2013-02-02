package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.ShipmentMediator;

import java.util.List;

public interface ShipmentMediatorDAO {

	public void addShipmentMediator(ShipmentMediator shipmentmediator);

	public List<ShipmentMediator> listShipmentMediator();

	public void removeShipmentMediator(Long id);

}
