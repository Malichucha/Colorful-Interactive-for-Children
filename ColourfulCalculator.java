//AMALIA SORFINA BINTI MAHDZIR
//ID : 1211111891
//Tutorial Section : TT4L
//Lecture Section : TC1L
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.function.BiFunction;

// Design pattern that being choose is using Model-View-Controller (MVC). 
//The Model handles the logic like perform calculations and return results based on user input
//The View is for GUI like display and buttons to ensure the user-friendly experience for pre-school
//The Controller is to manage user interactions, to connect the View and Model by handling events, pass input data
// and update the View result
//The design pattern that being used for this GUI application is Model-View-Controller Design Pattern

// Model: Handles the logic operation
class CalculatorModel {
    private final HashMap<String, BiFunction<Double, Double, String>> operations;

    public CalculatorModel() {
        operations = new HashMap<>();
        operations.put("+", (a, b) -> String.valueOf(a + b)); // addition
        operations.put("-", (a, b) -> String.valueOf(a - b)); // subtraction
        operations.put("*", (a, b) -> String.valueOf(a * b)); // multiplication
        operations.put("/", (a, b) -> b != 0 ? String.valueOf(a / b) : "Error: Division by zero"); // Handle division
    }

    public String calculate(double num1, double num2, String operator) {
        return operations.getOrDefault(operator, (a, b) -> "Invalid").apply(num1, num2);
    }
}

// View: Creates the GUI
class CalculatorView extends JFrame {
    private JTextField display; // Display input and output of calculator
    private JPanel buttonPanel, clearPanel; //Panels for basic buttons and clear buttons
    private JButton[] buttons; //Array main calculator button
    private JButton clearButton; //for clear button
    private String[] labels = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+"}; //label button name display

    public CalculatorView() {
        // Frame setup
        setTitle("Colourful Calculator "); // Set title window
        setSize(400, 500); // Set size of window
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Close the window operation
        setLayout(new BorderLayout()); //Use BorderLayout

        // Display North 
        display = new JTextField(); //Text field for display input and output
        display.setFont(new Font("Berlin Sans FB", Font.BOLD, 70)); //Set type of font
        display.setHorizontalAlignment(JTextField.RIGHT); //Set align text to right
        display.setPreferredSize(new Dimension(400, 100)); //Set size for the display calculator
        display.setBackground(new Color(235,232,213)); //Set background color
        add(display, BorderLayout.NORTH); // Add display to north region

        // Button Clear Center 
        buttonPanel = new JPanel(); //Panel for calculator buttons
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); // 4x4 grid for main buttons
        buttons = new JButton[labels.length]; //Create buttons array
        buttonPanel.setBackground(new Color(235,232,213)); //Set panel background colour

        for (int i = 0; i < labels.length; i++) {
            buttons[i] = new JButton(labels[i]); //Create button with label
            buttons[i].setFont(new Font("Berlin Sans FB", Font.BOLD, 27)); //Set font for buttons
            float hue = (float) i / labels.length; //Calculate hue for gradient colors
            buttons[i].setBackground(Color.getHSBColor(hue, 0.8f, 0.9f)); // Colorful buttons
            buttons[i].setForeground(Color.WHITE); //Set text color inside the buttons
            buttons[i].setOpaque(true); //make the button opaque
            buttons[i].setBorderPainted(true); //border of button 
            buttonPanel.add(buttons[i]); //Add button to Panel
        }
        add(buttonPanel, BorderLayout.CENTER); //Add button panel to center

        // Clear Button South
        clearPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for Clear button in center
        clearButton = new JButton("Clear All"); //Create "Clear All" button
        clearButton.setFont(new Font("Berlin Sans FB", Font.BOLD, 27)); //Set Clear button font
        clearButton.setBackground(new Color(138,42,186)); //Set background color
        clearButton.setForeground(Color.WHITE); //Set text color inside button
        clearButton.setPreferredSize(new Dimension(399, 50)); // Set size for emphasis
        clearPanel.add(clearButton); //Add clear button to panel
        add(clearPanel, BorderLayout.SOUTH); //Add clear panel to South
    }

    public JTextField getDisplay() {
        return display; 
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public JButton getClearButton() {
        return clearButton;
    }
}

// Controller: Handles interactions
class CalculatorController {
    private CalculatorModel model; //Reference to model
    private CalculatorView view; //Reference to view
    private String operator = ""; //Current operator
    private double num1 = 0; //First operand

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model; //initialize model
        this.view = view; //initialize view

        // Attach event listeners to buttons
        for (JButton button : view.getButtons()) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = e.getActionCommand();

                    if ("0123456789.".contains(command)) { //if number or decimal
                        view.getDisplay().setText(view.getDisplay().getText() + command); 
                    } else if ("+-*/".contains(command)) { //if operations
                        num1 = Double.parseDouble(view.getDisplay().getText());
                        operator = command;
                        view.getDisplay().setText(""); //clear display
                    } else if (command.equals("=")) { //if equals button
                        double num2 = Double.parseDouble(view.getDisplay().getText());
                        String result = model.calculate(num1, num2, operator); //perform calculation
                        view.getDisplay().setText(result); //display result
                    }
                }
            });
        }

        // Attach event listener to clear button
        view.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getDisplay().setText(""); // Clear the display
                num1 = 0;                      // Reset stored number
                operator = "";                 // Reset operator
            }
        });
    }
}

// Main Application
public class ColourfulCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorModel model = new CalculatorModel(); // Model
            CalculatorView view = new CalculatorView();   // View
            new CalculatorController(model, view);       // Controller
            view.setVisible(true);
        });
    }
}
