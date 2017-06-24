package iut.zero;

public class Joueur extends Entite
{
	private int points;
	
	public Joueur() {
		super.setLienImage("D:/Mes Documents/Cours Tests Unitaires/projetZero/src/images/vaisseau.png");
		super.setPv(3);
		points = 0;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	

}
