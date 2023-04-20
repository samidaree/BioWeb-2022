<?php 


require_once "Template.class.php"; 
require "selectID.php"; 

// Fonction pour afficher la page principale avec la liste des séquences de la BDD en dynamique 
function DisplayIndex($listeseq){
   $index = new Template("index.html"); 
   $index-> Replace("IDENTIFIANT", $listeseq); 
   $index->Display();  
}


// Fonction pour afficher l'alignement 
function DisplayAlignement(){
    // Si l'utilisateur a choisi de saisir les séquences par fichier 
    if ($_POST["saisie"]=="fichier"){
        // On s'assure qu'un fichier a bien été importé 
        if ((isset($_FILES["fichierFasta"])===true) &&file_exists($_FILES['fichierFasta']["tmp_name"])) { 
            $f = $_FILES['fichierFasta']; 
            // Verification de la taille du fichier 
            if (($f['error']==0)&&($f['size']<=1000000)){
                $infosfichier = pathinfo($f["name"]); 
                $extension = $infosfichier ["extension"]; 
                $exentions_autorisees = array("fasta","txt", ""); 
                // Verification de l'extension du fichier 
                if (in_array($extension,$exentions_autorisees)){
                    $sequences= escapeshellarg(file_get_contents($f["tmp_name"]));  
                }
                else 
                    echo "L'extension de votre fichier est invalide"; 
            }
            else 
                echo "Votre fichier est trop volumineux"; 
        }
    }
   
    // Si l'utilisateur a choisi de saisir les séquences manuellement 
    elseif ($_POST["saisie"]=="manuelle")  { 
        // Récupérer les séquences pour les utiliser en ligne de commande de facon sécurisée 
        $sequences = escapeshellarg($_POST['EntrerSequence']); 
    }
   else {
       $sequences=""; 
       // Inclusion des paramètres de connexions 
        require "AuthentificationSettings.php";
        // Deuxième connexion à la base de données 
        $mysqli2 = new mysqli('localhost:8889', $user, $password, $database, $port);

        // Cas ou l'utilisateur a choisi d'utiliser des séquences d'ADN 
       if ($_POST["nature"]=="adn"){
            $requete = mysqli_query($mysqli2,"SELECT * FROM seq_nucleotidiques");

            // Parcours l'ensemble des tuples de la table seq_nucleotidiques 
            while($res=mysqli_fetch_array($requete)) {
                // Cas alignement global ADN identifiant 
                if ($_POST["alignement"]=="Alignement Global"){ 
                    // Compare l'attribut CCDS_ID du tuple courant aux identifiants séléctionnées par l'utilisateur dans l'element select html 
                    if ($res["CCDS_ID"]==$_POST["identifiant"][0] || $res["CCDS_ID"]==$_POST["identifiant"][1] ){
                        $arg = escapeshellarg($res["CCDS_ID"]); 
                        shell_exec("javac EnsemblAPI.java"); 
                        $output = shell_exec("java EnsemblAPI $arg"); 
                        $sequences.=$output; 
                    }
                }
                // Cas alignement multiple ADN identifiant 
                else {
                    if ($res["CCDS_ID"]==$_POST["identifiant"][0] || $res["CCDS_ID"]==$_POST["identifiant"][1] || $res["CCDS_ID"]==$_POST["identifiant"][2] ){
                        $arg = escapeshellarg($res["CCDS_ID"]); 
                        shell_exec("javac EnsemblAPI.java"); 
                        $output = shell_exec("java EnsemblAPI $arg"); 
                        $sequences.=$output; 
                    }
                }
            }
        }
        // Cas ou l'utilisateur a choisi de saisir des séquences protéiques dans l'étape 1
       else {
         $requete = mysqli_query($mysqli2,"SELECT * FROM proteine");

         while($res=mysqli_fetch_array($requete)) {
             if ($_POST["alignement"]=="Alignement Global"){
                if ($res["ACC_NUMBER"]==$_POST["identifiant"][0] || $res["ACC_NUMBER"]==$_POST["identifiant"][1]){
                    $sequences.=$res["SEQ_FICHIER"]."\n"; 
                }
             }
             else {
                if ($res["ACC_NUMBER"]==$_POST["identifiant"][0] || $res["ACC_NUMBER"]==$_POST["identifiant"][1] || $res["ACC_NUMBER"]==$_POST["identifiant"][2]){
                    $sequences.=$res["SEQ_FICHIER"]."\n"; 
                }
             }
            
        }
       }
       $sequences = escapeshellarg($sequences); 

   }
   // Par défaut, la case "afficher la matrice de similarité" n'est pas cochée 
   $matrice=escapeshellarg("MatriceUnchecked"); 
   if (isset($_POST["matrice"])){
       // Si la valeur de la checkbox dont l'attribut name est matrice vaut "on", la case a été cochée 
        if ($_POST["matrice"]=="on"){
            $matrice = escapeshellarg("MatriceChecked"); 
        } 
   }
   // Récupérer toutes informations saisies par l'utilisateur nécéssaires à l'alignement et les escapeshelllarg() pour les utiliser de facon sécurisées en ligne de commande 
    $typeAlignement = escapeshellarg($_POST["alignement"]); 
    $match = escapeshellarg($_POST["match"]); 
    $mismatch = escapeshellarg($_POST["mismatch"]); 
    $gap = escapeshellarg($_POST["gap"]) ;
    $nature = escapeshellarg($_POST["nature"]); 
    // Compiler le fichier Main.java 
    shell_exec("javac Main.java"); 
    // Executer la classe Main en passant toutes informations précédement récupérées en arguments de ligne de commande et stocker l'affichage dans la variable $res 
    $res = shell_exec("java Main $sequences $typeAlignement $match $mismatch $gap $nature $matrice"); 
    $alignement = new Template("alignement.html"); 
    
    if ($typeAlignement == "'Weblogo'"){
        // Si l'utilisateur a choisi de faire un Weblogo, on affiche le weblogo comme image 
        $alignement->Replace("(RESULTAT)","<img src ="."Weblogo/sequence.png"." width = 50%>"); 
        $alignement->Display(); 
    }
    else { 
        // Si l'utilisateur a choisi AG ou AM, on remplace la chaine de caractères (RESULTAT) de la page alignement.html par le resultat de l'alignement 
        $alignement->Replace("(RESULTAT)",$res);
        $alignement->DisplayBr(); 
    }
    
}

