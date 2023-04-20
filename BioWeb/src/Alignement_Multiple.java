import java.util.ArrayList;
public class Alignement_Multiple {
	
	private Fasta1 fasta;
	private ArrayList<Sequence> sequences;
	private ArrayList<AlignementG> alignements;
	
	//Constructeurs
	public Alignement_Multiple(Fasta1 f) {
		fasta=f;											//Extrait les sequences (? mettre dans le main)
		sequences = f.getSequences();							//du fichier fasta
		
	}
	
	//Alignement des sequences
	public void calcul() {
		alignements= new ArrayList<AlignementG>();
		ArrayList<Integer> coo_i = new ArrayList<Integer>();
		ArrayList<Integer> coo_j = new ArrayList<Integer>();
		for(int i=0;i<sequences.size()-1;i++) {
			for(int j=i+1;j<sequences.size();j++) {
				AlignementG al = new AlignementG(sequences.get(i),sequences.get(j));
				
				int c =0;
				while(c<alignements.size()) {
					
					if(alignements.get(c).getChemins().get(0).getScore()<al.getChemins().get(0).getScore()) {
						break;
					}
					c++;
				}
				alignements.add(c,al);
				coo_i.add(c,i);
				coo_j.add(c,j);
			}
		}
		
		for(int i=0;i<this.sequences.size();i++) {
			for(int j=0;j<alignements.size();j++) {
				
				if(i==coo_i.get(j)) {
					
					this.sequences.get(i).setSeq(alignements.get(j).seq_SupToString());
					break;
				}
				if(i==coo_j.get(j)) {
					this.sequences.get(i).setSeq(alignements.get(j).seq_InfToString());
					
					break;
				}
			}
		}
		
		for(int i=0;i<sequences.size()-1;i++) {
			
			AlignementG al = new AlignementG(sequences.get(i),sequences.get(i+1));
			
			for(int j=0;j<sequences.get(i).getSeq().length();j++) {
				if(sequences.get(i).getSeq().charAt(j)!=al.getSeq_sup().getSeq().charAt(j)) {
					for(int c=0;c<=i;c++) {
						sequences.get(c).setSeq(sequences.get(c).getSeq().insert(j,"-"));
						
					}
				}
			}
			sequences.get(i+1).setSeq(al.getSeq_inf().getSeq());
		}
		
		
		
	}
	
	public void afficheSeqMult() {
		System.out.println(this.toString());
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		for(int i=0; i<sequences.size();i++) {
			s.append(sequences.get(i).getSeq()+"\n");
		}
		return s.toString();
	}
	
	public void exporterAlnfa() {
		Fasta1.creerAlnfa(fasta.getNom(), Sequence.listToString(sequences));
	}
	
}
