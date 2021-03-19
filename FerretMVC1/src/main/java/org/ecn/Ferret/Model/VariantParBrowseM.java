package org.ecn.Ferret.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe définissant une entrée sous forme de variant pour laquelle on a utilisé "browse".
 * @author Mathieu JUNG-MULLER
 * @author Bozhou WANG
 * @author Noe DE MONTARD
*/

public class VariantParBrowseM extends VariantM {

    private String adresse; // l'adresse du fichier où sont entrés les variants


    /**
     * Récupère les variants depuis un fichier et les renvois dans une liste de VariantParIDM.
     * <p>
     * Le fichier doit être au format cvs (délimité par des virgules), tab, 
     * tsv (délimités par des tabulations) ou txt (délimité par des espaces) 
     * (les passage à la ligne servent aussi de délimiteur dans tous les cas).
     * <p>
     * Son addresse est donné par l'attribut adresse accessible par 
     * setAdresse(String path) et getAdresse().
     * 
     * @return List VariantParIDM Liste des variants 
     * dont les identifiants sont inscrits dans le fichier.
     */
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

    /**
     * Défini l'adresse du fichier contenant les variants à récupérer
     * @param path le chemin d'accès
     */
    public void setAdresse(String path){
      this.adresse = path;
    }

    /**
     * Récupère l'adresse du fichier contenant les variants à récupérer
     * @return chemin du fichier
     */
    public String getAdresse(){
      return this.adresse;
    }
}