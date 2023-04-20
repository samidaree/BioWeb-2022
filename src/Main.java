public class Main {
	public static void main(String[] args) {
	
		String alphabet =""; 
		if (args[1].equals("Alignement Global"))
		{	
			System.out.println(" les séquences sont : \n" + args[0]); 
			if (args[5].equals("adn"))
			 	alphabet = "ACTGU";
		 
			else 
			 	alphabet = "ACDEFGHIPVLMYWKRQNST";
			
				//Slicing pour séparer les 2 séquences 
				int i = 1; 
				while ( args[0].charAt(i)!='>'){
					i++; 
				}
				StringBuffer seq1_fasta = new StringBuffer(args[0].substring(0,i-1));
				StringBuffer seq2_fasta = new StringBuffer(args[0].substring(i)); 
			

				// Slicing pour retirer le fasta de chaque séquence 
				int cut = 0; 
				for(int j=0; j<seq1_fasta.length(); j++){
					if (alphabet.indexOf(seq1_fasta.charAt(j))==-1 && seq1_fasta.charAt(j)!='\r' && seq1_fasta.charAt(j)!='\n' && seq1_fasta.charAt(j)!=' '){
						cut = j ; 
						//System.out.println(cut+"\t"); 
					}	
				}
				StringBuffer seq1 = new StringBuffer(seq1_fasta.substring(cut+1));

				//System.out.println("La séquence sans fasta est : " + seq1 +"\n"); 


				for(int j=0; j<seq2_fasta.length(); j++){
					if (alphabet.indexOf(seq2_fasta.charAt(j))==-1 && seq2_fasta.charAt(j)!='\r' && seq2_fasta.charAt(j)!='\n' && seq2_fasta.charAt(j)!=' '){
						cut = j ; 
					}	
				}

				StringBuffer seq2 = new StringBuffer(seq2_fasta.substring(cut+1));
				//System.out.println("la taille de seq2 est "+seq2.length()); 

				//System.out.println("La séquence2 sans fasta est : "+seq2+"\n"); 
				for (int k=0; k<seq1.length();k++){
					if (seq1.charAt(k)==' ' || seq1.charAt(k)=='\r' || seq1.charAt(k)=='\n')
						seq1.deleteCharAt(k); 
				}
				for (int k=0; k<seq2.length();k++){
					if (seq2.charAt(k)==' ' || seq2.charAt(k)=='\r' || seq2.charAt(k)=='\n')
						seq2.deleteCharAt(k); 
				}
				//System.out.println("La séquence1 sans fasta et espaces est : "+seq1+"\n"); 

				//System.out.println("La séquence2 sans fasta et espaces est : "+seq2+"\n"); 
				//System.out.println("la taille de seq2 est "+seq2.length()); 
			// Conversion des match mismatch et gap en entier 
			Matrice.setMatch(Integer.parseInt(args[2]));
			Matrice.setMismatch(Integer.parseInt(args[3]));
			Matrice.setGap(Integer.parseInt(args[4]));
			AlignementG al1 = new AlignementG(seq1,seq2); 
			if (args[6].equals("MatriceChecked")){
				al1.getMatrice().affiche(); 
			}
			al1.afficheAlignement();
			System.out.println("\n");  
		
		}
		else if (args[1].equals("Alignement Multiple") || args[1].equals("Weblogo")){
			System.out.println(" les séquences sont : \n" + args[0]); 
		
			if (args[5].equals("adn")){
				Sequence.setNature("ADN");
			}
			else  {
				Sequence.setNature("PROTEINE");
			}
				Matrice.setMatch(Integer.parseInt(args[2]));
				Matrice.setMismatch(Integer.parseInt(args[3]));
				Matrice.setGap(Integer.parseInt(args[4]));
				Fasta1 f=new Fasta1(args[0]);
				System.out.println("\n");	
				Alignement_Multiple alm = new Alignement_Multiple(f);
				alm.calcul();
			
				if (args[1].equals("Alignement Multiple")){
					alm.afficheSeqMult(); 
				}
				else if (args[1].equals("Weblogo")){
					String alignementMultiple = alm.toString(); 
					Weblogo weblogo = new Weblogo();
					weblogo.getWbAdressAndImage(alignementMultiple);
					//weblogo.getJFrame();
				}
			}
			
		}
		
	} 
