package iut.zero;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

public class App {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	final static int NB_ALIENS_PAR_LIGNE = 8;
	final static int NB_LIGNES = 4;
	final static int Y_DEBUT_VAISSEAU = 422;
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
	static Composite compShop;
	static Label lblTitre;
	static Label lblScore;
	static Label lblShop;
	static Label lblInfosShop;
	static Label lblNbPoints;
	static Label lblVie;
	static Button btnStart;
	static Button btnRetournerAuMenu;
	static Button btnNiveauSuivant;
	static Button btnShop;
	static Button btnQuitterShop;
	static Button btnLaser;
	static Button btnDoubleCanons;
	static List<Label> lblsVieJoueur;
	

	// Variables du tir
	static int yTir, xTir;
	static boolean tirEnCours = false;
	static boolean deuxiemePassage = false;
	static boolean entiteToucheeParJoueur = false;
	static boolean entiteToucheeParAlien = false;
	
	// Variable armes
	static Laser laser;
	static DoubleCanons doubleCanons;

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

	public static void initialisation() {
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
		btnRetournerAuMenu.setBounds(275, 280, 178, 42);
		btnRetournerAuMenu.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnRetournerAuMenu.setText("Retourner au menu");

		btnNiveauSuivant = new Button(compJeu, SWT.NONE);
		btnNiveauSuivant.setBounds(275, 170, 178, 42);
		btnNiveauSuivant.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnNiveauSuivant.setText("Niveau suivant");
		
		btnShop = new Button(compJeu, SWT.NONE);
		btnShop.setBounds(275, 225, 178, 42);
		btnShop.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnShop.setText("Boutique");		

		joueur = new Joueur(compJeu, display);
		joueur.setPoints(20);
		lblsVieJoueur = new ArrayList<Label>();

		lblScore = new Label(compJeu, SWT.NONE);
		lblScore.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblScore.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblScore.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblScore.setBounds(5, 0, 100, 21);
		
		lblVie = new Label(compJeu, SWT.NONE);
		lblVie.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblVie.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblVie.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblVie.setBounds(115, 0, 30, 21);
		lblVie.setText("PV : ");
		
		compShop = new Composite(shell, SWT.BORDER);
		compShop.setLayout(null);
		FormData fd_compShop = new FormData();
		fd_compShop.bottom = new FormAttachment(0, 492);
		fd_compShop.right = new FormAttachment(0, 732);
		fd_compShop.top = new FormAttachment(0);
		fd_compShop.left = new FormAttachment(0);
		compShop.setLayoutData(fd_compShop);
		compShop.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		
		lblShop = new Label(compShop, SWT.NONE);
		lblShop.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblShop.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblShop.setFont(SWTResourceManager.getFont("Segoe UI", 24, SWT.NORMAL));
		lblShop.setBounds(280, 8, 307, 57);
		lblShop.setText("Boutique");
		
		lblInfosShop = new Label(compShop, SWT.CENTER);
		lblInfosShop.setForeground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
		lblInfosShop.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblInfosShop.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblInfosShop.setBounds(190, 320, 387, 45);	
		
		lblNbPoints = new Label(compShop, SWT.CENTER);
		lblNbPoints.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblNbPoints.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNbPoints.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblNbPoints.setBounds(215, 80, 300, 50);
		
		btnQuitterShop = new Button(compShop, SWT.NONE);
		btnQuitterShop.setBounds(280, 410, 178, 42);
		btnQuitterShop.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnQuitterShop.setText("Retour");
		
		ajoutArmesDansShop();
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
				positionAlien += 50 + ECART_ENTRE_ALIENS; // 50 étant la largeur
															// de l'alien
				listeAliens.add(alien);
			}
		}
		bordDroitAlienLePlusADroite = 90 + (NB_ALIENS_PAR_LIGNE * 50) + (NB_ALIENS_PAR_LIGNE) * ECART_ENTRE_ALIENS;
		bordGaucheAlienLePlusAGauche = 90;
	}

	public static void generationListeners() {
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				lancementPartie();
			}
		});
		
		btnNiveauSuivant.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				lancementPartie();
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
		
		btnShop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compMenu.setVisible(false);
				compJeu.setVisible(false);
				compShop.setVisible(true);				
				lblNbPoints.setText("Points disponibles : " + joueur.getPoints());
			}
		});
		
		btnQuitterShop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compShop.setVisible(false);
				compJeu.setVisible(true);
			}
		});
		
		btnLaser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				acheterArme(laser);
			}
		});
		
		btnDoubleCanons.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				acheterArme(doubleCanons);
			}
		});

		compJeu.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent keyPressed) {
				if (jeuEnCours) {
					if (keyPressed.keyCode == SWT.ARROW_RIGHT) {
						deplacerVaisseau(15);
					} else if (keyPressed.keyCode == SWT.ARROW_LEFT) {
						deplacerVaisseau(-15);
					} else {
						if (keyPressed.keyCode == SWT.SPACE) {						
							if (!tirEnCours) {
								Display.getCurrent().asyncExec(new Runnable() {
									@Override
									public void run() {
										final Label lblTir = initialiserTirJoueur();
										if (joueur.getArmeEquipee() == null)
										{
											Display.getCurrent().timerExec(1, new Runnable() {
												@Override
												public void run() {
													tirJoueur(lblTir);
													if (tirEnCours) {
														Display.getCurrent().timerExec(1, this);
													}
												}
											});
										}
										else if (joueur.getArmeEquipee() instanceof Laser)
										{
											tirJoueur(lblTir);
											Display.getCurrent().timerExec(200, new Runnable() {
												@Override
												public void run() {													
													lblTir.dispose();
													tirEnCours = false;
												}
											});
										}										
									}
								});

							}
						}
					}
				}
			}
		});
	}

	public static boolean alienACettePosition(int x, int y, Label lblTir) {
		for (Alien alien : listeAliens) {
			Label lblAlien = alien.getLabel();
			int xAlien = lblAlien.getLocation().x;
			int yAlien = lblAlien.getLocation().y;
			int widthAlien = lblAlien.getSize().x;
			int heightAlien = lblAlien.getSize().y;
			if ((x >= xAlien && x <= xAlien + widthAlien) && (y <= yAlien && y >= yAlien - heightAlien))
			{
				alien.retirerPV(1);
				if (alien.getPv() <= 0) {
					if (!lblTir.getBackground().equals(SWTResourceManager.getColor(SWT.COLOR_WHITE))) // Si ce n'est pas un tir d'un alien
					{
						joueur.setPoints(joueur.getPoints() + 1);
						lblScore.setText("Score : " + joueur.getPoints());
					}
					listeAliens.remove(alien);
				}
				return true;
			}
		}
		return false;
	}
	
	public static void aliensDansLeLaser(int x, int y, Label lblTir) {
		List<Alien> aliensDansLaColonne = new ArrayList<>();
		for (Alien alien : listeAliens) 
		{
			Label lblAlien = alien.getLabel();
			int xAlien = lblAlien.getLocation().x;
			int yAlien = lblAlien.getLocation().y;
			int widthAlien = lblAlien.getSize().x;
			if ((x >= xAlien && x <= xAlien + widthAlien) && (y <= yAlien))				
			{
				alien.retirerPV(1);
				if (alien.getPv() <= 0) 
				{
					if (!lblTir.getBackground().equals(SWTResourceManager.getColor(SWT.COLOR_WHITE))) // Si ce n'est pas un tir d'un alien
					{
						joueur.setPoints(joueur.getPoints() + 1);
						lblScore.setText("Score : " + joueur.getPoints());
					}
					aliensDansLaColonne.add(alien);					
				}
			}
		}
		for (Alien alien : aliensDansLaColonne)
		{
			listeAliens.remove(alien);
		}
	}

	public static boolean joueurACettePosition(int x, int y) {
		Label lblVaisseau = joueur.getLabel();
		int xVaisseau = lblVaisseau.getLocation().x;
		int yVaisseau = lblVaisseau.getLocation().y;
		int widthVaisseau = lblVaisseau.getSize().x;
		int heightVaisseau = lblVaisseau.getSize().y;
		if ((x >= xVaisseau && x <= xVaisseau + widthVaisseau) && (y <= yVaisseau && y >= yVaisseau - heightVaisseau)) {
			lblVaisseau.setVisible(false);
			lblVaisseau.setVisible(true);
			joueur.retirerPV(1);
			if (joueur.getPv() >= 0)
			{
				lblsVieJoueur.get(joueur.getPv()).dispose();
				lblsVieJoueur.remove(joueur.getPv());
			}			
			return true;
		}
		return false;
	}

	public static void deplacerVaisseau(int mouvement) {
		Label lblVaisseau = joueur.getLabel();
		int bordDroitVaisseau = lblVaisseau.getLocation().x + lblVaisseau.getSize().x;
		if ((mouvement > 0 && !(bordDroitVaisseau + mouvement > compJeu.getSize().x))
				|| (mouvement < 0 && !(lblVaisseau.getLocation().x + mouvement < 0))) {
			lblVaisseau.setLocation(lblVaisseau.getLocation().x + mouvement, lblVaisseau.getLocation().y);
		}
	}

	public static void deplacementAliens() {
		if (((bordDroitAlienLePlusADroite >= compJeu.getSize().x) || (bordGaucheAlienLePlusAGauche + mouvement < 0))
				&& !bordAtteint && !descendu) {
			bordAtteint = true;
			if (bordDroitAlienLePlusADroite >= compJeu.getSize().x) {
				bordGaucheAlienLePlusAGauche = bordDroitAlienLePlusADroite - (NB_ALIENS_PAR_LIGNE * 50)
						- (NB_ALIENS_PAR_LIGNE) * ECART_ENTRE_ALIENS;
			} else {
				bordDroitAlienLePlusADroite = bordGaucheAlienLePlusAGauche + (NB_ALIENS_PAR_LIGNE * 50)
						+ (NB_ALIENS_PAR_LIGNE) * ECART_ENTRE_ALIENS;
			}
			mouvement *= -1;
		}
		deplacerAliens();
		if (jeuEnCours) {
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
	}

	public static void deplacerAliens() {
		for (Alien alien : listeAliens) {
			if (jeuEnCours) // Si le jeu n'est plus en cours, donc qu'un alien a
							// atteint le joueur, on ne déplace pas les aliens
							// restants
			{
				Label lblAlien = alien.getLabel();
				if (!bordAtteint || descendu) {
					lblAlien.setLocation(lblAlien.getLocation().x + mouvement, lblAlien.getLocation().y);
				} else if (bordAtteint && !descendu) {
					lblAlien.setLocation(lblAlien.getLocation().x, lblAlien.getLocation().y + 10);
				}
				if (lblAlien.getLocation().y >= (Y_DEBUT_VAISSEAU - 50)) {
					finDeLaPartie();
				}
			}
		}
	}

	public static void finDeLaPartie() {
		if (listeAliens.isEmpty()) //Dans le cas où le joueur a gagné
		{
			btnNiveauSuivant.setVisible(true);
			btnShop.setVisible(true);
		}
		else
		{
			joueur.setPoints(0); //Le joueur a perdu, on remet son score à 0
			joueur.setArmeEquipee(null);			
		}
		jeuEnCours = false;
		btnRetournerAuMenu.setVisible(true);
	}

	/**
	 * Initialise le tir et renvoie le label correspondant
	 * 
	 * @return Le label du tir initialisé
	 */
	public static Label initialiserTirJoueur() {
		Label lblVaisseau = joueur.getLabel();
		int x = lblVaisseau.getBounds().x;
		int y = lblVaisseau.getBounds().y;
		Label lblTir = new Label(compJeu, SWT.NONE);
		tirEnCours = true;
		
		if (joueur.getArmeEquipee() == null)
		{
			lblTir.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			lblTir.setBounds(x + lblVaisseau.getSize().x / 2, y - (lblVaisseau.getSize().y + 1), 5, 30);
			compJeu.update();
			xTir = lblTir.getBounds().x;
			yTir = lblTir.getBounds().y;
		}
		else if (joueur.getArmeEquipee() instanceof Laser)
		{
			lblTir.setBackground(laser.getCouleur());
			lblTir.moveAbove(null);
			lblTir.setBounds(x, 0, 50, compJeu.getSize().y - 150);
			System.out.println(lblTir.getBounds());
			compJeu.update();
			xTir = lblTir.getBounds().x;
			yTir = lblTir.getBounds().y;
		}
		
		return lblTir;
	}

	public static void tirJoueur(Label lblTir) {
		if (joueur.getArmeEquipee() == null)
		{
			yTir -= 8;
			lblTir.setLocation(xTir, yTir);
			if (alienACettePosition(xTir, yTir, lblTir) || joueurACettePosition(xTir, yTir)) {
				entiteToucheeParJoueur = true;
			} else {
				lblTir.redraw();
				compJeu.update();
			}
			if (yTir - 8 < 0 || entiteToucheeParJoueur) {
				if (!deuxiemePassage && !entiteToucheeParJoueur) {
					yTir = compJeu.getSize().y + 8;
					deuxiemePassage = true;
				} else {
					lblTir.dispose();
					tirEnCours = false;
					deuxiemePassage = false;
					entiteToucheeParJoueur = false;
					if (listeAliens.isEmpty() || joueur.getPv() == 0) {
						finDeLaPartie();
					}
				}
			}
		}
		else if (joueur.getArmeEquipee() instanceof Laser)
		{
			aliensDansLeLaser(lblTir.getLocation().x, lblTir.getLocation().y, lblTir);			
			if (listeAliens.isEmpty() || joueur.getPv() == 0) {
				finDeLaPartie();
			}
		}
	}

	/**
	 * Initialise le tir et renvoie le label correspondant
	 * 
	 * @return Le label du tir initialisé
	 */
	public static Label initialiserTirAlien() {
		Label lblTir = null;
		if (!listeAliens.isEmpty())
		{
			Random random = new Random();
			int indexAleatoire = random.nextInt(listeAliens.size());
			Alien alien = listeAliens.get(indexAleatoire);
			Label lblAlien = alien.getLabel();
			int x = lblAlien.getBounds().x;
			int y = lblAlien.getBounds().y;
			lblTir = new Label(compJeu, SWT.NONE);
			lblTir.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblTir.setBounds(x + lblAlien.getSize().x / 2, y + 1, 5, 30);
			compJeu.update();
		}		
		return lblTir;
	}

	public static void tirAlien(Label lblTir) {
		if (lblTir != null)
		{
			int xTir = lblTir.getLocation().x;
			int yTir = lblTir.getLocation().y;
			yTir += 8;
			lblTir.setLocation(xTir, yTir);
			if (alienACettePosition(xTir, yTir, lblTir) || joueurACettePosition(xTir, yTir)) {
				entiteToucheeParAlien = true;
				System.out.println("alien touché !");
			} else {
				lblTir.redraw();
				compJeu.update();
			}
			if (yTir + 8 > compJeu.getSize().y || entiteToucheeParAlien) {
				lblTir.dispose();
				entiteToucheeParAlien = false;
				if (listeAliens.isEmpty() || joueur.getPv() == 0) {
					finDeLaPartie();
				}
			}
		}		
	}

	public static void lancementPartie() {
		compJeu.setVisible(true);
		compMenu.setVisible(false);
		compShop.setVisible(false);
		btnRetournerAuMenu.setVisible(false);
		btnNiveauSuivant.setVisible(false);
		btnShop.setVisible(false);

		joueur.getLabel().setLocation(X_DEBUT_VAISSEAU, Y_DEBUT_VAISSEAU);
		joueur.getLabel().setVisible(true);
		joueur.setPv(3);
		int xLblViePrecedent = 140;
		int ecartEntreLbl = 15;
		for (int i = 0; i < 3; i++)
		{
			Label lblVie = new Label(compJeu, SWT.NONE);
			lblVie.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			lblVie.setSize(9, 15);
			lblVie.setLocation(xLblViePrecedent + ecartEntreLbl, 5);
			xLblViePrecedent = lblVie.getLocation().x;
			lblsVieJoueur.add(lblVie);
		}
		genererAliens(compJeu, display);
		lblScore.setText("Score : " + joueur.getPoints());

		jeuEnCours = true;
		mouvement = 10;
		
		lancementDeplacementAliens();
		lancementTirsAutoAliens();

	}

	public static void lancementDeplacementAliens() {
		// Deplacement des aliens
		Display.getCurrent().timerExec(200, new Runnable() {
			@Override
			public void run() {
				deplacementAliens();
				if (jeuEnCours) {
					Display.getCurrent().timerExec(200, this);
				} else {
					Display.getCurrent().timerExec(-1, this);
				}

			}
		});
	}

	public static void lancementTirsAutoAliens() {
		// Tir des aliens
		Display.getCurrent().timerExec(600, new Runnable() {
			@Override
			public void run() {
				Display.getCurrent().asyncExec(new Runnable() {
					@Override
					public void run() {
						final Label lblTir = initialiserTirAlien();
						Display.getCurrent().timerExec(1, new Runnable() {
							@Override
							public void run() {
								tirAlien(lblTir);
								if (lblTir != null && !lblTir.isDisposed()) {
									Display.getCurrent().timerExec(1, this);
								}
							}
						});
					}
				});
				if (jeuEnCours) {
					Display.getCurrent().timerExec(600, this);
				} else {
					Display.getCurrent().timerExec(-1, this);
				}
			}
		});
	}
	
	public static void ajoutArmesDansShop()
	{
		laser = new Laser();		
		Label lblLaser = new Label(compShop, SWT.NONE);
		lblLaser.setBackground(laser.couleur);
		lblLaser.setSize(50, 50);
		lblLaser.setLocation(80, 138);
				
		Label lblDescLaser = new Label(compShop, SWT.NONE);
		lblDescLaser.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDescLaser.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblDescLaser.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblDescLaser.setSize(420, 50);
		lblDescLaser.setLocation(150, 138);
		lblDescLaser.setText(laser.description);
		
		btnLaser = new Button(compShop, SWT.NONE);
		btnLaser.setBounds(580, 138, 50, 50);
		btnLaser.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnLaser.setText(String.valueOf(laser.prix));
		
		doubleCanons = new DoubleCanons();		
		Label lblDoubleCanons = new Label(compShop, SWT.NONE);
		lblDoubleCanons.setBackground(doubleCanons.couleur);
		lblDoubleCanons.setSize(50, 50);
		lblDoubleCanons.setLocation(80, 218);
		
		Label lblDescDoubleCanons = new Label(compShop, SWT.NONE);
		lblDescDoubleCanons.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDescDoubleCanons.setBackground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		lblDescDoubleCanons.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblDescDoubleCanons.setSize(350, 50);
		lblDescDoubleCanons.setLocation(150, 218);
		lblDescDoubleCanons.setText(doubleCanons.description);
		
		btnDoubleCanons = new Button(compShop, SWT.NONE);
		btnDoubleCanons.setBounds(580, 218, 50, 50);
		btnDoubleCanons.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnDoubleCanons.setText(String.valueOf(doubleCanons.prix));
	}
	
	public static void acheterArme(Arme arme)
	{
		if (joueur.getArmeEquipee() != null && joueur.getArmeEquipee().equals(arme))
		{
			lblInfosShop.setText("Vous disposez déjà de cette arme");
		}
		else
		{
			if (joueur.getPoints() >= arme.getPrix())
			{
				joueur.setPoints(joueur.getPoints() - arme.getPrix());
				joueur.setArmeEquipee(arme);
				lblInfosShop.setText("");
				lblNbPoints.setText("Points disponibles : " + joueur.getPoints());
			}
			else
			{
				lblInfosShop.setText("Points insuffisants pour acheter cette arme");
			}
		}		
	}
}
