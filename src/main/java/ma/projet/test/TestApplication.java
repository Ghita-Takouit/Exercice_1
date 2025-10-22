package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TestApplication {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private final CategorieService categorieService;
    private final ProduitService produitService;
    private final CommandeService commandeService;
    private final LigneCommandeService ligneCommandeService;

    @Autowired
    public TestApplication(CategorieService categorieService,
                          ProduitService produitService,
                          CommandeService commandeService,
                          LigneCommandeService ligneCommandeService) {
        this.categorieService = categorieService;
        this.produitService = produitService;
        this.commandeService = commandeService;
        this.ligneCommandeService = ligneCommandeService;
    }

    public void runTests() {
        try {
            System.out.println("\n>>> Test 1: Creation des categories");
            Categorie cat1 = new Categorie("CAT001", "Ordinateurs");
            Categorie cat2 = new Categorie("CAT002", "Peripheriques");
            Categorie cat3 = new Categorie("CAT003", "Composants");

            categorieService.create(cat1);
            categorieService.create(cat2);
            categorieService.create(cat3);
            System.out.println("✓ Categories creees avec succes");

            System.out.println("\n>>> Test 2: Creation des produits");
            Produit p1 = new Produit("ES12", 120.0f, cat1);
            Produit p2 = new Produit("ZR85", 100.0f, cat2);
            Produit p3 = new Produit("EE85", 200.0f, cat3);
            Produit p4 = new Produit("PC01", 150.0f, cat1);
            Produit p5 = new Produit("MOU01", 50.0f, cat2);
            Produit p6 = new Produit("RAM01", 80.0f, cat3);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);
            produitService.create(p6);
            System.out.println("✓ Produits crees avec succes");

            System.out.println("\n>>> Test 3: Creation des commandes");
            Commande cmd1 = new Commande(dateFormat.parse("14/03/2013"));
            Commande cmd2 = new Commande(dateFormat.parse("20/05/2013"));
            Commande cmd3 = new Commande(dateFormat.parse("10/06/2013"));

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);
            System.out.println("✓ Commandes creees avec succes");

            System.out.println("\n>>> Test 4: Creation des lignes de commande");
            LigneCommandeProduit lc1 = new LigneCommandeProduit(7, p1, cmd1);
            LigneCommandeProduit lc2 = new LigneCommandeProduit(14, p2, cmd1);
            LigneCommandeProduit lc3 = new LigneCommandeProduit(5, p3, cmd1);
            LigneCommandeProduit lc4 = new LigneCommandeProduit(3, p4, cmd2);
            LigneCommandeProduit lc5 = new LigneCommandeProduit(10, p5, cmd2);
            LigneCommandeProduit lc6 = new LigneCommandeProduit(2, p1, cmd3);

            ligneCommandeService.create(lc1);
            ligneCommandeService.create(lc2);
            ligneCommandeService.create(lc3);
            ligneCommandeService.create(lc4);
            ligneCommandeService.create(lc5);
            ligneCommandeService.create(lc6);
            System.out.println("✓ Lignes de commande creees avec succes");

            System.out.println("\n>>> Test 5: Produits par categorie");
            System.out.println("\nCategorie: " + cat1.getLibelle());
            List<Produit> produitsParCategorie = produitService.findByCategorie(cat1);
            for (Produit p : produitsParCategorie) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
            }

            System.out.println("\n>>> Test 6: Produits commandes entre le 01/03/2013 et 31/05/2013");
            Date dateDebut = dateFormat.parse("01/03/2013");
            Date dateFin = dateFormat.parse("31/05/2013");
            List<Produit> produitsEntreDates = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            for (Produit p : produitsEntreDates) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
            }

            System.out.println("\n>>> Test 7: Details de la commande n°" + cmd1.getId());
            afficherDetailsCommande(cmd1.getId());

            System.out.println("\n>>> Test 8: Produits dont le prix est superieur a 100 DH");
            List<Produit> produitsChers = produitService.findByPrixSuperieurA(100.0f);
            for (Produit p : produitsChers) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH (" + p.getCategorie().getLibelle() + ")");
            }

            System.out.println("\n>>> Test 9: Liste de toutes les commandes");
            List<Commande> commandes = commandeService.findAll();
            for (Commande cmd : commandes) {
                System.out.println("  - Commande n°" + cmd.getId() + " du " + dateFormat.format(cmd.getDate()));
            }

        } catch (ParseException e) {
            System.err.println("Erreur de parsing de date: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'execution des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void afficherDetailsCommande(int commandeId) {
        try {
            Commande commande = commandeService.findById(commandeId);

            if (commande == null) {
                System.out.println("Commande introuvable");
                return;
            }

            System.out.println("Commande : " + commande.getId() + "    Date : " +
                             new SimpleDateFormat("dd MMMM yyyy").format(commande.getDate()));
            System.out.println("Liste des produits :");
            System.out.println(String.format("%-15s %-15s %-10s", "Reference", "Prix", "Quantite"));
            System.out.println("---------------------------------------------");

            List<LigneCommandeProduit> lignes = ligneCommandeService.findByCommande(commandeId);
            for (LigneCommandeProduit ligne : lignes) {
                System.out.println(String.format("%-15s %-15s %-10d",
                    ligne.getProduit().getReference(),
                    ligne.getProduit().getPrix() + " DH",
                    ligne.getQuantite()));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affichage de la commande: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

