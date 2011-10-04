package ua.com.manometr.service.invoice;

import ua.com.manometr.model.invoice.Shipment;

import java.util.List;

public interface ShipmentService {

	public void addShipment(Shipment shipment);

	public List<Shipment> listShipment();

	public void removeShipment(Long id);

}
