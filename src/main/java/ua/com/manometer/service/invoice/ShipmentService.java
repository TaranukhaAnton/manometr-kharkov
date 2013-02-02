package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Shipment;

import java.util.List;

public interface ShipmentService {

	public void addShipment(Shipment shipment);

	public List<Shipment> listShipment();

	public void removeShipment(Long id);

}
