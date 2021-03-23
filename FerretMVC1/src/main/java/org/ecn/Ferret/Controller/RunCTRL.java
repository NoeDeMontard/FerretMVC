package org.ecn.Ferret.Controller;

import org.ecn.Ferret.Model.ElementDeRechercheM;
import org.ecn.Ferret.Model.SettingsM;
import org.ecn.Ferret.Model.Traitement1KG;
import org.ecn.Ferret.View.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Classe de lien entre le lancement de l'application Ferret par l'utilisateur et le traitement effectif par le modèle
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class RunCTRL {
    private Traitement1KG traitement; // la classe globale de traitement
    private PopulationGUI pg;
    private RegionInteretGUI rig;
//    private SettingsCTRL sc;
    private SettingsGUI sg;
    private RunGUI rg; // la classe GUI lançant l'exécution
    public RunCTRL(PopulationGUI pg,RegionInteretGUI rig,SettingsGUI sg, RunGUI rg, Traitement1KG traitement) {
        //this.run = run;
       // run.RunListener(this);
       this.traitement = traitement;
       this.pg=pg;
       this.rig=rig;
       this.sg=sg;
       this.rg=rg;
    }

	public Traitement1KG getTraitement() {
		return traitement;
	}

	public void setTraitement(Traitement1KG traitement) {
		this.traitement = traitement;
	}

	public PopulationGUI getPg() {
		return pg;
	}

	public void setPg(PopulationGUI pg) {
		this.pg = pg;
	}

	public RegionInteretGUI getRig() {
		return rig;
	}

	public void setRig(RegionInteretGUI rig) {
		this.rig = rig;
	}

	public SettingsGUI getSg() {
		return sg;
	}

	public void setSg(SettingsGUI sg) {
		this.sg = sg;
	}

	public RunGUI getRg() {
		return rg;
	}

	public void setRg(RunGUI rg) {
		this.rg = rg;
	}
/*
	public boolean VerificationParam(){//la methode pour verifier les paramatrs
         	final long startTime = System.nanoTime();
	        ArrayList<CharSequence> populations = new ArrayList<CharSequence>();
		for(int i = 0; i < afrsub.length; i++){
			if(afrsub[i].isSelected()){
				populations.add(afrCode[i]);
			}
		}
		for(int i = 0; i < eursub.length; i++){
			if(eursub[i].isSelected()){
				populations.add(eurCode[i]);
			}
		}
		for(int i = 0; i < sansub.length; i++){
			if(sansub[i].isSelected()){
				populations.add(sanCode[i]);
			}
		}
		for(int i = 0; i < asnsub.length; i++){
			if(asnsub[i].isSelected()){
				populations.add(asnCode[i]);
			}
		}
		for(int i = 0; i < amrsub.length; i++){
			if(amrsub[i].isSelected()){
				populations.add(amrCode[i]);
			}
		}
		if(allracessub[0].isSelected()){
			populations.add("ALL");
		}
		boolean popSelected = !populations.isEmpty(), fileLocSelected = (fileNameAndPath != null);

		if(inputSelect.getSelectedIndex() == 0){
					// Chr position input method
			boolean getESP = chrESPCheckBox.isSelected();
			String chrSelected = (String)chrList.getSelectedItem();
			String startPosition = startPosTextField.getText();
			String endPosition = endPosTextField.getText();

			boolean isChrSelected, startSelected, endSelected, startEndValid = true, withinRange = true;
			int chrEndBound = 0;
                        isChrSelected = !chrSelected.equals(" ");
                        // Checks to see if number and integer
			if((startSelected = !startPosition.isEmpty())){
				for(int i = 0; i < startPosition.length(); i++){
					if(!Character.isDigit(startPosition.charAt(i))){
						startSelected = false;
					}
				}
			}
			if((endSelected = !endPosition.isEmpty())){
				for(int i = 0; i < endPosition.length(); i++){
					if(!Character.isDigit(endPosition.charAt(i))){
						endSelected = false;
					}
				}
			}

			if(startSelected && endSelected){
				int tempEndPos, tempStartPos;
				try{
					if(startSelected && endSelected){
						startEndValid = Integer.parseInt(endPosition) >= Integer.parseInt(startPosition);
					}
					tempEndPos = Integer.parseInt(endPosition);
					tempStartPos = Integer.parseInt(startPosition);
				}catch(NumberFormatException e){
					tempEndPos = 250000000; tempStartPos = 0;
				}
				if (defaultHG[0]){
					switch (chrSelected){
					case "X": if(tempEndPos > 155270560 || tempStartPos < 1){withinRange = false; chrEndBound = 155270560;} break;
					case "1": if(tempEndPos > 249250621 || tempStartPos < 1){withinRange = false; chrEndBound = 249250621;} break;
					case "2": if(tempEndPos > 243199373 || tempStartPos < 1){withinRange = false; chrEndBound = 243199373;} break;
					case "3": if(tempEndPos > 198022430 || tempStartPos < 1){withinRange = false; chrEndBound = 198022430;} break;
					case "4": if(tempEndPos > 191154276 || tempStartPos < 1){withinRange = false; chrEndBound = 191154276;} break;
					case "5": if(tempEndPos > 180915260 || tempStartPos < 1){withinRange = false; chrEndBound = 180915260;} break;
					case "6": if(tempEndPos > 171115067 || tempStartPos < 1){withinRange = false; chrEndBound = 171115067;} break;
					case "7": if(tempEndPos > 159138663 || tempStartPos < 1){withinRange = false; chrEndBound = 159138663;} break;
					case "8": if(tempEndPos > 146364022 || tempStartPos < 1){withinRange = false; chrEndBound = 146364022;} break;
					case "9": if(tempEndPos > 141213431 || tempStartPos < 1){withinRange = false; chrEndBound = 141213431;} break;
					case "10": if(tempEndPos > 135534747 || tempStartPos < 1){withinRange = false; chrEndBound = 135534747;} break;
					case "11": if(tempEndPos > 135006516 || tempStartPos < 1){withinRange = false; chrEndBound = 135006516;} break;
					case "12": if(tempEndPos > 133851895 || tempStartPos < 1){withinRange = false; chrEndBound = 133851895;} break;
					case "13": if(tempEndPos > 115169878 || tempStartPos < 1){withinRange = false; chrEndBound = 115169878;} break;
					case "14": if(tempEndPos > 107349540 || tempStartPos < 1){withinRange = false; chrEndBound = 107349540;} break;
					case "15": if(tempEndPos > 102531392 || tempStartPos < 1){withinRange = false; chrEndBound = 102531392;} break;
					case "16": if(tempEndPos > 90354753 || tempStartPos < 1){withinRange = false; chrEndBound = 90354753;} break;
					case "17": if(tempEndPos > 81195210 || tempStartPos < 1){withinRange = false; chrEndBound = 81195210;} break;
					case "18": if(tempEndPos > 78077248 || tempStartPos < 1){withinRange = false; chrEndBound = 78077248;} break;
					case "19": if(tempEndPos > 59128983 || tempStartPos < 1){withinRange = false; chrEndBound = 59128983;} break;
					case "20": if(tempEndPos > 63025520 || tempStartPos < 1){withinRange = false; chrEndBound = 63025520;} break;
					case "21": if(tempEndPos > 48129895 || tempStartPos < 1){withinRange = false; chrEndBound = 48129895;} break;
					case "22": if(tempEndPos > 51304566 || tempStartPos < 1){withinRange = false; chrEndBound = 51304566;} break;
					}
				} else {
					switch (chrSelected){
					case "X": if(tempEndPos > 156040895 || tempStartPos < 1){withinRange = false; chrEndBound = 156040895;} break;
					case "1": if(tempEndPos > 248956422 || tempStartPos < 1){withinRange = false; chrEndBound = 248956422;} break;
					case "2": if(tempEndPos > 242193529 || tempStartPos < 1){withinRange = false; chrEndBound = 242193529;} break;
					case "3": if(tempEndPos > 198295559 || tempStartPos < 1){withinRange = false; chrEndBound = 198295559;} break;
					case "4": if(tempEndPos > 190214555 || tempStartPos < 1){withinRange = false; chrEndBound = 190214555;} break;
					case "5": if(tempEndPos > 181538259 || tempStartPos < 1){withinRange = false; chrEndBound = 181538259;} break;
					case "6": if(tempEndPos > 170805979 || tempStartPos < 1){withinRange = false; chrEndBound = 170805979;} break;
				        case "7": if(tempEndPos > 159345973 || tempStartPos < 1){withinRange = false; chrEndBound = 159345973;} break;
					case "8": if(tempEndPos > 145138636 || tempStartPos < 1){withinRange = false; chrEndBound = 145138636;} break;
					case "9": if(tempEndPos > 138394717 || tempStartPos < 1){withinRange = false; chrEndBound = 138394717;} break;
					case "10": if(tempEndPos > 133797422 || tempStartPos < 1){withinRange = false; chrEndBound = 133797422;} break;
					case "11": if(tempEndPos > 135086622 || tempStartPos < 1){withinRange = false; chrEndBound = 135086622;} break;
					case "12": if(tempEndPos > 133275309 || tempStartPos < 1){withinRange = false; chrEndBound = 133275309;} break;
					case "13": if(tempEndPos > 114364328 || tempStartPos < 1){withinRange = false; chrEndBound = 114364328;} break;
					case "14": if(tempEndPos > 107043718 || tempStartPos < 1){withinRange = false; chrEndBound = 107043718;} break;
					case "15": if(tempEndPos > 101991189 || tempStartPos < 1){withinRange = false; chrEndBound = 101991189;} break;
					case "16": if(tempEndPos > 90338345 || tempStartPos < 1){withinRange = false; chrEndBound = 90338345;} break;
					case "17": if(tempEndPos > 83257441 || tempStartPos < 1){withinRange = false; chrEndBound = 83257441;} break;
					case "18": if(tempEndPos > 80373285 || tempStartPos < 1){withinRange = false; chrEndBound = 80373285;} break;
					case "19": if(tempEndPos > 58617616 || tempStartPos < 1){withinRange = false; chrEndBound = 58617616;} break;
					case "20": if(tempEndPos > 64444167 || tempStartPos < 1){withinRange = false; chrEndBound = 64444167;} break;
					case "21": if(tempEndPos > 46709983 || tempStartPos < 1){withinRange = false; chrEndBound = 46709983;} break;
					case "22": if(tempEndPos > 50818468 || tempStartPos < 1){withinRange = false; chrEndBound = 50818468;} break;
					}
				}
			}

			
			if(isChrSelected && popSelected && startSelected && endSelected && startEndValid && withinRange && fileLocSelected ){
                           inputRegion[] queries = {new inputRegion(chrSelected, Integer.parseInt(startPosition), Integer.parseInt(endPosition))};
                         // if not get esp, string is none, else if get only ref, then string is ref, else string is both
			// this should be combined with the one single call to Ferret later
			// HERE
			final Integer[] variants = {0};
			String output = null;

			switch (currFileOut[0]){
			    case ALL:
				output = "all";
				break;
			    case FRQ:
				output = "freq";
				break;
			    case VCF:
				output = "vcf";
				break;
			}

			String webAddress = null;
			if (currVersion[0] == version1KG.ONE){
				webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20110521/ALL.chr$.phase1_release_v3.20101123.snps_indels_svs.genotypes.vcf.gz";
			} else if (currVersion[0] == version1KG.THREE & defaultHG[0]){
				webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/ALL.chr$.phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
			} else if (currVersion[0] == version1KG.THREE & !defaultHG[0]){
				webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/supporting/GRCh38_positions/ALL.chr$.phase3_shapeit2_mvncall_integrated_v3plus_nounphased.rsID.genotypes.GRCh38_dbSNP_no_SVs.vcf.gz";
			}
			
                        } else {
				StringBuffer errorMessage = new StringBuffer("Correct the following errors:");
				if(!isChrSelected){
					errorMessage.append("\n Select a chromosome");
				}
				if(!popSelected){
					errorMessage.append("\n Select one or more populations");
				}
				if(!startSelected){
					errorMessage.append("\n Enter a valid, integer starting position");
				}
				if(!endSelected){
					errorMessage.append("\n Enter a valid, integer ending position");
				}
				if(!startEndValid){
					errorMessage.append("\n Starting position must be less than ending position");
				}
				if(!withinRange){
					errorMessage.append("\n Invalid chromosome positions. Valid positions for chr" + chrSelected + " are from 1 to " + chrEndBound);
				}
				if(!fileLocSelected){
					errorMessage.append("\n Select a destination for the files to be saved");
				}
				JOptionPane.showMessageDialog(SNPFerret, errorMessage,"Error",JOptionPane.OK_OPTION);
			}

		 } else if(inputSelect.getSelectedIndex() == 1){	// Gene starts after this line ------------------------------------------------------------------
			boolean getESP = geneESPCheckBox.isSelected();
			String geneString = geneNameField.getText();
			String[] geneListArray = null;
			boolean geneListInputted = geneString.length() > 0;
			boolean geneFileImported = geneFileNameAndPath != null;
			boolean geneFileError = false;
			boolean geneFileExtensionError = false;
			boolean invalidCharacter = false;
			boolean geneNameInputted = geneNameRadioButton.isSelected();
			boolean fromNCBI = geneNCBIRadioButton.isSelected();

			String invalidRegex;
			if(geneNameInputted){
				invalidRegex = ".*[^a-zA-Z0-9\\-].*"; // This is everything except letters and numbers, including underscore
			} else {
				invalidRegex = ".*\\D.*"; // This is everything except numbers
			}

			if(geneFileImported){
				if(geneFileNameAndPath.length() <= 4){
					geneFileError = true;
				} else {
					String fileType = geneFileNameAndPath.substring(geneFileNameAndPath.length()-4);
					String delimiter = null;
					switch (fileType) {
					case ".csv":
						delimiter = ",";
						break;
					case ".tab":
					case ".tsv":
						delimiter = "\\t";
						break;
					case ".txt":
						delimiter = " ";
						break;
					default:
						geneFileExtensionError = true;
						break;
					}
					ArrayList<String> geneListArrayList = new ArrayList<String>();

					if(delimiter != null){
						try (
							BufferedReader geneFileRead = new BufferedReader(new FileReader(geneFileNameAndPath));
						  ){
							String geneStringToParse;
							while((geneStringToParse = geneFileRead.readLine()) != null){
								String[] text = geneStringToParse.split(delimiter);
								for(int i = 0; i < text.length; i++){
								text[i] = text[i].replace(" ", "").toUpperCase(new Locale("all")); // remove spaces
									if(text[i].matches(invalidRegex)){ // identify invalid characters
										invalidCharacter = true;
										break;
									}
									if(text[i].length() > 0){
										geneListArrayList.add(text[i]);
									}
								}
							}
							geneListArray = geneListArrayList.toArray(new String[geneListArrayList.size()]);
						} catch(IOException e){
							//e.printStackTrace();
							geneFileError = true;
						} catch(NullPointerException e){
							//File is empty 
							geneFileError = true;
						}
					}
				}

			} else if(geneListInputted){
				geneString = geneString.toUpperCase(new Locale("all"));
				String geneList = geneString.replace(" ", "");
				invalidCharacter = geneList.replace(",","").matches(invalidRegex);
				if(geneList.endsWith(",")){
					geneList = geneList.substring(0, geneList.length()-1);
				}
				geneListArray = geneList.split(",");
			}

	            if((geneListInputted || (geneFileImported && !geneFileError && !geneFileExtensionError)) && !invalidCharacter && popSelected && fileLocSelected ){

			// this should be combined with the one single call to Ferret later
			final Integer[] variants = {0};
			String output = null;

			switch (currFileOut[0]){
				case ALL:
					output = "all";
					break;
				case FRQ:
					output = "freq";
					break;
				case VCF:
					output = "vcf";
					break;
				}

				String webAddress = null;
                                if (currVersion[0] == version1KG.ONE){
					webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20110521/ALL.chr$.phase1_release_v3.20101123.snps_indels_svs.genotypes.vcf.gz";
				} else if (currVersion[0] == version1KG.THREE & defaultHG[0]){
					webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/ALL.chr$.phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
				} else if (currVersion[0] == version1KG.THREE & !defaultHG[0]){
					webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/supporting/GRCh38_positions/ALL.chr$.phase3_shapeit2_mvncall_integrated_v3plus_nounphased.rsID.genotypes.GRCh38_dbSNP_no_SVs.vcf.gz";
				}

				String geneQueryType;
				if(geneNameInputted){
					geneQueryType = "geneName";
				} else{
					geneQueryType = "geneID";
				}

			} else {
				StringBuffer errorMessage = new StringBuffer("Correct the following errors:");
				if(!geneListInputted && !geneFileImported){
					errorMessage.append("\n Enter a gene name/ID or select a file");
				}
				if(geneFileImported && geneFileError){
					errorMessage.append("\n There was a problem reading the Gene file. Please check the FAQ.");
				}
				if(geneFileImported && geneFileExtensionError){
					errorMessage.append("\n Invalid file extension. Ferret supports tsv, csv, tab, and txt files.");
				}
				if((geneListInputted || geneFileImported) && invalidCharacter){
					errorMessage.append("\n Invalid character entered");
				}
				if(!fileLocSelected){
					errorMessage.append("\n Select a destination for the files to be saved");
				}
				if(!popSelected){
					errorMessage.append("\n Select one or more populations");
				}
						
				JOptionPane.showMessageDialog(SNPFerret, errorMessage,"Error",JOptionPane.OK_OPTION);
			}
		} else { // SNP starts here ---------------------------------------------------------------------------------

			boolean getESP = snpESPCheckBox.isSelected();
			String snpString = snpTextField.getText();
			boolean snpListInputted = snpString.length() > 0;
			boolean snpFileImported = snpFileNameAndPath != null;
			boolean snpFileError = false;
			boolean snpFileExtensionError = false;
			boolean invalidCharacter = false;
			boolean fromNCBI = snpNCBIRadioButton.isSelected();
			String invalidRegex = ".*\\D.*"; // This is everything except numbers
			ArrayList<String> snpListArray = new ArrayList<String>();
			String snpWindowSize = snpWindowField.getText();
			boolean snpWindowSelected = snpWindowCheckBox.isSelected();
			boolean validWindowSizeEntered = true; // must be both not empty and an int

			if(snpWindowSelected){
				if(snpWindowSize.length() == 0){
					validWindowSizeEntered = false; // must have something there
				} else { // test for non ints
					for(int i = 0; i < snpWindowSize.length(); i++){
						if(!Character.isDigit(snpWindowSize.charAt(i))){
							validWindowSizeEntered = false;
						}
					}
				}
			}else{ //if no window specified, it's always fine
				snpWindowSize = "0";
			}

			if(snpFileImported){
				if(snpFileNameAndPath.length() <= 4){
					snpFileError = true;
				} else {
					String fileType = snpFileNameAndPath.substring(snpFileNameAndPath.length()-4);
					String delimiter = null;
					switch (fileType) {
					case ".csv":
						delimiter = ",";
						break;
					case ".tab":
					case ".tsv":
						delimiter = "\\t";
						break;
					case ".txt":
						delimiter = " ";
						break;
					default:
						snpFileExtensionError = true;
						break;
					}
					if(delimiter != null){
						try (
								BufferedReader snpFileRead = new BufferedReader(new FileReader(snpFileNameAndPath));
								){
							String snpStringToParse;
							while((snpStringToParse = snpFileRead.readLine()) != null){
								String[] text = snpStringToParse.split(delimiter);
								for(int i = 0; i < text.length; i++){
									text[i] = text[i].replace(" ", ""); // remove spaces
									if(text[i].matches(invalidRegex)){ // identify invalid characters
										invalidCharacter = true; // probably can just throw error here, might be easier/more straight forward. But then errors wouldn't be 'accumulated' to the end
										break;
									}
									if(text[i].length() > 0){
										snpListArray.add(text[i]);
									}
								}
							}
						} catch(IOException e) {
							//e.printStackTrace();
							snpFileError = true;
						} catch(NullPointerException e){
							snpFileError = true;
						}
					}
				}
			} else if(snpListInputted){

				while(snpString.endsWith(",") || snpString.endsWith(" ")){ // maybe this should be added for gene input too
					snpString = snpString.substring(0, snpString.length()-1);
				}
				String[] text = snpString.split(",");
				for(int i = 0; i < text.length; i++){
					text[i] = text[i].replace(" ", "");// remove spaces
					if(text[i].matches(invalidRegex)){
						invalidCharacter = true;
						break;
					}
				}
				snpListArray = new ArrayList<String>(Arrays.asList(text));
			}

			
		  if((snpListInputted || (snpFileImported && !snpFileError && !snpFileExtensionError)) && !invalidCharacter && validWindowSizeEntered && popSelected && fileLocSelected ){

				final Integer[] variants = {0};
				String output = null;

				switch (currFileOut[0]){
				case ALL:
					output = "all";
					break;
				case FRQ:
					output = "freq";
					break;
				case VCF:
					output = "vcf";
					break;
				}

				String webAddress = null;

				if (currVersion[0] == version1KG.ONE){
					webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20110521/ALL.chr$.phase1_release_v3.20101123.snps_indels_svs.genotypes.vcf.gz";
				} else if (currVersion[0] == version1KG.THREE & defaultHG[0]){
					webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/ALL.chr$.phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
				} else if (currVersion[0] == version1KG.THREE & !defaultHG[0]){
					webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/supporting/GRCh38_positions/ALL.chr$.phase3_shapeit2_mvncall_integrated_v3plus_nounphased.rsID.genotypes.GRCh38_dbSNP_no_SVs.vcf.gz";
				}


			} else {

				StringBuffer errorMessage = new StringBuffer("Correct the following errors:");
				if(!snpListInputted && !snpFileImported){
					errorMessage.append("\n Enter a variant number or select a file");
				}
				if(snpFileImported && snpFileError){
					errorMessage.append("\n There was a problem reading the variant file. Please check the FAQ.");
				}
				if(snpFileImported && snpFileExtensionError){
					errorMessage.append("\n Invalid file extension. Ferret supports tsv, csv, tab, and txt files.");
				}
				if((snpListInputted || snpFileImported) && invalidCharacter){
					errorMessage.append("\n Invalid character entered");
				}
				if(!fileLocSelected){
					errorMessage.append("\n Select a destination for the files to be saved");
				}
				if(!popSelected){
					errorMessage.append("\n Select one or more populations");
				}
				if(!validWindowSizeEntered){
					errorMessage.append("\n You must enter an integer window size if you wish to retrieve regions around variants");
				}
				
				}
				JOptionPane.showMessageDialog(SNPFerret, errorMessage,"Error",JOptionPane.OK_OPTION);

			}

}
      
    
    public void ExecutionTraitement1KG(){//executer traitement1kg
    	SettingsCTRL sc = new SettingsCTRL (sg,pg,rig);
    	InfosRechercheCTRL rc = new InfosRechercheCTRL(rig,pg, rg);
    	// TODO: Il faut spécifier les éléments de recherche depuis InfosRechercheCTRL
		LinkedList<ElementDeRechercheM> enteredQueries = new LinkedList<>();
		traitement.traitement(sc.getSettingsM(), enteredQueries);

    }
   */
}

