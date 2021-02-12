package org.ecn.Ferret.Model;

import java.io.*;
import java.util.ArrayList;
import org.ecn.Ferret.View.RegionInteretGUI; // TODO MVC : getGeneNameRadioButton : faire la récupération de isGeneNames par le controleur (via RegionInteretGUI.getGeneNameRadioButton().isSelected())

/**
 * Classe représentant une exception InvalidCharacterException - If a character doesn't match a verification Regex
 * @Authors: Noe DE MONTARD
*/
class InvalidCharacterException extends Exception { }
class UnsupportedFileTypeException  extends IOException { }

/**
 * Classe représentant une entrée de gène sous forme "browse"
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG & Noe DE MONTARD
*/

public class GeneParBrowseM extends GeneM {

    private String adresse;

    // NOTE : Ce code correspond à celui au niveau de la ligne 1685 du GUI.java de la version 2.1.1, 
    // de celui au niveau de la ligne 1920 de GUI_Formated.java (formatage netbeans automatique du GUI.java de la version 2.1.1), 
    // ou au commentaire suivant (vers la ligne 266 de RegionInteretGUI.java, méthode RegionInteretGUI.addRegoinInteret) :
    //"<html>You can load a file in any of the following formats for either gene names or gene IDs: <br> "
    //+ " - a comma-delimited .csv file (example: gene.csv containing CCR5, HCP5) <br>"
    //+ " - a tab-delimited .tab or .tsv file (example: gene.tab containing CCR5 &nbsp&nbsp&nbsp&nbsp HCP5) <br>"
    //+ " - a space-delimited .txt file (example: gene.txt containing CCR5 HCP5)"
    //+ "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>"
    private String getDelimiter() throws UnsupportedFileTypeException{
        //this.adresse.toLowerCase().endsWith(".csv"); // toLowerCase to make the extension check case independant
        int i = this.adresse.lastIndexOf('.');
        String extension = adresse.substring(i + 1).toLowerCase();
        String delimiter =   null;
        switch (extension) {
            case "csv":
                // "a comma-delimited .csv file (example: gene.csv containing CCR5, HCP5)"
                // "A carriage return can also be used as a delimiter for all file types."
                delimiter = ",";
                break;
            case "tab": //tab et tsv sont le même format
            case "tsv":
                // "a tab-delimited .tab or .tsv file (example: gene.tab containing CCR5 &nbsp&nbsp&nbsp&nbsp HCP5)"
                // "A carriage return can also be used as a delimiter for all file types."
                delimiter = "\\t";
                break;
            case "txt":
                // "a space-delimited .txt file (example: gene.txt containing CCR5 HCP5)"
                //" A carriage return can also be used as a delimiter for all file types."
                delimiter = " ";
                break;
            default:
                throw new UnsupportedFileTypeException();
        }
        return delimiter;
    }

    /**
     * Retourne la chaine de caractère correspondant à l'expression régulière (Regex)
     * détectant les caractères invalides.
     *
     * @return String invalidRegex 
     */
    private String getInvalidRegex(boolean isGeneNames) {
        String invalidRegex;
        if (isGeneNames) {
            invalidRegex = ".*[^a-zA-Z0-9\\-].*"; // Tout sauf les lettres et les nombres, y compris les tirets bas - This is everything except letters and numbers, including underscore
        } else {
            invalidRegex = ".*\\D.*"; // Tout sauf les nombres - This is everything except numbers
        }
        return invalidRegex;
    }
    
    /**
     * Retourne True si le fichier contient des gènes d'après leur nom.
     *
     * @return boolean geneNames
     */
    private boolean isGeneNames(){
        // TODO MVC : getGeneNameRadioButton : résoudre l'erreur : récupérer correctement le type de données -> à faire avec autre part ?
        return RegionInteretGUI.getGeneNameRadioButton().isSelected(); 
    }

    /**
     * Récupère les gènes du fichier et les renvoi dans une liste de GeneParIDM
     * ou de GeneParNomM, selon le boutton RegionInteretGUI.getGeneNameRadioButton.
     *
     * Get the genes from the file and return them in a list of GeneParIDM or of
     * GeneParNomM, in accordance to the RegionInteretGUI.getGeneNameRadioButton
     * Button.
     * 
     * @return ArrayList geneListArrayList liste de gènes GeneM
     */
    public ArrayList<GeneM>  getGeneIDNOM() { // Liste listeID ou listeNom
        ArrayList<GeneM> geneListArrayList = new ArrayList<>();
        try{
            String delimiter = this.getDelimiter();
            boolean geneNames = isGeneNames();
            String invalidRegex = getInvalidRegex(geneNames); 
            if ( delimiter != null ) {
                try (BufferedReader file = new BufferedReader(new FileReader(adresse));) {
                    String line;
                    line = file.readLine();
                    String[] genes;
                    while (line != null ) {
                        genes = line.split(delimiter);
                        for (String geneString : genes) {
                            geneString = geneString.replace(" ", "").toUpperCase();// Enlève les espaces - remove spaces
                            if ( geneString.matches(invalidRegex) ) { // identifier les caractères invalides - identify invalid characters
                                throw new InvalidCharacterException();
                            }
                            if (geneString.length() > 0) {
                                if ( geneNames ) {
                                    GeneParNomM geneParNom = new GeneParNomM();
                                    geneParNom.setNom(geneString);
                                    geneListArrayList.add(geneParNom);
                                } else {
                                    GeneParIDM geneParId = new GeneParIDM();
                                    geneParId.setIdentifiant(Integer.parseInt(geneString));
                                    geneListArrayList.add(geneParId);
                                }

                            }
                        }
                        line = file.readLine();
                    }
                    // file.close(); // "Java 7's try-with-resources structure automatically handles closing the resources that the try itself opens. Thus, adding an explicit close() call is redundant and potentially confusing."
                } catch (InvalidCharacterException | IOException e){
                    // TODO MVC : affichage d'erreur : demander un autre fichier ou afficher une erreur -> à faire avec autre part ?
                    // InvalidCharacterException  : cas de contenu du fichier invalide: InvalidCharacterException - If a character doesn't match a verification Regex
                    // ou FileNotFoundException - attrapé par l'IOException - if the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
                    // ou IOException - If an I/O error occurs
                }
            }
        } catch (UnsupportedFileTypeException e) {
            // TODO MVC : affichage d'erreur : demander un autre fichier ou afficher une erreur -> à faire avec autre part ?
            // UnsupportedDataTypeException : type de fichier non supporté
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