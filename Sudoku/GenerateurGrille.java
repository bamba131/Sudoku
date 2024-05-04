import javax.swing.JTextField;
import java.util.Random;

public class GenerateurGrille {

    private static final int TAILLE_GRILLE = 9;
    private static final int NOMBRES_ALEATOIRES = 30; // Nombre aléatoire de chiffres à placer
    private static final int COLONNES_A_PERMUTER = 12; // Nombre de colonnes à permuter

    // Génère une grille de Sudoku valide
    public static void generateGrid(JTextField[][] grille) {
        int[][] intGrid = new int[TAILLE_GRILLE][TAILLE_GRILLE];
        generate(intGrid);
        permuterColonnes(intGrid);
        placerNombresAleatoires(grille, intGrid);
        updateTextFieldGrid(grille, intGrid);
        effacerCases(grille); // Effacer un nombre aléatoire de cases
    }

    private static void permuterColonnes(int[][] grille) {
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

    // Met à jour les JTextField avec les valeurs de la grille générée
    private static void updateTextFieldGrid(JTextField[][] grille, int[][] intGrid) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                if (intGrid[i][j] != 0) {
                    grille[i][j].setText(String.valueOf(intGrid[i][j]));
                    grille[i][j].setEditable(false);
                } else {
                    grille[i][j].setText("");
                }
            }
        }
    }

    private static void placerNombresAleatoires(JTextField[][] grille, int[][] intGrid) {
        Random random = new Random();
        for (int i = 0; i < NOMBRES_ALEATOIRES; i++) {
            int ligne = random.nextInt(TAILLE_GRILLE);
            int col = random.nextInt(TAILLE_GRILLE);
            int num = random.nextInt(9) + 1; // Nombre aléatoire entre 1 et 9
            if (isValidPlacement(intGrid, num, ligne, col)) {
                intGrid[ligne][col] = num;
            }
        }
    }

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

    // Méthode récursive pour générer la grille
    private static boolean generate(int[][] grille) {
        Random random = new Random();
        for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
            for (int colonne = 0; colonne < TAILLE_GRILLE; colonne++) {
                if (grille[ligne][colonne] == 0) {
                    for (int num = 1; num <= TAILLE_GRILLE; num++) {
                        if (isValidPlacement(grille, num, ligne, colonne)) {
                            grille[ligne][colonne] = num;
                            if (generate(grille)) {
                                return true;
                            } else {
                                grille[ligne][colonne] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Vérifie si un nombre peut être placé dans la grille à la position spécifiée
    private static boolean isValidPlacement(int[][] grille, int num, int ligne, int colonne) {
        return !isNumberInRow(grille, num, ligne) &&
                !isNumberInColumn(grille, num, colonne) &&
                !isNumberInBox(grille, num, ligne, colonne);
    }

    // Vérifie si un nombre est déjà présent dans la ligne
    private static boolean isNumberInRow(int[][] grille, int num, int ligne) {
        for (int colonne = 0; colonne < TAILLE_GRILLE; colonne++) {
            if (grille[ligne][colonne] == num) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si un nombre est déjà présent dans la colonne
    private static boolean isNumberInColumn(int[][] grille, int num, int colonne) {
        for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
            if (grille[ligne][colonne] == num) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si un nombre est déjà présent dans la boîte 3x3
    private static boolean isNumberInBox(int[][] grille, int num, int ligne, int colonne) {
        int caseLigne = ligne - ligne % 3;
        int caseColonne = colonne - colonne % 3;
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