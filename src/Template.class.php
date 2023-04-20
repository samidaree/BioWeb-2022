<?php


// Définition de la classe Template.
class Template
{
	// Chaîne de caractères qui contiendra le modèle HTML.
	private $template;

	// *****************************************************************
	// Constructeur
	// -----------------------------------------------------------------
	// Le constructeur s'occupe du chargement du modèle HTML.
	// Si un problème survient lors du chargement du modèle,
	// alors le script PHP est arrêté et un message d'erreur
	// est affiché.
	// -----------------------------------------------------------------
	// Entrée : $file = Fichier HTML contenant le modèle.
	// *****************************************************************
	function __construct($file)
	{
		// Chargement du modèle dans la variable membre.
		$this->template = file_get_contents($file);
		
		// Si une erreur est survenue lors du chargement,
		// on stoppe le script avec un message d'erreur.
		if ($this->template === false)
			exit("Une erreur est survenue lors du chargement du fichier <i>\"".$file."\"</i>.");
	}
	
	// *****************************************************************
	// Replace()
	// -----------------------------------------------------------------
	// Remplace une chaîne de caractères dans le modèle.
	// -----------------------------------------------------------------
	// Entrées : $label = Chaîne à remplacer dans le modèle.
	//           $value = Chaîne de remplacement.
	// *****************************************************************
	function Replace($label, $value)
	{
		$this->template = str_replace($label, $value, $this->template);
	}
	
	// *****************************************************************
	// Display()
	// -----------------------------------------------------------------
	// Affiche le modèle dans le navigateur avec retour chariot 
	function DisplayBr()
	{
		 echo nl2br($this->template);
	}
	// Affiche le modèle dans le navigateur sans retour chariot 
	function Display(){
		echo ($this->template); 
	}
}
