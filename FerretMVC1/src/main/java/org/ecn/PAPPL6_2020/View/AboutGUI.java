/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.PAPPL6_2020.View;
// import classes
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 * Classe d'affichage de l'onglet About du menu. Cela s'affiche après que l'utilisateur a cliqué sur "About Ferret" dans le menu "Help".
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class AboutGUI extends ElementDuMenuGUI{
    //About window
    JFrame aboutFrame = new JFrame("About");
    JPanel aboutPanel = new JPanel();
    JLabel ferretVersionLabel = new JLabel("Ferret v2.1.1");
    JLabel ferretDateLabel = new JLabel("February 2016");
    JTextArea ferretCitation = new JTextArea("Citation: Limou, S., Taverner, A., Nelson, G., "
                + "Winkler, C.A. Ferret: a user-friendly Java tool to extract data from the 1000 Genomes "
                + "Project. Presented at the annual meeting of the American Society of Human Genetics "
                + "(ASHG), 2015, Baltimore, MD, USA.", 4, 50);
    
    // Constructor AboutGUI
    public AboutGUI() {
        // About window stuff
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.setVisible(true);

        LinkLabel ferretWebLabelAbout = null;
          try{
                  ferretWebLabelAbout = new LinkLabel(new URI("http://limousophie35.github.io/Ferret/"),"http://limousophie35.github.io/Ferret/");
          }catch (URISyntaxException e){
                  e.printStackTrace();
          }

        aboutFrame.getContentPane().add(aboutPanel);
        aboutFrame.setResizable(true);
        aboutFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        aboutPanel.add(ferretVersionLabel);
        aboutPanel.add(ferretDateLabel);
        aboutPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        ferretWebLabelAbout.setBackgroundColor(aboutPanel.getBackground());
        ferretWebLabelAbout.init();
        ferretWebLabelAbout.setAlignmentX(LEFT_ALIGNMENT);
        ferretWebLabelAbout.setMaximumSize(ferretWebLabelAbout.getPreferredSize());
        aboutPanel.add(ferretWebLabelAbout);
        aboutPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        ferretCitation.setAlignmentX(LEFT_ALIGNMENT);
        ferretCitation.setLineWrap(true);
        ferretCitation.setWrapStyleWord(true);
        ferretCitation.setBackground(aboutPanel.getBackground());
        ferretCitation.setMaximumSize(ferretCitation.getPreferredSize());
        aboutPanel.add(ferretCitation);
        aboutFrame.pack();
}
}