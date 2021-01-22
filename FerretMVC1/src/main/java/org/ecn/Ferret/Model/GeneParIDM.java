package org.ecn.Ferret.Model;

/**
 * Classe représentant une entrée de gène pour lequel on a donné l'ID.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class GeneParIDM extends GeneM {

  private int identifiant;


  // setters et getters

  public void setIdentifiant(int id){
    this.identifiant = id;
  }

  public int getIdentifiant(){
    return this.identifiant;
  }
}