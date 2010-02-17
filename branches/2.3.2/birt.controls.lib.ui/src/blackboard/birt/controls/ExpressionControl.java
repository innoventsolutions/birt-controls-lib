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
package blackboard.birt.controls;

import org.eclipse.birt.report.designer.internal.ui.util.UIUtil;
import org.eclipse.birt.report.designer.ui.dialogs.ExpressionBuilder;
import org.eclipse.birt.report.designer.ui.dialogs.ExpressionProvider;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ExpressionControl extends Composite {
	private Text text;
	private Button button;

	public ExpressionControl(final Composite parent, final int options,
			final ControlEventHandler handler) {
		super(parent, options);
		final GridLayout gridLayout = new GridLayout(2, false);
		this.setLayout(gridLayout);
		final Text text = new Text(this, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 200;
		gridData.heightHint = 17;
		text.setLayoutData(gridData);
		final Button button = new Button(this, SWT.PUSH);
		UIUtil.setExpressionButtonImage(button);
		this.text = text;
		this.button = button;
		this.button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				openExpression(handler.getModelHandle());
			}
		});
	}

	public String getText() {
		return text.getText();
	}

	public void setText(String text) {
		this.text.setText(text == null ? "" : text);
	}

	public void addFocusListener(FocusListener listener) {
		text.addFocusListener(listener);
	}

	private void openExpression(final ExtendedItemHandle modelHandle) {
		String oldValue = text.getText();
		ExpressionBuilder eb = new ExpressionBuilder(text.getShell(), oldValue);
		eb.setExpressionProvier(new ExpressionProvider(modelHandle));
		String result = oldValue;

		if (eb.open() == Window.OK)
			result = eb.getResult();

		if (!oldValue.equals(result)) {
			text.setText(result);
			notifyListeners(SWT.Modify, null);
		}
	}

}
