/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret.Model;

import javax.swing.*;

/**
 * Classe générique de traitement. Se décline en sous-classes pour les trois serveurs sur lesquels effectuer des requêtes.
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG & Imane SALMI & Imane TAHIRI
 */
public abstract class Traitement extends SwingWorker<Integer, String> {}
