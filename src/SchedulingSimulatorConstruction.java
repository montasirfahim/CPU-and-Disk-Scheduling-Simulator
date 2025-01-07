import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulingSimulatorConstruction extends JFrame implements ActionListener {

    CardLayout cardLayout;
    JPanel mainPanel, homePanel, cpuPanel, diskPanel;
    JLabel homeLabel;

    JButton cpuButton, diskButton, buttonHome, btnHomeCPU;

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
        btnHomeCPU = new JButton("Back to Home");
        btnHomeCPU.addActionListener(this);
        btnHomeCPU.setBounds(480, 500, 200, 30);
        cpuPanel.add(btnHomeCPU); // Add buttonHome to CPU panel

        // Disk panel
        diskPanel = new JPanel(null);
        diskPanel.setBackground(new Color(217, 238, 225));
        buttonHome = new JButton("Back to Home");
        buttonHome.addActionListener(this);
        buttonHome.setBounds(480, 500, 200, 30);
        diskPanel.add(buttonHome);

        // Add panels to mainPanel with CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(cpuPanel, "CPU Panel");
        mainPanel.add(diskPanel, "Disk Panel");

        // Add the mainPanel to JFrame and make visible
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
    }
}
