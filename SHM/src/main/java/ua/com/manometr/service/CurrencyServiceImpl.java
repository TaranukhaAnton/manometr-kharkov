package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.CurrencyDAO;
import ua.com.manometr.model.Currency;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyDAO currencyDAO;

	@Override
	@Transactional
	public void addCurrency(Currency currency) {
		currencyDAO.addCurrency(currency);
	}

	@Override
	@Transactional
	public List<Currency> listCurrency() {
		return currencyDAO.listCurrency();
	}

	@Override
	@Transactional
	public void removeCurrency(Long id) {
		currencyDAO.removeCurrency(id);
	}

}
