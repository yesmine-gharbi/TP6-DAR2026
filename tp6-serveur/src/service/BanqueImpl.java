package service;

import corbaBanque.*;
import java.util.ArrayList;
import java.util.List;

public class BanqueImpl extends IBanqueRemotePOA {

    private List<Compte> comptes = new ArrayList<Compte>();

    @Override
    public void creerCompte(Compte cpte) {
        comptes.add(cpte);
        System.out.println("Compte créé : " + cpte.code);
    }

    @Override
    public void verser(float mt, int code) {
        for (Compte c : comptes) {
            if (c.code == code) {
                c.solde = c.solde + mt;
                System.out.println("Versement effectué");
                return;
            }
        }
        System.out.println("Compte introuvable");
    }

    @Override
    public void retirer(float mt, int code) {
        for (Compte c : comptes) {
            if (c.code == code) {
                if (c.solde >= mt) {
                    c.solde = c.solde - mt;
                    System.out.println("Retrait effectué");
                } else {
                    System.out.println("Solde insuffisant");
                }
                return;
            }
        }
        System.out.println("Compte introuvable");
    }

    @Override
    public Compte getCompte(int code) {
        for (Compte c : comptes) {
            if (c.code == code) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Compte[] getComptes() {
        return comptes.toArray(new Compte[0]);
    }

    @Override
    public double conversion(float mt) {
        return mt * 3.4;
    }
}
