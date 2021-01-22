/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;

import javax.swing.JFrame;

/**
 *
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class test {
    public static void main(String[] args) {
    
    GUI g = new GUI();
    PopulationGUI pg = new PopulationGUI();
    RegionInteretGUI rig = new  RegionInteretGUI();
    RunGUI r = new RunGUI();
    
    MenuFerretGUI mfg= new MenuFerretGUI();
    MenuHelpGUI mhg= new MenuHelpGUI();
           
    mfg.addFerretMenuItems(g);
    mhg.addHelpMenuItems(g);
    pg.addPopulation(g);
    rig.addRegoinInteret(g);
    r.addRun(g);
    
    g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    g.afficher();
    }
}
