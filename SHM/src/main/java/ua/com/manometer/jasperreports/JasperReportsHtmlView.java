/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.com.manometer.jasperreports;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;

/**
 * Implementation of <code>AbstractJasperReportsSingleFormatView</code>
 * that renders report results in HTML format.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 1.1.3
 */
public class JasperReportsHtmlView extends AbstractJasperReportsSingleFormatView {

	public JasperReportsHtmlView() {
		setContentType("text/html");
	}

	@Override
	protected JRExporter createExporter() {
		return new JRHtmlExporter();
	}

	@Override
	protected boolean useWriter() {
		return true;
	}

}
