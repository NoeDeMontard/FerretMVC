package org.ecn.Ferret.Controller;
// import classes
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import org.ecn.Ferret.View.LinkLabel;
import org.ecn.Ferret.View.*;
import org.ecn.Ferret.Model.*;

/**
 * Classe faisant le lien entre la demande de mise à jour via la vue et le téléchargement de la mise à jour via le modèle.
  * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/
public class UpdateCTRL  implements ActionListener{
    private UpdateGUI upV; // ce que fait l'utilisateur pour demander ou non des màj

    public UpdateCTRL(UpdateGUI upV) {
        this.upV = upV;
        upV.updateListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(upV.getCheckedForUpdate()[0])) {
            upV.getCheckedForUpdate()[0] = true;
            final UpdateM updateWorker = new UpdateM();
            updateWorker.addPropertyChangeListener((PropertyChangeEvent arg0) -> {
                if (arg0.getPropertyName().equals("state")) {
                    if ((SwingWorker.StateValue) arg0.getNewValue() == SwingWorker.StateValue.DONE) {
                        
                        try {
                            updateWorker.doInBackground();
                        } catch (Exception ex) {
                            Logger.getLogger(UpdateCTRL.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String updateReason = updateWorker.updateStatus();
                        Boolean urgentUpdate = updateWorker.urgentUpdate();
                        Boolean needUpdate = updateWorker.needUpdate();
                        
                        if (urgentUpdate || needUpdate) {//If there is an update available
                            upV.getUpdateLabel().setText(updateReason);
                            upV.getUpdateBarHolder().remove(upV.getUpdateProgressBar());
                            LinkLabel ferretUpdate = null;
                            try {
                                ferretUpdate = new LinkLabel(new URI("http://limousophie35.github.io/Ferret/"), "http://limousophie35.github.io/Ferret/");
                            } catch (URISyntaxException ex) {
                            }
                            JLabel updateFerretLabel = new JLabel("Please update Ferret at:");
                            upV.getUpdateBarHolder().add(updateFerretLabel);
                            upV.getUpdateBarHolder().repaint();
                            updateFerretLabel.setText("");
                            updateFerretLabel.setText("Please update Ferret at:");
                            if (ferretUpdate != null) {
                                ferretUpdate.setBackgroundColor(upV.getUpdatePanel().getBackground());
                                ferretUpdate.init();
                                ferretUpdate.setAlignmentX(LEFT_ALIGNMENT);
                                ferretUpdate.setMaximumSize(ferretUpdate.getPreferredSize());
                                upV.getUpdateBarHolder().add(ferretUpdate);
                            }
                        } else {//If there is no update available
                            upV.getUpdateLabel().setText("");
                            upV.getUpdateBarHolder().remove(upV.getUpdateProgressBar());
                            upV.getUpdateBarHolder().add(new JLabel(updateReason));
                        }
                    }
                }
            });
            updateWorker.execute();
        }
       upV.getUpdateFrame().dispose();
    }
    
    // TODO: associer le contrôleur au modèle, par la classe UpdateM, à mettre en lien avec le clic de demande de màj de l'utilisateur
    // TODO: afficher sur la vue le retour du serveur au sujet de la disponibilité de la mise à jour
}