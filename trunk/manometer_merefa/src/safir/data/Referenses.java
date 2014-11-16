package safir.data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class Referenses {
	public Map<Double, List<Double>> map;
	public static final Referenses INSTANSE = new Referenses();
	Logger log;

	private Referenses() {
		map = new HashMap<Double, List<Double>>();

		log = Logger.getLogger(this.getClass());
	//	String s;
		try {
			InputStream is = Referenses.class.getResourceAsStream("REFERENCE.txt");
			Scanner scanner = new Scanner(is);


			while (scanner.hasNext()) {

				StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), ";");
				String lim = tokenizer.nextToken();
				List<Double> l = new LinkedList<Double>();
				while (tokenizer.hasMoreElements()) {
					l.add(new Double(tokenizer.nextToken()));
				}
				map.put(new Double(lim), l);
			}
		} catch (Exception e) {
			log.error("Проблемы с чтением файла REFERENCE.txt", e);
			System.err.println(e);
		}
	}
}
