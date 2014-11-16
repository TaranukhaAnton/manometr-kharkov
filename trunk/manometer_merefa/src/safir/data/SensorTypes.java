package safir.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class SensorTypes {
	public LinkedList<SensorType> sensorTypes;
	public static final SensorTypes INSTANSE = new SensorTypes();	
	Logger log;
	
	private SensorTypes() {
		log = Logger.getLogger(this.getClass());
		sensorTypes = new LinkedList<SensorType>();

	  //  XMLEncoder e=null;
//	    try{
//
//	        e = new XMLEncoder( new BufferedOutputStream(new FileOutputStream("Test.xml")));
//	    } catch(Exception q)
//	    {
//	         System.err.println(q.getMessage());
//	    }
//
//	       e.writeObject(bean);
//	       e.close();
		
		
	//	String s;
		try {
			//InputStream is = SensorTypes.class.getResourceAsStream("SensorTypes.txt");
			InputStream is = new FileInputStream("SensorTypes.txt");

	//		BufferedReader in = new BufferedReader(new FileReader(
		//			CommonConstants.SensorTypesPath));
			// e = new XMLEncoder( new BufferedOutputStream(new FileOutputStream("Test.xml")));
			Scanner scanner = new Scanner(is);
//			while ((s = in.readLine()) != null) {
//				SensorType st =new SensorType(s) ;
//				sensorTypes.add(st);
//			//	e.writeObject(st);
//			}
			while (scanner.hasNext()) {
				sensorTypes.add(new SensorType(scanner.nextLine()));
				
			}

			
		} catch (Exception er) {
			log.error("Проблемы с чтением файла SensorTypes.txt"+ er);// TODO Auto-generated catch block
			er.printStackTrace();
			
		}

		
		
		
		
	}

}
