package iut.zero;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class Alien extends Entite
{	
	public Alien(Composite composite, Display display) {
		pv = 1;
		label = new Label(composite, SWT.NONE);
		label.setImage(new Image(display, "src/images/alien.png"));
	}

}
