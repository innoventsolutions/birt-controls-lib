package blackboard.birt.extensions.dotbar.script;

import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.script.internal.element.ReportItem;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.extension.CompatibilityStatus;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IPropertyDefinition;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.api.metadata.IMethodInfo;
import org.eclipse.birt.report.model.api.simpleapi.IReportItem;

import blackboard.birt.extensions.dotbar.DotShape;
import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;
import blackboard.birt.extensions.dotbar.NumberPosition;
import blackboard.birt.extensions.util.ColorSpec;

/**
 * @author steve
 *
 */
public class Dotbar extends ReportItem implements IDotbar {
	private final DotbarItem dotbarItem;

	public Dotbar(DotbarItem dotbarItem) {
		super(dotbarItem.getModelHandle());
		this.dotbarItem = dotbarItem;
	}

	/* (non-Javadoc)
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#canExport()
	 */
	public boolean canExport() {
		return dotbarItem.canExport();
	}

	/* (non-Javadoc)
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#checkCompatibility()
	 */
	public CompatibilityStatus checkCompatibility() {
		return dotbarItem.checkCompatibility();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#checkProperty(java.lang
	 * .String, java.lang.Object)
	 */
	public void checkProperty(String propName, Object value) throws ExtendedElementException {
		dotbarItem.checkProperty(propName, value);
	}

