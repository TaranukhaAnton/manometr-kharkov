package application.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see <a
 * href="http://hibernate.org/42.html">http://hibernate.org/42.html</a>.
 */
public class HibernateSessionFactory {
	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	private static Configuration configuration = new AnnotationConfiguration();

	private static Session session;
	private static org.hibernate.SessionFactory sessionFactory;

	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 *
	 * @return Session
	 * @throws org.hibernate.HibernateException
	 */
	public static Session getSession() throws HibernateException {
//	 	Session session = threadLocal.get();


		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
           // System.out.println("создаем сессию");
			session = (sessionFactory != null) ? sessionFactory.openSession(): null;
//			threadLocal.set(session);
		}
//        System.out.println(Thread.currentThread().getName()+"  sesId ="+session.hashCode());
		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 *
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		//Session session = threadLocal.get();
//		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * Return the session factory.
	 *
	 * @return the session factory.
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
        //sessionFactory.
		return sessionFactory;
	}

	/**
	 * Return the session factory.
	 * <p>
	 * The session factory will be rebuilded in the next call.
	 *
	 * @param configFile
	 *            The configuration file to build the session factory with.
	 */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * Return the hibernate configuration.
	 *
	 * @return the hibernate configuration.
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

}
