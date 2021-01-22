/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.PAPPL6_2020.View;
// import classes
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Classe d'affichage de l'onglet Contact du menu. Cela s'affiche après que l'utilisateur a cliqué sur "Contact" dans le menu "Help".
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class ContactGUI extends ElementDuMenuGUI{
    //Contact window
    JFrame contactFrame = new JFrame("Contact");
    JPanel contactPanel = new JPanel();
    JLabel contactPeopleLabel = new JLabel("Sophie Limou and Andrew M. Taverner");
    JTextArea contactEmailLabel = new JTextArea("ferret@nih.gov");
    
    // Constructor ContactGUI
    public ContactGUI() {
        // Contact window stuff
        contactFrame.setLocationRelativeTo(null);
        contactFrame.setVisible(true);
        contactFrame.getContentPane().add(contactPanel);
        contactFrame.setResizable(true);
        contactFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contactPeopleLabel.setAlignmentX(CENTER_ALIGNMENT);
        contactPanel.add(contactPeopleLabel);
        contactEmailLabel.setAlignmentX(CENTER_ALIGNMENT);
        contactEmailLabel.setBackground(contactPanel.getBackground());
        contactPanel.add(contactEmailLabel);
        contactFrame.pack();
}
}
