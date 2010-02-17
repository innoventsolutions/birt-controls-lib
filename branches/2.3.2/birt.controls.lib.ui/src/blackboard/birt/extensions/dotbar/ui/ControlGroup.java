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
package blackboard.birt.extensions.dotbar.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.designer.ui.views.attributes.providers.ChoiceSetFactory;
import org.eclipse.birt.report.designer.util.AlphabeticallyComparator;
import org.eclipse.birt.report.designer.util.DEUtil;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.ReportDesignConstants;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.api.metadata.IChoice;
import org.eclipse.birt.report.model.api.metadata.IChoiceSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import blackboard.birt.controls.ColorControl;
import blackboard.birt.controls.ControlEventHandler;
import blackboard.birt.controls.DimensionControl;
import blackboard.birt.controls.BbModifyListener;
import blackboard.birt.controls.ExpressionControl;
import blackboard.birt.controls.FontSizeControl;
import blackboard.birt.controls.FontStyleControl;
import blackboard.birt.controls.IntegerControl;
import blackboard.birt.controls.RadioGroup;
import blackboard.birt.extensions.dotbar.DotShape;
import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;
import blackboard.birt.extensions.dotbar.NumberPosition;
import blackboard.birt.extensions.util.ColorSpec;

/**
 * Encapsulates all the controlss needed for the Dotbar properties.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class ControlGroup {
	private ExpressionControl expression;
	private IntegerControl displayValue;
	private DimensionControl dotWidth;
	private DimensionControl dotHeight;
	private DimensionControl dotSpacing;
	private RadioGroup orientation;
	private Button hasBorder;
	private ColorControl borderColor;
	private Button hasFill;
	private ColorControl fillColor;
	private RadioGroup dotShape;
	private RadioGroup numberPosition;
	private DimensionControl numberWidth;
	private DimensionControl numberHeight;
	private IntegerControl wrapPoint;
	private Combo fontFamily;
	private FontSizeControl fontSize;
	private FontStyleControl fontStyle;
	private ColorControl fontColor;

	public void save(DotbarData.Editor editor) {
		editor.valueExpression = getValueExpression();
		editor.displayValue = getDisplayValue();
		editor.dotWidth = getDotWidth();
		editor.dotHeight = getDotHeight();
		editor.dotSpacing = getDotSpacing();
		editor.vertical = isVertical();
		editor.dotShape = getDotShape();
		editor.hasFill = hasFill();
		editor.fillColor = getFillColor();
		editor.hasBorder = hasBorder();
		editor.borderColor = getBorderColor();
		editor.numberPosition = getNumberPosition();
		editor.numberWidth = getNumberWidth();
		editor.numberHeight = getNumberHeight();
		editor.wrapPoint = getWrapPoint();
		editor.fontFamily = getFontFamily();
		editor.fontSize = getFontSize();
		editor.fontItalic = isItalic();
		editor.fontBold = isBold();
		editor.fontColor = getFontColor();
	}

	public void load(DotbarData config) {
		this.expression.setText(config.valueExpression);
		this.displayValue.setValue(config.displayValue);
		this.dotWidth.setValue(config.dotWidth);
		this.dotHeight.setValue(config.dotHeight);
		this.dotSpacing.setValue(config.dotSpacing);
		this.orientation.setValue(config.vertical ? 1 : 0);
		this.hasBorder.setSelection(config.hasBorder);
		RGB borderRGB = new RGB(config.borderColor.r, config.borderColor.g,
				config.borderColor.b);
		this.borderColor.setRGB(borderRGB);
		this.hasFill.setSelection(config.hasFill);
		RGB fillRGB = new RGB(config.fillColor.r, config.fillColor.g,
				config.fillColor.b);
		this.fillColor.setRGB(fillRGB);
		this.dotShape.setValue(config.dotShape.index);
		this.numberPosition.setValue(config.numberPosition.index);
		this.numberWidth.setValue(config.numberWidth);
		this.numberHeight.setValue(config.numberHeight);
		this.wrapPoint.setValue(config.wrapPoint);
		this.borderColor.setEnabled(this.hasBorder.getSelection());
		this.fillColor.setEnabled(this.hasFill.getSelection());
		this.fontFamily.setText(getFontDisplayName(config.fontFamily));
		this.fontSize.setFontSizeValue(config.fontSize);
		this.fontStyle.setItalic(config.fontItalic);
		this.fontStyle.setBold(config.fontBold);
		RGB fontRGB = new RGB(config.fontColor.r, config.fontColor.g,
				config.fontColor.b);
		this.fontColor.setRGB(fontRGB);
	}

	public GridLayout getGridLayout() {
		return new GridLayout(4, false);
	}

	public void build(final Composite composite,
			final ControlEventHandler handler, final Color backgroundColor) {
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		{
			createLabel(composite, "Expression:", backgroundColor);
			expression = new ExpressionControl(composite, SWT.NONE, handler);
			expression.setBackground(backgroundColor);
		}
		this.displayValue = createIntegerControl(composite,
				"Designer Display Value:", backgroundColor);
		this.orientation = createRadioGroup(composite, "Orientation:",
				new String[] { "Horizontal", "Vertical" }, backgroundColor);
		this.wrapPoint = createIntegerControl(composite, "Wrap Point:",
				backgroundColor);
		createDotControls(composite, backgroundColor);
		createNumberControls(composite, backgroundColor);
		if (handler != null) {
			expression.addFocusListener(new FocusAdapter() {
				public void focusLost(org.eclipse.swt.events.FocusEvent e) {
					handler.updateModel(DotbarItem.VALUE_EXPRESSION_PROP);
				};
			});
			expression.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event arg0) {
					handler.updateModel(DotbarItem.VALUE_EXPRESSION_PROP);
				}
			});
			addModifyListener(this.displayValue, handler,
					DotbarItem.DISPLAY_VALUE_PROP);
			addFocusListener(this.displayValue, handler,
					DotbarItem.DISPLAY_VALUE_PROP);
			addModifyListener(this.dotWidth, handler, DotbarItem.DOT_WIDTH_PROP);
			addFocusListener(this.dotWidth, handler, DotbarItem.DOT_WIDTH_PROP);
			addModifyListener(this.dotHeight, handler,
					DotbarItem.DOT_HEIGHT_PROP);
			addFocusListener(this.dotHeight, handler,
					DotbarItem.DOT_HEIGHT_PROP);
			addModifyListener(this.dotSpacing, handler,
					DotbarItem.DOT_SPACING_PROP);
			addFocusListener(this.dotSpacing, handler,
					DotbarItem.DOT_SPACING_PROP);
			addModifyListener(this.numberWidth, handler,
					DotbarItem.NUMBER_WIDTH_PROP);
			addFocusListener(this.numberWidth, handler,
					DotbarItem.NUMBER_WIDTH_PROP);
			addModifyListener(this.numberHeight, handler,
					DotbarItem.NUMBER_HEIGHT_PROP);
			addFocusListener(this.numberHeight, handler,
					DotbarItem.NUMBER_HEIGHT_PROP);
			addModifyListener(this.wrapPoint, handler,
					DotbarItem.WRAP_POINT_PROP);
			addFocusListener(this.wrapPoint, handler,
					DotbarItem.WRAP_POINT_PROP);
			setRadioListener(this.orientation, handler,
					DotbarItem.VERTICAL_PROP);
			setRadioListener(this.dotShape, handler, DotbarItem.DOT_SHAPE_PROP);
			setRadioListener(this.numberPosition, handler,
					DotbarItem.NUMBER_POSITION_PROP);
			addSelectionListener(this.hasBorder, handler,
					DotbarItem.HAS_BORDER_PROP);
			addFocusListener(this.hasBorder, handler,
					DotbarItem.HAS_BORDER_PROP);
			addSelectionListener(this.hasFill, handler,
					DotbarItem.HAS_FILL_PROP);
			addFocusListener(this.hasFill, handler, DotbarItem.HAS_FILL_PROP);
			borderColor.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event arg0) {
					handler.updateModel(DotbarItem.BORDER_COLOR_PROP);
				}
			});
			fillColor.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event arg0) {
					handler.updateModel(DotbarItem.FILL_COLOR_PROP);
				}
			});
			fontColor.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event arg0) {
					handler.updateModel(DotbarItem.FONT_COLOR_PROP);
				}
			});
			this.hasBorder.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent arg0) {
					boolean enabled = hasBorder.getSelection();
					borderColor.setEnabled(enabled);
				}

				public void widgetSelected(SelectionEvent arg0) {
					boolean enabled = hasBorder.getSelection();
					borderColor.setEnabled(enabled);
				}
			});
			this.hasFill.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent arg0) {
					boolean enabled = hasFill.getSelection();
					fillColor.setEnabled(enabled);
				}

				public void widgetSelected(SelectionEvent arg0) {
					boolean enabled = hasFill.getSelection();
					fillColor.setEnabled(enabled);
				}
			});
			addFocusListener(this.fontFamily, handler,
					DotbarItem.FONT_FAMILY_PROP);
			addSelectionListener(this.fontFamily, handler,
					DotbarItem.FONT_FAMILY_PROP);
			fontSize.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event event) {
					handler.updateModel(DotbarItem.FONT_SIZE_PROP);
				}
			});
			// addSelectionListener( this.fontSizeNumber, handler, DotbarItem.FONT_SIZE_PROP );
			this.fontStyle.addModifyListener(new BbModifyListener() {
				public void onModified() {
					handler.updateModel(DotbarItem.FONT_STYLE_PROP);
					handler.updateModel(DotbarItem.FONT_WEIGHT_PROP);
				}
			});
		}
	}

	private void createNumberControls(Composite parent, Color backgroundColor) {
		final Group group = new Group(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(8, false);
		group.setLayout(gridLayout);
		{
			GridData gridData = new GridData();
			gridData.horizontalSpan = 4;
			group.setLayoutData(gridData);
		}
		group.setText("Number");
		this.numberPosition = createRadioGroup(group, "Position:",
				NumberPosition.getNames(), backgroundColor);
		{
			GridData gridData = new GridData();
			gridData.horizontalSpan = 3;
			this.numberPosition.setLayoutData(gridData);
		}
		this.numberWidth = createDimensionBuilder(group, "Width:",
				backgroundColor, DotbarItem.NUMBER_WIDTH_PROP);
		this.numberHeight = createDimensionBuilder(group, "Height:",
				backgroundColor, DotbarItem.NUMBER_HEIGHT_PROP);
		this.fontFamily = createFontFamilyControl(group, "Font:",
				backgroundColor);
		{
			GridData gridData = new GridData();
			gridData.horizontalSpan = 3;
			this.fontFamily.setLayoutData(gridData);
		}
		this.fontSize = createFontSizeBuilder(group, "Size:", backgroundColor);
		this.fontStyle = createFontStyleBuilder(group, "Style:",
				backgroundColor);
		this.fontColor = createColor(group, "Color:", backgroundColor);
		{
			GridData gridData = new GridData();
			gridData.horizontalSpan = 3;
			this.fontColor.setLayoutData(gridData);
		}
	}

	private final Map<String, String> fontDisplayNameMap = new HashMap<String, String>();
	private final Map<String, String> fontNameMap = new HashMap<String, String>();

	private Combo createFontFamilyControl(Group group, String string,
			Color backgroundColor) {
		createLabel(group, string, backgroundColor);
		Combo combo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		final IChoiceSet choiceSet = ChoiceSetFactory.getElementChoiceSet(
				ReportDesignConstants.STYLE_ELEMENT,
				StyleHandle.FONT_FAMILY_PROP);
		List<IChoice> choiceList = new ArrayList<IChoice>();
		for (IChoice choice : choiceSet.getChoices()) {
			String displayName = choice.getDisplayName();
			final Object choiceValue = choice.getValue();
			if (choiceValue instanceof String) {
				final String fontName = (String) choiceValue;
				fontDisplayNameMap.put(displayName, fontName);
				fontNameMap.put(fontName, displayName);
			} else {
				fontDisplayNameMap.put(displayName, displayName);
				fontNameMap.put(displayName, displayName);
			}
			choiceList.add(choice);
		}
		final List<String> list = new ArrayList<String>();
		list.add("Auto");
		Collections.sort(choiceList, new AlphabeticallyComparator());
		for (IChoice choice : choiceList)
			list.add(choice.getDisplayName());
		final String[] sysFont = DEUtil.getSystemFontNames();
		for (String item : sysFont) {
			String name = DEUtil.RemoveQuote(item);
			list.add(name);
			fontDisplayNameMap.put(name, name);
			fontNameMap.put(name, name);
		}
		combo.setItems(list.toArray(new String[0]));
		combo.setText(combo.getItem(0));
		return combo;
	}

	private String getFontName(String fontDisplayName) {
		String fontName = fontDisplayNameMap.get(fontDisplayName);
		return fontName;
	}

	private String getFontDisplayName(String fontName) {
		String unquotedFontName = DEUtil.RemoveQuote(fontName);
		String displayName = fontNameMap.get(unquotedFontName);
		return displayName;
	}

	private FontStyleControl createFontStyleBuilder(Group group, String string,
			Color backgroundColor) {
		createLabel(group, string, backgroundColor);
		FontStyleControl builder = new FontStyleControl(group, SWT.NONE);
		final GridData gridData = new GridData();
		builder.setLayoutData(gridData);
		return builder;
	}

	private FontSizeControl createFontSizeBuilder(Group group, String string,
			Color backgroundColor) {
		createLabel(group, string, backgroundColor);
		FontSizeControl builder = new FontSizeControl(group, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.widthHint = 200;
		builder.setLayoutData(gridData);
		return builder;
	}

	private void createDotControls(Composite parent, Color backgroundColor) {
		final Group group = new Group(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(6, false);
		group.setLayout(gridLayout);
		{
			GridData gridData = new GridData();
			gridData.horizontalSpan = 4;
			group.setLayoutData(gridData);
		}
		group.setText("Dot");
		this.dotSpacing = createDimensionBuilder(group, "Spacing:",
				backgroundColor, DotbarItem.DOT_SPACING_PROP);
		this.dotWidth = createDimensionBuilder(group, "Width:",
				backgroundColor, DotbarItem.DOT_WIDTH_PROP);
		createBorder(group, backgroundColor);
		this.dotShape = createRadioGroup(group, "Shape:", DotShape.getNames(),
				backgroundColor);
		{
			GridData gridData = new GridData();
			// gridData.horizontalSpan = 5;
			this.dotShape.setLayoutData(gridData);
		}
		this.dotHeight = createDimensionBuilder(group, "Height:",
				backgroundColor, DotbarItem.DOT_HEIGHT_PROP);
		createFill(group, backgroundColor);
	}

	private void createBorder(final Composite parent,
			final Color backgroundColor) {
		createLabel(parent, "Border:", backgroundColor);
		final Composite composite = new Composite(parent, SWT.NONE);
		{
			final GridLayout gridLayout = new GridLayout(2, false);
			composite.setLayout(gridLayout);
		}
		this.hasBorder = createCheckbox(composite, backgroundColor);
		this.borderColor = new ColorControl(composite, SWT.NONE);
		{
			final IChoiceSet choiceSet = ChoiceSetFactory
					.getElementChoiceSet(ReportDesignConstants.STYLE_ELEMENT,
							StyleHandle.COLOR_PROP);
			this.borderColor.setChoiceSet(choiceSet);
		}
	}

	private void createFill(final Composite parent, final Color backgroundColor) {
		createLabel(parent, "Fill:", backgroundColor);
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		composite.setLayout(gridLayout);
		this.hasFill = createCheckbox(composite, backgroundColor);
		this.fillColor = new ColorControl(composite, SWT.NONE);
		{
			final IChoiceSet choiceSet = ChoiceSetFactory
					.getElementChoiceSet(ReportDesignConstants.STYLE_ELEMENT,
							StyleHandle.COLOR_PROP);
			this.fillColor.setChoiceSet(choiceSet);
		}
	}

	private RadioGroup createRadioGroup(Composite composite, String labelText,
			String[] buttonLabels, Color backgroundColor) {
		createLabel(composite, labelText, backgroundColor);
		return new RadioGroup(composite, buttonLabels, 0, backgroundColor);
	}

	private void addModifyListener(final IntegerControl control,
			final ControlEventHandler handler, final String prop) {
		control.addModifyListener(new BbModifyListener() {
			public void onModified() {
				handler.updateModel(prop);
			}
		});
	}

	private void addModifyListener(final DimensionControl control,
			final ControlEventHandler handler, final String prop) {
		control.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event arg0) {
				handler.updateModel(prop);
			}
		});
	}

	private void addFocusListener(final IntegerControl control,
			final ControlEventHandler handler, final String prop) {
		control.addFocusListener(new FocusAdapter() {
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				handler.updateModel(prop);
			};
		});
	}

	private void addFocusListener(final Control control,
			final ControlEventHandler handler, final String prop) {
		control.addFocusListener(new FocusAdapter() {
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				handler.updateModel(prop);
			};
		});
	}

	private void addSelectionListener(final Combo combo,
			final ControlEventHandler handler, final String prop) {
		combo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				handler.updateModel(prop);
			}
		});
	}

	private void addSelectionListener(final Button button,
			final ControlEventHandler handler, final String prop) {
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				handler.updateModel(prop);
			}
		});
	}

	private void setRadioListener(final RadioGroup radioGroup,
			final ControlEventHandler handler, final String prop) {
		radioGroup.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event arg0) {
				handler.updateModel(prop);
			}
		});
	}

	private Label createLabel(final Composite parent, final String labelText,
			final Color backgroundColor) {
		final Label label = new Label(parent, SWT.None);
		label.setText(labelText);
		label.setBackground(backgroundColor);
		return label;
	}

	private ColorControl createColor(final Composite parent,
			final String labelText, final Color backgroundColor) {
		createLabel(parent, labelText, backgroundColor);
		final ColorControl colorBuilder = new ColorControl(parent, SWT.None);
		{
			final GridData gridData = new GridData();
			colorBuilder.setLayoutData(gridData);
		}
		{
			final IChoiceSet choiceSet = ChoiceSetFactory
					.getElementChoiceSet(ReportDesignConstants.STYLE_ELEMENT,
							StyleHandle.COLOR_PROP);
			colorBuilder.setChoiceSet(choiceSet);
		}
		return colorBuilder;
	}

	private DimensionControl createDimensionBuilder(final Composite parent,
			final String labelText, final Color backgroundColor,
			final String propName) {
		createLabel(parent, labelText, backgroundColor);
		final DimensionControl builder = new DimensionControl(parent, SWT.None,
				DotbarItem.EXTENSION_NAME, propName);
		final GridData gridData = new GridData();
		builder.setLayoutData(gridData);
		return builder;
	}

	private IntegerControl createIntegerControl(final Composite parent,
			final String labelText, final Color backgroundColor) {
		createLabel(parent, labelText, backgroundColor);
		final IntegerControl integerControl = new IntegerControl(parent,
				SWT.None, false);
		final GridData gridData = new GridData();
		gridData.widthHint = 32;
		gridData.heightHint = 17;
		integerControl.setLayoutData(gridData);
		return integerControl;
	}

	private Button createCheckbox(final Composite parent,
			final Color backgroundColor) {
		final Button button = new Button(parent, SWT.CHECK);
		final GridData gridData = new GridData();
		button.setLayoutData(gridData);
		button.setBackground(backgroundColor);
		return button;
	}

	private interface Updater {
		void update(DotbarItem dotbarItem, ControlGroup controlGroup)
				throws SemanticException;
	}

	private static final Map<String, Updater> UPDATERS;
	static {
		UPDATERS = new HashMap<String, Updater>();
		UPDATERS.put(DotbarItem.VALUE_EXPRESSION_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem
						.setValueExpression(controlGroup.getValueExpression());
			}
		});
		UPDATERS.put(DotbarItem.DISPLAY_VALUE_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setDisplayValue(controlGroup.getDisplayValue());
			}
		});
		UPDATERS.put(DotbarItem.DOT_WIDTH_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setDotWidth(controlGroup.getDotWidth());
			}
		});
		UPDATERS.put(DotbarItem.DOT_HEIGHT_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setDotHeight(controlGroup.getDotHeight());
			}
		});
		UPDATERS.put(DotbarItem.DOT_SPACING_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setDotSpacing(controlGroup.getDotSpacing());
			}
		});
		UPDATERS.put(DotbarItem.VERTICAL_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setVertical(controlGroup.isVertical());
			}
		});
		UPDATERS.put(DotbarItem.DOT_SHAPE_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setDotShape(controlGroup.getDotShape());
			}
		});
		UPDATERS.put(DotbarItem.HAS_FILL_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFill(controlGroup.hasFill());
			}
		});
		UPDATERS.put(DotbarItem.FILL_COLOR_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFillColor(controlGroup.getFillColor());
			}
		});
		UPDATERS.put(DotbarItem.HAS_BORDER_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setBorder(controlGroup.hasBorder());
			}
		});
		UPDATERS.put(DotbarItem.BORDER_COLOR_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setBorderColor(controlGroup.getBorderColor());
			}
		});
		UPDATERS.put(DotbarItem.NUMBER_POSITION_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setNumberPosition(controlGroup.getNumberPosition());
			}
		});
		UPDATERS.put(DotbarItem.NUMBER_WIDTH_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setNumberWidth(controlGroup.getNumberWidth());
			}
		});
		UPDATERS.put(DotbarItem.NUMBER_HEIGHT_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setNumberHeight(controlGroup.getNumberHeight());
			}
		});
		UPDATERS.put(DotbarItem.WRAP_POINT_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setWrapPoint(controlGroup.getWrapPoint());
			}
		});
		UPDATERS.put(DotbarItem.FONT_FAMILY_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFontFamily(controlGroup.getFontFamily());
			}
		});
		UPDATERS.put(DotbarItem.FONT_SIZE_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFontSize(controlGroup.getFontSize());
			}
		});
		UPDATERS.put(DotbarItem.FONT_STYLE_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFontItalic(controlGroup.isItalic());
			}
		});
		UPDATERS.put(DotbarItem.FONT_WEIGHT_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFontBold(controlGroup.isBold());
			}
		});
		UPDATERS.put(DotbarItem.FONT_COLOR_PROP, new Updater() {
			public void update(final DotbarItem dotbarItem,
					final ControlGroup controlGroup) throws SemanticException {
				dotbarItem.setFontColor(controlGroup.getFontColor());
			}
		});
	}

	public void updateModel(final DotbarItem dotbarItem, final String propName)
			throws SemanticException {
		final Updater updater = UPDATERS.get(propName);
		if (updater != null)
			updater.update(dotbarItem, this);
		else
			System.out.println("No updater found for " + propName);
	}

	public String getValueExpression() {
		return expression.getText();
	}

	public int getDisplayValue() {
		return displayValue.getValue();
	}

	public DimensionValue getDotWidth() {
		return dotWidth.getValue();
	}

	public DimensionValue getDotHeight() {
		return dotHeight.getValue();
	}

	public DimensionValue getDotSpacing() {
		return dotSpacing.getValue();
	}

	public boolean isVertical() {
		return orientation.value == 1;
	}

	public DotShape getDotShape() {
		return DotShape.create(this.dotShape.getButtonName());
	}

	public boolean hasFill() {
		return this.hasFill.getSelection();
	}

	public ColorSpec getFillColor() {
		RGB fillRGB = this.fillColor.getRGB();
		return new ColorSpec(fillRGB.red, fillRGB.green, fillRGB.blue);
	}

	public boolean hasBorder() {
		return hasBorder.getSelection();
	}

	public ColorSpec getBorderColor() {
		RGB borderRGB = this.borderColor.getRGB();
		return new ColorSpec(borderRGB.red, borderRGB.green, borderRGB.blue);
	}

	public NumberPosition getNumberPosition() {
		return NumberPosition.create(this.numberPosition.getButtonName());
	}

	public DimensionValue getNumberWidth() {
		return numberWidth.getValue();
	}

	public DimensionValue getNumberHeight() {
		return numberHeight.getValue();
	}

	public int getWrapPoint() {
		return wrapPoint.getValue();
	}

	public String getFontFamily() {
		return getFontName(this.fontFamily.getText());
	}

	public String getFontSize() {
		return fontSize.getFontSizeValue();
	}

	public boolean isItalic() {
		return fontStyle.isItalic();
	}

	public boolean isBold() {
		return fontStyle.isBold();
	}

	public ColorSpec getFontColor() {
		RGB fontRGB = this.fontColor.getRGB();
		return new ColorSpec(fontRGB.red, fontRGB.green, fontRGB.blue);
	}
}
