/*******************************************************************************
 * Copyright (c) 2008-2015  Innovent Solutions, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  Blackboard, Inc. - significant modification / refactor
 *  Steve Schafer - Innovent Solutions, Inc. - significant modification / refactor
 *  
 *******************************************************************************/
package blackboard.birt.extensions.rotatedtext.ui;

import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextFigure
 */
public class RotatedTextFigure extends Figure {
	private RotatedTextData lastData;
	private Image cachedImage;
	private RotatedTextItem textItem;

	RotatedTextFigure(final RotatedTextItem textItem) {
		super();
		this.textItem = textItem;

		this.addMouseListener(new MouseListener.Stub() {
			@Override
			public void mousePressed(final MouseEvent me) {
				if (me.button == 2) {
					try {
						RotatedTextFigure.this.textItem
								.setRotationAngle(RotatedTextFigure.this
										.normalize(RotatedTextFigure.this.textItem
												.getRotationAngle() + 45));
					} catch (final SemanticException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private int normalize(int angle) {
		angle = angle % 360;

		if (angle < 0)
			angle += 360;

		return angle;
	}

	@Override
	public Dimension getMinimumSize(final int hint, final int hint2) {
		return this.getPreferredSize(hint, hint2);
	}

	@Override
	public Dimension getPreferredSize(final int hint, final int hint2) {
		final Display display = Display.getCurrent();
		GC gc = null;

		try {
			final String text = this.truncateText(this.textItem.getText());
			final int angle = this.textItem.getRotationAngle();
			gc = new GC(display);
			final Font font = RotatedTextSwtUtil.getSwtFont(display,
					new RotatedTextData(this.textItem));
			gc.setFont(font);
			final Point pt = gc.textExtent(text);
			final double[] info = RotatedTextSwtUtil.computedRotatedInfo(pt.x,
					pt.y, angle);

			if (this.getBorder() != null) {
				final Insets bdInsets = this.getBorder().getInsets(this);
				return new Dimension((int) info[0] + bdInsets.getWidth(),
						(int) info[1] + bdInsets.getHeight());
			}
			return new Dimension((int) info[0], (int) info[1]);
		} finally {
			if (gc != null && !gc.isDisposed())
				gc.dispose();
		}
	}

	private String truncateText(final String text) {
		if (text == null)
			return "";
		if (text.length() > 28)
			return text.substring(0, 25) + "...";
		return text;
	}

	@Override
	protected void paintClientArea(final Graphics graphics) {
		final Rectangle r = this.getClientArea().getCopy();
		final RotatedTextData data = new RotatedTextData(this.textItem);

		if (!data.equals(this.lastData) || this.cachedImage == null
				|| this.cachedImage.isDisposed()) {
			this.lastData = data;

			if (this.cachedImage != null && !this.cachedImage.isDisposed())
				this.cachedImage.dispose();

			this.cachedImage = RotatedTextSwtUtil.createRotatedTextImage(data);
		}

		if (this.cachedImage != null && !this.cachedImage.isDisposed())
			graphics.drawImage(this.cachedImage, r.x, r.y);
	}

	void setRotatedTextItem(final RotatedTextItem item) {
		this.textItem = item;
	}

	void dispose() {
		if (this.cachedImage != null && !this.cachedImage.isDisposed()) {
			this.cachedImage.dispose();
		}
	}
}
