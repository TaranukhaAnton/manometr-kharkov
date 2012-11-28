package ua.com.manometer.service;

import ua.com.manometer.model.Supplier;

import java.util.List;

public interface SupplierService {

	public void addSupplier(Supplier supplier);

	public List<Supplier> listSupplier();

	public void removeSupplier(Long id);

    public Supplier getSupplier(Long id);
}
