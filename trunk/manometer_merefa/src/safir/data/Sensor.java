package safir.data;

import safir.bdUtils.DAO;
import safir.constants.SensorConst;
import safir.exceptions.NoFreeAddressException;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


public class Sensor extends DefaultMutableTreeNode {
    private static Set<Integer> addresses;
    // public static DataFlavor SENSOR_FLAVOR = new DataFlavor(Sensor.class,
    // "Sensor");

    // private DataFlavor flavors[] = { SENSOR_FLAVOR };

    private int year = 2008;
    private int address;
    private long serNum;
    private int group;
    private int koefAmplificftion = -1;
    private boolean isAvailable = false;
    private Float[][] table; // массив со снятыми точками давления
    private Float[] temperatures; // вектор для снятых температур
    private Integer[] tempFlags;// ={1,1,2,0};
    private Integer dataIsWrote = 0;
    private Integer k0diapIsWrote = 0;
    private String note;
    private int diapasonCount = -1;
    private float span = .0F;
    private boolean enabled = true;

    static {
        addresses = new HashSet<Integer>();
        for (int i = 4; i < 127; i++) {
            addresses.add(new Integer(i));
        }
        addresses.remove(43); // Удаляем адрес 43
        addresses.remove(85); // Удаляем адрес 0x55
    }

    public static int getFreeAddreesses() throws NoFreeAddressException {
        if (addresses.isEmpty())
            throw new NoFreeAddressException("Нет свободных адресов.");
        Integer in = addresses.iterator().next();
        addresses.remove(in);

        return in.intValue();
    }

