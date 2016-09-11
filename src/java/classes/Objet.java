/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author pj
 */
public class Objet {
    
    private String errorMsg;
    private int test;
    protected Connection con;

    public Objet() {
        this.errorMsg="";
        this.test=0;
    }
    
    public void getConnection() throws NamingException, SQLException {
        Context initCtx = new InitialContext();
        DataSource ds=(DataSource) initCtx.lookup("java:comp/env/jdbc/MyDB");
        this.con=ds.getConnection();
    }
    
    public void closeConnection() throws SQLException {
        this.getCon().close();
    }
 
    public String codeHTML(String texte) {
        texte.replaceAll("<", "&lt;");
        texte.replaceAll(">", "&gt;");
        return texte;
    }
    
    public String codeHTML2(String texte) {
        texte.replaceAll("<%", "&lt;%");
        texte.replaceAll("%>", "%&lg;");
        texte.replaceAll("<script", "&lt;script");
        texte.replaceAll("script>", "script&gt;");
        return texte;
    }

    public String getEncoded(String key) throws NoSuchAlgorithmException {
    	byte[] uniqueKey = key.getBytes();
	byte[] hash = null;
	hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
	StringBuilder hashString = new StringBuilder();
	for ( int i = 0; i < hash.length; ++i ) {
	String hex = Integer.toHexString(hash[i]);
	if ( hex.length() == 1 ) {
	hashString.append('0');
	hashString.append(hex.charAt(hex.length()-1));
	} else {
	hashString.append(hex.substring(hex.length()-2));
	}
	}
	return hashString.toString();
	}
    
        public void setCookie(long id, HttpServletResponse response) throws NamingException, SQLException {
            this.getConnection();
            int nb=0;
            String cookieCodeCrypte="";
            do {
            try {
                String cookieCode="";
                for(int i=1;i<=6;i++)
                   cookieCode+=Datas.ARRAYCHARS[(int)(Math.random()*Datas.ARRAYCHARS.length)];
                cookieCodeCrypte=this.getEncoded(cookieCode);
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE cookie_code=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, cookieCodeCrypte);
                ResultSet result=prepare.executeQuery();
                result.next();
                nb=result.getInt("nb");
                result.close();
                prepare.close();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Objet.class.getName()).log(Level.SEVERE, null, ex);
            }
            } while (nb>0);
            String query="UPDATE table_membres SET cookie_code=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, cookieCodeCrypte);
            prepare.setLong(2, id);
            prepare.executeUpdate();
            prepare.close();
            Cookie cookie=new Cookie("emploisercook", cookieCodeCrypte);
            cookie.setMaxAge(60*60*24*30*6);
            response.addCookie(cookie);
            this.closeConnection();
        }
        
    public HttpSession initSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String idSession=session.getId();
        Cookie cookie=new Cookie("JSESSIONID", idSession);
        cookie.setMaxAge(60*60*24*300*1000);
        response.addCookie(cookie);
        session=request.getSession();
        return session;
    }
    public String encodeTitre(String titre) {
        titre=titre.toLowerCase();
        for(int i=0;i<Datas.ARRAYREPLACE1.length;i++) {
            titre=titre.replaceAll(Datas.ARRAYREPLACE1[i], Datas.ARRAYREPLACE2[i]);
            }
        return titre;
    }

    public boolean testExtension(String extension) {
        extension=extension.toLowerCase();
        if(extension.equals(".png")||extension.equals(".gif")||extension.equals(".jpg")||extension.equals(".jpeg"))
            return true;
        else
            return false;
    }
    
    public String encodeRecherche(String texte) {
        for(int i=0; i<Datas.ARRAYREPRECH1.length; i++)
            texte=texte.replaceAll(Datas.ARRAYREPRECH1[i], Datas.ARRAYREPRECH2[i]);
        return texte;
    }
    
    public void blank() {
            this.test=0;
            this.errorMsg="";
        }
    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @return the test
     */
    public int getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(int test) {
        this.test = test;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg += errorMsg;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }
}
