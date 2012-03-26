package ua.com.manometer.service;

import ua.com.manometer.model.Profession;

import java.util.List;

public interface ProfessionService {

    public Profession getProfession(Long professionId);

	public void addProfession(Profession profession);

	public List<Profession> listProfession();

	public void removeProfession(Long id);

}
