package com.sp.util;

import java.util.List;

/**
 * Created by Alexander
 */
public class CustomRoutingContextHolder {

   private static final ThreadLocal<Integer> contextHolder = new ThreadLocal<Integer>();

   public static void setDbInstance(Integer instance) {
       if(instance== CustomRoutingDataSource.DEFAULT_DB_INDEX){
           clearAdditionalDataSources();
       }else{
            contextHolder.set(instance);
       }    
   }

   public static Integer getDbInstance() {
      return contextHolder.get();
   }

   public static void clearAdditionalDataSources() {
      contextHolder.remove();
   }

   public static List<Integer> getDbInstancesIndexList(){
       return CustomRoutingDataSource.getTargetDataSourcesKeys();
   }

   public static int getDbInstanceIndex(){
       return getDbInstance()==null ? CustomRoutingDataSource.DEFAULT_DB_INDEX : getDbInstance();
   }

    public static String getCurHost() {
        return CustomRoutingDataSource.getDbInastanceIndexHost().get(getDbInstanceIndex());
    }

    public static String getCurDbName() {
        return CustomRoutingDataSource.getDbInastanceIndexDbName().get(getDbInstanceIndex());
    }

}
