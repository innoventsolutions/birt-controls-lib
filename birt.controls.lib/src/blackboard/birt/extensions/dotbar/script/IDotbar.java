package blackboard.birt.extensions.dotbar.script;

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

import blackboard.birt.extensions.dotbar.DotbarData;

public interface IDotbar {

	boolean canExport();

	CompatibilityStatus checkCompatibility();

	void checkProperty(String propName, Object value) throws ExtendedElementException;

	ExtendedItemHandle getModelHandle();

	DotbarData getConfiguration();

	void setConfiguration(DotbarData config) throws SemanticException;

	int getInt(String propName);

	boolean getBoolean(String propName);

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

	String getString(String propName);

	void handleCompatibilityIssue();

	boolean hasFixedSize();

	boolean refreshPropertyDefinition();

	void set(String propName, Object text) throws SemanticException;

	void setValueExpression(String valueExpression) throws SemanticException;

	void setDisplayValue(int displayValue) throws SemanticException;

	void setDotWidth(DimensionValue dotWidth) throws SemanticException;

	void setDotHeight(DimensionValue dotHeight) throws SemanticException;

	void setDotSpacing(DimensionValue dotSpacing) throws SemanticException;

	void setVertical(boolean vertical) throws SemanticException;

	void setDotShape(String dotShape) throws SemanticException;

	void setFill(boolean hasFill) throws SemanticException;

	void setFillColor(int r, int g, int b) throws SemanticException;

	void setBorder(boolean hasBorder) throws SemanticException;

	void setBorderColor(int r, int g, int b) throws SemanticException;

	void setNumberPosition(String numberPosition) throws SemanticException;

	void setNumberWidth(DimensionValue numberWidth) throws SemanticException;

	void setNumberHeight(DimensionValue numberHeight) throws SemanticException;

	void setWrapPoint(int wrapPoint) throws SemanticException;

	void setFontFamily(String fontFamily) throws SemanticException;

	void setFontSize(String fontSize) throws SemanticException;

	void setFontItalic(boolean italic) throws SemanticException;

	void setFontBold(boolean bold) throws SemanticException;

	void setFontColor(int r, int g, int b) throws SemanticException;

	void setHandle(ExtendedItemHandle handle);

	void setProperty(String propName, Object value);

	String toString();

	void updateRowExpressions(@SuppressWarnings("rawtypes") Map newExpressions);

	void updateStyleReference(Map<String, String> styleMap);

	List<SemanticException> validate();

}