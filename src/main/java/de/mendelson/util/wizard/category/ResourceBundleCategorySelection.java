//$Header: /converteride/de/mendelson/util/wizard/category/ResourceBundleCategorySelection.java 1     1.04.05 11:17 Heller $
package de.mendelson.util.wizard.category;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */


/** 
 * ResourceBundle to localize a mendelson product
 * @author  S.Heller
 * @version $Revision: 1 $
 */
public class ResourceBundleCategorySelection extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

   /**List of messages in the specific language*/
  static final Object[][] contents = {
    {"button.ok", "Ok"},   
    {"button.cancel", "Cancel"},        
  };		
  
}