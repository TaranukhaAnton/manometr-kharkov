package ua.com.manometer.dao;

import ua.com.manometer.model.Currency;

import java.util.List;
public interface CurrencyDAO {

	public void addCurrency(Currency currency);

	public List<Currency> listCurrency();

	public void removeCurrency(Long id);

}