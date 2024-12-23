import Projet1.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

/**
 * La classe <code>elaborationGrille</code> est utilisé pour l'élaboration de grille de Sudoku.
 * Elle permet de créer, charger et sauvegarder des grilles de Sudoku.
 * 
 * @version 1.1
 * @author Bamba Top
 */
public class elaborationGrille {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grille de Sudoku");
        GrilleSudoku sudoku = new GrilleSudoku();
        frame.add(sudoku);
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        Object[] choix = { "Créer/Charger une grille", "Générer une grille" };

        String regles = "Règles du Sudoku :\n\n" +
        "1. Une grille Sudoku standard est une grille de 9x9 cases, divisée en neuf blocs de 3x3 cases.\n" +
        "2. Le but du jeu est de remplir la grille avec des chiffres de 1 à 9, de telle sorte que chaque ligne, chaque colonne et chaque bloc de 3x3 cases contienne tous les chiffres de 1 à 9, sans répétition.\n" +
        "3. Certaines cases de la grille peuvent contenir des chiffres initialement, et le joueur doit remplir les cases vides en suivant les règles ci-dessus.\n" +
        "4. Le Sudoku n'a qu'une seule solution valide.\n\n" +
        "Bonne chance !";

        JOptionPane.showMessageDialog(null, regles, "Règles du Sudoku", JOptionPane.INFORMATION_MESSAGE);

        int reponse = JOptionPane.showOptionDialog(null,
                "Que voulez vous?",
                "Question",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                null);

        if (reponse == 0) {
            // creation du bouton servant à charger une grille depuis un fichier.
            JButton oppenButton = new JButton("Charger une grille");
            oppenButton.addActionListener(e -> {
                try {
                    GestionFichier.ouvrirFichier(frame, sudoku.grille);
                } catch (IOException ex) {
                    System.err.println("erreur sur l'ouverture");
                }
            });
            buttonPanel.add(oppenButton);
        } else {

            //Bouton qui nous sert à génerer une grille si voulu.
            JButton gButton = new JButton("Generer une grille");
            gButton.addActionListener(new ActionListener() { //Evenement qui déclenche le processus par la classe GenerateurGrille.
                public void actionPerformed(ActionEvent e) {
                    GenerateurGrille.GenererGrille(sudoku.grille);
                }
            });
            buttonPanel.add(gButton);
        }
        //Bouton qui nous sert à sauvegarder une grille.
        JButton sButton = new JButton("Sauvegarder la grille");
        sButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GestionFichier.sauvegarder(frame, sudoku.grille);
                } catch (IOException ex) {
                    System.out.println("Erreur lors de la sauvegarde");
                }
            }
        });
        buttonPanel.add(sButton);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
