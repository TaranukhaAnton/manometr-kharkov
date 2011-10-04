package ua.com.manometr.service.address;

import ua.com.manometr.model.address.City;

import java.util.List;

public interface CityService {

	public void addCity(City city);

	public List<City> listCity();

	public void removeCity(Long id);

}
