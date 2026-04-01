package org.battleshipprojectp2p.GUI.utils;

import javafx.scene.control.TextField;

public class InputsCheckUtil {
    public static boolean checkIsNumber(String text) {
        return text.matches("^\\d+$");
    }

    public static boolean checkName(String text) {
        return text.matches("^[a-zA-Z\\d@!&]+$");
    }


    public static void outlineWrongInput(boolean isOk, TextField field) {
        if (!isOk) {
            field.setStyle("-fx-border-color: red;");
        } else {
            field.setStyle("-fx-border-color: black;");
        }
    }
}
