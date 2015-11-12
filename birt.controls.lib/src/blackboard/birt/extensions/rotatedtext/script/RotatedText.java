package blackboard.birt.extensions.rotatedtext.script;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
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

import blackboard.birt.extensions.rotatedtext.RotatedTextItem;
import blackboard.birt.extensions.util.ColorSpec;

/**
 * @author steve
 *
 */
public class RotatedText extends ReportItem implements IRotatedText {
	private final RotatedTextItem rotatedtextItem;

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * availableBindings()
	 */
	@SuppressWarnings("rawtypes")
	public Iterator availableBindings() {
		return rotatedtextItem.availableBindings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#canExport()
	 */
	public boolean canExport() {
		return rotatedtextItem.canExport();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * checkCompatibility()
	 */
	public CompatibilityStatus checkCompatibility() {
		return rotatedtextItem.checkCompatibility();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#checkProperty
	 * (java.lang.String, java.lang.Object)
	 */
	public void checkProperty(String propName, Object value) throws ExtendedElementException {
		rotatedtextItem.checkProperty(propName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#copy()
	 */
	public org.eclipse.birt.report.model.api.extension.IReportItem copy() {
		return rotatedtextItem.copy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#deserialize(
	 * java.lang.String, java.io.ByteArrayInputStream)
	 */
	public void deserialize(String propName, ByteArrayInputStream data) throws ExtendedElementException {
		rotatedtextItem.deserialize(propName, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#equals(java.
	 * lang.Object)
	 */
	public boolean equals(Object obj) {
		return rotatedtextItem.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getModelHandle()
	 */
	public ExtendedItemHandle getModelHandle() {
		return rotatedtextItem.getModelHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getText()
	 */
	public String getText() {
		return rotatedtextItem.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getRotationAngle()
	 */
	public int getRotationAngle() {
		return rotatedtextItem.getRotationAngle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getFontFamily
	 * ()
	 */
	public String getFontFamily() {
		return rotatedtextItem.getFontFamily();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getFontSize()
	 */
	public String getFontSize() {
		return rotatedtextItem.getFontSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getFontColor(
	 * )
	 */
	public ColorSpec getFontColor() {
		return rotatedtextItem.getFontColor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getLinkURL()
	 */
	public String getLinkURL() {
		return rotatedtextItem.getLinkURL();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getMethods()
	 */
	public IPropertyDefinition[] getMethods() {
		return rotatedtextItem.getMethods();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getMethods(
	 * java.lang.String)
	 */
	public IMethodInfo[] getMethods(String methodName) {
		return rotatedtextItem.getMethods(methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getPredefinedStyles()
	 */
	@SuppressWarnings("rawtypes")
	public List getPredefinedStyles() {
		return rotatedtextItem.getPredefinedStyles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getProperty(
	 * java.lang.String)
	 */
	public Object getProperty(String propName) {
		return rotatedtextItem.getProperty(propName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getPropertyDefinitions()
	 */
	public IPropertyDefinition[] getPropertyDefinitions() {
		return rotatedtextItem.getPropertyDefinitions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getReferencedStyle()
	 */
	public StyleHandle[] getReferencedStyle() {
		return rotatedtextItem.getReferencedStyle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getRowExpressions()
	 */
	@SuppressWarnings("rawtypes")
	public List getRowExpressions() {
		return rotatedtextItem.getRowExpressions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getScriptPropertyDefinition()
	 */
	public IPropertyDefinition getScriptPropertyDefinition() {
		return rotatedtextItem.getScriptPropertyDefinition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * getSimpleElement()
	 */
	public IReportItem getSimpleElement() {
		return rotatedtextItem.getSimpleElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#getWrapPoint(
	 * )
	 */
	public DimensionValue getWrapPoint() {
		return rotatedtextItem.getWrapPoint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * handleCompatibilityIssue()
	 */
	public void handleCompatibilityIssue() {
		rotatedtextItem.handleCompatibilityIssue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#hasFixedSize(
	 * )
	 */
	public boolean hasFixedSize() {
		return rotatedtextItem.hasFixedSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#hashCode()
	 */
	public int hashCode() {
		return rotatedtextItem.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setFontFamily
	 * (java.lang.String)
	 */
	public void setFontFamily(String fontFamily) throws SemanticException {
		rotatedtextItem.setFontFamily(fontFamily);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setFontSize(
	 * java.lang.String)
	 */
	public void setFontSize(String fontSize) throws SemanticException {
		rotatedtextItem.setFontSize(fontSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#isFontItalic(
	 * )
	 */
	public boolean isFontItalic() {
		return rotatedtextItem.isFontItalic();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#isFontBold()
	 */
	public boolean isFontBold() {
		return rotatedtextItem.isFontBold();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * refreshPropertyDefinition()
	 */
	public boolean refreshPropertyDefinition() {
		return rotatedtextItem.refreshPropertyDefinition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#serialize(
	 * java.lang.String)
	 */
	public ByteArrayOutputStream serialize(String propName) {
		return rotatedtextItem.serialize(propName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setText(java.
	 * lang.String)
	 */
	public void setText(String value) throws SemanticException {
		rotatedtextItem.setText(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * setRotationAngle(int)
	 */
	public void setRotationAngle(int value) throws SemanticException {
		rotatedtextItem.setRotationAngle(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setWrapPoint(
	 * org.eclipse.birt.report.model.api.metadata.DimensionValue)
	 */
	public void setWrapPoint(DimensionValue value) throws SemanticException {
		rotatedtextItem.setWrapPoint(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setFontItalic
	 * (boolean)
	 */
	public void setFontItalic(boolean italic) throws SemanticException {
		rotatedtextItem.setFontItalic(italic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setFontBold(
	 * boolean)
	 */
	public void setFontBold(boolean bold) throws SemanticException {
		rotatedtextItem.setFontBold(bold);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setFontColor(
	 * blackboard.birt.extensions.util.ColorSpec)
	 */
	public void setFontColor(ColorSpec fontColor) throws SemanticException {
		rotatedtextItem.setFontColor(fontColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setHandle(org
	 * .eclipse.birt.report.model.api.ExtendedItemHandle)
	 */
	public void setHandle(ExtendedItemHandle handle) {
		rotatedtextItem.setHandle(handle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setLinkURL(
	 * java.lang.String)
	 */
	public void setLinkURL(String linkURL) throws SemanticException {
		rotatedtextItem.setLinkURL(linkURL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#setProperty(
	 * java.lang.String, java.lang.Object)
	 */
	public void setProperty(String propName, Object value) {
		rotatedtextItem.setProperty(propName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#toString()
	 */
	public String toString() {
		return rotatedtextItem.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * updateRowExpressions(java.util.Map)
	 */
	public void updateRowExpressions(@SuppressWarnings("rawtypes") Map newExpressions) {
		rotatedtextItem.updateRowExpressions(newExpressions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blackboard.birt.extensions.rotatedtext.script.IRotatedText2#
	 * updateStyleReference(java.util.Map)
	 */
	public void updateStyleReference(Map<String, String> styleMap) {
		rotatedtextItem.updateStyleReference(styleMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * blackboard.birt.extensions.rotatedtext.script.IRotatedText2#validate()
	 */
	public List<SemanticException> validate() {
		return rotatedtextItem.validate();
	}

	public RotatedText(RotatedTextItem rotatedtextItem) {
		super(rotatedtextItem.getModelHandle());
		this.rotatedtextItem = rotatedtextItem;
	}
}
