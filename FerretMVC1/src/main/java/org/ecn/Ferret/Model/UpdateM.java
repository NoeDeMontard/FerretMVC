package org.ecn.Ferret.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.SwingWorker;

/**
 * Classe correspondant aux mises à jour.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG & Siqi Yu
*/

public class UpdateM extends SwingWorker<Boolean,Object>{
  //If there is an update available, needUpdate=true;
  Boolean needUpdate = null;
  //If there is an urgent update, urgentUpdate=true;
  Boolean urgentUpdate = null;
  //The message for the update
  String updateMessage = null;

  public Boolean needUpdate(){
    return needUpdate;
  }

  public Boolean urgentUpdate(){
    return urgentUpdate;
  }

  public String updateStatus(){
    return updateMessage;
  }
  
  @Override
    public Boolean doInBackground() throws Exception {
        URL urlLocation = new URL("https://webspace.princeton.edu/users/taverner/updateFerret.txt");//This link doesn't work now
        try(BufferedReader br = new BufferedReader(new InputStreamReader(urlLocation.openStream()))){
            String currentString;
            while((currentString = br.readLine()) != null){
                String[] updateInformation = currentString.split(":");
                if(Integer.parseInt(updateInformation[0]) <= 3){ // 2 is the internal version number of this version of Ferret
                    if(updateInformation[1].equals("urgentUpdate")){
                        updateMessage = "Urgent update. Ferret may not be functional until you update: " + updateInformation[2];
                        needUpdate = true;
                        urgentUpdate = true;
                    }
                    else if(updateInformation[1].equals("recommendedUpdate")) {
                        updateMessage = "Recommended update: " + updateInformation[2];
                        needUpdate = true;
                        urgentUpdate = false;
                    }
                    else if(updateInformation[1].equals("noUpdate")){
                        updateMessage = "Ferret is up to date";
                        needUpdate = false;
                        urgentUpdate = false;
                    }
                }
            }
            if (updateMessage == null){ // si on n'a pas pu se connecter au serveur, le message sera toujours sur nul
                needUpdate = true;
                urgentUpdate = true;
                updateMessage = "Unable to contact update server. Try again later or update Ferret.";
            }
        }
        catch(IOException e){
            needUpdate = true;
            urgentUpdate = true;
            updateMessage = "Unable to contact update server. Try again later or update Ferret.";
        }		
        return needUpdate;
    }
  
}
