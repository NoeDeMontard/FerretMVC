/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * Classe d'affichage pour l'entrée des informations sur les régions ciblées. Les entrées peuvent se faire en désignant un locus, un gène ou un variant.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class RegionInteretGUI extends GUI {
    // Locus Research Panel Declarations
    JLabel chrLabel = new JLabel(": Chromosome:");
    JLabel selectChrRegionLabel = new JLabel("Input Locus ");
    JLabel startLabel = new JLabel("Start:");
    JLabel endLabel = new JLabel("End:");
    String[] chrOptions = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
    public JComboBox<String> chrList = new JComboBox<>(chrOptions);
    public JTextField startPosTextField = new JTextField(8);
    public JTextField endPosTextField = new JTextField(8);
    public JCheckBox chrESPCheckBox = new JCheckBox("Output frequencies from the Exome Sequencing Project");
    JLabel questionMarkLocusInput = new JLabel(questionMark);
   
    // Gene Panel Declarations
    JLabel geneNameLabel = new JLabel("Input gene(s):");
    public JCheckBox geneESPCheckBox = new JCheckBox("Output frequencies from the Exome Sequencing Project");
    private JTextField geneWindowField = new JTextField(8);
    JCheckBox geneWindowCheckBox = new JCheckBox("Include borders of all selected gene(s) in a window of ");
    JLabel geneWindowBP = new JLabel(" bp");
    JPanel geneWindowPanel = new JPanel();
    JPanel chromosomeSelectPanel = new JPanel();
    JPanel chromosomeInputPanel = new JPanel();
    JPanel chromosomeESPOptionPanel = new JPanel();
    JPanel geneSelectPanel = new JPanel();
    JPanel geneInputPanel = new JPanel();
    JPanel geneSelectOptionsPanel = new JPanel();
    JPanel geneESPOptionPanel = new JPanel();
    public JTabbedPane inputSelect = new JTabbedPane();
    JLabel geneOR = new JLabel("OR");
    public String geneFileNameAndPath;
    JLabel geneFileLocation = new JLabel("No file selected");
    JLabel geneInputType = new JLabel("Input gene as: ");
    JButton geneFileBrowseButton = new JButton("Browse");
    JButton geneFileClearButton = new JButton("Clear");
    public JRadioButton geneNameRadioButton = new JRadioButton("Name");
    JRadioButton geneIDRadioButton = new JRadioButton("ID");
    ButtonGroup geneInputButtonGroup = new ButtonGroup();
    public JRadioButton geneNCBIRadioButton = new JRadioButton("NCBI");
    JRadioButton geneV37RadioButton = new JRadioButton("v37");
    ButtonGroup geneSourceButtonGroup = new ButtonGroup();
    JLabel questionMarkGeneInput = new JLabel(questionMark);
    JLabel questionMarkGeneFileInput = new JLabel(questionMark);

    // Variant Panel Declarations
    public JTextField snpTextField = new JTextField(8);
    public JTextField snpWindowField = new JTextField(8);
    public JTextField geneNameField = new JTextField(8);
    public JCheckBox snpWindowCheckBox = new JCheckBox("Include surrounding variant(s) in a window of ");
    JLabel snpWindowBP = new JLabel("bp");
    JLabel snpTextLabel = new JLabel("Input variant ID(s):");
    JPanel snpWindowPanel = new JPanel();
    JPanel snpSelectPanel = new JPanel();
    JPanel snpInputPanel = new JPanel();
    JPanel snpOptionsPanel = new JPanel();
    JPanel snpESPOptionPanel = new JPanel();
    JLabel snpOR = new JLabel("OR");
    JButton snpFileBrowseButton = new JButton("Browse");
    JButton snpFileClearButton = new JButton("Clear");
    JLabel snpFileLocation = new JLabel("No file selected");
    public JCheckBox snpESPCheckBox = new JCheckBox("Output frequencies from the Exome Sequencing Project");
    public String snpFileNameAndPath;
    public JRadioButton snpNCBIRadioButton = new JRadioButton("NCBI");
    JRadioButton snpV37RadioButton = new JRadioButton("v37");
    ButtonGroup snpSourceButtonGroup = new ButtonGroup();
    JLabel questionMarkSNPInput = new JLabel(questionMark);
    JLabel questionMarkSNPFileInput = new JLabel(questionMark);
    
    //Getters et Setters
    public JComboBox<String> getChrList() {
        return chrList;
    }

    public JTextField getStartPosTextField() {
        return startPosTextField;
    }

    public JTextField getEndPosTextField() {
        return endPosTextField;
    }

    public JCheckBox getChrESPCheckBox() {
        return chrESPCheckBox;
    }

    public JLabel getQuestionMarkLocusInput() {
        return questionMarkLocusInput;
    }

    public JTextField getSnpTextField() {
        return snpTextField;
    }

    public JTextField getSnpWindowField() {
        return snpWindowField;
    }

    public JTextField getGeneNameField() {
        return geneNameField;
    }

    public JCheckBox getSnpWindowCheckBox() {
        return snpWindowCheckBox;
    }

    public JButton getSnpFileClearButton() {
        return snpFileClearButton;
    }

    public JLabel getSnpFileLocation() {
        return snpFileLocation;
    }

    public JCheckBox getSnpESPCheckBox() {
        return snpESPCheckBox;
    }

    public String getSnpFileNameAndPath() {
        return snpFileNameAndPath;
    }

    public void setSnpFileNameAndPath(String snpFileNameAndPath) {
        this.snpFileNameAndPath = snpFileNameAndPath;
    }

    public JRadioButton getSnpNCBIRadioButton() {
        return snpNCBIRadioButton;
    }

    public JCheckBox getGeneESPCheckBox() {
        return geneESPCheckBox;
    }

    public JTextField getGeneWindowField() {
        return geneWindowField;
    }

    public JCheckBox getGeneWindowCheckBox() {
        return geneWindowCheckBox;
    }

    public JTabbedPane getInputSelect() {
        return inputSelect;
    }

    public String getGeneFileNameAndPath() {
        return geneFileNameAndPath;
    }

    public void setGeneFileNameAndPath(String geneFileNameAndPath) {
        this.geneFileNameAndPath = geneFileNameAndPath;
    }

    public JLabel getGeneFileLocation() {
        return geneFileLocation;
    }

    public JButton getGeneFileClearButton() {
        return geneFileClearButton;
    }

    public JRadioButton getGeneNameRadioButton() {
        return geneNameRadioButton;
    }

    public JRadioButton getGeneNCBIRadioButton() {
        return geneNCBIRadioButton;
    }
    
    public void addRegoinInteret(GUI g){
        g.bigPanel.add(inputSelect);

        // Method select tabs
        inputSelect.addTab("Locus", chromosomeSelectPanel);
        inputSelect.addTab("Gene", geneSelectPanel);
        inputSelect.addTab("Variant", snpSelectPanel);

        // Source selection panel for SNP
        snpSourceButtonGroup.add(snpNCBIRadioButton);
        snpSourceButtonGroup.add(snpV37RadioButton);
        snpNCBIRadioButton.setSelected(true);

        // SNP input panel
        snpInputPanel.setLayout(new BoxLayout(snpInputPanel, BoxLayout.X_AXIS));
        snpInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        snpInputPanel.add(snpTextLabel);
        snpInputPanel.add(questionMarkSNPInput);
        questionMarkSNPInput.setToolTipText("<html>Input the rs number without the letters 'rs'<br><u>Example:</u> 73885319 for rs73885319<br><br>"
                + "To input multiple variants at once, enter a list of variant IDs separated by a comma, or input a file.<br><u>Example:</u> 73885319, 2395029 for rs73885319 and rs2395029</html>");
        snpInputPanel.add(snpTextField);
        snpTextField.setMaximumSize(snpTextField.getPreferredSize());
        snpInputPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        snpInputPanel.add(snpOR);
        snpInputPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        //Variant tab browse
        snpInputPanel.add(snpFileBrowseButton);
        snpFileBrowseButton.setPreferredSize(new Dimension(100, 30));
        snpInputPanel.add(snpFileLocation);
        snpInputPanel.add(snpFileClearButton);
        snpInputPanel.add(questionMarkSNPFileInput);
        questionMarkSNPFileInput.setToolTipText("<html>You can load a file in any of the following formats: <br> - a comma-delimited .csv file (example: variant.csv containing 73885319, 2395029) <br>"
                + " - a tab-delimited .tab or .tsv file (example: variant.tab containing 73885319 &nbsp&nbsp&nbsp&nbsp 2395029) <br>"
                + " - a space-delimited .txt file (example: variant.txt containing 73885319 2395029)"
                + "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>");
        snpFileClearButton.setEnabled(false);
        snpFileClearButton.setPreferredSize(new Dimension(100, 30));
        snpWindowPanel.setLayout(new BoxLayout(snpWindowPanel, BoxLayout.X_AXIS));
        snpWindowPanel.add(snpWindowCheckBox);
        snpWindowPanel.add(snpWindowField);
        snpWindowField.setEnabled(false);
        snpWindowPanel.add(snpWindowBP);
        snpWindowField.setMaximumSize(snpWindowField.getPreferredSize());
        snpWindowPanel.setMaximumSize(snpWindowPanel.getPreferredSize());
        snpESPOptionPanel.setLayout(new BoxLayout(snpESPOptionPanel, BoxLayout.X_AXIS));
        snpESPOptionPanel.add(snpESPCheckBox);
        snpESPOptionPanel.setMaximumSize(snpESPOptionPanel.getPreferredSize());

        // SNP selection method
        snpSelectPanel.setLayout(new BoxLayout(snpSelectPanel, BoxLayout.Y_AXIS));
        snpSelectPanel.add(snpInputPanel);
        snpSelectPanel.add(snpWindowPanel);
        snpSelectPanel.add(snpESPOptionPanel);

        // Gene selection method ----------------------------------------------------------
        // Create the button groups
        geneSourceButtonGroup.add(geneNCBIRadioButton);
        geneSourceButtonGroup.add(geneV37RadioButton);
        geneNCBIRadioButton.setSelected(true);
        geneInputButtonGroup.add(geneNameRadioButton);
        geneInputButtonGroup.add(geneIDRadioButton);

        // Create the gene input panel
        geneInputPanel.setLayout(new BoxLayout(geneInputPanel, BoxLayout.X_AXIS));
        geneInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        geneInputPanel.add(geneNameLabel);
        geneInputPanel.add(questionMarkGeneInput);
        questionMarkGeneInput.setToolTipText("<html>Input a gene name or a gene ID, and check the corresponding box (Name vs ID) <br>"
                + "<u>Example:</u> CCR5 for gene name or 1234 for gene ID <br><br>"
                + "To input multiple genes at once, enter a list of genes separated by a comma or input a file. <br>"
                + "<u>Example:</u> CCR5, HCP5 for gene name input or 1234, 10866 for gene ID input.</html>");
        geneInputPanel.add(geneNameField);
        geneNameField.setMaximumSize(geneNameField.getPreferredSize());
        geneInputPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        geneInputPanel.add(geneOR);
        geneInputPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        geneInputPanel.add(geneFileBrowseButton);
        geneFileBrowseButton.setPreferredSize(new Dimension(100, 30));
        geneInputPanel.add(geneFileLocation);
        geneFileClearButton.setPreferredSize(new Dimension(100, 30));
        geneInputPanel.add(geneFileClearButton);
        geneInputPanel.add(questionMarkGeneFileInput);
        questionMarkGeneFileInput.setToolTipText("<html>You can load a file in any of the following formats for either gene names or gene IDs: <br> "
                + " - a comma-delimited .csv file (example: gene.csv containing CCR5, HCP5) <br>"
                + " - a tab-delimited .tab or .tsv file (example: gene.tab containing CCR5 &nbsp&nbsp&nbsp&nbsp HCP5) <br>"
                + " - a space-delimited .txt file (example: gene.txt containing CCR5 HCP5)"
                + "<br><br> A carriage return can also be used as a delimiter for all above file types.</html>");
        geneFileClearButton.setEnabled(false);

        //the option gene borders
        geneWindowPanel.setLayout(new BoxLayout(geneWindowPanel, BoxLayout.X_AXIS));
        geneWindowPanel.add(geneWindowCheckBox);
        geneWindowPanel.add(geneWindowField);
        geneWindowField.setEnabled(false);
        geneWindowPanel.add(geneWindowBP);
        geneWindowField.setMaximumSize(geneWindowField.getPreferredSize());
        geneWindowPanel.setMaximumSize(geneWindowPanel.getPreferredSize());
        // Create the NCBI/Frozen look-up panel
        geneSelectOptionsPanel.setLayout(new BoxLayout(geneSelectOptionsPanel, BoxLayout.X_AXIS));
        geneSelectOptionsPanel.add(geneInputType);
        geneSelectOptionsPanel.add(geneNameRadioButton);
        geneSelectOptionsPanel.add(geneIDRadioButton);
        geneNameRadioButton.setSelected(true);
        geneESPOptionPanel.setLayout(new BoxLayout(geneESPOptionPanel, BoxLayout.X_AXIS));
        geneESPOptionPanel.add(geneESPCheckBox);

        // Finally add the panels to the main panel
        geneSelectPanel.add(Box.createVerticalGlue());
        geneSelectPanel.setLayout(new BoxLayout(geneSelectPanel, BoxLayout.Y_AXIS));
        geneSelectPanel.add(geneInputPanel);
        geneSelectPanel.add(geneSelectOptionsPanel);
        geneSelectPanel.add(geneWindowPanel);
        geneSelectPanel.add(geneESPOptionPanel);
        geneSelectPanel.add(Box.createVerticalGlue());
        // end gene selection method ----------------------------------------------------------

        // Chromosome region selection method
        chromosomeInputPanel.setLayout(new BoxLayout(chromosomeInputPanel, BoxLayout.X_AXIS));
        chromosomeInputPanel.add(selectChrRegionLabel);
        chromosomeInputPanel.add(questionMarkLocusInput);
        questionMarkLocusInput.setToolTipText("<html>Input hg19 human genome version coordinates in bp. <br><u> Example for CCR5:</u> Chromosome: 3 Start: 46411633 End: 46417697</html>");
        chromosomeInputPanel.add(chrLabel);
        chromosomeInputPanel.add(chrList);
        chromosomeInputPanel.add(startLabel);
        chromosomeInputPanel.add(startPosTextField);
        startPosTextField.setMaximumSize(startPosTextField.getPreferredSize());
        chromosomeInputPanel.add(endLabel);
        chromosomeInputPanel.add(endPosTextField);
        endPosTextField.setMaximumSize(endPosTextField.getPreferredSize());

        chromosomeESPOptionPanel.add(chrESPCheckBox);

        chromosomeSelectPanel.setLayout(new BoxLayout(chromosomeSelectPanel, BoxLayout.Y_AXIS));
        chromosomeSelectPanel.add(Box.createVerticalGlue());
        chromosomeSelectPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80));
        chromosomeSelectPanel.add(chromosomeInputPanel);
        chromosomeSelectPanel.add(chromosomeESPOptionPanel);
        chromosomeSelectPanel.add(Box.createVerticalGlue());
        // end chromosome selection panel (now called Locus) ----------------------------------

        inputSelect.setMaximumSize(inputSelect.getPreferredSize());
        
        // setActionCommand
        snpFileBrowseButton.setActionCommand("snpFileBrowseButton");
        snpFileClearButton.setActionCommand("snpFileClearButton");
        snpWindowCheckBox.setActionCommand("snpWindowCheckBox");
        geneFileBrowseButton.setActionCommand("geneFileBrowseButton");
        geneFileClearButton.setActionCommand("geneFileClearButton");
        geneWindowCheckBox.setActionCommand("geneWindowCheckBox");
    }
    
    //Listeners for view->controller
    public void regionInteretlListener(ActionListener a) {
        snpFileBrowseButton.addActionListener(a);
        snpFileClearButton.addActionListener(a);
        snpWindowCheckBox.addActionListener(a);
        geneFileBrowseButton.addActionListener(a);
        geneFileClearButton.addActionListener(a);
        geneWindowCheckBox.addActionListener(a);
    }
}
