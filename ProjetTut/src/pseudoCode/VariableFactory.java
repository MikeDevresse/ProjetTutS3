package pseudoCode;

import main.StringFormateur;

public class VariableFactory {

	/**
	 * Parse une expression et crée la variable associée
	 * 
	 * @param expression pseudo-code
	 * @param constante vrai si constante
	 * @return variable
	 */
	public static Variable<?> createVariable(String expression, boolean constante) {

		// nettoyage de l'expression
		expression = StringFormateur.enleverEspace(expression);
		expression = expression.replace("d'", "de ");

		String varName = expression.split(":")[0];
		String varType = expression.split(":")[1];
		varType = varType.toLowerCase();

		// tableau de variables
		String tabDelimiter = StringFormateur.enleverEspace("tableau de ");
		if (varType.indexOf(tabDelimiter) != -1) {
			String tabType = varType.substring(varType.indexOf(tabDelimiter) + tabDelimiter.length());

			switch (tabType) {

			case "entier":
				return new Variable<Integer[]>(varName, varType, constante);
			case "booléen":
				return new Variable<Boolean[]>(varName, varType, constante);
			case "chaîne de caractère":
				return new Variable<String[]>(varName, varType, constante);
			case "réel":
				return new Variable<Double[]>(varName, varType, constante);
			case "caractère":
				return new Variable<Character[]>(varName, varType, constante);

			default:
				return null;
			}
		}

		// variables simples
		switch (varType) {

		case "entier":
			return new Variable<Integer>(varName, varType, constante);
		case "booléen":
			return new Variable<Boolean>(varName, varType, constante);
		case "chaînedecaractère":
			return new Variable<String>(varName, varType, constante);
		case "réel":
			return new Variable<Double>(varName, varType, constante);
		case "caractère":
			return new Variable<Character>(varName, varType, constante);

		default:
			return null;
		}
	}

}
