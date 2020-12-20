// Exercise 15.4
// Calculator
// Josh Williams

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.Stack;

public class EX15_4 extends Application {

    // TODO: Improve UX by clearing the entry box upon first entry after displaying a result
    // TODO: Improve UX by keeping the equation in the text box and displaying '= result' after pressing equals

    static boolean debugging = false;

    // Create buttons
    Button btAdd = new Button("+");
    Button btSubtract = new Button("-");
    Button btMultiply = new Button("X");
    Button btDivide = new Button("/");
    Button btEquals = new Button("=");
    Button btClear = new Button("C");
    Button bt0 = new Button("0");
    Button bt1 = new Button("1");
    Button bt2 = new Button("2");
    Button bt3 = new Button("3");
    Button bt4 = new Button("4");
    Button bt5 = new Button("5");
    Button bt6 = new Button("6");
    Button bt7 = new Button("7");
    Button bt8 = new Button("8");
    Button bt9 = new Button("9");

    // Create entry/result text field
    TextField result = new TextField();

    // We are extending Application which is an abstract class. We must implement any abstract methods with our own.
    // The start(Stage) method is the entry point for all JavaFX applications and thus is an abstract method.
    @Override
    public void start(Stage stage) {
        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPrefSize(300, 300);

        // Set the preferred size of each numerical button
        bt9.setPrefSize(25, 25);
        bt8.setPrefSize(25, 25);
        bt7.setPrefSize(25, 25);
        bt6.setPrefSize(25, 25);
        bt5.setPrefSize(25, 25);
        bt4.setPrefSize(25, 25);
        bt3.setPrefSize(25, 25);
        bt2.setPrefSize(25, 25);
        bt1.setPrefSize(25, 25);
        bt0.setPrefSize(52, 25); // Wider "0" button

        // Set the preferred size of the function buttons
        btAdd.setPrefSize(50, 25);
        btSubtract.setPrefSize(50, 25);
        btMultiply.setPrefSize(50, 25);
        btDivide.setPrefSize(50, 25);
        btEquals.setPrefSize(25, 25);
        btClear.setPrefSize(50, 50);

        // ===== BEGIN ADDING ELEMENTS TO THE GRID =====

        // Entry & Result Textbox
        gridPane.add(result, 0, 0, 5, 1); // Spans all columns

        // Numerical buttons
        gridPane.add(bt7, 0, 1);
        gridPane.add(bt8, 1, 1);
        gridPane.add(bt9, 2, 1);
        gridPane.add(bt4, 0, 2);
        gridPane.add(bt5, 1, 2);
        gridPane.add(bt6, 2, 2);
        gridPane.add(bt1, 0, 3);
        gridPane.add(bt2, 1, 3);
        gridPane.add(bt3, 2, 3);
        gridPane.add(bt0, 0, 4, 3, 1);

        // Function Buttons
        gridPane.add(btAdd, 3, 1);
        gridPane.add(btSubtract, 3, 2);
        gridPane.add(btMultiply, 3, 3);
        gridPane.add(btDivide, 3, 4);
        gridPane.add(btEquals, 2, 4);
        gridPane.add(btClear, 4,1, 1, 2);

        // ===== END ADDING ELEMENTS TO THE GRID =====

        // ===== BEGIN PROCESSING BUTTON EVENTS =====

        // Add Button
        btAdd.setOnAction(e -> {
            // Check the existing string to see if it already ends with an opp
            if (!checkForSymbol(result.getText())) {
                result.setText(result.getText() + " + ");
            } else {
                // Replacing the existing final operator with a new one
                String newText = replaceFinalSymbol("+");
                result.setText(""); // Clear text area
                result.setText(newText);
            }
        });

        // Subtract Button
        btSubtract.setOnAction(e -> {
            // Check the existing string to see if it already ends with an opp
            if (!checkForSymbol(result.getText())) {
                result.setText(result.getText() + " - ");
            } else {
                // Replacing the existing final operator with a new one
                String newText = replaceFinalSymbol("-");
                result.setText(""); // Clear text area
                result.setText(newText);
            }
        });

        // Multiply Button
        btMultiply.setOnAction(e -> {
            // Check the existing string to see if it already ends with an operator
            if (!checkForSymbol(result.getText())) {
                result.setText(result.getText() + " * ");
            } else {
                // Replacing the existing final operator with a new one
                String newText = replaceFinalSymbol("*");
                result.setText(""); // Clear text area
                result.setText(newText);
            }
        });

        // Divide Button
        btDivide.setOnAction(e -> {
            // Check the existing string to see if it already ends with an opp
            if (!checkForSymbol(result.getText())) {
                result.setText(result.getText() + " / ");
            } else {
                // Replacing the existing final operator with a new one
                String newText = replaceFinalSymbol("/");
                result.setText(""); // Clear text area
                result.setText(newText);
            }
        });

        // Clear Button
        btClear.setOnAction(e -> result.setText(""));

        // Equals Button
        btEquals.setOnAction(e -> equalsPressed());

        // Numerical Buttons
        bt0.setOnAction(e -> result.setText(result.getText() + "0"));
        bt1.setOnAction(e -> result.setText(result.getText() + "1"));
        bt2.setOnAction(e -> result.setText(result.getText() + "2"));
        bt3.setOnAction(e -> result.setText(result.getText() + "3"));
        bt4.setOnAction(e -> result.setText(result.getText() + "4"));
        bt5.setOnAction(e -> result.setText(result.getText() + "5"));
        bt6.setOnAction(e -> result.setText(result.getText() + "6"));
        bt7.setOnAction(e -> result.setText(result.getText() + "7"));
        bt8.setOnAction(e -> result.setText(result.getText() + "8"));
        bt9.setOnAction(e -> result.setText(result.getText() + "9"));

        // ===== END PROCESSING BUTTON EVENTS =====

        // Place the grid in a scene and display
        Scene scene = new Scene(gridPane);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkForSymbol(String text) {
        /** Checks the supplied string for ending in a math symbol and returns a boolean */
        String[] section = text.split(" ");
        String check = section[section.length - 1];
        return check.equals("+") || check.equals("-") || check.equals("*") || check.equals("/");
    }

    private String replaceFinalSymbol(String newSymbol) {
        /** Replaces the final element in the string with the passed in string */
        String[] split = result.getText().split(" ");
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            newString.append(split[i]).append(" ");
        }
        newString.append(newSymbol).append(" ");
        return newString.toString();
    }

