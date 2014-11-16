package safir.rs232connect;

import java.util.LinkedList;
import java.util.List;

import safir.constants.SensorConst;
import safir.data.Message;
import safir.data.Sensor;
import safir.exceptions.NoDataException;
import safir.utils.SafUtil;

public class SerialConnectionMediator {

	public String enabledFixedCurrtntOutput(Sensor sensor) {
		int address = sensor.getAddress();
		byte[] txDat = new byte[14];

		txDat[0] = Integer.valueOf("85").byteValue();// 0x55
		txDat[1] = Integer.valueOf(address).byteValue();
		txDat[2] = 0;
		txDat[3] = 0;
		txDat[4] = 5;
		txDat[5] = Integer.valueOf((-(address + 5)) & 255).byteValue();
		txDat[6] = (byte) 0x0d;
		txDat[7] = 0;//
		txDat[8] = Integer.valueOf("96").byteValue();// 0x60
		txDat[9] = 1;
		txDat[10] = 8;
		txDat[11] = Integer.valueOf("138").byteValue();// 0x8a
		txDat[12] = Integer.valueOf("85").byteValue();// 0x55
		txDat[13] = Integer.valueOf("170").byteValue();// 0xaa

		//		
		// for (Byte b:txDat)
		// {
		// try {
		// os.write(b);
		// // Thread.sleep(25);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//		
		// return "Возникла ошибка";

		// SafUtil.printHexArray(Arrays.copyOfRange(txDat, 0, 6), true);
		// SafUtil.printHexArray(Arrays.copyOfRange(txDat, 6, 13), true);
		// (Arrays.copyOfRange(txDat, 0, 6));
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// writeToCom(Arrays.copyOfRange(txDat, 6, 13));

		try {
			Message res = SerialConnection.INSTANSE.writeData(txDat);

			return SafUtil.printHexArray(res.getData(), false);
		}

		catch (NoDataException e) {

			sensor.setAvailable(false);
			return "Возникла ошибка";

		} catch (InterruptedException e) {

			return "Возникла ошибка";

		}

	}

	// 55 04 00 00 05 f7 0d 00 60 01 08 8a 55 aa
	// 55 04 00 00 05 f7 0d 00 60 01 00 92 55 aa
	public String disabledFixedCurrtntOutput(Sensor sensor) {
		int address = sensor.getAddress();
		byte[] txDat = new byte[14];

		txDat[0] = Integer.valueOf("85").byteValue();// 0x55
		txDat[1] = Integer.valueOf(address).byteValue();
		txDat[2] = 0;
		txDat[3] = 0;
		txDat[4] = 5;
		txDat[5] = Integer.valueOf((-(address + 5)) & 255).byteValue();
		txDat[6] = (byte) 0x0D;//
		txDat[7] = 0;//
		txDat[8] = Integer.valueOf("96").byteValue();// 0x60
		txDat[9] = 1;
		txDat[10] = 0;
		txDat[11] = Integer.valueOf("146").byteValue();// 0x92
		txDat[12] = Integer.valueOf("85").byteValue();// 0x55
		txDat[13] = Integer.valueOf("170").byteValue();// 0xaa

		try {
			Message res = SerialConnection.INSTANSE.writeData(txDat);

			return SafUtil.printHexArray(res.getData(), false);
		}

		catch (NoDataException e) {
			// log
			// .error("Возникли ошибки при выключении режима выдачи
			// фиксированного тока "
			// + e.getMessage());
			sensor.setAvailable(false);
			return "Возникла ошибка";

		} catch (InterruptedException e) {
			// log
			// .error("Возникли ошибки при выключении режима выдачи
			// фиксированного тока "
			// + e.getMessage());
			return "Возникла ошибка";
		}

	}

	public boolean writeKa(int address, int KA) throws NoDataException,
			InterruptedException {
		/*
		 * Коэффициент усилинеия ацп лежит в пределах 0..7 и хранится в 3..5
		 * битах переменной с адресом SensorConst.coefAmplification
		 */
		if ((KA > 7) | (KA < 0)) // проверяем корректность входных данных
			throw new IllegalArgumentException(
					"Неверный коэффициент усиления --" + KA);
		KA = KA << 3;
		// int[] data = { KA };
		writeVar(SensorConst.coefAmplification, address, new int[] { KA });
		resetSensor(address);
		// после записи ку датчик необходимо перезагрузить
		return true;
	}

	public void writeNewAddress(int newAddress) throws NoDataException,
			InterruptedException {
		writeVar(SensorConst.ADDRESS, 2, new int[] { newAddress });
		// записываем в датчик новый адрес
		resetSensor(2);
		// перезапускаем датчик обращаясь к нему по старому адресу

	}

