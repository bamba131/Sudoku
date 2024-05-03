import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class prog {
    public static void main(String[] args){
        JFrame frame = new JFrame("Grille de Sudoku");
        GrilleSudoku gs = new GrilleSudoku();
        frame.add(gs);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        Object[] choix = {"Importer une grille","Générer une grille"};
  
  
        int reponse = JOptionPane.showOptionDialog(null,
        "Que voulez vous?",
        "Question",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        choix,
        null);
  
        if (reponse == 0){
        // creation du bouton servant à ouvrir un fichier pour y récuperer une grille dans nos dossiers
        JButton oppenButton = new JButton("Charger une grille");
            oppenButton.addActionListener(e -> {
                try {
                    GestionFichier.ouvrirFichier(frame,gs.grille);
                } catch (IOException ex) {
                    System.out.println("Erreur lors de l'ouverture de la grille");
                }
            });
            buttonPanel.add(oppenButton);
      }
      else {
  
      JButton gButton = new JButton("Generer une grille");
      gButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            GenerateurGrille.generateGrid(gs.grille);
        }
      });
      buttonPanel.add(gButton);
    }
        //Creation du bouton qui nous permettra de résoudre si l'on en a envie.
        JButton rButton = new JButton("Résoudre la grille");
        rButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gs.resoudreGrille();
            }
        });
        buttonPanel.add(rButton);

        JButton sButton = new JButton("Sauvegarder la grille");
        sButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    GestionFichier.sauvegarder(frame, gs.grille);
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

