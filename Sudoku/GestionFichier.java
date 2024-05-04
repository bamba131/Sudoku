
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.security.auth.login.CredentialException;
import javax.swing.JFileChooser;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.ByteOrder;
import java.awt.*;

/*Cette classe nous permet de gérer tout ce qui est la gestion des fichiers cad elle nous permet d'ouvrir les fichiers 
et y prendre des grilles mais aussi de les sauvegarder */
public class GestionFichier {


    //Ouvre un fichier par les méthodes des flux d'octets
    public static void ouvrirFichier(JFrame frame, JTextField[][] grille) throws IOException {
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier GRI", "gri"); //Crée un filtre pour restreindre la sélection de fichiers aux fichiers avec l'extension ".gri"
        filechooser.addChoosableFileFilter(filter);
        filechooser.setAcceptAllFileFilterUsed(false);
        int v = filechooser.showOpenDialog(null);

        if (v == JFileChooser.APPROVE_OPTION) {
            File fichierchoisi = filechooser.getSelectedFile();
            FileInputStream entre = new FileInputStream(fichierchoisi);
            FileChannel channel = entre.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.BIG_ENDIAN);

            for (int i = 0; i <= 8; i++) {
                channel.read(buffer);
                buffer.flip();
                int valeur = buffer.getInt();
                String nbr = Integer.toString(valeur);
                // permet d'ajouter des 0 au debut si la ligne contient moin de 9 chiffre
                while (nbr.length() < 9) {
                    nbr = "0" + nbr;
                }

                for (int j = 0; j <= 8; j++) {
                    // permet de retirer les 0 de la grille
                    char charAtPosition = nbr.charAt(j);
                    if (charAtPosition == '0') {
                        grille[i][j].setText("");
                    } else {
                        grille[i][j].setText(String.valueOf(charAtPosition));
                        grille[i][j].setEditable(false);
                        grille[i][j].setForeground(Color.BLACK);
                    }
                }
                buffer.clear();
            }

            channel.close();
            entre.close();

        }

    }

    public static void sauvegarder(JFrame fenetre, JTextField[][] textFields) throws IOException {
        JFileChooser sauvegarder = new JFileChooser();
        FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichier GRI", "gri");

        sauvegarder.addChoosableFileFilter(filtre);
        sauvegarder.setAcceptAllFileFilterUsed(false);
        int verif = sauvegarder.showSaveDialog(null);

        if (verif == JFileChooser.APPROVE_OPTION) {
            File fichier = sauvegarder.getSelectedFile();

            if (!fichier.getPath().endsWith(".gri")) {
                fichier = new File(fichier.getPath() + ".gri");
            }

            FileOutputStream sortie = new FileOutputStream(fichier);
            FileChannel channel = sortie.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.BIG_ENDIAN);

            for (int i = 0; i <= 8; i++) {
                int valeur = 0;
                for (int j = 0; j <= 8; j++) {
                    String textFieldContent = textFields[i][j].getText();
                    int digit = textFieldContent.isEmpty() ? 0 : Integer.parseInt(textFieldContent);
                    valeur = valeur * 10 + digit; // Construire l'entier à partir des chiffres
                }

                buffer.putInt(valeur);
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }

            channel.close();
            sortie.close();
        }
    }
 }
    


