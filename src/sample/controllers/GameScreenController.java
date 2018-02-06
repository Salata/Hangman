package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import sample.WordDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class GameScreenController {
    public Label errorLabel;
    public Label wordLabel;
    public TextField textEntered;
    public ImageView manImage;

    private String wordToSolve;
    private List<String> wordToSolveAsChar = new ArrayList<>();
    private List<String> lettersGuessed = new ArrayList<>();

    //when score hits 6, you lose
    private int score = 0;

    @FXML
    public void initialize() {
        Random generator = new Random();
        int randomNumber = generator.nextInt(WordDAO.getWords().size() - 1);
        wordToSolve = WordDAO.getWords().get(randomNumber);

        //divide word into chars
        for (int i = 0; i < wordToSolve.length(); i++) {
            wordToSolveAsChar.add(String.valueOf(wordToSolve.charAt(i)));
            lettersGuessed.add("_");
        }

        displayKnownLetters();

        //display image
        Image man = new Image("sample/resources/man.png");
        manImage.setImage(man);
        //show first frame
        displayImage();
    }

    public void checkValue(ActionEvent actionEvent) {
        String characterEntered;
        String buffer;
        buffer = textEntered.getText().trim();
        textEntered.setText("");
        if (Pattern.matches("[a-zA-Z].*", buffer)) {
            characterEntered = String.valueOf(buffer.charAt(0));
            errorLabel.setText("");
            isValueCorrect(characterEntered, actionEvent);
        } else {
            errorLabel.setText("WRONG VALUE ENTERED");
        }

    }

    private void isValueCorrect(String characterEntered, ActionEvent actionEvent) {
        boolean anyHit = false;
        for (int i = 0; i < wordToSolve.length(); i++) {
            if (wordToSolveAsChar.get(i).equals(characterEntered) && !lettersGuessed.get(i).equals(characterEntered)) {
                lettersGuessed.set(i, characterEntered);
                anyHit = true;
            }
        }
//      check if player lost
        if (!anyHit) {
            score++;
            displayImage();
            if (score == 6) {
                //this will change image on start screen to loser image
                WordDAO.setResult(6);
                System.out.println("LOST");
                try {
                    changeScreen(actionEvent, false);
                } catch (IOException ioe) {
                    System.out.println("IOException IOIOIOIOIOIO");
                }
            }
        }

//      check if players won the entire game
        label:
        {
            for (int i = 0; i < wordToSolve.length(); i++) {
                if (!wordToSolveAsChar.get(i).equals(lettersGuessed.get(i))) {
                    break label;
                }
            }
            //this will change image on start screen to winner image
            WordDAO.setResult(0);
            System.out.println("WON");
            try {
                changeScreen(actionEvent, true);
            } catch (IOException ioe) {
                System.out.println("IOException IOIOIOIOIOIO");
            }
        }
        displayKnownLetters();
    }

    private void displayKnownLetters() {
        String wordToDisplay;
        StringBuilder buffer = new StringBuilder();
        for (String x : lettersGuessed) {
            buffer.append(x);
        }
        wordToDisplay = buffer.toString();
        wordLabel.setText(wordToDisplay);
    }

    private void changeScreen(ActionEvent actionEvent, boolean result) throws IOException {
        //set the label on the screen to show result; true is won
        if (result) {
            WordDAO.setStartScreenLabel("You've Won!");
        } else {
            WordDAO.setStartScreenLabel("You've Lost!");
        }
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/vision/StartScreen.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    private void displayImage() {
        final int imageWidth = 75;
        final int imageHeight = 200;
        Rectangle2D rectangle = new Rectangle2D(score * imageWidth, 0, imageWidth, imageHeight);
        manImage.setViewport(rectangle);
    }
}