	public void writeTask(List<Sensor> data) throws NoDataException {
		// boolean res = true;
		Message m = new Message();
		m.setToAddress(0xff);
		m.setFromAddres(0x00);
		m.setCommand(0x87); // команда на измерения
		m.setAddressVar1(0x32);
		List<Integer> addresses = new LinkedList<Integer>();
		for (Sensor s : data)
			addresses.add(s.getAddress());

		m.setData(addresses);

		try {
			SerialConnection.INSTANSE.writeData(m.getTxDat());
		} catch (InterruptedException e) {
			// игрорируем InterruptedException
			// потому что прерывать выполнение этой команды никто не будет
		}
	}

	public static synchronized void zeroizeSensor(int address) {
		byte[] txDat = new byte[8];
		txDat[0] = (byte) 0x55;
		txDat[1] = (byte) address;
		txDat[2] = 0;
		txDat[3] = (byte) 0x85;
		txDat[4] = 0;
		txDat[5] = (byte) ((-(address + 133)) & 255);
		txDat[6] = (byte) 0x55;
		txDat[7] = (byte) 0xaa;
		// SafUtil.printHexArray(txDat);
		SerialConnection.INSTANSE.writeToCom(txDat);
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
		}

	}

	public void spanSensor(int address) {
		byte[] txDat = new byte[8];
		txDat[0] = Integer.valueOf("85").byteValue();// 0x55
		txDat[1] = Integer.valueOf(address).byteValue();
		txDat[2] = 0;
		txDat[3] = Integer.valueOf("134").byteValue();// 0x86
		txDat[4] = 0;
		txDat[5] = Integer.valueOf((-(address + 134)) & 255).byteValue();
		txDat[6] = Integer.valueOf("85").byteValue();// 0x55
		txDat[7] = Integer.valueOf("170").byteValue();// 0xaa
		// SafUtil.printHexArray(txDat);
		SerialConnection.INSTANSE.writeToCom(txDat);
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
		}

	}

	public boolean writeDefAddress(int address) throws NoDataException,
			InterruptedException {
		writeVar(SensorConst.ADDRESS, address, new int[] { 2 });
		// записываем в датчик новый адрес
		resetSensor(address);
		return true;
	}

	public int readPressure(int address) throws InterruptedException,
			NoDataException {

		int[] data = readVar(SensorConst.PRS1DAT, address).getData();
		return SafUtil.ByteToInt(data[0], data[1]);
	}

	public int readTemperature(int address) throws InterruptedException,
			NoDataException {
		// ;
		int[] data = readVar(SensorConst.TMPDAT, address).getData();
		return SafUtil.ByteToInt(data[0], data[1]);
	}

	// 55 04 00 00 04 f8 fc 7f 00 04 81 55 aa
	public String getCheckSum(Sensor sensor) {
		int address = sensor.getAddress();
		byte[] txDat = new byte[13];

		txDat[0] = (byte) 0x55;
		txDat[1] = (byte) address;// ).byteValue();
		txDat[2] = 0;
		txDat[3] = 0;
		txDat[4] = 4;
		txDat[5] = (byte) ((-(address + 4)) & 255);// .byteValue();
		txDat[6] = (byte) 0xfc;
		txDat[7] = (byte) 0x7f;
		txDat[8] = 0;
		txDat[9] = 4;
		txDat[10] = (byte) 0x81;
		txDat[11] = (byte) 0x55;
		txDat[12] = (byte) 0xaa;

		try {
			Message res = SerialConnection.INSTANSE.writeData(txDat);
			StringBuffer buffer = new StringBuffer();
			for (int i = res.getData().length - 1; i >= 0; i--)
				buffer.append((res.getData()[i] > 15 ? Integer.toHexString(res
						.getData()[i]) : "0"
						+ Integer.toHexString(res.getData()[i]))
						+ "  ");
			return buffer.toString();
		}

		catch (NoDataException e) {

			sensor.setAvailable(false);
			return "Возникла ошибка";

		} catch (InterruptedException e) {

			return "Возникла ошибка";
		}

	}

	public void resetSensor(int address) {
		byte[] txDat = new byte[8];
		txDat[0] = Integer.valueOf("85").byteValue();// 0x55
		txDat[1] = Integer.valueOf(address).byteValue();
		txDat[2] = 0;
		txDat[3] = Integer.valueOf("128").byteValue();// 0x80
		txDat[4] = 0;
		txDat[5] = Integer.valueOf((-(address + 128)) & 255).byteValue();
		txDat[6] = Integer.valueOf("85").byteValue();// 0x55
		txDat[7] = Integer.valueOf("170").byteValue();// 0xaa
		for (int i = 0; i < 3; i++) {
			SerialConnection.INSTANSE.writeToCom(txDat);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	public synchronized Message readVar(int[] var, int address)
			throws InterruptedException, NoDataException {
		Message tMes = new Message(address, var[0], var[1], 0x20, var[2]);
		return SerialConnection.INSTANSE.writeData(tMes.getTxDat());

	}

	public synchronized Message writeVar(int[] var, int address, int data[])
			throws NoDataException, InterruptedException {
		Message tMes = new Message(address,var[0], var[1], 0x60, var[2], data);
		return SerialConnection.INSTANSE.writeData(tMes.getTxDat());
		
	}
}
