package application;

import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.hibernate.HibernateException;


public class HibernatePlugIn implements PlugIn {
   private String _configFilePath = "/hibernate.cfg.xml";

    /**
     * the key under which the <code>SessionFactory </code> instance is stored
     * in the <code>ServletContext</code>.
     */
    public static final String SESSION_FACTORY_KEY = SessionFactory.class.getName();

  private SessionFactory _factory = null;

   public void destroy() {
     try{
       _factory.close();
     }catch(HibernateException e){
      System.out.println("Unable to close Hibernate Session Factory: " + e.getMessage());
     }

   }

   public void init(ActionServlet servlet,
ModuleConfig config) throws ServletException {
     System.out.println("*************************************");
   System.out.println("**** Initilizing HibernatePlugIn   **********");
        Configuration configuration = null;
        URL configFileURL = null;
        ServletContext context = null;

   try{
            configFileURL =
HibernatePlugIn.class.getResource(_configFilePath);
            context = servlet.getServletContext();
            configuration = (new Configuration()).configure(configFileURL);
            _factory = configuration.buildSessionFactory();
      //Set the factory into session
      context.setAttribute(SESSION_FACTORY_KEY, _factory);

   }catch(HibernateException e){
    System.out.println("Error while initializing hibernate: " + e.getMessage());
   }
   System.out.println("**************** *********************");

   }

    /**
     * Setter for property configFilePath.
     * @param configFilePath New value of property configFilePath.
     */
    public void setConfigFilePath(String configFilePath) {
        if ((configFilePath == null) || (configFilePath.trim().length() == 0)) {
            throw new IllegalArgumentException("configFilePath cannot be blank or null.");
        }

        System.out.println("Setting ' configFilePath' to '"  + configFilePath + "'...");
        _configFilePath = configFilePath;
    }


/*(SessionFactory) servletContext.getAttribute
(HibernatePlugIn.SESSION_FACTORY_KEY);
*/

}