package iut.zero;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
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

public class Window {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
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

		Button btnRetournerAuMenu = new Button(compJeu, SWT.NONE);
		btnRetournerAuMenu.setBounds(330, 280, 178, 38);
		btnRetournerAuMenu.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnRetournerAuMenu.setText("Retourner au menu");
		btnRetournerAuMenu.setVisible(false);
		
		final Label lblVaisseau = new Label(compJeu, SWT.NONE);
		lblVaisseau.setBounds(330, 380, 50, 50);
		lblVaisseau.setImage(
				new Image(display, "D:/Mes Documents/Cours Tests Unitaires/projetZero/src/images/vaisseau.png"));

		// Gestion des listeners

		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compJeu.setVisible(true);
				compMenu.setVisible(false);
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
				int x = lblVaisseau.getBounds().x;
				int y = lblVaisseau.getBounds().y;
				int width = lblVaisseau.getBounds().width;
				int bordDroitVaisseau = x + width;
				int bordGaucheVaisseau = x;
				if (keyPressed.keyCode == SWT.ARROW_RIGHT) {
					if (!(bordDroitVaisseau + 30 > compJeu.getSize().x)) {
						lblVaisseau.setLocation(x + 15, y);
					}
				} else if (keyPressed.keyCode == SWT.ARROW_LEFT) {
					if (!(bordGaucheVaisseau - 15 < 0)) {
						lblVaisseau.setLocation(x - 15, y);
					}
				} else {
					if (keyPressed.keyCode == SWT.SPACE) {
						Label lblTir = new Label(compJeu, SWT.NONE);
						lblTir.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						lblTir.setBounds(x + 25, y - 25, 5, 50);
						compJeu.layout();
						int xTir = lblTir.getBounds().x;
						int yTir = lblTir.getBounds().y;
						while (yTir - 10 > 0) {
							yTir -= 10;
							lblTir.setLocation(xTir, yTir);
						}
						lblTir.dispose();
					}
				}
			}
		});
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
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
}
