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
   // SettingsGUI sg=new SettingsGUI();
    MenuFerretGUI mfg= new MenuFerretGUI();
    MenuHelpGUI mhg= new MenuHelpGUI();
           
    mfg.addFerretMenuItems(g);
    mhg.addHelpMenuItems(g);
    pg.addPopulation(g);
    rig.addRegoinInteret(g);
    
    r.addRun(g,pg,rig,mfg/*,sg*/);
    
    g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    g.afficher();
    }
}
