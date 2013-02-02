package ua.com.manometer.service.address;

import ua.com.manometer.model.address.Country;

import java.util.List;

public interface CountryService {

	public void addCountry(Country country);

	public List<Country> listCountry();


}
