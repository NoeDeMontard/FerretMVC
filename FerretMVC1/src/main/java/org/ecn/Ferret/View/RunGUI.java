/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.ecn.Ferret.Controller.RunCTRL;

/**
 * Classe d'affichage du lancement de l'application Ferret.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class RunGUI extends GUI{
    JPanel goPanel = new JPanel();
    JButton goButton = new JButton("Run Ferret, Run!");
    private PopulationGUI pg;
    private RegionInteretGUI rig;
    private MenuFerretGUI mfg;
    public void addRun(GUI g, PopulationGUI pg, RegionInteretGUI rig, MenuFerretGUI mfg){
        g.bigPanel.add(goPanel);
        goPanel.add(goButton);
        goButton.setPreferredSize(new Dimension(320, 60));
        goPanel.setBackground(Color.gray);
        // setActionCommand
        goButton.setActionCommand("goButton");
        this.rig=rig;
        this.pg=pg;
        this.mfg=mfg;
    }
    
    public void RunListener(ActionListener a) {
        goButton.addActionListener(a);
    }
     public void actionPerformed(ActionEvent e){//appeler controller et transmettre les classes gui popultaion, region et settings pour verifier les parametres et faire l'execution 
         RunCTRL runctrl=new RunCTRL(pg,rig,mfg.settings);
         if (runctrl.VerificationParam()){
             runctrl.ExecutionTraitement1KG();
         }
     }

}
