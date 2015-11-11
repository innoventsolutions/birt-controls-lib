package blackboard.birt.extensions.dotbar.script;

import org.eclipse.birt.report.engine.api.script.IReportContext;

public interface IDotbarEventHandler {

	void onPrepare(IDotbar dotbar, IReportContext reportContext);

	void onCreate(IDotbar dotbar /* IDotbarInstance iDotbarInstance */, IReportContext reportContext);

	void onRender(IDotbar dotbar /* IDotbarInstance iDotbarInstance */, IReportContext reportContext);
}
