package code.DEV.DEVCOPIE.Java.Projet;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * La classe <code>GrilleSudoku</code> est la classe utilisée pour la conception
 * et l'affichage de la grille.
 * GrilleSudoku hérite de JComponent pour nous permettre d'intégrer la partie graphique.
 * 
 * @version 1.1
 * @author Bamba Top
 */
public class GrilleSudoku extends JComponent {
    public JTextField[][] grille;
    private Graphics pinceauG;
    private int erreurs = 0; // Variable pour compter le nombre d'erreurs si besoin
    private long start; //Variable pour mesurer le temps
    private long endGame;
    private int success = 0;
    
    /**
     * Constructeur destiné à l'initialisation de la grille.
     * 
     * @version 1.1
     * @author Bamba Top
     */
    public GrilleSudoku() {

        start = System.nanoTime(); // Initialisation du timer au début du jeu
        grille = new JTextField[9][9];
        setLayout(new GridLayout(9, 9,3,3)); // Utilisation d'un GridLayout pour organiser les JTextField
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final int ligne = i; 
                final int col = j;
                grille[i][j] = new JTextField();
                grille[i][j].setHorizontalAlignment(JTextField.CENTER);
                grille[i][j].setEditable(true); // Rendre le JTextField éditable
                grille[i][j].setBorder(BorderFactory.createEmptyBorder()); // Rendre la bordure vide
                add(grille[i][j]); // ajoute la grille dans le composant graphique

                Document doc = grille[i][j].getDocument();// Ajout du filtre au Document de chaque JTextField
                if (doc instanceof PlainDocument) {
                    ((PlainDocument) doc).setDocumentFilter(new JTextFieldLimit());
                }
            }
        }
        configurerEcouteurs(); // Appel de la méthode pour configurer les écouteurs d'événements
    }

    /**
     * Méthode servant à dessiner.
     */
    @Override
    protected void paintComponent(Graphics pinceau) {
    Graphics pinceauG = pinceau.create();
    this.pinceauG = pinceau.create();
    // Dessine la grille
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            String text = grille[i][j].getText();
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
    /**
     * sert à dessiner des cellules par la méthode paintComponent.
     * @param pinceauG
     * @param ligne
     * @param colonne
     * @param text
     */
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

    

     /**
      *Classe qui Définit une limite de caractères.
      */
    class JTextFieldLimit extends DocumentFilter {
        private int max = 12; // Limite de 12 caractères

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;

            // Vérifier si la longueur du texte après insertion dépasse la limite
            if ((fb.getDocument().getLength() + string.length()) <= max) {
                super.insertString(fb, offset, string.replaceAll("[^\\d\\{\\}]", ""), attr); // Permet uniquement les chiffres et les accolades
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;

            // Vérifier si la longueur du texte après remplacement dépasse la limite
            int newLength = fb.getDocument().getLength() + text.length() - length;
            if (newLength <= max) {
                super.replace(fb, offset, length, text.replaceAll("[^\\d\\{\\}]", ""), attrs); // Permet uniquement les chiffres et les accolades
            }
        }
    }
   
    /**
     * Méthode qui vérifie les cases,colonnes et blocs 3x3.
     * 
     * @return true si une valeur peut être rentrée sans problème.
     */
    public boolean entreeValide(int ligne, int colonne, int valeur) {
        for (int j = 0; j < 9; j++) {
            if (j != colonne && grille[ligne][j].getText().equals(String.valueOf(valeur))) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i != ligne && grille[i][colonne].getText().equals(String.valueOf(valeur))) {
                return false;
            }
        }

        int blocLigne = ligne / 3 * 3;
        int blocColonne = colonne / 3 * 3;
        for (int i = blocLigne; i < blocLigne + 3; i++) {
            for (int j = blocColonne; j < blocColonne + 3; j++) {
                if (!(i == ligne && j == colonne) && grille[i][j].getText().equals(String.valueOf(valeur))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Méthode qui gère l'interaction entre la grille et l'utilisateur.
     */
    public void configurerEcouteurs() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grille[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField source = (JTextField) e.getSource();
                        int ligne = -1;
                        int colonne = -1;
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                if (grille[i][j] == source) {
                                    ligne = i;
                                    colonne = j;
                                    break;
                                }
                            }
                        }

                        String texte = source.getText();
                        if (texte.length() > 1) {
                            // Si le texte de la case est supérieur à un chiffre, désactiver les contraintes du Sudoku
                            // en changeant la couleur de la police à noire
                            source.setForeground(Color.BLACK);
                            return ;
                        }

                        if(texte.length() < 1){
                            JOptionPane.showMessageDialog(null,"Entrez une valeur?!");
                            return;
                        }

                        int valeur = Integer.parseInt(source.getText());
                        if (!entreeValide(ligne, colonne, valeur)) {
                            source.setForeground(Color.RED);
                            erreurs++;
                           /*if (erreurs >= 3) {
                                JOptionPane.showMessageDialog(null, "Trop d'erreurs! Il faut réessayer.");
                                System.exit(0);
                            } */
                        } else {
                            source.setForeground(Color.BLACK);
                            erreurs = 0;
                        }
                        
                        if (isGrilleComplete() && entreeValide(ligne, colonne,valeur)) {
                            success++;
                            endGame = System.nanoTime(); // Enregistrer le temps de fin du jeu
                            // Calculer la durée du jeu en secondes
                            long dureeJeu = (endGame - start) / 1_000_000_000;
                            // Pour calculer naivement le temps en minutes
                            dureeJeu = dureeJeu - 10 ;
                            if(success == 1){
                            JOptionPane.showMessageDialog(null, "Félicitations! La grille est complète. La durée du jeu est de " +dureeJeu+" minutes.");
                            //Pas de System.exit pour que le joueur puisse relire le sudoku
                            }
                        }
                        else if(isGrilleComplete() && !entreeValide(ligne, colonne,valeur)){
                            JOptionPane.showMessageDialog(null,"La grille est fausse. Veuillez réessayer!");
                        }
                    }
                });
            }
        }
        success = 0;
    }

    

    /**
     * Méthode qui verifie si une grille est complète.
     * @return l'état de la grille.
     */
    public boolean isGrilleComplete(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if (grille[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
    
