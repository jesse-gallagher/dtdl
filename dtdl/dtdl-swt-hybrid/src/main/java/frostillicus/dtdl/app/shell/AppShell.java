package frostillicus.dtdl.app.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.darwino.mobile.platform.DarwinoMobileApplication;
import com.darwino.platform.DarwinoManifest;

public class AppShell extends Shell {

	/**
	 * Create the shell.
	 * @param display
	 */
	public AppShell(Display display) {
		super(display, SWT.SHELL_TRIM);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		DarwinoManifest manifest = DarwinoMobileApplication.get().getManifest();
		setText(manifest.getLabel());
		setLayout(new FillLayout());

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
