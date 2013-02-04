package ua.com.manometer.model.modeldescription;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.StringTokenizer;

@Entity
public class ModelDescription {
    public static String[] MAT = {"01", "02", "05", "07", "09", "11", "12"};
    public static String[] ERR = {"0.1", "0.15", "0.25", "0.5", "1"};
    public static String[] STAT = {"", "0.16", "0.25", "1.6", "2.5", "4.0", "10", "16", "25", "32", "40"};
    public static String[] Du = {"", "50", "80"};
    public static String[] OUT = {"42", "24", "&#8730 42", "05", "50", "&#8730 05"};
    public static String[] ISP = {"общ.", "вн", "Ex", "AC", "К"};
    public static String[] KLIM3 = {"УХЛ3.1*", "У2*", "Т3*"};


    @Id
    private Integer id;
    private Integer loLimit;
    private Integer hiLimit;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    private List<Integer> KMCH;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    private List<Integer> isp;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    private List<Integer> materials;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    private List<Integer> errors;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    private List<Integer> staticPressures;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    @Column( name ="outputSygnals")
    private List<Integer> outputs;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    private List<Integer> DU;

    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean VM;


    public ModelDescription() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoLimit() {
        return loLimit;
    }

    public void setLoLimit(Integer loLimit) {
        this.loLimit = loLimit;
    }

    public Integer getHiLimit() {
        return hiLimit;
    }

    public void setHiLimit(Integer hiLimit) {
        this.hiLimit = hiLimit;
    }

    public List<Integer> getKMCH() {
        return KMCH;
    }

    public void setKMCH(List<Integer> KMCH) {
        this.KMCH = KMCH;
    }

    public List<Integer> getIsp() {
        return isp;
    }

    public void setIsp(List<Integer> isp) {
        this.isp = isp;
    }

    public List<Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Integer> materials) {
        this.materials = materials;
    }

    public List<Integer> getErrors() {
        return errors;
    }

    public void setErrors(List<Integer> errors) {
        this.errors = errors;
    }

    public List<Integer> getStaticPressures() {
        return staticPressures;
    }

    public void setStaticPressures(List<Integer> staticPressures) {
        this.staticPressures = staticPressures;
    }

    public List<Integer> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Integer> outputs) {
        this.outputs = outputs;
    }

    public List<Integer> getDU() {
        return DU;
    }

    public void setDU(List<Integer> DU) {
        this.DU = DU;
    }

    public boolean isVM() {
        return VM;
    }

    public void setVM(boolean VM) {
        this.VM = VM;
    }

    /*   public String toJSONString() {

        String res = "";

        res += "{";
        StringTokenizer tokenizer = new StringTokenizer(this.getIsp(), "|");
        res += " \"isp\": [";
        for (; tokenizer.hasMoreTokens();) {
            String token = tokenizer.nextToken();

            res += "{\"type\": \"" + token + "\", \"text\": \"" + ISP[new Integer(token)] + "\"},";
        }
        res = res.substring(0, res.length() - 1) + "], \"mat\":[";

        tokenizer = new StringTokenizer(this.getMaterials(), "|");
        for (; tokenizer.hasMoreTokens();) {
            String token = tokenizer.nextToken();
            res += "{\"type\":\"" + token + "\", \"text\":\"" + MAT[new Integer(token)] + "\"},";
        }
        res = res.substring(0, res.length() - 1) + "], \"err\":[";

        tokenizer = new StringTokenizer(this.getErrors(), "|");
        for (; tokenizer.hasMoreTokens();) {
            String token = tokenizer.nextToken();
            res += "{\"type\": \"" + token + "\", \"text\": \"" + ERR[new Integer(token)] + "\"},";
        }
        res = res.substring(0, res.length() - 1) + "],\"stat\":";

        if (this.getStaticPressures().charAt(0) == '0') res += "\"\",\"out\":[";
        else {
            res += "[";
            tokenizer = new StringTokenizer(this.getStaticPressures(), "|");
            for (; tokenizer.hasMoreTokens();) {
                String token = tokenizer.nextToken();
                res += "{\"type\":\"" + token + "\", \"text\":\"" + STAT[new Integer(token)] + "\"},";
            }
            res = res.substring(0, res.length() - 1) + "],\"out\":[";
        }

        tokenizer = new StringTokenizer(this.getOutputSygnals(), "|");
        for (; tokenizer.hasMoreTokens();) {
            String token = tokenizer.nextToken();
            res += "{\"type\": \"" + token + "\", \"text\": \"" + OUT[new Integer(token)] + "\"},";
        }
        res = res.substring(0, res.length() - 1) + "],\"du\":";

        if (this.getDU().charAt(0) == '0') {
            res += "\"\",\"kmch\":[";
        } else {
            res += "[";
            tokenizer = new StringTokenizer(this.getDU(), "|");
            for (; tokenizer.hasMoreTokens();) {
                String token = tokenizer.nextToken();
                res += "{\"type\":\"" + token + "\", \"text\":\"" + Du[new Integer(token)] + "\"},";
            }
            res = res.substring(0, res.length() - 1) + "],\"kmch\":[";
        }
        tokenizer = new StringTokenizer(this.getKMCH(), "|");
        for (; tokenizer.hasMoreTokens();) {
            String token = tokenizer.nextToken();
            String text = (token.equals("0")) ? "" : ("Н" + token);
            res += "{\"type\": \"" + token + "\", \"text\": \"" + text + "\"},";
        }
        res = res.substring(0, res.length() - 1) + "],";
        res += "\"hilimit\":\"" + this.getHiLimit() + "\",";
        res += "\"lolimit\":\"" + this.getLoLimit() + "\",";
        res += "\"vm\":\"" + this.isVM() + "\"";
        res += "}";
        return res;
    }*/
}
