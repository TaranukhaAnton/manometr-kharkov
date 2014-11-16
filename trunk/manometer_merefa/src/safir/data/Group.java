package safir.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import safir.bdUtils.DAO;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

@SuppressWarnings("serial")
public class Group extends DefaultMutableTreeNode{
//	private static Set<Integer> numbers;// номера групп.

	private int groupNum;
	private float minPressure;// *
	private float maxPressure;// *
	private int MinTemp;// *
	private int MaxTemp;// *
	private int PointsP;// *
	private int PointsT;// *
	private int Type;
	private int TermType;
	private int Camera;//
	private String note;
	private List<Double> pressures;
	private List<Double> temperatures;
	// private int T23;
	private int baseRow = -1;

	private boolean avail = true;
	private Integer KA = 1;
	private Integer K0 = 1;
	private Integer[] flags;
	private Integer dataWrote = 1;
	private String measure;
	

	// private int state;

//	static {
//		numbers = new HashSet<Integer>();
//		for (int i = 1; i < 127; i++) {
//			numbers.add(new Integer(i));
//		}
//
//	}
//
//	private int getFreeNumber() {
//		Integer in = numbers.iterator().next();
//		numbers.remove(in);
//		return in;
//	}

	public Group() {
		// ListAdrSens=new Vector<Integer>();
	//	groupNum = getFreeNumber();
	}

	public Group(int groupNum) {
		this.groupNum = groupNum;
	//	numbers.remove(groupNum);
	}

