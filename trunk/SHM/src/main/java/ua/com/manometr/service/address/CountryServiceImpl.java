package ua.com.manometr.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.address.CountryDAO;
import ua.com.manometr.model.address.Country;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDAO countryDAO;

	@Override
	@Transactional
	public void addCountry(Country country) {
		countryDAO.addCountry(country);
	}

	@Override
	@Transactional
	public List<Country> listCountry() {
		return countryDAO.listCountry();
	}

	@Override
	@Transactional
	public void removeCountry(Long id) {
		countryDAO.removeCountry(id);
	}

}
