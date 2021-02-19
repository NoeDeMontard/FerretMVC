/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe destinée au traitement sur le serveur NCBI.
 *
 * @author Mathieu JUNG-MULLER
 * @author Bozhou WANG
 * @author Noe DE MONTARD
 */
public class TraitementNCBI extends Traitement {
    // aucune des méthodes de cette classe n'a subi de modification de notre part, elles ont simplement été déplacées
    // nous avons simplement remplacé l'ancienne classe InputRegion et ses occurrences ici par LocusM

    // le serveur NCBI est organisé sous forme d'arbres impliquant des noeuds père et fils, d'où l'importance d'utiliser des méthodes prédéfinies permettant de naviguer au sein de cet ensemble
    // pour la théorie là-dessus, ce que nous avons vu sur les arbres en MADIS est suffisant
    /**
     * Fonction permettant d'obtenir un noeud fils dont on connait le nom à
     * partir d'un noeud père.
     *
     * @param parentNode noeud père
     * @param childName nom du fils
     * @return nom fils recherché
     */
    private static Node getChildByName(Node parentNode, String childName) {
        Node toReturn = null;
        NodeList childrenNodeList = parentNode.getChildNodes();
        int listLength = childrenNodeList.getLength();
        for (int i = 0; i < listLength; i++) {
            if (childrenNodeList.item(i).getNodeName().equals(childName)) {
                toReturn = childrenNodeList.item(i);
                break;
            }
        }
        return toReturn;
    }

    /**
     * Fonction permettant de récupérer un noeud spécifique.
     *
     * @param test
     * @param typeDesired
     * @param headingDesired
     * @param nodeNameToRetrieve
     * @return le noeud désiré
     */
    private static Node xmlCommentFinder(NodeList test, String typeDesired, String headingDesired, String nodeNameToRetrieve) {
        Node toReturn = null;
        int listLength = test.getLength();
        Node desiredNode = null;
        for (int i = 0; i < listLength; i++) {
            Node currentNode = test.item(i);
            if (xmlCommentChecker(currentNode, typeDesired, headingDesired)) {
                desiredNode = currentNode;
                break;
            }
        }
        if (desiredNode != null) {
            NodeList desiredNodeList = desiredNode.getChildNodes();
            listLength = desiredNodeList.getLength();
            for (int i = 0; i < listLength; i++) {
                if (desiredNodeList.item(i).getNodeName().equals(nodeNameToRetrieve)) {
                    toReturn = desiredNodeList.item(i);
                }
            }
        }
        return toReturn;
    }

