package org.ecn.PAPPL6_2020.Controller;
// import classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.ecn.PAPPL6_2020.View.*;

/**
 * Classe faisant le lien entre l'affichage des paramètres et la gestion des paramètres dans le modèle.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/
public class SettingsCTRL implements ActionListener, ChangeListener, PropertyChangeListener{
    private SettingsGUI settingsV;
    private PopulationGUI pop;
    private RegionInteretGUI region;
    public SettingsCTRL (SettingsGUI settingsV,PopulationGUI pop,RegionInteretGUI region) {
        this.settingsV = settingsV;
        this.pop = pop;
        this.region = region;
        settingsV.settingsListener(this);
        settingsV.settingsChangeListener(this);
        settingsV.settingsPropertyChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Retriving the ActionCommand ie. the String associated with the button clicked
        String viewAction = e.getActionCommand();
        switch (viewAction) {
            // affichage pour l'ensemble des boutons possibles sur lesquels cliquer pour régler l'ensemble des paramètres d'exécution
            case "phase3Button":
                settingsV.getVersion38Button().setEnabled(true);
                break;
            case "phase1Button":
                settingsV.getVersion19Button().setSelected(true);
                settingsV.getVersion38Button().setEnabled(false);
                break;
            case "allFilesButton":
                if (!(settingsV.getVersion38Button().isSelected())) {
                    settingsV.getEspMAF().setEnabled(true);
                }
                break;
            case "freqFileButton":
                if (!(settingsV.getVersion38Button().isSelected())) {
                    settingsV.getEspMAF().setEnabled(true);
                }
                break;
            case "vcfFilesButton":
                if (settingsV.getEspMAF().isSelected()) {
                    settingsV.getEspMAF().setSelected(false);
                }
                settingsV.getEspMAF().setEnabled(false);
                break;
            case "version19Button":
                settingsV.getPhase1Button().setEnabled(true);
                if (!(settingsV.getVcfFileButton().isSelected())) {
                    settingsV.getEspMAF().setEnabled(true);
                }
                break;
            case "version38Button":
                settingsV.getPhase3Button().setSelected(true);
                settingsV.getPhase1Button().setEnabled(false);
                if (settingsV.getEspMAF().isSelected()) {
                    settingsV.getEspMAF().setSelected(false);
                }
                settingsV.getEspMAF().setEnabled(false);
                break;
            case "settingsCancel":
                settingsV.getPhase3Button().setSelected(settingsV.getCurrVersion()[0] == GUI.version1KG.THREE);
                settingsV.getPhase3Button().setEnabled(true);
                settingsV.getPhase1Button().setEnabled(settingsV.getDefaultHG()[0]);
                settingsV.getPhase1Button().setSelected(settingsV.getCurrVersion()[0] == GUI.version1KG.ONE);
                settingsV.getMafText().setValue(settingsV.getMafThreshold()[0]);
                settingsV.getMafSlider().setValue((int) (settingsV.getMafThreshold()[0] * 10000));


                if (settingsV.getCurrFileOut()[0] == GUI.fileOutput.VCF || !(settingsV.getDefaultHG()[0])) {
                    settingsV.getEspMAF().setEnabled(false);
                } else {
                    settingsV.getEspMAF().setEnabled(true);
                }
                settingsV.getEspMAF().setSelected(settingsV.getEspMAFBoolean()[0]);
                settingsV.getAllFilesButton().setSelected(settingsV.getCurrFileOut()[0] == GUI.fileOutput.ALL);
                settingsV.getFreqFileButton().setSelected(settingsV.getCurrFileOut()[0] == GUI.fileOutput.FRQ);
                settingsV.getVcfFileButton().setSelected(settingsV.getCurrFileOut()[0] == GUI.fileOutput.VCF);
                settingsV.getVcfFileButton().setEnabled(true);
                settingsV.getVersion19Button().setEnabled(true);
                settingsV.getVersion19Button().setSelected(settingsV.getDefaultHG()[0]);
                settingsV.getVersion38Button().setSelected(!(settingsV.getDefaultHG()[0]));
                settingsV.getVersion38Button().setEnabled(settingsV.getCurrVersion()[0] == GUI.version1KG.THREE);
                settingsV.getSettingsFrame().dispose();
                break;
            case "settingsOK":
                if (settingsV.getPhase3Button().isSelected()) {
                    settingsV.getCurrVersion()[0] = GUI.version1KG.THREE;
                    pop.setPhase3();
                } else if (settingsV.getPhase1Button().isSelected()) {
                    settingsV.getCurrVersion()[0] = GUI.version1KG.ONE;
                    pop.setPhase1();
                } else {
                    settingsV.getCurrVersion()[0] = GUI.version1KG.ZERO;
                }

                if (settingsV.getAllFilesButton().isSelected()) {
                    settingsV.getCurrFileOut()[0] = GUI.fileOutput.ALL;
                } else if (settingsV.getFreqFileButton().isSelected()) {
                    settingsV.getCurrFileOut()[0] = GUI.fileOutput.FRQ;
                } else if (settingsV.getVcfFileButton().isSelected()) {
                    settingsV.getCurrFileOut()[0] = GUI.fileOutput.VCF;
                }

                settingsV.getDefaultHG()[0] = settingsV.getVersion19Button().isSelected();
                settingsV.getEspMAFBoolean()[0] = settingsV.getEspMAF().isSelected();
                // Requesting either GRCh38 or VCF only prevents ESP button from working
                if ((settingsV.getVersion38Button().isSelected() || settingsV.getVcfFileButton().isSelected()) && (region.getSnpESPCheckBox().isSelected() || region.getGeneESPCheckBox().isSelected() || region.getChrESPCheckBox().isSelected())) {
                    region.getSnpESPCheckBox().setSelected(false);
                    region.getGeneESPCheckBox().setSelected(false);
                    region.getChrESPCheckBox().setSelected(false);
                }
                if (settingsV.getVersion38Button().isSelected()) {
                    region.getQuestionMarkLocusInput().setToolTipText("<html>Input hg38 human genome version coordinates in bp. <br><u> Example for CCR5:</u> Chromosome: 3 Start: 46370142 End: 46376206</html>");
                }
                if (settingsV.getVersion19Button().isSelected()) {
                    region.getQuestionMarkLocusInput().setToolTipText("<html>Input hg19 human genome version coordinates in bp. <br><u> Example for CCR5:</u> Chromosome: 3 Start: 46411633 End: 46417697</html>");
                }
                region.getSnpESPCheckBox().setEnabled(settingsV.getVersion19Button().isSelected() && !(settingsV.getVcfFileButton().isSelected()));
                region.getGeneESPCheckBox().setEnabled(settingsV.getVersion19Button().isSelected() && !(settingsV.getVcfFileButton().isSelected()));
                region.getChrESPCheckBox().setEnabled(settingsV.getVersion19Button().isSelected() && !(settingsV.getVcfFileButton().isSelected()));
                settingsV.getSettingsFrame().dispose();
                break;
            default:
                break;
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        double localMAFThreshold = settingsV.getMafSlider().getValue();
        settingsV.getMafText().setValue(localMAFThreshold/10000);
        }
    
    @Override
    // ajouter la valeur de la MAF threshold
    public void propertyChange(PropertyChangeEvent evt) {
        double localMAFThreshold = ((Number)settingsV.getMafText().getValue()).doubleValue();
				if (localMAFThreshold > 0.5){
					localMAFThreshold = 0.5;
					settingsV.getMafText().setValue(localMAFThreshold);
				}else if(localMAFThreshold < 0.0){
					localMAFThreshold = 0.0;
					settingsV.getMafText().setValue(localMAFThreshold);
				}
				settingsV.getMafSlider().setValue((int)(localMAFThreshold*10000));
    }        
}