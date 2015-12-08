package innovent.birt.controls.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.engine.ReportEngine;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test starts a report engine, runs a report, and then checks the output.
 * 
 * To run this test you will need to include the birt-runtime classes in the
 * classpath and also build this plugin into a jar and put it in the classpath
 * as well.
 * 
 * @author steve
 *
 */
public class EventHandlerTest {
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() throws UnsupportedEncodingException {
		try {
			final IReportEngine reportEngine = ReportEngine.getReportEngine();
			final InputStream is = this.getClass()
					.getResourceAsStream("/reports/test_events.rptdesign");
			Assert.assertNotNull(is);
			final IReportRunnable design = reportEngine.openReportDesign(is);
			final IGetParameterDefinitionTask paramTask = reportEngine
					.createGetParameterDefinitionTask(design);
			List<EngineException> errors = null;
			try {
				final IRunAndRenderTask rrTask = reportEngine
						.createRunAndRenderTask(design);
				final Map<String, Object> appContext = rrTask.getAppContext();
				final ClassLoader classLoader = getClass().getClassLoader();
				appContext.put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
						classLoader);
				// rrTask.setAppContext(appContext);
				try {
					final ByteArrayOutputStream os = new ByteArrayOutputStream();
					final RenderOption options = new HTMLRenderOption();
					options.setOutputFormat("HTML");
					options.setOutputStream(os);
					rrTask.setRenderOption(options);
					rrTask.run();
					errors = rrTask.getErrors();
					String output = os.toString("utf-8");
					System.out.println(output);
				}
				finally {
					rrTask.close();
				}
			}
			finally {
				paramTask.close();
			}
			if (errors != null) {
				Iterator<EngineException> iterator = errors.iterator();
				if (iterator.hasNext()) {
					EngineException error = iterator.next();
					Assert.fail("Engine exception: " + error);
				}
			}
			File file = new File("test_events.log");
			System.out.println(file.getAbsolutePath());
			Assert.assertTrue(file.exists());
			StringBuilder sb = new StringBuilder();
			try {
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				try {
					char[] buffer = new char[0x1000];
					int charsRead = isr.read(buffer);
					while (charsRead >= 0) {
						sb.append(buffer, 0, charsRead);
						charsRead = isr.read(buffer);
					}
				}
				finally {
					isr.close();
				}
			}
			catch (Exception e) {
				Assert.fail("Failed to read log file: " + e);
			}
			String logResults = sb.toString();
			Assert.assertTrue(logResults.indexOf("DotBar.onPrepare") >= 0);
			Assert.assertTrue(logResults.indexOf("DotBar.onCreate") >= 0);
			Assert.assertTrue(logResults.indexOf("DotBar.onRender") >= 0);
			Assert.assertTrue(logResults.indexOf("RotatedText.onPrepare") >= 0);
			Assert.assertTrue(logResults.indexOf("RotatedText.onCreate") >= 0);
			Assert.assertTrue(logResults.indexOf("RotatedText.onRender") >= 0);
		}
		catch (BirtException e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}
}
