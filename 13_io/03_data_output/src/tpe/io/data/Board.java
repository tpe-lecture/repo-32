package tpe.io.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Ein Dame-Brett.
 */
public class Board {

    /** Belegung des Brettes. */
    private Color[][] brett = new Color[8][8];

    /**
     * Setzt einen Stein an die gegebene Koordinate.
     *
     * @param koordinate Koordinate in "a1"-Notation.
     * @param farbe Fabe des Spielsteins.
     */
    public void set(String koordinate, Color farbe) {
        Tupel t = parseCoordinates(koordinate);
        brett[t.x][t.y] = farbe;
    }

    /**
     * Tupel von zwei int-Werten.
     */
    private static class Tupel {
        int x;
        int y;
    }

    /**
     * Wandelt textuelle Koordinaten in x- und y-Wert um.
     *
     * @param koordinate Koordinate als String.
     * @return Koordinate als Integer-Tupel.
     */
    private Tupel parseCoordinates(String koordinate) {
        char buchstabe = koordinate.toLowerCase().charAt(0);
        char zahl = koordinate.charAt(1);

        Tupel t = new Tupel();

        t.y = buchstabe - 'a';
        t.x = zahl - '1';

        if (t.x < 0 || t.x > 7) {
            throw new IllegalArgumentException();
        }

        if (t.y < 0 || t.y > 7) {
            throw new IllegalArgumentException();
        }

        return t;
    }

    /**
     * Schreibt den Inhalt des Spielfeldes in eine Datei.
     *
     * @param file Dateiname.
     * @throws IOException IO-Probleme
     */
    public void writeToFile(String file) throws IOException {

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        // TODO: Daten mit DataOutputStream persistieren

        // TODO: String "Schachbrett" speichern, um das Datenformat zu definieren
        dos.writeUTF("Schachbrett");


        for(int i=0; i<brett.length;i++){
            for(int j = 0; j<brett[i].length; j++){
                if(brett[i][j]!=null){
                    dos.write(i);
                    dos.write(j);
                    dos.writeInt(brett[i][j].ordinal());
                }
            }
        }

        dos.write(0xFF);
        dos.write(0xFF);
        dos.writeInt(-1);
        // TODO: x- und y-Koordinate des Steins und seine Farbe (als Integer)
        //       speichern

        // TODO: Dateiende mit 0xff 0xff -1 kennzeichnen
    }

    /**
     * Liest den Inhalt aus einer Datei.
     *
     * @param file Dateipfad.
     * @return das gelesene Spielfeld
     * @throws IOException IO-Probleme
     */
    public static Board loadFromFile(String file) throws IOException {
        // TODO: Daten aus Datei lesen

        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        Board board= new Board();
        String marker = dis.readUTF();

        if(!"Schachbrett".equals(marker)){
            throw new IOException("Falsch");
        }

        int i;

        while((i=dis.read())!=-1){
            if((byte)i==(byte)-1){
                break;
            }

            int j=dis.read();
            int ordinal = dis.readInt();
            board.brett[i][j]=Color.values()[ordinal];
        }
        dis.close();
        // TODO: Zuerst überprüfen, ob Datei mit "Schachbrett" anfängt,
        //       wenn nein, Ausnahme werfen

        // TODO: Positionen und Farbe der Figuren einlesen

        return board;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        char y = '1';

        result.append(" ");
        for (char c : "abcdefgh".toCharArray()) {
            result.append(' ').append(c);
        }

        result.append("\n");

        for (Color[] figurs : brett) {

            result.append(y++);

            for (Color figur : figurs) {
                if (figur != null) {
                    result.append(' ')
                        .append(figur);
                }
                else {
                    result.append("  ");
                }
            }

            result.append("\n");
        }

        return result.toString();
    }
}
