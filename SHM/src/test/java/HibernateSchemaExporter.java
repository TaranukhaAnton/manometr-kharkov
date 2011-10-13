import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import java.io.File;
import java.io.IOException;

/**
 * @author Denis Bondarenko
 */
public class HibernateSchemaExporter {

    private final static Logger log = LoggerFactory.getLogger(HibernateSchemaExporter.class);
    private static final String CREATE_SCHEMA_FILE = "src/test/resources/sql/generated-schema.sql";

    public static void main(String[] args) throws IOException {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/test/resources/spring-servlet.xml");
        LocalSessionFactoryBean sessionFactory = (LocalSessionFactoryBean) context.getBean("&sessionFactory");
        Configuration configuration = sessionFactory.getConfiguration();
        File file = new File(CREATE_SCHEMA_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Exporting schema to file: " + file.getAbsolutePath());
        SchemaExport schemaExport = new SchemaExport(configuration);
        schemaExport.setOutputFile(file.getAbsolutePath());
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");

        schemaExport.execute(false, false, false, true);
    }
}
