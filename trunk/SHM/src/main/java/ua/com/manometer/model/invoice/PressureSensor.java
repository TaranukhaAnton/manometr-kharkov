package ua.com.manometer.model.invoice;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue("PressureSensor")
@Table(name="pressure_sensor")
public class PressureSensor extends InvoiceItem {
    private String preamble;
    private Integer series;
    private Integer isp;
    private String model;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean P;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean VM;
    private Integer mat;
    private Integer klim;
    private Integer error;

    private Float lowLimit;
    private Float hiLimit;

    private Integer ed_izm;
    private Integer stat;
    private Integer outType;
    private Integer kmch;
    private Integer du;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean R;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean I;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean PI;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean HIM;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean TU;
    private String afterSpec;


    private static String[] klimArray = {"\u0423\u0425\u041b3.1*", "\u0423\u0425\u041b3.1*(+5..+50)", "\u0423\u0425\u041b3.1*(+5..+80)", "\u04232*", "\u04232*(-30..+50)", "\u04232*(-40..+50)", "\u04223**", "\u04223**(-5..+80)"};
    private static String[] outArr = {"42", "24", "\u221a42", "05", "50", "\u221a05"};
    private static String[] matArr = {"01", "02", "05", "07", "09", "11", "12"};
    private static String[] errArr = {"0,1", "0,15", "0,25", "0,5", "1"};
    private static String[] ed_izmArr = {"\u043a\u041f\u0430", "\u041c\u041f\u0430", "\u043a\u0433\u0441/\u0441\u043c\u00b2", "\u043a\u0433\u0441/\u043c\u00b2", "kPa", "MPa", "kgf/cm\u00b2", "kgf/m\u00b2", "bar", "mbar"};
    private static String[] statArr = {"", "0,16", "0,25", "1,6", "2,5", "4", "10", "16", "25", "32", "40"};
    private static String[] duArr = {"", "50", "80"};
    private static Integer[] koef = {1000, 1, 10, 100000, 1000, 1, 10, 100000, 10, 10000};

//    @Override
//    public InvoiceItem getClone() {
//        PressureSensor clone = new PressureSensor();
//
//        clone.setType(getType());
//        clone.price = price;
//        clone.cost = cost;
//        clone.sellingPrice = sellingPrice;
//        clone.additionalCost = additionalCost;
//        clone.transportationCost = transportationCost;
//        clone.setDeliveryTime(getDeliveryTime());
//        clone.setQuantity(getQuantity());
//        clone.setManufacturedDate(getManufacturedDate());
//
//        clone.setPreamble(preamble);
//        clone.setSeries(series);
//        clone.setIsp(isp);
//        clone.setModel(model);
//        clone.setP(P);
//        clone.setVM(VM);
//        clone.setMat(mat);
//        clone.setKlim(klim);
//        clone.setError(error);
//        clone.setLowLimit(lowLimit);
//        clone.setHiLimit(hiLimit);
//        clone.setEd_izm(ed_izm);
//        clone.setStat(stat);
//        clone.setOutType(outType);
//        clone.setKmch(kmch);
//        clone.setDu(du);
//        clone.setR(R);
//        clone.setI(I);
//        clone.setPI(PI);
//        clone.setHIM(HIM);
//        clone.setTU(TU);
//        clone.setAfterSpec(afterSpec);
//
//
//        return clone;  //To change body of implemented methods use File | Settings | File Templates.
//    }

    public String getName() {

        return toString();
    }

    public String getAfterSpec() {
        return afterSpec;
    }

    public void setAfterSpec(String afterSpec) {
        this.afterSpec = afterSpec;
    }

    public boolean isTU() {
        return TU;
    }

    public void setTU(boolean TU) {
        this.TU = TU;
    }

    public boolean isI() {
        return I;
    }

    public void setI(boolean i) {
        I = i;
    }

    public boolean isPI() {
        return PI;
    }

    public void setPI(boolean PI) {
        this.PI = PI;
    }

    public boolean isHIM() {
        return HIM;
    }

    public void setHIM(boolean HIM) {
        this.HIM = HIM;
    }

    public boolean isR() {
        return R;
    }

    public void setR(boolean r) {
        R = r;
    }

    public Integer getDu() {
        return du;
    }

    public void setDu(Integer du) {
        this.du = du;
    }

    public Integer getKmch() {
        return kmch;
    }

    public void setKmch(Integer kmch) {
        this.kmch = kmch;
    }

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public Integer getKlim() {
        return klim;
    }

