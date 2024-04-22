import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;


/* Pour créer une grille de Sudoku, on s'est d'abord attaqué à la structure de donnée de la grille.
 * Pour cela on a utilisé un tableau bidimensionel.
 * Pour ce qui est du dessin, on a fait un héritage de "GrilleSudoku" à la classe "JComponent"
 * afin d'avoir accés au nécessaire dans notre interface graphique.
*/


public class GrilleSudoku extends JComponent {
    private  int[][] grille;
    private Graphics pinceauG;
    public JTextField field;


// Le constructeur de la grille qui est la base pour comprendre ce qu'on affiche.
    public GrilleSudoku() {
        grille = new int[9][9]; // tableau bidimensionel pour definir colonne et ligne.
        // Initialise la grille avec des valeurs par défaut c'est à dire zéro.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grille[i][j] = 0;
            }
        }
    }

// Comme son nom l'indique permet de lire dans un fichier une grille.
    public void lireGrilleDepuisFichier(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) { // BufferedReader dans le boc try pour fermer le fichier ouvert dans tous les cas possibles.
            String ligne;
            int lignes = 0;
            // Lecture du fichier ligne par ligne
            while ((ligne = br.readLine()) != null && lignes < 9) {
                remplirLigneGrille(lignes, ligne);
                lignes++;
            }
            // Redessin de la grille après la lecture du fichier
            repaint();
        } catch (IOException e) {
            e.printStackTrace(); //Pour comprendre d'ou provient le problème
        }
    }

    // Remplir la grille ligne par ligne
    private void remplirLigneGrille(int ligneIndex, String ligne) {
        for (int colonne = 0; colonne < 9; colonne++) {
            char chiffre = ligne.charAt(colonne);
            if (chiffre >= '0' && chiffre <= '9') {
                grille[ligneIndex][colonne] = chiffre - '0';
            } else {
                grille[ligneIndex][colonne] = 0;
               
            }
        }
    }
// Pour les dessins
    @Override
    protected void paintComponent(Graphics pinceau) {
        Graphics pinceauG = pinceau.create();
        this.pinceauG = pinceau.create();
        // Dessine la grille
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valeur = grille[i][j];
                String text = (valeur == 0) ? "" : String.valueOf(valeur); // Condition booléenne
                dessinCell(pinceauG, i, j, text);
            }
        }
        // Dessine les lignes de séparation
        pinceauG.setColor(Color.BLACK);
        for (int i = 0; i <= 9; i++) {
            int epaisseur = (i % 3 == 0) ? 3 : 1;
            Graphics2D pinceauG2 = (Graphics2D) pinceauG;
            pinceauG2.setStroke(new BasicStroke(epaisseur));
            pinceauG.drawLine(i * getWidth() / 9, 0, i * getWidth() / 9, getHeight());
            pinceauG.drawLine(0, i * getHeight() / 9, getWidth(), i * getHeight() / 9);
        }
    }

    private void dessinCell(Graphics pinceauG, int ligne, int colonne, String text) {
        int x = colonne * getWidth() / 9;
        int y = ligne * getHeight() / 9;
        pinceauG.setColor(Color.BLACK);
        pinceauG.drawRect(x, y, getWidth() / 9, getHeight() / 9);
        Font font = new Font("Arial", Font.PLAIN, 20);
        pinceauG.setFont(font);
        FontMetrics metrics = pinceauG.getFontMetrics(font);
        int textX = x + (getWidth() / 9 - metrics.stringWidth(text)) / 2;
        int textY = y + ((getHeight() / 9 - metrics.getHeight()) / 2) + metrics.getAscent();
        pinceauG.drawString(text, textX, textY);
    }

// cette méthode nous sert par le biais de la classe SudokuSolver de résoudre la grille choisie.
    public void resoudreGrille() {
        SudokuSolver.resoudreGrille(grille); // on utilise la méthode resoudreGrille de SudokuSolver.
        repaint();
    }

   /*  public void genererGrille(){
    // remplir la grille de manière aléatoire 
    remplirGrilleAleatoirement();

    //Résoudre la grille générée pour obtenir une grille de Sudoku valide
    SudokuSolver.resoudreGrille(grille);

    // Effacer certains éléments de la grille pour la rendre partiellement rempli
    effacerElements();

    repaint();
    }*/

     private void remplirGrilleAleatoirement(){
        

    }
    

    private void effacerElements(){

    }

   /*  public static void main(String[] args){
       GrilleSudoku gs = new GrilleSudoku();
       gs.remplirGrilleAleatoirement();
        return grille;

    }*/
}
