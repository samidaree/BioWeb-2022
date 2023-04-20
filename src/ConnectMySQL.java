import java.sql.*; 

public class ConnectMySQL {
	
	public static String toString(String requete) {
		StringBuffer sb = new StringBuffer () ; 
		try 
		{
			// étape 1 : charger la classe de driver 
			Class.forName("com.mysql.jdbc.Driver"); 
			//étape 2: Créer l'objet de connexion 
			Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:8889/bddproteine?serverTimezone=UTC", "root", "root"); 
			//étape 3 : créer l'objet statement 
			Statement stmt = conn.createStatement(); 
			ResultSet res = stmt.executeQuery(requete); 
			
			//etape4 : executer la requete 
			while (res.next())
				// En paramètres, les colones/attributs à afficher et leurs types. 
				sb.append(res.getString(1)).append("\n").append(res.getString(2)).append("\n").append(res.getString(3));
			//etape 5: fermer l'objet de connexion 
			conn.close(); 
		}
		catch(Exception e) {
			System.out.println(e); 
		}
		return sb.toString() ; 
	}

	public static void main(String [] args) {
		System.out.println(ConnectMySQL.toString("SELECT S1.seq, S2.seq, S3.seq from proteine S1,proteine S2, proteine S3 where S1.acc_number = 'P99999.2' and S2.acc_number = 'P01308.1' and S3.acc_number='P06306'")); 
	}


}