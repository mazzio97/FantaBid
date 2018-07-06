package org.fantabid.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Labels {
    
    public static Label listLabel(final String text) {
        final Label l = new Label(text);
        l.setAlignment(Pos.CENTER);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setFont(Font.font(18));
        l.setPrefHeight(40);
        return l;
    }
}
