/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.View;
// import classes
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe d'affichage du choix des populations d'intérêt pour la recherche d'informations.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class PopulationGUI extends GUI{
    private final Font usedFont = new Font("Serif", Font.BOLD, 20);
    // Races Selection Declarations
    public String[] asnCode = {"EAS","CDX","CHB","CHS","JPT","KHV","CHD"};
    public String[] eurCode = {"EUR","CEU","GBR","FIN","IBS","TSI"};
    public String[] afrCode = {"AFR","ACB","ASW","ESN","GWD","LWK","MSL","YRI"};
    public String[] amrCode = {"AMR","CLM","MXL","PEL","PUR"};
    public String[] sanCode = {"SAS","BEB","GIH","ITU","PJL","STU"};
    public String[] allracesString = {"ALL"};

    public JLabel afrLabel = new JLabel("Africans");
    public JLabel eurLabel = new JLabel("Europeans");
    public JLabel asnLabel = new JLabel("East Asians");
    public JLabel amrLabel = new JLabel("Americans");
    public JLabel sanLabel = new JLabel("South Asians");
    public JLabel allracesLabel = new JLabel("All Populations");

    public JCheckBox[] afrsub = new JCheckBox[8];
    public JCheckBox[] eursub = new JCheckBox[6];
    public JCheckBox[] amrsub = new JCheckBox[5];
    public JCheckBox[] sansub = new JCheckBox[6];
    public JCheckBox[] asnsub = new JCheckBox[7];
    public JCheckBox[] allracessub = new JCheckBox[1];

    //Getters
    public String[] getAsnCode() {
        return asnCode;
    }

    public String[] getEurCode() {
        return eurCode;
    }

    public String[] getAfrCode() {
        return afrCode;
    }

    public String[] getAmrCode() {
        return amrCode;
    }

    public String[] getSanCode() {
        return sanCode;
    }

    public String[] getAllracesString() {
        return allracesString;
    }

    public JCheckBox[] getAfrsub() {
        return afrsub;
    }

    public JCheckBox[] getEursub() {
        return eursub;
    }

    public JCheckBox[] getAmrsub() {
        return amrsub;
    }

    public JCheckBox[] getSansub() {
        return sansub;
    }

    public JCheckBox[] getAsnsub() {
        return asnsub;
    }

    public JCheckBox[] getAllracessub() {
        return allracessub;
    }
    
    // méthodes
    
    public void addPopulation(GUI g){
        allracessub[0] = new JCheckBox("ALL All Populations (n=2,504)");
        allracesLabel.setFont(usedFont);
        g.allracesPanel.add(allracesLabel);
        g.allracesPanel.add(allracessub[0]);
        
        g.kgPopulationPanel.add(afrPanel);
        afrPanel.setLayout(new GridLayout(9, 1));
        afrsub[0] = new JCheckBox("AFR All Africans (n=661)");
        afrsub[1] = new JCheckBox("ACB African Caribbean (n=96)");
        afrsub[2] = new JCheckBox("ASW African American (n=61)");
        afrsub[3] = new JCheckBox("ESN Esan (n=99)");
        afrsub[4] = new JCheckBox("GWD Gambian (n=113)");
        afrsub[5] = new JCheckBox("LWK Luhya (n=99)");
        afrsub[6] = new JCheckBox("MSL Mende (n=85)");
        afrsub[7] = new JCheckBox("YRI Yoruba (n=108)");
        afrLabel.setFont(usedFont);
        afrPanel.add(afrLabel);
        for (JCheckBox afrsub1 : afrsub) {
            afrPanel.add(afrsub1);
            if (afrsub1.getText().contains("n=0")) {
                afrsub1.setEnabled(false);
            }
        }

        g.kgPopulationPanel.add(amrPanel);
        amrPanel.setLayout(new GridLayout(9, 1));
        amrsub[0] = new JCheckBox("AMR All Americans (n=347)");
        amrsub[1] = new JCheckBox("CLM Colombian (n=94)");
        amrsub[2] = new JCheckBox("MXL Mexican American (n=64)");
        amrsub[3] = new JCheckBox("PEL Peruvian (n=85)");
        amrsub[4] = new JCheckBox("PUR Puerto Rican (n=104)");
        amrLabel.setFont(usedFont);
        amrPanel.add(amrLabel);
        for (int i = 0; i < amrsub.length; i++) {
            amrPanel.add(amrsub[i]);
            if (amrsub[i].getText().contains("n=0")) {
                amrsub[i].setEnabled(false);
            }
        }

        g.kgPopulationPanel.add(asnPanel);
        asnPanel.setLayout(new GridLayout(9, 1));
        asnsub[0] = new JCheckBox("EAS All East Asians (n=504)");
        asnsub[1] = new JCheckBox("CDX Dai Chinese (n=93)");
        asnsub[2] = new JCheckBox("CHB Han Chinese (n=103)");
        asnsub[3] = new JCheckBox("CHS Southern Han Chinese (n=105)");
        asnsub[4] = new JCheckBox("JPT Japanese (n=104)");
        asnsub[5] = new JCheckBox("KHV Kinh Vietnamese (n=99)");
        asnsub[6] = new JCheckBox("CHD Denver Chinese (n=0)");
        asnLabel.setFont(usedFont);
        asnPanel.add(asnLabel);
        for (int i = 0; i < asnsub.length; i++) {
            asnPanel.add(asnsub[i]);
            if (asnsub[i].getText().contains("n=0")) {
                asnsub[i].setEnabled(false);
            }
        }

        g.kgPopulationPanel.add(eurPanel);
        eurPanel.setLayout(new GridLayout(9, 1));
        eursub[0] = new JCheckBox("EUR All Europeans (n=503)");
        eursub[1] = new JCheckBox("CEU CEPH (n=99)");
        eursub[2] = new JCheckBox("GBR British (n=91)");
        eursub[3] = new JCheckBox("FIN Finnish (n=99)");
        eursub[4] = new JCheckBox("IBS Spanish (n=107)");
        eursub[5] = new JCheckBox("TSI Tuscan (n=107)");
        eurLabel.setFont(usedFont);
        eurPanel.add(eurLabel);
        for (int i = 0; i < eursub.length; i++) {
            eurPanel.add(eursub[i]);
            if (eursub[i].getText().contains("n=0")) {
                eursub[i].setEnabled(false);
            }
        }

        g.kgPopulationPanel.add(sanPanel);
        sanPanel.setLayout(new GridLayout(9, 1));
        sansub[0] = new JCheckBox("SAS All South Asians (n=489)");
        sansub[1] = new JCheckBox("BEB Bengali (n=86)");
        sansub[2] = new JCheckBox("GIH Gujarati Indian (n=103)");
        sansub[3] = new JCheckBox("ITU Indian Telugu (n=102)");
        sansub[4] = new JCheckBox("PJL Punjabi (n=96)");
        sansub[5] = new JCheckBox("STU Sri Lankan Tamil (n=102)");
        sanLabel.setFont(usedFont);
        sanPanel.add(sanLabel);
        for (int i = 0; i < sansub.length; i++) {
            sanPanel.add(sansub[i]);
            if (sansub[i].getText().contains("n=0")) {
                sansub[i].setEnabled(false);
            }
        }
        
        allracessub[0].setActionCommand("allracessub[0]");
        afrsub[0].setActionCommand("afrsub[0]");
        amrsub[0].setActionCommand("amrsub[0]");
        asnsub[0].setActionCommand("asnsub[0]");
        eursub[0].setActionCommand("eursub[0]");
        sansub[0].setActionCommand("sansub[0]");
    }
    
    public void checkBoxReset() {
        if (allracessub[0].isSelected()) {
            setAfrican(0, false);
            setEuropean(0, false);
            setAmerican(0, false);
            setSouthAsian(0, false);
            setAsian(0, false);
        } else {
            if (afrsub[0].isSelected()) {
                setAfrican(1, false);
            }
            if (eursub[0].isSelected()) {
                setEuropean(1, false);
            }
            if (amrsub[0].isSelected()) {
                setAmerican(1, false);
            }
            if (sansub[0].isSelected()) {
                setSouthAsian(1, false);
            }
            if (asnsub[0].isSelected()) {
                setAsian(1, false);
            }
        }
    }
    
      public void setAfrican(int start, boolean state) {
        for (int i = start; i < afrsub.length; i++) {
            if (!afrsub[i].getText().contains("n=0)")) {
                afrsub[i].setEnabled(state);
            }
            afrsub[i].setSelected(false);
            afrsub[i].updateUI();
        }
    }

    public void setAsian(int start, boolean state) {
        for (int i = start; i < asnsub.length; i++) {
            if (!asnsub[i].getText().contains("n=0)")) {
                asnsub[i].setEnabled(state);
            }
            asnsub[i].setSelected(false);
            asnPanel.updateUI();
        }
    }

    public void setEuropean(int start, boolean state) {
        for (int i = start; i < eursub.length; i++) {
            if (!eursub[i].getText().contains("n=0)")) {
                eursub[i].setEnabled(state);
            }
            eursub[i].setSelected(false);
            eursub[i].updateUI();
        }
    }

    public void setAmerican(int start, boolean state) {
        for (int i = start; i < amrsub.length; i++) {
            if (!amrsub[i].getText().contains("n=0)")) {
                amrsub[i].setEnabled(state);
            }
            amrsub[i].setSelected(false);
            amrPanel.updateUI();
        }
    }

    public void setSouthAsian(int start, boolean state) {
        for (int i = start; i < sansub.length; i++) {
            if (!sansub[i].getText().contains("n=0)")) {
                sansub[i].setEnabled(state);
            }
            sansub[i].setSelected(false);
            sansub[i].updateUI();
        }
    }

    public void setPhase1() {

        allracessub[0].setText("ALL All Populations (n=1,092)");

        sansub[0].setText("SAS All South Asians (n=0)");
        sansub[1].setText("BEB Bengali (n=0)");
        sansub[2].setText("GIH Gujarati Indian (n=0)");
        sansub[3].setText("ITU Indian Telugu (n=0)");
        sansub[4].setText("PJL Punjabi (n=0)");
        sansub[5].setText("STU Sri Lankan Tamil (n=0)");
        if (!allracessub[0].isSelected() && !sansub[0].isSelected()) {
            for (int i = 0; i < sansub.length; i++) {
                if (sansub[i].getText().contains("n=0)")) {
                    sansub[i].setEnabled(false);
                    if (sansub[i].isSelected()) {
                        sansub[i].setSelected(false);
                    }
                } else {
                    sansub[i].setEnabled(true);
                }
            }
        }

        eursub[0].setText("EUR All Europeans (n=379)");
        eursub[1].setText("CEU CEPH (n=85)");
        eursub[2].setText("GBR British (n=89)");
        eursub[3].setText("FIN Finnish (n=93)");
        eursub[4].setText("IBS Spanish (n=14)");
        eursub[5].setText("TSI Tuscan (n=98)");
        if (!allracessub[0].isSelected() && !eursub[0].isSelected()) {
            for (int i = 0; i < eursub.length; i++) {
                if (eursub[i].getText().contains("n=0)")) {
                    eursub[i].setEnabled(false);
                    if (eursub[i].isSelected()) {
                        eursub[i].setSelected(false);
                    }
                } else {
                    eursub[i].setEnabled(true);
                }
            }
        }

        asnsub[0].setText("EAS All East Asians (n=286)");
        asnsub[1].setText("CDX Dai Chinese (n=0)");
        asnsub[2].setText("CHB Han Chinese (n=97)");
        asnsub[3].setText("CHS Southern Han Chinese (n=100)");
        asnsub[4].setText("JPT Japanese (n=89)");
        asnsub[5].setText("KHV Kinh Vietnamese (n=0)");
        asnsub[6].setText("CHD Denver Chinese (n=0)");
        if (!allracessub[0].isSelected() && !asnsub[0].isSelected()) {
            for (int i = 0; i < asnsub.length; i++) {
                if (asnsub[i].getText().contains("n=0)")) {
                    asnsub[i].setEnabled(false);
                    if (asnsub[i].isSelected()) {
                        asnsub[i].setSelected(false);
                    }
                } else {
                    asnsub[i].setEnabled(true);
                }
            }
        }

        amrsub[0].setText("AMR All Americans (n=181)");
        amrsub[1].setText("CLM Colombian (n=60)");
        amrsub[2].setText("MXL Mexican American (n=66)");
        amrsub[3].setText("PEL Peruvian (n=0)");
        amrsub[4].setText("PUR Puerto Rican (n=55)");
        if (!allracessub[0].isSelected() && !amrsub[0].isSelected()) {
            for (int i = 0; i < amrsub.length; i++) {
                if (amrsub[i].getText().contains("n=0)")) {
                    amrsub[i].setEnabled(false);
                    if (amrsub[i].isSelected()) {
                        amrsub[i].setSelected(false);
                    }
                } else {
                    amrsub[i].setEnabled(true);
                }
            }
        }

        afrsub[0].setText("AFR All Africans (n=246)");
        afrsub[1].setText("ACB African Caribbean (n=0)");
        afrsub[2].setText("ASW African American (n=61)");
        afrsub[3].setText("ESN Esan (n=0)");
        afrsub[4].setText("GWD Gambian (n=0)");
        afrsub[5].setText("LWK Luhya (n=97)");
        afrsub[6].setText("MSL Mende (n=0)");
        afrsub[7].setText("YRI Yoruba (n=88)");
        if (!allracessub[0].isSelected() && !afrsub[0].isSelected()) {
            for (int i = 0; i < afrsub.length; i++) {
                if (afrsub[i].getText().contains("n=0)")) {
                    afrsub[i].setEnabled(false);
                    if (afrsub[i].isSelected()) {
                        afrsub[i].setSelected(false);
                    }
                } else {
                    afrsub[i].setEnabled(true);
                }
            }
        }
    }

    public void setPhase3() {
        allracessub[0].setText("ALL All Populations (n=2,504)");

        sansub[0].setText("SAS All South Asians (n=489)");
        sansub[1].setText("BEB Bengali (n=86)");
        sansub[2].setText("GIH Gujarati Indian (n=103)");
        sansub[3].setText("ITU Indian Telugu (n=102)");
        sansub[4].setText("PJL Punjabi (n=96)");
        sansub[5].setText("STU Sri Lankan Tamil (n=102)");
        if (!allracessub[0].isSelected() && !sansub[0].isSelected()) {
            for (int i = 0; i < sansub.length; i++) {
                if (sansub[i].getText().contains("n=0)")) {
                    sansub[i].setEnabled(false);
                    if (sansub[i].isSelected()) {
                        sansub[i].setSelected(false);
                    }
                } else {
                    sansub[i].setEnabled(true);
                }
            }
        }

        eursub[0].setText("EUR All Europeans (n=503)");
        eursub[1].setText("CEU CEPH (n=99)");
        eursub[2].setText("GBR British (n=91)");
        eursub[3].setText("FIN Finnish (n=99)");
        eursub[4].setText("IBS Spanish (n=107)");
        eursub[5].setText("TSI Tuscan (n=107)");
        if (!allracessub[0].isSelected() && !eursub[0].isSelected()) {
            for (int i = 0; i < eursub.length; i++) {
                if (eursub[i].getText().contains("n=0)")) {
                    eursub[i].setEnabled(false);
                    if (eursub[i].isSelected()) {
                        eursub[i].setSelected(false);
                    }
                } else {
                    eursub[i].setEnabled(true);
                }
            }
        }

        asnsub[0].setText("EAS All East Asians (n=504)");
        asnsub[1].setText("CDX Dai Chinese (n=93)");
        asnsub[2].setText("CHB Han Chinese (n=103)");
        asnsub[3].setText("CHS Southern Han Chinese (n=105)");
        asnsub[4].setText("JPT Japanese (n=104)");
        asnsub[5].setText("KHV Kinh Vietnamese (n=99)");
        asnsub[6].setText("CHD Denver Chinese (n=0)");
        if (!allracessub[0].isSelected() && !asnsub[0].isSelected()) {
            for (int i = 0; i < asnsub.length; i++) {
                if (asnsub[i].getText().contains("n=0)")) {
                    asnsub[i].setEnabled(false);
                    if (asnsub[i].isSelected()) {
                        asnsub[i].setSelected(false);
                    }
                } else {
                    asnsub[i].setEnabled(true);
                }
            }
        }

        amrsub[0].setText("AMR All Americans (n=347)");
        amrsub[1].setText("CLM Colombian (n=94)");
        amrsub[2].setText("MXL Mexican American (n=64)");
        amrsub[3].setText("PEL Peruvian (n=85)");
        amrsub[4].setText("PUR Puerto Rican (n=104)");
        if (!allracessub[0].isSelected() && !amrsub[0].isSelected()) {
            for (int i = 0; i < amrsub.length; i++) {
                if (amrsub[i].getText().contains("n=0)")) {
                    amrsub[i].setEnabled(false);
                    if (amrsub[i].isSelected()) {
                        amrsub[i].setSelected(false);
                    }
                } else {
                    amrsub[i].setEnabled(true);
                }
            }
        }

        afrsub[0].setText("AFR All Africans (n=661)");
        afrsub[1].setText("ACB African Caribbean (n=96)");
        afrsub[2].setText("ASW African American (n=61)");
        afrsub[3].setText("ESN Esan (n=99)");
        afrsub[4].setText("GWD Gambian (n=113)");
        afrsub[5].setText("LWK Luhya (n=99)");
        afrsub[6].setText("MSL Mende (n=85)");
        afrsub[7].setText("YRI Yoruba (n=108)");
        if (!allracessub[0].isSelected() && !afrsub[0].isSelected()) {
            for (int i = 0; i < afrsub.length; i++) {
                if (afrsub[i].getText().contains("n=0)")) {
                    afrsub[i].setEnabled(false);
                    if (afrsub[i].isSelected()) {
                        afrsub[i].setSelected(false);
                    }
                } else {
                    afrsub[i].setEnabled(true);
                }
            }
        }
    }
    
    //Listeners for view->controller
    public void populationListener(ActionListener a) {
        afrsub[0].addActionListener(a);
        amrsub[0].addActionListener(a);
        asnsub[0].addActionListener(a);
        eursub[0].addActionListener(a);
        sansub[0].addActionListener(a);
        allracessub[0].addActionListener(a);
    }
}

