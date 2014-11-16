package safir.exceptions;

public class SafException extends Exception {
	/**
	 * 
	 */
	static String[] msgExc = { "", // 0
			"Один из заданных параметров не является цифрой", // 1
			" задано неверно", " задана неверно", " задан неверно",// 2 3 4
			"Время", "Температура", "Термокамера", " Диапазон",// 5 6 7 8
			"Минимальная температура", "Максимальная температура", // 9 10
			"Минимальное давление", "Максимальное давление", // 11 12
			"Число точек по давлению", "Число точек по температуре", // 13 14
			"Ошибка открытия COM-порта", "Ошибка вычислений ", // 15 16
			"Деление на ноль", "Определитель матрицы равен нулю", // 17 18
			"Ошибка парсинга данных ", "температуры", "давления", // 19 20 21
			"Диапазон давлений задан неверно" };
	public final static int Empty = 0, noDigit = 1, Wrong_o = 2, Wrong_a = 3,
			Wrong_n = 4, Time = 5, Temp = 6, Camera = 7, Diap = 8, MinT = 9,
			MaxT = 10, MinP = 11, MaxP = 12, PointsP = 13, PointsT = 14,
			ComPort = 15, Calc = 16, DevZero = 17, DecZero = 18, Parse = 19,
			ParseT = 20, ParseP = 21, DiapP = 22

			;

	public SafException(String s) {
		super(s);
	}

	public SafException(int n1, int n2) {
		super(msgExc[n1] + msgExc[n2]);
	}

}
