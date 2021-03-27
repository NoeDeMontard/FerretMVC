/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret;

import javax.swing.JFrame;
import org.ecn.Ferret.Controller.*;
import org.ecn.Ferret.View.*;
import org.ecn.Ferret.Model.*;
/**
 *
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class FerretMain {
    

    public static void main(String[] args) {
         GUI g = new GUI();
         PopulationGUI pg = new PopulationGUI();
         RegionInteretGUI rig = new  RegionInteretGUI();
         RunGUI r = new RunGUI();
         MenuFerretGUI mfg= new MenuFerretGUI();
         MenuHelpGUI mhg= new MenuHelpGUI();
        // SettingsGUI sg=new SettingsGUI(); //TODO: dans la methode d'execution traitement1kg de RunCTRL, on a besoin de SettingGUI comme parametre, mais l'appel de son constructeur va afficher la fenetre de settings qui n’est pas desire au lancement de l'applcation 
         mfg.addFerretMenuItems(g);
         mhg.addHelpMenuItems(g);
         pg.addPopulation(g);
         rig.addRegoinInteret(g);
    
         r.addRun(g,pg,rig,mfg/*,sg*/);
    
         g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         g.afficher();
      
    }
}