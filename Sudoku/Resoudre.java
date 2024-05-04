import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class Resoudre {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grille de Sudoku");
        GrilleSudoku gs = new GrilleSudoku();
        frame.add(gs);
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        Object[] choix = { "automatiquement", " manuellement" };

        int reponse = JOptionPane.showOptionDialog(null,
                "Comment voulez-vous résoudre la grille?",
                "Question",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                null);

        if (reponse == 0) {
            // Creation du bouton qui nous permettra de résoudre si l'on en a envie.
            JButton rButton = new JButton("Résoudre la grille");
            rButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gs.resoudreGrille();
                }
            });
            buttonPanel.add(rButton);
        }
        // creation du bouton servant à ouvrir un fichier pour y récuperer une grille
        // dans nos dossiers
        JButton oppenButton = new JButton("Charger la grille sauvegardée");
        oppenButton.addActionListener(e -> {
            try {
                GestionFichier.ouvrirFichier(frame, gs.grille);
            } catch (IOException exeption1) {
                System.err.println("erreur sur l'ouverture");
            }
        });
        buttonPanel.add(oppenButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);

    }
}
