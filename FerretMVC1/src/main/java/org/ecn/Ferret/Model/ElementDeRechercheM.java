// NB : cette classe est considérée comme provisoirement terminée

package org.ecn.Ferret.Model;

/**
 * Classe générale sur l'élément de recherche ciblé. Va se décliner en sous-classes en fonction de la manière dont les donnéees sont entrées.
 * Cette classe est également utilisée pour repérer des éléments ayant les mêmes caractéristiques (locus, gènes, snp)
 * même s'ils ne sont pas entrés à l'origine en tant qu'éléments de recherche.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class ElementDeRechercheM {

  private String[] populations; // l'ensemble des populations sur lesquelles s'effectue l'étude
  private String fileLocation; // l'endroit où les fichiers créés doivent être sauvegardés


  // setters et getters

  public void setPopulations(String[] pop){
    this.populations = pop;
  }

  public void setFileLocation(String path){
    this.fileLocation = path;
  }

  public String[] getPopulations(){
    return this.populations;
  }

  public String getFileLocation(){
    return this.fileLocation;
  }
  
  public boolean contain(String s){
      boolean b = false;
      for (String pop: this.populations){
          if (pop.equals(s)){
              b = true;
          }
      }
      return b;
  }

}