	public void deleteGroup() {
	//	numbers.add(groupNum);
		try {
			DAO.INSTANSE.deleteGroup(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sensorsStateScan() {
		if (SerialConnection.INSTANSE.isOpen()) {
			for (Sensor sensor : getListSensors())
				sensor.StateScan();
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(groupNum);
		buffer.append(". ");
		buffer.append(SensorTypes.INSTANSE.sensorTypes.get(Type).name + " ");

/*		switch (SensorTypes.INSTANSE.sensorTypes.get(Type).measure) {
		case 0:
			buffer.append(maxPressure + " Па");
			break;
		case 1:
			buffer.append(maxPressure + " кПа");
			break;
		case 2:
			buffer.append(maxPressure + " МПа");
			break;
		}*/
		buffer.append(maxPressure + " "+getStrMeasure());

		switch (TermType) {
		case 0:
			buffer.append(" У2 ");
			break;
		case 1:
			buffer.append(" УХЛ 3.1 ");
			break;

		case 2:
			buffer.append(" Т3 ");
			break;

		default:
			break;
		}

		if (note != null)
			buffer.append("\n" + note);
		return buffer.toString();
	}

	public int getCamera() {
		return Camera;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public float getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(float minPressure) {
		this.minPressure = minPressure;
	}

	public float getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(float maxPressure) {
		this.maxPressure = maxPressure;
	}

	public int getMinTemp() {
		return MinTemp;
	}

	public void setMinTemp(int minTemp) {
		MinTemp = minTemp;
	}

	public int getMaxTemp() {
		return MaxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		MaxTemp = maxTemp;
	}

	public int getPointsP() {
		return PointsP;
	}

	public void setPointsP(int pointsP) {
		PointsP = pointsP;
	}

	public int getPointsT() {
		return PointsT;
	}

	public void setPointsT(int pointsT) {
		PointsT = pointsT;
	}

	public void setCamera(int camera) {
		Camera = camera;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Double> getPressures() {
		if (pressures==null)
			pressures = computePressures(minPressure, maxPressure, PointsP);
		return pressures;
	}

//	public void setPressures(List<Double> pressures) {
//	//	this.pressures = pressures;
//	}

	public List<Double> getTemperatures() {
		if (temperatures==null) 
			temperatures = computeTemperatures(MinTemp, MaxTemp, PointsT);
		return temperatures;
	}

//	public void setTemperatures(List<Double> temperatures) {
//	//	this.temperatures = temperatures;
//	}

	/**
	 * @return the t23
	 */
	public int getT23() {
		if (temperatures==null) 
			temperatures = computeTemperatures(MinTemp, MaxTemp, PointsT);
		
		return temperatures.indexOf(23.);
	}

	/**
	 * @param t23
	 *            the t23 to set
	 */
	// public void setT23(int t23) {
	// T23 = t23;
	// }
	/**
	 * @return the zeroPressure
	 */
	public int getZeroPressure() {
		if (pressures==null)
			pressures = computePressures(minPressure, maxPressure, PointsP);
		
		return pressures.indexOf(0.);
	}



	/**
	 * @return the baseRow
	 */
	public int getBaseRow() {
		return baseRow;
	}

	/**
	 * @param baseRow
	 *            the baseRow to set
	 */
	public void setBaseRow(int baseRow) {
		this.baseRow = baseRow;
	}

	// public int getState() {
	// return state;
	// }

	// public void setState(int state) {
	// this.state = state;
	// }

	public int getTermType() {
		return TermType;
	}

	public void setTermType(int termType) {
		TermType = termType;
	}

	public Icon getIcon() {
		// Icon ico =
		if (getChildCount() == 0)
			return SafUtil.createImptIcon();

		State();
		// System.out.println(avail);
		return SafUtil.createIcon(avail, KA, getT23(), flags, dataWrote, K0);

		// }

		// return SafUtil.createImptIcon();

	}

	public void State() {
		// Integer[] res = null;
		KA = 1;
		K0 = 1;
		dataWrote = 1;
		
		flags = new Integer[PointsT];
		Arrays.fill(flags, 1);
		avail = true;
		for (Sensor sensor : getListSensors()) {
			
			KA = (KA != 2) & (sensor.getKAFlag() != 1) ? sensor.getKAFlag()
					: KA;
			dataWrote = (dataWrote != 2) & (sensor.getDataIsWrote() != 1) ? sensor
					.getDataIsWrote()
					: dataWrote;
			K0 = (K0 != 2) & (sensor.getK0diapIsWrote() != 1) ? sensor
					.getK0diapIsWrote() : K0;
			avail = avail & sensor.isAvailable();
			for (int i = 0; i < sensor.getTempFlags().length; i++) {
				flags[i] = (flags[i] != 2) & (sensor.getTempFlags()[i] != 1) ? sensor
						.getTempFlags()[i]
						: flags[i];
			}

		}
	};

	@SuppressWarnings("unchecked")
	public List<Sensor> getListSensors() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		Enumeration<DefaultMutableTreeNode> childrens = this.children();
		while (childrens.hasMoreElements()) {
			sensors.add((Sensor) childrens.nextElement());
		}

		return sensors;

	}
	
	
	public static List<Double> computePressures(float minP, float maxP, int pP)
	{
		List<Double> result = new LinkedList<Double>();
		double d =(maxP-minP);
		
		double delta = d/(pP-1);
		
		for (int i=0; i<(pP-1);i++)
			result.add(minP+delta*i);
		result.add(Double.valueOf(maxP));
		
		return result;
	
		
	}


	
	
	
	
	
	
	public static List<Double> computeTemperatures(int  minT, int maxT, int pT)
	{
		List<Double> result = new LinkedList<Double>();
		//double k = (maxT-minT);
		double delta = (maxT-minT)/(pT-1);
		for (int i=0; i<(pT-1);i++)
			result.add(minT+delta*i);
		result.add(Double.valueOf(maxT));
		
		
		Double q = Double.valueOf(java.lang.Math.abs(minT - 23));
		Double val = Double.valueOf(minT);
		for (Double d : result)
			if (q > java.lang.Math.abs(d - 23)) {
				q = java.lang.Math.abs(d - 23);
				val = d;
			}

		int ind = result.indexOf(val);

		result.set(ind, 23.);
		return result;
	}
	
	public  String getStrMeasure()
	{
		if(measure==null)
		{
			switch (SensorTypes.INSTANSE.sensorTypes.get(Type).measure) {
			case 0:
				measure = " Па";
				break;
			case 1:
				measure = " кПа";
				break;
			case 2:
				measure = " МПа";
				break;
			}
		}
		return measure;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	
	
	
	
/*	@Override
	public synchronized Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		System.out.println("getTransferData");
		return this;
	}

	@Override
	public synchronized DataFlavor[] getTransferDataFlavors() {
		
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0) {
		System.out.println("isDataFlavorSupported");
		return true;
	}*/
	
	

}
