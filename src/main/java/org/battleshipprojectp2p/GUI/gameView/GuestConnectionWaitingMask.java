package org.battleshipprojectp2p.GUI.gameView;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GuestConnectionWaitingMask extends VBox {

    public GuestConnectionWaitingMask() {
        String notice = "Please wait till battle is loading";
        final Label text = new Label(notice);

        this.getChildren().addAll(text);
        this.setSpacing(15);
        this.setPadding(new Insets(15));
    }
}
