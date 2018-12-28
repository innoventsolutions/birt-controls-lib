/*
 * Copyright (c) 2008-2015  Innovent Solutions, Inc.
 * 
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms 
 * of the Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 	Scott Rosenbaum - Innovent Solutions
 *  Steve Schafer - Innovent Solutions
 * 				 
 */
package blackboard.birt.extensions.rotatedtext;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.extension.IBaseResultSet;
import org.eclipse.birt.report.engine.extension.ICubeResultSet;
import org.eclipse.birt.report.engine.extension.IQueryResultSet;
import org.eclipse.birt.report.engine.extension.ReportItemPresentationBase;
import org.eclipse.birt.report.engine.extension.Size;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;

/**
 * Handles the rendering of the control during report generation. This class is
 * specified in the org.eclipse.birt.report.engine.reportitemPresentation
 * extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class RotatedTextPresentationImpl extends ReportItemPresentationBase {
	protected RotatedTextItem textItem;
	protected static int index = 0;
	
	protected Size size;

	@Override
	public void setModelObject(final ExtendedItemHandle modelHandle) {
		try {
			this.textItem = (RotatedTextItem) modelHandle.getReportItem();
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getOutputType() {
		return OUTPUT_AS_IMAGE_WITH_MAP;
	}
	
	@Override
	public Size getSize() {
	  return size;
	}

	@Override
	public Object onRowSets(final IBaseResultSet[] results)
			throws BirtException {
		if (this.textItem == null)
			return null;

		final RotatedTextData data = new RotatedTextData(this.textItem);
		String text = this.evaluate(data.text, results);
		if (text == null || text.length() == 0)
			text = " ";
		final String url = this.evaluate(data.linkURL, results);

		TextImage image = SwingGraphicsUtil.createRotatedTextImage(text, data, this.dpi, 1, "in", getImageMIMEType());
		size = new Size();
		size.setWidth(image.getWidth());
		size.setHeight(image.getHeight());
		size.setUnit("px");
		return new Object[] {
		    image.getImage(),
				this.createImageMap(url, text, image.getWidth(), image.getHeight()) };
	}

	protected String evaluate(final String expression,
			final IBaseResultSet[] results) throws BirtException {
		String text = null;
		if (expression == null || "null".equalsIgnoreCase(expression))
			return null;
		if (results != null && results.length > 0) {
			final IBaseResultSet baseResultSet = results[0];
			if (baseResultSet instanceof IQueryResultSet) {
				final IQueryResultSet queryResultSet = (IQueryResultSet) baseResultSet;
				if (queryResultSet.isBeforeFirst())
					queryResultSet.next();
				final Object object = queryResultSet.evaluate(expression);
				text = String.valueOf(object);
			} else if (baseResultSet instanceof ICubeResultSet) {
				final ICubeResultSet cubeResultSet = (ICubeResultSet) baseResultSet;
				final Object object = cubeResultSet.evaluate(expression);
				text = String.valueOf(object);
			} else if (baseResultSet != null)
				text = baseResultSet.getClass().getName() + " " + expression;
			else
				text = expression;
		} else {
			final Object object = this.context.evaluate(expression);
			text = String.valueOf(object);
		}
		return text;
	}

	protected String createImageMap(final String url, final String text,
			final int width, final int height) {
		final StringBuilder sb = new StringBuilder();

		// There is a bug in BIRT 2.5.2 that results in extra white-space
		// between attribute definitions
		// becoming part of the key. For example given
		// "<area    id='id-val'...", a bad regular expression
		// in the engine will end up creating a Map entry with key="   id"; this
		// causes errors later when
		// the engine tries to locate "id" in this Map.
		//
		sb.append("<area ");
		sb.append("id=\"RotatedTextImageMapArea" + index + "\" ");
		sb.append("shape =\"rect\" ");
		sb.append("coords=\"(0,0," + width + "," + height + ")\" ");
		if (url != null)
			sb.append("href =\"" + url + "\" ");
		sb.append("target=\"_self\"");
		sb.append("/>");
		// sb.append(">\n");
		// sb.append("</area>");
		sb.append("<script>");
		sb
				.append(" var areaNode = document.getElementById(\"RotatedTextImageMapArea"
						+ index + "\");");
		sb.append(" if(areaNode != null)");
		sb.append(" {");
		sb.append("   areaNode.setAttribute(\"alt\", \""
				+ text.replace("\"", "'") + "\");");
		sb.append("   areaNode.setAttribute(\"title\", \""
				+ text.replace("\"", "'") + "\");");
		sb.append(" }");
		sb.append(" else");
		sb.append("  alert(\"area not found\");");
		sb.append("</script>");
		index++;
		return sb.toString();
	}
}
