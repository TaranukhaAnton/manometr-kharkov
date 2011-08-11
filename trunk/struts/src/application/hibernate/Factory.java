package application.hibernate;

import application.data.*;
import application.data.ModelDescription.ModelDescription;
import application.data.address.Area;
import application.data.address.City;
import application.data.address.Country;
import application.data.address.Region;
import application.data.invoice.*;
import application.data.price.OptionsPrice;
import application.data.price.ProductionPrice;
import application.hibernate.generic.CustomerDAO;
import application.hibernate.generic.GenericHibernateDAO;
import application.hibernate.generic.PriceDAO;


public class Factory {

    static {
        SINGLETON = new Factory();
    }

    private static Factory SINGLETON;


    private GenericHibernateDAO<User> userDAO = null;
    private CustomerDAO customerDAO = null;

    private GenericHibernateDAO<Contact> contacDAO = null;
    private GenericHibernateDAO<Profession> professionDAO = null;
    private GenericHibernateDAO<Country> countryDAO = null;
    private GenericHibernateDAO<OrgForm> orgFormDAO = null;
    private GenericHibernateDAO<Region> regionDAO = null;
    private GenericHibernateDAO<Area> areaDAO = null;
    private GenericHibernateDAO<City> cityDAO = null;
//    private GenericHibernateDAO<ModelCoDescription> modelCoDescriptionDAO = null;
    //    private GenericHibernateDAO<ModelAoDescription> modelAoDescriptionDAO = null;
//    private GenericHibernateDAO<ModelOpDescription> modelOpDescriptionDAO = null;
    private GenericHibernateDAO<ModelDescription> modelDescriptionDAO = null;

    private PriceDAO priceFirstDAO = null;
    private GenericHibernateDAO<ProductionPrice> productionListDAO = null;
    private GenericHibernateDAO<OptionsPrice> optionsPriceDAO = null;
    private GenericHibernateDAO<Supplier> supplierDAO = null;
    private GenericHibernateDAO<Invoice> invoiceDAO = null;
    private GenericHibernateDAO<InvoiceItem> invoiceItemDAO = null;
    private GenericHibernateDAO<Currency> currencyDAO = null;
    private GenericHibernateDAO<Booking> bookingDAO = null;
    private GenericHibernateDAO<InvoiceFilter> invoiceFilterDAO = null;
//    private GenericHibernateDAO<Shipment> shipmentDAO = null;


//    public static synchronized Factory getSINGLETON() {
//        if (SINGLETON == null) {
//            SINGLETON = new Factory();
//        }
//        return SINGLETON;
//    }

    public Factory() {


    }


