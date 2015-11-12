package blackboard.birt.extensions.rotatedtext.script;

import org.eclipse.birt.report.engine.api.script.IReportContext;

public interface IRotatedTextEventHandler {

	void onPrepare(IRotatedText rotatedtext, IReportContext reportContext);

	void onCreate(IRotatedText rotatedtext, IReportContext reportContext);

	void onRender(IRotatedText rotatedtext, IReportContext reportContext);
}
