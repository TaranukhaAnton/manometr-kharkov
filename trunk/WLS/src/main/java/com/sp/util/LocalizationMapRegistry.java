package com.sp.util;

import com.sp.model.LanguageId;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: andrew
 * Date: 29.10.2010
 */
public class LocalizationMapRegistry {
    private static Map<LanguageId, Map<String, String>> localizationMaps;

    private final static Logger LOGGER = Logger.getLogger(LocalizationMapRegistry.class);

    static {
        localizationMaps = new HashMap<LanguageId, Map<String, String>>();
        for (LanguageId lang : LanguageId.values()) {
            Properties properties = new Properties();
            try {
                properties.load(LocalizationMapRegistry.class.getClassLoader().getResourceAsStream("locale/"+lang+"/messages.properties"));
            } catch (IOException e) {
                LOGGER.error("LocalizationMapRegistry static block:", e);
            }
            Map<String, String> localizationMap = new HashMap<String, String>(properties.size());
            for (Object key : properties.keySet()) {
                localizationMap.put(key.toString(), properties.getProperty(key.toString()));
            }
            localizationMaps.put(lang, localizationMap);
        }
    }

    public static Map<String, String> getLocalizationMap() throws IOException {
        return null;
    }

    public static Map<String, String> getLocalizationMap(LanguageId languageId){
        return localizationMaps.get(languageId);
    }
}
