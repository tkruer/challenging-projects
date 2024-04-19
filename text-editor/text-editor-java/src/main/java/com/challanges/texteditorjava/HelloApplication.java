package com.challanges.texteditorjava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloApplication extends Application {

  private TextArea textArea = new TextArea();

  @Override
  public void start(Stage primaryStage) {
    // Setup the root pane
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 800, 600);

    // Setup the sidebar with controls
    VBox sidebar = new VBox(10);
    Button newFileButton = new Button("New File");
    Button openFileButton = new Button("Open File");
    Button saveFileButton = new Button("Save File");

    sidebar.getChildren().addAll(newFileButton, openFileButton, saveFileButton);
    root.setLeft(sidebar);

    // Setup the text area in the center
    textArea.setWrapText(true);
    root.setCenter(textArea);

    // Handlers for the buttons
    newFileButton.setOnAction(e -> textArea.clear());
    openFileButton.setOnAction(e -> openFile(primaryStage));
    saveFileButton.setOnAction(e -> saveFile(primaryStage));

    // Configure and show the stage
    primaryStage.setTitle("Simple Text Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void openFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Text File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      try {
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
        textArea.setText(content);
      } catch (IOException ex) {
        System.err.println("Failed to read file: " + ex.getMessage());
      }
    }
  }

  private void saveFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Text File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      try {
        Files.write(Paths.get(file.toURI()), textArea.getText().getBytes());
      } catch (IOException ex) {
        System.err.println("Failed to save file: " + ex.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
