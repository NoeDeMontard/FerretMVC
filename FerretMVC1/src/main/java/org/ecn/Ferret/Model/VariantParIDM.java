package org.ecn.Ferret.Model;

/**
 * Classe définissant une entrée sous forme de variant défini par son ID.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class VariantParIDM extends VariantM {

  private int identifiant;
  private int surroundingDist;

  // setters et getters

  public void setIndentifiant(int id){
    this.identifiant = id;
  }

  public void SetSurroundingDist(int sr){
    this.surroundingDist = sr;
  }

  public int GetIdentifiant(){
    return this.identifiant;
  }

  public int getSurroundingDist(){
    return this.surroundingDist;
  }
}