    public static synchronized GenericHibernateDAO<User>  getUserDAO() {
        if (SINGLETON.userDAO == null) {
            SINGLETON.userDAO =new GenericHibernateDAO<User>() {
            };
            //	userDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.userDAO;
    }

    public static CustomerDAO getCustomerDAO() {
        if (SINGLETON.customerDAO == null) {
            SINGLETON.customerDAO = new CustomerDAO();
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.customerDAO;
    }

    public static GenericHibernateDAO<Contact> getContacDAO() {
        if (SINGLETON.contacDAO == null) {
            SINGLETON.contacDAO = new GenericHibernateDAO<Contact>() {
            };
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.contacDAO;
    }

    public static GenericHibernateDAO<Profession> getProfessionDAO() {
        if (SINGLETON.professionDAO == null) {
            SINGLETON.professionDAO = new GenericHibernateDAO<Profession>() {
            };
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.professionDAO;
    }

    public static GenericHibernateDAO<Country> getCountryDAO() {
        if (SINGLETON.countryDAO == null) {
            SINGLETON.countryDAO = new GenericHibernateDAO<Country>() {
            };
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.countryDAO;
    }

    public static GenericHibernateDAO<OrgForm> getOrgFormDAO() {
        if (SINGLETON.orgFormDAO == null) {
            SINGLETON.orgFormDAO = new GenericHibernateDAO<OrgForm>() {
            };
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.orgFormDAO;
    }

    public static GenericHibernateDAO<Region> getRegionDAO() {
        if (SINGLETON.regionDAO == null) {
            SINGLETON.regionDAO = new GenericHibernateDAO<Region>() {
            };
        }


        return SINGLETON.regionDAO;
    }

    public static GenericHibernateDAO<City> getCityDAO() {
        if (SINGLETON.cityDAO == null) {
            SINGLETON.cityDAO = new GenericHibernateDAO<City>() {
            };
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.cityDAO;
    }

    public static GenericHibernateDAO<Area> getAreaDAO() {
        if (SINGLETON.areaDAO == null) {
            SINGLETON.areaDAO = new GenericHibernateDAO<Area>() {
            };
            //customerDAO.setSessionFactory(HibernateUtil.getSessionFactory());
        }
        return SINGLETON.areaDAO;
    }


    public static PriceDAO getPriceFirstDAO() {
        if (SINGLETON.priceFirstDAO == null)
            SINGLETON.priceFirstDAO = new PriceDAO();

        return SINGLETON.priceFirstDAO;
    }

    public static GenericHibernateDAO<ProductionPrice> getProductionDAO() {

        if (SINGLETON.productionListDAO == null)
            SINGLETON.productionListDAO = new GenericHibernateDAO<ProductionPrice>() {
            };

        return SINGLETON.productionListDAO;
    }

    public static GenericHibernateDAO<OptionsPrice> getOptionsPriceDAO() {

        if (SINGLETON.optionsPriceDAO == null)
            SINGLETON.optionsPriceDAO = new GenericHibernateDAO<OptionsPrice>() {
            };

        return SINGLETON.optionsPriceDAO;
    }


//    optionsPriceDAO

    public static GenericHibernateDAO<Supplier> getSupplierDAO() {

        if (SINGLETON.supplierDAO == null)
            SINGLETON.supplierDAO = new GenericHibernateDAO<Supplier>() {
            };


        return SINGLETON.supplierDAO;
    }

    public static GenericHibernateDAO<Invoice> getInvoiceDAO() {

        if (SINGLETON.invoiceDAO == null)
            SINGLETON.invoiceDAO = new GenericHibernateDAO<Invoice>() {
            };


        return SINGLETON.invoiceDAO;
    }


    public static GenericHibernateDAO<InvoiceItem> getInvoiceItemDAO() {


        if (SINGLETON.invoiceItemDAO == null)
            SINGLETON.invoiceItemDAO = new GenericHibernateDAO<InvoiceItem>() {
            };


        return SINGLETON.invoiceItemDAO;
    }

    public static GenericHibernateDAO<Currency> getCurrencyDAO() {


        if (SINGLETON.currencyDAO == null)
            SINGLETON.currencyDAO = new GenericHibernateDAO<Currency>() {
            };


        return SINGLETON.currencyDAO;
    }


    public static GenericHibernateDAO<ModelDescription> getModelDescriptionDAO() {

        if (SINGLETON.modelDescriptionDAO == null)
            SINGLETON.modelDescriptionDAO = new GenericHibernateDAO<ModelDescription>() {
            };


        return SINGLETON.modelDescriptionDAO;
    }

    public static GenericHibernateDAO<Booking> getBookingDAO() {

        if (SINGLETON.bookingDAO == null)
            SINGLETON.bookingDAO = new GenericHibernateDAO<Booking>() {
            };


        return SINGLETON.bookingDAO;
    }

    public static GenericHibernateDAO<InvoiceFilter> getInvoiceFilterDAO() {

        if (SINGLETON.invoiceFilterDAO == null)
            SINGLETON.invoiceFilterDAO = new GenericHibernateDAO<InvoiceFilter>() {
            };


        return SINGLETON.invoiceFilterDAO;
    }

    public static GenericHibernateDAO<ProductionPrice> getProductionListDAO() {
        if (SINGLETON.productionListDAO == null)
            SINGLETON.productionListDAO = new GenericHibernateDAO<ProductionPrice>() {
            };
        return SINGLETON.productionListDAO;
    }

    public static GenericHibernateDAO<Shipment> getShipmentDAO() {


        return new GenericHibernateDAO<Shipment>() {
        };
    }

    public static GenericHibernateDAO<Payment> getPaymentDAO() {


        return new GenericHibernateDAO<Payment>() {
        };
    }


}
