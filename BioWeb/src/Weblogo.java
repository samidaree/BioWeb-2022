
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.MalformedURLException;
import java.io.PrintStream;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JToggleButton;
class Weblogo {
	private String url = "http://weblogo.threeplusone.com/create.cgi";

	public void getWbAdressAndImage(String listeSeq) {
		try {
//////////////Permettre de lire le fichier en string pour les paramètres//////////////
			


	        System.out.println("Contenu du fichier :\n" +listeSeq);
	        String encodedSequence = URLEncoder.encode(listeSeq, StandardCharsets.UTF_8);
			
	          
//////////////Connexion à l'URL//////////////
	          URL logo = new URL(url);	           
	          // Ouvrir la connexion
	          URLConnection co = logo.openConnection();
	          co.setDoOutput(true);           
	          // Permettre de régler les paramètres du site
	          PrintStream o = new PrintStream(co.getOutputStream());           
	          // Paramètre permettant de mettre les uploads des séquences
	          o.print("sequences=" + encodedSequence);
		  o.print("&");
		  // Paramètre permettant de mettre le titre du logo
		  o.print("logo_title=" + "");
		  o.print("&");
		  // Paramètre permettant le choix de sortie de l'image ("png_print", "png", "jpeg", "eps, "pdf", "svg") 
		  o.print("format=" + "png_print");
		  o.print("&");
		  // Paramètre permettant de chosir le type de séquences (protéiques ou nucléiques). Choisir "alphabet_auto" pour que les séquences soient reconnu automatiquement
		  o.print("alphabet=" + "alphabet_auto");
		  o.print("&");
		  // Paramètre permettant de choisir la taille du logo. Choisir entre "small", "medium" et "large"
		  o.print("stack_width=" + "medium");
		  o.print("&");
		  o.print("stacks_per_line=" + "40");
		  o.print("&");
		  // Paramètre permettant de voir ou non les cases en minuscules. Choisir "off"
		  o.print("ignore_lower_case=" + "off");
		  o.print("&");
		  // Paramètre permettant de choisir le type d'unité. Choisir "bits"
		  o.print("unit_name=" + "bits");
		  o.print("&");
		  // Paramètre permettant de chosir le position du premier nombre
		  o.print("first_index=" + "1");
		  o.print("&");
		  // Paramètre permettant de choisir le commencement et fin du logo
		  o.print("logo_start=" + "");
		  o.print("&");
		  o.print("logo_end=" + "");
		  o.print("&");
		  // Paramètre Figure Label
		  o.print("logo_label=" + "");
		  o.print("&");
		  // Paramètre permettant de mettre à l'échelle les largeurs de pile
		  o.print("scale_width=" + "on");
		  o.print("&");
		  // Paramètre permettant de chosir la compôsition. Choisir "comp_auto"
		  o.print("composition=" + "comp_auto");
		  o.print("&");
		  // Paramètre permettant d'activer les barres d'erreur. Choisir "on"
		  o.print("show_errorbars=" + "on");
		  o.print("&");
		  // Paramètre permettant d'afficher les étiquettes de fin de séquence. Choisir "off"
		  
		  o.print("show_ends=" + "off");
		  o.print("&");
		  // Paramètre permettant d'activer une version avec des petis caractères
		  o.print("show_fineprint=" + "on");
		  o.print("&");
		  // Paramètre permettant de régler xaxis et yaxis.
		  o.print("show_xaxis=" + "on");
		  o.print("&");
	  	  o.print("xaxis_label=" + "auto");
		  o.print("&");
		  o.print("show_yaxis=" + "on");
		  o.print("&");
		  o.print("yaxis_label=" + "auto");
		  o.print("&");
		  o.print("yaxis_scale=" + "auto");
		  o.print("&");
		  o.print("yaxis_tic_interval" + "1.0");
		  o.print("&");			
		  // Paramètre permettant de choisir la couleur du schéma. Choisir "color_auto"
		  o.print("color_scheme=" + "color_auto");
		  o.print("&");				
		  // Paramètre color key
		  o.print("show_color_key=" + "on");
		  o.print("&");				
		  // Paramètre permettant d'ajuster la couleur des symboles
		  o.print("symbols0=" + "");
		  o.print("&");
		  o.print("color0=" + "");
		  o.print("&");
		  o.print("symbols1=" + "");
		  o.print("&");
		  o.print("color1=" + "");
		  o.print("&");
		  o.print("symbols2=" + "");
		  o.print("&");
		  o.print("color2=" + "");
		  o.print("&");
		  o.print("symbols3=" + "");
		  o.print("&");
		  o.print("color3=" + "");
		  o.print("&");
		  o.print("symbols4=" + "");
		  o.print("&");
		  o.print("color4=" + "");
		  o.print("&");
		  // Paramètre permettant de créer le logo
		  o.print("cmd_create=" + "Create WebLogo");
	      o.close();
//////////////Utilisation InputStream sur co et transformation en byte[] pour pouvoir afficher l'image facilement//////////////
	            
	          byte[] imageLogo;
	          InputStream is = co.getInputStream();
	          imageLogo = is.readAllBytes();
	          System.out.println(imageLogo);
	          ByteArrayInputStream bais = new ByteArrayInputStream(imageLogo);
		  BufferedImage bImage = ImageIO.read(bais);
		  ImageIO.write(bImage, "png", new File("Weblogo/sequence.png"));
		  System.out.println("image cr��e");
         
		} catch (MalformedURLException e1) {
	          	System.out.println("URL illégale : " + e1); // Gère les exceptions et les erreurs
		  } catch (IOException e2) {
	         	  System.out.println("IOException : " + e2); // Gère les exceptions et les erreurs
		    } catch (Exception e3) {
	         	  System.out.println("Erreur : " + e3); // Gère les exceptions et les erreurs
	              }
	}	
	public void getJFrame() {   
////////////Création de JFrame pour afficher le logo////////////
	      JToggleButton jButton = new JToggleButton(); // affiche du bouton 
	   	  JFrame frame = new JFrame("SEQUENCES_LOGO");
	   	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   	  frame.setSize(800,840);
	   	  frame.setLocationRelativeTo(null);
	   	  frame.setVisible(true); 
                    
		  jButton.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
                  jButton.setText("Retour");	   	       
	   	  // URL de l'image	   	    
	   	  String lienFichier = "Weblogo/sequence.png";
	   	  ImageIcon icone = new ImageIcon(new ImageIcon(lienFichier).getImage().getScaledInstance(800,800,Image.SCALE_DEFAULT));
	   	  // Création de JLable avec un alignement gauche
	   	  JLabel jlabel = new JLabel(icone, JLabel.CENTER);
	   	  jlabel.setSize(300, 120);
	   	  // ajouter les deux JLabel a JFrame
	   	  frame.getContentPane().add(jlabel, BorderLayout.CENTER);
	   	  frame.validate();
	}
}