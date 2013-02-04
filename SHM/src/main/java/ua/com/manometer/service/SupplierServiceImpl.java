package ua.com.manometer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.BaseDAO;
import ua.com.manometer.dao.SupplierDAO;
import ua.com.manometer.model.Supplier;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDAO supplierDAO;

    @Autowired
    private BaseDAO baseDAO;


    @Override
    @Transactional
    public void addSupplier(Supplier supplier) {
        baseDAO.saveOrUpdate(supplier);
    }

    @Override
    @Transactional
    public List<Supplier> listSupplier() {
        return supplierDAO.listSupplier();
    }

    @Override
    @Transactional
    public void removeSupplier(Integer id) {
        supplierDAO.removeSupplier(id);
    }

    @Override
    @Transactional
    public Supplier getSupplier(Integer id) {
        return (Supplier) baseDAO.getById(id, Supplier.class);
    }

    @Override
    @Transactional
    public Supplier getDefSupplier() {
        return  supplierDAO.getDefSupplier();
    }


}
