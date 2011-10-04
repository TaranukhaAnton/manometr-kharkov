package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.ShipmentMediator;

import java.util.List;

public interface ShipmentMediatorDAO {

	public void addShipmentMediator(ShipmentMediator shipmentmediator);

	public List<ShipmentMediator> listShipmentMediator();

	public void removeShipmentMediator(Long id);

}
