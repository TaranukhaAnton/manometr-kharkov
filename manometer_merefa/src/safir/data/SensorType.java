package safir.data;


import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class SensorType {
	public String name;
	public int type;
	public int measure; // единицы измерения. Па кПа МПа
	public TreeMap<String, LinkedList<String>> limits;
	public Map<String, String> map;

	public SensorType(String initString) {
		limits = new TreeMap<String, LinkedList<String>>();
		StringTokenizer st1 = new StringTokenizer(initString, "~");
		name = st1.nextToken();
		type = Integer.parseInt(st1.nextToken());// тип 0..3
		measure = Integer.parseInt(st1.nextToken());// единицы измерения
		map = new TreeMap<String, String>();

		while (st1.hasMoreElements()) {

			StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ";");
			String lim = st2.nextToken();

			if (lim.indexOf("->") == -1)
				map.put(lim, lim);
			else {

				String[] q = lim.split("->");
				map.put(q[0], q[1]);
				lim = q[0];
			}

			LinkedList<String> set = new LinkedList<String>();
			while (st2.hasMoreElements()) {
				set.add(st2.nextToken());
			}
			limits.put(lim, set);

		}
	}
}
