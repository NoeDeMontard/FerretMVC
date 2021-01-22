package org.ecn.PAPPL6_2020.Model;

/**
 * Classe représentant une entrée de gène sous forme "browse"
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class GeneParBrowseM extends GeneM {

  private String adresse;
  
  //TODO: écrire une fonction qui enregistre les données sous formes de GeneParIDM ou GeneParNomM à partir du fichier donné par l'attribut adresse

  // setters et getters

  public void setAdresse(String path){
    this.adresse = path;
  }

  public String getAdresse(){
    return this.adresse;
  }
}