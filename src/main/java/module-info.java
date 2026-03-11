module org.battleshipprojectp2p {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.desktop;

    opens org.battleshipprojectp2p to javafx.fxml;
    exports org.battleshipprojectp2p;
    opens org.battleshipprojectp2p.GUI.gameConnectView to javafx.fxml;
    exports org.battleshipprojectp2p.GUI.gameConnectView;
    opens org.battleshipprojectp2p.GUI.startView to javafx.fxml;
    exports org.battleshipprojectp2p.GUI.startView;
    opens org.battleshipprojectp2p.GUI.gameView to javafx.fxml;
    exports org.battleshipprojectp2p.GUI.gameView;
    exports org.battleshipprojectp2p.game;
    exports org.battleshipprojectp2p.game.gameDto;
    exports org.battleshipprojectp2p.game.observer;
    exports org.battleshipprojectp2p.game.board;
    exports org.battleshipprojectp2p.game.board.boardRules;
    exports org.battleshipprojectp2p.game.player;
    exports org.battleshipprojectp2p.game.ship;
    exports org.battleshipprojectp2p.error;
    exports org.battleshipprojectp2p.GUI.boardModel;
    opens org.battleshipprojectp2p.GUI.boardModel to javafx.fxml;
    exports org.battleshipprojectp2p.common;

}