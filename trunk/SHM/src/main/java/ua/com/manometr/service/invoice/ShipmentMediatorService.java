package ua.com.manometr.service.invoice;

import ua.com.manometr.model.invoice.ShipmentMediator;

import java.util.List;

public interface ShipmentMediatorService {

	public void addShipmentMediator(ShipmentMediator shipmentmediator);

	public List<ShipmentMediator> listShipmentMediator();

	public void removeShipmentMediator(Long id);

}
