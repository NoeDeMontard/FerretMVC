/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.Model;

/**
 * Classe exprimant le résultat de requêtes sur le serveur.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class FoundGeneAndRegion {
    // cette classe a été reprise identique à celle fournie dans l'ancienne version de Ferret
    // demander plus de précision à Mmes LIMOU et BA si besoin
    
    
    private String geneNamesFound;
    private LocusM[] locationOfFoundGenes;
    private boolean allFound;

    public FoundGeneAndRegion(String geneNamesFound, LocusM[] locationOfFoundGenes, boolean allFound){
        this.geneNamesFound = geneNamesFound; 
        this.locationOfFoundGenes = locationOfFoundGenes;
        this.allFound = allFound;
    }


    //setters et getters
    public void setGeneNamesFound(String geneNamesFound){
        this.geneNamesFound = geneNamesFound;
    }

    public void setLocationOfFoundGenes(LocusM[] locationOfFoundGenes) {
        this.locationOfFoundGenes = locationOfFoundGenes;
    }

    public void setAllFound(boolean allFound) {
        this.allFound = allFound;
    }

    public String getFoundGenes() {
        return this.geneNamesFound;
    }
	
    public LocusM[] getInputRegionArray(){
            return this.locationOfFoundGenes;
    }

    public Boolean getFoundAllGenes(){
            return this.allFound;
    }
	
}