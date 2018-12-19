package main;

import java.util.ArrayList;
import java.util.Scanner;

import ihmCui.Affichage;
import pseudoCode.Algorithme;
import pseudoCode.AlgorithmeException;
import pseudoCode.Programme;
import pseudoCode.Variable;

/*
 * Retour en arriere :
 * Tableau du projet en serialize qu'on reprend
 */

public class Controleur
{

	/** nom du fichier */
	private final String	   input		  = "tests/Test1.algo";


	/** objet programme */
	private Programme		   prog;

	/** lecteur de fichier */
	private LectureFichier	   lecture;

	private Scanner			   sc;

	private int				   ligneAAttendre = -1;

	private int				   ligneRestantes = -1;

	private ArrayList<Integer> etapes;

	private static Controleur  ctrl;

	private Affichage		   aff;

	public static Controleur getControleur ()
	{
		if ( Controleur.ctrl == null ) return new Controleur();
		else return Controleur.ctrl;
	}

	/**
	 * Constructeur du controleur.
	 */
	private Controleur ()
	{
		this.etapes = new ArrayList<Integer>();
		Controleur.ctrl = this;
		this.sc = new Scanner( System.in );
		this.lecture = new LectureFichier( input );
		try
		{
			this.prog = new Programme( lecture.getTexteParLigne() );
		}
		catch ( AlgorithmeException e )
		{
			e.printStackTrace();
		}
		getVariableATracer();
		this.aff = new Affichage( lecture.getTexteParLigne(), prog );

		this.aff.afficher();

		while ( !prog.getMain().estTerminer() )
		{
			try
			{
				if ( prog.getCurrent().ligneSuivante() )
				{
					this.aff.afficher();
				}
			}
			catch ( AlgorithmeException e )
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * Permet à l'utilisateur de renseigner la valeur d'une variable
	 * 
	 * @param nomVar
	 *            nom de la variable
	 */
	public void lireVariable ( String nomVar )
	{
		System.out.print( "Entrez la valeur de " + nomVar + " : " );
		String valeur = this.sc.nextLine();
		this.prog.traceExec += "l:"+valeur+"\n";
		this.prog.getCurrent().setValeur( nomVar, valeur );
	}

	public void getVariableATracer ()
	{
		for ( Algorithme algo : this.prog.getAlgos() )
		{
			for ( Variable var : algo.getVariables() )
			{
				System.out.print( "Tracer la variable \"" + var.getNom() + "\" de l'algo " + algo.getNom() + " (Y/n) : " );
				String reponse = sc.nextLine();
				if ( reponse.trim().equalsIgnoreCase( "Y" ) || reponse.trim().equals( "" ) )
				{
					prog.ajouterVariableATracer( var );
				}
			}
		}

	}

	/**
	 * Attend une action de l'utilisateur
	 */
	public void attend ()
	{
		this.aff.afficher();

		etapes.add( this.prog.getCurrent().getLigneCourrante() );

		if ( this.ligneRestantes > 0 )
		{
			ligneRestantes--;
		}
		else if ( ligneAAttendre != -1 && ligneAAttendre > prog.getCurrent().getLigneCourrante() )
		{

		}
		else
		{
			ligneRestantes = -1;
			ligneAAttendre = -1;
			String commande = this.sc.nextLine();
			System.out.println( "commande + " + commande );
			if ( !commande.equals( "" ))
				this.prog.traceExec += "a:";
			/*
			 * Gestion des commandes
			 */
			if ( commande.equalsIgnoreCase( "b" ) )
			{
				retour();
			}
			else if ( commande.matches( "[\\+\\-] var [\\w]+" ) )
			{
				String varATracer = commande.replaceAll( "[\\+\\-] var ([\\w]+)", "$1" );
				boolean ajouter = commande.replaceAll( "([\\+\\-]) var [\\w]+", "$1" ).equals( "+" );
				for ( Algorithme algo : this.prog.getAlgos() )
				{
					for ( Variable var : algo.getVariables() )
					{
						if ( var.getNom().equals( varATracer ))
						{
							if ( ajouter )
							{
								prog.ajouterVariableATracer( var );
							}
							else
							{
								prog.enleverVariableATracer( var );
							}
						}
					}
				}
				reste();
			}
			else if ( commande.matches( "[Ll][0-9]+" ) )
			{
				int ligne = Integer.parseInt( commande.replaceAll( "[Ll]([0-9]+)", "$1" ) )-1;
				ligneAAttendre = ligne;
				this.etapes = new ArrayList<Integer>();
				this.prog.reset();
			}
			else if ( commande.matches( "[+-] var \\w*" ) )
			{
				if ( commande.startsWith( "+" ) ) System.out.println( "ajout de la variable " + commande.replaceAll( "[+-] var (\\w*)", "$1" ) );
				else System.out.println( "suppression de variable " + commande.replaceAll( "[+-] var (\\w*)", "$1" ) );
			}
			
			if ( !commande.equals( "" ))
				this.prog.traceExec += "\n";
		}
	}
	
	private void reste ()
	{
		this.ligneAAttendre = this.etapes.get( this.etapes.indexOf( prog.getCurrent().getLigneCourrante() ) );
		this.prog.reset();
		this.etapes = new ArrayList<Integer>();
	}

	private void retour ()
	{
		this.ligneAAttendre = this.etapes.get( this.etapes.indexOf( prog.getCurrent().getLigneCourrante() ) - 1 );
		this.etapes = new ArrayList<Integer>();
		this.prog.reset();
	}

	/**
	 * Fonction main.
	 */
	public static void main ( String[] a )
	{
		new Controleur();
	}

	public Programme getProgramme ()
	{
		return this.prog;
		
	}
}