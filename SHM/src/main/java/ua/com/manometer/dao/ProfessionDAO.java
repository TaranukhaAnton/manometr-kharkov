package ua.com.manometer.dao;

import ua.com.manometer.model.Profession;

import java.util.List;

public interface ProfessionDAO {

	public void addProfession(Profession profession);

	public List<Profession> listProfession();

	public void removeProfession(Integer id);

    Profession getProfession(Integer id);
}
