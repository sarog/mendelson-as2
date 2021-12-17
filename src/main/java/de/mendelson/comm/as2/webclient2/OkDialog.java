//$Header: /as2/de/mendelson/comm/as2/webclient2/OkDialog.java 6     4.12.20 9:11 Heller $
package de.mendelson.comm.as2.webclient2;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog that display an ok button and will close if you click it, modal. DO
 * NOT use it for login purpose as the modality is just rendered on the client
 * side and is not attack-safe
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class OkDialog extends Window implements Button.ClickListener {

    private Button okButton = new Button("Ok");
    private int width = 100;
    private int height = 100;

    public OkDialog(int width, int height, String caption) {
        super(caption);
        this.width = width;
        this.height = height;
    }

    public void init(boolean displayOkButton) {
        this.setModal(true);
        VerticalLayout okDialogLayout = new VerticalLayout();
        HorizontalLayout contentLayout = new HorizontalLayout();
        AbstractComponent contentPanel = this.getContentPanel();
        contentLayout.addComponent(contentPanel);
        contentLayout.setMargin(true);
        contentLayout.setSizeFull();
        okDialogLayout.addComponent(contentLayout);
        if (displayOkButton) {
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.setSizeFull();
            buttonLayout.setMargin(new MarginInfo(false, true, true, false));
            buttonLayout.addComponent(this.okButton);
            buttonLayout.setComponentAlignment(this.okButton, Alignment.MIDDLE_RIGHT);
            this.okButton.addClickListener(this);
            okDialogLayout.addComponent(buttonLayout);
            okDialogLayout.setExpandRatio(buttonLayout, 0.0f);
        }
        okDialogLayout.setExpandRatio(contentLayout, 1.0f);
        this.setContent(okDialogLayout);
        this.setHeight(this.height + "px");
        this.setWidth(this.width + "px");
    }

    /**
     * Could be overwritten, contains the content to display
     */
    public AbstractComponent getContentPanel() {
        Panel panel = new Panel();
        return (panel);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        this.close();
    }
}
