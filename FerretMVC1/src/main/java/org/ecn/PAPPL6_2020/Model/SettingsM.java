// NB : cette classe est considérée comme provisoirement terminée

package org.ecn.PAPPL6_2020.Model;

/**
 * Classe pour les paramètres de la recherche. A utiliser pour appeler les fonctions de traitement.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class SettingsM {


    private int version1KG; // la version utilisée (pour l'instant, a priori 1 ou 3)
    private float maf; // permet de différencier les variants rares des communs
    private String output; // quels fichiers vont être créés par Ferret
    private boolean versionHG; // version par défaut ou non
    private boolean esp; // est-ce que l'utilisateur veut aussi générer des infos du serveur ESP

  
    // setters et getters

    public void setVersion1KG(int v){
      this.version1KG = v;
    }

    public void setMaf(float m){
      this.maf = m;
    }

    public void setOutput(String out){
      this.output = out;
    }

    public void setVersionHG(boolean v){
      this.versionHG = v;
    }

    public void setEsp(boolean b){
      this.esp = b;
    }

    public int getVersion1KG(){
      return this.version1KG;
    }

    public float getMaf(){
      return this.maf;
    }

    public String getOutput(){
      return this.output;
    }

    public boolean getVersionHG(){
      return this.versionHG;
    }

    public boolean getEsp(){
      return this.esp;
    }

}