package iut.zero;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.eclipse.swt.SWT;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	private App app;
	private Robot robot;
	
	@Before
	public void initialisation()
	{
		app = new App();		
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
		App.initialisation();
		App.generationListeners();
		App.shell.layout();
		App.shell.pack();
		Joueur joueur = App.joueur;
		joueur.getLabel().setLocation(App.X_DEBUT_VAISSEAU,App.Y_DEBUT_VAISSEAU);
		int ancienX = joueur.getLabel().getLocation().x;
		App.deplacerVaisseau(15);
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
		App.deplacerVaisseau(-15);
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
		App.deplacerVaisseau(15);
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
		App.deplacerVaisseau(-15);
		assertEquals(ancienX,joueur.getLabel().getBounds().x);		
	}
	
    
}
