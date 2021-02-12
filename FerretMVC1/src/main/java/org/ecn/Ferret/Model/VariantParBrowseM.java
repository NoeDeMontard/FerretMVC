package org.ecn.Ferret.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe définissant une entrée sous forme de variant pour laquelle on a utilisé "browse".
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class VariantParBrowseM extends VariantM {

    private String adresse; // l'adresse du fichier où sont entrés les variants

    // TODO: écrire une fonction qui enregistre les données sous forme de VariantParIDM à partir du fichier donné par l'attribut adresse
    // NOTE : Ce code correspond au commentaire suivant (vers la ligne 215 de RegionInteretGUI.java, méthode RegionInteretGUI.addRegionInteret) :
    //"<html>You can load a file in any of the following formats: <br> - a comma-delimited .csv file (example: variant.csv containing 73885319, 2395029) <br>"
    //+ " - a tab-delimited .tab or .tsv file (example: variant.tab containing 73885319 &nbsp&nbsp&nbsp&nbsp 2395029) <br>"
    //+ " - a space-delimited .txt file (example: variant.txt containing 73885319 2395029)"
    //+ "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>");
    // getInvalidRegex(True)
    public List<VariantParIDM>  getVariantId() { // Liste 
        List<VariantParIDM> variantListArrayList = null;
        try{
            String delimiter = BrowseM.getDelimiter(this.adresse);
            variantListArrayList = getVariantId(delimiter);
        } catch (UnsupportedFileTypeException e) {
            // TODO MVC : affichage d'erreur : demander un autre fichier ou afficher une erreur -> à faire avec autre part ?
            // UnsupportedDataTypeException : type de fichier non supporté
        }

        return variantListArrayList;
    }
    
    private List<VariantParIDM>  getVariantId(String delimiter) {  // séparé en 2 pour éviter d'imbriquer des try-catch
        ArrayList<VariantParIDM> variantListArrayList = new ArrayList<>();
        String invalidRegex = BrowseM.getInvalidRegex( true ); 
        try (BufferedReader file = new BufferedReader(new FileReader(adresse));) {
            String line;
            line = file.readLine();
            String[] variants;
            while (line != null ) {
                variants = line.split(delimiter);
                for (String variantString : variants) {
                    variantString = variantString.replace(" ", "").toUpperCase();// Enlève les espaces - remove spaces
                    if ( variantString.matches(invalidRegex) ) { // identifier les caractères invalides - identify invalid characters
                        throw new InvalidCharacterException();
                    }
                    if (variantString.length() > 0) {
                        VariantParIDM variantParIDM = new VariantParIDM();
                        variantParIDM.setIdentifiant(Integer.parseInt(variantString));
                        variantListArrayList.add(variantParIDM);
                        
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
        return variantListArrayList;
    }
    // setters et getters

    public void setAdresse(String path){
      this.adresse = path;
    }

    public String getAdresse(){
      return this.adresse;
    }
}