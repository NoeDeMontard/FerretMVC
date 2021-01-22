/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.PAPPL6_2020.View;
// import classes
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 * Classe d'affichage des mises à jour.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class UpdateGUI extends ElementDuMenuGUI{
    // Update Window
    JFrame updateFrame = new JFrame("Update");
    JPanel updatePanel = new JPanel();
    JPanel updateBarHolder = new JPanel();
    JPanel updateButtonHolder = new JPanel();
    JProgressBar updateProgressBar = new JProgressBar();
    JLabel updateLabel = new JLabel("Checking for update...");
    JLabel updateDetailLabel = new JLabel("");
    JButton updateOK = new JButton("OK");
    
    //Getters
    public JFrame getUpdateFrame() {
        return updateFrame;
    }
    
    public JLabel getUpdateLabel() {
        return updateLabel;
    }
    
    public JPanel getUpdateBarHolder() {
        return updateBarHolder;
    }
    
    public JProgressBar getUpdateProgressBar() {
        return updateProgressBar;
    }
    
    public JPanel getUpdatePanel() {
        return updatePanel;
    }
    
    // Constructor UpdateGUI
    public UpdateGUI (){ 
        // Update window stuff
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setVisible(true); 
        updateFrame.setResizable(true);
        updateFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        updateFrame.getContentPane().add(updatePanel);
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        updatePanel.add(updateLabel);
        updatePanel.add(Box.createRigidArea(new Dimension(500, 0)));
        updateLabel.setAlignmentX(CENTER_ALIGNMENT);
        updateProgressBar.setIndeterminate(true);
        updateDetailLabel.setAlignmentX(CENTER_ALIGNMENT);
        updateBarHolder.add(updateProgressBar);
        updatePanel.add(updateBarHolder);
        updatePanel.add(updateButtonHolder);
        updateButtonHolder.setLayout(new BoxLayout(updateButtonHolder, BoxLayout.X_AXIS));
        updateButtonHolder.add(updateOK);
        updateFrame.pack();
    }
    
    //Listeners for view->controller
    public void updateListener(ActionListener a) {
        updateOK.addActionListener(a);
    }
}