function ajouterSeq(){
    $id = $_POST["identifiantbdd"]; 
    // Mettre tous les caractères de l'identifiant entré par l'utilisateur en majuscule 
    $id = strtoupper($id); 
    // Execution du script InsertSeqSQL.py en ligne de commande avec l'id saisie par l'utilisateur comme argument 
    $res = exec("/Users/hs/opt/anaconda3/bin/python InsertSeqSQL.py $id"); 
    // Rediriger l'utilisateur vers la page principale, il peut maintenant séléctionner l'id qu'il a saisi précédement dans la liste des séquences de la BDD 
    header("location: callJava.php");
}

if (isset($_POST["soumettreid"])){
    ajouterSeq(); 
}

// Si l'un des champs pour saisir des séquences est rempli et soumit via un bouton de type submit, on affiche l'alignement 
if (isset($_POST['EntrerSequence'])||isset($_FILES['fichierFasta']) || isset($_POST["identifiant"])){
    // On n'affiche pas l'alignement si l'utilisateur a cliqué sur le bouton submit pour rentrer une séquence dans la BDD 
    if (isset($_POST["soumettreid"])==false)
        DisplayAlignement(); 
 
}

// Si aucun champ du formulaire est rempli et soumit via un bouton de type submit, on affiche la page principale de l'interface web
else {
    DisplayIndex($select);   
}
