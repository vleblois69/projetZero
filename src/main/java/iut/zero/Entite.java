package iut.zero;

import org.eclipse.swt.widgets.Label;

public class Entite 
{
	protected int pv;
	protected Label label;
	
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	
	public void retirerPV(int degats)
	{
		pv -= degats;
		if (pv <= 0)
		{
			label.dispose();
		}
	}
	
	
	
	

}
