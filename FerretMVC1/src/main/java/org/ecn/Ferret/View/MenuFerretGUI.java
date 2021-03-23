/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.ecn.Ferret.Controller.MenuCTRL;

/**
 * Classe d'affichage général du menu de l'application Ferret. Le menu permettra ensuite d'ouvrir les différents onglets.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class MenuFerretGUI extends ElementDuMenuGUI implements ActionListener{
    // MenuBar_Ferret
    JMenu ferretMenu = new JMenu("Ferret");
    JMenuItem settingsMenuItem = new JMenuItem("Settings");
    JMenuItem updateMenuItem = new JMenuItem("Check for updates");
    JMenuItem exitMenuItem = new JMenuItem("Quit");
    public UpdateGUI update;
    public SettingsGUI settings;
    
    public void addFerretMenuItems(GUI g){ 
        g.ferretMenuBar.add(ferretMenu);
        ferretMenu.add(settingsMenuItem);
        ferretMenu.add(updateMenuItem);
        ferretMenu.add(exitMenuItem);
        // setActionCommand
        settingsMenuItem.addActionListener(this);
        settingsMenuItem.setActionCommand("settingsMenuItem");
        exitMenuItem.addActionListener(this);
        exitMenuItem.setActionCommand("exitMenuItem");
        updateMenuItem.addActionListener(this);
        updateMenuItem.setActionCommand("updateMenuItem");
    }
    
    public void menuFerretListener(ActionListener a) {
        settingsMenuItem.addActionListener(a);
        exitMenuItem.addActionListener(a);
        updateMenuItem.addActionListener(a);
    }
    public void actionPerformed(ActionEvent ae) {
        MenuCTRL menu = new MenuCTRL();
        menu.actionPerformed(ae);
    }
}