import Projet1.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

/**
 * La classe <code>Resoudre</code> est utilisée pour la résolution 
 * de la grille de Sudoku.
 * Elle permet de charger et de résoudre automatiquement ou manuellement
 * une grille.
 * 
 * @version 1.1
 * @author Bamba Top
 */

public class Resoudre {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grille de Sudoku");
        GrilleSudoku sudoku = new GrilleSudoku();
        frame.add(sudoku);
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        Object[] choix = { "automatiquement", " manuellement" };

        String etape = "Etapes à respecter :\n\n" +
        "1. Choisissez un mode de résolution (Attention pas de retour en arrière possible)\n" +
        "2. Chargez votre grille sauvegardée\n" +
        "3. Résolvez votre grille (Il faut valider pour entrer une valeur)\n" +
        "Bonne chance !";

        JOptionPane.showMessageDialog(null, etape, "Etape à suivre", JOptionPane.INFORMATION_MESSAGE);

        int reponse = JOptionPane.showOptionDialog(null,
                "Comment voulez-vous résoudre la grille?",
                "Question",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                null);

        JButton oppenButton = new JButton("Charger la grille sauvegardée");
        oppenButton.addActionListener(e -> {
            try {
                GestionFichier.ouvrirFichier(frame, sudoku.grille);
            } catch (IOException exeption1) {
                System.err.println("erreur sur l'ouverture");
            }
        });
        buttonPanel.add(oppenButton, BorderLayout.WEST);

        if (reponse == 0) {
            // Creation du bouton qui nous permettra de résoudre si l'on en a envie.
            JButton rButton = new JButton("Résoudre la grille");
            rButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Resolution.resoudreGrille(sudoku.grille);
                }
            });
            buttonPanel.add(rButton);
        }

        JButton finPartie = new JButton("Finir la partie");
        finPartie.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //quitte la fenetre.
            }
        });
        buttonPanel.add(finPartie);
        
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);

    }
}
