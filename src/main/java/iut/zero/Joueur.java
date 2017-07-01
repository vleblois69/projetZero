package iut.zero;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class Joueur extends Entite
{
	public Arme armeEquipee;
	
	public Joueur(Composite composite, Display display) {
		armeEquipee = null;
		pv = 3;
		points = 0;
		label = new Label(composite, SWT.NONE);
		label.setImage(new Image(display, "src/images/vaisseau.png"));
		label.setSize(50, 50); //Dimensions de l'image
	}

	private int points;
	
		

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}	

}
