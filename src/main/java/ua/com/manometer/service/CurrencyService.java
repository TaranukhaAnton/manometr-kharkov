package ua.com.manometer.service;

import ua.com.manometer.model.Currency;

import java.util.List;

public interface CurrencyService {

	public void addCurrency(Currency currency);

	public List<Currency> listCurrency();

	public void removeCurrency(Long id);

}