    /**
     * Fonction qui permet de vérifier que ce qu'on cherche est accessible à
     * partir d'un point de départ.
     * Utilitaire pour xmlCommentFinder où il s'agit de le trouver.
     *
     * @param test
     * @param typeDesired
     * @param headingDesired
     * @return la valeur de vérité du critère d'accessibilité
     */
    private static boolean xmlCommentChecker(Node test, String typeDesired, String headingDesired) {
        boolean typeMatch = false;
        boolean headingMatch = false;
        if (typeDesired.equals("any")) {
            typeMatch = true;
        }
        if (headingDesired.equals("any")) {
            headingMatch = true;
        }
        NodeList commentTagList = test.getChildNodes();
        int commentLength = commentTagList.getLength();
        for (int j = 0; j < commentLength; j++) {
            String commentString = commentTagList.item(j).getNodeName();
            switch (commentString) {
                case "Gene-commentary_type":
                    NodeList geneCommentaryType = commentTagList.item(j).getChildNodes();
                    for (int k = 0; k < geneCommentaryType.getLength(); k++) {
                        if (geneCommentaryType.item(k).getNodeType() == Node.TEXT_NODE) {
                            typeMatch = geneCommentaryType.item(k).getNodeValue().equals(typeDesired);
                        }
                    }
                    break;
                case "Gene-commentary_heading":
                    NodeList geneCommentaryHeading = commentTagList.item(j).getChildNodes();
                    for (int k = 0; k < geneCommentaryHeading.getLength(); k++) {
                        if (geneCommentaryHeading.item(k).getNodeType() == Node.TEXT_NODE) {
                            headingMatch = geneCommentaryHeading.item(k).getNodeValue().equals(headingDesired);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return typeMatch && headingMatch;
    }

    /**
     * Fonction permettant de récupérer une information au sein du serveur NCBI
     * sous forme de FoundGeneAndRegion.
     *
     * @param geneListArray liste des noms des gènes recherchés
     * @param defaultHG format de Human Genome Version
     * @return FoundGeneAndRegion format du retour
     */
    public static FoundGeneAndRegion getQueryFromGeneID(String[] geneListArray, boolean defaultHG) {
        return getQueryFromGene(geneListArray, defaultHG, false);
    }

    public static FoundGeneAndRegion getQueryFromGeneName(String[] geneListArray, boolean defaultHG) {
        return getQueryFromGene(geneListArray, defaultHG, true);
    }

    /**
     * Fonction permettant de récupérer une information au sein du serveur NCBI
     * sous forme de FoundGeneAndRegion.
     *
     * @param geneListArray liste des noms des gènes recherchés
     * @param defaultHG format de Human Genome Version
     * @param isNameAndNotID requete de type nom si vrai, de type ID si faux
     * @return FoundGeneAndRegion format du retour
     */
    private static FoundGeneAndRegion getQueryFromGene(String[] geneListArray, boolean defaultHG, boolean isNameAndNotID) {

        if (geneListArray.length == 0) {
            return null;
        }
        String geneListGeneIndicator;
        String geneListGeneSeparator;
        if (isNameAndNotID) {
            geneListGeneIndicator = "[GENE]";
            geneListGeneSeparator = "+OR+";
        } else {
            geneListGeneIndicator = "";
            geneListGeneSeparator = ",";
        }
        StringBuilder geneList = new StringBuilder();
        for (int i = 0; i < geneListArray.length - 1; i++) {
            geneList.append(geneListArray[i] + geneListGeneIndicator + geneListGeneSeparator);
        }
        geneList.append(geneListArray[geneListArray.length - 1] + geneListGeneIndicator);
        String geneString = "";
        DocumentBuilder docBldr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        dbf.setNamespaceAware(true);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setCoalescing(false);
        StringBuilder foundGenes = new StringBuilder();
        ArrayList<LocusM> queriesArrayList = new ArrayList<>();
        if (isNameAndNotID) {
            try {
                docBldr = dbf.newDocumentBuilder();

                String ncbiEutilsSearchURL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=gene&term=" + geneList + "+AND+Homo+sapiens[ORGN]";
                org.w3c.dom.Document doc = docBldr.parse(ncbiEutilsSearchURL);

                Node resultsNode = doc.getElementsByTagName("eSearchResult").item(0);
                Node idListNode = getChildByName(resultsNode, "IdList");
                NodeList idsNodeList = idListNode.getChildNodes();
                int listLength = idsNodeList.getLength();

                if (listLength == 0) { // nothing found so return null; might have to do something more advanced here later
                    return null;
                }
                StringBuilder geneListIDBuffer = new StringBuilder();
                for (int i = 0; i < listLength; i++) {
                    geneListIDBuffer.append(idsNodeList.item(i).getFirstChild().getNodeValue());
                    geneListIDBuffer.append(',');
                }
                geneListIDBuffer.trimToSize();
                geneListIDBuffer = geneListIDBuffer.deleteCharAt(geneListIDBuffer.length() - 1);
                geneString = geneListIDBuffer.toString();
            } catch (ParserConfigurationException | IOException | SAXException e) {
                return null;
            }
        }
        try {
            docBldr = dbf.newDocumentBuilder();
            // see if there are more than 500 in the list
            String ncbiEutilsFetchURL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=gene&id=" + geneString + "&retmode=xml";
            org.w3c.dom.Document doc = docBldr.parse(ncbiEutilsFetchURL);
            // Do the next steps in a loop for each gene id

            NodeList entrezgeneNodeList = doc.getElementsByTagName("Entrezgene");
            int listLength = entrezgeneNodeList.getLength();

            for (int i = 0; i < listLength; i++) {
                Node currentEntrezNode = entrezgeneNodeList.item(i);
                Node trackNode = getChildByName(currentEntrezNode, "Entrezgene_track-info");
                Node geneTrackNode = getChildByName(trackNode, "Gene-track");
                String currentGene;
                if (isNameAndNotID) {
                    Node geneStatusNode = getChildByName(geneTrackNode, "Gene-track_status");
                    NamedNodeMap attributes = geneStatusNode.getAttributes();
                    if (attributes.getLength() != 1) {
                        continue;
                    }
                    if (!attributes.item(0).getNodeValue().equals("live")) {
                        continue;
                    }
                    Node geneNode = getChildByName(currentEntrezNode, "Entrezgene_gene");
                    Node geneRefNode = getChildByName(geneNode, "Gene-ref");
                    Node geneRefLocusNode = getChildByName(geneRefNode, "Gene-ref_locus");
                    String geneNameFound = geneRefLocusNode.getFirstChild().getNodeValue();
                    if (!Arrays.asList(geneListArray).contains(geneNameFound.toUpperCase())) {
                        continue;
                    }
                    currentGene = geneNameFound;
                } else {
                    currentGene = getChildByName(geneTrackNode, "Gene-track_geneid").getFirstChild().getNodeValue();

                }
                // Paramètres de xmlCommentFinder revenant plusieurs fois
                String xmlGeneComment= "Gene-commentary_comment";
                
                // This is a horrible line. Mainly, this is horrible because I don't think nesting makes sense here but if not, would
                // result in a lot of confusing variable names for nodes
                Node subSourceNodeForChr = getChildByName(getChildByName(getChildByName(getChildByName(getChildByName(currentEntrezNode, "Entrezgene_source"),
                        "BioSource"), "BioSource_subtype"), "SubSource"), "SubSource_name");
                String chromosome = subSourceNodeForChr.getFirstChild().getNodeValue();
                NodeList commentList = getChildByName(currentEntrezNode, "Entrezgene_comments").getChildNodes();
                Node geneLocationHistoryNode = xmlCommentFinder(commentList, "254", "Gene Location History", xmlGeneComment);
                Node primaryAssemblyNode;
                if (defaultHG) {
                    Node annotationRelease105Node = xmlCommentFinder(geneLocationHistoryNode.getChildNodes(), "254", "Homo sapiens Annotation Release 105", xmlGeneComment);
                    if (annotationRelease105Node == null) {
                        continue;
                    }
                    Node grch37p13Node = xmlCommentFinder(annotationRelease105Node.getChildNodes(), "24", "GRCh37.p13", xmlGeneComment);
                    primaryAssemblyNode = xmlCommentFinder(grch37p13Node.getChildNodes(), "25", "Primary Assembly", xmlGeneComment);
                } else {
                    Node annotationRelease107Node = xmlCommentFinder(geneLocationHistoryNode.getChildNodes(), "254", "Homo sapiens Annotation Release 107", xmlGeneComment);
                    if (annotationRelease107Node == null) {
                        continue;
                    }
                    Node grch38p2Node = xmlCommentFinder(annotationRelease107Node.getChildNodes(), "24", "GRCh38.p2", xmlGeneComment);
                    primaryAssemblyNode = xmlCommentFinder(grch38p2Node.getChildNodes(), "25", "Primary Assembly", xmlGeneComment);
                }
                Node genomicAssemblyNode = xmlCommentFinder(primaryAssemblyNode.getChildNodes(), "1", "any", "Gene-commentary_seqs");
                Node seqLocNode = getChildByName(genomicAssemblyNode, "Seq-loc");
                Node seqLocIntNode = getChildByName(seqLocNode, "Seq-loc_int");
                Node seqIntervalNode = getChildByName(seqLocIntNode, "Seq-interval");
                NodeList sequenceLocationNodeList = seqIntervalNode.getChildNodes();
                int listLocationLength = sequenceLocationNodeList.getLength();
                String startPos = "";
                String endPos = "";
                for (int j = 0; j < listLocationLength; j++) {
                    Node currentNode = sequenceLocationNodeList.item(j);
                    if (currentNode.getNodeName().equals("Seq-interval_from")) {
                        startPos = currentNode.getFirstChild().getNodeValue();
                    }
                    if (currentNode.getNodeName().equals("Seq-interval_to")) {
                        endPos = currentNode.getFirstChild().getNodeValue();
                    }
                }
                if (!chromosome.equals("X") && !chromosome.equals("Y") && !chromosome.equals("MT")) {
                    queriesArrayList.add(new LocusM(Integer.parseInt(chromosome), Integer.parseInt(startPos), Integer.parseInt(endPos)));
                    foundGenes.append(currentGene + ",");
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            return null;
        }

        LocusM[] queriesFound = queriesArrayList.toArray(new LocusM[queriesArrayList.size()]);

        foundGenes.deleteCharAt(foundGenes.length() - 1);
        return new FoundGeneAndRegion(foundGenes.toString(), queriesFound, queriesFound.length == geneListArray.length);
    }
}
