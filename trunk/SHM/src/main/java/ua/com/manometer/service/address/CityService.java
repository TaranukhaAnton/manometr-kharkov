package ua.com.manometer.service.address;

import ua.com.manometer.model.address.City;

import java.util.List;

public interface CityService {

	public void addCity(City city);

	public List<City> listCity();

	public void removeCity(Long id);

    public  List<City> listCityForArea(Long areaId);
}
