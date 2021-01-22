package org.ecn.PAPPL6_2020.Model;

/**
 * Classe définissant une entrée sous forme de variant pour laquelle on a utilisé "browse".
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class VariantParBrowseM extends VariantM {

  private String adresse; // l'adresse du fichier où sont entrés les variants
  
  // TODO: écrire une fonction qui enregistre les données sous forme de VariantParIDM à partir du fichier donné par l'attribut adresse

  // setters et getters

  public void setAdresse(String path){
    this.adresse = path;
  }

  public String getAdresse(){
    return this.adresse;
  }
}