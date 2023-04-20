<?php 

// On inclut les paramètres de connexions 
require "AuthentificationSettings.php";

// Tentative de connexion à la BDD 
$mysqli = new mysqli('localhost:8889', $user, $password, $database, $port);

if ($mysqli->connect_error) {
    die('Connect Error (' . $mysqli->connect_errno . ') '. $mysqli->connect_error);
}


$requete1 = mysqli_query($mysqli,"SELECT ACC_NUMBER FROM proteine" );

$select ="<optgroup label = 'Protéines'>"; 
// On parcourt les ACC_NUMBER (identifiant comme P99999) de tous les tuples de la table proteine 
foreach ($requete1 as $res){
    // On recupère dynamiquement l'ACC_NUMBER du tuple courant sous forme de liste html 
    $select .= "<option value ='".$res["ACC_NUMBER"]."'>".$res["ACC_NUMBER"]."</option>\n"; 
}
$select .='</optgroup>'; 

$select.="<optgroup label = 'ADN'>"; 

$requete2 = mysqli_query($mysqli,"SELECT CCDS_ID FROM seq_nucleotidiques");

// On parcourt les CCDS_ID de tous les tuples de la table seq_nucleotidiques 
foreach ($requete2 as $res){
    $select .= "<option value ='".$res["CCDS_ID"]."'>".$res["CCDS_ID"]."</option>\n"; 
}
$select.='</optgroup>'; 

?>