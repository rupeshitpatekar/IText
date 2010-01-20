/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 11:25:07 AM
PdfService.java
 */
package com.wissen.itext.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * @author Rupeshit Patekar
 *
 * Create Date : 18-Jan-2010
 */
@RemoteServiceRelativePath("greet")
public interface PdfService extends RemoteService  {

    /***
     * 
     * @param url
     * @return
     */
    public String generatePdf();
}
