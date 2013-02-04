package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.Country;

import java.util.List;

public interface CountryDAO {

	public void addCountry(Country country);

	public List<Country> listCountry();

    public Country getCountry(Integer countryId);
}