	public boolean equals(Object obj) {
		return dotbarItem.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#getModelHandle()
	 */
	public ExtendedItemHandle getModelHandle() {
		return dotbarItem.getModelHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#getConfiguration()
	 */
	public DotbarData getConfiguration() {
		return dotbarItem.getConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#setConfiguration(
	 * blackboard.birt.extensions.dotbar.DotbarData)
	 */
	public void setConfiguration(DotbarData config) throws SemanticException {
		dotbarItem.setConfiguration(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#getInt(java.lang.
	 * String)
	 */
	public int getInt(String propName) {
		return dotbarItem.getInt(propName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getBoolean(java.lang.
	 * String)
	 */
	public boolean getBoolean(String propName) {
		return dotbarItem.getBoolean(propName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#getMethods()
	 */
	public IPropertyDefinition[] getMethods() {
		return dotbarItem.getMethods();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getMethods(java.lang.
	 * String)
	 */
	public IMethodInfo[] getMethods(String methodName) {
		return dotbarItem.getMethods(methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getPredefinedStyles()
	 */
	@SuppressWarnings("rawtypes")
	public List getPredefinedStyles() {
		return dotbarItem.getPredefinedStyles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getProperty(java.lang.
	 * String)
	 */
	public Object getProperty(String propName) {
		return dotbarItem.getProperty(propName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getPropertyDefinitions(
	 * )
	 */
	public IPropertyDefinition[] getPropertyDefinitions() {
		return dotbarItem.getPropertyDefinitions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getReferencedStyle()
	 */
	public StyleHandle[] getReferencedStyle() {
		return dotbarItem.getReferencedStyle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getRowExpressions()
	 */
	@SuppressWarnings("rawtypes")
	public List getRowExpressions() {
		return dotbarItem.getRowExpressions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#
	 * getScriptPropertyDefinition()
	 */
	public IPropertyDefinition getScriptPropertyDefinition() {
		return dotbarItem.getScriptPropertyDefinition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#getSimpleElement()
	 */
	public IReportItem getSimpleElement() {
		return dotbarItem.getSimpleElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#getString(java.lang.
	 * String)
	 */
	public String getString(String propName) {
		return dotbarItem.getString(propName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#
	 * handleCompatibilityIssue()
	 */
	public void handleCompatibilityIssue() {
		dotbarItem.handleCompatibilityIssue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#hasFixedSize()
	 */
	public boolean hasFixedSize() {
		return dotbarItem.hasFixedSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#hashCode()
	 */
	public int hashCode() {
		return dotbarItem.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#
	 * refreshPropertyDefinition()
	 */
	public boolean refreshPropertyDefinition() {
		return dotbarItem.refreshPropertyDefinition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#set(java.lang.String,
	 * java.lang.Object)
	 */
	public void set(String propName, Object text) throws SemanticException {
		dotbarItem.set(propName, text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setValueExpression(java
	 * .lang.String)
	 */
	public void setValueExpression(String valueExpression) throws SemanticException {
		dotbarItem.setValueExpression(valueExpression);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setDisplayValue(int)
	 */
	public void setDisplayValue(int displayValue) throws SemanticException {
		dotbarItem.setDisplayValue(displayValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setDotWidth(org.eclipse
	 * .birt.report.model.api.metadata.DimensionValue)
	 */
	public void setDotWidth(DimensionValue dotWidth) throws SemanticException {
		dotbarItem.setDotWidth(dotWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#setDotHeight(org.
	 * eclipse.birt.report.model.api.metadata.DimensionValue)
	 */
	public void setDotHeight(DimensionValue dotHeight) throws SemanticException {
		dotbarItem.setDotHeight(dotHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#setDotSpacing(org.
	 * eclipse.birt.report.model.api.metadata.DimensionValue)
	 */
	public void setDotSpacing(DimensionValue dotSpacing) throws SemanticException {
		dotbarItem.setDotSpacing(dotSpacing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setVertical(boolean)
	 */
	public void setVertical(boolean vertical) throws SemanticException {
		dotbarItem.setVertical(vertical);
	}

	public void setDotShape(String dotShape) throws SemanticException {
		dotbarItem.setDotShape(DotShape.create(dotShape));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#setFill(boolean)
	 */
	public void setFill(boolean hasFill) throws SemanticException {
		dotbarItem.setFill(hasFill);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#setFillColor(int,
	 * int, int)
	 */
	public void setFillColor(int r, int g, int b) throws SemanticException {
		dotbarItem.setFillColor(new ColorSpec(r, g, b));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#setBorder(boolean)
	 */
	public void setBorder(boolean hasBorder) throws SemanticException {
		dotbarItem.setBorder(hasBorder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#setBorderColor(int,
	 * int, int)
	 */
	public void setBorderColor(int r, int g, int b) throws SemanticException {
		dotbarItem.setBorderColor(new ColorSpec(r, g, b));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar#setNumberPosition(java.
	 * lang.String)
	 */
	public void setNumberPosition(String numberPosition) throws SemanticException {
		dotbarItem.setNumberPosition(NumberPosition.create(numberPosition));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#setNumberWidth(org.
	 * eclipse.birt.report.model.api.metadata.DimensionValue)
	 */
	public void setNumberWidth(DimensionValue numberWidth) throws SemanticException {
		dotbarItem.setNumberWidth(numberWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar#setNumberHeight(org.
	 * eclipse.birt.report.model.api.metadata.DimensionValue)
	 */
	public void setNumberHeight(DimensionValue numberHeight) throws SemanticException {
		dotbarItem.setNumberHeight(numberHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar#setWrapPoint(int)
	 */
	public void setWrapPoint(int wrapPoint) throws SemanticException {
		dotbarItem.setWrapPoint(wrapPoint);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar#setFontFamily(java.lang.
	 * String)
	 */
	public void setFontFamily(String fontFamily) throws SemanticException {
		dotbarItem.setFontFamily(fontFamily);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar#setFontSize(java.lang.
	 * String)
	 */
	public void setFontSize(String fontSize) throws SemanticException {
		dotbarItem.setFontSize(fontSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar#setFontItalic(boolean)
	 */
	public void setFontItalic(boolean italic) throws SemanticException {
		dotbarItem.setFontItalic(italic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setFontBold(boolean)
	 */
	public void setFontBold(boolean bold) throws SemanticException {
		dotbarItem.setFontBold(bold);
	}

	public void setFontColor(int r, int g, int b) throws SemanticException {
		dotbarItem.setFontColor(new ColorSpec(r, g, b));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setHandle(org.eclipse.
	 * birt.report.model.api.ExtendedItemHandle)
	 */
	public void setHandle(ExtendedItemHandle handle) {
		dotbarItem.setHandle(handle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#setProperty(java.lang.
	 * String, java.lang.Object)
	 */
	public void setProperty(String propName, Object value) {
		dotbarItem.setProperty(propName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#toString()
	 */
	public String toString() {
		return dotbarItem.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#updateRowExpressions(
	 * java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public void updateRowExpressions(Map newExpressions) {
		dotbarItem.updateRowExpressions(newExpressions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.dotbar.script.IDotbar2#updateStyleReference(
	 * java.util.Map)
	 */
	public void updateStyleReference(Map<String, String> styleMap) {
		dotbarItem.updateStyleReference(styleMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.dotbar.script.IDotbar2#validate()
	 */
	public List<SemanticException> validate() {
		return dotbarItem.validate();
	}
}
