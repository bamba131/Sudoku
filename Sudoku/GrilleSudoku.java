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



public class GrilleSudoku extends JComponent {
    public JTextField[][] grille;
    private Graphics pinceauG;
    private int erreurs = 0; // Variable pour compter le nombre d'erreurs


    public GrilleSudoku() {
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

                grille[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField source = (JTextField) e.getSource();
                        String text = source.getText();
                        if (text.length() < 4) { // Limiter à 4 chiffres max
                            // Ajouter le chiffre sélectionné à la fin du texte actuel dans la case
                            String value = source.getText();
                            if (!value.contains(String.valueOf(ligne))) { // Vérifier si le chiffre n'est pas déjà présent
                                source.setText(value + ligne);
                            }
                        }
                    }
                });
                add(grille[i][j]);

                // Ajout du DocumentFilter au Document de chaque JTextField
                Document doc = grille[i][j].getDocument();
                if (doc instanceof PlainDocument) {
                    ((PlainDocument) doc).setDocumentFilter(new JTextFieldLimit());
                }
            }
        }
        configurerEcouteurs(); // Appel de la méthode pour configurer les écouteurs d'événements
       
    }

   
    

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

    public void resoudreGrille() {
        Resolution.resoudreGrille(grille);
    }

     // Définit un DocumentFilter pour limiter la saisie à un seul caractère
     class JTextFieldLimit extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;

            if ((fb.getDocument().getLength() + string.length()) <= 4) {
                super.insertString(fb, offset, string.replaceAll("\\D", ""), attr); // Permet uniquement les chiffres
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;

            int newLength = fb.getDocument().getLength() + text.length() - length;
            if (newLength <= 1) {
                super.replace(fb, offset, length, text.replaceAll("\\D", ""), attrs); // Permet uniquement les chiffres
            }
        }
    }

    public boolean isValidInput(int ligne, int colonne, int valeur) {
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
                        int valeur = Integer.parseInt(source.getText());
                        if (!isValidInput(ligne, colonne, valeur)) {
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
                        
                        if (isGrilleComplete() && isValidInput(ligne, colonne,valeur)) {
                            JOptionPane.showMessageDialog(null, "Félicitations! La grille est complète.");
                            System.exit(0);
                        }
                        else if(isGrilleComplete() && !isValidInput(ligne, colonne,valeur)){
                            JOptionPane.showMessageDialog(null,"La grille est fausse. Veuillez réessayer!");
                        }
                    }
                });
            }
        }
    }

    

    // Méthode pour vérifier si la grille est complète
    public boolean isGrilleComplete() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grille[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
        
    }
}