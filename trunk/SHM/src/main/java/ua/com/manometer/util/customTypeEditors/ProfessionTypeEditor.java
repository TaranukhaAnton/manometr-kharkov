package ua.com.manometer.util.customTypeEditors;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.manometer.model.Profession;
import ua.com.manometer.service.ProfessionService;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: 29.02.12
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
public class ProfessionTypeEditor extends PropertyEditorSupport {
    @Autowired
    ProfessionService professionService;

    public void setAsText(String text) throws IllegalArgumentException {
        Integer id = new Integer(text);
        Profession profession = professionService.getProfession(id);
        setValue(profession);
    }

    public String getAsText() {
        return ((Profession) getValue()).getId().toString();
    }

}
