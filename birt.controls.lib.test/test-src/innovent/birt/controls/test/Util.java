package innovent.birt.controls.test;

import org.eclipse.birt.report.model.api.DimensionHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.mockito.Mockito;

import blackboard.birt.extensions.dotbar.DotbarItem;
import blackboard.birt.extensions.dotbar.DotbarItemFactory;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;
import blackboard.birt.extensions.rotatedtext.RotatedTextItemFactory;

public class Util {
	public static final double RT_WRAP_MEASURE = 1;
	public static final String RT_WRAP_UNITS = "in";
	public static final String RT_TEXT = "test.RotatedText.text";
	public static final int RT_ANGLE = 123;
	public static final String RT_FONT_FAMILY = "foobar";
	public static final String RT_FONT_SIZE = "test_font_size";
	public static final String RT_FONT_WEIGHT = "bold";
	public static final String RT_FONT_STYLE = "ITALIC";
	public static final String RT_LINK_URL = "test_link_url";

	public static IReportItem getRotatedTextReportItem() {
		RotatedTextItemFactory rtif = new RotatedTextItemFactory();
		ExtendedItemHandle modelHandle = Mockito.mock(ExtendedItemHandle.class);
		Mockito.when(modelHandle.getStringProperty(RotatedTextItem.TEXT_PROP)).thenReturn(RT_TEXT);
		Mockito.when(modelHandle.getIntProperty(RotatedTextItem.ROTATION_ANGLE_PROP)).thenReturn(RT_ANGLE);
		DimensionHandle dimHandle = Mockito.mock(DimensionHandle.class);
		DimensionValue dimValue = Mockito.mock(DimensionValue.class);
		Mockito.when(dimHandle.getValue()).thenReturn(dimValue);
		Mockito.when(dimValue.getMeasure()).thenReturn(RT_WRAP_MEASURE);
		Mockito.when(dimValue.getUnits()).thenReturn(RT_WRAP_UNITS);
		Mockito.when(modelHandle.getDimensionProperty(RotatedTextItem.WRAP_POINT_PROP)).thenReturn(dimHandle);
		Mockito.when(modelHandle.getStringProperty(RotatedTextItem.FONT_FAMILY_PROP)).thenReturn(RT_FONT_FAMILY);
		Mockito.when(modelHandle.getStringProperty(RotatedTextItem.FONT_SIZE_PROP)).thenReturn(RT_FONT_SIZE);
		Mockito.when(modelHandle.getStringProperty(RotatedTextItem.FONT_WEIGHT_PROP)).thenReturn(RT_FONT_WEIGHT);
		Mockito.when(modelHandle.getStringProperty(RotatedTextItem.FONT_STYLE_PROP)).thenReturn(RT_FONT_STYLE);
		Mockito.when(modelHandle.getStringProperty(RotatedTextItem.LINK_URL_PROP)).thenReturn(RT_LINK_URL);
		Mockito.when(modelHandle.getExtensionName()).thenReturn(RotatedTextItem.EXTENSION_NAME);
		return rtif.newReportItem(modelHandle);
	}

	public static final String DB_VALUE_EXPRESSION = "dotbar value expression";
	public static final int DB_DISPLAY_VALUE = 123;
	public static final double DB_DOT_WIDTH_MEASURE = 1.23;
	public static final String DB_DOT_WIDTH_UNITS = "in";
	public static final double DB_DOT_HEIGHT_MEASURE = 2.34;
	public static final String DB_DOT_HEIGHT_UNITS = "pt";
	public static final double DB_DOT_SPACING_MEASURE = 3.45;
	public static final String DB_DOT_SPACING_UNITS = "pt";
	public static final boolean DB_VERTICAL = true;
	public static final String DB_DOT_SHAPE = "OVAL";
	public static final boolean DB_HAS_BORDER = true;
	public static final String DB_BORDER_COLOR = "#CCC";
	public static final boolean DB_HAS_FILL = true;
	public static final String DB_FILL_COLOR = "#ddd";
	public static final String DB_NUMBER_POSITION = "Hidden";
	public static final Double DB_NUMBER_WIDTH_MEASURE = 4.56;
	public static final String DB_NUMBER_WIDTH_UNITS = "CM";
	public static final Double DB_NUMBER_HEIGHT_MEASURE = 5.67;
	public static final String DB_NUMBER_HEIGHT_UNITS = "em";
	public static final int DB_WRAP_POINT = 678;
	public static final String DB_FONT_SIZE = "11pt";
	public static final String DB_FONT_FAMILY = "helvarial";
	public static final String DB_FONT_STYLE = "ITALIC";
	public static final String DB_FONT_WEIGHT = "bold";
	public static final String DB_FONT_COLOR = "#EEEEEE";

