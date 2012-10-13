package application;

import javax.servlet.*;
import java.io.IOException;

public class SetCharacterEncodingFilter implements Filter {

	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = true;

	public void destroy() {

		this.encoding = null;
		this.filterConfig = null;

	}

	/**
	 * 148 * Select and set (if specified) the character encoding to be used to
	 * 149 * interpret request parameters for this request. 150 * 151 * @param
	 * request The servlet request we are processing 152 * @param result The
	 * servlet response we are creating 153 * @param chain The filter chain we
	 * are processing 154 * 155 * @exception IOException if an input/output
	 * error occurs 156 * @exception ServletException if a servlet error occurs
	 * 157
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// Conditionally select and set the character encoding to be used
		if (ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if (encoding != null)
				request.setCharacterEncoding(encoding);
		}

		// Pass control on to the next filter
		chain.doFilter(request, response);

	}

	/**
	 * 176 * Place this filter into service. 177 * 178 * @param filterConfig The
	 * filter configuration object 179
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;

	}

	/**
	 * 201 * Select an appropriate character encoding to be used, based on the
	 * 202 * characteristics of the current request and/or filter initialization
	 * 203 * parameters. If no character encoding should be set, return 204 *
	 * <code>null</code>. 205 *
	 * <p>
	 * 206 * The default implementation unconditionally returns the value
	 * configured 207 * by the <strong>encoding</strong> initialization
	 * parameter for this 208 * filter. 209 * 210 * @param request The servlet
	 * request we are processing 211
	 */
	protected String selectEncoding(ServletRequest request) {

		return (this.encoding);

	}

}
