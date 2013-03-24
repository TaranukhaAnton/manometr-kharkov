package ua.com.manometer.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Extends {@link org.springframework.aop.interceptor.CustomizableTraceInterceptor} to provide custom logging levels
 */
public class TraceInterceptor extends CustomizableTraceInterceptor {

	private static final long serialVersionUID = 287162721460370957L;
	protected static Logger logger4J = Logger.getLogger("aop");

    @Override
	protected void writeToLog(Log logger, String message, Throwable ex) {
       // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = getName();

		if (ex != null) {
			logger4J.error(name + message, ex);
		} else {
			logger4J.debug(name+message);
		}
	}

    private String getName() {
        String name="";

        //test
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            name   ="["+ authentication.getName()+"] "; //get logged in username
        }
        return name;
    }

    @Override
	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return true;
	}
}