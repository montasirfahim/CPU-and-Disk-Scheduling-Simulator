import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.HashSet;
public class SchedulingSimulatorConstruction extends Application {

    private StackPane mainPane;
    private AnchorPane homePane, cpuPane, diskPane;
    Button cpuButton, diskButton, btnHomeCPU, backButton, addProcessButton, resetButton;
    Button btnBankers, btnPageReplacement, btnMemoryAllocation, btnCalculateCPU;
    Label lblTimeQuantum, lblAddedProcess;
    private TextField fieldTimeQuantum;
    ComboBox<String> comboBox;
    TextField processIdField, arrivalTimeField, burstTimeField;
    List<Processes> processDataList = new ArrayList<>();
    // Declare the TableView
    private TableView<Processes> processTable;
    TableView<Process> tableView;


    Set<Integer> setProcessID = new HashSet<>();

    @Override
    public void start(Stage primaryStage) {
        mainPane = new StackPane();

        setupHomePane();
        setupCPUPane();
        setupDiskPane();

        mainPane.getChildren().add(homePane);

        primaryStage.setTitle("CPU and Disk Scheduling Simulator");
        primaryStage.setScene(new Scene(mainPane, 1100, 600));
        primaryStage.show();
    }

    private void setupHomePane() {
        homePane = new AnchorPane();
        homePane.setPadding(new Insets(20));
        homePane.setStyle("-fx-background-color: lightblue;");

        Label homeLabel = new Label("Select any option:");
        homeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: Arial;");
        homeLabel.setLayoutX(20);
        homeLabel.setLayoutY(20);

        cpuButton = new Button("CPU Scheduling Algorithms");
        cpuButton.setLayoutX(450);
        cpuButton.setLayoutY(100);
        styleButton(cpuButton);
        cpuButton.setOnAction(this::handleAction);

        diskButton = new Button("Disk Scheduling Algorithms");
        diskButton.setLayoutX(450);
        diskButton.setLayoutY(150);
        styleButton(diskButton);
        diskButton.setOnAction(this::handleAction);

        btnBankers = new Button();
        btnPageReplacement = new Button();
        btnMemoryAllocation = new Button();
        btnBankers.setText("Banker's Algorithm");
        btnBankers.setLayoutX(450);
        btnBankers.setLayoutY(200);
        styleButton(btnBankers);
        btnBankers.setOnAction(this::handleAction);


        btnPageReplacement.setText("Page Replacement Algorithms");
        btnPageReplacement.setLayoutX(450);
        btnPageReplacement.setLayoutY(250);
        styleButton(btnPageReplacement);
        btnPageReplacement.setOnAction(this::handleAction);


        btnMemoryAllocation.setText("Memory Allocation Algorithms");
        btnMemoryAllocation.setLayoutX(450);
        btnMemoryAllocation.setLayoutY(300);
        styleButton(btnMemoryAllocation);
        btnMemoryAllocation.setOnAction(this::handleAction);


        homePane.getChildren().addAll(btnBankers, btnPageReplacement, btnMemoryAllocation);
        homePane.getChildren().addAll(homeLabel, cpuButton, diskButton);
    }

    private void setupCPUPane() {
        cpuPane = new AnchorPane();
        cpuPane.setPadding(new Insets(20));
        cpuPane.setStyle("-fx-background-color: lightblue;");
        cpuPane.setPrefSize(1000, 1200);  // Set a larger preferred size to ensure scrolling

        setupProcessTable();
        processTable.setVisible(false);


        Label cpuLabel = new Label("CPU Scheduling Algorithms");
        cpuLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: Arial;");
        cpuLabel.setLayoutX(480);
        cpuLabel.setLayoutY(30);

        btnHomeCPU = new Button("Back to Home");
        styleHomeButton(btnHomeCPU);
        btnHomeCPU.setLayoutX(540);
        btnHomeCPU.setLayoutY(550);
        btnHomeCPU.setOnAction(this::handleAction);

        btnCalculateCPU = new Button("Calculate");
        btnCalculateCPU.setLayoutX(115);
        btnCalculateCPU.setLayoutY(550);
        styleCalculateButton(btnCalculateCPU);
        //btnCalculateCPU.setStyle("-fx-background-color: #04AA6D; -fx-pref-width: 100; -fx-text-fill: white; -fx-font-weight:bold; -fx-font-size: 14px; -fx-padding: 10px;");
        btnCalculateCPU.setOnAction(this::handleAction);

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Select any Algorithm",
                "First Come First Served", "Shortest Job Next",
                "Shortest Remaining Time First", "Priority Scheduling", "Round Robin");
        comboBox.getSelectionModel().select("Select any Algorithm");
        comboBox.setStyle("-fx-font-size: 14px;");
        comboBox.setLayoutX(850);
        comboBox.setLayoutY(80);
        comboBox.setOnAction(this::handleAction);

