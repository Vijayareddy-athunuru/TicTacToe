package ticpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Tictac extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X'; // Human player
    private char computerPlayer = 'O'; // Computer player
    private Random random = new Random();
    private JButton resetButton; // New reset button

    public Tictac() {
        // Set up the frame
        setTitle("Tic-Tac-Toe");
        setSize(300, 400); // Increased height for reset button
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add the title label
        JLabel titleLabel = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Initialize buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                buttonPanel.add(buttons[i][j]);
            }
        }

        // Add button panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);

        // Create and add reset button
        resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(e -> resetGame()); // Reset button action
        add(resetButton, BorderLayout.SOUTH); // Add reset button to the bottom

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        if (buttonClicked.getText().equals("")) {
            buttonClicked.setText(String.valueOf(currentPlayer));
            if (checkWinner(currentPlayer)) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetGame();
            } else {
                currentPlayer = computerPlayer; // Switch to computer's turn
                computerMove();
            }
        }
    }

    private void computerMove() {
        while (true) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(computerPlayer));
                if (checkWinner(computerPlayer)) {
                    JOptionPane.showMessageDialog(this, "Player " + computerPlayer + " wins!");
                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resetGame();
                }
                currentPlayer = 'X'; // Switch back to human player's turn
                break;
            }
        }
    }

    private boolean checkWinner(char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(String.valueOf(player)) &&
                 buttons[i][1].getText().equals(String.valueOf(player)) &&
                 buttons[i][2].getText().equals(String.valueOf(player))) ||
                (buttons[0][i].getText().equals(String.valueOf(player)) &&
                 buttons[1][i].getText().equals(String.valueOf(player)) &&
                 buttons[2][i].getText().equals(String.valueOf(player)))) {
                return true;
            }
        }
        // Check diagonals
        return (buttons[0][0].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][2].getText().equals(String.valueOf(player))) ||
               (buttons[0][2].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][0].getText().equals(String.valueOf(player)));
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        currentPlayer = 'X'; // Reset to human player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tictac::new);
    }
}
