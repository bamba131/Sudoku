package Projet1;

import javax.swing.*;

/**
 * La classe <code>Resolution</code> est utilisée pour la résolution automatique de la grille de Sudoku.
 * Elle propose des méthodes pour vérifier la validité d'un placement de chiffre dans la grille ainsi que pour résoudre la grille.
 * 
 * @version 1.1
 * @author Bamba Top
 */
public class Resolution {
    /**
     * Constante qui définit la taille de la grille (c'est à dire le nombre de lignes et de colonnes).
     */
    private static final int TAILLE_GRILLE = 9;
    /**
     * Constante qui définit un délai en millisecondes pour le calcul du temps écoulé.
     */
    private static final int DELAI_MILLISECONDES = 100; 

    
    /**
     * Constructeur par défaut de la classe Resolution.
     * Ce constructeur est utilisé pour créer une instance de Resolution.
     */
    public Resolution() {
        // Code du constructeur...
    }

    /**
     * Méthode qui vérifie si un chiffre est déjà présent dans une ligne de la grille.
     * 
     * @param grille la grille de Sudoku représentée par un tableau de champs de texte (JTextField)
     * @param chiffre le chiffre à vérifier
     * @param ligne la ligne dans laquelle on vérifie la présence du chiffre
     * @return true si le chiffre est présent dans la ligne, sinon false
     */
    private static boolean estDansLigne(JTextField[][] grille, String chiffre, int ligne) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            if (grille[ligne][i].getText().equals(chiffre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui vérifie si un chiffre est déjà présent dans une colonne de la grille.
     * 
     * @param grille la grille de Sudoku représentée par un tableau de champs de texte (JTextField)
     * @param chiffre le chiffre à vérifier
     * @param colonne la colonne dans laquelle on vérifie la présence du chiffre
     * @return true si le chiffre est présent dans la colonne, sinon false
     */
    private static boolean estDansColonne(JTextField[][] grille, String chiffre, int colonne) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            if (grille[i][colonne].getText().equals(chiffre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui vérifie si un chiffre est déjà présent dans une sous-grille de 3x3 de la grille.
     * 
     * @param grille la grille de Sudoku représentée par un tableau de champs de texte (JTextField)
     * @param chiffre le chiffre à vérifier
     * @param ligne la ligne de la cellule dans laquelle on vérifie la présence du chiffre
     * @param colonne la colonne de la cellule dans laquelle on vérifie la présence du chiffre
     * @return true si le chiffre est présent dans la sous-grille, sinon false
     */
    private static boolean estDansCase(JTextField[][] grille, String chiffre, int ligne, int colonne) {
        int caseLigne = ligne - ligne % 3;
        int caseColonne = colonne - colonne % 3;

        for (int i = caseLigne; i < caseLigne + 3; i++) {
            for (int j = caseColonne; j < caseColonne + 3; j++) {
                if (grille[i][j].getText().equals(chiffre)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Méthode qui vérifie si un chiffre peut être intégré dans une cellule de la grille.
     * @param grille la grille de Sudoku représentée par un tableau de champs de texte (JTextField)
     * @param chiffre le chiffre à placer
     * @param ligne la ligne de la cellule dans laquelle on veut placer le chiffre
     * @param colonne la colonne de la cellule dans laquelle on veut placer le chiffre
     * @return true si le chiffre peut être placé dans la cellule, sinon false
     */
    private static boolean isValidPlacement(JTextField[][] grille, String chiffre, int ligne, int colonne) {
        return !estDansLigne(grille, chiffre, ligne) &&
                !estDansColonne(grille, chiffre, colonne) &&
                !estDansCase(grille, chiffre, ligne, colonne);
    }
    
    /**
     * Méthode qui résout la grille de Sudoku récursivement en utilisant la technique de backtracking.
     * @param grille la grille de Sudoku à résoudre représentée par un tableau de champs de texte (JTextField)
     * @return true si la grille est résolue avec succès, sinon false
     */
    public static boolean resoudreGrille(JTextField[][] grille) {
        for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
            for (int colonne = 0; colonne < TAILLE_GRILLE; colonne++) {
                if (grille[ligne][colonne].getText().equals("")) {
                    for (int essai = 1; essai <= TAILLE_GRILLE; essai++) {
                        String chiffre = String.valueOf(essai);
                        if (isValidPlacement(grille, chiffre, ligne, colonne)) {
                            grille[ligne][colonne].setText(chiffre);
                            grille[ligne][colonne].repaint(); // Redessiner la grille après chaque essai
                            pause(DELAI_MILLISECONDES); // Ajouter un délai entre chaque essai
                            if (resoudreGrille(grille)) {
                                return true;
                            } else {
                                grille[ligne][colonne].setText("");
                                grille[ligne][colonne].repaint(); // Redessiner la grille après chaque essai
                                pause(DELAI_MILLISECONDES); // Ajouter un délai entre chaque essai
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
     * Méthode qui définit une pause pour la résolution de la grille.
     * 
     * @param milliseconds le nombre de millisecondes à attendre
     */
    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
