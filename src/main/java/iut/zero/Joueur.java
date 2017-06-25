package iut.zero;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class Joueur extends Entite
{
	public Joueur(Composite composite, Display display) {
		pv = 3;
		points = 0;
		label = new Label(composite, SWT.NONE);
		label.setImage(new Image(display, "src/images/vaisseau.png"));
	}

	private int points;
	
		

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}	

}
