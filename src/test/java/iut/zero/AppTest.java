package iut.zero;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private Robot robot;
	private Joueur joueur;
	
	@Before
	public void initialisation()
	{
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		joueur = App.joueur;
	}
	
	/*@Test
	public void appuyerSurBoutonStartCacheMenu()
	{
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		App.centrerSurEcran(App.display, App.shell);
		App.shell.open();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		System.out.println(App.display.getCursorLocation());
		robot.mouseMove(App.shell.getLocation().x + App.btnStart.getLocation().x + 50, App.shell.getLocation().y + App.btnStart.getLocation().y + 60);
		System.out.println(App.display.getCursorLocation());
		robot.delay(200);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		System.out.println("compMenu visible : " + App.compMenu.isVisible());
		System.out.println("compJeu visible : " + App.compJeu.isVisible());
		assertTrue(!App.compMenu.isVisible() && App.compJeu.isVisible());
	}*/
	
	@Test
	public void deplacerVaisseauVerslaDroite()
	{		
		raz();
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		App.deplacerVaisseau(15);
		assertEquals(ancienX + 15,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void deplacerVaisseauVerslaGauche()
	{		
		raz();
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		App.deplacerVaisseau(-15);
		assertEquals(ancienX - 15,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void bloquerVaisseauContreLeBordDroit()
	{		
		raz();
		joueur.getLabel().setLocation(App.compJeu.getSize().x - 50,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		App.deplacerVaisseau(15);
		assertEquals(ancienX,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void bloquerVaisseauContreLeBordGauche()
	{		
		raz();
		joueur.getLabel().setLocation(0,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		App.deplacerVaisseau(-15);
		assertEquals(ancienX,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void deplacerTousLesAliensUneFois()
	{		
		raz();
		App.genererAliens(App.compJeu, App.display);
		Alien premierAlien = App.listeAliens.get(0);
		int ancienX = premierAlien.getLabel().getLocation().x;
		App.deplacementAliens();
		assertEquals(ancienX + App.mouvement, premierAlien.getLabel().getLocation().x);
	}
	
	@Test
	public void descendreAliensSiBordAtteint()
	{		
		raz();
		App.genererAliens(App.compJeu, App.display);
		App.jeuEnCours = true;
		App.bordAtteint = true;
		Alien premierAlien = App.listeAliens.get(0);
		int ancienY = premierAlien.getLabel().getLocation().y;
		App.deplacementAliens();
		assertEquals(ancienY + 10, premierAlien.getLabel().getLocation().y);
	}
	
	@Test
	public void enleverDeLaVieAuJoueurSiToucheParPropreTir()
	{		
		raz();
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		int ancienPV = joueur.getPv();
		Label lblTir = App.initialiserTirJoueur();
		while (App.tirEnCours)
		{
			App.tirJoueur(lblTir);
		}		
		assertEquals(ancienPV - 1, joueur.getPv());
	}
	
	@Test
	public void detruireUnAlienSiToucheParTirJoueur()
	{		
		raz();
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		Alien alien = new Alien(App.compJeu, App.display);		
		alien.setPv(1);
		alien.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU - 100);
		App.listeAliens.add(alien);
		Label lblTir = App.initialiserTirJoueur();
		while (App.tirEnCours)
		{
			App.tirJoueur(lblTir);
		}	
		assertTrue(alien.getLabel().isDisposed());
	}
	
	@Test
	public void augmenterLeScoreSiAlienDetruitParTirJoueur()
	{		
		raz();
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		Alien alien = new Alien(App.compJeu, App.display);		
		alien.setPv(1);
		alien.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU - 100);
		App.listeAliens.add(alien);
		int ancienScore = joueur.getPoints();
		Label lblTir = App.initialiserTirJoueur();
		while (App.tirEnCours)
		{
			App.tirJoueur(lblTir);
		}	
		assertEquals(ancienScore + 1, joueur.getPoints());
	}
	
	@Test
	public void detruireUnAlienSiToucheParTirAlien()
	{		
		raz();
		Alien alien1 = new Alien(App.compJeu, App.display);
		alien1.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU - 100);
		App.listeAliens.add(alien1);
		
		Alien alien2 = new Alien(App.compJeu, App.display);		
		alien2.setPv(1);
		alien2.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU);
		App.listeAliens.add(alien2);		

		Label lblTir = App.initialiserTirAlien();
		lblTir.setLocation(alien1.getLabel().getLocation().x, alien1.getLabel().getLocation().y + 51);
		App.tirAlien(lblTir);
			
		assertTrue(alien2.getLabel().isDisposed());
	}
	
	@Test
	public void pasAugmenterLeScoreSiAlienDetruitParTirAlien()
	{		
		raz();
		int ancienScore = joueur.getPoints();
		Alien alien1 = new Alien(App.compJeu, App.display);
		alien1.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU - 100);
		App.listeAliens.add(alien1);
		
		Alien alien2 = new Alien(App.compJeu, App.display);		
		alien2.setPv(1);
		alien2.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU);
		App.listeAliens.add(alien2);		

		Label lblTir = App.initialiserTirAlien();
		lblTir.setLocation(alien1.getLabel().getLocation().x, alien1.getLabel().getLocation().y + 51);
		App.tirAlien(lblTir);
			
		assertEquals(ancienScore, joueur.getPoints());
	}
	
	@Test
	public void perdreRemetLeScoreAZero()
	{		
		raz();
		joueur.setPoints(5);
		joueur.setPv(1);
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU);
		
		Alien alien1 = new Alien(App.compJeu, App.display);
		alien1.getLabel().setLocation(App.X_DEBUT_VAISSEAU, App.Y_DEBUT_VAISSEAU - 100);
		App.listeAliens.add(alien1);	

		Label lblTir = App.initialiserTirAlien();
		lblTir.setLocation(alien1.getLabel().getLocation().x, alien1.getLabel().getLocation().y + 51);
		App.tirAlien(lblTir);
			
		assertEquals(0, joueur.getPoints());
	}
	
	@Test
	public void acheterUneArmeDiminueLeNbDePoints()
	{		
		joueur.setPoints(15);
		DoubleCanons doubleCanons = new DoubleCanons();
		App.acheterArme(doubleCanons);
		assertEquals(15 - doubleCanons.getPrix(), joueur.getPoints());
	}
	
	@Test
	public void acheterUneArmeLEquipe()
	{		
		raz();
		joueur.setPoints(15);
		DoubleCanons doubleCanons = new DoubleCanons();
		App.acheterArme(doubleCanons);
		assertEquals(doubleCanons, joueur.getArmeEquipee());
	}
	
	@Test
	public void pasRetirerPointsSiAchatArmeDejaEquipee()
	{		
		raz();
		joueur.setPoints(40);
		DoubleCanons doubleCanons = new DoubleCanons();
		App.acheterArme(doubleCanons);
		int ancienPoints = joueur.getPoints();
		App.acheterArme(doubleCanons);
		assertEquals(ancienPoints, joueur.getPoints());
	}
	
	public void raz()
	{
		App.listeAliens.clear();
		joueur.getLabel().setLocation(0,0);
		joueur.setPv(3);
		joueur.setPoints(0);
		joueur.setArmeEquipee(null);
	}
    
}