	public static IReportItem getDotbarReportItem() {
		DotbarItemFactory dif = new DotbarItemFactory();
		ExtendedItemHandle modelHandle = Mockito.mock(ExtendedItemHandle.class);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.VALUE_EXPRESSION_PROP)).thenReturn(DB_VALUE_EXPRESSION);
		Mockito.when(modelHandle.getIntProperty(DotbarItem.DISPLAY_VALUE_PROP)).thenReturn(DB_DISPLAY_VALUE);
		{
			DimensionHandle dimHandle = Mockito.mock(DimensionHandle.class);
			DimensionValue dimValue = Mockito.mock(DimensionValue.class);
			Mockito.when(dimHandle.getValue()).thenReturn(dimValue);
			Mockito.when(dimValue.getMeasure()).thenReturn(DB_DOT_WIDTH_MEASURE);
			Mockito.when(dimValue.getUnits()).thenReturn(DB_DOT_WIDTH_UNITS);
			Mockito.when(modelHandle.getDimensionProperty(DotbarItem.DOT_WIDTH_PROP)).thenReturn(dimHandle);
		}
		{
			DimensionHandle dimHandle = Mockito.mock(DimensionHandle.class);
			DimensionValue dimValue = Mockito.mock(DimensionValue.class);
			Mockito.when(dimHandle.getValue()).thenReturn(dimValue);
			Mockito.when(dimValue.getMeasure()).thenReturn(DB_DOT_HEIGHT_MEASURE);
			Mockito.when(dimValue.getUnits()).thenReturn(DB_DOT_HEIGHT_UNITS);
			Mockito.when(modelHandle.getDimensionProperty(DotbarItem.DOT_HEIGHT_PROP)).thenReturn(dimHandle);
		}
		{
			DimensionHandle dimHandle = Mockito.mock(DimensionHandle.class);
			DimensionValue dimValue = Mockito.mock(DimensionValue.class);
			Mockito.when(dimHandle.getValue()).thenReturn(dimValue);
			Mockito.when(dimValue.getMeasure()).thenReturn(DB_DOT_SPACING_MEASURE);
			Mockito.when(dimValue.getUnits()).thenReturn(DB_DOT_SPACING_UNITS);
			Mockito.when(modelHandle.getDimensionProperty(DotbarItem.DOT_SPACING_PROP)).thenReturn(dimHandle);
		}
		Mockito.when(modelHandle.getBooleanProperty(DotbarItem.VERTICAL_PROP)).thenReturn(DB_VERTICAL);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.DOT_SHAPE_PROP)).thenReturn(DB_DOT_SHAPE);
		Mockito.when(modelHandle.getBooleanProperty(DotbarItem.HAS_BORDER_PROP)).thenReturn(DB_HAS_BORDER);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.BORDER_COLOR_PROP)).thenReturn(DB_BORDER_COLOR);
		Mockito.when(modelHandle.getBooleanProperty(DotbarItem.HAS_FILL_PROP)).thenReturn(DB_HAS_FILL);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.FILL_COLOR_PROP)).thenReturn(DB_FILL_COLOR);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.NUMBER_POSITION_PROP)).thenReturn(DB_NUMBER_POSITION);
		{
			DimensionHandle dimHandle = Mockito.mock(DimensionHandle.class);
			DimensionValue dimValue = Mockito.mock(DimensionValue.class);
			Mockito.when(dimHandle.getValue()).thenReturn(dimValue);
			Mockito.when(dimValue.getMeasure()).thenReturn(DB_NUMBER_WIDTH_MEASURE);
			Mockito.when(dimValue.getUnits()).thenReturn(DB_NUMBER_WIDTH_UNITS);
			Mockito.when(modelHandle.getDimensionProperty(DotbarItem.NUMBER_WIDTH_PROP)).thenReturn(dimHandle);
		}
		{
			DimensionHandle dimHandle = Mockito.mock(DimensionHandle.class);
			DimensionValue dimValue = Mockito.mock(DimensionValue.class);
			Mockito.when(dimHandle.getValue()).thenReturn(dimValue);
			Mockito.when(dimValue.getMeasure()).thenReturn(DB_NUMBER_HEIGHT_MEASURE);
			Mockito.when(dimValue.getUnits()).thenReturn(DB_NUMBER_HEIGHT_UNITS);
			Mockito.when(modelHandle.getDimensionProperty(DotbarItem.NUMBER_HEIGHT_PROP)).thenReturn(dimHandle);
		}
		Mockito.when(modelHandle.getIntProperty(DotbarItem.WRAP_POINT_PROP)).thenReturn(DB_WRAP_POINT);
		Mockito.when(modelHandle.getExtensionName()).thenReturn(DotbarItem.EXTENSION_NAME);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.FONT_SIZE_PROP)).thenReturn(DB_FONT_SIZE);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.FONT_FAMILY_PROP)).thenReturn(DB_FONT_FAMILY);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.FONT_STYLE_PROP)).thenReturn(DB_FONT_STYLE);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.FONT_WEIGHT_PROP)).thenReturn(DB_FONT_WEIGHT);
		Mockito.when(modelHandle.getStringProperty(DotbarItem.FONT_COLOR_PROP)).thenReturn(DB_FONT_COLOR);
		return dif.newReportItem(modelHandle);
	}
}