        lblTimeQuantum = new Label("Enter Time Quantum:");
        lblTimeQuantum.setStyle("-fx-font-size: 14px;");
        lblTimeQuantum.setLayoutX(900);
        lblTimeQuantum.setLayoutY(120);
        fieldTimeQuantum = new TextField();
        fieldTimeQuantum.setStyle("-fx-font-size: 14px;");
        fieldTimeQuantum.setLayoutX(880);
        fieldTimeQuantum.setLayoutY(145);

        lblTimeQuantum.setVisible(false);
        fieldTimeQuantum.setVisible(false);

        //Initial Process Fields
        processIdField = new TextField();
        processIdField.setPromptText("Process ID");
        arrivalTimeField = new TextField();
        arrivalTimeField.setPromptText("Arrival Time");
        burstTimeField = new TextField();
        burstTimeField.setPromptText("Burst Time");

        //Set Layout for Initial Fields
        processIdField.setLayoutX(50);
        processIdField.setLayoutY(80);
        arrivalTimeField.setLayoutX(200);
        arrivalTimeField.setLayoutY(80);
        burstTimeField.setLayoutX(350);
        burstTimeField.setLayoutY(80);

        //Add Process Button
        addProcessButton = new Button("Add Process");
        addProcessButton.setLayoutX(510);
        addProcessButton.setLayoutY(80);
        addProcessButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: green;");
        addProcessButton.setOnAction(this::handleAction);
        //reset button
        resetButton = new Button("Reset All");
        resetButton.setOnAction(this::handleAction);
        resetButton.setLayoutX(600);
        resetButton.setLayoutY(80);
        resetButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: red;");

        lblAddedProcess = new Label("Process Added");
        lblAddedProcess.setStyle("-fx-font-weight: BOLD; -fx-font-size: 12px; -fx-text-fill: green");
        lblAddedProcess.setLayoutX(250);
        lblAddedProcess.setLayoutY(50);
        lblAddedProcess.setVisible(false);

