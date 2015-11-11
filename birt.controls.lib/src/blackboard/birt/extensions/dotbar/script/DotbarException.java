package blackboard.birt.extensions.dotbar.script;

import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.core.DesignElement;

public class DotbarException extends SemanticException {
	private static final long serialVersionUID = 1L;
	public static final String DOTBAR_PLUGIN_ID = "birt.controls.lib"; //$NON-NLS-1$

	/**
	 * @param element
	 * @param errCode
	 * @param cause
	 */
	public DotbarException(DesignElement element, String errCode, Throwable cause) {
		super(element, errCode, cause);
		this.pluginId = DOTBAR_PLUGIN_ID;
	}

	/**
	 * @param element
	 * @param errCode
	 */
	public DotbarException(DesignElement element, String errCode) {
		super(element, errCode);
		this.pluginId = DOTBAR_PLUGIN_ID;
	}

	/**
	 * @param element
	 * @param values
	 * @param errCode
	 * @param cause
	 */
	public DotbarException(DesignElement element, String[] values, String errCode, Throwable cause) {
		super(element, values, errCode, cause);
		this.pluginId = DOTBAR_PLUGIN_ID;
	}

	/**
	 * @param element
	 * @param values
	 * @param errCode
	 */
	public DotbarException(DesignElement element, String[] values, String errCode) {
		super(element, values, errCode);
		this.pluginId = DOTBAR_PLUGIN_ID;
	}

	public DotbarException(Throwable cause) {
		super(DOTBAR_PLUGIN_ID, cause.getLocalizedMessage(), (Object[]) null, cause);
	}

	public DotbarException(String errorMsg) {
		super(DOTBAR_PLUGIN_ID, errorMsg, null);
	}
}
