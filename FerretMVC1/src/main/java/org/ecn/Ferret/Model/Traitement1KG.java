package org.ecn.Ferret.Model;


import htsjdk.tribble.readers.TabixReader;

import java.io.*;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe pour le traitement général sur le serveur 1000 Génomes.
 * Cette classe nécessite d'être fortement retravaillée, notamment pour gérer les différents format possibles d'entrées.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
*/

public class Traitement1KG extends Traitement {

    LocusM[] queries;
    String ftpAddress; // DONE: déterminer à quel moment la valeur de cet attribut est entrée
    int progress;
    boolean allSNPsFound;
    boolean noSNPFound;
    //JLabel status;  // DONE: à enlever pour mettre dans le GUI l'affichage par les JLabel

    // DONE: Implémentation d'un constructeur pour la classe Traitement1KG

    /**
     * Constructeur de Traitement1KG
     * @param queries La requête de recherche du locus entrée dans le contrôleur
     * @param ftpAddress L'adresse cible de la recherche
     * @param progress Progrès de l'opération de recherche de la requête
     */
    public Traitement1KG(LocusM[] queries, String ftpAddress, int progress) {
        this.queries = queries;
        this.ftpAddress = ftpAddress;
        this.progress = progress;
    }

    /**
     * Constructeur de Traitement1KG
     * @param queries La requête de recherche du locus entrée dans le contrôleur
     * @param ftpAddress L'adresse cible de la recherche
     * @param progress Progrès de l'opération de recherche de la requête
     * @param allSNPsFound
     * @param noSNPFound
     */
    public Traitement1KG(LocusM[] queries, String ftpAddress, int progress,boolean allSNPsFound, boolean noSNPFound) {
        this.queries = queries;
        this.ftpAddress = ftpAddress;
        this.progress = progress;
        this.allSNPsFound = allSNPsFound;
        this.noSNPFound = noSNPFound;
    }

    public Traitement1KG() {
        this.queries = null;
        this.ftpAddress = "";
        this.progress = 0;
    }

    public LocusM[] getQueries() {
        return queries;
    }

    public String getFtpAddress() {
        return ftpAddress;
    }

    public int getProgress() {
        return progress;
    }

