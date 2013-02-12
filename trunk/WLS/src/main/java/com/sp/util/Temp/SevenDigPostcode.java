package com.sp.util.Temp;

import java.io.*;

/**
 * Created by Alexander
 */
public class SevenDigPostcode {
    private static String sqlInsert = "insert into postcode_seven_digits (postcode,lat,lon) values ";
    private static BufferedOutputStream dataStream;
    private static String inPath = "d:\\temp\\ForGIS postcode points Q1-2012, NI.csv";
    private static String outPath = "d:\\temp\\NI_insert.sql";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inPath));
        String header = br.readLine();
        String line;
        int index = 1;
        dataStream = new BufferedOutputStream(new FileOutputStream(outPath,false), 1048576);
        writeToFile(sqlInsert);
        while((line = br.readLine())!=null){
            index = insertPostcard(line,index);

        }
        br.close();
        dataStream.close();
        System.out.print("FINISH");
    }

    private static int insertPostcard(String line, int index){
        String[] items = line.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append(index > 1 ? "," : " ");
        builder.append("(");
        builder.append(items[0].replaceAll("\"","'"));
        builder.append(",");
        builder.append(items[5]);
        builder.append(",");
        builder.append(items[6]);
        builder.append(")");
        if(index%300==0){
            builder.append(";");
            builder.append(sqlInsert);
        }
        if (items[2].equals("\"Y\"")) {
            writeToFile(builder.toString());
            return index%300==0 ? 1 : index+1;
        }
        return index;
    }

    private static void writeToFile(String str){
        try {
            dataStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        System.out.println(str);
    }

}
