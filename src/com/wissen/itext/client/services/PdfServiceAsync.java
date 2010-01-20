/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 6:15:20 PM
PdfServiceAsync.java
 */
package com.wissen.itext.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Rupeshit Patekar
 *
 * Create Date : 18-Jan-2010
 */
public interface PdfServiceAsync {
    /***
     * 
     * @param url
     * @param callback
     */
    void generatePdf(AsyncCallback<String> callback);
}
