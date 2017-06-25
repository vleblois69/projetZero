package iut.zero;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	private App app;
	
	@Before
	public void initialisation()
	{
		app = new App();
	}
	
	@Test
	public void deplacerVaisseauVerslaDroite()
	{
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		Joueur joueur = App.joueur;
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		joueur.deplacerVaisseau(15);
		assertEquals(ancienX + 15,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void deplacerVaisseauVerslaGauche()
	{
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		Joueur joueur = App.joueur;
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		joueur.deplacerVaisseau(-15);
		assertEquals(ancienX - 15,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void bloquerVaisseauContreLeBordDroit()
	{
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		Joueur joueur = App.joueur;
		joueur.getLabel().setLocation(App.compJeu.getSize().x - 50,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		joueur.deplacerVaisseau(15);
		assertEquals(ancienX,joueur.getLabel().getBounds().x);		
	}
	
	@Test
	public void bloquerVaisseauContreLeBordGauche()
	{
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		Joueur joueur = App.joueur;
		joueur.getLabel().setLocation(0,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		joueur.deplacerVaisseau(-15);
		assertEquals(ancienX,joueur.getLabel().getBounds().x);		
	}
	
    
}
