package org.fantabid.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public final class Buttons {
    
    public static final Image REFRESH_ICON = new  Image("org/fantabid/images/Refresh.png", 30, 30, true, true);
    public static final ImageView REFRESH_BUTTON_GRAPHIC = new ImageView(REFRESH_ICON);
    
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
