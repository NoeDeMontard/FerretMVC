package org.ecn.Ferret.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ecn.Ferret.View.RegionInteretGUI; // TODO MVC : getGeneNameRadioButton : faire la récupération de isGeneNames par le controleur (via RegionInteretGUI.getGeneNameRadioButton().isSelected())

/**
 * Classe représentant une exception InvalidCharacterException - If a character doesn't match a verification Regex
 * @Authors: Noe DE MONTARD
*/

/**
 * Classe représentant une entrée de gène sous forme "browse"
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG & Noe DE MONTARD
*/

public class GeneParBrowseM extends GeneM {

    private String adresse;

    
        
    /**
     * Retourne True si le fichier contient des gènes d'après leur nom.
     *
     * @return boolean geneNames
     */
    private boolean isGeneNames(){
        // TODO MVC : getGeneNameRadioButton : résoudre l'erreur : récupérer correctement le type de données -> à faire avec autre part ?
        RegionInteretGUI RIgui = new RegionInteretGUI();
        return RIgui.getGeneNameRadioButton().isSelected(); 
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
    public List<GeneM>  getGeneIDNom() { // Liste listeID ou listeNom
        List<GeneM> geneListArrayList = null;
        try{
            String delimiter = BrowseM.getDelimiter(this.adresse);
            geneListArrayList = getGeneIDNom(delimiter);
        } catch (UnsupportedFileTypeException e) {
            // TODO MVC : affichage d'erreur : demander un autre fichier ou afficher une erreur -> à faire avec autre part ?
            // UnsupportedDataTypeException : type de fichier non supporté
        }

        return geneListArrayList;
    }
    private List<GeneM>  getGeneIDNom(String delimiter) { // Liste listeID ou listeNom, séparé en 2 pour éviter d'imbriquer des try-catch
        ArrayList<GeneM> geneListArrayList = new ArrayList<>();
        boolean geneNames = isGeneNames();
        String invalidRegex = BrowseM.getInvalidRegex( !geneNames); 
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
