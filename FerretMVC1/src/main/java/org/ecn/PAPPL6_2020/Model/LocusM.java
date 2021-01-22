package org.ecn.PAPPL6_2020.Model;

/**
 * Classe représentant une entrée sous forme de locus.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class LocusM extends ElementDeRechercheM implements Comparable<LocusM> {

    private int chromosome; // le chromosome qu'on étudie
    private int start; // le début de la zone d'étude sur ce chromosome
    private int end; // la fin de la zone d'étude sur ce chromosome

    @Override
    // fonction qui compare deux locus pour déterminer lequel vient avant l'autre (d'abord en fonction des chromosomes, puis de la position de départ si sur le même chromosome
    // cela permet de trier, donc de vérifier qu'on aura trouvé tout ce qu'il faut
    public int compareTo(LocusM o) {
        if (! (this.chromosome == o.chromosome)){
            return this.chromosome - o.chromosome;
        }
        else{
            return this.start - o.start;
        }
    }
    
    // constructeur
    public LocusM(int chromosome, int start, int end) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
    }


    // setters et getters

    public void setChromosome(int chr){
      this.chromosome = chr;
    }

    public void setStart(int d){
      this.start = d;
    }

    public void setEnd(int f){
      this.end = f;
    }

    public int getChromosome(){
      return this.chromosome;
    }

    public int getStart(){
      return this.start;
    }

    public int getEnd(){
      return this.end;
    }
    
  }