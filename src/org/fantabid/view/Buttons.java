package org.fantabid.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public final class Buttons {
    
    private Buttons() { }
    
    public static Button listButton(final String text) {
        final Button b = new Button(text);
        b.setAlignment(Pos.CENTER);
        b.setTextAlignment(TextAlignment.CENTER);
        b.setFont(Font.font(14));
        b.setPadding(new Insets(10, 10, 10, 10));
        b.setPrefWidth(500);
        return b;
    }

}
