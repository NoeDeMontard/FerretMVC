/**
 * Classe de lien entre le lancement de l'application Ferret par l'utilisateur et le traitement effectif par le modèle
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class RunCTRL {
   // private RunGUI run; // la classe GUI lançant l'exécution
    //private Traitement1KG traitement; // la classe globale de traitement
    private PopulationGUI pg;
    private RegionInteretGUI rig;
    private SettingsGUI sg;
    public RunCTRL(PopulationGUI pg,RegionInteretGUI rig,SettingsGUI sg) {
        //this.run = run;
       // run.RunListener(this);
       // this.traitement = traitement;
       this.pg=pg;
       this.rig=rig;
       this.sg=sg;
    }
    public boolean VerificationParam(){//la methode pour verifier les paramatrs
         
    }
    public void ExecutionTraitement1KG(){//executer traitement1kg
        
    }