    private double applyOperator(char operator, double b, double a) { // Since we are working with a stack and not a queue, b needs to come first here
        /** Applies the supplied operator to operands a and b */
        switch (operator) {
            case '+':
                return a+b;
            case '-':
                return a-b;
            case '*':
                return a*b;
            case '/':
                // Check for 0 divisor
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by 0");
                if (debugging) System.out.println("Division return: " + a + "/" + b + " = " + a / b);
                return a/b;
        }
        return 0;
    }

    private boolean hasPrecedence(char o1, char o2) {
        /** Determines if o2 has mathematically the same or more precedence over o1 */
        if ((o1 == '*' || o1 == '/') && (o2 == '+' || o2 == '-')) {
            return false;
        }
        else {
            return true;
        }
    }

    private void equalsPressed() {
        /** Performs the heavy lifting for this application. */
        // The overall idea is to create two stacks, one for numbers and one for operations. Determine precedence and
        // compute the results until we end up with a single value on the stack.

        // Generate a character array to iterate over
        char[] tokens = result.getText().toCharArray();

        // Number stack
        Stack<Double> values = new Stack<>();

        // Operator stack
        Stack<Character> ops = new Stack<Character>();

        // Check for a trailing operator or whitespace
        while (tokens[tokens.length - 1] == '+' || tokens[tokens.length - 1] == '-' || tokens[tokens.length - 1] == '*' || tokens[tokens.length - 1] == '/' || tokens[tokens.length - 1] == ' ')
            tokens = Arrays.copyOfRange(tokens, 0, tokens.length - 1);

        // Check for a leading operator or whitespace
        while (tokens[0] == '+' || tokens[0] == '-' || tokens[0] == '*' || tokens[0] == '/' || tokens[0] == ' ')
            tokens = Arrays.copyOfRange(tokens, 1, tokens.length);

        for (int i = 0; i < tokens.length; i++) {
            // Check for non-numbers/non-operators
            try {
                if (tokens[i] != '+' && tokens[i] != '-' && tokens[i] != '*' && tokens[i] != '/' && tokens[i] != ' ')
                    Integer.parseInt(String.valueOf(tokens[i]));
            }
            catch(NumberFormatException e) {
                result.setText("Invalid character detected");
                if (debugging) e.printStackTrace();
                break; // Bad character detected. Exit the loop.
            }

            // Ignore whitespace
            if (tokens[i] == ' ')
                continue;

            // Add any numbers to their stack
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                // Check for multi digit numbers
                StringBuilder buff = new StringBuilder();
                while (i < (tokens).length && tokens[i] >= '0' && tokens[i] <= '9') {
                    buff.append(tokens[i++]);
                }
                values.push(Double.valueOf(buff.toString()));
            }

            // Add operators to their stack
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                // While the top of stack has same or greater precedence to the current token,
                // solve what's on the stack.
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOperator(ops.pop(), values.pop(), values.pop()));
                }
                // Add operator to the stack.
                ops.push(tokens[i]);
            }
        }

        // All precedences have been resolved. Solve the remaining item in the stack.
        while (!ops.empty()) {
            values.push(applyOperator(ops.pop(), values.pop(), values.pop()));
        }

        // The top of values has the result, print it
        result.setText(""); // Clear the entry box
        // Handle trailing 0's
        double v = values.pop();
        if (v % 1.0 != 0)
            result.setText(String.format("%s", v));
        else
            result.setText(String.format("%.0f", v));
    }
}