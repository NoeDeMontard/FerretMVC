package org.ecn.Ferret.Controller;
// import classes
import org.ecn.Ferret.View.PopulationGUI;
import org.ecn.Ferret.View.GUI;
import org.ecn.Ferret.View.RegionInteretGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * Classe qui fait correspondre les infos de recherches entrées dans l'application Ferret à leur utilisation dans le modèle.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/
public class InfosRechercheCTRL implements ActionListener{
    private RegionInteretGUI region; // la région d'intérêt à étudier (entrée sous différentes formes possibles)
    private PopulationGUI pop; // l'ensemble de populations à étudier
    private GUI gui; // la fenêtre à ouvrir
    private static final String FILE_LOCATION = "File Location: "; // destination de fichier

    public InfosRechercheCTRL(RegionInteretGUI region,PopulationGUI pop,GUI gui) {
        this.region = region;
        this.pop = pop;
        this.gui = gui;
        region.regionInteretlListener(this);
        pop.populationListener(this);
        gui.mainPanelListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Retriving the ActionCommand ie. the String associated with the button clicked
        String viewAction = e.getActionCommand();
        switch (viewAction) {
            // on s'occupe en premier des différentes manières pour l'utilisateur d'entrer les infos sur la zone / le variant à étudier
            
            // sous forme de variant d'abord
            case "snpFileBrowseButton":
                region.getOpenFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = region.getOpenFileChooser().showOpenDialog(region);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File snpFile = region.getOpenFileChooser().getSelectedFile();
                    region.setFileNameAndPath(snpFile.getAbsolutePath());
                    String snpDisplayFileName;
                    if (region.getSnpFileNameAndPath().length() > 35 && region.getSnpFileNameAndPath().lastIndexOf('/') != -1) {

                        snpDisplayFileName = ".." + region.getSnpFileNameAndPath().substring(region.getSnpFileNameAndPath().lastIndexOf('/'));
                    } else if (region.getSnpFileNameAndPath().length() > 35 && region.getSnpFileNameAndPath().lastIndexOf('\\') != -1) {
                        snpDisplayFileName = ".." + region.getSnpFileNameAndPath().substring(region.getSnpFileNameAndPath().lastIndexOf('\\'));
                    } else {
                        snpDisplayFileName = region.getSnpFileNameAndPath();
                    }
                    region.getSnpFileLocation().setText(FILE_LOCATION + snpDisplayFileName);
                    region.getSnpFileClearButton().setEnabled(true);
                    region.getSnpTextField().setEnabled(false);
                    region.getSnpTextField().setText("");
                }
                break;
            case "snpFileClearButton":
                region.setSnpFileNameAndPath(null);
                region.getSnpFileLocation().setText("No file selected");
                region.getSnpFileClearButton().setEnabled(false);
                region.getSnpTextField().setEnabled(true);
                break;
            case "snpWindowCheckBox":
                if (region.getSnpWindowCheckBox().isSelected()) {
                    region.getSnpWindowField().setEnabled(true);
                } else {
                    region.getSnpWindowField().setEnabled(false);
                    region.getSnpWindowField().setText("");
                }
                break;
            
            // ensuite sous forme de gène
            case "geneFileBrowseButton":
                region.getOpenFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal2 = region.getOpenFileChooser().showOpenDialog(region);
                if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                    File geneFile = region.getOpenFileChooser().getSelectedFile();
                    region.setGeneFileNameAndPath(geneFile.getAbsolutePath());

                    String geneDisplayFileName;
                    if (region.getGeneFileNameAndPath().length() > 35 && region.getGeneFileNameAndPath().lastIndexOf('/') != -1) {
                        geneDisplayFileName = ".." + region.getGeneFileNameAndPath().substring(region.getGeneFileNameAndPath().lastIndexOf('/'));
                    } else if (region.getGeneFileNameAndPath().length() > 35 && region.getGeneFileNameAndPath().lastIndexOf('\\') != -1) {
                        geneDisplayFileName = ".." + region.getGeneFileNameAndPath().substring(region.getGeneFileNameAndPath().lastIndexOf('\\'));

                    } else {
                        geneDisplayFileName = region.getGeneFileNameAndPath();
                    }
                    region.getGeneFileLocation().setText(FILE_LOCATION + geneDisplayFileName);
                    region.getGeneFileClearButton().setEnabled(true);
                    region.getGeneNameField().setEnabled(false);
                    region.getGeneNameField().setText("");
                }
                break;
            case "geneFileClearButton":
                region.setGeneFileNameAndPath(null);
                region.getGeneFileLocation().setText("No file selected");
                region.getGeneFileClearButton().setEnabled(false);
                region.getGeneNameField().setEnabled(true);
                break;
            case "geneWindowCheckBox":
                if (region.getGeneWindowCheckBox().isSelected()) {
                    region.getGeneWindowField().setEnabled(true);
                } else {
                    region.getGeneWindowField().setEnabled(false);
                    region.getGeneWindowField().setText("");
                }
                break;
            
            // ici on s'occupe des populations à sélectionner
            case "allracessub[0]":
                pop.setAfrican(0, !(pop.getAllracessub()[0].isSelected()));
                pop.setAsian(0, !(pop.getAllracessub()[0].isSelected()));
                pop.setEuropean(0, !(pop.getAllracessub()[0].isSelected()));
                pop.setAmerican(0, !(pop.getAllracessub()[0].isSelected()));
                pop.setSouthAsian(0, !(pop.getAllracessub()[0].isSelected()));
                break;
            case "afrsub[0]":
                pop.setAfrican(1, !(pop.getAfrsub()[0].isSelected()));
                break;
            case "amrsub[0]":
                pop.setAmerican(1, !(pop.getAmrsub()[0].isSelected()));
                break;
            case "asnsub[0]":
                pop.setAsian(1, !(pop.getAsnsub()[0].isSelected()));
                break;
            case "eursub[0]":
                pop.setEuropean(1, !(pop.getEursub()[0].isSelected()));
                break;
            case "sansub[0]":
                pop.setSouthAsian(1, !(pop.getSansub()[0].isSelected()));
                break;
            
            // localisation des fichiers pour la sauvegarde
            case "browseButton":
                gui.getSaveFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                gui.getSaveFileChooser().setDialogTitle("Save As");
                int returnVal3 = gui.getSaveFileChooser().showSaveDialog(gui);
                if (returnVal3 == JFileChooser.APPROVE_OPTION) {
                    File file = gui.getSaveFileChooser().getSelectedFile();
                    gui.setFileNameAndPath(file.getAbsolutePath());
                    gui.getFileLocation().setText(FILE_LOCATION + gui.getFileNameAndPath());
                }
                break;
            default:
                break;
        }
    }
}