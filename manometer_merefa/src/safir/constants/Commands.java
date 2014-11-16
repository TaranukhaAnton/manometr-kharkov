package safir.constants;

public class Commands {
	public static final  int restart 	 = 0x00; //Перезапустить датчик
	public static final  int takingADCp1 = 0x81; //Измерить параметр АЦП давление  1
	public static final  int takingADCp2 = 0x82; //Измерить параметр АЦП давление 2
	public static final  int takingADCt1 = 0x83; //Измерить параметр АЦП температура 1
	public static final  int takingADCt2 = 0x84; //Измерить параметр АЦП температура 2
	public static final  int setZero 	 = 0x85; //Установить "НОЛЬ"
	public static final  int setRange	 = 0x86; //Установить "ДИАПАЗОН"
	public static final  int takingAvPT  = 0x87; //Усредненное измерение давления и температуры
	public static final  int newCommand  = 0x88; //Усредненное измерение давления и температуры


}
