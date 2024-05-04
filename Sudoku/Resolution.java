import javax.swing.*;

public class Resolution {
    /*La classe resolution nous permet de resoudre la grille automatiquement
     * en respectant les contraintes du Sudoku.
     * Une pause est effectué à la résolution automatiquement
     * pour pouvoir mesurer naîvement le temps nécessaire pour résoudre la grille.
    */

    private static final int TAILLE_GRILLE = 9;
    private static final int DELAI_MILLISECONDES = 100; // Délai en millisecondes entre chaque essai

    //Parcours la ligne pour detecter le meme chiffre.
    private static boolean estDansLigne(JTextField[][] grille, String chiffre, int ligne) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            if (grille[ligne][i].getText().equals(chiffre)) {
                return true;
            }
        }
        return false;
    }

    //Parcours la colonne pour detecter le meme chiffre.
    private static boolean estDansColonne(JTextField[][] grille, String chiffre, int colonne) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            if (grille[i][colonne].getText().equals(chiffre)) {
                return true;
            }
        }
        return false;
    }

    //Parcours le bloc 3x3 pour detecter le meme chiffre.
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

    //retourne true si le chiffre n'est pas dans la ligne, colonne et bloc.
    private static boolean isValidPlacement(JTextField[][] grille, String chiffre, int ligne, int colonne) {
        return !estDansLigne(grille, chiffre, ligne) &&
                !estDansColonne(grille, chiffre, colonne) &&
                !estDansCase(grille, chiffre, ligne, colonne);
    }
    
    //résoudre la grille en fesant des essais pour respecter les contraints
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

    //une méthode pause qui nous permet d'attendre 100 ms par essai.
    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
