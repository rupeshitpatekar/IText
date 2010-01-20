/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 5:10:17 PM
ButtonWidget.java
 */
package com.wissen.itext.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wissen.itext.client.controller.Controller;

/**
 * @author Rupeshit Patekar
 *
 * Create Date : 18-Jan-2010
 */
public class ButtonWidget extends Composite  {
private Button generatePdf;
private HorizontalPanel panel;

public ButtonWidget(){
    generatePdf=new Button("Generate PDF");
    panel=new HorizontalPanel();
    panel.add(generatePdf);
    
    generatePdf.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            generatePdf();
        }
    });

    initWidget(panel);
}
public void generatePdf(){
    Controller.getInstance().generatePdf();
}
}
