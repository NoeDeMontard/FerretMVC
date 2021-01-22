/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.PAPPL6_2020.View;
// import classes
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe d'affichage du lancement de l'application Ferret.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class RunGUI extends GUI{
    JPanel goPanel = new JPanel();
    JButton goButton = new JButton("Run Ferret, Run!");
    
    public void addRun(GUI g){
        g.bigPanel.add(goPanel);
        goPanel.add(goButton);
        goButton.setPreferredSize(new Dimension(320, 60));
        goPanel.setBackground(Color.gray);
        // setActionCommand
        goButton.setActionCommand("goButton");
    }
    
    public void RunListener(ActionListener a) {
        goButton.addActionListener(a);
    }
}
