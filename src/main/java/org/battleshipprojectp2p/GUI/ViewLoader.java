package org.battleshipprojectp2p.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.battleshipprojectp2p.BattleshipApplication;
import org.battleshipprojectp2p.GUI.gameView.GameViewController;
import org.battleshipprojectp2p.service.AbstractService;

import java.util.Objects;

import static org.battleshipprojectp2p.BattleshipApplication.getPrimaryStage;

public class ViewLoader {

    public static void loadNewView(String fxmlFile) {
        if (fxmlFile == null || fxmlFile.isEmpty()) return;

        if (!fxmlFile.endsWith(".fxml")) {
            throw new IllegalArgumentException("The fxml file must end with .fxml");
        }

        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(BattleshipApplication.class.getResource(fxmlFile)));
            Stage stage = getPrimaryStage();
            Parent root = stage.getScene().getRoot();
            if (root instanceof BorderPane) {
                ((BorderPane) root).setCenter(parent);
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadGameView(AbstractService service) {
        IO.println(service.getClass());
        try {
            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(BattleshipApplication.class
                            .getResource("game-view.fxml"))
            );
            loader.setControllerFactory(type -> {
                if (type == GameViewController.class) {
                    return new GameViewController(service);
                }
                try {
                    return type.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            Parent parent = loader.load();

            Stage stage = getPrimaryStage();
            Parent root = stage.getScene().getRoot();
            if (root instanceof BorderPane) {
                ((BorderPane) root).setCenter(parent);
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
