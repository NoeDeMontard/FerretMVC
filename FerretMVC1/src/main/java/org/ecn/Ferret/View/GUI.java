/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.NumberFormat;
import javax.swing.*;

/**
 * Super-classe GUI, les autres classes vont hériter de celle-ci.
 * Elle regroupe l'affichage de la fenêtre principale et plusieurs paramètres d'affichage.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class GUI extends JFrame implements ActionListener {
    //Declarations
    private String fileNameAndPath;
    URL questionMarkURL = getClass().getResource("questionMark25.png");
    ImageIcon questionMark = new ImageIcon(questionMarkURL);
    // DONE: (Assurez-vous que le fichier questionMark.png se trouve bien dans le fichier du code)
    //Toujours un problème avec cette ImageIcon, elle bloque l'éxecution du test main
    // questionMarkURL semble null. J'ai tenté de déplacer l'image, de mettre un
    // chemin plus complet ou d'utiliser ImageIcon("questionMark25.png"); mais 
    // aucune de ses solutions n'a marché (la dernière ne met pas de message d'
    // erreur mais l'icone ne s'affiche pas
    JLabel fileLocation = new JLabel("File location: None Selected");
    static JFrame snpFerret = new JFrame("Ferret v2.1.2");
    
    //------------------------ 
    JPanel bigPanel = new JPanel();
    JPanel kgPopulationPanel = new JPanel();
    JPanel afrPanel = new JPanel();
    JPanel eurPanel = new JPanel();
    JPanel asnPanel = new JPanel();
    JPanel amrPanel = new JPanel();
    JPanel sanPanel = new JPanel();
    JPanel allracesPanel = new JPanel();
    JButton browseButton = new JButton("Browse");
    JFileChooser openFileChooser = new JFileChooser();
    JScrollPane scrollBigPanel = new JScrollPane(bigPanel);
    static JFrame progressWindow = new JFrame("Working...");
    static JProgressBar progressBar = new JProgressBar(0, 100);
    JLabel progressText = new JLabel("Initializing...");
    static Integer variantCounterResult;
    JPanel fileChoosePanel = new JPanel();

    // MenuBar
    JMenuBar ferretMenuBar = new JMenuBar();
    JLabel questionMarkMAFThreshold = new JLabel(questionMark);
    JLabel questionMarkESPMAF = new JLabel(questionMark);

    //Others
    NumberFormat mafFormat = NumberFormat.getNumberInstance();

    //Constants
    static final version1KG[] currVersion = {version1KG.THREE};
    static final fileOutput[] currFileOut = {fileOutput.ALL};
    static final Boolean[] defaultHG = {true};
    static final double[] mafThreshold = {0.0};
    static final double[] mafThresholdMax = {0.5};
    static final Boolean[] espMAFBoolean = {false};
    static final Boolean[] checkedForUpdate = {false};
    final JFileChooser saveFileChooser = new JFileChooser();

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Enumerations
    public enum version1KG {
        ZERO, ONE, THREE
    }

    public enum fileOutput {
        ALL, FRQ, VCF
    }

    //Getters et Setters
    public version1KG[] getCurrVersion() {
        return currVersion;
    }

    public fileOutput[] getCurrFileOut() {
        return currFileOut;
    }

    public Boolean[] getDefaultHG() {
        return defaultHG;
    }

    public double[] getMafThreshold() {
        return mafThreshold;
    }

    public double[] getMafThresholdMax() {
        return mafThresholdMax;
    }

    public Boolean[] getEspMAFBoolean() {
        return espMAFBoolean;
    }

    public Boolean[] getCheckedForUpdate() {
        return checkedForUpdate;
    }

    public String getFileNameAndPath() {
        return fileNameAndPath;
    }

    public JLabel getFileLocation() {
        return fileLocation;
    }

    public static JFrame getSnpFerret() {
        return snpFerret;
    }

    public JPanel getAfrPanel() {
        return afrPanel;
    }

    public JPanel getEurPanel() {
        return eurPanel;
    }

    public JPanel getAsnPanel() {
        return asnPanel;
    }

    public JPanel getAmrPanel() {
        return amrPanel;
    }

    public JPanel getSanPanel() {
        return sanPanel;
    }

    public JPanel getAllracesPanel() {
        return allracesPanel;
    }

    public JFileChooser getOpenFileChooser() {
        return openFileChooser;
    }

    public static JFrame getProgressWindow() {
        return progressWindow;
    }

    public static JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getProgressText() {
        return progressText;
    }
    
    public JFileChooser getSaveFileChooser() {
        return saveFileChooser;
    }
    
    public void setFileNameAndPath(String fileNameAndPath) {
        this.fileNameAndPath = fileNameAndPath;
    }
    
    public void afficher() {
        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(20000);

        // Progress window stuff
        progressWindow.setResizable(true);
        progressWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JPanel progressPanel = new JPanel();
        progressWindow.getContentPane().add(progressPanel);
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        progressPanel.add(Box.createVerticalGlue());
        progressPanel.add(progressText);
        progressText.setAlignmentX(Container.CENTER_ALIGNMENT);
        progressPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        progressPanel.add(progressBar);
        progressPanel.add(Box.createVerticalGlue());
        progressWindow.pack();
        snpFerret.setJMenuBar(ferretMenuBar);
        snpFerret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snpFerret.setResizable(true);
        snpFerret.getContentPane().add(scrollBigPanel);
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));

        bigPanel.add(kgPopulationPanel);

        kgPopulationPanel.setLayout(new GridLayout(2, 3));
        kgPopulationPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 20));
        kgPopulationPanel.add(allracesPanel);
        allracesPanel.setLayout(new GridLayout(9, 1));

        bigPanel.add(fileChoosePanel);
        fileChoosePanel.add(browseButton);
        browseButton.setPreferredSize(new Dimension(100, 30));
        fileChoosePanel.add(fileLocation);
        bigPanel.add(Box.createVerticalGlue());
        snpFerret.pack();
        snpFerret.setVisible(true);
        
        // setActionCommand
        browseButton.addActionListener(this);
        browseButton.setActionCommand("browseButton");
    }

    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container) component, enable);
            }
        }
    }
   
    //Listeners for view->controller
    public void mainPanelListener(ActionListener a) {
        browseButton.addActionListener(a);
    }
}
