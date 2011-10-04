package ua.com.manometr.service;

import ua.com.manometr.model.Currency;

import java.util.List;

public interface CurrencyService {

	public void addCurrency(Currency currency);

	public List<Currency> listCurrency();

	public void removeCurrency(Long id);

}
