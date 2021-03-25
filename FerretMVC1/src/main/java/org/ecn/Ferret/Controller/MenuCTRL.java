// NB : cette classe est considérée comme provisoirement terminée

package org.ecn.Ferret.Controller;
// import classes
import org.ecn.Ferret.View.PopulationGUI;
import org.ecn.Ferret.View.MenuHelpGUI;
import org.ecn.Ferret.View.AboutGUI;
import org.ecn.Ferret.View.ContactGUI;
import org.ecn.Ferret.View.UpdateGUI;
import org.ecn.Ferret.View.MenuFerretGUI;
import org.ecn.Ferret.View.SettingsGUI;
import org.ecn.Ferret.View.RegionInteretGUI;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.ecn.Ferret.Controller.UpdateCTRL;

/**
 * Classe de lien entre l'affichage du menu et les actions à effectuer.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG & Imane SALMI & Imane TAHIRI
*/
public class MenuCTRL implements ActionListener {

    public MenuFerretGUI mfg; // la partie "Ferret" du menu
    private MenuHelpGUI mhg; // la partie "Help" du menu
    public static PopulationGUI pop; // la population d'étude
    public static RegionInteretGUI region; // la région d'intérêt
    
    public MenuCTRL (MenuFerretGUI mfg,MenuHelpGUI mhg,PopulationGUI pop,RegionInteretGUI region) {
        this.mfg = mfg;
        this.pop = pop;
        this.region = region;
        mfg.menuFerretListener(this);
        mhg.menuHelpListener(this);
    }

    public MenuCTRL() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Retriving the ActionCommand ie. the String associated with the button clicked
        try{String viewAction = e.getActionCommand();
        switch (viewAction) {
            // les différents cas à afficher au sein du menu (regarder l'exécutable pour bien se représenter)
            case "settingsMenuItem":
                mfg.settings = new SettingsGUI();
                new SettingsCTRL(mfg.settings,MenuCTRL.pop,MenuCTRL.region);
                break;
            case "exitMenuItem":
                System.exit(0);
                break;
            case "updateMenuItem":
                mfg.update = new UpdateGUI();
                new UpdateCTRL(mfg.update);
                break;
            case "aboutMenuItem":
                mhg.about = new AboutGUI();
          
                break;
            case "faqMenuItem":
                try {
                    Desktop.getDesktop().browse(new URI("http://limousophie35.github.io/Ferret/#faq"));
                } catch (IOException | URISyntaxException exception) {
                }
                break;
            case "contactMenuItem":
                mhg.contact = new ContactGUI();
           
                break;
            default:
                break;
        }
        }catch(Exception ex){
        }
}
}
