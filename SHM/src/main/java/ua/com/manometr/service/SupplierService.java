package ua.com.manometr.service;

import ua.com.manometr.model.Supplier;

import java.util.List;

public interface SupplierService {

	public void addSupplier(Supplier supplier);

	public List<Supplier> listSupplier();

	public void removeSupplier(Long id);

}
