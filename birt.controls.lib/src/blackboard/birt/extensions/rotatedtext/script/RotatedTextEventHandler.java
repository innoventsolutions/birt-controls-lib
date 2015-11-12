package blackboard.birt.extensions.rotatedtext.script;

import java.lang.reflect.Field;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.executor.EventHandlerManager;
import org.eclipse.birt.report.engine.executor.ExecutionContext;
import org.eclipse.birt.report.engine.extension.IOnCreateEvent;
import org.eclipse.birt.report.engine.extension.IOnPrepareEvent;
import org.eclipse.birt.report.engine.extension.IOnRenderEvent;
import org.eclipse.birt.report.engine.extension.IReportEvent;
import org.eclipse.birt.report.engine.extension.IReportEventContext;
import org.eclipse.birt.report.engine.extension.ReportEventHandlerBase;
import org.eclipse.birt.report.engine.ir.Expression;
import org.eclipse.birt.report.engine.ir.ReportItemDesign;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.ModuleUtil;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.birt.report.model.elements.interfaces.IReportItemModel;

import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

public class RotatedTextEventHandler extends ReportEventHandlerBase {

	public RotatedTextEventHandler() {
	}

	@Override
	public void onPrepare(IOnPrepareEvent event) throws BirtException {
		DesignElementHandle modelHandle = event.getHandle();

		if (!(modelHandle instanceof ExtendedItemHandle)) {
			return;
		}

		ReportItemHandle reportItemHandle = (ReportItemHandle) modelHandle;
		ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) modelHandle;

		String javascript = reportItemHandle.getOnPrepare();
		boolean hasJavascript = (javascript != null) && (javascript.length() != 0);
		boolean hasJavaCode = (reportItemHandle.getEventHandlerClass() != null)
				&& (reportItemHandle.getEventHandlerClass().length() != 0);
		if (!hasJavascript && !hasJavaCode)
			return;

		IReportEventContext context = event.getContext();
		ExecutionContext executionContext = extractExecutionContext(context);
		IRotatedText rotatedtext = new RotatedText((RotatedTextItem) extendedItemHandle.getReportItem());

