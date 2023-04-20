import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
public class Fasta1 {
	
	private String nomFichier;
	private ArrayList<Sequence> sequences;
	
	public Fasta1 (String nom,File fichier){
		nomFichier=nom;
		sequences= new ArrayList<Sequence>();
		try {
			Scanner lecteur = new Scanner(fichier);
			while (lecteur.hasNextLine()) {
				String ligne = lecteur.nextLine();
				if(!(ligne.equals(""))) {
					if(ligne.charAt(0)=='>') {
						if(Sequence.getNature().equals("ADN")) {
							sequences.add(new ADN(ligne));
						}else if (Sequence.getNature().equals("PROTEINE")) {
							sequences.add(new Proteine(ligne));
						}
					}else {
						if(Sequence.getNature().equals("ADN")) {
							ADN seq=(ADN)sequences.get(sequences.size()-1);
							seq.setSeq(seq.getSeq().append(ligne));
							if(!(seq.estValide())) {
								sequences.remove(sequences.size()-1);
							}
						}else if(Sequence.getNature().equals("PROTEINE")) {
							Proteine seq = (Proteine)sequences.get(sequences.size()-1);
							seq.setSeq(seq.getSeq().append(ligne));
							if(!(seq.estValide())) {
								sequences.remove(sequences.size()-1);
							}
						}
						
					}
					
				}
			}
			lecteur.close();
		}catch(FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
		
	}
	
	
	public Fasta1(String sequences) {
		
		this.sequences= new ArrayList<Sequence>();
		Scanner lecteur = new Scanner(sequences);
		while (lecteur.hasNextLine()) {
			String ligne = lecteur.nextLine();
			if(!(ligne.equals(""))) {
				if(ligne.charAt(0)=='>') {
					if(Sequence.getNature().equals("ADN")) {
						this.sequences.add(new ADN(ligne));
					}else if (Sequence.getNature().equals("PROTEINE")) {
						this.sequences.add(new Proteine(ligne));
					}
				}else {
					if(Sequence.getNature().equals("ADN")) {
						ADN seq=(ADN)this.sequences.get(this.sequences.size()-1);
						seq.getSeq().append(ligne);
						if(!(seq.estValide())) {
							this.sequences.remove(this.sequences.size()-1);
						}
					}else if(Sequence.getNature().equals("PROTEINE")) {
						Proteine seq = (Proteine)this.sequences.get(this.sequences.size()-1);
						seq.getSeq().append(ligne);
						if(!(seq.estValide())) {
							this.sequences.remove(this.sequences.size()-1);
						}
					}
				}
			}
		}
		lecteur.close();
		nomFichier=this.sequences.get(0).getId()+".fasta";
		
	}
	
	public String getNom() {
		return nomFichier;
	}
	
	public ArrayList<Sequence> getSequences(){
		return sequences;
	}
	
	public static void creerAlnfa(String nom,String sequences) {
		try {
			FileWriter createur = new FileWriter(nom.substring(0,nom.indexOf(".fasta"))+".aln-fa");
			createur.write(sequences);
			createur.close();
			System.out.println("Fichier cr??");
		}catch(Exception e) {
			System.out.println("Erreur de cr?ation");
		}
	}
}

