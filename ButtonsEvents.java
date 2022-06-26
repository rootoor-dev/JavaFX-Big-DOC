/*
-- Les actions 

FERMER
AGRANDIR
REDUIRE
et bien d'autres

*/

@FXML
private void Minimize_clicked(MouseEvent event) {
    Stage stage = (Stage) minimize_button.getScene().getWindow();
    stage.setIconified(true);
}

// This will close your JavaFX application entirely
@FXML
private void Close_clicked(MouseEvent event) {
   javafx.application.Platform.exit();
}

// Closing the application in the decorated window

 stage.setOnCloseRequest(event ->{
   javafx.application.Platform.exit();
});


// To minimize the stage into task bar set an action for your minimize button similar to this:

    btnMinimize.setOnAction(new EventHandler<ActionEvent>() {

        public void handle(ActionEvent event) {
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            // is stage minimizable into task bar. (true | false)
            stage.setIconified(true);
        }
    });

 @FXML
    public void minimize(MouseEvent evt) {

      Stage stage = (Stage)((Label)evt.getSource()).getScene().getWindow();

      stage.setIconified(true);
    }

/* Hide button */
Button hideButton = new Button("hide");
hideButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        stage.hide();
    }
});

/* Show button */
Button showButton = new Button("show");
showButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        stage.show();
    }
});

// filechooser  ,   https://oliver-loeffler.github.io/FXFileChooser/

Using the JavaFX Dialog version
public class FxDialogDemo extends Application  {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Show File Chooser Dialog");
        FXFileChooserDialog dialog = FXFileChooserDialog.create(Skin.DARK);
        button.setOnAction(evt-> dialog.showOpenDialog(primaryStage).ifPresent(this::showSelection));

        Scene scene = new Scene(button);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Demo");
        primaryStage.show();
    }

    private void showSelection(Path selectedPath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("File Selection");
        alert.setContentText(selectedPath.toString());
        alert.show();
    }
}

// Swing version with Filter

// A version with a completely customizable stage
public class FxStageDemo extends Application  {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Show File Chooser Stage");
        FXFileChooserStage fc = FXFileChooserStage.create(Skin.DARK);
        button.setOnAction(evt-> fc.showOpenDialog(primaryStage).ifPresent(this::showSelection));

        Scene scene = new Scene(button);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Demo");
        primaryStage.show();
    }

    private void showSelection(Path selectedPath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("File Selection");
        alert.setContentText(selectedPath.toString());
        alert.show();
    }
}







