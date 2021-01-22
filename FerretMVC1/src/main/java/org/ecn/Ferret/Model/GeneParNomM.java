package org.ecn.Ferret.Model;

/**
 * Classe représentant une entrée sous forme de gène pour laquelle on a indiqué le nom.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class GeneParNomM extends GeneM {

  private String nom;


  // setters et getters

  public void setNom(String n){
    this.nom = n;
  }

  public String getNom(){
    return this.nom;
  }
}