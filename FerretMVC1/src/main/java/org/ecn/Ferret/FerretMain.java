/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecn.Ferret;

import org.ecn.Ferret.Controller.*;
import org.ecn.Ferret.View.*;
import org.ecn.Ferret.Model.*;
import org.ecn.PAPPL6_2020.Controller.UpdateCTRL;
/**
 *
 * @Authors: Mathieu JUNG-MULLER & Bozhou WANG
 */
public class FerretMain {
    

    public static void main(String[] args) {
      GUI gui = new GUI();
      UpdateCTRL updateCtrl = new UpdateCTRL((UpdateGUI)gui);
      RunCTRL r = new RunCTRL();
      System.out.println(r);
      System.out.println("C'est bon ça marche.");
    }
}
