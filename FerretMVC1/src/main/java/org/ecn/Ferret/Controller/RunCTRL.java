package org.ecn.Ferret.Controller;

import org.ecn.Ferret.View.RunGUI;
import org.ecn.Ferret.Model.Traitement1KG;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe de lien entre le lancement de l'application Ferret par l'utilisateur et le traitement effectif par le modèle
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class RunCTRL implements ActionListener{
    private RunGUI run; // la classe GUI lançant l'exécution
    private Traitement1KG traitement; // la classe globale de traitement
    
    public RunCTRL(RunGUI run, Traitement1KG traitement) {
        this.run = run;
        run.RunListener(this);
        this.traitement = traitement;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        //TODO: il s'agit de vérifier que les paramètres d'exécution sont OK (pour pouvoir lancer Traitement1KG)
        //TODO: connecter la vue, le contrôleur et le modèle pour lancer l'exécution à partir des infos entrées par l'utilisateur
    }
}