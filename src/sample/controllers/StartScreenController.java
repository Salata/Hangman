package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.WordDAO;

import java.io.IOException;

public class StartScreenController {


    public Label startScreenLabel;
    public ImageView manImage;

    @FXML
    public void initialize(){
        startScreenLabel.setText(WordDAO.getStartScreenLabel());


        //display image
        Image man = new Image("sample/resources/man.png");
        manImage.setImage(man);
        //show first frame
        final int imageWidth = 75;
        final int imageHeight = 200;
        Rectangle2D rectangle = new Rectangle2D(WordDAO.getResult() * imageWidth, 0, imageWidth, imageHeight);
        manImage.setViewport(rectangle);
    }
    //buttons
    public void changeScreen(ActionEvent actionEvent) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/vision/gameScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //this line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setStartScreenLabel(Label startScreenLabel) {
        this.startScreenLabel = startScreenLabel;
    }
}
