package iut.zero;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class App {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	final static int NB_ALIENS_PAR_LIGNE = 3;
	final static int NB_LIGNES = 3;
	
	int bordDroitDernierAlien;
	int bordGauchePremierAlien = 130;
	static Map<Label,Alien> listeAliens = new HashMap<Label,Alien>();
	static boolean jeuEnCours = false;
	static Joueur joueur;

	public static void main(String[] args) {
		
		joueur = new Joueur();
		
		final Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(732, 492);
		shell.setText("Space Invaders");
		shell.setLayout(new FormLayout());

		final Composite compMenu = new Composite(shell, SWT.BORDER);
		FormData fd_compMenu = new FormData();
		fd_compMenu.bottom = new FormAttachment(0, 492);
		fd_compMenu.right = new FormAttachment(0, 732);
		fd_compMenu.top = new FormAttachment(0);
		fd_compMenu.left = new FormAttachment(0);
		compMenu.setLayoutData(fd_compMenu);
		compMenu.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		Label lblTitre = new Label(compMenu, SWT.NONE);
		lblTitre.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblTitre.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblTitre.setFont(SWTResourceManager.getFont("Segoe UI", 27, SWT.NORMAL));
		lblTitre.setBounds(199, 68, 307, 67);
		lblTitre.setText("Space Invaders");

		Button btnStart = new Button(compMenu, SWT.CENTER);
		btnStart.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnStart.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnStart.setBounds(284, 226, 117, 43);
		btnStart.setText("Start");
		centrerSurEcran(display, shell);

		final Composite compJeu = new Composite(shell, SWT.BORDER);
		compJeu.setLayout(null);
		FormData fd_compJeu = new FormData();
		fd_compJeu.bottom = new FormAttachment(0, 492);
		fd_compJeu.right = new FormAttachment(0, 732);
		fd_compJeu.top = new FormAttachment(0);
		fd_compJeu.left = new FormAttachment(0);
		compJeu.setLayoutData(fd_compJeu);
		compJeu.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		compJeu.setVisible(true);

		final Button btnRetournerAuMenu = new Button(compJeu, SWT.NONE);
		btnRetournerAuMenu.setBounds(275, 210, 178, 42);
		btnRetournerAuMenu.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnRetournerAuMenu.setText("Retourner au menu");

		final Label lblVaisseau = new Label(compJeu, SWT.NONE);		
		lblVaisseau.setImage(
				new Image(display, joueur.getLienImage()));		

		// Gestion des listeners

		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compJeu.setVisible(true);
				compMenu.setVisible(false);
				btnRetournerAuMenu.setVisible(false);
				
				lblVaisseau.setBounds(330, 380, 50, 50);
				genererAliens(compJeu, display);				
				
				jeuEnCours = true;
				/*Display.getCurrent().asyncExec(new Runnable() {
					@Override
					public void run() {
						// Deplacement des aliens
						while (jeuEnCours) {
							Display.getCurrent().timerExec(10, new Runnable() {
								@Override
								public void run() {
									for (Label lblAlien : listeAliens) {
										lblAlien.setLocation(lblAlien.getBounds().x + 10, lblAlien.getBounds().y);
									}
								}
							});
						}
					}
				});*/
			}
		});

		btnRetournerAuMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compMenu.setVisible(true);
				compJeu.setVisible(false);
			}
		});

		compJeu.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent keyPressed) {
				if (jeuEnCours) 
				{
					final int x = lblVaisseau.getBounds().x;
					final int y = lblVaisseau.getBounds().y;
					int width = lblVaisseau.getBounds().width;
					int bordDroitVaisseau = x + width;
					int bordGaucheVaisseau = x;
					if (keyPressed.keyCode == SWT.ARROW_RIGHT) 
					{
						if (!(bordDroitVaisseau + 30 > compJeu.getSize().x)) 
						{
							lblVaisseau.setLocation(x + 15, y);
						}
					} else if (keyPressed.keyCode == SWT.ARROW_LEFT) 
					{
						if (!(bordGaucheVaisseau - 15 < 0)) 
						{
							lblVaisseau.setLocation(x - 15, y);
						}
					} else {
						if (keyPressed.keyCode == SWT.SPACE) 
						{
							Display.getCurrent().asyncExec(new Runnable() {
								@Override
								public void run() {
									Label lblTir = new Label(compJeu, SWT.NONE);
									lblTir.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
									lblTir.setBounds(x + 25, y, 5, 30);
									compJeu.update();
									int xTir = lblTir.getBounds().x;
									int yTir = lblTir.getBounds().y;
									while (yTir - 1 > 0) 
									{
										yTir -= 1;
										lblTir.setLocation(xTir, yTir);
										if (alienACettePosition(xTir, yTir)) 
										{
											yTir = 0;
										} 
										else 
										{
											lblTir.redraw();
											compJeu.update();
											try 
											{
												Thread.sleep(2);
											} catch (InterruptedException e) 
											{
												e.printStackTrace();
											}
										}
									}
									lblTir.dispose();
									if (listeAliens.isEmpty())
									{
										jeuEnCours = false;
										btnRetournerAuMenu.setVisible(true);
									}
								}
							});
						}
					}
				}
			}// vraie fin fonction
		});

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) 
		{
			if (!display.readAndDispatch()) 
			{
				display.sleep();
			}
		}
	}

	// Centre la fenetre au milieu de l'écran
	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));
	}

	public static void genererAliens(Composite compJeu, Display display) {
		int positionAlien;
		for (int i = 0; i < NB_LIGNES; i++) {
			positionAlien = 90; // La position du premier alien (pour chaque
								// ligne)
			for (int j = 0; j < NB_ALIENS_PAR_LIGNE; j++) {
				Alien alien = new Alien();
				Label lblAlien = new Label(compJeu, SWT.NONE);
				lblAlien.setBounds(positionAlien, 50 + (i * 50) + 20, 50, 50);
				positionAlien += 60;
				lblAlien.setImage(
						new Image(display, alien.getLienImage()));
				listeAliens.put(lblAlien, alien);
			}
		}
	}

	public static boolean alienACettePosition(int x, int y) {
		for (Label lblAlien : listeAliens.keySet()) 
		{
			if ((x >= lblAlien.getLocation().x && x <= lblAlien.getLocation().x + 50)
					&& y == lblAlien.getLocation().y) 
			{
				Alien alien = listeAliens.get(lblAlien);
				alien.retirerPV(1);
				if (alien.getPv() <= 0)
				{
					listeAliens.remove(lblAlien);
					lblAlien.dispose();
				}				
				return true;
			}
		}
		return false;
	}
}
