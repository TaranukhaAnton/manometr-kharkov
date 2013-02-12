package com.sp.util;

/**
 * Created by Alexander
 */

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {
    private final static Logger LOGGER = Logger.getLogger(CustomRoutingDataSource.class);
    private static List<Integer> targetDataSourcesKeys = new ArrayList<Integer>();
    private static Map<Integer,String> dbInastanceIndexHost = new HashMap<Integer, String>();
    private static Map<Integer,String> dbInastanceIndexDbName = new HashMap<Integer, String>();
    public static final int DEFAULT_DB_INDEX = -1;
    private Map targetDataSourceMap;
    private BasicDataSource defaultDataSource;

    public void setTargetDataSources(Map map) {
        try {
            defaultDataSource = (BasicDataSource)map.get("default");
            Properties config = new Properties();
            config.load(CustomRoutingDataSource.class.getClassLoader().getResourceAsStream("config.properties"));
            targetDataSourceMap = new HashMap();
            Map<String,String> defaultPropMap = getDefaultMap(config);
            fillMaps(defaultPropMap,DEFAULT_DB_INDEX);
            for(int i=0;;i++){
                Map<String,String> propMap = getPropertiesMap(config,i);
                if(propMap==null){
                    break;
                }
                targetDataSourceMap.put(i,getBasicDataSource(defaultDataSource,defaultPropMap,propMap));
                fillMaps(propMap,i);
            }
            super.setTargetDataSources(targetDataSourceMap);
            targetDataSourcesKeys.addAll(targetDataSourceMap.keySet());
        } catch (IOException e) {
            LOGGER.error("setTargetDataSources():", e);
            return;
        }
    }

    private void fillMaps(Map<String,String> properties, int index){
        dbInastanceIndexHost.put(index,properties.get("host"));
        dbInastanceIndexDbName.put(index,properties.get("database"));
    }

    public static List<Integer> getTargetDataSourcesKeys() {
        return targetDataSourcesKeys;
    }
    
    private Map<String,String> getDefaultMap(Properties config){
        Map<String,String> resMap = new HashMap<String, String>();
        resMap.put("host",config.getProperty("jdbc.host"));
        resMap.put("port",config.getProperty("jdbc.port"));
        resMap.put("database",config.getProperty("jdbc.database"));
        resMap.put("username",config.getProperty("jdbc.username"));
        resMap.put("password",config.getProperty("jdbc.password"));
        return resMap;
    }

    private Map<String,String> getPropertiesMap(Properties config, int i){
        String host = config.getProperty("jdbc."+i+".host");
        if(host==null){
            return null;
        }
        Map<String,String> resMap = new HashMap<String, String>();
        resMap.put("host",host);
        resMap.put("port",config.getProperty("jdbc."+i+".port"));
        resMap.put("database",config.getProperty("jdbc."+i+".database"));
        resMap.put("username",config.getProperty("jdbc."+i+".username"));
        resMap.put("password",config.getProperty("jdbc."+i+".password"));
        return resMap;
    }

    private BasicDataSource getBasicDataSource(BasicDataSource defaultDataSource,Map<String,String> defaultMap, Map<String,String> propertiesMap){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(propertiesMap.get("username"));
        String url = defaultDataSource.getUrl().replace(defaultMap.get("host"),propertiesMap.get("host"))
                                               .replace(defaultMap.get("port"),propertiesMap.get("port"))
                                               .replace(defaultMap.get("database"),propertiesMap.get("database"));
        basicDataSource.setUrl(url);
        basicDataSource.setPassword(propertiesMap.get("password"));
        basicDataSource.setDriverClassName(defaultDataSource.getDriverClassName());
        basicDataSource.setMaxActive(defaultDataSource.getMaxActive());
        basicDataSource.setMaxWait(defaultDataSource.getMaxWait());
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setValidationQuery("select count(*) from box_type");
        return basicDataSource;
    }

    public static Map<Integer, String> getDbInastanceIndexHost() {
        return dbInastanceIndexHost;
    }

    public static Map<Integer, String> getDbInastanceIndexDbName() {
        return dbInastanceIndexDbName;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
   protected Object determineCurrentLookupKey() {
      return CustomRoutingContextHolder.getDbInstance();
   }

    public Map<Integer, BasicDataSource> getTargetDataSourceMap() {
        return targetDataSourceMap;
    }

    public BasicDataSource getDefaultDataSource() {
        return defaultDataSource;
    }
}