    public void setQueries(LocusM[] queries) {
        this.queries = queries;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public void setProgress(int p){
        this.progress = p;
    }

    public boolean isAllSNPsFound() {
        return allSNPsFound;
    }

    public void setAllSNPsFound(boolean allSNPsFound) {
        this.allSNPsFound = allSNPsFound;
    }

    public boolean isNoSNPFound() {
        return noSNPFound;
    }

    public void setNoSNPFound(boolean noSNPFound) {
        this.noSNPFound = noSNPFound;
    }

    /**
     * Fonction ayant pour but de traiter le cas où l'entrée se fait par un variant.
     * @param settings les paramètres de la recherche
     * @param snpQueries l'ensemble des requêtes entrées sous forme de variants (snp)
     */
    public void traitementVariantID(SettingsM settings, LinkedList<ElementDeRechercheM> snpQueries){

        // on s'intéresse d'abord à la requête utilisant une entrée sous forme de variant

                //publish("Looking up variant locations...");
                LinkedList<String> chromosome = new LinkedList<>();
                LinkedList<String> startPos = new LinkedList<>();
                LinkedList<String> endPos = new LinkedList<>();
//                LocusM[] queries = null;
                ArrayList<String> SNPsFound = new ArrayList<>();
                boolean allSNPsFound = true;
                try {
                    // pour chacun des variants entrés
                    for(int i = 0; i < snpQueries.size(); i++){
                        // on va voir ce que dit le NCBI à ce sujet, puis on récupère les infos pour faire le traitement
                        URL urlLocation = new URL("https://www.ncbi.nlm.nih.gov/projects/SNP/snp_gene.cgi?connect=&rs=" + snpQueries.get(i));
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlLocation.openStream()));
                        String currentString;
                        if (settings.getVersionHG()){
                            while((currentString = br.readLine()) != null && !currentString.contains("\"GRCh37.p13\" : [")){}
                        }else{
                            while((currentString = br.readLine()) != null && !currentString.contains("\"GRCh38.p2\" : [")){}
                        }
                        boolean chrFound = false, startFound = false, endFound = false, locatedOnInvalidChr = false;
                        while(!(startFound && endFound && chrFound) && (currentString = br.readLine()) != null){
                            String substring = currentString.substring(currentString.indexOf(" : \"") + 4, currentString.indexOf("\","));
                            if(currentString.contains("\"chrPosFrom\"")){
                                startPos.add(substring);
                                startFound = true;
                            } else if(currentString.contains("\"chr\"")){
                                chromosome.add(substring);
                                locatedOnInvalidChr = chromosome.peekLast().equals("X") | chromosome.peekLast().equals("Y") | chromosome.peekLast().equals("MT");
                                chrFound = true;
                            } else if(currentString.contains("\"chrPosTo\"")){
                                endPos.add(substring);
                                endFound = true;
                            }
                        }
                        if(!(startFound && endFound && chrFound && !locatedOnInvalidChr)){
                            // If one of the three elements is missing the other elements corresponding to the missing one are removed
                            if(startFound){startPos.removeLast();}
                            if(endFound){endPos.removeLast();}
                            if(chrFound){chromosome.removeLast();}
                            allSNPsFound = false;
                        }else{
                            SNPsFound.add(Integer.toString(((VariantParIDM)snpQueries.get(i)).GetIdentifiant()));
                        }
                        br.close();
                    }
                    int sd = ((VariantParIDM)snpQueries.get(0)).getSurroundingDist();
                    this.allSNPsFound = allSNPsFound;
                    this.noSNPFound = SNPsFound.isEmpty();
//                    if(!allSNPsFound && !SNPsFound.isEmpty()){//Partial list
//
//                        // Done: Il s'agit de mettre en place du code qui permettrq de gérer l'affichage graphique de ce qui suit
//                        // (ie, demander à l'utilisateur de continuer ou bien afficher les problèmes / résultats obtenus)
//                        // par l'intermédiaire du MVC, et non au sein de cette classe du modèle.
//                            String[] options = {"Yes","No"};
//                            JPanel partialSNPPanel = new JPanel();
//                            JTextArea listOfSNPs = new JTextArea(SNPsFound.toString().substring(1, SNPsFound.toString().length()-1));
//                            listOfSNPs.setWrapStyleWord(true);
//                            listOfSNPs.setLineWrap(true);
//                            listOfSNPs.setBackground(partialSNPPanel.getBackground());
//                            partialSNPPanel.setLayout(new BoxLayout(partialSNPPanel, BoxLayout.Y_AXIS));
//                            partialSNPPanel.add(new JLabel("Ferret encountered problems retrieving the variant positions from the NCBI SNP Database."));
//                            partialSNPPanel.add(new JLabel("Here are the variants successfully retrieved:"));
//                            JScrollPane listOfSNPScrollPane = new JScrollPane(listOfSNPs,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//                            listOfSNPScrollPane.setBorder(BorderFactory.createEmptyBorder());
//                            listOfSNPScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
//                            partialSNPPanel.add(listOfSNPScrollPane);
//                            partialSNPPanel.add(new JLabel("Do you wish to continue?"));
//
//                            int choice = JOptionPane.showOptionDialog(null,
//                                            partialSNPPanel,
//                                            "Continue?",
//                                            JOptionPane.YES_NO_OPTION,
//                                            JOptionPane.PLAIN_MESSAGE,
//                                            null,
//                                            options,
//                                            null);
//                            if(choice == JOptionPane.YES_OPTION){
//                                queries = new LocusM[chromosome.size()];
//                                for(int i = 0; chromosome.size() > 0; i++){
//                                    queries[i] = new LocusM(Integer.parseInt(chromosome.remove()), (Integer.parseInt(startPos.remove()) - sd), (Integer.parseInt(endPos.remove()) + sd));
//                                }
//                            }
//                    } else if(SNPsFound.isEmpty()){
//                            JOptionPane.showMessageDialog(null, "Ferret was unable to retrieve any variants","Error",JOptionPane.OK_OPTION);}
//                    if(allSNPsFound){
//                        queries = new LocusM[chromosome.size()];
//                        for(int i = 0; chromosome.size() > 0; i++){
//                            queries[i] = new LocusM(Integer.parseInt(chromosome.remove()), Integer.parseInt(startPos.remove()) - sd, Integer.parseInt(endPos.remove()) + sd);
//                        }
//                    }
//                    this.queries = queries;
                } catch (FileNotFoundException e) {
                        // Either will occur if ncbi is down or if something is wrong with the input
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Ferret was unable to retrieve any variants","Error",JOptionPane.OK_OPTION);
                } catch(IOException e){
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Ferret was unable to retrieve any variants","Error",JOptionPane.OK_OPTION);
                } catch(Exception e){
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Ferret was unable to retrieve any variants","Error",JOptionPane.OK_OPTION);
                }
        }
    /**
     * Fonction ayant pour but de traiter le cas où l'entrée se fait par un gène.
     * @param settings les paramètres de la recherche
     * @param geneQueries l'ensemble des requêtes entrées sous forme de gène
     */
    public void traitementGene(SettingsM settings, LinkedList<ElementDeRechercheM> geneQueries){
        // requête où l'entrée est sous forme de gène (que ce soit ID du gène ou nom du gène)

            //publish("Looking up gene locations...");
            FoundGeneAndRegion[] geneLocationFromGeneName = {null};
            if(geneQueries.get(0).getClass().getSimpleName().equals("geneParNomM")){
                String[] geneList;
                geneList = new String[geneQueries.size()];
                for (int e=0; e<geneQueries.size(); e++){
                    geneList[e] = ((GeneParNomM)geneQueries.get(e)).getNom();
                }
                geneLocationFromGeneName[0] = TraitementNCBI.getQueryFromGeneName(geneList,settings.getVersionHG());
            }else{
                String[] geneList;
                geneList = new String[geneQueries.size()];
                for (int e=0; e<geneQueries.size(); e++){
                    geneList[e] = Integer.toString(((GeneParIDM)geneQueries.get(e)).getIdentifiant());
                }
                geneLocationFromGeneName[0] = TraitementNCBI.getQueryFromGeneID(geneList,settings.getVersionHG());
            }
        }

    /**
     * Fonction exécutant les entrées par gène ou variant (appelant donc la fonction appropriée parmi les deux précédentes).
     * @param settings les paramètres de la recherche
     * @param enteredQueries
     */
    public void traitement(SettingsM settings, LinkedList<ElementDeRechercheM> enteredQueries) {

        if(enteredQueries != null && enteredQueries.get(0).getClass().getSimpleName().equals("VariantParIDM")){
            traitementVariantID(settings, enteredQueries);
        }
        else if(enteredQueries != null && enteredQueries.get(0).getClass().getSimpleName().startsWith("Gene")){
            traitementGene(settings, enteredQueries);
        }
        // TODO: meme chose trouver une solution pour publish
        //publish("Parsing Individuals...");

        // analyse des individus
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_UP);
        // Initialize some variables
        ArrayList<String> peopleOfInterest = new ArrayList<String>();
        int variantCounter = 0, peopleCounter = 0;
        String[][] genotypes = null;

        ArrayList<LocusM> sortedQueries = sortByWindow(queries);
        String webAddress = ftpAddress.replace('$', '1');
        HashMap<String, Integer> peopleSet;
        peopleSet = new HashMap<>();
        try {
            peopleSet = getPopulationIndices(webAddress);
        } catch(IOException e){}

        int queryNumber = sortedQueries.size();
        int[] querySize = new int[queryNumber + 1];
        querySize[0] = 0;
        for(int i = 0; i < sortedQueries.size(); i++){
            querySize[i+1] = querySize[i] + (sortedQueries.get(i).getEnd() - sortedQueries.get(i).getStart());
        }

        // Gather the individuals to return genotypes
        try {
            String s;
            // If custom URL is specified and the date for phase 1 is not in the name, Ferret will use
            // phase 3 individuals
            BufferedReader popBuffRead = null;
            if (ftpAddress.contains("20110521")){
                popBuffRead = new BufferedReader(new InputStreamReader(Traitement1KG.class.getClassLoader().getResourceAsStream("samplesPhase1.txt")));
            } else {
                popBuffRead = new BufferedReader(new InputStreamReader(Traitement1KG.class.getClassLoader().getResourceAsStream("samplesPhase3.txt")));
            }

            String[] IDs;
            s = popBuffRead.readLine();
            if(!enteredQueries.get(0).contain("ALL")){
                while (s != null){
                    IDs = s.split("\t");
                    if (enteredQueries.get(0).contain(IDs[1]) || enteredQueries.get(0).contain(IDs[2])) {
                        if (peopleSet.containsKey(IDs[0])){
                            peopleOfInterest.add(IDs[0]);
                            peopleCounter++;
                        }
                    }
                    s = popBuffRead.readLine();
                }
            } else {
                while(s != null){
                    IDs = s.split("\t");
                    if (peopleSet.containsKey(IDs[0])){
                        peopleOfInterest.add(IDs[0]);
                        peopleCounter++;
                    }
                    s = popBuffRead.readLine();
                }
            }
            popBuffRead.close();
        } catch (IOException e) {
            //This shouldn't be a problem since the file being read comes with Ferret
            //e.printStackTrace();
        }

        //publish("Downloading Data from 1000 Genomes...");
        String s;
        BufferedWriter vcfWrite = null;
        long startTime = 0;
        Integer tempInt = null;

        // écriture des fichiers

        if (settings.getOutput().equals("vcf")){
            // Outputting VCF does not require writing the VCF from 1KG
            String fileName;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
            LocalDateTime now = LocalDateTime.now();
            fileName = "Ferret_Data" + dtf.format(now);
            File vcfWriteFile = new File(fileName + ".vcf");
            try {
                vcfWriteFile.createNewFile();
                vcfWrite = new BufferedWriter(new FileWriter(vcfWriteFile));
                // Uses the web address from peopleSet, but I don't really see a problem with this
                TabixReader tr = new TabixReader(webAddress);
                // Done: récupérer un bon TabixReader, nous n'en avons pas trouvé de convenable qui corresponde aux fonctionnalités développées ici
                // (je ne sais pas pourquoi l'ancien TabixReader n'est plus valable...)
                s = tr.readLine();
                while (!s.contains("CHROM")) {
                    vcfWrite.write(s);
                    vcfWrite.newLine();
                    s = tr.readLine();
                }
                String[] stringSplit = s.split("\t");
                // Write VCF header/column label
                for(int i = 0; i < 9; i++){
                    vcfWrite.write(stringSplit[i] + "\t");
                }
                ArrayList<Integer> peopleIndexSet = new ArrayList<Integer>();
                // Write individuals
                for(int i = 9; i < stringSplit.length; i++){
                    if (peopleOfInterest.contains(stringSplit[i])){
                        vcfWrite.write(stringSplit[i] + "\t");
                        peopleIndexSet.add(i);
                    }
                }
                vcfWrite.newLine();

                for(int j = 0; j < queryNumber; j++){
                    webAddress = ftpAddress.replace("$",Integer.toString(sortedQueries.get(j).getChromosome()));
                    tr = new TabixReader(webAddress);
                    startTime = System.nanoTime();
                    TabixReader.Iterator iter = tr.query(Integer.toString(sortedQueries.get(j).getChromosome()) + ":" + sortedQueries.get(j).getStart() + "-" + sortedQueries.get(j).getEnd());
                    long endTime = System.nanoTime();
                    System.out.println("Tabix iterator time: " + (endTime - startTime));

                    while ((s = iter.next()) != null) {
                        variantCounter++;
                        stringSplit = s.split("\t");

                        if (isInteger(stringSplit[1])){
                            tempInt = Integer.parseInt(stringSplit[1]) - sortedQueries.get(j).getStart();
                            if (tempInt > 0){
                                setProgress((int)((tempInt + querySize[j])/(double)querySize[queryNumber]*99));
                            }
                        }

                        String[] multiAllele = stringSplit[4].split(",");
                        int[] variantFreq = new int[multiAllele.length + 1];

                        int chromosomeCount = 0;

                        StringBuffer tempString = new StringBuffer();
                        for(int i = 0; i < 9; i++){
                            tempString.append(stringSplit[i] + "\t");
                        }
                        for(int i = 9; i < stringSplit.length; i++){
                            if (peopleIndexSet.contains(i)){
                                tempString.append(stringSplit[i] + "\t");
                                variantFreq[Character.getNumericValue(stringSplit[i].charAt(0))]++;
                                variantFreq[Character.getNumericValue(stringSplit[i].charAt(0))]++;
                                chromosomeCount += 2;
                            }
                        }
                        // This loop is equivalent to an if "all"
                        boolean tempBoolean = true;
                        for(int i = 0; i < variantFreq.length; i++){
                            if((variantFreq[i]/(float)chromosomeCount) < settings.getMaf()){
                                tempBoolean = false; //if fail MAF Threshold, continue to next line
                            }
                        }
                        if(tempBoolean){
                            vcfWrite.write(tempString.toString());
                            vcfWrite.newLine();
                        }
                        /*if (stringSplit[2].equals(".")) {
                            stringSplit[2] = "chr" + sortedQueries.get(j).getChr() + "_" + stringSplit[1];
                            for (int i = 0; i < stringSplit.length; i++) {
                                    vcfBuffWrite.write(stringSplit[i] + "\t");
                            }
                        }else{
                            vcfBuffWrite.write(s);
                        } */
                    }

                }
                vcfWrite.close();
                if(variantCounter == 0){
                    vcfWriteFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
                vcfWriteFile.delete();
            }
        } else {

            // VCF writing code is right here for compatibility with swingWorker
            try {
                String fileName;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime now = LocalDateTime.now();
                fileName = "Ferret_Data_genotypes" + dtf.format(now);
                File vcfFile = new File(fileName + ".vcf");
                vcfFile.createNewFile();
                BufferedWriter vcfBuffWrite = new BufferedWriter(new FileWriter(vcfFile));
                for(int j = 0; j < queryNumber; j++){
                    webAddress = ftpAddress.replace("$",Integer.toString(sortedQueries.get(j).getChromosome()));
                    /*
                if (hgVersion == "GRCh37"){
                        webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/ALL.chr" + sortedQueries.get(j).getChr() + ".phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
                } else{
                        webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/supporting/GRCh38_positions/ALL.chr" + sortedQueries.get(j).getChr() + ".phase3_shapeit2_mvncall_integrated_v3plus_nounphased.rsID.genotypes.GRCh38_dbSNP_no_SVs.vcf.gz";
                }
                         */
                        //webAddress = "ftp://ftp-trace.ncbi.nih.gov/1000genomes/ftp/release/20130502/ALL.chr" + sortedQueries.get(j).getChr() + ".phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
                        //webAddress = "ftp://ftp-trace.ncbi.nih.gov/1000genomes/ftp/release/20130502/supporting/GRCh38_positions/ALL.chr" + sortedQueries.get(j).getChr() + ".phase3_shapeit2_mvncall_integrated_v3plus_nounphased.rsID.genotypes.GRCh38_dbSNP_no_SVs.vcf.gz";
                    TabixReader tr = new TabixReader(webAddress);

                    // Get the iterator
                    startTime = System.nanoTime();
                    TabixReader.Iterator iter = tr.query(sortedQueries.get(j).getChromosome() + ":" + sortedQueries.get(j).getStart() + "-" + sortedQueries.get(j).getEnd());
                    long endTime = System.nanoTime();
                    System.out.println("Tabix iterator time: " + (endTime - startTime));
                    while ((s = iter.next()) != null) {
                        variantCounter++;
                        String[] stringSplit = s.split("\t");
                        if (stringSplit[6].equals("PASS")) {
                            if (isInteger(stringSplit[1])){
                                tempInt = Integer.parseInt(stringSplit[1]) - sortedQueries.get(j).getStart();
                                // For debugging:
                                //System.out.println(Integer.toString(tempInt) + " - " + Integer.toString(variantCounter));
                                if (tempInt > 0){
                                    setProgress((int)((tempInt + querySize[j])/(double)querySize[queryNumber]*99));
                                }
                            }
                            if (stringSplit[2].equals(".")) {
                                stringSplit[2] = "chr" + sortedQueries.get(j).getChromosome() + "_" + stringSplit[1];
                                for (int i = 0; i < stringSplit.length; i++) {
                                    vcfBuffWrite.write(stringSplit[i] + "\t");
                                }
                            }else{
                                vcfBuffWrite.write(s);
                            }
                            vcfBuffWrite.newLine();
                        } else {
                            vcfBuffWrite.write(s);
                            vcfBuffWrite.newLine();
                        }
                    }
                    long endEndTime = System.nanoTime();
                    System.out.println("Iteration time: " + (endEndTime - endTime));
                }
                vcfBuffWrite.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOException " + tempInt);
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Null Pointer Exception " + tempInt);
                // Tabix iterator doesn't have has.next method, so this protects from regions without variants
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.out.println("Runtime Exception" + tempInt);
                System.out.println("Iteration time: " + (System.nanoTime() - startTime));
            }
            // end VCF writing


            // une fois que le traitement standard par le serveur 1KG est réalisé, on va s'intéresser au traitement sur le serveur ESP
            // si demandé par l'utilisateur dans les paramètres
            LinkedList<EspInfoObj> espData = null;
            if(settings.getEsp()){
                //publish("Downloading Data from Exome Sequencing Project...");
                espData = TraitementESP.exomeSequencingProject(sortedQueries); // on appelle la méthode de traitement statique
            }
            if(variantCounter == 0 && (espData == null || espData.size() == 0)){
                String fileName;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime now = LocalDateTime.now();
                fileName = "Ferret_Data" + dtf.format(now);
                File vcfFile = new File(fileName + "_genotypes.vcf");
                vcfFile.delete();
            }

            //publish("Outputting files...");
            try {
                // Creating lookup HashMap for family info, etc.
                HashMap<String, String[]> familyInfo = new HashMap<String, String[]>(5000);
                BufferedReader familyInfoRead = new BufferedReader(new InputStreamReader(Traitement1KG.class.getClassLoader().getResourceAsStream("family_info.txt")));
                s = familyInfoRead.readLine();
                while((s = familyInfoRead.readLine()) != null){
                    String[] text = s.split("\t");
                    String[] temp = {text[0], text[2], text[3], text[4]};
                    familyInfo.put(text[1], temp);
                }
                familyInfoRead.close();

                BufferedWriter mapWrite = null, infoWrite = null, pedWrite = null, frqWrite = null;
                boolean fileEmpty = true, frqFileEmpty = true;

                String fileName;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime now = LocalDateTime.now();
                fileName = "Ferret_Data" + dtf.format(now);
                if(settings.getOutput().equals("all")){
                    genotypes = new String[peopleCounter+1][2*variantCounter + 6];

                    File mapFile = new File(fileName + ".map");
                    mapFile.createNewFile();
                    mapWrite = new BufferedWriter(new FileWriter(mapFile));

                    File infoFile = new File(fileName + ".info");
                    infoFile.createNewFile();
                    infoWrite = new BufferedWriter(new FileWriter(infoFile));

                    File pedFile = new File(fileName + ".ped");
                    pedFile.createNewFile();
                    pedWrite = new BufferedWriter(new FileWriter(pedFile));
                }

                BufferedReader vcfRead = new BufferedReader(new FileReader(fileName + "_genotypes.vcf"));

                File freqFile = new File(fileName + "AlleleFreq.frq");
                freqFile.createNewFile();
                frqWrite = new BufferedWriter(new FileWriter(freqFile));
                if(settings.getEsp()){
                    frqWrite.write("CHROM\tVARIANT\tPOS\tALLELE1\tALLELE2\tNB_1KG_CHR\t1KG_A1_FREQ\tESP6500_EA_A1_FREQ\tESP6500_AA_A1_FREQ");
                    frqWrite.newLine();
                } else {
                    frqWrite.write("CHROM\tVARIANT\tPOS\tALLELE1\tALLELE2\tNB_CHR\t1KG_A1_FREQ\t1KG_A2_FREQ");
                    frqWrite.newLine();
                }


                // Populate the genotypes array with patient/family data
                if (settings.getOutput().equals("all")){
                    for(int i = 0; i < peopleCounter; i++){
                        genotypes[i+1][1] = peopleOfInterest.get(i);
                        String[] temp = familyInfo.get(genotypes[i+1][1]);
                        genotypes[i+1][0] = temp[0];
                        genotypes[i+1][2] = temp[1];
                        genotypes[i+1][3] = temp[2];
                        genotypes[i+1][4] = temp[3];
                        genotypes[i+1][5] = "0";
                    }
                }

                int index = 0;
                int espErrorCount = 0;
                //System.out.println("Got to reading vcf part");
                while ((s = vcfRead.readLine()) != null) {
                    String[] text = s.split("\t");
                    // populate variables about this variant
                    String[] variantPossibilities;
                    String[] multAllele = text[4].split(",");
                    int[] variantFreq = {0, 0};
                    variantPossibilities = new String[multAllele.length + 1];
                    variantPossibilities[0] = text[3];
                    for(int i = 0; i < multAllele.length; i++){
                        variantPossibilities[i+1] = multAllele[i];
                    }
                    // indel trick
                    String[] retainedPossibility = new String[2];
                    if(!text[4].contains("CN") && variantPossibilities.length == 2 &&
                        (variantPossibilities[0].length() > 1 || variantPossibilities[1].length() > 1)){
                        text[2] = "indel_" + text[2] + "_" + variantPossibilities[0] + "/" + variantPossibilities[1];
                        retainedPossibility[0] = variantPossibilities[0];
                        retainedPossibility[1] = variantPossibilities[1];
                        variantPossibilities[0] = "A";
                        variantPossibilities[1] = "T";
                    }
                    int numChr = 0;
                    double freqZero = 0, freqOne = 0;
                    // only write for biallelic variants
                    if(variantPossibilities.length == 2){
                        //CN = CNV
                        if(!text[4].contains("CN")){
                            for(int i = 0; i < peopleOfInterest.size(); i++){
                                String tempPerson = peopleOfInterest.get(i);
                                int personIndex = peopleSet.get(tempPerson);
                                String personGtype = text[personIndex];
                                int temp = Character.getNumericValue(personGtype.charAt(0));
                                if(settings.getOutput().equals("all")){
                                    genotypes[i+1][2*index + 6] = variantPossibilities[temp];
                                }
                                variantFreq[temp]++;
                                temp = Character.getNumericValue(personGtype.charAt(2));
                                if(settings.getOutput().equals("all")){
                                    genotypes[i+1][2*index + 7] = variantPossibilities[temp];
                                }
                                variantFreq[temp]++;
                            }
                            // If you were thinking index should be incremented here, you're right
                            // but now it's in essentially the same loop several lines down
                        }else if(text[4].contains("CN")){
                            for(int i = 0; i < peopleOfInterest.size(); i++){
                                String tempPerson = peopleOfInterest.get(i);
                                int personIndex = peopleSet.get(tempPerson);
                                String personGtype = text[personIndex];
                                int temp = Character.getNumericValue(personGtype.charAt(0));
                                variantFreq[temp]++;
                                temp = Character.getNumericValue(personGtype.charAt(2));
                                variantFreq[temp]++;
                            }
                        }
                        numChr = variantFreq[0] + variantFreq[1];
                        freqZero = Math.round(variantFreq[0]/((double)numChr)*10000)/10000.0;
                        freqOne = Math.round(variantFreq[1]/((double)numChr)*10000)/10000.0;
                        if(settings.getOutput().equals("all")){
                            if(!text[4].contains("CN")){
                                genotypes[0][2*index + 6] = Double.toString(freqZero);
                                genotypes[0][2*index + 7] = Double.toString(freqZero);
                                index++;
                            }
                            if (!text[4].contains("CN") && (freqZero >= settings.getMaf() && freqOne >= settings.getMaf())){
                                fileEmpty = false;
                                mapWrite.write(text[0] + "\t" + text[2] + "\t0\t" + text[1]);
                                mapWrite.newLine();
                                infoWrite.write(text[2] + "\t" + text[1]);
                                infoWrite.newLine();
                            }
                        }
                    }
                    if(text[2].contains("indel")){
                        variantPossibilities = retainedPossibility;
                    }
                    // frq file is written here, ESP data is added
                    if(settings.getEsp()){
                            // The following line says: while espData is not empty AND [esp chr is less than 1KG chr OR (esp chr is equal to 1KG chr AND esp pos is less than 1KG pos)] then:
                            while(!espData.isEmpty() && ( (espData.peek().getChrAsInt() < Integer.parseInt(text[0])) ||
                                            ( (espData.peek().getChrAsInt() == Integer.parseInt(text[0])) && (espData.peek().getPos() < Integer.parseInt(text[1])) ) )){
                                    EspInfoObj temp = espData.remove();

                                    // nous n'avons pas pris en compte d'ESP MAF (cette option n'existe pas dans notre version), donc par défaut on va de 0 à 1
                                    if ((temp.getEAFreq() >= 0 && temp.getEAFreq() <= 1) || (temp.getAAFreq() >= 0 && temp.getAAFreq() <= (1))){
                                            String snpName;
                                            if(temp.getSNP().equals(".")){
                                                    snpName = "chr" + temp.getChr() + "_" + temp.getPos();
                                            } else {
                                                    snpName = temp.getSNP();
                                            }
                                            frqFileEmpty = false;
                                            frqWrite.write(temp.getChr() + "\t" + snpName + "\t" + temp.getPos() + "\t" +
                                                            temp.getRefAllele() + "\t" + temp.getAltAllele() + "\t" + "." + "\t" + "." + "\t" +
                                                            df.format(temp.getEAFreq()) + "\t" + df.format(temp.getAAFreq()));
                                            frqWrite.newLine();
                                    }
                            }
                            // The following line says: if espData is not empty AND esp chr equals 1KG chr AND esp pos equals 1KG pos AND it's biallelic
                            if(!espData.isEmpty() && espData.peek().getChrAsInt() == Integer.parseInt(text[0]) && espData.peek().getPos() == Integer.parseInt(text[1]) && variantPossibilities.length == 2){
                                    EspInfoObj temp = espData.remove();///
                                    // 1KG and ESP Ref alleles and Alt alleles match
                                    if (variantPossibilities[0].equals(temp.getRefAllele()) && variantPossibilities[1].equals(temp.getAltAllele())){
                                        if ((freqZero >= settings.getMaf() && freqOne >= settings.getMaf()) ||(temp.getEAFreq() >= 0 && temp.getEAFreq() <= 1) || (temp.getAAFreq() >= 0 && temp.getAAFreq() <= 1)){
                                            frqFileEmpty = false;
                                            frqWrite.write(text[0] + "\t" + text[2] + "\t" + text[1] + "\t" +
                                                variantPossibilities[0] + "\t" + variantPossibilities[1] + "\t" + numChr + "\t" + df.format(freqZero) + "\t" +
                                                df.format(temp.getEAFreq()) + "\t" + df.format(temp.getAAFreq()));
                                            frqWrite.newLine();
                                        }
                                            // 1KG and ESP ref/alt alleles are switched
                                    } else if (variantPossibilities[0].equals(temp.getAltAllele()) && variantPossibilities[1].equals(temp.getRefAllele())){
                                        if ((freqOne >= settings.getMaf() && freqZero >= settings.getMaf())||(temp.getEAFreq() >= 0 && temp.getEAFreq() <= 1) || (temp.getAAFreq() >= 0 && temp.getAAFreq() <= 1)){
                                            frqFileEmpty = false;
                                            frqWrite.write(text[0] + "\t" + text[2] + "\t" + text[1] + "\t" +
                                                variantPossibilities[0] + "\t" + variantPossibilities[1] + "\t" + numChr + "\t" + df.format(freqZero) + "\t" +
                                                df.format(temp.getEAFreqAlt()) + "\t" + df.format(temp.getAAFreqAlt()));
                                            frqWrite.newLine();
                                        }
                                            // ESP does not match with 1KG so ESP omitted
                                    } else{
                                        if (freqOne >= settings.getMaf() && freqZero >= settings.getMaf()){
                                            frqFileEmpty = false;
                                            frqWrite.write(text[0] + "\t" + text[2] + "\t" + text[1] + "\t" +
                                                variantPossibilities[0] + "\t" + variantPossibilities[1] + "\t" + numChr + "\t" + df.format(freqZero) + "\t" +
                                                "." + "\t" + ".");
                                            frqWrite.newLine();
                                        }
                                        espErrorCount++;
                                    }
                            } else if(variantPossibilities.length == 2){
                                if (freqOne >= settings.getMaf() && freqZero >= settings.getMaf()){
                                    frqFileEmpty = false;
                                    frqWrite.write(text[0] + "\t" + text[2] + "\t" + text[1] + "\t" +
                                        variantPossibilities[0] + "\t" + variantPossibilities[1] + "\t" + numChr + "\t" + df.format(freqZero) + "\t" +
                                        "." + "\t" + ".");
                                    frqWrite.newLine();
                                }
                            }
                        } else {
                            if(variantPossibilities.length == 2 && freqOne >= settings.getMaf() && freqZero >= settings.getMaf()){
                                frqFileEmpty = false;
                                frqWrite.write(text[0] + "\t" + text[2] + "\t" + text[1] + "\t" + variantPossibilities[0] + "\t" +
                                    variantPossibilities[1] + "\t" + numChr + "\t" + df.format(freqZero) + "\t" + df.format(freqOne));
                                frqWrite.newLine();
                            }
                        }
                    }
                    // this bracket marks the end of VCF reading
                    //System.out.println("ESP Error Count: " + espErrorCount);
                    // Don't need to have MAF threshold here, because not written to genotypes array if MAF too low
                    if (settings.getOutput().equals("all")){
                            for(int i = 0; i < peopleCounter; i++){
                                    // Write information about individuals
                                    for(int j = 0; j <= 5; j++){
                                        pedWrite.write(genotypes[i+1][j] + "\t");
                                    }
                                    // Write information about genotypes
                                    for(int j = 6; j < index*2 + 6; j++){
                                        if(Double.parseDouble(genotypes[0][j]) >= settings.getMaf() && (1 - Double.parseDouble(genotypes[0][j])) >= settings.getMaf()){
                                            pedWrite.write(genotypes[i+1][j] + "\t");
                                        }
                                    }
                                    pedWrite.newLine();
                            }
                            pedWrite.close();
                            mapWrite.close();
                            infoWrite.close();
                            if (fileEmpty){
                                new File(fileName + ".ped").delete();
                                new File(fileName + ".info").delete();
                                new File(fileName + ".map").delete();
                            }
                    }
                    frqWrite.close();
                    vcfRead.close();
                    File vcfFile = new File(fileName + "_genotypes.vcf");
                    vcfFile.delete();
                    if (frqFileEmpty){
                        freqFile.delete();
                    }
            } catch (IOException e) {
                    //e.printStackTrace();
            }
            setProgress(100);
            System.out.println("Finished");
        }

    }


    // fonctions auxiliaires

    /**
     * Fonction calculant l'évolution du process au fur et à mesure (pour après l'afficher via la Vue).
     * @param processStatus
     */
    public String process(List<String> processStatus){
            int statusIndex = processStatus.size();
            return processStatus.get(statusIndex-1);
            // TODO: Relier cette partie a la vue aussi
            //status.setText(processStatus.get(statusIndex-1));
    }

    /**
     * Fonction qui vérifie qu'une chaîne de caractères représente un entier.
     * @param str la chaîne de caractères à étudier
     * @return vrai ou faux selon que la chaîne de caractères correspond à un entier
     */
    protected boolean isInteger(String str) {
            if (str == null) {
                    return false;
            }
            int length = str.length();
            if (length == 0) {
                    return false;
            }
            for (int i = 0; i < length; i++) {
                    char c = str.charAt(i);
                    if (c < '0' || c > '9') {
                            return false;
                    }
            }
            return true;
    }

    /**
     * Fonctions qui retourne une liste triée de Locus à partir d'une liste non triée. Pour voir la méthode de tri, consulter la classe LocusM.
     * @param queries la liste non-ordonnée de locus
     * @return la liste triée
     */
    private static ArrayList<LocusM> sortByWindow(LocusM[] queries){

            ArrayList<ArrayList<LocusM>> sortedByChr = new ArrayList<>(23);
            for(int i = 0; i < 23; i++){
                    sortedByChr.add(new ArrayList<>());
            }

            for(int i = 0; i < queries.length; i++){
                    sortedByChr.get(queries[i].getChromosome() - 1).add(queries[i]);
            }
            ArrayList<LocusM> sortedQueries = new ArrayList<>();
            for(int i = 0; i < 23; i++){
                    ArrayList<LocusM> currentList = sortedByChr.get(i);
                    Collections.sort(currentList);
                    int j = 0;
                    while(j < currentList.size()){
                            String currentChr = Integer.toString(currentList.get(j).getChromosome());
                            int minPos = currentList.get(j).getStart(), maxPos = currentList.get(j++).getEnd();
                            while(j < currentList.size() && currentList.get(j).getStart() <= maxPos){
                                maxPos = currentList.get(j++).getEnd();
                            }
                            sortedQueries.add(new LocusM(Integer.parseInt(currentChr),minPos,maxPos));
                    }
            }
            return sortedQueries;
    }

    /**
     * Récupère les indices au sein d'une population à partir d'un fichier.
     * @param fileName le nom du fichier
     * @return une Hashmap avec population et indice associé
     * @throws IOException
     */
    public static HashMap<String, Integer> getPopulationIndices(String fileName) throws IOException{
            // Since this seems to stay the same, this could be added to the population txt file. It would
            // probably save 3-4 seconds of run time
            HashMap<String, Integer> peopleSet = new HashMap<>(3500);

            TabixReader tr = new TabixReader(fileName);
            // Get the header info
            String s = tr.readLine();
            while (!s.contains("CHROM")) {
                    s = tr.readLine();
            }
            String[] peopleStringArray = s.split("\t");
            for (int i = 0; i < peopleStringArray.length; i++) {
                    peopleSet.put(peopleStringArray[i], i);
            }

            return peopleSet;
    }

    /**
     * Fonction récupérant les infos sur les gens à partir de la version 3 standard.
     * @param chrNum le numéro du chromosome
     * @return la chaîne de caractères correspondant aux infos sur la personne en question
     */
    public static String getPeopleStringPhase3(String chrNum){
            // helper method for the below method
            String s = null;
            try {
                    //String webAddress = "ftp://ftp-trace.ncbi.nih.gov/1000genomes/ftp/release/20130502/ALL.chr" + chrNum + ".phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
                    String webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/ALL.chr" + chrNum + ".phase3_shapeit2_mvncall_integrated_v5a.20130502.genotypes.vcf.gz";
                    // Prepares objects for file writing
                    TabixReader tr = new TabixReader(webAddress);
                    // Get the header info
                    s = tr.readLine();
                    while (!s.contains("CHROM")) {
                            s = tr.readLine();
                    }
                    //vcfBuffWrite.close();
            } catch (RuntimeException | IOException e) {
                    //e.printStackTrace();
            }
            return s;
    }

    /**
     * Fonction récupérant les infos sur les gens à partir de la version 3 non-standard (ce n'est pas celle par défaut).
     * @param chrNum le numéro du chromosome
     * @return la chaîne de caractères correspondant aux infos sur la personne en question
     */
    public static String getPeopleStringPhase3GRCh38(String chrNum){
            // helper method for the below method
            String s = null;
            try {
                    String webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20130502/supporting/GRCh38_positions/ALL.chr" + chrNum + ".phase3_shapeit2_mvncall_integrated_v3plus_nounphased.rsID.genotypes.GRCh38_dbSNP_no_SVs.vcf.gz";
                    // Prepares objects for file writing
                    TabixReader tr = new TabixReader(webAddress);
                    // Get the header info
                    s = tr.readLine();
                    while (!s.contains("CHROM")) {
                            s = tr.readLine();
                    }
                    //vcfBuffWrite.close();
            } catch (RuntimeException | IOException e) {
                    //e.printStackTrace();
            }
            return s;
    }

    /**
     * Fonction récupérant les infos sur les gens à partir de la version 1 (ce n'est ni la plus récente, ni celle par défaut).
     * @param chrNum le numéro du chromosome
     * @return la chaîne de caractères correspondant aux infos sur la personne en question
     */
    public static String getPeopleStringPhase1(String chrNum){
            // helper method for the below method
            String s = null;
            try {
                    //ftp://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20110521/
                    String webAddress = "http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/release/20110521/ALL.chr" + chrNum + ".phase1_release_v3.20101123.snps_indels_svs.genotypes.vcf.gz";
                    TabixReader tr = new TabixReader(webAddress);
                    // Get the header info
                    s = tr.readLine();
                    while (!s.contains("CHROM")) {
                            s = tr.readLine();
                    }
                    //vcfBuffWrite.close();
            } //e.printStackTrace();
        catch (RuntimeException | IOException e) {
                    //e.printStackTrace();
            }
            return s;
    }


    /**
     * Fonction ayant purement un rôle de test. Il s'agit de s'assurer que les gens sont ordonnés de la bonne manière.
     * @param whichTest la chaîne de caractères représentant les gens à tester
     * @return vrai si le tri est bon
     */
    public static boolean testPeopleOrder(String whichTest){
            // Run this to make sure the people are in the same order between all chromosomes, mainly for testing; never run
            for(int i = 2; i < 23; i++){
                    System.out.println(i);
                    switch (whichTest){
                    case "Phase 1":
                            if(!getPeopleStringPhase1("1").equals(getPeopleStringPhase1(Integer.toString(i)))){
                                    return false;
                            }
                            break;
                    case "Phase 3":
                            if(!getPeopleStringPhase3("1").equals(getPeopleStringPhase3(Integer.toString(i)))){
                                    return false;
                            }
                            break;
                    case "Phase 3 GRCh38":
                            if(!getPeopleStringPhase3GRCh38("1").equals(getPeopleStringPhase3GRCh38(Integer.toString(i)))){
                                    return false;
                            }
                            break;
                    }
            }

            switch (whichTest){
            case "Phase 1":
                    if(!getPeopleStringPhase1("1").equals(getPeopleStringPhase1("X"))){
                            return false;
                    }
                    break;
            case "Phase 3":
                    if(!getPeopleStringPhase3("1").equals(getPeopleStringPhase3("X"))){
                            return false;
                    }
                    break;
            case "Phase 3 GRCh38":
                    if(!getPeopleStringPhase3GRCh38("1").equals(getPeopleStringPhase3GRCh38("X"))){
                            return false;
                    }
                    break;
            }
            return true;
    }
}

