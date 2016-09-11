/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author pj
 */
public class Datas {
    public static final String URLROOT="http://www.emploiservices.net";
    public static final String TITRESITE="EMPLOI SERVICES";
    public static final String DIR="/var/datas/emploiser/";
    public static final String[] ARRAYCHARS={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    public static final String[] ARRAYREPLACE1={"%","=","\\^",":","/","&quot;","&","\\?"," ","é","è","ê","ë","à","ü","ô","ö","'","\\.","\""};
    public static final String[] ARRAYREPLACE2={"pourcent","","","","-","","et","-","-","e","e","e","e","a","u","o","o","-","","-"};
    public static final int MAXUPLOADSIZE=1000000;
    public static final int HAUT1=125;
    public static final int LARG2=100;
    private static final int MAX_WIDTH=800;
    private static final int MAX_HEIGHT=600;
    public static final int NBANNONCESINDEX=6;
    public static final int NBANNONCESPAGE=10;
    public static final String[] ARRAYREPRECH1={"le ", "la ", "les ","l'"," de", " dans", " du", "d'", " à", " a", " aux", " des"};
    public static final String[] ARRAYREPRECH2={"", "", "", "", " ", " ", " ", "", " ", " ", " ", " "};
    private static final long NBMOISEFFACE=8;
    private static final long NBMOISLIMITE=7;
    private static final String EMAIL_ADMINISTRATOR="hardibopj@yahoo.fr";

    /**
     * @return the NBMOISEFFACE
     */
    public static long getNBMOISEFFACE() {
        return NBMOISEFFACE;
    }

    /**
     * @return the NBMOISLIMITE
     */
    public static long getNBMOISLIMITE() {
        return NBMOISLIMITE;
    }

    /**
     * @return the EMAIL_ADMINISTRATOR
     */
    public static String getEMAIL_ADMINISTRATOR() {
        return EMAIL_ADMINISTRATOR;
    }

    /**
     * @return the MAX_WIDTH
     */
    public static int getMAX_WIDTH() {
        return MAX_WIDTH;
    }

    /**
     * @return the MAX_HEIGHT
     */
    public static int getMAX_HEIGHT() {
        return MAX_HEIGHT;
    }
}
