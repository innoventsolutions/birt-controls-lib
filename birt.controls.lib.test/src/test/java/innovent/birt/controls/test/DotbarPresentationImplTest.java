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

import blackboard.birt.extensions.dotbar.DotbarPresentationImpl;

public class DotbarPresentationImplTest {
	private static final Object DB_VALUE_RESULT = "100";

	@Test
	public void testDotbarPresentationImpl() {
		IReportItem ri = Util.getDotbarReportItem();
		DotbarPresentationImpl rtp = new DotbarPresentationImpl();
		ExtendedItemHandle eih = Mockito.mock(ExtendedItemHandle.class);
		try {
			Mockito.when(eih.getReportItem()).thenReturn(ri);
		}
		catch (ExtendedElementException e) {
			Assert.fail("Failed to mock ExtendedItemHandle.getReportItem(): "
					+ e.toString());
		}
		rtp.setModelObject(eih);
		IQueryResultSet qrs = Mockito.mock(IQueryResultSet.class);
		try {
			Mockito.when(qrs.evaluate(Util.DB_VALUE_EXPRESSION)).thenReturn(DB_VALUE_RESULT);
		}
		catch (BirtException e) {
			Assert.fail(
					"Failed to mock IQueryResultSet.evaluate(\"" + DB_VALUE_RESULT + "\"): "
							+ e.toString());
		}
		IBaseResultSet[] results = new IBaseResultSet[] { qrs };
		Object object = null;
		try {
			object = rtp.onRowSets(results);
		}
		catch (BirtException e) {
			Assert.fail("onRowSets failed: " + e.toString());
		}
		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof ByteArrayInputStream);
	}
}
