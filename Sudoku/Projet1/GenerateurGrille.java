package Projet1;

import javax.swing.JTextField;
import java.util.Random;


/**
 * La classe <code>GenerateurGrille</code> est utilisée pour générer des grilles de Sudoku.
 * 
 * @version 1.1
 * @author Bamba Top
 */
public class GenerateurGrille {

    /**
     * Constante qui définit la taille de la grille (c'est à dire le nombre de lignes et de colonnes).
     */
    private static final int TAILLE_GRILLE = 9;
    /**
     * Constante qui définit un nombre aléatoire de chiffres à placer dès le début.
     */
    private static final int NOMBRES_ALEATOIRES = 30; 
    /**
     * Constante qui définit les colonnes à permuter pour ne pas avoir les mêmes grilles.
     */
    private static final int COLONNES_A_PERMUTER = 12; 

     /**
     * Constructeur par défaut de la classe GenerateurGrille.
     * Ce constructeur est utilisé pour créer une instance de GenerateurGrille.
     */
    public GenerateurGrille() {
        //dispensable
    }


   /**
    * Constructeur destiné à la création d'une grille valide.
    * 
    * @param grille la grille de Sudoku à générer
    */
    public static void GenererGrille(JTextField[][] grille) {
        int[][] intGrille = new int[TAILLE_GRILLE][TAILLE_GRILLE];
        generate(intGrille);
        permuterColonnes(intGrille);
        placerNombresAleatoires(grille, intGrille);
        updateTextFieldGrid(grille, intGrille);
        effacerCases(grille); // Effacer un nombre aléatoire de cases
    }

    /**
     * Méthode qui échange des colonnes pour créer des grilles différentes.
     * 
     * @param grille la grille de Sudoku à modifier
     */
    public static void permuterColonnes(int[][] grille) {
        Random random = new Random();
        for (int i = 0; i < COLONNES_A_PERMUTER; i++) {
            int col1 = random.nextInt(TAILLE_GRILLE / 3) * 3;
            int col2 = random.nextInt(TAILLE_GRILLE / 3) * 3;
            for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
                int temp = grille[ligne][col1];
                grille[ligne][col1] = grille[ligne][col2];
                grille[ligne][col2] = temp;
            }
        }
    }

    /**
     * Méthode qui met à jour les JTextField avec les valeurs de la grille générée.
     * @param grille la grille de JTextField à mettre à jour
     * @param intGrille la grille de valeurs numériques
     */
    public static void updateTextFieldGrid(JTextField[][] grille, int[][] intGrille) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                if (intGrille[i][j] != 0) {
                    grille[i][j].setText(String.valueOf(intGrille[i][j]));
                    grille[i][j].setEditable(false);
                } else {
                    grille[i][j].setText("");
                }
            }
        }
    }

    /**
     * Méthode qui place des nombres aléatoires dans la grille.
     * @param grille la grille de Sudoku à remplir
     * @param intGrille la grille de valeurs numériques
     */
    public static void placerNombresAleatoires(JTextField[][] grille, int[][] intGrille) {
        Random random = new Random();
        for (int i = 0; i < NOMBRES_ALEATOIRES; i++) {
            int ligne = random.nextInt(TAILLE_GRILLE);
            int col = random.nextInt(TAILLE_GRILLE);
            int num = random.nextInt(9) + 1; // Nombre aléatoire entre 1 et 9
            if (estValide(intGrille, num, ligne, col)) {
                intGrille[ligne][col] = num;
            }
        }
    }
    /**
     * Méthode qui efface un nombre aléatoire de cases dans la grille.
     * 
     * @param grille la grille de Sudoku à modifier
     */
    public static void effacerCases(JTextField[][] grille) {
        Random random = new Random();
        int casesAEffacer = random.nextInt(30) + 50; // Nombre aléatoire de cases à effacer.
        for (int i = 0; i < casesAEffacer; i++) {
            int ligne = random.nextInt(TAILLE_GRILLE);
            int col = random.nextInt(TAILLE_GRILLE);
            grille[ligne][col].setText(""); // Effacer la case
            grille[ligne][col].setEditable(true); // Rendre la case éditable pour l'utilisateur
        }
    }

     /**
     * Méthode récursive pour générer la grille de Sudoku.
     * 
     * @param grille la grille de Sudoku à remplir
     * @return true si la grille est générée avec succès, sinon false
     */
    public static boolean generate(int[][] grille) {
        Random random = new Random();
        for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
            for (int col = 0; col < TAILLE_GRILLE; col++) {
                if (grille[ligne][col] == 0) {
                    for (int num = 1; num <= TAILLE_GRILLE; num++) {
                        if (estValide(grille, num, ligne, col)) {
                            grille[ligne][col] = num;
                            if (generate(grille)) {
                                return true;
                            } else {
                                grille[ligne][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Vérifie si un nombre peut être placé dans la grille à la position spécifiée.
     * 
     * @param grille la grille de Sudoku à vérifier
     * @param num le nombre à placer
     * @param ligne la ligne de la position
     * @param col la colonne de la position
     * @return true si le nombre peut être placé, sinon false
     */
    public static boolean estValide(int[][] grille, int num, int ligne, int col) {
        return !estDansLigne(grille, num, ligne) &&
                !estDansColonne(grille, num, col) &&
                !estDansCase(grille, num, ligne, col);
    }

    /**
     * Vérifie si un chiffre est déjà présent dans la ligne.
     * 
     * @param grille la grille de Sudoku à vérifier
     * @param num le chiffre à vérifier
     * @param ligne la ligne à vérifier
     * @return true si le chiffre est présent dans la ligne, sinon false
     */
    public static boolean estDansLigne(int[][] grille, int num, int ligne) {
        for (int col = 0; col < TAILLE_GRILLE; col++) {
            if (grille[ligne][col] == num) {
                return true;
            }
        }
        return false;
    }

     /**
     * Vérifie si un chiffre est déjà présent dans la colonne.
     * 
     * @param grille la grille de Sudoku à vérifier
     * @param num le chiffre à vérifier
     * @param col la colonne à vérifier
     * @return true si le chiffre est présent dans la colonne, sinon false
     */
    public static boolean estDansColonne(int[][] grille, int num, int col) {
        for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
            if (grille[ligne][col] == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un chiffre est déjà présent dans le bloc 3x3.
     * 
     * @param grille la grille de Sudoku à vérifier
     * @param num le chiffre à vérifier
     * @param ligne la ligne du bloc à vérifier
     * @param col la colonne du bloc à vérifier
     * @return true si le chiffre est présent dans le bloc, sinon false
     */
    public static boolean estDansCase(int[][] grille, int num, int ligne, int col) {
        int caseLigne = ligne - ligne % 3;
        int caseColonne = col - col % 3;
        for (int r = caseLigne; r < caseLigne + 3; r++) {
            for (int c = caseColonne; c < caseColonne + 3; c++) {
                if (grille[r][c] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}