        //Add initial components to the pane
        cpuPane.getChildren().addAll(cpuLabel, btnHomeCPU, comboBox, lblTimeQuantum, fieldTimeQuantum,
                processIdField, arrivalTimeField, burstTimeField, addProcessButton,resetButton, lblAddedProcess);
        cpuPane.getChildren().addAll(btnCalculateCPU);
        ScrollPane scrollPane = new ScrollPane(cpuPane);
        scrollPane.setFitToWidth(true);  // This makes the ScrollPane fit the width of the content
        scrollPane.setPrefHeight(600);
        mainPane.getChildren().addAll(scrollPane);
    }

    private void setupDiskPane() {
        diskPane = new AnchorPane();
        diskPane.setPadding(new Insets(20));
        diskPane.setStyle("-fx-background-color: lightblue;");

        Label diskLabel = new Label("Disk Scheduling Algorithms");
        diskLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: Arial;");
        diskLabel.setLayoutX(480);
        diskLabel.setLayoutY(40);

        backButton  = new Button("Back to Home");
        styleHomeButton(backButton);
        backButton.setLayoutX(540);
        backButton.setLayoutY(550);

        backButton.setOnAction(this::handleAction);

        diskPane.getChildren().addAll(diskLabel, backButton);
    }

    private void styleButton(Button button){
        button.setStyle("-fx-background-color: #8282C8; -fx-pref-width: 250; -fx-text-fill: white; -fx-font-weight:bold; -fx-font-size: 14px; -fx-padding: 10px;");
    }

    private void styleHomeButton(Button button){
        button.setStyle("-fx-background-color: #8282C8; -fx-text-fill: white; -fx-font-weight:bold; -fx-font-size: 14px; -fx-padding: 10px;");
    }

    private void styleCalculateButton(Button button){
        button.setStyle("-fx-background-color: #04AA6D; -fx-pref-width: 100; -fx-text-fill: white; -fx-font-weight:bold; -fx-font-size: 14px; -fx-padding: 10px;");
    }

    private void handleAction(ActionEvent event){
        if(event.getSource() != addProcessButton){
            lblAddedProcess.setVisible(false);
        }
        if(event.getSource() == cpuButton){
            mainPane.getChildren().setAll(cpuPane);
        }
        else if (event.getSource() == diskButton){
            mainPane.getChildren().setAll(diskPane);
        }
        else if(event.getSource() == btnHomeCPU){
            mainPane.getChildren().setAll(homePane);
            processDataList.clear();
            setProcessID.clear();
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            fieldTimeQuantum.clear();
        }
        else if(event.getSource() == backButton){
            mainPane.getChildren().setAll(homePane);
            processDataList.clear();
            setProcessID.clear();
        }
        else if(event.getSource() == addProcessButton){
            String processIdText = processIdField.getText();
            String arrivalTimeText = arrivalTimeField.getText();
            String burstTimeText = burstTimeField.getText();

            if(processIdText.isEmpty() || arrivalTimeText.isEmpty() || burstTimeText.isEmpty()){
                showErrorDialog("Please Fill-up All Fields");
                return;
            }

            // Check if the inputs are valid integers
            if(!isValidInteger(processIdText) || !isValidInteger(arrivalTimeText) || !isValidInteger(burstTimeText)){
                showErrorDialog("Please Enter Valid Data");
                return;
            }

            int id = Integer.parseInt(processIdField.getText());
            int arrTime = Integer.parseInt(arrivalTimeField.getText());
            int burTime = Integer.parseInt(burstTimeField.getText());
            if(id <= 0 || arrTime < 0 || burTime < 0){
                showErrorDialog("Please Enter Valid Data");
                return;
            }

            if(setProcessID.contains(id)){
                showErrorDialog("Duplicate Process ID");
                return;
            }

            setProcessID.add(id);
            Processes newProcess = new Processes(id, arrTime, burTime);
            processDataList.add(newProcess);
            lblAddedProcess.setVisible(false);
            System.out.println(processDataList);
            processIdField.clear();
            arrivalTimeField.clear();
            burstTimeField.clear();
            showTimedDialog("Process Added Successfully!", cpuPane);
            processTable.getItems().add(newProcess);
            processTable.setVisible(true);
        }
        else if(event.getSource() == resetButton){
            processDataList.clear();
            setProcessID.clear();
            lblAddedProcess.setVisible(false);

            comboBox.getSelectionModel().select("Select any Algorithm");
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            fieldTimeQuantum.clear();
            processIdField.clear();
            arrivalTimeField.clear();
            burstTimeField.clear();
            processTable.getItems().clear();
            processTable.setVisible(false);
            tableView.getItems().clear();
            tableView.setVisible(false);
            cpuPane.layout();

        }
        else if(event.getSource() == btnCalculateCPU){
            if(setProcessID.isEmpty()){
                showErrorDialog("Please Enter Some Processes!");
                return;
            }
            if(!isValidInteger(fieldTimeQuantum.getText())){
                showErrorDialog("Enter Valid Time-Quantum");
                return;
            }

            int timeQuantumt = Integer.parseInt(fieldTimeQuantum.getText());
            if(timeQuantumt <= 0){
                showErrorDialog("Enter Valid Time-Quantum");
                return;
            }

            ObservableList<Process> resultData = FXCollections.observableArrayList();
            //Run the Round Robin scheduling algorithm

            RoundRobin roundRobin = new RoundRobin(processDataList, timeQuantumt);
            roundRobin.runRoundRobin();

            //Get the processes and add them to the resultData list
            //resultData.addAll(roundRobin.getProcesses());

            // Now call the method to generate and display the result table in cpuPane
            generateResultTable(cpuPane, resultData);
            tableView.setVisible(true);

        }
        else if(event.getSource() instanceof ComboBox) {
            handleComboBoxSelection((ComboBox<?>) event.getSource());
        }
    }

    private void handleComboBoxSelection(ComboBox<?> comboBox){
        String selectedOption = (String) comboBox.getValue();
//        if(!selectedOption.isEmpty()){
//            lblAddedProcess.setVisible(false);
//        }
//        tableView.getItems().clear(); //clear before pressing calculate button
//        tableView.setVisible(false);
        if(selectedOption.contentEquals("First Come First Served")){
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            System.out.println("Selected: " + selectedOption);
        }
        else if(selectedOption.contentEquals("Shortest Job Next")){
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            System.out.println("Selected: " + selectedOption);
        }
        else if(selectedOption.contentEquals("Shortest Remaining Time First")){
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            System.out.println("Selected: " + selectedOption);
        }
        else if(selectedOption.contentEquals("Priority Scheduling")){
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            System.out.println("Selected: " + selectedOption);
        }
        else if(selectedOption.contentEquals("Round Robin")){
            lblTimeQuantum.setVisible(true);
            fieldTimeQuantum.setVisible(true);
            System.out.println("Selected: " + selectedOption);
        }
        else if(selectedOption.contentEquals("Select any Algorithm")){
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
        }

    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showTimedDialog(String message, Pane parentPane){
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px;");

        StackPane dialogPane = new StackPane(messageLabel);
        dialogPane.setStyle("-fx-background-color: #04AA6D; -fx-border-color: black; -fx-border-radius: 5px;");

        //Add the dialog to the parent pane
        parentPane.getChildren().add(dialogPane);

        dialogPane.setLayoutX(210);
        dialogPane.setLayoutY(30);

        //Fade transition to hide the dialog after 4 second
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), dialogPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> parentPane.getChildren().remove(dialogPane)); // Remove dialog when fade-out finishes
        fadeOut.play();
    }

    private void setupProcessTable() {
        processTable = new TableView<>();

        // Create Table Columns
        TableColumn<Processes, Integer> idColumn = new TableColumn<>("Process ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("processId"));

        TableColumn<Processes, Integer> arrivalColumn = new TableColumn<>("Arrival Time");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Processes, Integer> burstColumn = new TableColumn<>("Burst Time");
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        // Add Columns to Table
        processTable.getColumns().addAll(idColumn, arrivalColumn, burstColumn);

        // Set Table Size
        processTable.setPrefWidth(240);
        processTable.setMinHeight(30);  // Minimum height for the header
        processTable.setMaxHeight(Region.USE_COMPUTED_SIZE);  // Let the height adjust dynamically as rows are added

        processTable.setLayoutX(50);
        processTable.setLayoutY(120);
        // Start with no rows (empty table)
        processTable.getItems().clear();  //Clear any existing rows

        // Add TableView to the pane
        cpuPane.getChildren().add(processTable);
    }

    public void generateResultTable(AnchorPane cpuPane, ObservableList<Process> resultData) {
        // Create a new TableView instance
        tableView = new TableView<>();
        // Create columns for the table
        TableColumn<Process, Integer> idColumn = new TableColumn<>("Process ID");
        TableColumn<Process, Integer> arrivalColumn = new TableColumn<>("Arrival Time");
        TableColumn<Process, Integer> burstColumn = new TableColumn<>("Burst Time");
        TableColumn<Process, Integer> completionColumn = new TableColumn<>("Completion Time");
        TableColumn<Process, Integer> waitingColumn = new TableColumn<>("Waiting Time");
        TableColumn<Process, Integer> turnaroundColumn = new TableColumn<>("Turnaround Time");

        // Set up the cell value factories (map each column to the appropriate property in Process)
//        idColumn.setCellValueFactory(cellData -> cellData.getValue().processIdProperty().asObject());
//        arrivalColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asObject());
//        burstColumn.setCellValueFactory(cellData -> cellData.getValue().burstTimeProperty().asObject());
//        completionColumn.setCellValueFactory(cellData -> cellData.getValue().completionTimeProperty().asObject());
//        waitingColumn.setCellValueFactory(cellData -> cellData.getValue().waitingTimeProperty().asObject());
//        turnaroundColumn.setCellValueFactory(cellData -> cellData.getValue().turnaroundTimeProperty().asObject());

        // Add columns to the TableView
        tableView.getColumns().addAll(idColumn, arrivalColumn, burstColumn, completionColumn, waitingColumn, turnaroundColumn);

        // Populate the table with data (the ObservableList resultData)
        tableView.setItems(resultData);

        // Adjust table layout (you can customize these values based on your layout)
        tableView.setPrefSize(550, 300);  // Width: 700, Height: 300
        tableView.setLayoutX(300);         // Adjust X position
        tableView.setLayoutY(150);        // Adjust Y position

        // Clear any previous content and add the TableView to cpuPane
        //cpuPane.getChildren().clear();
        cpuPane.getChildren().add(tableView);
        tableView.setVisible(false);
    }


    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;  // If an exception occurs, it's not a valid integer
        }
    }



}
