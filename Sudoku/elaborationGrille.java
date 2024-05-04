import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class elaborationGrille {
    public static void main(String[] args){
        JFrame frame = new JFrame("Grille de Sudoku");
        GrilleSudoku gs = new GrilleSudoku();
        frame.add(gs);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        Object[] choix = {"Créer/Charger une grille","Générer une grille"};
  
  
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
                   System.err.println("erreur sur l'ouverture");
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
