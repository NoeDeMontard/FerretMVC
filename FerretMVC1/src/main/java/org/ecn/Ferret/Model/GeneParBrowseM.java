package org.ecn.Ferret.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ecn.Ferret.View.RegionInteretGUI; // TODO : faire la récupération de isGeneNames par le controleur (via RegionInteretGUI.getGeneNameRadioButton().isSelected())
/**
 * Classe représentant une entrée de gène sous forme "browse"
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG & Noe DE MONTARD
*/

public class GeneParBrowseM extends GeneM {

    private String adresse;

    //TODO: écrire une fonction qui enregistre les données sous formes de GeneParIDM ou GeneParNomM à partir du fichier donné par l'attribut adresse
    // NOTE PERSO : voir ligne 1685 de l'ancien GUI.java, ligne 1920 de l'"ancien" GUI_Formated.java, ou le commentaire suivant (ligne 266 de RegionInteretGUI.java) :
    /*
    "<html>You can load a file in any of the following formats for either gene names or gene IDs: <br> "
    + " - a comma-delimited .csv file (example: gene.csv containing CCR5, HCP5) <br>"
    + " - a tab-delimited .tab or .tsv file (example: gene.tab containing CCR5 &nbsp&nbsp&nbsp&nbsp HCP5) <br>"
    + " - a space-delimited .txt file (example: gene.txt containing CCR5 HCP5)"
    + "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>"
     */
    // je préfererais utilier un découpage selon les .  que récupérer les 4 dernières lettre pour trouver le format.
    private String getDelimiter() {
        //this.adresse.toLowerCase().endsWith(".csv"); // toLowerCase to make the extension check case independant
        int i = this.adresse.lastIndexOf('.');
        String extension = adresse.substring(i + 1).toLowerCase();
        String delimiter =   null;
        switch (extension) {
            case "csv":
                // a comma-delimited .csv file (example: gene.csv containing CCR5, HCP5)
                // A carriage return can also be used as a delimiter for all file types.
                delimiter = ",";
                break;
            case "tab": //tab et tsv sont le même format
            case "tsv":
                // a tab-delimited .tab or .tsv file (example: gene.tab containing CCR5 &nbsp&nbsp&nbsp&nbsp HCP5) // Note : vérifier que le &nbsp représente une tabulation
                // A carriage return can also be used as a delimiter for all file types.
                delimiter = "\\t";
                break;
            case "txt":
                // a space-delimited .txt file (example: gene.txt containing CCR5 HCP5)
                // A carriage return can also be used as a delimiter for all file types.
                delimiter = " ";
                break;
            default:
                // TODO : erreur
                break;
        }
        return delimiter;
    }

    private String getInvalidRegex() {
        boolean geneNameInputted;
        geneNameInputted = isGeneNames();
        String invalidRegex;
        if (geneNameInputted) {
            invalidRegex = ".*[^a-zA-Z0-9\\-].*"; // This is everything except letters and numbers, including underscore
        } else {
            invalidRegex = ".*\\D.*"; // This is everything except numbers
        }
        return invalidRegex;
    }
    private boolean isGeneNames(){
        return RegionInteretGUI.getGeneNameRadioButton().isSelected(); // TODO résoudre l'erreur : récupérer correctement le type de données
    }
    /**
     * Récupère les gènes du fichier et les renvoi dans un tableau de listes de
     * GeneParIDM et GeneParNomM, respectivement.
     *
     * @return [List listeID, List listeNom] tabeau des listes de gènes par type
     */
    public List getGeneIDNOM() { // Liste listeID ou listeNom]
        ArrayList<String> geneListArrayList = new ArrayList<>();
        String delimiter = this.getDelimiter();
        String invalidRegex = getInvalidRegex();
        boolean invalidCharacter = false;
        if ( delimiter != null ) {
            try {
                BufferedReader file = new BufferedReader(new FileReader(adresse));
                String line;
                line = file.readLine();
                String[] genes;
                while (line != null) {
                    genes = line.split(delimiter);
                    for (String gene : genes) {
                        gene = gene.replace(" ", "").toUpperCase();// remove spaces
                        if ( gene.matches(invalidRegex) ) { // identify invalid characters
                            invalidCharacter = true;
                            break;
                        }
                        if (gene.length() > 0) {
                            geneListArrayList.add(gene);
                        }
                    }
                    line = file.readLine();
                }
                file.close();
            } catch (FileNotFoundException e) {
                // TODO FileNotFoundException
                // FileNotFoundException - if the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
            } catch (IOException e) {
                // TODO IO Exception
                // IOException - If an I/O error occurs
            }
        }
        return geneListArrayList;
    }

    // setters et getters

    public void setAdresse(String path){
        this.adresse = path;
    }

    public String getAdresse(){
        return this.adresse;
    }
}