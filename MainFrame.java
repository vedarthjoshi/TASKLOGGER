import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame {

    Color backgroundColor = new Color(50, 50, 50);
    Color buttonColor = Color.BLACK;

    private JFrame window;
    private JLabel console;
    private JPanel buttonPanel;
    private JButton revert;
    private JPanel south;

    ArrayList<String> taskList;
    ArrayList<String> complist;

    String read = "tasks.txt";
    String write = "complete.txt";

    MainFrame() {
        initialize();
        putButtons();
    }

    void initialize() {

        // INITIALIZATIONS
        taskList = new ArrayList<String>();
        complist = new ArrayList<String>();
        // writeTasks();
        readTasks();

        // JFRAME
        window = new JFrame();
        window.getContentPane().setBackground(backgroundColor);
        window.setTitle("Task Logger");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(400, 400);
        window.setSize(800, 500);

        //Revert
        south = new JPanel(new GridLayout(2,0));
        
        buttonPanel = new JPanel(new GridLayout(0, 3)); // 3 columns, dynamic number of rows
        buttonPanel.setBackground(backgroundColor);
        
        // CONSOLE JLABEL
        console = new JLabel("console >>");
        south.add(console);

        revert = new JButton("Revert");
        revert.setFocusable(false);
        revert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Swap the task lists
                ArrayList<String> tempTasks = taskList;
                taskList = complist;
                complist = tempTasks;
        
                // Clear the button panel
                buttonPanel.removeAll();
        
                // Add buttons corresponding to the new task list
                putButtons();
        
                // Repaint the window
                window.revalidate();
                window.repaint();
            }
        });
        
        south.add(revert);
        
        window.add(south, BorderLayout.SOUTH);
        // JPANEL
        window.add(buttonPanel, BorderLayout.NORTH);
        
    }

    public void show() {
        this.window.setVisible(true);
    }

    void readTasks() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(read));
            while ((line = reader.readLine()) != null) {
                taskList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            console.setText("FILE NOT FOUND");
            e.printStackTrace();
        }
    }

    // void putButtons() {

    //     for (String task : taskList) {
    //         // CONFIGS OF BUTTON
    //         JButton button = new JButton(task);
    //         button.setFont(new Font("Courier New", Font.PLAIN, 25));
    //         button.setToolTipText(task);
    //         button.setFocusable(false);
    //         button.setBackground(buttonColor);
    //         button.setForeground(Color.WHITE);
    //         // button.setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, backgroundColor, backgroundColor, backgroundColor));
    //         button.addActionListener(new ButtonClickListener()); // Add ActionListener
    //         buttonPanel.add(button);
    //     }
    // }

    void putButtons() {
        // Clear the button panel before re-adding buttons
        buttonPanel.removeAll();
    
        for (String task : taskList) {
            // CONFIGS OF BUTTON
            JButton button = new JButton(task);
            button.setFont(new Font("Courier New", Font.PLAIN, 25));
            button.setToolTipText(task);
            button.setFocusable(false);
            button.setBackground(buttonColor);
            button.setForeground(Color.WHITE);
            // button.setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, backgroundColor, backgroundColor, backgroundColor));
            button.addActionListener(new ButtonClickListener()); // Add ActionListener
            buttonPanel.add(button);
        }
    
        // Revalidate the button panel to reflect the changes
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    

    // ActionListener implementation
    class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Handle button click event here
            JButton button = (JButton) e.getSource();
            // A variable called taskName to save that name
            String taskName = button.getText();
            // Button Panel Will remove this button
            buttonPanel.remove(button);
            // It will also remove it from task list
            taskList.remove(taskName);
            // And it will add it to complist
            complist.add(taskName);
            console.setText("Selected Task: " + taskName);

            try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(read));
                    BufferedWriter cw = new BufferedWriter(new FileWriter(write));
                    // For every complist task aopend the read file
                    for (String ctask : complist) {
                            cw.append(ctask + "\n");
                        }
                        // bw.flush();
                        //
                    for (String task : taskList) {
                                bw.append(task + "\n");
                        }
                        bw.close();
                        cw.close();
            } 

            catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
            }
        }
    }
}

