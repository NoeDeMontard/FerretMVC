/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;

// import classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.ecn.Ferret.Controller.MenuCTRL;
/**
 * Classe d'affichage du menu d'aide de l'application Ferret. Le menu permettra ensuite d'ouvrir les différents onglets.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class MenuHelpGUI extends ElementDuMenuGUI implements ActionListener{	
    // MenuBar_Help
    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutMenuItem = new JMenuItem("About Ferret");
    JMenuItem faqMenuItem = new JMenuItem("FAQ");
    JMenuItem contactMenuItem = new JMenuItem("Contact");
    public AboutGUI about;
    public ContactGUI contact;

    public void addHelpMenuItems(GUI g) {
        g.ferretMenuBar.add(helpMenu);
        helpMenu.add(aboutMenuItem);
        helpMenu.add(faqMenuItem);
        helpMenu.add(contactMenuItem);
        // setActionCommand
        aboutMenuItem.addActionListener(this);
        aboutMenuItem.setActionCommand("aboutMenuItem");
        faqMenuItem.addActionListener(this);
        faqMenuItem.setActionCommand("faqMenuItem");
        contactMenuItem.addActionListener(this);
        contactMenuItem.setActionCommand("contactMenuItem");
    }
    
    //Listeners for view->controller
    public void menuHelpListener(ActionListener a) {
        aboutMenuItem.addActionListener(a);
        faqMenuItem.addActionListener(a);
        contactMenuItem.addActionListener(a);
    }
     public void actionPerformed(ActionEvent ae) {
        MenuCTRL menu = new MenuCTRL();
        menu.actionPerformed(ae);
    }
}