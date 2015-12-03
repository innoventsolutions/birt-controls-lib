package innovent.birt.controls.test;

import java.io.ByteArrayInputStream;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.extension.IBaseResultSet;
import org.eclipse.birt.report.engine.extension.IQueryResultSet;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import blackboard.birt.extensions.rotatedtext.RotatedTextPresentationImpl;

public class RotatedTextPresentationImplTest {

	private static final Object TEXT_RESULT = "text result";
	private static final Object URL_RESULT = "url result";

	@Test
	public void testRotatedTextPresentationImpl() {
		IReportItem ri = Util.getRotatedTextReportItem();
		RotatedTextPresentationImpl rtp = new RotatedTextPresentationImpl();
		ExtendedItemHandle eih = Mockito.mock(ExtendedItemHandle.class);
		try {
			Mockito.when(eih.getReportItem()).thenReturn(ri);
		} catch (ExtendedElementException e) {
			Assert.fail("Failed to mock ExtendedItemHandle.getReportItem(): " + e.toString());
		}
		rtp.setModelObject(eih);
		IQueryResultSet qrs = Mockito.mock(IQueryResultSet.class);
		try {
			Mockito.when(qrs.evaluate(Util.RT_TEXT)).thenReturn(TEXT_RESULT);
		} catch (BirtException e) {
			Assert.fail("Failed to mock IQueryResultSet.evaluate(TEST_TEXT): " + e.toString());
		}
		try {
			Mockito.when(qrs.evaluate(Util.RT_LINK_URL)).thenReturn(URL_RESULT);
		} catch (BirtException e) {
			Assert.fail("Failed to mock IQueryResultSet.evaluate(TEST_LINK_URL): " + e.toString());
		}
		IBaseResultSet[] results = new IBaseResultSet[] { qrs };
		Object object = null;
		try {
			object = rtp.onRowSets(results);

		} catch (BirtException e) {
			Assert.fail("onRowSets failed: " + e.toString());
		}
		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof Object[]);
		Object[] objects = (Object[]) object;
		Assert.assertEquals(2, objects.length);
		Object areaObj = objects[1];
		Assert.assertNotNull(areaObj);
		Assert.assertTrue(areaObj instanceof String);
		String areaStr = (String) areaObj;
		Assert.assertTrue(areaStr.indexOf("areaNode.setAttribute(\"alt\", \"" + TEXT_RESULT + "\");") >= 0);
		Assert.assertTrue(areaStr.indexOf("areaNode.setAttribute(\"title\", \"" + TEXT_RESULT + "\");") >= 0);
		Assert.assertTrue(areaStr.indexOf("href =\"" + URL_RESULT + "\"") >= 0);
		Object imageObj = objects[0];
		Assert.assertTrue(imageObj instanceof ByteArrayInputStream);
	}
}
