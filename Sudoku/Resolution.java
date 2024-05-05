import javax.swing.*;

/**
 * La classe <code>Resolution</code> est utilisée pour la résolution 
 * automatique de la grille.
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
     * Méthode qui vérifie qu'un chiffre est deux fois dans la meme ligne.
     * @param grille 
     * @param chiffre
     * @param ligne
     * @return
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
     * Méthode qui vérifie qu'un chiffre est dans une colonne.
     * @param grille
     * @param chiffre
     * @param colonne
     * @return
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
     * Méthode qui vérifie qu'un chiffre est dans une case.
     * @param grille
     * @param chiffre
     * @param ligne
     * @param colonne
     * @return
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
     * Méthode qui vérifie si un chiffre peut être intégré dans une cellule.
     * @param grille
     * @param chiffre
     * @param ligne
     * @param colonne
     * @return
     */
    private static boolean isValidPlacement(JTextField[][] grille, String chiffre, int ligne, int colonne) {
        return !estDansLigne(grille, chiffre, ligne) &&
                !estDansColonne(grille, chiffre, colonne) &&
                !estDansCase(grille, chiffre, ligne, colonne);
    }
    
    /**
     * Méthode qui résoud la grille.
     * @param grille
     * @return
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
     * Méthode définit une pause pour la résolution.
     * @param milliseconds
     */
    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
