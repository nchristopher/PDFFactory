/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffactory;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;

/**
 *
 * @author Nimil
 */
public class PDFFactory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        // TODO code application logic here
        CreatePDF createSample = new CreatePDF("C:\\Users\\Nimil\\Documents\\NetBeansProjects\\PDFFactory\\src\\pdffactory\\a.pdf","");
    }
}
