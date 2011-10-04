package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.SupplierDAO;
import ua.com.manometr.model.Supplier;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDAO supplierDAO;

	@Override
	@Transactional
	public void addSupplier(Supplier supplier) {
		supplierDAO.addSupplier(supplier);
	}

	@Override
	@Transactional
	public List<Supplier> listSupplier() {
		return supplierDAO.listSupplier();
	}

	@Override
	@Transactional
	public void removeSupplier(Long id) {
		supplierDAO.removeSupplier(id);
	}

}
