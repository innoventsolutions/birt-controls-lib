package innovent.birt.controls.test;

import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.junit.Assert;
import org.junit.Test;

import blackboard.birt.extensions.dotbar.DotShape;
import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;
import blackboard.birt.extensions.dotbar.NumberPosition;
import blackboard.birt.extensions.util.ColorSpec;

public class DotbarItemFactoryTest {

	@Test
	public void testDotbarItemFactory() {
		IReportItem ri = Util.getDotbarReportItem();
		Assert.assertNotNull(ri);
		Assert.assertTrue(ri instanceof DotbarItem);
		DotbarItem rti = (DotbarItem) ri;
		DotbarData rtd = rti.getConfiguration();
		Assert.assertNotNull(rtd);
		Assert.assertEquals(Util.DB_VALUE_EXPRESSION, rtd.valueExpression);
		Assert.assertEquals(Util.DB_DISPLAY_VALUE, rtd.displayValue);
		{
			DimensionValue dimValue = rtd.dotWidth;
			Assert.assertNotNull(dimValue);
			Assert.assertEquals(Util.DB_DOT_WIDTH_MEASURE, dimValue.getMeasure(), .0001);
			Assert.assertEquals(Util.DB_DOT_WIDTH_UNITS, dimValue.getUnits());
		}
		{
			DimensionValue dimValue = rtd.dotHeight;
			Assert.assertNotNull(dimValue);
			Assert.assertEquals(Util.DB_DOT_HEIGHT_MEASURE, dimValue.getMeasure(), .0001);
			Assert.assertEquals(Util.DB_DOT_HEIGHT_UNITS, dimValue.getUnits());
		}
		{
			DimensionValue dimValue = rtd.dotSpacing;
			Assert.assertNotNull(dimValue);
			Assert.assertEquals(Util.DB_DOT_SPACING_MEASURE, dimValue.getMeasure(), .0001);
			Assert.assertEquals(Util.DB_DOT_SPACING_UNITS, dimValue.getUnits());
		}
		Assert.assertTrue(rtd.vertical);
		DotShape dotShape = rtd.dotShape;
		Assert.assertNotNull(dotShape);
		Assert.assertEquals(DotShape.OVAL, dotShape);
		Assert.assertTrue(rtd.hasFill);
		Assert.assertEquals(ColorSpec.getColor(Util.DB_BORDER_COLOR), rtd.borderColor);
		Assert.assertTrue(rtd.hasBorder);
		Assert.assertEquals(ColorSpec.getColor(Util.DB_FILL_COLOR), rtd.fillColor);
		Assert.assertEquals(NumberPosition.HIDDEN, rtd.numberPosition);
		{
			DimensionValue dimValue = rtd.numberWidth;
			Assert.assertNotNull(dimValue);
			Assert.assertEquals(Util.DB_NUMBER_WIDTH_MEASURE, dimValue.getMeasure(), .0001);
			Assert.assertEquals(Util.DB_NUMBER_WIDTH_UNITS, dimValue.getUnits());
		}
		{
			DimensionValue dimValue = rtd.numberHeight;
			Assert.assertNotNull(dimValue);
			Assert.assertEquals(Util.DB_NUMBER_HEIGHT_MEASURE, dimValue.getMeasure(), .0001);
			Assert.assertEquals(Util.DB_NUMBER_HEIGHT_UNITS, dimValue.getUnits());
		}
		Assert.assertEquals(Util.DB_WRAP_POINT, rtd.wrapPoint);
		Assert.assertEquals(Util.DB_FONT_SIZE, rtd.fontSize);
		Assert.assertEquals(Util.DB_FONT_FAMILY, rtd.fontFamily);
		Assert.assertTrue(rtd.fontItalic);
		Assert.assertTrue(rtd.fontBold);
		Assert.assertEquals(ColorSpec.getColor(Util.DB_FONT_COLOR), rtd.fontColor);
	}
}
