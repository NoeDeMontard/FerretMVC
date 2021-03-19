/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.ChangeListener;
/**
 * Classe d'affichage des paramètres de recherche pour l'application Ferret.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class SettingsGUI extends ElementDuMenuGUI{
    private final Font usedFont = new Font("SansSerif", Font.BOLD, 16);
    //Settings pane:
    JFrame settingsFrame = new JFrame("Settings");
    JPanel settingsPanel = new JPanel();
    JTextField vcfURLText = new JTextField();
    JTextField fileNomenclatureText = new JTextField();
    JSlider mafSlider = new JSlider(0, 5000, 0);
  
    JRadioButton phase3Button = new JRadioButton("Phase 3 (2,504 individuals) [default]");
    JRadioButton phase1Button = new JRadioButton("Phase 1 (1,092 individuals)");
    ButtonGroup vcfRadioButtons = new ButtonGroup();
    JRadioButton allFilesButton = new JRadioButton("Allele Frequencies (.frq) + Plink/HaploView (.map/.ped/.info) [default]");
    JRadioButton freqFileButton = new JRadioButton("Allele Frequencies (.frq) only");
    JRadioButton vcfFileButton = new JRadioButton("VCF file only");
    ButtonGroup fileOutputButtons = new ButtonGroup();
    JRadioButton version19Button = new JRadioButton("hg19/GRCh37 [default]");
    JRadioButton version38Button = new JRadioButton("hg38/GRCh38 [only available for Phase 3 data]");
    ButtonGroup hgVersionButtons = new ButtonGroup();
    JLabel vcfVersionLabel = new JLabel("1000 Genomes Version");
    JLabel vcfURLLabel = new JLabel("OR specify the VCF URL");
    JLabel vcfNomenclatureLabel = new JLabel("AND file nomenclature for chr $");
    JLabel mafOptionLabel = new JLabel("Minor Allele Frequency (MAF)");
    JLabel mafThresholdLabel = new JLabel("MAF Threshold: ");
    JLabel hgVersionLabel = new JLabel("Human Genome Version");
    JLabel filesLabel = new JLabel("Output Files");
    JButton settingsOK = new JButton("OK");
    JButton settingsCancel = new JButton("Cancel");
    JPanel mafPanel = new JPanel();
    JPanel mafESPPanel = new JPanel();
    JPanel vcfVersionPanel = new JPanel();
    JPanel settingsButtonPanel = new JPanel();
    JCheckBox espMAF = new JCheckBox("Apply MAF threshold to the Exome Sequencing Project");
    
    //Constants
    final JFormattedTextField mafText = new JFormattedTextField(mafFormat);
    final JFormattedTextField mafTextMax = new JFormattedTextField(mafFormat);
    
    //Getters
    public JFormattedTextField getMafText() {
        return mafText;
    }

   public JFormattedTextField getMafTextMax() {
        return mafTextMax;
    }
    
    
    public JFrame getSettingsFrame() {
        return settingsFrame;
    }

    public JSlider getMafSlider() {
        return mafSlider;
    }

    public JRadioButton getPhase3Button() {
        return phase3Button;
    }

    public JRadioButton getPhase1Button() {
        return phase1Button;
    }

    public JRadioButton getAllFilesButton() {
        return allFilesButton;
    }

    public JRadioButton getFreqFileButton() {
        return freqFileButton;
    }

    public JRadioButton getVcfFileButton() {
        return vcfFileButton;
    }

    public JRadioButton getVersion19Button() {
        return version19Button;
    }

    public JRadioButton getVersion38Button() {
        return version38Button;
    }

    public JCheckBox getEspMAF() {
        return espMAF;
    }
    
    // Constructor SettingsGUI
    public SettingsGUI() {
        // Settings window stuff
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
        settingsFrame.getContentPane().add(settingsPanel);
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        settingsFrame.setResizable(true);
        settingsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        vcfVersionLabel.setFont(usedFont);
        settingsPanel.add(vcfVersionLabel);
        vcfRadioButtons.add(phase3Button);
        vcfRadioButtons.add(phase1Button);
        settingsPanel.add(phase3Button);
        settingsPanel.add(phase1Button);
        phase3Button.setSelected(true);

        settingsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mafPanel.setAlignmentX(LEFT_ALIGNMENT);
        settingsPanel.add(mafOptionLabel);
        mafOptionLabel.setFont(usedFont);
        settingsPanel.add(mafPanel);
        mafPanel.setLayout(new BoxLayout(mafPanel, BoxLayout.X_AXIS));
        mafPanel.add(mafThresholdLabel);
        mafText.setColumns(5);
        mafText.setMaximumSize(mafText.getPreferredSize());
        mafText.setValue(Double.valueOf(0));        
        mafPanel.add(mafText);

        mafSlider.setMajorTickSpacing(1000);
        mafSlider.setPaintTicks(true);
        Hashtable<Integer,JLabel> labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0.0"));
        labelTable.put(5000, new JLabel("0.5"));
        mafSlider.setLabelTable(labelTable);
        mafSlider.setValue(0);
        mafSlider.setPaintLabels(true);
        mafPanel.add(mafSlider);

        Hashtable<Integer,JLabel> labelTable2 = new Hashtable();
        labelTable2.put(0, new JLabel("0.0"));
        labelTable2.put(5000, new JLabel("0.5"));

        mafPanel.add(questionMarkMAFThreshold);
        questionMarkMAFThreshold.setToolTipText("<html>The MAF threshold is applied to the selected 1000 Genomes populations<br>"
                + "<u>Example:</u> For a MAF threshold of 0.05 (i.e. 5%), Ferret will only output variants with <br> a frequency >= 5% in the "
                + "selected populations.</html>");
        mafPanel.add(Box.createHorizontalGlue());
        mafESPPanel.setLayout(new BoxLayout(mafESPPanel, BoxLayout.X_AXIS));
        mafESPPanel.setAlignmentX(LEFT_ALIGNMENT);
        mafESPPanel.add(espMAF);
        mafESPPanel.add(questionMarkESPMAF);
        settingsPanel.add(mafESPPanel);
        questionMarkESPMAF.setToolTipText("<html> If checked, the MAF threshold is also applied to the Exome Sequencing Project populations."
                + "<br><u>Example:</u> For a MAF threshold of 0.05 (i.e. 5%), Ferret will only output variants with a frequency >= 5% <br> "
                + "in either the selected 1000 Genomes populations, or the European American population from the <br>"
                + "Exome Sequencing Project, or the African American population from the Exome Sequencing Project. </html>");
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        settingsPanel.add(filesLabel);
        filesLabel.setFont(usedFont);
        fileOutputButtons.add(allFilesButton);
        fileOutputButtons.add(freqFileButton);
        fileOutputButtons.add(vcfFileButton);
        settingsPanel.add(allFilesButton);
        settingsPanel.add(freqFileButton);
        settingsPanel.add(vcfFileButton);

        mafText.setName("mafText");
        mafTextMax.setName("mafTextMax");
        
        // setActionCommand
        phase3Button.setActionCommand("phase3Button");
        phase1Button.setActionCommand("phase1Button");
        version19Button.setActionCommand("version19Button");
        version38Button.setActionCommand("version38Button");
        settingsCancel.setActionCommand("settingsCancel");
        settingsOK.setActionCommand("settingsOK");
        allFilesButton.setActionCommand("allFilesButton");
        freqFileButton.setActionCommand("freqFileButton");
        vcfFileButton.setActionCommand("vcfFilesButton");

        allFilesButton.setSelected(true);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        settingsPanel.add(hgVersionLabel);
        hgVersionLabel.setFont(usedFont);

        hgVersionButtons.add(version19Button);
        hgVersionButtons.add(version38Button);
        settingsPanel.add(version19Button);
        settingsPanel.add(version38Button);

        version19Button.setSelected(true);
        settingsButtonPanel.setAlignmentX(LEFT_ALIGNMENT);
        settingsButtonPanel.setLayout(new BoxLayout(settingsButtonPanel, BoxLayout.X_AXIS));
        settingsPanel.add(settingsButtonPanel);
        settingsButtonPanel.add(Box.createHorizontalGlue());
        settingsButtonPanel.add(settingsCancel);
        settingsButtonPanel.add(settingsOK);
        settingsFrame.pack();
    }
    
    //Listeners for view->controller
    public void settingsListener(ActionListener a) {
        phase3Button.addActionListener(a);
        phase1Button.addActionListener(a);
        allFilesButton.addActionListener(a);
        freqFileButton.addActionListener(a);
        vcfFileButton.addActionListener(a);
        version19Button.addActionListener(a);
        version38Button.addActionListener(a);
        settingsCancel.addActionListener(a);
        settingsOK.addActionListener(a);
    }

    public void settingsChangeListener(ChangeListener c) {
        mafSlider.addChangeListener(c);
    }

    public void settingsPropertyChangeListener(PropertyChangeListener p) {
        mafText.addPropertyChangeListener(p);
        mafTextMax.addPropertyChangeListener(p);
    }
}