import java.util.ArrayList;
public class AlignementG {
	
	private ArrayList<Chemin> chemins;				//Liste des alignements possibles
	private Sequence seq_haut;						//Sequences
	private Sequence seq_gauche;						//? aligner
	private Matrice matrice;				//Matrice N-W 
	
	// Constructeurs
	public AlignementG(Sequence seq_haut,Sequence seq_gauche) {
		
		this.seq_haut=seq_haut;
		this.seq_gauche=seq_gauche;
		matrice=new Matrice(seq_haut.getSeq(),seq_gauche.getSeq());
		chemins= new ArrayList<Chemin>();
		backtrack(matrice.getGauche().length(),matrice.getHaut().length(),new StringBuffer( (char)(int)matrice.getTableau().get(0).get(matrice.getHaut().length())+1),new StringBuffer( (char)(int)matrice.getTableau().get(matrice.getGauche().length()+1).get(0)));
		for(int c=0;c<chemins.size();c++) {
			chemins.get(c).setScore(matrice.getTableau().get(matrice.getGauche().length()-1+2).get(matrice.getHaut().length()-1+2));
		}
	}
	
	public AlignementG(StringBuffer seq_haut,StringBuffer seq_gauche) {
		this(new Sequence("",seq_haut),new Sequence("",seq_gauche));
	}
	
	//Calcul des alignements
	public void backtrack(int i, int j,StringBuffer seq_sup,StringBuffer seq_inf) {
		ArrayList<String> fleches = matrice.getFleches().get(i).get(j).getFleches();
		int max=0;
		int c=0;
		while(c<fleches.size()) {
			if(fleches.get(c)=="diagonale") {
				max=matrice.getTableau().get(i+2-1).get(j+2-1);
			}
			if(fleches.get(c)=="haut") {
				max=matrice.getTableau().get(i+2-1).get(j);
			}
			if(fleches.get(c)=="gauche") {
				max=matrice.getTableau().get(i).get(j+2-1);
			}
			c++;
		}
		
		for(int c_fleches=0;c_fleches<fleches.size();c_fleches++) {
			if(fleches.get(c_fleches)=="diagonale" && matrice.getTableau().get(i+2-1).get(j+2-1)==max) {
				if(i==0 || j==0) {
					seq_sup.reverse();
					seq_inf.reverse();
					chemins.add(new Chemin(new Sequence(seq_haut.getId(), seq_sup),new Sequence(seq_gauche.getId(),seq_inf)));
					
				}else {
					backtrack(i-1,j-1,seq_sup.append( (char)(int)matrice.getTableau().get(0).get(j+2-1)),seq_inf.append( (char)(int)matrice.getTableau().get(i+2-1).get(0)));
					break;
				}
			}
			else if(fleches.get(c_fleches)=="haut" && matrice.getTableau().get(i+2-1).get(j)==max) {
				backtrack(i-1,j,seq_sup.append( '-'),seq_inf.append( (char)(int)matrice.getTableau().get(i+2-1).get(0)));
				break;
			}
			else if(fleches.get(c_fleches)=="gauche" && matrice.getTableau().get(i).get(j+2-1)==max) {
				backtrack(i,j-1,seq_sup.append( (char)(int)matrice.getTableau().get(0).get(j+2-1)),seq_inf.append('-'));
				break;
			}
				
		}
		
	
	}
	
	// affiche les alignements et leur score 
	public void afficheChemins() {
		System.out.println(cheminsToString());
	}
	
	public String cheminsToString() {
		StringBuffer s = new StringBuffer();
		for(int i=0;i<chemins.size();i++) {
			s.append(chemins.get(i).getSeq_sup()+"\t"+chemins.get(i).getScore()+"\n"+chemins.get(i).getSeq_inf()+"\n");
		}
		return s.toString();
	}
	
	public void afficheAlignement() {
		System.out.println(this.toString());
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(this.seq_SupToString()+"\n"+this.seq_InfToString()+"\n"+this.getScore());
		return s.toString();
	}
	
	public void afficheSup() {
		System.out.println(seq_SupToString());
	}
	public StringBuffer seq_SupToString() {
		return this.getSeq_sup().getSeq();
	}
	public void afficheInf() {
		System.out.println(seq_InfToString());
	}
	public StringBuffer seq_InfToString() {
		return this.getSeq_inf().getSeq();
	}
	
	//Gets
	public Chemin getAlignement(){
		return chemins.get(0);
	}
	
	public Sequence getSeq_sup() {
		return this.getAlignement().getSeq_sup();
	}
	
	public Sequence getSeq_inf() {
		return this.getAlignement().getSeq_inf();
	}
	
	public int getScore() {
		return this.getAlignement().getScore();
	}
	
	public ArrayList<Chemin> getChemins(){
		return chemins;
	}
	
	public Matrice getMatrice() {
		return matrice;
	}
	
	
	
	
}
////////////////////////////////////////////////////////////////////////////////////////
//Alignement possible
class Chemin {
	private Sequence seq_sup;				//Sequences
	private Sequence seq_inf;				//alignees
	private int score;				//Score d alignement
	
	//Constructeur
	public Chemin() {
		this.seq_sup =new Sequence("",new StringBuffer(""));
		this.seq_inf = new Sequence("",new StringBuffer(""));
		this.score = 0;
	}
	
	public Chemin(Sequence seq_sup,Sequence seq_inf) {
		this.seq_sup=seq_sup;
		this.seq_inf=seq_inf;
		this.score=0;
	}
	
	//Lecture
	public Sequence getSeq_sup() {
		return seq_sup;
	}
	
	public Sequence getSeq_inf() {
		return seq_inf;
	}
	
	public int getScore() {
		return score;
	}
	
	//Ecriture
	public void setScore(int s) {
		score=s;
	}
	
	public void setSeq_sup(Sequence s) {
		seq_sup=s;
	}
	
	public void setSeq_inf(Sequence s) {
		seq_inf=s;
	}
}