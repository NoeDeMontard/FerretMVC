/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.Model;

import java.io.IOException;

class InvalidCharacterException extends Exception { }
class UnsupportedFileTypeException  extends IOException { }

/**
 * Classe 
 * @author Noé de Montard (Noe.de-Montard@eleves.ec-nantes.fr)
 */
public class BrowseM {
    // NOTE : Ce code correspond aux commentaires suivants (vers les lignes 215 et 266 de RegionInteretGUI.java, méthode RegionInteretGUI.addRegionInteret) :
    //"<html>You can load a file in any of the following formats: <br> - a comma-delimited .csv file (example: variant.csv containing 73885319, 2395029) <br>"
    //+ " - a tab-delimited .tab or .tsv file (example: variant.tab containing 73885319 &nbsp&nbsp&nbsp&nbsp 2395029) <br>"
    //+ " - a space-delimited .txt file (example: variant.txt containing 73885319 2395029)"
    //+ "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>");
    // et
    //"<html>You can load a file in any of the following formats for either gene names or gene IDs: <br> "
    //+ " - a comma-delimited .csv file (example: gene.csv containing CCR5, HCP5) <br>"
    //+ " - a tab-delimited .tab or .tsv file (example: gene.tab containing CCR5 &nbsp&nbsp&nbsp&nbsp HCP5) <br>"
    //+ " - a space-delimited .txt file (example: gene.txt containing CCR5 HCP5)"
    //+ "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>"

    /**
     * Retourne le délimiteur du fichier en focntion de type (d'après son extension)
     * @param adresse adresse d'un fichier
     * @return delimiter chaine de caractère délimitant les entrées du fichier en adresse
     * @throws UnsupportedFileTypeException extension non reconnue
     */
    
    public static String getDelimiter(String adresse) throws UnsupportedFileTypeException{
        //this.adresse.toLowerCase().endsWith(".csv"); // toLowerCase to make the extension check case independant
        int i = adresse.lastIndexOf('.');
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
     * @param onlyNumbers décrit si ce qui est invalide est uniquement les nombres ou aussi les lettres
     * @return String invalidRegex 
     */
    public static String getInvalidRegex(boolean onlyNumbers) {
        String invalidRegex;
        if (onlyNumbers) {
            invalidRegex = ".*\\D.*"; // Tout sauf les nombres - This is everything except numbers
        } else {
            invalidRegex = ".*[^a-zA-Z0-9\\-].*"; // Tout sauf les lettres et les nombres, y compris les tirets bas - This is everything except letters and numbers, including underscore
        }
        return invalidRegex;
    }
}
