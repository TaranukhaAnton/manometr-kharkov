package ua.com.manometer.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.address.CountryDAO;
import ua.com.manometer.model.address.Country;

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

    @Override
    @Transactional
    public Country getCountry(Long countryId) {
        return countryDAO.getCountry( countryId);
    }


}
