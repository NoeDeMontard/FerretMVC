/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.PAPPL6_2020.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.LinkedList;

import org.ecn.PAPPL6_2020.FerretMain;

/**
 * Classe correspondant au traitement sur le serveur ESP.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */


public class TraitementESP extends Traitement {


    /**
     * Fonction de connexion au serveur ESP et de récupération des infos qui s'y trouvent.
     * A executer si l'option ESP a été cochée par l'utilisateur. Est lancée depuis Traitement1KG.
     * @param sortedQueries liste des requêtes où l'on repère l'élément cherché par le locus
     * @return list d'EspInfoObj
     */
    // aucune modification n'a été apportée sur ce traitement (nous avons seulement restreint l'application de la méthode à une information entrée sous forme de locus, à élargir si besoin)
    // TODO: transformer au préalable les infos entrées autrement en infos sous forme de locus, à partir de la classe Traitement1KG (s'il y a besoin, nous n'avons pas testé)
    public static LinkedList<EspInfoObj> exomeSequencingProject(ArrayList<LocusM> sortedQueries){
        // nous n'avons pas touché au corps de cette fonction
        LinkedList<EspInfoObj> espData = null;
        try{
            //System.out.println("Got to ESP section");
            espData = new LinkedList<>();
            CodeSource codeSource = FerretMain.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            Process proc;
            for(int i = 0; i < sortedQueries.size(); i++){
                int startQuery = sortedQueries.get(i).getStart();
                while(sortedQueries.get(i).getEnd() - startQuery >= 1000000){
                    String temp = "java -jar evsClient0_15.jar -t " +  sortedQueries.get(i).getChromosome()+ ":" + startQuery + "-" + (startQuery + 999999) + " -f vcf";
                    proc = Runtime.getRuntime().exec(temp);
                    proc.waitFor();
                    String fileName = sortedQueries.get(i).getChromosome()+ "-" + startQuery + "-" + (startQuery + 999999);
                    BufferedReader vcfRead = new BufferedReader(new FileReader("wsEVS_variant_download_chr" + fileName + ".vcf"));
                    String s;
                    while ((s = vcfRead.readLine()) != null) {
                        if (s.charAt(0) != '#'){
                            String[] stringSplit = s.split("\t");
                            String[] uselessSubString = stringSplit[7].split(";");
                            String[] EAString = uselessSubString[1].split(",");
                            String[] AAString = uselessSubString[2].split(",");
                            espData.add(new EspInfoObj(stringSplit[0], stringSplit[1], stringSplit[2], stringSplit[3], stringSplit[4], EAString[1], EAString[0].substring(6), AAString[1], AAString[0].substring(6)));
                        }
                    }
                    vcfRead.close();
                    new File("wsEVS_variant_download_chr" + fileName + ".vcf").delete();
                    new File("wsEVS_Coverage_download_chr" + fileName + "_AllSites.txt").delete();
                    new File("wsEVS_Coverage_download_chr" + fileName + "_SummaryStats.txt").delete();
                    startQuery += 1000000;
                }
                String temp = "java -jar evsClient0_15.jar -t " + sortedQueries.get(i).getChromosome()+ ":" + startQuery + "-" + sortedQueries.get(i).getEnd() + " -f vcf";
                proc = Runtime.getRuntime().exec(temp);
                proc.waitFor();
                String fileName = sortedQueries.get(i).getChromosome()+ "-" + startQuery + "-" + sortedQueries.get(i).getEnd();
                BufferedReader vcfRead = new BufferedReader(new FileReader("wsEVS_variant_download_chr" + fileName + ".vcf"));
                String s;
                while ((s = vcfRead.readLine()) != null) {
                    if (s.charAt(0) != '#'){
                        String[] stringSplit = s.split("\t");
                        String[] uselessSubString = stringSplit[7].split(";");
                        String[] EAString = uselessSubString[1].split(",");
                        String[] AAString = uselessSubString[2].split(",");
                        espData.add(new EspInfoObj(stringSplit[0], stringSplit[1], stringSplit[2], stringSplit[3], stringSplit[4], EAString[1], EAString[0].substring(6), AAString[1], AAString[0].substring(6)));
                    }
                }
                vcfRead.close();
                new File("wsEVS_variant_download_chr" + fileName + ".vcf").delete();
                new File("wsEVS_Coverage_download_chr" + fileName + "_AllSites.txt").delete();
                new File("wsEVS_Coverage_download_chr" + fileName + "_SummaryStats.txt").delete();
            }
        }catch(IOException e){
                //e.printStackTrace();
        }catch(InterruptedException e){
                //e.printStackTrace();
        }catch(URISyntaxException e){
                //e.printStackTrace();
        }catch(Exception e){
                //e.printStackTrace();
        }
        return espData;
    }
}

