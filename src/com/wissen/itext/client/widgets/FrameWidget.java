/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 1:50:20 PM
FrameWidget.java
 */
package com.wissen.itext.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;
import com.wissen.itext.client.controller.Controller;
import com.wissen.itext.client.observers.PdfObserver;


/**
 * @author Rupeshit Patekar
 *
 * Create Date : 19-Jan-2010
 */
public class FrameWidget extends Composite implements PdfObserver {

    private Frame pdfFrame;
    
    public FrameWidget(){
        pdfFrame = new Frame();
        pdfFrame.setWidth("600%");
        pdfFrame.setHeight("1000px");
        
        initWidget(pdfFrame);
        Controller.getInstance().addPdfObserver(this);
    }
    @Override
    public void notifyPdfGeneratonFail(String errorMessage) {
        // TODO Auto-generated method stub
        Window.alert(errorMessage);
    }

    @Override
    public void notifyPdfGenratedSuccessfully(String url) {
        // TODO Auto-generated method stub
        pdfFrame.setUrl(url);
    }

}
