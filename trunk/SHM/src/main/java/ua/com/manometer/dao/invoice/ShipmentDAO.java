package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Shipment;

import java.util.List;

public interface ShipmentDAO {

	public void addShipment(Shipment shipment);

	public List<Shipment> listShipment();

	public void removeShipment(Integer id);

}
