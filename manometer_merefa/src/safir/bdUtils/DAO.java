package safir.bdUtils;

import org.apache.log4j.Logger;
import safir.data.*;
import safir.exceptions.NoFreeAddressException;
import safir.frame.components.RowHeaderRenderer;
import safir.rs232connect.SerialConnection;
import safir.utils.SafirProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.*;

public class DAO {
    Logger log;
    private Connection conn = null;
    public static final DAO INSTANSE = new DAO();

    private DAO() {
        log = Logger.getLogger(this.getClass());
        try {
            openConnection();
        } catch (SQLException e) {

        }
    }

    public void openConnection() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("Драйвер для работы с БД не найден");
            throw new SQLException("Драйвер для работы с БД не найден");
        }
        try {
            String url = SafirProperties.getUrl();
            String user = SafirProperties.getUser();
            String password = SafirProperties.getPassword();


            conn = DriverManager.getConnection(
                    url,
                    user,
                    password);
        } catch (SQLException e) {
            log.error("Не удалось установить соединение с БД", e);

            throw new SQLException("Не удалось установить соединение с БД");
        }
    }

    public boolean checkConnection() {
        if (conn != null)
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM test");
                return (rs != null);
            } catch (SQLException e) {
                return false;
                // openConnection();
            }
        else
            return false;

    }

    public boolean AvailableConnection() {
        if (!checkConnection()) {
            try {
                openConnection();
                return true;
            } catch (SQLException e) {
                return false;
            }
        } else
            return true;
    }

    public Vector<Termocam> getTermocams() throws SQLException {
        Vector<Termocam> termocams = new Vector<Termocam>();

        try {

            if (!checkConnection())
                openConnection();


            Statement st = conn.createStatement();
            String id = SafirProperties.getComputerId();
            ResultSet rs = st.executeQuery("SELECT * FROM `termocam` where id_computer =" + id);
            if (rs != null)
                while (rs.next()) {

                    termocams.add(new Termocam(rs.getInt("id"), rs
                            .getString("name")));

                }

            return termocams;
        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении"
                    + " к таблице `termocam`", e);

            throw new SQLException("Возникли проблемы при обращении"
                    + " к таблице `termocam`", e);
        }

    }

    @SuppressWarnings("unchecked")
    public Vector<Group> getGroups(int termocam) throws SQLException {
        Vector<Group> groups = new Vector<Group>();
        try {
            if (!checkConnection())
                openConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st
                    .executeQuery("SELECT * FROM `groups` where Camera ="
                            + termocam);
            if (rs != null)
                while (rs.next()) {
                    Group group = new Group(rs.getInt("id"));
                    // Создаем при помощи конструктора в котором явно указываем
                    // его номер.
                    // Иначе номер выберется случайный из списка свободных
                    // номеров.
                    // Тут использовать конструктор без параметров категорически
                    // нельзя.
                    group.setCamera(rs.getInt("Camera"));
                    group.setMaxPressure(rs.getFloat("maxPressure"));
                    group.setMinPressure(rs.getFloat("minPressure"));
                    group.setMaxTemp(rs.getInt("MaxTemp"));
                    group.setMinTemp(rs.getInt("MinTemp"));
                    group.setPointsP(rs.getInt("pointsp"));
                    group.setPointsT(rs.getInt("pointsT"));
                    group.setBaseRow(rs.getInt("baseRow"));
                    group.setType(rs.getInt("type"));
                    group.setNote(rs.getString("note"));
                    group.setTermType(rs.getInt("termType"));
                    groups.add(group);
                }
            return groups;
        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении к таблице `groups`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `groups`", e);
        }

    }

    public boolean existSensor(Long sernum, int year) throws SQLException {
        try {
            checkConnection();
            Statement st = conn.createStatement();
            String query = "select COUNT(*) from sensor  where `year`= " + year + " and `serNum`="
                    + sernum;
            ResultSet rs = st.executeQuery(query);
            if (rs != null)
                while (rs.next()) {
                    return (rs.getInt("COUNT(*)") != 0);
                }
        } catch (Exception e) {
            log.error("Возникли проблемы при обращении к таблице `sensor`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `sensor`", e);
        }
        return false;
    }

    public boolean existSensorInArchive(Long sernum, int year)
            throws SQLException {
        try {
            checkConnection();
            Statement st = conn.createStatement();
            String query = "select COUNT(*) from archiv  where `year`= " + year + " and `serNum`="
                    + sernum;
            ResultSet rs = st.executeQuery(query);
            if (rs != null)
                while (rs.next()) {
                    return (rs.getInt("COUNT(*)") != 0);
                }
        } catch (Exception e) {
            log.error("Возникли проблемы при обращении к таблице `archiv`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `archiv`", e);
        }
        return false;
    }

    public boolean groupsEqualType(Long sernum, int year, Group group)
            throws SQLException {

        try {
            checkConnection();
            Statement st = conn.createStatement();

            String query = "select * from archiv  where `year`= " + year + " and `serNum`="
                    + sernum;
            ResultSet rs = st.executeQuery(query);
            if (rs != null) {
                rs.next();
                return group.getType() == rs.getInt("type");

            }

        } catch (Exception e) {
            log.error("Возникли проблемы при обращении к таблице `archiv`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `archiv`", e);
        }

        return false;
    }

    public Sensor sensorToCurrentGroup(Long sernum, int year, Group group)
            throws SQLException {
        Sensor sensor = null;

        try {
            sensor = new Sensor();
            checkConnection();

            Statement st = conn.createStatement();
            String query = "select * from archiv  where `year`= " + year + " and `serNum`="
                    + sernum;
            ResultSet rs = st.executeQuery(query);
            if (rs != null) {
                rs.next();
                Integer[] tempFlags = new Integer[group.getPointsT()];
                Arrays.fill(tempFlags, 0);
                sensor.setTempFlags(tempFlags);
                sensor.setTable(new Float[group.getPointsP()][group
                        .getPointsT()]);
                sensor.setTemperatures(new Float[group.getPointsT()]);
                sensor.setAvailable(true);
                sensor.setGroup(group.getGroupNum());
                sensor.setSerNum(sernum);
                sensor.setYear(year);
                sensor.setNote(rs.getString("note"));
                if (group.getMaxPressure() == rs.getInt("maxP")) {
                    sensor.setKoefAmplificftion(rs.getInt("ka"));
                    if (group.getPointsP() == rs.getInt("pointsp")) {
                        List<Double> temperaturesNew = group.getTemperatures();
                        List<Double> temperaturesOld = Group
                                .computeTemperatures(rs.getInt("minT"), rs
                                        .getInt("maxT"), rs.getInt("pointst"));
                        // ObjectInputStream in;
                        Float[][] table;
                        Float[] temperatures;
                        try {
                            table = (Float[][]) (new ObjectInputStream(rs
                                    .getBinaryStream("table"))).readObject();
                            temperatures = (Float[]) (new ObjectInputStream(rs
                                    .getBinaryStream("temperatures")))
                                    .readObject();
                            boolean allPoints = true;
                            for (Double tempNew : temperaturesNew) {
                                int indNew = temperaturesNew.indexOf(tempNew);
                                int indOld = -1;
                                List<Double> list = Referenses.INSTANSE.map
                                        .get(tempNew);
                                for (Double d : list) {
                                    indOld = temperaturesOld.indexOf(d);
                                    // System.out.println(indOld);
                                    if (indOld != -1)
                                        break;
                                }
                                if (indOld == -1)
                                    continue;
                                for (int i = 0; i < group.getPointsP(); i++) {
                                    sensor.getTable()[i][indNew] = table[i][indOld];
                                }
                                sensor.getTemperatures()[indNew] = temperatures[indOld];
                                sensor.getTempFlags()[indNew] = sensor
                                        .getTemperatures()[indNew] == null ? 0
                                        : 1;
                                if (sensor.getTemperatures()[indNew] == null)
                                    allPoints = false;
                            }
                            sensor.setDiapasonCount(rs.getInt("diapCount"));
                            if ((allPoints)
                                    & (temperaturesOld.size() == temperaturesNew
                                    .size())
                                    & (sensor.getDiapasonCount() != -1))
                                sensor.setDataIsWrote(1);
                            sensor.setSpan(rs.getFloat("span"));

                            if ((sensor.getDataIsWrote() == 1)
                                    & (sensor.getSpan() != 0))
                                sensor.setK0diapIsWrote(1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                insertSensor(sensor, year);
                SerialConnection.INSTANSE.writeNewAddress(sensor.getAddress());
            }

        } catch (SQLException e1) {
            log.error("Возникли проблемы при обращении к таблице `archiv`", e1);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `archiv`", e1);
        } catch (NoFreeAddressException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }

        return sensor;
    }

    public Group sensorToNewGroup(long sernum, int year, int camera)
            throws SQLException {
        Group group = new Group();
        Sensor sensor;
        Set<Integer> numbers;
        numbers = new HashSet<Integer>();
        for (int i = 1; i < 127; i++) {
            numbers.add(new Integer(i));
        }

        try {
            checkConnection();

            sensor = new Sensor();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT groups.id FROM groups");

            if (rs != null)
                while (rs.next()) {
                    numbers.remove(rs.getInt("id"));

                }

            // Statement st = conn.createStatement();
            String query = "select * from archiv  where `year`= " + year + " and `serNum`="
                    + sernum;
            rs = st.executeQuery(query);
            if (rs != null) {
                rs.next();
                group.setGroupNum(numbers.iterator().next());
                group.setMaxPressure(rs.getFloat("maxP"));
                group.setMinPressure(rs.getFloat("minP"));
                group.setMaxTemp(rs.getInt("MaxT"));
                group.setMinTemp(rs.getInt("MinT"));
                group.setPointsP(rs.getInt("pointsp"));
                group.setBaseRow(rs.getInt("baseRow"));
                group.setType(rs.getInt("type"));
                group.setCamera(camera);

                sensor.setNote(rs.getString("note"));
                sensor.setKoefAmplificftion(rs.getInt("ka"));
                sensor.setAvailable(true);
                sensor.setYear(year);
                sensor.setSerNum(sernum);
                sensor.setSpan(rs.getFloat("span"));
                sensor.setDiapasonCount(rs.getInt("diapCount"));

                if (sensor.getDiapasonCount() != -1)
                    sensor.setDataIsWrote(1);
                if (sensor.getSpan() != 0)
                    sensor.setK0diapIsWrote(1);

                sensor.setGroup(group.getGroupNum());
                group.setPointsT(rs.getInt("pointsT"));

                ObjectInputStream in;
                try {
                    in = new ObjectInputStream(rs.getBinaryStream("table"));
                    sensor.setTable((Float[][]) in.readObject());
                    in = new ObjectInputStream(rs
                            .getBinaryStream("temperatures"));
                    sensor.setTemperatures((Float[]) in.readObject());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Integer[] flags = new Integer[group.getPointsT()];
                for (int i = 0; i < sensor.getTemperatures().length; i++)
                    flags[i] = (sensor.getTemperatures()[i] == null) ? 0 : 1;
                sensor.setTempFlags(flags);

                group.add(sensor);
                insertGroup(group);
                insertSensor(sensor, year);
                SerialConnection.INSTANSE.writeNewAddress(sensor.getAddress());
            }

        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении к таблице `archiv`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `archiv`", e);
        } catch (NoFreeAddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return group;
    }

    public Vector<Sensor> getSensors(int gr) throws SQLException {
        Vector<Sensor> sensors = new Vector<Sensor>();

        try {
            if (!checkConnection())
                openConnection();

            Statement st = conn.createStatement();
            String query = "select * from sensor where sensor.group = " + gr;
            ResultSet rs = st.executeQuery(query);
            if (rs != null)
                while (rs.next()) {
                    Sensor sensor = new Sensor(rs.getInt("address"));
                    sensor.setSerNum(rs.getLong("serNum"));
                    sensor.setGroup(rs.getInt("group"));
                    sensor.setNote(rs.getString("note"));
                    sensor.setKoefAmplificftion(rs.getInt("koefAmplificftion"));
                    sensor.setDataIsWrote(rs.getInt("DataWrote"));
                    sensor.setK0diapIsWrote(rs.getInt("Koef0Diap"));
                    sensor.setDiapasonCount(rs.getInt("diapCount"));
                    sensor.setSpan(rs.getFloat("span"));
                    sensor.setYear(rs.getInt("year"));
                    // if (sensor.getDiapasonCount()!=-1)
                    // sensor.setDataIsWrote(1);
                    // if (sensor.getSpan()!=0) sensor.setK0diapIsWrote(1);

                    ObjectInputStream in;
                    try {
                        in = new ObjectInputStream(rs.getBinaryStream("table"));
                        sensor.setTable((Float[][]) in.readObject());

                        in = new ObjectInputStream(rs
                                .getBinaryStream("temperatures"));
                        sensor.setTemperatures((Float[]) in.readObject());

                        in = new ObjectInputStream(rs
                                .getBinaryStream("TempFlags"));
                        sensor.setTempFlags((Integer[]) in.readObject());

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    sensors.add(sensor);
                }
            return sensors;
        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении к таблице `sensor`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `sensor`", e);
        }

    }

    public void insertSensor(Sensor sensor, int year) throws SQLException {

        try {
            checkConnection();
            String query = "INSERT INTO `sensor` (`serNum`,`year`,`address`,`group`,`table`,`temperatures`,`koefAmplificftion`,`TempFlags`,`diapCount`,`span`,`DataWrote`,`Koef0Diap`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, sensor.getSerNum());
            ps.setInt(2, year);
            ps.setInt(3, sensor.getAddress());
            ps.setInt(4, sensor.getGroup());
            ps.setObject(5, sensor.getTable());
            ps.setObject(6, sensor.getTemperatures());
            ps.setInt(7, sensor.getKoefAmplificftion());
            ps.setObject(8, sensor.getTempFlags());
            ps.setInt(9, sensor.getDiapasonCount());
            ps.setFloat(10, sensor.getSpan());
            ps.setInt(11, sensor.getDataIsWrote());
            ps.setInt(12, sensor.getK0diapIsWrote());

            ps.execute();
        } catch (SQLException e) {

            log.error("Возникли проблемы при "
                    + "добавлении записи в таблицу `sensor`", e);
            throw new SQLException("Возникли проблемы при добавлении"
                    + " записи в таблицу `sensor`", e);

        }
    }

    public void insertGroup(Group group) throws SQLException {

        Set<Integer> numbers;
        numbers = new HashSet<Integer>();
        for (int i = 1; i < 127; i++) {
            numbers.add(new Integer(i));
        }

        try {
            checkConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT groups.id FROM groups");

            if (rs != null)
                while (rs.next()) {
                    numbers.remove(rs.getInt("id"));

                }
            // System.out.println(numbers);

            String query = "INSERT INTO `groups` " +
            /* 1 */"(`id`," +
            /* 2 */"`minPressure`," +
            /* 3 */"`maxPressure`," +
            /* 4 */"`MinTemp`," +
            /* 5 */"`MaxTemp`," +
            /* 6 */"`PointsP`," +
            /* 7 */"`PointsT`,	" +
            /* 8 */"`Camera`," +
            /* 9 */"`type`," +
            /* 10 */"`note`," +
			/* 11 */"`baseRow`," +
			/* 12 */"`termType`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);
            group.setGroupNum(numbers.iterator().next());
            ps.setInt(1, group.getGroupNum());
            ps.setFloat(2, group.getMinPressure());
            ps.setFloat(3, group.getMaxPressure());
            ps.setInt(4, group.getMinTemp());
            ps.setInt(5, group.getMaxTemp());
            ps.setInt(6, group.getPointsP());
            ps.setInt(7, group.getPointsT());
            ps.setInt(8, group.getCamera());
            ps.setInt(9, group.getType());
            // ps.setObject(10, group.getTemperatures());
            // ps.setObject(11, group.getPressures());

            if (group.getNote() != null)
                ps.setString(10, group.getNote());
            else
                ps.setString(10, "");
            ps.setInt(11, group.getBaseRow());
            ps.setInt(12, group.getTermType());
            ps.execute();
        } catch (SQLException e) {
            log.error("Возникли проблемы при "
                    + "добавлении записи в таблицу `groups`", e);
            throw new SQLException("Возникли проблемы при добавлении "
                    + "записи в таблицу `groups`", e);
        }

    }

    public void deleteSensor(Sensor sensor) throws SQLException {

        try {
            checkConnection();
            String query = "DELETE FROM `sensor` WHERE `serNum`=? and `year`= ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, sensor.getSerNum());
            ps.setInt(2, sensor.getYear());
            ps.execute();
        } catch (SQLException e) {
            log.error("Возникли проблемы при "
                    + "удалении записи из таблицы `sensor`", e);
            throw new SQLException("Возникли проблемы при "
                    + "удалении записи из таблицы `sensor`", e);

        }

    }

    public void deleteGroup(Group group) throws SQLException {
        try {
            checkConnection();
            String query = "DELETE FROM `groups` WHERE `id`=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, group.getGroupNum());
            ps.execute();
        } catch (SQLException e) {
            log.error("Возникли проблемы при удалении"
                    + " записи из таблицы `groups`", e);
            throw new SQLException("Возникли проблемы при удалении"
                    + " записи из таблицы `groups`", e);
        }

    }

    public void updateSensor(Sensor sensor) throws SQLException {

        try {
            checkConnection();

            String query = "UPDATE `sensor` SET " + "`note`=?, "
                    + "`table`=?, " + "`temperatures`=?,"
                    + " `koefAmplificftion` =?," + " `TempFlags` =?, "
                    + "`Koef0Diap` = ?, " + "`DataWrote`=?,  "
                    + "`diapCount`=?, "
                    + "`span`=?, `group`=? WHERE `serNum`=? and `year`= ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, sensor.getNote());
            ps.setObject(2, sensor.getTable());
            ps.setObject(3, sensor.getTemperatures());
            ps.setInt(4, sensor.getKoefAmplificftion());
            ps.setObject(5, sensor.getTempFlags());
            ps.setInt(6, sensor.getK0diapIsWrote());
            ps.setInt(7, sensor.getDataIsWrote());
            ps.setInt(8, sensor.getDiapasonCount());
            ps.setFloat(9, sensor.getSpan());
            ps.setInt(10, sensor.getGroup());
            ps.setLong(11, sensor.getSerNum());
            ps.setInt(12, sensor.getYear());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Возникли проблемы при обновлении "
                    + "записи в таблице `sensor`", e);
            throw new SQLException("Возникли проблемы при обновлении "
                    + "записи в таблице `sensor`", e);

        }
    }

    public void updateGroup(Group group) throws SQLException {

        try {
            checkConnection();
            String query = "UPDATE `groups` SET `note`=?, `baseRow`=? WHERE `id`=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, group.getNote());
            ps.setInt(2, group.getBaseRow());
            ps.setInt(3, group.getGroupNum());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Возникли проблемы при обновлении "
                    + "записи в таблице `groups`", e);
            throw new SQLException("Возникли проблемы при обновлении "
                    + "записи в таблице `groups`", e);

        }
    }

    public void closeConnection() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
    }

    public void saveSensorToArchive(Sensor sensor) throws SQLException {

        Group group = (Group) sensor.getParent();
        try {
            checkConnection();
            Statement st = conn.createStatement();
            String query = "select COUNT(*) from archiv  where `year`= " + sensor.getYear() + " and `serNum`="
                    + sensor.getSerNum();
            ResultSet rs = st.executeQuery(query);

            boolean exist = false;

            if (rs != null)
                while (rs.next()) {
                    exist = (rs.getInt("COUNT(*)") != 0);
                }
            if (exist) {
                query = "UPDATE `archiv` SET " + // 0
                        "`note`=?, " + // 1
                        "`table`=?," + // 2
                        "`temperatures`=?," + // 3
                        "`ka` =?," + // 4
                        "`type` =?," + // 5
                        "`minP` =?," + // 6
                        "`maxP` =?," + // 7
                        "`minT` =?," + // 8
                        "`maxT` =?," + // 9
                        "`pointsP` =?," + // 10
                        "`pointsT` =?," + // 11
                        "`baseRow` =?," + // 12
                        "`diapCount`=?," + // 13
                        "`span`=?" + // 14
                        " WHERE `serNum`=? and `year`= ?";// 15--16
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, sensor.getNote());
                ps.setObject(2, sensor.getTable());
                ps.setObject(3, sensor.getTemperatures());
                ps.setInt(4, sensor.getKoefAmplificftion());
                ps.setInt(5, group.getType());
                ps.setFloat(6, group.getMinPressure());
                ps.setFloat(7, group.getMaxPressure());
                ps.setInt(8, group.getMinTemp());
                ps.setInt(9, group.getMaxTemp());
                ps.setInt(10, group.getPointsP());
                ps.setInt(11, group.getPointsT());
                ps.setInt(12, group.getBaseRow());
                ps.setInt(13, sensor.getDiapasonCount());
                ps.setFloat(14, sensor.getSpan());
                ps.setLong(15, sensor.getSerNum());
                ps.setInt(16, sensor.getYear());
                ps.executeUpdate();
            } else {
                query = "INSERT INTO `archiv` (" + // 0
                        "`note`, " + // 1
                        "`table`," + // 2
                        "`temperatures`," + // 3
                        "`ka` ," + // 4
                        "`type` ," + // 5
                        "`minP` ," + // 6
                        "`maxP` ," + // 7
                        "`minT` ," + // 8
                        "`maxT` ," + // 9
                        "`pointsP`," + // 10
                        "`pointsT` ," + // 11
                        "`baseRow`," + // 12
                        "`serNum`," + // 13
                        "`year`," + // 14
                        "`diapCount`," + // 15
                        "`span`" + // 16
                        ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, sensor.getNote());
                ps.setObject(2, sensor.getTable());
                ps.setObject(3, sensor.getTemperatures());
                ps.setInt(4, sensor.getKoefAmplificftion());
                ps.setInt(5, group.getType());
                ps.setFloat(6, group.getMinPressure());
                ps.setFloat(7, group.getMaxPressure());
                ps.setInt(8, group.getMinTemp());
                ps.setInt(9, group.getMaxTemp());
                ps.setInt(10, group.getPointsP());
                ps.setInt(11, group.getPointsT());
                ps.setInt(12, group.getBaseRow());
                ps.setLong(13, sensor.getSerNum());
                ps.setInt(14, sensor.getYear());
                ps.setInt(15, sensor.getDiapasonCount());
                ps.setFloat(16, sensor.getSpan());

                ps.execute();

            }

        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении к таблице `sensor`", e);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `sensor`", e);

        }
        // return sensors;

    }

    public JScrollPane getInfoFromDB(JTextArea area1, String year, long SerNum) {
        Float[][] tableData = null;
        String[] columnHeaders = null;
        String[] rowHeaders = null;
        try {
            if (!checkConnection())
                openConnection();

            Statement st = conn.createStatement();
            String query = "select * from sensor s, groups g where `year`="
                    + year + " and `serNum`=" + SerNum + " and g.id= s.group";

            ResultSet rs = st.executeQuery(query);
            if (rs != null)
                while (rs.next()) {
                    // Sensor sensor = new Sensor(rs.getInt("address"));
                    area1.append(" Адрес ----------------------------- "
                            + rs.getInt("address"));
                    area1.append("\n Серийный номер ------------ "
                            + rs.getLong("serNum"));
                    area1.append("\n Год -------------------------------- "
                            + year);
                    area1.append("\n Группа --------------------------- "
                            + rs.getInt("group"));
                    area1.append("\n Коэф. усиления --------------- "
                            + rs.getInt("koefAmplificftion"));
                    area1.append("\n span ------------------------------ "
                            + rs.getFloat("span"));
                    area1.append("\n Количество диапазонов --- "
                            + rs.getInt("diapCount"));
                    SensorType stp = SensorTypes.INSTANSE.sensorTypes.get(rs
                            .getInt("type"));
                    String measure = "";
                    switch (stp.measure) {
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
                    Float minP = rs.getFloat("minPressure");
                    Float maxP = rs.getFloat("maxPressure");
                    Integer minT = rs.getInt("MinTemp");
                    Integer maxT = rs.getInt("MaxTemp");
                    int pP = rs.getInt("pointsP");
                    int pT = rs.getInt("pointsT");

                    area1.append("\n Тип ----------------------------- "
                            + stp.name);
                    area1.append("\n Pmax ----------------------------- "
                            + maxP + " " + measure);
                    area1.append("\n Pmin ------------------------------ "
                            + minP + " " + measure);
                    area1.append("\n Tmax ----------------------------- "
                            + maxT);
                    area1.append("\n Tmin ------------------------------ "
                            + minT);
                    area1.append("\n Базовая строка --------------- "
                            + rs.getInt("baseRow"));

                    List<Double> tmp = Group.computeTemperatures(minT, maxT, pT);
                    columnHeaders = new String[tmp.size()];

                    for (int i = 0; i < tmp.size(); i++) {
                        columnHeaders[i] = tmp.get(i).toString();
                    }

                    tmp = Group.computePressures(minP, maxP, pP);
                    rowHeaders = new String[tmp.size() + 1];
                    int a = 1;
                    rowHeaders[0] = "Код T";
                    for (Double d : tmp)
                        rowHeaders[a++] = d.toString();


                    ObjectInputStream in;
                    try {
//						  in = new  ObjectInputStream(rs.getBinaryStream("table")); //
//					  sensor.setTable((Float[][]) in.readObject()); // // in =
//					  new ObjectInputStream(rs //
//					  .getBinaryStream("temperatures")); //
//					  sensor.setTemperatures((Float[]) in.readObject()); // //
//					  in = new ObjectInputStream(rs //
//					  .getBinaryStream("TempFlags")); //
//					  sensor.setTempFlags((Integer[]) in.readObject());


                        in = new ObjectInputStream(rs.getBinaryStream("table"));
                        Float[][] tmpTable = (Float[][]) in.readObject();

                        in = new ObjectInputStream(rs
                                .getBinaryStream("temperatures"));
                        Float[] temps = (Float[]) in.readObject();

                        tableData = new Float[tmpTable.length + 1][tmpTable[0].length];

                        tableData[0] = temps;

                        for (int i = 0; i < tmpTable.length; i++) {
                            tableData[i + 1] = tmpTable[i];

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch
                            (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            // return sensors;
        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении к таблице `sensor`", e);
            e.printStackTrace();
            // throw new SQLException(
            // "Возникли проблемы при обращении к таблице `sensor`", e);
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnHeaders);

        JTable table = new JTable(tableModel);

        JList rowHeader = new JList(rowHeaders);
        rowHeader.setFixedCellWidth(60);
        rowHeader.setFixedCellHeight(table.getRowHeight()
                + table.getRowMargin() - 1);
        rowHeader.setCellRenderer(new RowHeaderRenderer(table));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setRowHeaderView(rowHeader);


        return tableScroll;


    }

    public JScrollPane getInfoFromArchive(JTextArea area2,  String year, long SerNum) {
        Float[][] tableData = null;
        String[] columnHeaders = null;
        String[] rowHeaders = null;
        try {
            if (!checkConnection())
                openConnection();

            Statement st = conn.createStatement();
            String query = "select * from archiv where `year`=" + year
                    + " and `serNum`=" + SerNum;

            ResultSet rs = st.executeQuery(query);
            if (rs != null)
                while (rs.next()) {
                    area2.append("\n Серийный номер ------------ "
                            + rs.getLong("serNum"));
                    area2.append("\n Год -------------------------------- "
                            + year);
                    area2.append("\n \n Коэф. усиления --------------- "
                            + rs.getInt("ka"));
                    area2.append("\n span ------------------------------ "
                            + rs.getFloat("span"));
                    area2.append("\n Количество диапазонов --- "
                            + rs.getInt("diapCount"));
                    SensorType stp = SensorTypes.INSTANSE.sensorTypes.get(rs
                            .getInt("type"));
                    String measure = "";
                    switch (stp.measure) {
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

                    area2.append("\n Тип ----------------------------- "
                            + stp.name);
                    Float maxP = rs.getFloat("maxP");
                    Float minP = rs.getFloat("minP");
                    Integer maxT = rs.getInt("MaxT");
                    Integer minT = rs.getInt("MinT");

                    area2.append("\n Pmax ----------------------------- "
                            + maxP + " " + measure);
                    area2.append("\n Pmin ------------------------------ "
                            + minP + " " + measure);
                    area2.append("\n Tmax ----------------------------- "
                            + maxT);
                    area2.append("\n Tmin ------------------------------ "
                            + minT);
                    area2.append("\n Базовая строка --------------- "
                            + rs.getInt("baseRow"));
                    int pP = rs.getInt("pointsP");
                    int pT = rs.getInt("pointsT");


                    //Float deltaT =
                    List<Double> tmp = Group.computeTemperatures(minT, maxT, pT);
                    columnHeaders = new String[tmp.size()];

                    for (int i = 0; i < tmp.size(); i++) {
                        columnHeaders[i] = tmp.get(i).toString();
                    }

                    tmp = Group.computePressures(minP, maxP, pP);
                    rowHeaders = new String[tmp.size() + 1];
                    int a = 1;
                    rowHeaders[0] = "Код T";
                    for (Double d : tmp)
                        rowHeaders[a++] = d.toString();


                    ObjectInputStream in;
                    // temps = null;

                    try {
                        in = new ObjectInputStream(rs.getBinaryStream("table"));
                        Float[][] tmpTable = (Float[][]) in.readObject();

                        in = new ObjectInputStream(rs
                                .getBinaryStream("temperatures"));
                        Float[] temps = (Float[]) in.readObject();

                        tableData = new Float[tmpTable.length + 1][tmpTable[0].length];

                        tableData[0] = temps;

                        for (int i = 0; i < tmpTable.length; i++) {
                            tableData[i + 1] = tmpTable[i];

                        }


                        // in = new ObjectInputStream(rs
                        // .getBinaryStream("TempFlags"));
                        // sensor.setTempFlags((Integer[]) in.readObject());

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }

        } catch (SQLException e) {
            log.error("Возникли проблемы при обращении к таблице `sensor`", e);
            e.printStackTrace();
            // throw new SQLException(
            // "Возникли проблемы при обращении к таблице `sensor`", e);
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnHeaders);

        JTable table = new JTable(tableModel);

        JList rowHeader = new JList(rowHeaders);
        rowHeader.setFixedCellWidth(60);
        rowHeader.setFixedCellHeight(table.getRowHeight()
                + table.getRowMargin() - 1);
        rowHeader.setCellRenderer(new RowHeaderRenderer(table));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setRowHeaderView(rowHeader);


        return tableScroll;
    }


    public List<String> findYearsInArchive(Long serialNumber) throws SQLException {
        List<String> result = new ArrayList<String>();

        try {
            checkConnection();

            Statement st = conn.createStatement();
            String query = "select year from archiv  where `serNum` =" + serialNumber;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString("year"));
            }

        } catch (SQLException e1) {
            log.error("Возникли проблемы при обращении к таблице `archiv`", e1);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `archiv`", e1);
        }

        return result;
    }


    public List<String> findYearsInSensors(Long serialNumber) throws SQLException {
        List<String> result = new ArrayList<String>();

        try {
            checkConnection();

            Statement st = conn.createStatement();
            String query = "select year from sensor  where `serNum` =" + serialNumber;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString("year"));
            }

        } catch (SQLException e1) {
            log.error("Возникли проблемы при обращении к таблице `archiv`", e1);
            throw new SQLException(
                    "Возникли проблемы при обращении к таблице `archiv`", e1);
        }

        return result;
    }


}
