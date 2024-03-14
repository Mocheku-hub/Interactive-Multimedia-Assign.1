package com.example.last2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class HelloApplication extends Application {
    private int currentIndex = 0;
    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a GridPane to hold the images
            BorderPane root = new BorderPane();
            GridPane gridPane = new GridPane();
            gridPane.getStyleClass().add("grid-pane");

            //root.setCenter(gridPane);

            gridPane.setHgap(10);
            gridPane.setVgap(10);

            // Create a toolbar with label and buttons
            HBox toolbar = new HBox();
            Label label = new Label("Image Gallery");
            Button shareButton = new Button("Share");
            Button deleteButton = new Button("Delete");
            Button searchButton = new Button("Search");

            deleteButton.setStyle("-fx-background-color: red");

            toolbar.getChildren().add(label);

            // create HBox to hold buttons
            HBox buttonBox = new HBox(shareButton, deleteButton, searchButton);

            // add button box to the toolbar and set alignment
            toolbar.getChildren().add(buttonBox);
            HBox.setHgrow(buttonBox, Priority.ALWAYS);
            buttonBox.setStyle("-fx-alignment: baseline_right");

            // add toolbar to the top of the borderPane
            root.setTop(toolbar);
            root.setCenter(gridPane);

            // Array of file paths for different images
            String[] imagePaths = {
                    "src/main/resources/com/example/last2/Afghanistan.png",
                    "src/main/resources/com/example/last2/Albania.png",
                    "src/main/resources/com/example/last2/Algeria.png",
                    "src/main/resources/com/example/last2/Andorra.png",
                    "src/main/resources/com/example/last2/Antarctica.png",
                    "src/main/resources/com/example/last2/Antigua_and_Barbuda.png",
                    "src/main/resources/com/example/last2/Argentina.png",
                    "src/main/resources/com/example/last2/Bahrain.png",
                    "src/main/resources/com/example/last2/Bangladesh.png",
                    "src/main/resources/com/example/last2/Barbados.png",
                    "src/main/resources/com/example/last2/Belarus.png",
                    "src/main/resources/com/example/last2/Bahamas.png"
            };

            // Load and display four images
            for (int i = 0; i < 12; i++) {
                FileInputStream url = new FileInputStream(imagePaths[i]);
                Image pic = new Image(url);
                ImageView imageView = new ImageView(pic);
                imageView.setFitWidth(200); // Set width
                imageView.setFitHeight(200); // Set height
                imageView.getStyleClass().add("image-view");

                imageView.setPickOnBounds(true);

                int finalI = i;

                // Add event handler for image click
                // Inside the event handler for image click
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Create a new stage to display the clicked image
                        Stage imageStage = new Stage();
                        ImageView clickedImageView = new ImageView(pic);
                        clickedImageView.setPreserveRatio(true);
                        clickedImageView.setFitWidth(1500);
                        clickedImageView.setFitHeight(700);
                        //clickedImageView.setResizable(false);

                        // Create a Pane as the root node for the image window
                        Pane imagePane = new Pane();
                        imagePane.getChildren().add(clickedImageView); // Add clickedImageView to the Pane

                        // Create a back button
                        Button backButton = new Button("Back");
                        backButton.setLayoutX(10);
                        backButton.setLayoutY(10);
                        backButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Close the current image window
                                imageStage.close();
                            }
                        });
                        imagePane.getChildren().add(backButton); // Add the back button to the Pane

                        // Create navigation buttons
                        Button previousButton = new Button("Previous");
                        previousButton.getStyleClass().add("navigationButton");
                        previousButton.setLayoutX(10);
                        previousButton.setLayoutY(340);
                        previousButton.setOnAction(event1 -> {
                            currentIndex = (finalI - 1 + imagePaths.length) % imagePaths.length;
                            //imageView.setImage(imagePaths[currentIndex]);
                            try {
                                clickedImageView.setImage(new Image(new FileInputStream(imagePaths[currentIndex])));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });


                        Button nextButton = new Button("Next");
                        nextButton.getStyleClass().add("navigationButton");
                        nextButton.setLayoutX(900);
                        nextButton.setLayoutY(340);
                        nextButton.setOnAction(event1 -> {
                            currentIndex = (finalI + 1) % imagePaths.length;
                            //imageView.setImage(Image.fromPlatformImage(imagePaths[currentIndex]));
                            try {
                                clickedImageView.setImage(new Image(new FileInputStream(imagePaths[currentIndex])));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        imagePane.getChildren().addAll(previousButton, nextButton); // Add the navigation to the pane




                        // Create a Scene with the Pane as the root
                        Scene imageScene = new Scene(imagePane);
                        imageScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                        imageStage.setScene(imageScene);
                        imageStage.show();
                    }
                });

                // Add the image view to the grid pane
                gridPane.add(imageView, i % 6, i / 6);
            }

            // Create a scene and place it in the stage
            Scene scene = new Scene(root, 800, 400);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle("Image Gallery"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage.
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in placing nodes in images");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}