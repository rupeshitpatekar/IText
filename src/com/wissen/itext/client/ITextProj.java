package com.wissen.itext.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wissen.itext.client.widgets.ButtonWidget;
import com.wissen.itext.client.widgets.FrameWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITextProj implements EntryPoint {

    private FlexTable flexTable;

    @Override
    public void onModuleLoad() {
        // TODO Auto-generated method stub
        flexTable = new FlexTable();

       flexTable.setWidget(1, 0, new ButtonWidget());
       flexTable.setWidget(2, 0,new FrameWidget());

        RootPanel.get("pageContent").add(flexTable);

    }

}
