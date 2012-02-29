package ua.com.manometr.util.customTypeEditors;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.manometr.model.Profession;
import ua.com.manometr.service.ProfessionService;

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
        Long id = new Long(text);
        Profession profession = professionService.getProfession(id);
        setValue(profession);
    }

    public String getAsText() {
        return ((Profession) getValue()).getId().toString();
    }

}
