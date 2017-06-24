package iut.zero;

public class Alien extends Entite
{
	public Alien() {
		super.setLienImage("src/images/alien.png");
		super.setPv(1);
	}
	
	public void retirerPV(int degats)
	{
		setPv(getPv() - degats);
	}

}
