package ua.com.manometr.dao;

import ua.com.manometr.model.Profession;

import java.util.List;

public interface ProfessionDAO {

	public void addProfession(Profession profession);

	public List<Profession> listProfession();

	public void removeProfession(Long id);

}
