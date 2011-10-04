package ua.com.manometr.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.invoice.ShipmentDAO;
import ua.com.manometr.model.invoice.Shipment;

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
	public void removeShipment(Long id) {
		shipmentDAO.removeShipment(id);
	}

}
