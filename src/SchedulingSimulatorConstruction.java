import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SchedulingSimulatorConstruction extends JFrame implements ActionListener {

    CardLayout cardLayout;
    JPanel mainPanel, homePanel, cpuPanel, diskPanel;
    JLabel homeLabel, lblTimeQuantum;

    JButton cpuButton, diskButton, buttonHome, btnHomeCPU;
    JButton fcfsButton, sjnButton, priorityButton, rrButton;
    JSpinner timeQuantum;
    JFormattedTextField fieldProcessId;
    public SchedulingSimulatorConstruction() { // constructor
        setTitle("CPU and Disk Scheduling Simulator");
        setLayout(new BorderLayout()); // new BorderLayout()
        setLocation(200, 120);
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Home panel
        homePanel = new JPanel(null);
        homePanel.setBackground(new Color(217, 238, 225));
        homeLabel = new JLabel("Select any option: ");
        homeLabel.setBounds(30, 30, 200, 30);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        cpuButton = new JButton("CPU Scheduling Algorithms");
        cpuButton.addActionListener(this);
        diskButton = new JButton("Disk Scheduling Algorithms");
        diskButton.addActionListener(this);
        cpuButton.setBounds(460, 140, 250, 30);
        diskButton.setBounds(460, 190, 250, 30);

        homePanel.add(homeLabel);
        homePanel.add(cpuButton);
        homePanel.add(diskButton);

        // CPU panel
        cpuPanel = new JPanel(null);
        cpuPanel.setBackground(new Color(217, 238, 225));

        fcfsButton = new JButton("First-Come, First-Served");
        fcfsButton.addActionListener(this);
        sjnButton = new JButton("Shortest Job Next");
        sjnButton.addActionListener(this);
        priorityButton = new JButton("Priority Scheduling");
        priorityButton.addActionListener(this);
        rrButton = new JButton("Round Robin");
        rrButton.addActionListener(this);
        //set bounds
        fcfsButton.setBounds(480, 100, 200, 30);
        sjnButton.setBounds(480, 150, 200, 30);
        priorityButton.setBounds(480, 200, 200, 30);
        rrButton.setBounds(480, 250, 200, 30);


        btnHomeCPU = new JButton("Back to Home");
        btnHomeCPU.addActionListener(this);
        btnHomeCPU.setBounds(480, 500, 200, 30);
        btnHomeCPU.setFont(new Font("Arial", Font.BOLD, 16));
        btnHomeCPU.setBackground(new Color(130, 130, 200));
        btnHomeCPU.setForeground(Color.WHITE);
        btnHomeCPU.setMargin(new Insets(10, 20, 10, 20));
        cpuPanel.add(btnHomeCPU);

        fieldProcessId = new JFormattedTextField();
        fieldProcessId.setBounds(30, 30, 100, 30);
        fieldProcessId.setText("Process ID");
        fieldProcessId.setForeground(Color.GRAY);

        fieldProcessId.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e){
                if (fieldProcessId.getText().equals("Process ID")){
                    fieldProcessId.setText("");
                    fieldProcessId.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e){
                if (fieldProcessId.getText().isEmpty()){
                    fieldProcessId.setText("Process ID");
                    fieldProcessId.setForeground(Color.GRAY);
                }
            }
        });

        //cpuPanel.add(fieldProcessId);

        //div
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1); // Start at 0, min 0, no max, increment by 1
        timeQuantum = new JSpinner(spinnerModel);
        timeQuantum.setVisible(false);  // Initially hidden
        lblTimeQuantum = new JLabel("Enter Time-Quantum: ");
        lblTimeQuantum.setVisible(false);
        JPanel boxPanel = new JPanel(); //div
        boxPanel.setLayout(new FlowLayout());
        boxPanel.setBorder(BorderFactory.createLineBorder(new Color(76, 107, 82), 0)); // Adding a border
        boxPanel.setBounds(800, 60, 250, 150); // Positioning the boxPanel on the right side
        boxPanel.setBackground(new Color(200, 230, 200));

        //Dropdown menu inside the box
        String[] options = {"Select any Algorithm",
                "First Come First Served",
                "Shortest Job Next",
                "Shortest Remaining Time First",
                "Priority Scheduling",
                "Round Robin"};

        JComboBox<String> dropdownMenu = new JComboBox<>(options);
        dropdownMenu.addActionListener(this);
        boxPanel.add(dropdownMenu);
        boxPanel.add(lblTimeQuantum);
        boxPanel.add(timeQuantum);


        //Add the boxPanel to the cpuPanel
        cpuPanel.add(boxPanel);
        cpuPanel.add(fieldProcessId);

//        cpuPanel.add(fcfsButton);
//        cpuPanel.add(sjnButton);
//        cpuPanel.add(priorityButton);
//        cpuPanel.add(rrButton);

        // Disk panel
        diskPanel = new JPanel(null);
        diskPanel.setBackground(new Color(217, 238, 225));
        buttonHome = new JButton("Back to Home");
        buttonHome.addActionListener(this);
        buttonHome.setBounds(480, 500, 200, 30);
        buttonHome.setFont(new Font("Arial", Font.BOLD, 16));
        buttonHome.setBackground(new Color(130, 130, 200));
        buttonHome.setForeground(Color.WHITE);
        buttonHome.setMargin(new Insets(10, 20, 10, 20));




        diskPanel.add(buttonHome);

        //Add panels to mainPanel with CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(cpuPanel, "CPU Panel");
        mainPanel.add(diskPanel, "Disk Panel");

        //Add the mainPanel to JFrame and make visible
        add(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event){ //implementing method from Interface
        if(event.getSource() == cpuButton){
            cardLayout.show(mainPanel, "CPU Panel");
        }
        if(event.getSource() == diskButton){
            cardLayout.show(mainPanel, "Disk Panel");
        }
        if(event.getSource() == buttonHome){
            cardLayout.show(mainPanel, "Home");
        }
        if(event.getSource() == btnHomeCPU){
            cardLayout.show(mainPanel, "Home");
        }

        if(event.getSource() instanceof JComboBox){
            JComboBox<String> comboBox = (JComboBox<String>) event.getSource();
            String selectedOption = (String) comboBox.getSelectedItem();
            //Handling the selected option
            if("First Come First Served".equals(selectedOption)){
                // Code for First Come First Served
                lblTimeQuantum.setVisible(false);
                timeQuantum.setVisible(false);
                System.out.println("Selected: First Come First Served");
            }
            else if("Shortest Job Next".equals(selectedOption)){
                // Code for Shortest Job Next
                lblTimeQuantum.setVisible(false);
                timeQuantum.setVisible(false);
                System.out.println("Selected: Shortest Job Next");
            }
            else if("Shortest Remaining Time First".equals(selectedOption)){
                // Code for Shortest Remaining Time First
                lblTimeQuantum.setVisible(false);
                timeQuantum.setVisible(false);
                System.out.println("Selected: Shortest Remaining Time First");
            }
            else if("Priority Scheduling".equals(selectedOption)){
                // Code for Priority Scheduling
                lblTimeQuantum.setVisible(false);
                timeQuantum.setVisible(false);
                System.out.println("Selected: Priority Scheduling");
            }
            else if("Round Robin".equals(selectedOption)){
                // Code for Round Robin
                lblTimeQuantum.setVisible(true);
                timeQuantum.setVisible(true);
                System.out.println("Selected: Round Robin");
            }

        }
    }
}
