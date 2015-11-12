package blackboard.birt.extensions.rotatedtext.script;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.extension.CompatibilityStatus;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IPropertyDefinition;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.api.metadata.IMethodInfo;
import org.eclipse.birt.report.model.api.simpleapi.IReportItem;

import blackboard.birt.extensions.util.ColorSpec;

public interface IRotatedText {

	@SuppressWarnings("rawtypes")
	Iterator availableBindings();

	boolean canExport();

	CompatibilityStatus checkCompatibility();

	void checkProperty(String propName, Object value) throws ExtendedElementException;

	org.eclipse.birt.report.model.api.extension.IReportItem copy();

	void deserialize(String propName, ByteArrayInputStream data) throws ExtendedElementException;

	boolean equals(Object obj);

	ExtendedItemHandle getModelHandle();

	String getText();

	int getRotationAngle();

	String getFontFamily();

	String getFontSize();

	ColorSpec getFontColor();

	String getLinkURL();

	IPropertyDefinition[] getMethods();

	IMethodInfo[] getMethods(String methodName);

	@SuppressWarnings("rawtypes")
	List getPredefinedStyles();

	Object getProperty(String propName);

	IPropertyDefinition[] getPropertyDefinitions();

	StyleHandle[] getReferencedStyle();

	@SuppressWarnings("rawtypes")
	List getRowExpressions();

	IPropertyDefinition getScriptPropertyDefinition();

	IReportItem getSimpleElement();

	DimensionValue getWrapPoint();

	void handleCompatibilityIssue();

	boolean hasFixedSize();

	int hashCode();

	void setFontFamily(String fontFamily) throws SemanticException;

	void setFontSize(String fontSize) throws SemanticException;

	boolean isFontItalic();

	boolean isFontBold();

	boolean refreshPropertyDefinition();

	ByteArrayOutputStream serialize(String propName);

	void setText(String value) throws SemanticException;

	void setRotationAngle(int value) throws SemanticException;

	void setWrapPoint(DimensionValue value) throws SemanticException;

	void setFontItalic(boolean italic) throws SemanticException;

	void setFontBold(boolean bold) throws SemanticException;

	void setFontColor(ColorSpec fontColor) throws SemanticException;

	void setHandle(ExtendedItemHandle handle);

	void setLinkURL(String linkURL) throws SemanticException;

	void setProperty(String propName, Object value);

	String toString();

	void updateRowExpressions(@SuppressWarnings("rawtypes") Map newExpressions);

	void updateStyleReference(Map<String, String> styleMap);

	List<SemanticException> validate();

}