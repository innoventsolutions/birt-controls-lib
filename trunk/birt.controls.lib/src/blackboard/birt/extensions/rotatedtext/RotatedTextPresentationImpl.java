/*
 * (C) Copyright Blackboard Inc. 1998-2009 - All Rights Reserved
 *
 * Permission to use, copy, modify, and distribute this software without prior explicit written approval is
 * strictly  prohibited. Please refer to the file "copyright.html" for further important copyright and licensing information.
 *
 * BLACKBOARD MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT.
 * BLACKBOARD SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * authors: Scott Rosenbaum / Steve Schafer, Innovent Solutions, Inc.
 */
package blackboard.birt.extensions.rotatedtext;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.extension.IBaseResultSet;
import org.eclipse.birt.report.engine.extension.ICubeResultSet;
import org.eclipse.birt.report.engine.extension.IQueryResultSet;
import org.eclipse.birt.report.engine.extension.ReportItemPresentationBase;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;

/**
 * Handles the rendering of the control during report generation. This class is specified in the org.eclipse.birt.report.engine.reportitemPresentation
 * extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class RotatedTextPresentationImpl extends ReportItemPresentationBase {
	private RotatedTextItem textItem;
	private static int index = 0;

	public void setModelObject(ExtendedItemHandle modelHandle) {
		try {
			textItem = (RotatedTextItem) modelHandle.getReportItem();
		} catch (ExtendedElementException e) {
			e.printStackTrace();
		}
	}

	public int getOutputType() {
		return OUTPUT_AS_IMAGE_WITH_MAP;
	}

	public Object onRowSets(IBaseResultSet[] results) throws BirtException {
		if (textItem == null)
			return null;

		final RotatedTextData data = new RotatedTextData(textItem);
		final String text = evaluate(data.text, results);
		final String url = evaluate(data.linkURL, results);

		final BufferedImage rotatedImage = SwingGraphicsUtil
				.createRotatedTextImage(text, data, this.dpi, 1, "in");
		ByteArrayInputStream bis = null;

		try {
			ImageIO.setUseCache(false);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
			ImageIO.write(rotatedImage, "png", ios); //$NON-NLS-1$
			ios.flush();
			ios.close();
			bis = new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Object[] {
				bis,
				createImageMap(url, text, rotatedImage.getWidth(), rotatedImage
						.getHeight()) };
	}

	private String evaluate(final String expression,
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
			final Object object = context.evaluate(expression);
			text = String.valueOf(object);
		}
		return text;
	}

	private String createImageMap(final String url, final String text,
			final int width, final int height) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<area ");
		sb.append("id=\"RotatedTextImageMapArea" + index + "\" ");
		sb.append("shape =\"rect\" ");
		sb.append("coords=\"(0,0," + width + "," + height + ")\" ");
		if (url != null)
			sb.append("href =\"" + url + "\" ");
		sb.append("target=\"_self\"");
		sb.append(">\n");
		sb.append("</area>");
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
		//    sb.append( "  var mapNode = areaNode.parentNode;" );
		//    sb.append( "  alert(\"map = \" + mapNode.nodeName);" );
		//    sb.append( "  var imgNode = mapNode.nextSibling;" );
		//    sb.append( "  while(imgNode != null && imgNode.nodeName.toLowerCase() != \"img\")" );
		//    sb.append( "  {" );
		//    sb.append( "   alert(\"'\" + imgNode.nodeName + \"'\");" );
		//    sb.append( "   imgNode = imgNode.nextSibling;" );
		//    sb.append( "  }" );
		//    sb.append( "  if(imgNode != null)" );
		//    sb.append( "   imgNode.setAttribute(\"alt\", \"" + text.replace( "\"", "'" ) + "\");" );
		//    sb.append( "  else" );
		//    sb.append( "   alert(\"IMG not found\");" );
		sb.append(" }");
		sb.append(" else");
		sb.append("  alert(\"area not found\");");
		sb.append("</script>");
		index++;
		return sb.toString();
	}
}
