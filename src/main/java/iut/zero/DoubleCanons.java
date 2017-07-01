package iut.zero;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class DoubleCanons extends Arme {

	public DoubleCanons() {
		nom = "Double canons";
		description = "Permet de tirer 2 lasers latéraux";
		prix = 10;
		degats = 2;
		couleur = SWTResourceManager.getColor(SWT.COLOR_CYAN);
	}

}
