package com.sp.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexander
 */
public class FillDbJourneysUtil {
    private static final int IMEI_INDEX = 0;
    private static final int START_TIMESTEMP_INDEX = 2;
    private static final int END_TIMESTEMP_INDEX = 3;
    private static final int LAT_INDEX = 4;
    private static final int LON_INDEX = 5;
    private static final String journeyInsertSql = "INSERT IGNORE INTO `journey` (`unit_id`, `start_date`, `end_date`, `max_speed`, `end_log_id`, `start_street_name`, `end_street_name`, `driver_id`, `distance`, `start_log_id`, `idling`, `start_city`, `end_city`, `start_poi_descr`, `end_poi_descr`, `start_postcode`, `end_postcode`, `max_acceleration`, `max_deceleration`, `device_max_journey_speed`, `box_type_id`, `device_odometer`, `start_aoi_descr`, `end_aoi_descr`, `mpg`, `in_stealth_mode`, `end_lat`, `end_lon`, `harsh_acceleration`, `harsh_braking`, `harsh_cornering`, `excessive_speed`) VALUES ";
    public static final String lineSeparator = System.getProperty("line.separator");

    private static String inPath = "f:\\Temp\\export\\skmodernheatingltd_driving_table.csv";
    private static String outPath = "f:\\Temp\\driving\\skmodernheatingltd_driving_table_out.sql";

    private static BufferedOutputStream dataStream;


    public static void main(String[] args) throws IOException {
        fillSql();
    }

    private static void fillSql() throws IOException{
        dataStream = new BufferedOutputStream(new FileOutputStream(outPath,false), 1048576);
        BufferedReader br = new BufferedReader(new FileReader(inPath));
        String line;
        br.readLine();
        int index = 1;
        int count = 0;
        writeToFile(journeyInsertSql);
        while ((line = br.readLine()) != null) {
            if (insertJourney(line,index)) {
                index = index%300==0 ? 1 : index+1;
                count++;
            }
        }
        writeToFile(";");
        System.out.println(count);
        br.close();
        dataStream.close();
    }

    private static boolean insertJourney(String line, int index){
        String[] items = line.split(",");
        if(items.length<7){
            System.out.println("Wrong record index = " + index);
            return false;
        }
        String imei = items[IMEI_INDEX];
        String startDate = getDateStrByTimestemp(items[START_TIMESTEMP_INDEX]);
        String endDate = getDateStrByTimestemp(items[END_TIMESTEMP_INDEX]);
        String lat = items[LAT_INDEX];
        String lon = items[LON_INDEX];
        String dist = getDistance(line);
        String location = getLocation(line);

        String sql = (index > 1 ? "," : " ") +
            "(<unit_id>, '<start_date>','<end_date>'," +
            " 0, NULL, NULL, " +
            " \"<location>\", NULL, <distance>, " +
            " NULL, 0, 111, " +
            " NULL, NULL, NULL, " +
            " NULL, NULL, NULL," +
            " NULL, 0, 16, " +
            " NULL, NULL, NULL, " +
            " NULL, 0, <end_lat>, " +
            " <end_lon>, 0, 0, " +
            " 0, 0)";
        if(index%300==0){
            sql+=";"+journeyInsertSql;
        }
        if (FillDbConstants.imeiUnitMap.get(imei) != null) {
            writeToFile(sql.replaceFirst("<unit_id>",String.valueOf(FillDbConstants.imeiUnitMap.get(imei)))
                        .replaceFirst("<start_date>",startDate)
                        .replaceFirst("<end_date>",endDate)
                        .replaceFirst("<location>",location)
                        .replaceFirst("<distance>",dist)
                        .replaceFirst("<end_lon>",lon)
                        .replaceFirst("<end_lat>",lat));

            return true;
        }
        return false;
    }

    private static String getLocation(String line){
        String lon = line.split(",")[LON_INDEX];
        return line.substring(line.indexOf(lon)+lon.length()+1,line.lastIndexOf(","));
    }

    
    private static String getDistance(String line){
        String result = "0";
        try {
            result =  Math.round(Double.valueOf(line.substring(line.lastIndexOf(",")+1,line.length())) * 1000) + "";
        } catch(NumberFormatException e) {

        }
        return result;
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String getDateStrByTimestemp(String tt){
        return dateFormat.format(getDate(tt));
    }

    private static Date getDate(String tt){
        return new Date(Long.valueOf(tt.trim()));
    }

    private static void writeToFile(String str){
        try {
            dataStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(str);
    }
}
