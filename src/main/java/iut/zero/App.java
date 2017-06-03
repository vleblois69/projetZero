package iut.zero;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
	private String choixJoueur;
	private static HashMap<Integer, String> choixPossibles = new HashMap<Integer,String>();
	private String choixOrdi;
	
	public int additionner(int premierChiffre, int deuxiemeChiffre)
	{
		return premierChiffre + deuxiemeChiffre;
	}
	
	public int soustraire(int premierChiffre, int deuxiemeChiffre)
	{
		return premierChiffre - deuxiemeChiffre;
	}
	
	public int generationChiffreAleatoire()
	{
		int resultat;
		Random r = new Random();
		resultat = 1 + r.nextInt(3-1);
		return resultat;
	}
		
    public static void main( String[] args )
    {
        choixPossibles.put(1,"Pierre");
        choixPossibles.put(2,"Feuille");
        choixPossibles.put(3, "Ciseaux");
        
        Scanner s = new Scanner(System.in);
        
        
    }
}
