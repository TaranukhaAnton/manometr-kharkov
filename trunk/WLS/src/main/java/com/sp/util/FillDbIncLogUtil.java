package com.sp.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Alexander
 */

public class FillDbIncLogUtil {

    private static final int IMEI_INDEX = 0;
    private static final int TIMESTEMP_INDEX = 1;
    private static final int LAT_INDEX = 100;
    private static final int LON_INDEX = 100;
    private static final int ANGLE_INDEX = 3;
    private static final int SPEED_INDEX = 2;
    //private static final int ID_INDEX = 8;
    private static final int DIST_INDEX = 6;
    private static final int IGNITION_INDEX = 6;

   //public static final int START_PARSE_INDEX = 1;
    public static final String lineSeparator = System.getProperty("line.separator");

    private static String inPath = "f:\\temp\\export\\tithegrovelimited_records.csv";
    private static String outPath = "c:\\Temp\\gisglos_table\\gisglos_table_date_subset_prepared.csv";
    private static BufferedOutputStream dataStream;


    public static void main(String[] args) throws IOException {
        //makeSubset();
        //fillSql();
        printImeiSet();
    }

    public static void makeSubset() throws IOException {
         dataStream = new BufferedOutputStream(new FileOutputStream(outPath,false), 1048576);
         BufferedReader br = new BufferedReader(new FileReader(inPath));
         String line;
        List<String> lineList = new ArrayList<String>();
         while ((line = br.readLine()) != null) {
              String[] items = line.split(",");
              Date date = new Date(Long.valueOf(items[TIMESTEMP_INDEX]));

              if(date.before(new Date())){
                  lineList.add(line);
              //if(items[IMEI_INDEX].equals("352848027240437") && ){
                  //resLine=getDateStrByTimestemp(items[TIMESTEMP_INDEX]) + "," + items[IGNITION_INDEX] + "," + items[SPEED_INDEX];
//                  line = line.replaceFirst(items[TIMESTEMP_INDEX],getDateStrByTimestemp(items[TIMESTEMP_INDEX]));
//                  writeToFile(line + lineSeparator);
              }

              //}
          }
          Collections.sort(lineList, new CompareLinesByDate());
          String prevLine=null;
          for(String l : lineList){
              String[] items = l.split(",");
              if(prevLine!=null){
                  String[] prevItems = prevLine.split(",");
                  if(get5MinFutureDate(getDate(prevItems[TIMESTEMP_INDEX])).before(getDate(items[TIMESTEMP_INDEX]))){
                      writeToFile("MORE THEN 5 MIN BREAK" + lineSeparator);
                  }
                  if(!items[IGNITION_INDEX].equals(prevItems[IGNITION_INDEX])){
                      writeToFile("IGNITION CHANGED" + lineSeparator);
                  }
              }
              writeToFile(getDateStrByTimestemp(items[TIMESTEMP_INDEX]) + "," + items[IGNITION_INDEX] + "," + items[SPEED_INDEX] + lineSeparator);
              //writeToFile(l.replaceFirst(items[TIMESTEMP_INDEX],getDateStrByTimestemp(items[TIMESTEMP_INDEX])) + lineSeparator);
              prevLine = l;
          }
    }

