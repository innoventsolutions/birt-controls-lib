package blackboard.birt.extensions.dotbar;

import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.IMessages;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.extension.ReportItemFactory;

/**
 * A ReportItemFactory to instantiate the dotbar report item. This class is
 * specified in the org.eclipse.birt.report.model.reportItemModel extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarItemFactory extends ReportItemFactory {

	@Override
	public IReportItem newReportItem(final DesignElementHandle modelHandle) {
		if (modelHandle instanceof ExtendedItemHandle
				&& DotbarItem.EXTENSION_NAME.equals(((ExtendedItemHandle) modelHandle).getExtensionName())) {
			return new DotbarItem((ExtendedItemHandle) modelHandle);
		}
		return null;
	}

	@Override
	public IMessages getMessages() {
		return null;
	}
}
