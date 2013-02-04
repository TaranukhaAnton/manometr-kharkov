package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.City;

import java.util.List;

public interface CityDAO {



    public  List<City> listCityForArea(Integer areaId);
}
