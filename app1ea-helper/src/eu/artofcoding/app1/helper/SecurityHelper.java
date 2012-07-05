package eu.artofcoding.app1.helper;

import org.jboss.security.Base64Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class SecurityHelper {

    /**
     * Make a hashed password.
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] makeHashedPassword(String algorithm, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] passwordBytes = password.getBytes();
        byte[] hash = md.digest(passwordBytes);
        return hash;
    }

    /**
     * Make a MD5 hashed password.
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] makeMD5Password(String password) throws NoSuchAlgorithmException {
        return makeHashedPassword("MD5",password);
    }

    /**
     * Make a Base64 encoded MD5 hashed password.
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String makeBase64EncodedMD5Password(String password) throws NoSuchAlgorithmException {
        byte[] passwordHash = makeMD5Password(password);
        return Base64Utils.tob64(passwordHash);
    }

    /**
     * Provide a CLI interface to generate passwords.
     * @param args
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String password;
        if (args.length == 1) {
            password = args[0];
        } else {
            System.out.print("Enter password: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            password = reader.readLine();
        }
        byte[] md5PasswordHash = makeMD5Password(password);
        String md5PasswordString = new String(md5PasswordHash);
        System.out.println("MD5 password hash: " + md5PasswordString);
        String base64PasswordHash = makeBase64EncodedMD5Password(password);
        System.out.println("Base64 encoded password hash: " + base64PasswordHash);
    }

}
