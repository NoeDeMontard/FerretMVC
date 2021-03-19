/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes

import org.ecn.Ferret.Controller.RunCTRL;
import org.ecn.Ferret.Model.FoundGeneAndRegion;
import org.ecn.Ferret.Model.LocusM;
import org.ecn.Ferret.Model.Traitement1KG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

//import java.awt.TextField;
//import java.io.FileNotFoundException;
//import java.io.IOException;

/**
 * Classe d'affichage du lancement de l'application Ferret.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class RunGUI extends GUI{
    JPanel goPanel = new JPanel();
    JLabel status;
    JButton goButton = new JButton("Run Ferret, Run!");
    RunCTRL runCTRL;

    //à appeler dans le contrôleur
    public void setStatus(Traitement1KG traitement, List<String> processStatus){
        status.setText(traitement.process(processStatus));
    }

    public void addRun(GUI g){
        g.bigPanel.add(goPanel);
        goPanel.add(goButton);
        goButton.setPreferredSize(new Dimension(320, 60));
        goPanel.setBackground(Color.gray);
        // setActionCommand
        goButton.setActionCommand("goButton");
    }

    public void runListener(ActionListener a) {
        goButton.addActionListener(a);}

//    public void RunListener() {
//        goButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                JOptionPane.showMessageDialog(null, "Ferret was unable to retrieve any variants","Error",JOptionPane.OK_OPTION);
//            }
//        });
//    }

    public void afficheVariantID(List<String> SNPsFound,LinkedList<String> chromosome,LinkedList<String> startPos,LinkedList<String> endPos,int sd){

        String[] options = {"Yes","No"};
        JPanel partialSNPPanel = new JPanel();
        JTextArea listOfSNPs = new JTextArea(SNPsFound.toString().substring(1, SNPsFound.toString().length()-1));
        listOfSNPs.setWrapStyleWord(true);
        listOfSNPs.setLineWrap(true);
        listOfSNPs.setBackground(partialSNPPanel.getBackground());
        partialSNPPanel.setLayout(new BoxLayout(partialSNPPanel, BoxLayout.Y_AXIS));
        partialSNPPanel.add(new JLabel("Ferret encountered problems retrieving the variant positions from the NCBI SNP Database."));
        partialSNPPanel.add(new JLabel("Here are the variants successfully retrieved:"));
        JScrollPane listOfSNPScrollPane = new JScrollPane(listOfSNPs,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listOfSNPScrollPane.setBorder(BorderFactory.createEmptyBorder());
        listOfSNPScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        partialSNPPanel.add(listOfSNPScrollPane);
        partialSNPPanel.add(new JLabel("Do you wish to continue?"));

        int choice = JOptionPane.showOptionDialog(null,
                partialSNPPanel,
                "Continue?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
        if(choice == JOptionPane.YES_OPTION){
            LocusM[] queries = new LocusM[chromosome.size()];
            for(int i = 0; ! chromosome.isEmpty() ; i++){
                queries[i] = new LocusM(Integer.parseInt(chromosome.remove()), (Integer.parseInt(startPos.remove()) - sd), (Integer.parseInt(endPos.remove()) + sd));
            }}}

    public void affichageError(){
        JOptionPane.showMessageDialog(null, "Ferret was unable to retrieve any variants","Error",JOptionPane.OK_OPTION);
    }
    public void affichageAllSNPsFound(LinkedList<String> chromosome,LinkedList<String> startPos,LinkedList<String> endPos,int sd){
        LocusM[] queries = new LocusM[chromosome.size()];
        for(int i = 0; ! chromosome.isEmpty(); i++){
            queries[i] = new LocusM(Integer.parseInt(chromosome.remove()), Integer.parseInt(startPos.remove()) - sd, Integer.parseInt(endPos.remove()) + sd);
        }}

    /**
     *
     * @param geneLocationFromGeneName
     */
    public void affichageGene(FoundGeneAndRegion[] geneLocationFromGeneName){
        String[] options = {"Yes","No"};
        LocusM[] queries;

        JPanel partialGenePanel = new JPanel();
        JTextArea listOfGenes = new JTextArea(geneLocationFromGeneName[0].getFoundGenes());
        listOfGenes.setWrapStyleWord(true);
        listOfGenes.setLineWrap(true);
        listOfGenes.setBackground(partialGenePanel.getBackground());
        partialGenePanel.setLayout(new BoxLayout(partialGenePanel, BoxLayout.Y_AXIS));
        partialGenePanel.add(new JLabel("Ferret encountered problems retrieving the gene positions from the NCBI Gene Database."));
        partialGenePanel.add(new JLabel("Here are the genes successfully retrieved:"));
        JScrollPane listOfGenesScrollPane = new JScrollPane(listOfGenes,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listOfGenesScrollPane.setBorder(BorderFactory.createEmptyBorder());
        listOfGenesScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        partialGenePanel.add(listOfGenesScrollPane);
        partialGenePanel.add(new JLabel("Do you wish to continue?"));

        int choice = JOptionPane.showOptionDialog(null,
                partialGenePanel,
                "Continue?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
        if(choice == JOptionPane.YES_OPTION){
            queries = geneLocationFromGeneName[0].getInputRegionArray();
        }
        else {
            queries = geneLocationFromGeneName[0].getInputRegionArray();
        }
    }
}