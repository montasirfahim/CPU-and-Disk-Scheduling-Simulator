import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.HashSet;
public class SchedulingSimulatorConstruction extends Application {

    private StackPane mainPane;
    private AnchorPane homePane, cpuPane, diskPane, pagingPane;
    Button cpuButton, diskButton, btnHomeCPU, backButton, addProcessButton, resetButton;
    Button btnBankers, btnPageReplacement, btnMemoryAllocation, btnCalculateCPU;
    Button btnPagingHome, btnPagingAddString, btnPagingFrameSize, btnPageReset, btnPagingCalculate;
    Label lblPagingHeader, lblPagingString;
    ComboBox<String> pageComboBox;
    Label lblTimeQuantum, lblAddedProcess;
    Label lblAvgWaitingTime, lblAvgTAT, lblAvgResponseTime, lblGanttPID, lblGanttPTime, lblGantt;
    Line topLine, bottomLine, rightLine, leftLine;
    private TextField fieldTimeQuantum;
    ComboBox<String> comboBox;
    TextField processIdField, arrivalTimeField, burstTimeField;
    List<Processes> processDataList = new ArrayList<>();

    private TableView<Processes> processTable;
    private TableView<Processes> tableView;
    Set<Integer> setProcessID = new HashSet<>();

    @Override
    public void start(Stage primaryStage) {
        mainPane = new StackPane();
        setupHomePane();
        setupCPUPane();
        setupDiskPane();
        setUpPagingPane();

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
        cpuPane.setPrefSize(1000, 1200);

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
        btnCalculateCPU.setOnAction(this::handleAction);

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Select any Algorithm",
                "First Come First Served", "Shortest Job First",
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

        //summary of result printing
        lblAvgWaitingTime = new Label("Average Waiting Time: ");
        lblAvgWaitingTime.setLayoutX(880); lblAvgWaitingTime.setLayoutY(195);
        lblAvgWaitingTime.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        lblAvgTAT = new Label("Average TurnAround Time: ");
        lblAvgTAT.setLayoutX(880); lblAvgTAT.setLayoutY(220);
        lblAvgTAT.setStyle("-fx-font-weight: bold; -fx-font-size: 12px");
        lblAvgResponseTime = new Label("Average Response Time: ");
        lblAvgResponseTime.setLayoutX(880); lblAvgResponseTime.setLayoutY(245);
        lblAvgResponseTime.setStyle("-fx-font-weight: bold; -fx-font-size: 12px");

        lblGantt = new Label("Gantt Chart: ");
        lblGantt.setLayoutX(300); lblGantt.setLayoutY(470);
        lblGantt.setStyle("-fx-font-weight: bold; -fx-font-size: 13px");


        lblGanttPID = new Label();
        lblGanttPID.setLayoutX(310); lblGanttPID.setLayoutY(500);
        lblGanttPID.setStyle("-fx-font-family: 'Courier New'; -fx-font-weight: bold; -fx-font-size: 13px;");
        lblGanttPTime = new Label();
        lblGanttPTime.setLayoutX(310); lblGanttPTime.setLayoutY(515);
        lblGanttPTime.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13px;");

        double boxX = 300; //Starting X position
        double boxY = 490; //Starting Y position
        double boxWidth = 575; //Box width
        double boxHeight = 50; //Box height (covers both labels)

        topLine = new Line(boxX, boxY, boxX + boxWidth, boxY);
        bottomLine = new Line(boxX, boxY + boxHeight, boxX + boxWidth, boxY + boxHeight);
        leftLine = new Line(boxX, boxY, boxX, boxY + boxHeight);
        rightLine = new Line(boxX + boxWidth, boxY, boxX + boxWidth, boxY + boxHeight);

        topLine.setStroke(Color.BLACK);
        bottomLine.setStroke(Color.BLACK);
        leftLine.setStroke(Color.BLACK);
        rightLine.setStroke(Color.BLACK);

        topLine.setStrokeWidth(1.0);
        bottomLine.setStrokeWidth(1.0);
        leftLine.setStrokeWidth(1.0);
        rightLine.setStrokeWidth(1.0);

        setVisibilityOfAverageCPUResult(false);


        //initial Process Fields
        processIdField = new TextField();
        processIdField.setPromptText("Process ID");
        arrivalTimeField = new TextField();
        arrivalTimeField.setPromptText("Arrival Time / Priority");
        burstTimeField = new TextField();
        burstTimeField.setPromptText("Burst Time");

        //set Layout for Initial Fields
        processIdField.setLayoutX(50);
        processIdField.setLayoutY(80);
        arrivalTimeField.setLayoutX(200);
        arrivalTimeField.setLayoutY(80);
        burstTimeField.setLayoutX(350);
        burstTimeField.setLayoutY(80);

        //add Process Button
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

        //add initial components to the pane
        cpuPane.getChildren().addAll(cpuLabel, btnHomeCPU, comboBox, lblTimeQuantum, fieldTimeQuantum,
                processIdField, arrivalTimeField, burstTimeField, addProcessButton,resetButton, lblAddedProcess);
        cpuPane.getChildren().addAll(btnCalculateCPU);
        cpuPane.getChildren().addAll(lblAvgWaitingTime, lblAvgTAT, lblAvgResponseTime);
        cpuPane.getChildren().addAll(lblGanttPID, lblGanttPTime);
        cpuPane.getChildren().addAll(topLine, bottomLine, leftLine, rightLine, lblGantt);
        ScrollPane scrollPane = new ScrollPane(cpuPane);
        scrollPane.setFitToWidth(true);  //this makes the ScrollPane fit the width of the content
        scrollPane.setPrefHeight(600);
        mainPane.getChildren().addAll(scrollPane);
    }