    private static Date get5MinFutureDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE,5);
        return cal.getTime();
    }

    static class CompareLinesByDate implements Comparator {
       public int compare(Object a, Object b) {
           String[] aLine = a.toString().split(",");
           String[] bLine = b.toString().split(",");
           return getDate(aLine[TIMESTEMP_INDEX]).compareTo(getDate(bLine[TIMESTEMP_INDEX]));
       }
    }





    private static final String sqlInsert = "INSERT INTO incoming_log " +
                "(speed,direction_device," +
                "Voltage,NodeID,lon," +
                "lat,rec_date,UnitisinMotion," +
                "input1High,input2High,Dout1Active," +
                "Dout2Active,journey_id,StreetName," +
                "created_date,unit_id,is_journey_start," +
                "satellites,distance_from_last_msg,poi_descr," +
                "is_journey_end,road_lat,road_lon," +
                "distance_to_road,processing_status,postcode," +
                "acceleration,deceleration,box_type_id," +
                "idling,device_max_journey_speed,is_ignition_active," +
                "tracking_device_id,input3High,input4High," +
                "input5High,input6High,aoi_descr," +
                "distance_from_last_msg_calc,in_stealth_mode,received_date," +
                "direction_calc,journey_distance,vehicle_odometer," +
                "Dout3Active,Dout4Active,dallas_key_code," +
                "trakm8_message_type,harsh_acceleration,harsh_braking," +
                "harsh_cornering,overspeed_start,overspeed_end," +
                "overspeed,speed_limit) VALUES ";
    private static void fillSql() throws IOException{
        dataStream = new BufferedOutputStream(new FileOutputStream(outPath,false), 1048576);
        BufferedReader br = new BufferedReader(new FileReader(inPath));
        String line;
        br.readLine();
        int index = 1;
        writeToFile(sqlInsert);
        
        while ((line = br.readLine()) != null) {
            String[] items = line.split(",");
            insertIncLog(items,index);
            index = index%300==0 ? 1 : index+1;
        }
        writeToFile(";");
        br.close();
        dataStream.close();
    }

    private static void insertIncLog(String[] items, int index){
        if(items.length<11){
            System.out.println("Wrong record index = " + index);
            return;
        }

        String imei = items[IMEI_INDEX];
        String recDate = getDateStrByTimestemp(items[TIMESTEMP_INDEX]);
        String lat = items[LAT_INDEX];
        String lon = items[LON_INDEX];
        String angel = items[ANGLE_INDEX];
        String speed = items[SPEED_INDEX];
        String dist = items[DIST_INDEX];
        String ignition = items[IGNITION_INDEX];
        String sql = (index > 1 ? "," : " ") +
                "(<speed>,<direction>," +
                "777777,'<imei>',<lon>," +
                "<lat>,'<rec_date>',0," +
                "<is_ignition_active>,0,0," +
                "0,NULL,NULL," +
                "'<rec_date>',<unit_id>,0," +
                "7,<distance_from_last_msg>,NULL," +
                "0,NULL,NULL," +
                "NULL,NULL,NULL," +
                "NULL,NULL,16," +
                "NULL,0,<is_ignition_active>," +
                "<imei_id>,0,0," +
                "0,0,NULL," +
                "NULL,0,'<rec_date>'," +
                "0,NULL,NULL," +
                "0,0,NULL,NULL,0,0,0,0,0,0,NULL)";
        if(index%300==0){
            sql+=";"+sqlInsert;
        }
        writeToFile(sql.replaceFirst("<speed>",speed)
                    .replaceFirst("<direction>",angel)
                    .replaceFirst("<imei>",imei)
                    .replaceFirst("<lon>",lon)
                    .replaceFirst("<lat>",lat)
                    .replaceFirst("<distance_from_last_msg>",dist)
                    .replaceAll("<rec_date>",recDate)
                    .replaceAll("<is_ignition_active>",ignition)
                    .replaceFirst("<unit_id>",String.valueOf(FillDbConstants.imeiUnitMap.get(imei)))
                    .replaceFirst("<imei_id>",String.valueOf(FillDbConstants.imeiMap.get(imei))));
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String getDateStrByTimestemp(String tt){
        return dateFormat.format(getDate(tt));
    }

    private static Date getDate(String tt){
        return new Date(Long.valueOf(tt.trim()));
    }

    private static void printImeiSet() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inPath));
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            FillDbConstants.imeiSet.add(FillDbConstants.imeiUnitMap.get(line.split(",")[IMEI_INDEX].trim()) + "");
        }
        br.close();
        writeToConsole(FillDbConstants.imeiSet.toString());
    }


    private static void writeToFile(String str){
        try {
            dataStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(str);
    }

    private static void writeToConsole(String str){
        System.out.println(str);
    }

}
