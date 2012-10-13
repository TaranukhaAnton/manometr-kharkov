/**
 * Created by IntelliJ IDEA.
 * User: Anton
 * Date: 10 ���� 2009
 * Time: 1:06:57
 * To change this template use File | Settings | File Templates.
 */
package application.tests;

import application.data.ModelDescription.ModelDescription;
import application.data.price.PriceFirstPart;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import application.hibernate.generic.PriceDAO;

import java.math.BigDecimal;
import java.util.List;

public class test {
    public static void main(String[] args) {
        PriceDAO DAO = Factory.getPriceFirstDAO();
        Long time;
        Long count=0l;

        //  DAO.setPrice();

        GenericHibernateDAO<ModelDescription> ddd = Factory.getModelDescriptionDAO();
        List<ModelDescription> listCO = ddd.findAll();
        time = System.currentTimeMillis();
        for (ModelDescription md : listCO) {
            for (int err = 0; err < 5; err++)
                for (int mat = 0; mat < 7; mat++)
                    for (int klim = 0; klim < 3; klim++)
                        for (int isp = 0; isp < 5; isp++) {
                            PriceFirstPart p = new PriceFirstPart();
                            p.setErr(err);
                            p.setModel(md.getId());
                            p.setKlim(klim);
                            p.setIsp(isp);
                            p.setMat(mat);
                            p.setCost(new BigDecimal("0"));
                            p.setPrice(new BigDecimal("0"));
                            p.setCostTmp(new BigDecimal("0"));
                            p.setPriceTmp(new BigDecimal("0"));                            
                            DAO.makePersistent(p);
                            count++;
                        }
        }

        System.out.println("CO" + (System.currentTimeMillis() - time) / 60000+" count = "+count);
//           count =0l;
//        GenericHibernateDAO<ModelAoDescription> ppp = Factory.getModelAoDescriptionDAO();
//        List<ModelAoDescription> listAO = ppp.findAll();
//        time = System.currentTimeMillis();
//        for (ModelAoDescription md : listAO) {
//            for (int err = 0; err < 5; err++)
//                for (int mat = 0; mat < 7; mat++)
//                    for (int klim = 0; klim < 3; klim++)
//                        for (int isp = 0; isp < 5; isp++) {
//                            PriceFirstPart p = new PriceFirstPart();
//                            p.setErr(err);
//                            p.setModel(md.getId());
//                            p.setKlim(klim);
//                            p.setIsp(isp);
//                            p.setMat(mat);
//                            p.setCost(new BigDecimal("0"));
//                            p.setPrice(new BigDecimal("0"));
//                            p.setCostTmp(new BigDecimal("0"));
//                            p.setPriceTmp(new BigDecimal("0"));
//                            DAO.makePersistent(p);
//                            count++;
//                        }
//        }
//        System.out.println("AO" + (System.currentTimeMillis() - time) / 60000+" count = "+count);
//
//
//        int[] matA = {0,1,2,5,6};
//        int[] ispA = {0,2};
//
//
//           count =0l;
//
//        GenericHibernateDAO<ModelOpDescription> qqq = Factory.getModelOpDescriptionDAO();
//        List<ModelOpDescription> listOP = qqq.findAll();
//        time = System.currentTimeMillis();
//        for (ModelOpDescription md : listOP) {
//            System.out.println("md = " + md);
//
//            for (int err = 2; err < 5; err++)
//                for (Integer matIt:matA)
//                    for (int klim = 0; klim < 3; klim++)
//                        for (Integer ispIt:ispA) {
//                            PriceFirstPart p = new PriceFirstPart();
//                            p.setErr(err);
//                            p.setModel(md.getId());
//                            p.setKlim(klim);
//                            p.setIsp(ispIt);
//                            p.setMat(matIt);
//                            p.setCost(new BigDecimal("0"));
//                            p.setPrice(new BigDecimal("0"));
//                            p.setCostTmp(new BigDecimal("0"));
//                            p.setPriceTmp(new BigDecimal("0"));
//                            DAO.makePersistent(p);
//                            count++;
//                        }
//        }
//        System.out.println("OP" + (System.currentTimeMillis() - time) / 60000+" count = "+count);


    }
    /*
    *    public void createBD() {
        try {
            String query = "INSERT INTO `pricefirstpart` (`err`,`isp`,`klim`,`mat`,`model`,`cost`,`price`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            Long[] CO = {5020l, 5030l, 5040l, 5041l, 5050l, 5051l, 5101l, 5110l, 5115l, 5120l, 5130l, 5133l, 5135l, 5140l, 5141l, 5142l, 5143l, 5145l, 5150l, 5151l, 5152l, 5153l, 5155l, 5160l, 5161l, 5162l, 5163l, 5170l, 5171l, 5201l, 5210l, 5215l, 5220l, 5230l, 5233l, 5235l, 5240l, 5241l, 5242l, 5243l, 5245l, 5301l, 5310l, 5315l, 5320l, 5330l, 5333l, 5335l, 5340l, 5341l, 5342l, 5343l, 5345l, 5350l, 5351l, 5352l, 5353l, 5355l, 5401l, 5410l, 5415l, 5420l, 5424l, 5430l, 5434l, 5440l, 5444l, 5450l, 5454l, 5460l, 5464l, 5520l, 5530l, 5540l, 5550l};
            Long[] AO = {2020l, 2030l, 2040l, 2041l, 2050l, 2101l, 2110l, 2115l, 2120l, 2130l, 2140l, 2141l, 2150l, 2151l, 2160l, 2161l, 2170l, 2171l, 2201l, 2210l, 2215l, 2220l, 2230l, 2240l, 2241l, 2301l, 2310l, 2315l, 2320l, 2330l, 2340l, 2341l, 2350l, 2351l, 2401l, 2410l, 2415l, 2420l, 2424l, 2430l, 2434l, 2440l, 2444l, 2450l, 2454l, 2460l, 2464l, 2520l, 2530l, 2540l};
            Long[] OP = {3030l, 3040l, 3041l, 3050l, 3051l, 3130l, 3131l, 3140l, 3141l, 3150l, 3151l, 3160l, 3161l, 3170l, 3171l, 3230l, 3231l, 3240l, 3241l, 3330l, 3331l, 3340l, 3341l, 3350l, 3351l};

            Long time;
            Long count;

            count = 0l;
            time = System.currentTimeMillis();
            for (Long md : CO) {
                System.out.println("md = " + md);
                for (int err = 0; err < 5; err++)
                    for (int mat = 0; mat < 7; mat++)
                        for (int klim = 0; klim < 3; klim++)
                            for (int isp = 0; isp < 5; isp++) {
                                ps.setInt(1, err);
                                ps.setInt(2, isp);
                                ps.setInt(3, klim);
                                ps.setInt(4, mat);
                                ps.setLong(5, md);
                                ps.setBigDecimal(6, new BigDecimal("0"));
                                ps.setBigDecimal(7, new BigDecimal("0"));
                                ps.execute();
                                count++;
                            }
            }
            System.out.println("CO " + (System.currentTimeMillis() - time) / 60000 + " count = " + count);

            count = 0l;
            time = System.currentTimeMillis();
            for (Long md : AO) {
                System.out.println("md = " + md);
                for (int err = 0; err < 5; err++)
                    for (int mat = 0; mat < 7; mat++)
                        for (int klim = 0; klim < 3; klim++)
                            for (int isp = 0; isp < 5; isp++) {
                                 ps.setInt(1, err);
                                ps.setInt(2, isp);
                                ps.setInt(3, klim);
                                ps.setInt(4, mat);
                                ps.setLong(5, md);
                                ps.setBigDecimal(6, new BigDecimal("0"));
                                ps.setBigDecimal(7, new BigDecimal("0"));
                                ps.execute();
                                count++;
                            }
            }
            System.out.println("AO " + (System.currentTimeMillis() - time) / 60000 + " count = " + count);


            int[] matA = {0, 1, 2, 5, 6};
            int[] ispA = {0, 2};


            count = 0l;
            time = System.currentTimeMillis();
            for (Long md : OP) {
                System.out.println("md = " + md);
                for (int err = 2; err < 5; err++)
                    for (Integer matIt : matA)
                        for (int klim = 0; klim < 3; klim++)
                            for (Integer ispIt : ispA) {
                                ps.setInt(1, err);
                                ps.setInt(2, ispIt);
                                ps.setInt(3, klim);
                                ps.setInt(4, matIt);
                                ps.setLong(5, md);
                                ps.setBigDecimal(6, new BigDecimal("0"));
                                ps.setBigDecimal(7, new BigDecimal("0"));
                                ps.execute();
                                count++;
                            }
            }
            System.out.println("OP " + (System.currentTimeMillis() - time) / 60000 + " count = " + count);


        } catch (Exception e) {
        }
    }*/
}