package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.ShipmentDAO;
import ua.com.manometer.model.invoice.Shipment;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private ShipmentDAO shipmentDAO;

	@Override
	@Transactional
	public void addShipment(Shipment shipment) {
		shipmentDAO.addShipment(shipment);
	}

	@Override
	@Transactional
	public List<Shipment> listShipment() {
		return shipmentDAO.listShipment();
	}

	@Override
	@Transactional
	public void removeShipment(Integer id) {
		shipmentDAO.removeShipment(id);
	}

}
