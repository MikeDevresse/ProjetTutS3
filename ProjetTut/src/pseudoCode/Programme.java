package pseudoCode;

import main.StringFormateur;

public class Programme
{
	private String[] fichier;
	private Algorithme algo;
	
	
	private String def;
	private boolean debut = false;
	private boolean fin = false;
	private int ligneCourrante = 0;
	
	public Programme ( String[] fichier ) throws AlgorithmeException
	{
		this.fichier = fichier;
		
		while ( !fin )
		{
			LigneSuivante();
		}
	}
	
	public void LigneSuivante() throws AlgorithmeException
	{
		String current = fichier[ligneCourrante++];
		String[] mots = current.split( " " );
		
		boolean ignore = false;
		if ( StringFormateur.enleverEspace(current).equals( "" ));
			ignore = true;
		
		
		if ( !debut && !ignore )
		{
			if ( algo == null )
			{	
				if ( !mots[0].equals( "ALGORITHME" ) )
				{
					throw new AlgorithmeException("Mauvaise structure du fichier");
				}
				else
				{
					algo = new Algorithme( mots[1] );
				}
			}
			
			else if ( mots[0].replaceAll(":","").equals( "constante" ))
			{
				this.def = "constante";
			}
			else if( mots[0].replaceAll( ":", "" ).equals( "variable" ))
			{
				this.def = "variable";
			}
			else if ( mots[0].equals( "DEBUT" ) )
			{
				this.debut = true;
			}
			else
			{
				if ( this.def == null || this.def == "" )
				{
					return;
				}
				String[] vars = current.split( ":" );
				if ( vars.length != 2 )
				{
					return;
				}
				String type = StringFormateur.enleverEspace( vars[1] );
				for ( String s : vars[0].split( "," ))
				{
					
				}
			}
		}
		else if ( debut && !ignore )
		{
			if ( mots[0].equals( "FIN" ))
				this.fin = true;
		}
		
		
		
		
		
		
		
			
			
	}
}