    public void setKlim(Integer klim) {
        this.klim = klim;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Float getHiLimit() {
        return hiLimit;
    }

    public void setHiLimit(Float hiLimit) {
        this.hiLimit = hiLimit;
    }

    public Integer getEd_izm() {
        return ed_izm;
    }

    public void setEd_izm(Integer ed_izm) {
        this.ed_izm = ed_izm;
    }

    public Integer getMat() {
        return mat;
    }

    public void setMat(Integer mat) {
        this.mat = mat;
    }

    public boolean isVM() {
        return VM;
    }

    public void setVM(boolean VM) {
        this.VM = VM;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getIsp() {
        return isp;
    }

    public void setIsp(Integer isp) {
        this.isp = isp;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getPreamble() {
        return preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public boolean isP() {
        return P;
    }

    public void setP(boolean p) {
        P = p;
    }

    public Float getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(Float lowLimit) {
        this.lowLimit = lowLimit;
    }

    public String toString() {
        String typeAlias[] = {"\"\u0421\u0430\u0444i\u0440 \u041c\"", "\"\u0421\u0430\u0444i\u0440\"", "\u0421\u041c\u0425"};
        StringBuffer buffer = new StringBuffer();
        buffer.append(preamble);
        buffer.append(typeAlias[series]);
        buffer.append((isp == 1) ? "-\u0412\u043d" : "");
        buffer.append((isp == 2) ? "-Ex" : "");
        // buffer.append((isp != 3) ? "-" + model : "");
        buffer.append(((isp == 3) & (model.charAt(0) == '5') & (series == 1)) ? "-2" + model.substring(1, 4) : "-" + model);
        // buffer.append(((isp == 3) & (model.charAt(0) != '5')) ?"-"+ model:"");

        buffer.append((isp == 3) ? "-AC" : "");
        buffer.append((isp == 4) ? "-\u041a" : "");
        buffer.append((P) ? "-\u041f" : "");
        buffer.append((VM) ? "-\u0412\u041c" : "");

        buffer.append("-" + matArr[mat]);
        buffer.append("-" + klimArray[klim]);
        buffer.append("-" + errArr[error]);

        buffer.append("-");

        if (lowLimit != null) {
            buffer.append("(" + Float.toString(lowLimit).replace('.', ',') + "..." + Float.toString(hiLimit).replace('.', ',') + ")");
        } else {
            if (model.charAt(1) == '3') {
                Double res = ((hiLimit / 2) > (0.1 * koef[ed_izm])) ? (hiLimit - 0.1 * koef[ed_izm]) : (hiLimit / 2);


                String tmp = Double.toString(roundDownScale(res));
                // tmp.replace('.',',')

                buffer.append(tmp.replace('.', ','));
//                buffer.append(roundDownScale(res));
            } else {
                buffer.append(Float.toString(hiLimit).replace('.', ','));
            }
        }


        buffer.append(" " + ed_izmArr[ed_izm]);
        buffer.append((statArr[stat] == "") ? "" : "-" + statArr[stat]);

        buffer.append("-" + outArr[outType]);

        buffer.append((kmch == 0) ? "" : "-\u041d" + kmch);
        buffer.append((du == 0) ? "" : "-" + duArr[du]);
        buffer.append((R) ? "-P" : "");
        buffer.append(((isp == 3) & (model.charAt(0) == '5')) ? "-\u0424" : "");
        buffer.append((I) ? "-\u0418" : "");
        buffer.append((PI) ? "-\u041f\u0418" : "");
        buffer.append((HIM) ? "-\u0425\u0438\u043c" : "");
//        buffer.append((HIM) ? "-\u0425\u0438\u043c" : "");
        buffer.append(((isp == 3) & (TU)) ? " \u0422\u0423 \u0423 24275859.002-99" : "");
        buffer.append(((isp != 3) & (TU)) ? " \u0422\u0423 \u0423 24275859.003-2000" : "");
        buffer.append((afterSpec == null) ? "" : " " + afterSpec);
        return buffer.toString();

    }

    public static double roundDownScale(double aValue) {
        BigDecimal decimal = new BigDecimal(aValue);
//    decimal = decimal.setScale(10,BigDecimal.ROUND_UP);
        decimal = decimal.setScale(4, BigDecimal.ROUND_HALF_DOWN);
        double result = decimal.doubleValue();
        return result;
    }


//     public void setupMoneyFields(BigDecimal koef ) {
//
//
//        PriceFirstPart priceFirstPart = Factory.getPriceFirstDAO().findUniqueByExample(new PriceFirstPart(new Integer(model), isp, mat, (klim < 3) ? 0 : (klim < 6) ? 1 : 2, error));
//        GenericHibernateDAO<OptionsPrice> dao = Factory.getOptionsPriceDAO();
////        System.out.println("priceFirstPart.getCost() "+);
////        System.out.println("model = " + model);
////        System.out.println("isp = " + isp);
////        System.out.println("mat = " + mat);
////        System.out.println("klim = " + klim);
////        System.out.println("error = " + error);
////        System.out.println("priceFirstPart = " + priceFirstPart);
//
//        BigDecimal cost = priceFirstPart.getCost();
//        BigDecimal price = priceFirstPart.getPrice();
//
////        System.out.println("model.charAt(0)"+model.charAt(0));
//        Integer type = (model.charAt(0) == '3') ? 2 : (model.charAt(0) == '2') ? 1 : 0;
//
////        System.out.println("type = " + type);
//
//        OptionsPrice op = dao.findUniqueByExample(new OptionsPrice(type, isp, "ou" + outType));
////        System.out.println("out  " + op);
//        cost = cost.add(op.getCost());
//        price = price.add(op.getPrice());
//
//
//        if (kmch != 0) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "H" + kmch));
////            System.out.println(" kmch " + op);
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//
//
//        }
//
//        if (du != 0) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "du" + du));
////            System.out.println("du " + op);
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//        }
//        if (I) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "I"));
//            System.out.println("I " + op);
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//        }
//        if (PI) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "PI"));
////            System.out.println("PI " + op);
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//        }
//
//        if (VM) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "VM"));
////            System.out.println("VM " + op);
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//        }
//
//        if (HIM) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "HIM"));
////            System.out.println("HIM " + op);
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//        }
//        if (R) {
//            op = dao.findUniqueByExample(new OptionsPrice(type, isp, "R"));
//            cost = cost.add(op.getCost());
//            price = price.add(op.getPrice());
//        }
//
//        op = dao.findUniqueByExample(new OptionsPrice(type, isp, "GP"));
//        cost = cost.add(op.getCost());
//        price = price.add(op.getPrice());
//        this.setCost(cost);
//        this.setPrice(price);
//
//        BigDecimal  p1 = price.divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(additionalCost);
//        sellingPrice = koef.multiply(p1).add(transportationCost);
//
//      //  this.setSellingPrice(price.multiply(koef).divide(exchangeRate,2));
//    }


    public String toJSONString() {
        //{"du":0,"series":"\"\u0421\u0430\u0444i\u0440 \u041c\"","model":"5020","hiLimit":4,"lowLimit":null,"sum":"344","VM":false,"ed_izm":0,"type":0,"id":3,"kmch":0,"preamble":"","name":"\"\u0421\u0430\u0444i\u0440 \u041c\"-5020-01-\u0423\u0425\u041b3.1*-0,1-4,0\u043a\u041f\u0430-42 ","quantity":1,"manufacturedDate":null,"HIM":false,"error":0,"afterSpec":"","mat":0,"isp":0,"cost":"235","stat":0,"i":false,"sellingPrice":"344","price":"234","deliveryTime":45,"r":false,"PI":false,"p":false,"TU":false,"outType":0,"klim":0}
        StringBuffer buf = new StringBuffer();
        buf.append("{\"du\":" + du + ",");
        buf.append("\"model\":\"" + model + "\",");
        buf.append("\"hiLimit\":" + hiLimit + ",");
        buf.append("\"lowLimit\":" + lowLimit + ",");
        buf.append("\"VM\":" + VM + ",");
        buf.append("\"ed_izm\":" + ed_izm + ",");
        buf.append("\"type\":" + series + ",");
        // buf.append("\"id\":3,");
        buf.append("\"kmch\":" + kmch + ",");
        buf.append("\"preamble\":\"" + preamble + "\",");
        buf.append("\"HIM\":" + HIM + ",");
        buf.append("\"error\":" + error + ",");
        buf.append("\"afterSpec\":\"" + afterSpec + "\",");
        buf.append("\"mat\":" + mat + ",");
        buf.append("\"isp\":" + isp + ",");
        buf.append("\"stat\":" + stat + ",");
        buf.append("\"i\":" + I + ",");
        buf.append("\"r\":" + R + ",");
        buf.append("\"PI\":" + PI + ",");
        buf.append("\"p\":" + P + ",");
        buf.append("\"TU\":" + TU + ",");
        buf.append("\"outType\":" + outType + ",");
        buf.append("\"klim\":" + klim + "}");

//        String result = "{\"du\":0,\"model\":\"5020\",\"hiLimit\":4,\"lowLimit\":null," +
//                "\"VM\":false,\"ed_izm\":0,\"type\":0,\"id\":3,\"kmch\":0,\"preamble\":\"\",\"HIM\":false," +
//                "\"error\":0,\"afterSpec\":\"\",\"mat\":0,\"isp\":0,\"stat\":0,\"i\":false,\"r\":false," +
//                "\"PI\":false,\"p\":false,\"TU\":false,\"outType\":0,\"klim\":0}";


        return buf.toString();
    }


}
