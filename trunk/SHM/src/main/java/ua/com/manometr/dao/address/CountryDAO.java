package ua.com.manometr.dao.address;
import ua.com.manometr.model.address.Country;

import java.util.List;

public interface CountryDAO {

	public void addCountry(Country country);

	public List<Country> listCountry();

	public void removeCountry(Long id);

}
