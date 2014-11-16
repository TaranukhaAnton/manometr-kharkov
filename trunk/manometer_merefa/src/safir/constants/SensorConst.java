package safir.constants;

public class SensorConst {
	/*В первые два элемента в массиве адрес переменной в датчике,
	 *третий -- его длина.
	 * */
	
	public static final int[] ZERO = { 0, 0, 4 };
	public static final int[] SPAN = { 1, 0, 4 };
	public static final int[] APROX = { 2, 0, 0x40 };
	public static final int[] NOLINE = { 2, 0x08, 0x20 };
	public static final int[] KDIAP = { 3, 0, 0x20 };
	public static final int[] PASSPORT = { 4, 0, 1 };
	public static final int[] ADDRESS = { 5, 0 , 1 }; // АДРЕС ДАТЧИКА
	public static final int[] VERSION = { 6, 0, 4 };
	public static final int[] DAC = { 7, 0, 2 };
	public static final int[] NOMSENS = { 8, 0, 3 };//длина бала 3
	public static final int[] PRS1DAT = { 9, 0, 2 }; // код измеренного
														// давления
	public static final int[] TMPDAT = { 0x0a, 0,  2 };// код измеренной
	// температуры
	public static final int[] PRS1BUF = { 0x0b, 0, 4 };
	public static final int[] TMPBUF = { 0x0c, 0, 4 };
	public static final int[] CONFIG = { 0x0d, 0, 1 };
	public static final int[] DEMPFER = { 0x0e, 0, 4 };
	public static final int[] MENUP = { 0x0f, 0, 0x08 };
	public static final int[] MENUF = { 0x10, 0, 1 };
	public static final int[] EEPROMREV = { 0x11, 0, 0x02 };
	public static final int[] EZERO = { 0x12, 0, 4 };
	public static final int[] ESPAN = { 0x13, 0, 4 };
	public static final int[] ECONFIG = { 0x14, 0, 1 };
	public static final int[] EEZERO = { 0x15, 0, 4 };
	public static final int[] EESPAN = { 0x16, 0, 0x4 };
	public static final int[] kDIV = { 0x17, 0, 4 };
	public static final int[] iDAC_RWF = { 0x18, 0, 4 };
	public static final int[] PTAVR = { 0x19, 0, 0x28 }; 
	// Усредненные измеренные данные
	public static final int[] temperatures = { 0x24, 0x00, 0x40 }; //температуры
	public static final int[] currents = { 0x24, 0x08, 0x40 };  //токи
	public static final int[] PminPmaxTminTmax = { 0x24, 0x90, 0x10 };
	public static final int[] span = { 0x24, 0x92, 0x01 };

	public static final int[] coefAmplification = { 0x2b, 0, 1 };

	
	
	public static final int[][] table = { { 0x24, 0x10, 0x40 },
			{ 0x24, 0x18, 0x40 }, { 0x24, 0x20, 0x40 }, { 0x24, 0x28, 0x40 },
			{ 0x24, 0x30, 0x40 }, { 0x24, 0x38, 0x40 }, { 0x24, 0x40, 0x40 },
			{ 0x24, 0x48, 0x40 }, { 0x24, 0x50, 0x40 }, { 0x24, 0x58, 0x40 },
			{ 0x24, 0x60, 0x40 }, { 0x24, 0x68, 0x40 }, { 0x24, 0x70, 0x40 },
			{ 0x24, 0x78, 0x40 }, { 0x24, 0x80, 0x40 }, { 0x24, 0x88, 0x40 } };

	public static final int defaultAddress = 0x02;
	
	
	

}
