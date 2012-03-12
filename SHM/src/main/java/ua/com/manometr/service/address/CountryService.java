package ua.com.manometr.service.address;

import ua.com.manometr.model.address.Country;

import java.util.List;

public interface CountryService {

	public void addCountry(Country country);

	public List<Country> listCountry();

	public void removeCountry(Long id);

    public  Country getCountry(Long countryId);
}
