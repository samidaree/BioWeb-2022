import sys

import requests

import mysql.connector


#Récupération de l'identifiant saisie par l'utilisateur sur l'interface web et envoyé sur python par ligne de commande php 
accesnb = sys.argv[1]  


#acces au .fasta via l'url et le package requests

url = 'https://www.uniprot.org/uniprot/' + accesnb + '.fasta'

r = requests.get(url, auth=('user', 'pass'))

seqfasta = r.text

#Connexion a la BD

mydb = mysql.connector.connect(

    host="localhost",
    port = 8889, 

    user="root",

    password="root",

    database="bddproteine" #A CHANGER

)


mycursor = mydb.cursor(buffered=True)


seqsplit = seqfasta.split('\n', 1)

fasta = seqsplit[0]

seq = seqsplit[1]


seq2 = ""

for c in seq:

    if c in "AZERTYUIOPMLKJHGFDSQWXCVBN":

        seq2 += c

#Initialisation de la taille de la Sequence
seqlenght = len(seq2)

#Recuperation du nombre de séquences deja présentes dans la base de données pour connaitre le numero id de la prochaine séquence à insérer 
mycursor.execute("select count(*) from proteine")
id = mycursor.fetchall()[0][0]+1

requete = "INSERT INTO `proteine` (`ID`, `ACC_NUMBER`, `SEQ_LENGTH`, `SEQ`, `SEQ_FICHIER`) " \
 "VALUES ("+str(id)+", '"+accesnb+"', '"+str(seqlenght)+"', '"+seq2+"', '"+fasta+seq2+"') "

print(requete) 

mycursor.execute(requete) #Requete
