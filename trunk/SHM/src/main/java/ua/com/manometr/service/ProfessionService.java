package ua.com.manometr.service;

import ua.com.manometr.model.Profession;

import java.util.List;

public interface ProfessionService {

	public void addProfession(Profession profession);

	public List<Profession> listProfession();

	public void removeProfession(Long id);

}
