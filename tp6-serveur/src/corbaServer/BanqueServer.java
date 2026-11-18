package corbaServer;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;
import service.BanqueImpl;

public class BanqueServer {

    public static void main(String[] args) {

        try {
            // 1. Initialiser l'ORB
            ORB orb = ORB.init(args, null);

            // 2. Recuperer le RootPOA
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

            // 3. Activer le POAManager
            rootpoa.the_POAManager().activate();

            // 4. Creer le servant
            BanqueImpl banque = new BanqueImpl();

            // 5. Obtenir la reference CORBA
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(banque);

            IBanqueRemote href = IBanqueRemoteHelper.narrow(ref);

            // 6. Configurer l'annuaire JNDI
            Hashtable<String, String> env = new Hashtable<String, String>();

            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.cosnaming.CNCtxFactory");

            env.put(Context.PROVIDER_URL,
                    "iiop://localhost:900");

            Context ctx = new InitialContext(env);

            // 7. Enregistrer le service dans l'annuaire
            ctx.rebind("BANQUE", href);

            System.out.println("Serveur Banque pret !");

            // 8. Attendre les requetes des clients
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}