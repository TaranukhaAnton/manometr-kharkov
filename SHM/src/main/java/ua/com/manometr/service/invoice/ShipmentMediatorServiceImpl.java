package ua.com.manometr.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.invoice.ShipmentMediatorDAO;
import ua.com.manometr.model.invoice.ShipmentMediator;

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
	public void removeShipmentMediator(Long id) {
		shipmentmediatorDAO.removeShipmentMediator(id);
	}

}
