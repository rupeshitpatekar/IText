/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 6:25:26 PM
PdfOnserver.java
 */
package com.wissen.itext.client.observers;


/**
 * @author Rupeshit Patekar
 *
 * Create Date : 18-Jan-2010
 */
public interface PdfObserver {

    /**
     * Notify the observer pdf generated  successfully
     */
    void notifyPdfGenratedSuccessfully(String url);

    /**
     * Notify the observer pdf generation failed  with error message
     * 
     * @param errorMessage
     * */
    void notifyPdfGeneratonFail(String errorMessage);

}
