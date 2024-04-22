import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class SudokuSolver {
  
    private static final int TailleGrille = 9; // définition de la taille de grille cad ligne et colonne
    
    //Une méthode booléenne pour savoir si un nombre est dans la ligne.
    private static boolean estDansLigne(int[][] grille, int num, int ligne) {
      for (int i = 0; i < TailleGrille; i++) {
        if (grille[ligne][i] == num) {
          return true;
        }
      }
      return false;
    }
    //Une méthode booléenne pour savoir si un nombre est dans la colonne.
    private static boolean estDansColonne(int[][] grille, int num, int colonne) {
      for (int i = 0; i < TailleGrille; i++) {
        if (grille[i][colonne] == num) {
          return true;
        }
      }
      return false;
    }
    
    //Une méthode booléenne pour savoir si un nombre est dans la box c'est à dire le carré 3x3.
    private static boolean estDansCase(int[][] grille, int num, int ligne, int colonne) {
      int CaseLigne = ligne - ligne % 3;
      int CaseColonne = colonne - colonne % 3;
      
      for (int i = CaseLigne; i < CaseLigne + 3; i++) {
        for (int j = CaseColonne; j < CaseColonne + 3; j++) {
          if (grille[i][j] == num) {
            return true; 
          }
        }
      }
      return false;
    }
    
    //Une méthode booléenne pour les placements.
    private static boolean isValidPlacement(int[][] grille, int num, int ligne, int colonne) {
      return !estDansLigne(grille, num, ligne) &&
          !estDansColonne(grille, num, colonne) &&
          !estDansCase(grille, num, ligne, colonne);
    }

    //Une méthode booléenne pour résoudre la grille
    public static boolean resoudreGrille(int[][] grille) {
      for (int ligne = 0; ligne < TailleGrille; ligne++) {
        for (int colonne = 0; colonne < TailleGrille; colonne++) {
          if (grille[ligne][colonne] == 0) {
            for (int essai = 1; essai <= TailleGrille; essai++) {
              if (isValidPlacement(grille, essai, ligne, colonne)) {
                grille[ligne][colonne] = essai;
                
                if (resoudreGrille(grille)) {
                    System.out.println("Reussi");
                  return true;
                  
                }
                else {
                  grille[ligne][colonne] = 0;
                  System.out.println("Echec");
                }
              }
            }
            return false;
          }
        }
      }
      return true;
    } 
    
    
    public static void main(String[] args) {
      JFrame frame = new JFrame("Grille de Sudoku");
      GrilleSudoku gs = new GrilleSudoku();
      frame.add(gs);
      frame.setSize(500, 500);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel buttonPanel = new JPanel();
      Object[] choix = {"Importer une grille","Charger une grille"};


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
      JButton openButton = new JButton("Charger une grille");
      openButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              JFileChooser fileChooser = new JFileChooser();
              int returnValue = fileChooser.showOpenDialog(null);
              if (returnValue == JFileChooser.APPROVE_OPTION) {
                  File selectedFile = fileChooser.getSelectedFile();
                  gs.lireGrilleDepuisFichier(selectedFile);
              }
          }
      });
      buttonPanel.add(openButton);
    }
      //Creation du bouton qui nous permettra de résoudre si l'on en a envie.
      JButton rButton = new JButton("Résoudre la grille");
      rButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              gs.resoudreGrille();
          }
      });

      
      
      buttonPanel.add(rButton);
      frame.add(buttonPanel, BorderLayout.NORTH);

      frame.setVisible(true);
  }
  }
  
  
  