    private void setVisibilityOfAverageCPUResult(boolean isVisible){
        lblAvgWaitingTime.setVisible(isVisible);
        lblAvgTAT.setVisible(isVisible);
        lblAvgResponseTime.setVisible(isVisible);
        lblGanttPID.setVisible(isVisible);
        lblGanttPTime.setVisible(isVisible);
        lblGantt.setVisible(isVisible);
        topLine.setVisible(isVisible);
        bottomLine.setVisible(isVisible);
        leftLine.setVisible(isVisible);
        rightLine.setVisible(isVisible);
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

    private void setUpPagingPane() {
        pagingPane = new AnchorPane();
        pagingPane.setPadding(new Insets(20));
        pagingPane.setStyle("-fx-background-color: lightblue;");

        Label lblHeader = new Label("Page Replacement Algorithms");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: Arial;");
        lblHeader.setLayoutX(480); lblHeader.setLayoutY(30);

        btnPagingHome = new Button("Back to Home");
        styleHomeButton(btnPagingHome);
        btnPagingHome.setLayoutX(540); btnPagingHome.setLayoutY(550);
        btnPagingHome.setOnAction(this::handleAction);

        pageComboBox = new ComboBox<>();
        pageComboBox.getItems().addAll("Select any Algorithm",
                "First In First Out", "Least Recently Used", "Optimal");
        pageComboBox.getSelectionModel().select("Select any Algorithm");
        pageComboBox.setStyle("-fx-font-size: 14px;");
        pageComboBox.setLayoutX(850);
        pageComboBox.setLayoutY(80);
        pageComboBox.setOnAction(this::handleAction);

        pagingPane.getChildren().addAll(lblHeader, btnPagingHome, pageComboBox);
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
        else if(event.getSource() == btnPageReplacement){
            mainPane.getChildren().setAll(pagingPane);
        }
        else if(event.getSource() == btnHomeCPU){
            mainPane.getChildren().setAll(homePane);
            processDataList.clear();
            setProcessID.clear();
            comboBox.getSelectionModel().select("Select any Algorithm");
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            fieldTimeQuantum.clear();
            processTable.getItems().clear();
            processTable.setVisible(false);
            if(tableView != null){
                tableView.getItems().clear();
                tableView.setVisible(false);
                tableView.setManaged(false);
            }
            setVisibilityOfAverageCPUResult(false);
            cpuPane.layout();
            homePane.layout(); //force UI refresh
        }
        else if(event.getSource() == backButton){
            mainPane.getChildren().setAll(homePane);
            processDataList.clear();
            setProcessID.clear();
        }
        else if(event.getSource() == btnPagingHome){
            mainPane.getChildren().setAll(homePane);
        }

        else if(event.getSource() == addProcessButton){
            String processIdText = processIdField.getText();
            String arrivalTimeText = arrivalTimeField.getText();
            String burstTimeText = burstTimeField.getText();

            if(processIdText.isEmpty() || arrivalTimeText.isEmpty() || burstTimeText.isEmpty()){
                showErrorDialog("Please Fill-up All Fields");
                return;
            }

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
            System.out.println("Reset Pressed!");
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
            if(tableView != null){
                tableView.getItems().clear();
                tableView.setVisible(false);
                tableView.setManaged(false);
            }
            setVisibilityOfAverageCPUResult(false);
            cpuPane.layout();

        }
        else if(event.getSource() == btnCalculateCPU){
            if(tableView != null){
                tableView.getItems().clear();
                tableView.getColumns().clear();
                tableView.setVisible(false);
            }
            if(setProcessID.isEmpty()){
                showErrorDialog("Please Enter Some Processes!");
                return;
            }
            String selectedAlgo = (String) comboBox.getValue();
            int timeQuantum;
            if(selectedAlgo.contentEquals("Round Robin")){
                if(!isValidInteger(fieldTimeQuantum.getText())){
                    showErrorDialog("Enter Valid Time-Quantum");
                    return;
                }

                timeQuantum = Integer.parseInt(fieldTimeQuantum.getText());
                if(timeQuantum <= 0){
                    showErrorDialog("Enter Valid Time-Quantum");
                    return;
                }
            }

            List<Processes> copiedList = deepCopyProcesses(processDataList);
            copiedList.sort(Comparator.comparingInt(process -> process.proArrivalTime));
            ObservableList<Processes> resultData = FXCollections.observableArrayList();

            if(selectedAlgo.contentEquals("Round Robin")){
                setVisibilityOfAverageCPUResult(false);
                timeQuantum = Integer.parseInt(fieldTimeQuantum.getText());
                RoundRobin roundRobin = new RoundRobin(copiedList, timeQuantum);
                roundRobin.runRoundRobin();
                roundRobin.getAverageResult();
                lblAvgWaitingTime.setText("Average Waiting Time: " + String.format("%.2f", roundRobin.rrAWT));
                lblAvgTAT.setText("Average TurnAround Time: " + String.format("%.2f", roundRobin.rrATAT));
                lblAvgResponseTime.setText("Average Response Time: " + String.format("%.2f", roundRobin.rrART));
                lblGanttPID.setText(roundRobin.getGanttPIDString());
                lblGanttPTime.setText(roundRobin.getGanttTimeString());
                setVisibilityOfAverageCPUResult(true);
                roundRobin.printProcessInfo();

                resultData.clear();
                resultData.addAll(roundRobin.getProcesses());  //get the processes and add them to the resultData list
                generateResultTable(cpuPane, resultData); //to generate and display the result table in cpuPane
                tableView.setVisible(true);
            }
            else if(selectedAlgo.contentEquals("First Come First Served")){
                FCFS fcfs = new FCFS(copiedList);
                fcfs.runFCFS();
                fcfs.getAverageFCFSResult();

                lblAvgWaitingTime.setText("Average Waiting Time: " + String.format("%.2f", fcfs.fcfsAWT));
                lblAvgTAT.setText("Average TurnAround Time: " + String.format("%.2f", fcfs.fcfsATAT));
                lblAvgResponseTime.setText("Average Response Time: " + String.format("%.2f", fcfs.fcfsART));
                lblGanttPID.setText(fcfs.getGantString());
                lblGanttPTime.setText(fcfs.getGantTimeString());
                setVisibilityOfAverageCPUResult(true);

                resultData.clear();
                resultData.addAll(fcfs.getProcesses());
                generateResultTable(cpuPane, resultData);
                tableView.setVisible(true);
            }

            else if(selectedAlgo.contentEquals("Priority Scheduling")){
                PriorityScheduling priorityScheduling = new PriorityScheduling(copiedList);
                priorityScheduling.runPriorityScheduling();

                lblAvgWaitingTime.setText("Average Waiting Time: " + String.format("%.2f", priorityScheduling.priorityAWT));
                lblAvgTAT.setText("Average TurnAround Time: " + String.format("%.2f", priorityScheduling.priorityATAT));
                lblAvgResponseTime.setText("Average Response Time: " + String.format("%.2f", priorityScheduling.priorityART));
                lblGanttPID.setText(priorityScheduling.getGanttPID());
                lblGanttPTime.setText(priorityScheduling.getGanttPTime());
                setVisibilityOfAverageCPUResult(true);

                resultData.clear();
                resultData.addAll(priorityScheduling.getProcesses());
                generateResultTable(cpuPane, resultData);
            }

            else if(selectedAlgo.contentEquals("Shortest Job First")){
                ShortestJobFirst shortestJobFirst = new ShortestJobFirst(copiedList);
                shortestJobFirst.runShortestJobFirst();

                lblAvgWaitingTime.setText("Average Waiting Time: " + String.format("%.2f", shortestJobFirst.sjfAWT));
                lblAvgTAT.setText("Average TurnAround Time: " + String.format("%.2f", shortestJobFirst.sjfATAT));
                lblAvgResponseTime.setText("Average Response Time: " + String.format("%.2f", shortestJobFirst.sjfART));
                lblGanttPID.setText(shortestJobFirst.getGanttPID());
                lblGanttPTime.setText(shortestJobFirst.getGanttPTime());
                setVisibilityOfAverageCPUResult(true);

                resultData.clear();
                resultData.addAll(shortestJobFirst.getProcesses());
                generateResultTable(cpuPane, resultData);
            }


        }

        else if(event.getSource() instanceof ComboBox){
            handleComboBoxSelection((ComboBox<?>) event.getSource());
        }
    }

    private void handleComboBoxSelection(ComboBox<?> comboBox){
        String selectedOption = (String) comboBox.getValue();

        if(selectedOption.contentEquals("First Come First Served")){
            lblTimeQuantum.setVisible(false);
            fieldTimeQuantum.setVisible(false);
            System.out.println("Selected: " + selectedOption);
        }
        else if(selectedOption.contentEquals("Shortest Job First")){
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

        //Create Table Columns
        TableColumn<Processes, Integer> idColumn = new TableColumn<>("Process ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("processId"));

        TableColumn<Processes, Integer> arrivalColumn = new TableColumn<>("Arrival Time");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("proArrivalTime"));

        TableColumn<Processes, Integer> burstColumn = new TableColumn<>("Burst Time");
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("proBurstTime"));

        //Add Columns to Table
        processTable.getColumns().addAll(idColumn, arrivalColumn, burstColumn);

        //Set Table Size
        processTable.setPrefWidth(240);
        processTable.setMinHeight(30);  //Minimum height for the header
        processTable.setMaxHeight(Region.USE_COMPUTED_SIZE);  //Let the height adjust dynamically as rows are added

        processTable.setLayoutX(50);
        processTable.setLayoutY(120);
        processTable.getItems().clear();  //Clear any existing rows
        cpuPane.getChildren().add(processTable);  //Add TableView to the pane
    }

    public void generateResultTable(AnchorPane cpuPane, ObservableList<Processes> resultData) {
        tableView = new TableView<>();
        tableView.getItems().clear();

        //create columns for the table
        TableColumn<Processes, Integer> idColumn = new TableColumn<>("Process ID");
        TableColumn<Processes, Integer> arrivalColumn = new TableColumn<>("Arrival Time");
        TableColumn<Processes, Integer> burstColumn = new TableColumn<>("Burst Time");
        TableColumn<Processes, Integer> completionColumn = new TableColumn<>("Completion Time");
        TableColumn<Processes, Integer> waitingColumn = new TableColumn<>("Waiting Time");
        TableColumn<Processes, Integer> turnaroundColumn = new TableColumn<>("Turnaround Time");

        //set up the cell value
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProcessId()));
        arrivalColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProArrivalTime()));
        burstColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProBurstTime()));
        completionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCompletionTime()));
        waitingColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWaitingTime()));
        turnaroundColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTurnaroundTime()));

        //add columns to the table
        tableView.getColumns().addAll(idColumn, arrivalColumn, burstColumn, completionColumn, waitingColumn, turnaroundColumn);

        tableView.setItems(resultData); //provide table data

        //adjust table layout (you can customize these values based on your layout)
        tableView.setPrefSize(573, 300);
        tableView.setLayoutX(300);
        tableView.setLayoutY(150);

        //clear any previous content and add the TableView to cpuPane
        cpuPane.getChildren().add(tableView);
        tableView.setVisible(true);
    }

    private static boolean isValidInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    private List<Processes> deepCopyProcesses(List<Processes> original){
        List<Processes> copy = new ArrayList<>();
        for(Processes process : original){
            copy.add(new Processes(process)); //use the copy constructor to create a new object
        }
        return copy;
    }

}
