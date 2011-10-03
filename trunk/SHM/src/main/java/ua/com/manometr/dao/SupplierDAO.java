package ua.com.manometr.dao;

import ua.com.manometr.model.Supplier;

import java.util.List;

public interface SupplierDAO {

	public void addSupplier(Supplier supplier);

	public List<Supplier> listSupplier();

	public void removeSupplier(Long id);
}


