package iut.zero;

public class Alien extends Entite
{
	public Alien() {
		super.setLienImage("D:/Mes Documents/Cours Tests Unitaires/projetZero/src/images/alien.png");
		super.setPv(1);
	}
	
	public void retirerPV(int degats)
	{
		setPv(getPv() - degats);
	}

}
