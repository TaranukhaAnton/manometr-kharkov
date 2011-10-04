package ua.com.manometr.dao.address;
import ua.com.manometr.model.address.City;

import java.util.List;

public interface CityDAO {

	public void addCity(City city);

	public List<City> listCity();

	public void removeCity(Long id);

}
