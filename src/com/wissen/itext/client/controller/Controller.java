/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 6:17:49 PM
Controller.java
 */
package com.wissen.itext.client.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wissen.itext.client.controller.Controller;
import com.wissen.itext.client.services.PdfService;
import com.wissen.itext.client.services.PdfServiceAsync;
import com.wissen.itext.client.observers.PdfObserver;

/**
 * @author Rupeshit Patekar
 *
 * Create Date : 18-Jan-2010
 */
public class Controller {
    /**
     * Create a remote service proxy to talk to the server-side pdf
     * service.
     */
    private final PdfServiceAsync pdfService = GWT
            .create(PdfService.class);
    private static Controller _instance;

    private Controller() {
    }
    public static synchronized Controller getInstance(){
        if (_instance == null) {
            _instance = new Controller();
        }
        return _instance;
    }
    public PdfServiceAsync getPdfService() {
        return pdfService;
    }

    // ---------------------- Observers -------------------------------
    private List<PdfObserver> pdfObservers = new ArrayList<PdfObserver>();

    // --------------- Registration/DeRegitration methods --------------
    public void addPdfObserver(PdfObserver observer) {
        pdfObservers.add(observer);
    }
    public void removePdfObserver(PdfObserver observer) {
        pdfObservers.remove(observer);
    }

    // ---------------------- Controller's Methods ---------------------
    public void generatePdf() {
        pdfService.generatePdf(pdfCallback);
    }
    

    // -------------------- Call Back classes ---------------------------
    AsyncCallback<String> pdfCallback = new AsyncCallback<String>() {
        @Override
        public void onFailure(Throwable caught) {
            GWT.log("Error in user validation", caught);
            for (PdfObserver observer : pdfObservers) {
                observer.notifyPdfGeneratonFail("Login failed: " + caught);
            }
        }
        @Override
        public void onSuccess(String url) {
            
                for (PdfObserver observer : pdfObservers) {
                    observer.notifyPdfGenratedSuccessfully(url);
                }
            
        }
    };

}