    public boolean isAvailable() {

        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getKoefAmplificftion() {
        return koefAmplificftion;
    }

    public void setKoefAmplificftion(int koefAmplificftion) {

        this.koefAmplificftion = koefAmplificftion;
    }

    public long getSerNum() {
        return serNum;
    }

    public void setSerNum(long serNum) {
        this.serNum = serNum;
    }

    // public int getState() {
    // return state;
    // }
    //
    // public void setState(int state) {
    // this.state = state;
    // }

    /**
     * Этот конструктор нужен для восстановления объектов из бызы данных
     *
     * @param address
     */
    public Sensor(int address) {
        this.address = address;
        addresses.remove(address);
    }

	/*
     * public Sensor(String[] s) { note = s[4]; serNum = Long.parseLong(s[0]);
	 * group = Integer.parseInt(s[1]); address = Integer.parseInt(s[2]); }
	 */

    public Sensor() throws NoFreeAddressException {
        address = getFreeAddreesses();
    }

    public String toString() {
        String  s = serNum + " ad " + address;
        if ((note != null) && (note.length() != 0)) {
            s += "\n " + note;
        }
        return s;
    }

    public int getAddress() {
        return address;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float[][] getTable() {
        return table;
    }

    public void setTable(Float[][] table) {
        this.table = table;
    }

    public Float[] getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(Float[] temperatures) {
        this.temperatures = temperatures;
    }

    public Icon getIcon() {
        int pos23 = ((Group) getParent()).getTemperatures().indexOf(23.);
        return SafUtil.createIcon(enabled,isAvailable, getKAFlag(), pos23, tempFlags,
                dataIsWrote, k0diapIsWrote);

    }

    public Integer[] getTempFlags() {
        return tempFlags;
    }

    public void setTempFlags(Integer[] tempFlags) {
        this.tempFlags = tempFlags;
    }

    public Integer getDataIsWrote() {
        return dataIsWrote;
    }

    public void setDataIsWrote(Integer dataIsWrote) {
        this.dataIsWrote = dataIsWrote;
    }

    public Integer getK0diapIsWrote() {
        return k0diapIsWrote;
    }

    public void setK0diapIsWrote(Integer isWrote) {
        k0diapIsWrote = isWrote;
    }

    public Integer getKAFlag() {
        if ((koefAmplificftion > 1) & (koefAmplificftion < 7))
            return 1;
        if (koefAmplificftion == -1)
            return 0;
        return 2;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void returnAddres() {
        addresses.add(Integer.valueOf(address));
    }

    public boolean deleteSensor(boolean SaveInArchiv) {
        if (SaveInArchiv)
            SafUtil.log(this, "Удалили датчик", true);
        else SafUtil.log(this, "Удалили датчик без сохранения в архиве", true);

        addresses.add(Integer.valueOf(address));
        try {
            if (SaveInArchiv)
                DAO.INSTANSE.saveSensorToArchive(this);
            DAO.INSTANSE.deleteSensor(this);
        } catch (SQLException e) {

        }


        return SerialConnection.INSTANSE.writeDefAddress(address);
    }
	
	
	
/*	public boolean deleteSensorWithoutSavingInArchiv() {
		SafUtil.log(this, "Удалили датчик", true);
		addresses.add(Integer.valueOf(address));
		try {
			DAO.INSTANSE.saveSensorToArchive(this);
			DAO.INSTANSE.deleteSensor(this);
		} catch (SQLException e) {

		}
		
		
		return SerialConnection.INSTANSE.writeDefAddress(address);
	}*/


    public boolean StateScan() {

        if (SerialConnection.INSTANSE.isOpen())

            try {

                SerialConnection.INSTANSE.readVar(SensorConst.PRS1DAT, this
                        .getAddress());
                //SafUtil.log(this, "Датчик ответил", true);

                this.setAvailable(true);
                return true;

            } catch (Exception e) {
                ///SafUtil.log(this, "Датчик не ответил", true);
                this.setAvailable(false);
                return false;
            }
        return false;

    }

    public String getCheckSum() {
        String result = "";

        return result;

    }



    public boolean writeData(int espan, int menup) {
        SerialConnection conn = SerialConnection.INSTANSE;
        ByteArrayOutputStream os;// = new ByteArrayOutputStream();
        SafUtil.log(this, "Пишем данные в датчик", true);
        StringBuffer buffer;
        byte[] zero = {0, 0, 0, 0};
        SafUtil.log(this, "Таблица снятых данных", false);
        try {
            for (int i = 0; i < 16; i++) {
                buffer = new StringBuffer();
                os = new ByteArrayOutputStream();

                for (int j = 0; j < 16; j++)

                    if ((j >= table.length) | (i >= table[0].length)) {
                        os.write(zero);
                        buffer.append(0f + "  ");
                    } else {
                        os.write((table[j][i] == null) ? zero : SafUtil
                                .floatToByteArray(table[j][i]));
                        buffer.append((table[j][i] == null) ? 0f : table[j][i]
                                + "  ");

                    }

                SafUtil.log(this, buffer.toString(), false);
                System.out.println(i + "*   " + buffer.toString());
                byte[] mm = os.toByteArray();
                int[] ll = new int[mm.length];
                for (int k = 0; k < mm.length; k++)
                    ll[k] = mm[k];
                conn.writeVar(SensorConst.table[i], address, ll);
            }

            int passport = ((table[0].length - 1) > 15) ? 15 : table[0].length - 1;

            passport <<= 4;
            passport += ((table.length - 1) > 15) ? 15 : table.length - 1;


            conn
                    .writeVar(SensorConst.PASSPORT, address,
                            new int[]{passport});
            SafUtil.log(this, "Размерность таблицы T * P  ---"
                    + Integer.toHexString(passport), false);
            SafUtil.log(this, "Строка  стредних температур", false);

            buffer = new StringBuffer();
            os = new ByteArrayOutputStream();
            for (int i = 0; i < 16; i++) {
                if (i >= temperatures.length) {
                    os.write(zero);
                    buffer.append(0f + "  ");
                } else {
                    os.write((temperatures[i] == null) ? zero : SafUtil
                            .floatToByteArray(temperatures[i]));
                    buffer.append((temperatures[i] == null) ? 0f
                            : temperatures[i] + "  ");
                }
            }
            SafUtil.log(this, buffer.toString(), false);

            SafUtil.log(this, "Строка  токов", false);
            buffer = new StringBuffer();
            int[] tmp = new int[os.size()];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = os.toByteArray()[i];
            conn.writeVar(SensorConst.temperatures, address, tmp);
            Float[] iout = new Float[table.length];
            Float step = (float) 16 / (table.length - 1);
            for (int i = 0; i < table.length; i++) {
                iout[i] = 4 + i * step;
            }
            os = new ByteArrayOutputStream();
            for (int i = 0; i < 16; i++) {

                if (i >= iout.length) {
                    os.write(zero);
                    buffer.append(0f + "  ");
                } else {
                    os.write((iout[i] == null) ? zero : SafUtil
                            .floatToByteArray(iout[i]));
                    buffer.append((iout[i] == null) ? 0f : iout[i] + "  ");
                }
            }
            tmp = new int[os.size()];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = os.toByteArray()[i];
            conn.writeVar(SensorConst.currents, address, tmp);
            SafUtil.log(this, buffer.toString(), false);

            SafUtil.log(this, "Строка  диапазонов", false);
            buffer = new StringBuffer();
            Group group = (Group) parent;
            os = new ByteArrayOutputStream();
            float[] span = SafUtil.baseContD(new Float(group.getMaxPressure()),
                    new Float(group.getMinPressure()));

            // for (int i = 0; i < span.length; i++) {
            // os.write(SafUtil.floatToByteArray(span[i]));
            // buffer.append(span[i] + " ");
            // }

            for (int i = span.length; i > 0; i--) {
                os.write(SafUtil.floatToByteArray(span[i - 1]));
                buffer.append(span[i - 1] + "  ");
            }

            tmp = new int[os.size()];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = os.toByteArray()[i];
            conn.writeVar(SensorConst.KDIAP, address, tmp);
            SafUtil.log(this, buffer.toString(), false);

			/*
			 * 
			 * 
			 */
            SafUtil.log(this, "Pmin Pmax Tmin Tmax", false);
            buffer = new StringBuffer();
            os = new ByteArrayOutputStream();
            os.write(SafUtil
                    .floatToByteArray(new Float(group.getMinPressure())));
            buffer.append(new Float(group.getMinPressure()) + "  ");
            os.write(SafUtil
                    .floatToByteArray(new Float(group.getMaxPressure())));
            buffer.append(new Float(group.getMaxPressure()) + "  ");
            os.write(SafUtil.floatToByteArray(new Float(group.getMinTemp())));
            buffer.append(new Float(group.getMinTemp()) + "  ");
            os.write(SafUtil.floatToByteArray(new Float(group.getMaxTemp())));
            buffer.append(new Float(group.getMaxTemp()) + "  ");
            tmp = new int[os.size()];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = os.toByteArray()[i];
            conn.writeVar(SensorConst.PminPmaxTminTmax, address, tmp);
            SafUtil.log(this, buffer.toString(), false);
			/*
			 * 
			 * 
			 */
            SafUtil.log(this, "Коэффициенты апроксимации", false);
            buffer = new StringBuffer();
            float[] coef = SafUtil.CalkAprox(table, iout, temperatures, group
                    .getZeroPressure(), group.getBaseRow(), group.getT23());

            os = new ByteArrayOutputStream();
            for (int i = 0; i < 16; i++) {
                os.write(SafUtil.floatToByteArray(coef[i]));
                buffer.append(coef[i] + "  ");

            }
            tmp = new int[os.size()];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = os.toByteArray()[i];
            conn.writeVar(SensorConst.APROX, address, tmp);
            SafUtil.log(this, buffer.toString(), false);
			/*
			 * 
			 * 
			 */

            SafUtil.log(this, "Коэффициенты нелинейности", false);
            buffer = new StringBuffer();
            os = new ByteArrayOutputStream();
            for (int i = 16; i < 24; i++) {
                os.write(SafUtil.floatToByteArray(coef[i]));
                buffer.append(coef[i] + "  ");
            }
            tmp = new int[os.size()];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = os.toByteArray()[i];
            conn.writeVar(SensorConst.NOLINE, address, tmp);
            SafUtil.log(this, buffer.toString(), false);
			/*
			 * 
			 * 
			 * 
			 */
            conn.writeVar(SensorConst.span, address,
                    new int[]{SensorTypes.INSTANSE.sensorTypes.get(group
                            .getType()).measure});
            SafUtil.log(this,
                    "Размерность  "
                            + SensorTypes.INSTANSE.sensorTypes.get(group
                            .getType()).measure, false);

			/*
			 * 
			 * 
			 */
////Long l =new Long("10");
//			os = new ByteArrayOutputStream();
//			
//			
//		//	for (int i = 16; i < 24; i++) {
//				os.write(SafUtil.floatToByteArray(SafUtil.LongToFloat(serNum)));
//				//buffer.append(coef[i] + "  ");
//		//	}
//			tmp = new int[os.size()];
//			for (int i = 0; i < tmp.length; i++)
//				tmp[i] = os.toByteArray()[i];


            conn.writeVar(SensorConst.NOMSENS, address, SafUtil.getSerNumMas(serNum));

            SafUtil.log(this, "menup ", false);
            buffer = new StringBuffer();

            int[] menupArr = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

            for (int i = 0; i < (menupArr.length - menup); i++) {
                menupArr[i] += 0x80;

            }
            for (int i = 0; i < menupArr.length; i++) {
                buffer.append(Integer.toHexString(menupArr[i]) + "  ");
            }

            conn.writeVar(SensorConst.MENUP, address, menupArr);
            SafUtil.log(this, buffer.toString(), false);
			/*
			 * 
			 * 
			 */
            // conn.writeVar(SensorConst.coefAmplification, address,
            // new int[] { koefAmplificftion });


			/*
			 * 
			 * 
			 */

            conn.writeVar(SensorConst.MENUF, address, new int[]{0x16});
            SafUtil.log(this, "MENUF -- 0x16", false);

            conn.writeKa(address, koefAmplificftion);
            SafUtil.log(this, "Коэффициент усиления -- " + koefAmplificftion,
                    false);

            // прочитать 0x27 байт
            // из массива span записать какое то число
            // записать обратно

            SerialConnection.INSTANSE.getTuneArray(address, span[espan]);


            // conn.writeVar(SensorConst.ECONFIG, address, new int[] { 0x00 });
            // SafUtil.log(this, "ECONFIG -- 00", false);

            // os = new ByteArrayOutputStream();
            // os.write(SafUtil.floatToByteArray(span[espan]));
            // tmp = new int[os.size()];
            // for (int i = 0; i < tmp.length; i++)
            // tmp[i] = os.toByteArray()[i];
            // conn.writeVar(SensorConst.EESPAN, address, tmp);
            // SafUtil.log(this, "ESPAN -- " + span[espan], false);

            // conn.writeVar(SensorConst.EEPROMREV, address, new int[] {
            // 0x00,0x00, });

            // SafUtil.log(this, "EEPROMREV -- 0 0", false);

            return true;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }


    }

    public int getDiapasonCount() {
        return diapasonCount;
    }

    public void setDiapasonCount(int diapasonCount) {
        this.diapasonCount = diapasonCount;
    }

    public float getSpan() {
        return span;
    }

    public void setSpan(float span) {

        //dataIsWrote = 1;
        this.span = span;

    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
