package org.battleshipprojectp2p.GUI.gameView;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.HostServerAddress;

public class HostConnectionWaitingMask extends VBox {

    public HostConnectionWaitingMask(HostServerAddress inetAddress) {
        String notice = "Please wait till opponent connect to the battle";
        final Label text = new Label(notice);
        final Label ip = new Label("IP: " + inetAddress.ip());
        final Label port = new Label("Port: " + inetAddress.port());

        this.getChildren().addAll(text, ip, port);
        this.setSpacing(15);
        this.setPadding(new Insets(15));
    }
}
