package iut.zero;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class Laser extends Arme {

	public Laser() {
		nom = "Laser";
		description = "Un rayon pouvant détruire une colonne entière";
		prix = 20;
		degats = 50;
		couleur = SWTResourceManager.getColor(SWT.COLOR_RED);		
	}

}
