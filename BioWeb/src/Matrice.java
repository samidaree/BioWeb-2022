import java.util.*;
public class Matrice {
	
	private static int match;				//Parametres
	private static int mismatch;			//de
	private static int gap;					//calcul
	
	private StringBuffer seq_haut;					//Sequences ?
	private StringBuffer seq_gauche;				//aligner
	
	private ArrayList<ArrayList<Integer>> tableau;			//Tableau valeurs et sequences
	private ArrayList<ArrayList<Fleches>> tableauFleches;			//Tableau de mise en memoire mouvements
	//Constructeur
	public Matrice(StringBuffer seq1,StringBuffer seq2) {
		this.seq_haut = seq1;
		this.seq_gauche = seq2;
		this.tableau = new ArrayList<ArrayList<Integer>>((seq2.length()+2));
				for(int i=0;i<(seq2.length()+2);i++) {
					tableau.add(new ArrayList<Integer>(seq1.length()+2));
				}
		this.tableauFleches = new ArrayList<ArrayList<Fleches>>(seq2.length()+1);
		for(int i=0;i<(seq2.length()+1);i++) {
			tableauFleches.add(new ArrayList<Fleches>(seq1.length()+1));
		}
		
		init();
		calcul();
	}
	
	//Remplissage sequences et valeurs gap
	public void init() {
		this.tableau.get(0).add((int)'-');
		this.tableau.get(0).add((int)'-');
		this.tableau.get(1).add((int)'-');
		for (int i=0;i<(seq_haut.length());i++) {
			this.tableau.get(0).add((int)seq_haut.charAt(i));
		}
		for (int i=0;i<(seq_haut.length())+1;i++) {
			this.tableau.get(1).add( i*gap);
		}
		for (int i=0;i<(seq_gauche.length());i++) {
			this.tableau.get(i+2).add((int)seq_gauche.charAt(i));
		}
		for (int i=0;i<(seq_gauche.length())+1;i++) {
			this.tableau.get(i+1).add( i*gap);
		}
		
		this.tableauFleches.get(0).add(new Fleches(0,0,1,1));
		for (int i=1;i<seq_haut.length()+1;i++) {
			this.tableauFleches.get(0).add(new Fleches(0,1,0,1));
		}
		for (int i=1;i<seq_gauche.length()+1;i++) {
			this.tableauFleches.get(i).add(new Fleches(1,0,0,1));
		}
		
	}
	
	//Remplissage score et mouvements
	public void calcul() {
		int haut,gauche,diagonale,max;
		for(int i=0;i<seq_gauche.length();i++) {
			for(int j=0;j<seq_haut.length();j++) {
				haut=tableau.get( i+1).get(j+2)+gap;
				gauche=tableau.get(i+2).get(j+1)+gap;
				if(tableau.get(i+2).get(0)==tableau.get(0).get(j+2)) {
					diagonale=tableau.get(i+1).get(j+1)+match;
				}else {
					diagonale=tableau.get(i+1).get(j+1)+mismatch;
				}
				max=Math.max(Math.max(haut,gauche),diagonale);
				this.tableauFleches.get(i+1).add( j+1, new Fleches(haut,gauche,diagonale,max));
				this.tableau.get(i+2).add( j+2,max);
			}
		}
	}
	
	//Gets
	public StringBuffer getHaut() {
		return seq_haut;
	}
	
	public StringBuffer getGauche() {
		return seq_gauche;
	}
	
	public ArrayList<ArrayList<Integer>> getTableau() {
		return tableau;
	}
	
	public ArrayList<ArrayList<Fleches>> getFleches() {
		return tableauFleches;
	}
	
	// Affichage matrice
	public void affiche() {
		System.out.println(tableauToString());
	}
	
	public String tableauToString() {
		StringBuffer s = new StringBuffer();
		for(int i=0;i<tableau.size();i++) {
			for(int j=0;j<tableau.get(0).size();j++) {
				if(i==0 || j==0) {
				s.append((char)(int)tableau.get(i).get(j) + "\t");
				}else {
					s.append(tableau.get(i).get(j) + "\t");
				}
			}
			s.append("\n");
		}
		return s.toString();
	}
	public void afficheFleches() {
		System.out.println(flechesToString());
	}
	
	public String flechesToString() {
		StringBuffer s = new StringBuffer();
		for(int i=0;i<tableauFleches.size();i++) {
			for(int j=0;j<tableauFleches.get(0).size();j++) {
				s.append("("+tableauFleches.get(i).get(j).getFleches()+")" + "\t");
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	//Set Match, Mismatch, Gap////////////
	public static void setMatch(int val) {
		match=val;
	}
	public static void setMismatch(int val) {
		mismatch=val;
	}
	public static void setGap(int val) {
		gap=val;
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
class Fleches {
	
	//El?ment pour mise en m?moire du mouvement qui donne le meilleur score
	private ArrayList<String> fleches;
	
	//Constructeur
	public Fleches(int haut,int gauche,int diagonale,int max) {
		this.fleches = new ArrayList<String>();
		if(haut==max) {
			this.fleches.add("haut");
		}
		if(gauche==max) {
			this.fleches.add("gauche");
		}
		if(diagonale==max) {
			this.fleches.add("diagonale");
		}
	}
	//Acces mouvements
	public ArrayList<String> getFleches() {
		return fleches;
	}
	
}