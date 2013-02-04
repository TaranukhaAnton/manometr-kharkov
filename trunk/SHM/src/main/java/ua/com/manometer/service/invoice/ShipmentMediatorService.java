package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.ShipmentMediator;

import java.util.List;

public interface ShipmentMediatorService {

	public void addShipmentMediator(ShipmentMediator shipmentmediator);

	public List<ShipmentMediator> listShipmentMediator();

	public void removeShipmentMediator(Integer id);

}
