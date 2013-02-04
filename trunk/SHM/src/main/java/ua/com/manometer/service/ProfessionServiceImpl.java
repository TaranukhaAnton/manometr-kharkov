package ua.com.manometer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.ProfessionDAO;
import ua.com.manometer.model.Profession;

import java.util.List;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionDAO professionDAO;

    @Override
    @Transactional
    public Profession getProfession(Integer professionId) {
       return professionDAO.getProfession(professionId);
    }

    @Override
    @Transactional
    public void addProfession(Profession profession) {
        professionDAO.addProfession(profession);
    }

    @Override
    @Transactional
    public List<Profession> listProfession() {
        return professionDAO.listProfession();
    }

    @Override
    @Transactional
    public void removeProfession(Integer id) {
        professionDAO.removeProfession(id);
    }

}
