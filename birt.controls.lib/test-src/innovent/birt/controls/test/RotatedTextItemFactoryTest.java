package innovent.birt.controls.test;

import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.junit.Assert;
import org.junit.Test;

import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;
import blackboard.birt.extensions.util.ColorSpec;

public class RotatedTextItemFactoryTest {
	@Test
	public void testRotatedTextItemFactory() {
		IReportItem ri = Util.getRotatedTextReportItem();
		Assert.assertNotNull(ri);
		Assert.assertTrue(ri instanceof RotatedTextItem);
		RotatedTextItem rti = (RotatedTextItem) ri;
		RotatedTextData rtd = new RotatedTextData(rti);
		Assert.assertEquals(Util.RT_FONT_FAMILY, rtd.fontFamily);
		Assert.assertEquals(Util.RT_FONT_SIZE, rtd.fontSize);
		Assert.assertEquals(Util.RT_LINK_URL, rtd.linkURL);
		Assert.assertEquals(Util.RT_TEXT, rtd.text);
		Assert.assertEquals(Util.RT_ANGLE, rtd.angle);
		Assert.assertTrue(rtd.fontBold);
		Assert.assertEquals(rtd.fontColor, new ColorSpec(0, 0, 0));
		Assert.assertTrue(rtd.fontItalic);
		Assert.assertNotNull(rtd.wrapPoint);
		Assert.assertEquals(Util.RT_WRAP_MEASURE, rtd.wrapPoint.getMeasure(), .00001);
		Assert.assertEquals(Util.RT_WRAP_UNITS, rtd.wrapPoint.getUnits());
	}
}
