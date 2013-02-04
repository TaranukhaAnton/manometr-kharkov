package ua.com.manometer.dao;

import ua.com.manometer.model.Supplier;

import java.util.List;

public interface SupplierDAO {

	public void addSupplier(Supplier supplier);

	public List<Supplier> listSupplier();

	public void removeSupplier(Integer id);

    public  Supplier getDefSupplier();
}


