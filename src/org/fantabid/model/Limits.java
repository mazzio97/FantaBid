package org.fantabid.model;

import java.util.Optional;

import javafx.scene.control.TextFormatter;

public final class Limits {

    public final static int MAX_NAME_CHARS = 30;
    public final static int MAX_SURNAME_CHARS = 30;
    public final static int MAX_USERNAME_CHARS = 30;
    public final static int MAX_PASSWORD_CHARS = 30;
    
    public static TextFormatter<String> getTextFormatter(int limit) {
        return new TextFormatter<>(s -> Optional.of(s)
                                                .filter(t -> t.getControlNewText().length() <= limit)
                                                .orElse(null));
    }
}
