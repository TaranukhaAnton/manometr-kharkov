package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.Shipment;

import java.util.List;

public interface ShipmentDAO {

	public void addShipment(Shipment shipment);

	public List<Shipment> listShipment();

	public void removeShipment(Long id);

}
