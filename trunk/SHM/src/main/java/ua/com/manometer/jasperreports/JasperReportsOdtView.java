package ua.com.manometer.jasperreports;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

public class JasperReportsOdtView extends AbstractJasperReportsSingleFormatView{

    public JasperReportsOdtView() {
        setContentType("application/odt");
    }

    @Override
    protected JRExporter createExporter() {
        return new JROdtExporter();
    }

    @Override
    protected boolean useWriter() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