		if (hasJavascript) {
			String id = ModuleUtil.getScriptUID(reportItemHandle.getPropertyHandle(IReportItemModel.ON_PREPARE_METHOD));
			executionContext.newScope(rotatedtext);
			executionContext.evaluate("javascript", id, 1, javascript);
		} else {
			try {
				IRotatedTextEventHandler eh = getEventHandler(reportItemHandle, executionContext);
				if (eh != null)
					eh.onPrepare(rotatedtext, context);
			} catch (Exception e) {
				addException(executionContext, e);
			}
		}
		super.onPrepare(event);
	}

	@Override
	public void onCreate(IOnCreateEvent event) throws BirtException {
		DesignElementHandle modelHandle = event.getHandle();
		ReportItemHandle reportItemHandle = (ReportItemHandle) modelHandle;
		ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) modelHandle;
		IContent content = event.getContent();
		ReportItemDesign rotatedtextItemDesign = (ReportItemDesign) content.getGenerateBy();

		if (rotatedtextItemDesign == null) {
			return;
		}
		Expression onCreate = rotatedtextItemDesign.getOnCreate();
		String javaClassName = rotatedtextItemDesign.getJavaClass();

		if (onCreate == null && javaClassName == null) {
			return;
		}

		IReportEventContext context = event.getContext();
		ExecutionContext executionContext = extractExecutionContext(context);
		IRotatedText rotatedtext = new RotatedText((RotatedTextItem) extendedItemHandle.getReportItem());

		// IRotatedTextInstance rotatedtextInstance = new
		// RotatedTextInstanceImpl(content,
		// executionContext, RunningState.CREATE);

		if (onCreate != null) {
			executionContext.newScope(rotatedtext);
			executionContext.evaluate(onCreate);
			return;
		}

		try {
			IRotatedTextEventHandler eh = getEventHandler(reportItemHandle, executionContext);
			if (eh != null)
				eh.onCreate(rotatedtext, context);
		} catch (Exception e) {
			addException(executionContext, e);
		}
		super.onCreate(event);
	}

	@Override
	public void onRender(IOnRenderEvent event) throws BirtException {
		DesignElementHandle modelHandle = event.getHandle();
		ReportItemHandle reportItemHandle = (ReportItemHandle) modelHandle;
		ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) modelHandle;
		IContent content = event.getContent();
		ReportItemDesign rotatedtextItemDesign = (ReportItemDesign) content.getGenerateBy();

		if (rotatedtextItemDesign == null) {
			return;
		}
		Expression onRender = rotatedtextItemDesign.getOnRender();
		String javaClassName = rotatedtextItemDesign.getJavaClass();

		if (onRender == null && javaClassName == null) {
			return;
		}

		IReportEventContext context = event.getContext();
		ExecutionContext executionContext = extractExecutionContext(context);
		IRotatedText rotatedtext = new RotatedText((RotatedTextItem) extendedItemHandle.getReportItem());

		// IRotatedTextInstance rotatedtextInstance = new
		// RotatedTextInstanceImpl(content,
		// executionContext, RunningState.RENDER);

		if (onRender != null) {
			executionContext.newScope(rotatedtext);
			executionContext.evaluate(onRender);
			return;
		}

		try {
			IRotatedTextEventHandler eh = getEventHandler(reportItemHandle, executionContext);
			if (eh != null)
				eh.onRender(rotatedtext, context);
		} catch (Exception e) {
			addException(executionContext, e);
		}
		super.onRender(event);
	}

	@Override
	public void onPagebreak(IReportEvent event) throws BirtException {
		super.onPagebreak(event);
	}

	public static ExecutionContext extractExecutionContext(final IReportContext reportContext) {
		final Class<?> clazz = reportContext.getClass();
		try {
			// Field field = AccessController.doPrivileged(new
			// PrivilegedAction<Field>() {
			//
			// public Field run() {
			// try {
			// Field field = clazz.getDeclaredField("context");
			// field.setAccessible(true);
			// return field;
			// } catch (NoSuchFieldException e) {
			// System.out.println("Exception:" + e);
			// }
			// return null;
			// }
			// });
			Field field = getField(clazz, "context");
			field.setAccessible(true);
			Object exeContextObject = field.get(reportContext);
			if (exeContextObject instanceof ExecutionContext) {
				return (ExecutionContext) exeContextObject;
			}
		} catch (Exception ex) {
		}

		return null;
	}

	private static Field getField(final Class<?> classObj, final String fieldName) throws NoSuchFieldException {
		try {
			return classObj.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superClass = classObj.getSuperclass();
			if (superClass == null)
				throw e;
			return getField(superClass, fieldName);
		}
	}

	protected static void addException(ExecutionContext context, Exception e) {
		addException(context, e, null);
	}

	protected static void addException(ExecutionContext context, Exception e, DesignElementHandle handle) {
		EngineException eex = null;
		if (e instanceof EngineException)
			eex = (EngineException) e;
		else if (e instanceof BirtException) {
			eex = new EngineException((BirtException) e);
		} else {
			eex = new EngineException("Unhandled script error"
					/* MessageConstants.UNHANDLED_SCRIPT_ERROR */, e);
		}

		// log.log(Level.WARNING, eex.getMessage(), eex);
		if (context == null)
			return;

		if (handle == null)
			context.addException(eex);
		else
			context.addException(handle, eex);
	}

	private static IRotatedTextEventHandler getEventHandler(ReportItemHandle handle, ExecutionContext context) {
		try {
			return (IRotatedTextEventHandler) getInstance(handle, context);
		} catch (ClassCastException e) {
			addClassCastException(context, e, handle, IRotatedTextEventHandler.class);
		} catch (EngineException e) {
			addException(context, e, handle);
		}
		return null;
	}

	protected static Object getInstance(DesignElementHandle handle, ExecutionContext context) throws EngineException {
		EventHandlerManager eventHandlerManager = context.getEventHandlerManager();
		return eventHandlerManager.getInstance(handle, context);
	}

	protected static void addClassCastException(ExecutionContext context, Exception e, DesignElementHandle handle,
			Class<?> requiredInterface) {
		EngineException ex = new EngineException("Script class cast error"
				/* MessageConstants.SCRIPT_CLASS_CAST_ERROR */,
				new Object[] { handle.getEventHandlerClass(), requiredInterface.getName() }, e);

		// log.log(Level.WARNING, e.getMessage(), e);
		if (context == null)
			return;

		context.addException(handle, ex);
	}
}
