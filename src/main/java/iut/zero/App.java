package iut.zero;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class App {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	final static int NB_ALIENS_PAR_LIGNE = 10;
	final static int NB_LIGNES = 3;
	final static int Y_DEBUT_VAISSEAU = 432;
	final static int X_DEBUT_VAISSEAU = 380;
	final static int ECART_ENTRE_ALIENS = 10;

	static int mouvement;
	static List<Alien> listeAliens = new ArrayList<Alien>();
	static boolean jeuEnCours = false;
	static Joueur joueur;
	static int bordDroitAlienLePlusADroite;
	static int bordGaucheAlienLePlusAGauche;
	static boolean bordAtteint = false;
	static boolean descendu = false;
	
	// Composants
	static Display display;
	static Shell shell;
	static Composite compMenu;
	static Composite compJeu;
	static Label lblTitre;
	static Button btnStart;
	static Button btnRetournerAuMenu;
	static Label lblScore;

	// Variables du tir
	static int yTir, xTir;
	static boolean tirEnCours = false;

	public static void main(String[] args) {

		initialisation();
		generationListeners();

		shell.open();
		shell.layout();
		shell.pack();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public static void initialisation()
	{
		display = Display.getDefault();
		shell = new Shell();
		shell.setSize(732, 492);
		shell.setText("Space Invaders");
		shell.setLayout(new FormLayout());

		compMenu = new Composite(shell, SWT.BORDER);
		FormData fd_compMenu = new FormData();
		fd_compMenu.bottom = new FormAttachment(0, 492);
		fd_compMenu.right = new FormAttachment(0, 732);
		fd_compMenu.top = new FormAttachment(0);
		fd_compMenu.left = new FormAttachment(0);
		compMenu.setLayoutData(fd_compMenu);
		compMenu.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		lblTitre = new Label(compMenu, SWT.NONE);
		lblTitre.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblTitre.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblTitre.setFont(SWTResourceManager.getFont("Segoe UI", 27, SWT.NORMAL));
		lblTitre.setBounds(199, 68, 307, 67);
		lblTitre.setText("Space Invaders");

		btnStart = new Button(compMenu, SWT.CENTER);		
		btnStart.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnStart.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnStart.setBounds(284, 226, 117, 43);
		btnStart.setText("Start");
		centrerSurEcran(display, shell);

		compJeu = new Composite(shell, SWT.BORDER);
		compJeu.setLayout(null);
		FormData fd_compJeu = new FormData();
		fd_compJeu.bottom = new FormAttachment(0, 492);
		fd_compJeu.right = new FormAttachment(0, 732);
		fd_compJeu.top = new FormAttachment(0);
		fd_compJeu.left = new FormAttachment(0);
		compJeu.setLayoutData(fd_compJeu);
		compJeu.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		btnRetournerAuMenu = new Button(compJeu, SWT.NONE);
		btnRetournerAuMenu.setBounds(275, 210, 178, 42);
		btnRetournerAuMenu.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnRetournerAuMenu.setText("Retourner au menu");

		joueur = new Joueur(compJeu, display);
		
		lblScore = new Label(compJeu, SWT.NONE);
		lblScore.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblScore.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblScore.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblScore.setBounds(5,0,104,21);
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
				Alien alien = new Alien(compJeu, display);
				Label lblAlien = alien.getLabel();
				lblAlien.setBounds(positionAlien, 50 + (i * 50) + 20, 50, 50);
				positionAlien += 50 + ECART_ENTRE_ALIENS; //50 étant la largeur de l'alien
				listeAliens.add(alien);
			}
		}
		bordDroitAlienLePlusADroite = 90 + (NB_ALIENS_PAR_LIGNE * 50) + (NB_ALIENS_PAR_LIGNE)*ECART_ENTRE_ALIENS;
		bordGaucheAlienLePlusAGauche = 90;
	}

	public static void generationListeners()
	{
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("Passe bien dans le listener");
				compJeu.setVisible(true);
				compMenu.setVisible(false);
				btnRetournerAuMenu.setVisible(false);

				joueur.getLabel().setLocation(X_DEBUT_VAISSEAU, Y_DEBUT_VAISSEAU);
				genererAliens(compJeu, display);
				lblScore.setText("Score : " + joueur.getPoints());

				jeuEnCours = true;
				mouvement = 10;
				Display.getCurrent().timerExec(200, new Runnable() {

					@Override
					public void run() {

						if (((bordDroitAlienLePlusADroite >= compJeu.getSize().x)
								|| (bordGaucheAlienLePlusAGauche + mouvement < 0)) && !bordAtteint && !descendu) {	
							bordAtteint = true;
							if (bordDroitAlienLePlusADroite >= compJeu.getSize().x) {
								bordGaucheAlienLePlusAGauche = bordDroitAlienLePlusADroite - (NB_ALIENS_PAR_LIGNE * 50) - (NB_ALIENS_PAR_LIGNE)*ECART_ENTRE_ALIENS;
							} else {
								bordDroitAlienLePlusADroite = bordGaucheAlienLePlusAGauche + (NB_ALIENS_PAR_LIGNE * 50) + (NB_ALIENS_PAR_LIGNE)*ECART_ENTRE_ALIENS;
							}
							mouvement *= -1;

						}
						for (Alien alien : listeAliens) {
							Label lblAlien = alien.getLabel();
							if (!bordAtteint || descendu) {
								lblAlien.setLocation(lblAlien.getLocation().x + mouvement, lblAlien.getLocation().y);
							} else if (bordAtteint && !descendu) {
								lblAlien.setLocation(lblAlien.getLocation().x, lblAlien.getLocation().y + 10);
							}
							if (lblAlien.getLocation().y >= (Y_DEBUT_VAISSEAU - 50)) {
								jeuEnCours = false;
								btnRetournerAuMenu.setVisible(true);
								Display.getCurrent().timerExec(-1, this);
							}
							if (jeuEnCours) {
								Display.getCurrent().timerExec(200, this);
							}
						}
						if (!bordAtteint || descendu) {
							bordDroitAlienLePlusADroite += mouvement;							
							bordGaucheAlienLePlusAGauche += mouvement;							
						}
						if (bordAtteint && !descendu) {
							descendu = true;
						} else if (bordAtteint && descendu) {
							bordAtteint = false;
						} else if (!bordAtteint && descendu) {
							descendu = false;
						}
					}
				});
			}
		});

		btnRetournerAuMenu.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compMenu.setVisible(true);
				compJeu.setVisible(false);
				for (Alien alien : listeAliens) {
					alien.getLabel().dispose();
				}
				listeAliens.clear();
			}
		});

		compJeu.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent keyPressed) {
				if (jeuEnCours) {
					Label lblVaisseau = joueur.getLabel();
					final int x = lblVaisseau.getBounds().x;
					final int y = lblVaisseau.getBounds().y;					
					if (keyPressed.keyCode == SWT.ARROW_RIGHT) {
						joueur.deplacerVaisseau(15);
					} else if (keyPressed.keyCode == SWT.ARROW_LEFT) {
						joueur.deplacerVaisseau(-15);
					} else {
						if (keyPressed.keyCode == SWT.SPACE) {
							if (!tirEnCours)
							{
								Display.getCurrent().asyncExec(new Runnable() {
									@Override
									public void run() {
										final Label lblTir = new Label(compJeu, SWT.NONE);
										tirEnCours = true;
										lblTir.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
										lblTir.setBounds(x + 25, y, 5, 30);
										compJeu.update();
										xTir = lblTir.getBounds().x;
										yTir = lblTir.getBounds().y;
										Display.getCurrent().timerExec(1, new Runnable() {
											@Override
											public void run() {
												yTir -= 8;
												lblTir.setLocation(xTir, yTir);
												if (alienACettePosition(xTir, yTir)) {
													yTir = 0;
												} else {
													lblTir.redraw();
													compJeu.update();
												}
												if (yTir - 8 > 0) {
													Display.getCurrent().timerExec(1, this);
												} else {
													lblTir.dispose();
													tirEnCours = false;
													if (listeAliens.isEmpty()) {
														jeuEnCours = false;
														btnRetournerAuMenu.setVisible(true);
													}
												}
											}
										});
									}
								});
							}							
						}
					}
				}
			}
		});
	}
	
	
	public static boolean alienACettePosition(int x, int y) {
		for (Alien alien : listeAliens) {
			Label lblAlien = alien.getLabel();
			if ((x >= lblAlien.getLocation().x && x <= lblAlien.getLocation().x + 50)
					&& y <= lblAlien.getLocation().y) {
				alien.retirerPV(1);
				if (alien.getPv() <= 0) {
					joueur.setPoints(joueur.getPoints() + 1);
					lblScore.setText("Score : " + joueur.getPoints());
					listeAliens.remove(alien);
				}
				return true;
			}
		}
		return false;
	}
}
