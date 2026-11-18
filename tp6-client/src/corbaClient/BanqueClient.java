package corbaClient;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

import corbaBanque.*;

public class BanqueClient {

    public static void main(String[] args) {

        try {
            // 1. Recuperer les proprietes JNDI
            Hashtable<String, String> env = new Hashtable<String, String>();

            env.put(
                    Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");

            env.put(
                    Context.PROVIDER_URL,"iiop://localhost:900");

            Context ctx = new InitialContext(env);

            // 2. Recuperer la reference distante
            org.omg.CORBA.Object ref = (org.omg.CORBA.Object)
                    ctx.lookup("BANQUE");

            // 3. Convertir la reference en IBanqueRemote
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(ref);

            System.out.println("Connexion au serveur reussie !");

            // 4. Creer un compte
            Compte c = new Compte(1, 1000);
            banque.creerCompte(c);

            System.out.println("Compte cree !");

            // 5. Verser de l'argent
            banque.verser(500, 1);
            System.out.println("Versement effectue !");

            // 6. Retirer de l'argent
            banque.retirer(200, 1);
            System.out.println("Retrait effectue !");

            // 7. Consulter un compte
            Compte compte = banque.getCompte(1);

            System.out.println("Code : " + compte.code);
            System.out.println("Solde : " + compte.solde);

            // 8. Consulter tous les comptes
            Compte[] comptes = banque.getComptes();

            System.out.println("Nombre de comptes : " + comptes.length);

            // 9. Conversion
            double resultat = banque.conversion(100);

            System.out.println("Resultat conversion : " + resultat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
