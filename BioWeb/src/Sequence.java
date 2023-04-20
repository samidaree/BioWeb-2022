import java.util.ArrayList;
public class Sequence {
	private String id;
	private StringBuffer seq;
	private static String nature;
	
	//Constructeurs
	public Sequence(String id) {
		this.id=id;
		this.seq=new StringBuffer("");
	}
	
	public Sequence(String id, StringBuffer seq) {
		this(id);
		this.seq=seq;
	}
	
	//Gets et Sets
	public String getId() {
		return id;
	}
	public StringBuffer getSeq() {
		return seq;
	}
	public void setSeq(StringBuffer seq) {
		this.seq=seq;
	}
	
	public static String getNature() {
		return nature;
	}
	public static void setNature(String nature) {
		Sequence.nature=nature;
	}
	
	//Affichage
	public String toString() {
		return id+"\n"+seq;
	}
	
	public void affiche() {
		System.out.println(this.toString());
	}
	
	//Retourne une chaine de caractères des id et séquences d'une liste de séquence prise en paramètre
	public static String listToString(ArrayList<Sequence> sequences) {
		StringBuffer s = new StringBuffer();
		for(int i =0; i<sequences.size();i++) {															//
			s.append(sequences.get(i).toString()+"\n");
		}
		return s.toString();
	}
	
	public static void afficheList(ArrayList<Sequence> sequences) {
		System.out.println(Sequence.listToString(sequences));
	}
   
}
class ADN extends Sequence {
	private String alphabet = "ATUCG";
	
	public ADN(String id) {
		super(id);
	}
	
	public ADN(String id,StringBuffer seq) {
		super(id,seq);
	}
	
	public boolean estValide() {
		for(int i=0;i<super.getSeq().length();i++) {
			// Si le caractère d'indice i n'est pas une lettre d'alphabet, la sequence n'est pas valide 
			if(alphabet.indexOf(super.getSeq().charAt(i))==-1) {
				return false;
			}
		}
		return true;
	}
	
	//Gets et Sets
	public String getId() {
		return super.getId();
	}
	public StringBuffer getSeq() {
		return super.getSeq();
	}
	public void setSeq(StringBuffer seq) {
		super.setSeq(seq);
	}
	
}
class Proteine extends Sequence {
	private String alphabet = "ACDEFGHIPVLMYWKRQNST";
	
	public Proteine(String id) {
		super(id); 
	}
	
	public Proteine(String id,StringBuffer seq) {
		super(id,seq); 
	}
	
	public boolean estValide() {
		for(int i=0;i<super.getSeq().length();i++) {
			if(alphabet.indexOf(super.getSeq().charAt(i))==-1) {
				return false;
			}
		}
		return true;
	}
	
	//Gets et Sets
	public String getId() {
		return super.getId();
	}
	public StringBuffer getSeq() {
		return super.getSeq();
	}
	public void setSeq(StringBuffer seq) {
		super.setSeq(seq);
	}

	
	
}