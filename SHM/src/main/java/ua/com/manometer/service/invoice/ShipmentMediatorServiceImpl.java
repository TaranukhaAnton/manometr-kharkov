package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.ShipmentMediatorDAO;
import ua.com.manometer.model.invoice.ShipmentMediator;

import java.util.List;

@Service
public class ShipmentMediatorServiceImpl implements ShipmentMediatorService {

	@Autowired
	private ShipmentMediatorDAO shipmentmediatorDAO;

	@Override
	@Transactional
	public void addShipmentMediator(ShipmentMediator shipmentmediator) {
		shipmentmediatorDAO.addShipmentMediator(shipmentmediator);
	}

	@Override
	@Transactional
	public List<ShipmentMediator> listShipmentMediator() {
		return shipmentmediatorDAO.listShipmentMediator();
	}

	@Override
	@Transactional
	public void removeShipmentMediator(Integer id) {
		shipmentmediatorDAO.removeShipmentMediator(id);
	}

}
