import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.omg.CORBA.ExceptionList;

import java.util.HashMap;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

public class PdfChecker {
    public static void main( String[] args ) throws IOException {
	for (int i = 0; i < args.length; i++ ) {
	    check( args[i], args.length );
	}
    }

    public static void check( String filename, int argc ) throws IOException {
        //List<PageDPI> exceptionsList = Collections.synchronizedList(new ArrayList<PageDPI>());
		Map<PdfKey, Vector<PageDPI>> exceptionsList = new ConcurrentHashMap<PdfKey, Vector<PageDPI>>();

    	PdfReader pdf = new PdfReader( filename );
	//if ( argc == 1 ) {
	    //output( null, "filename", filename );
	    //filename = null;
	//}
	// output( filename, "version", pdf.getPdfVersion() );
	// output( filename, "encryption", pdf.isEncrypted() );
	// if ( pdf.isEncrypted() ) {
	//     int permission = pdf.getPermissions();
	//     output( filename, "permission: allow print", ( ( permission & PdfWriter.ALLOW_PRINTING ) == PdfWriter.ALLOW_PRINTING ) );
	//     output( filename, "permission: allow modify", ( ( permission & PdfWriter.ALLOW_MODIFY_CONTENTS ) == PdfWriter.ALLOW_MODIFY_CONTENTS ) );
	//     output( filename, "permission: allow assembly", ( ( permission & PdfWriter.ALLOW_ASSEMBLY ) == PdfWriter.ALLOW_ASSEMBLY ) );
	//     output( filename, "permission: allow copy", ( ( permission & PdfWriter.ALLOW_COPY ) == PdfWriter.ALLOW_COPY ) );
	//     output( filename, "permission: allow screenreader", ( ( permission & PdfWriter.ALLOW_SCREENREADERS ) == PdfWriter.ALLOW_SCREENREADERS ) );
	// }
	HashMap map = pdf.getInfo();
	Iterator ite = map.keySet().iterator();
	// while( ite.hasNext() ) {
	//     String prop = (String) ite.next();
	//     output( filename, prop.toLowerCase(), map.get( prop ) );
	// }
	//output( filename, "pages", pdf.getNumberOfPages() );
	//print("Number of pages: " + pdf.getNumberOfPages());
    exceptionsList.put(new PdfKey(filename, pdf.getNumberOfPages()), new Vector<PageDPI>());

	  PdfReaderContentParser parser = new PdfReaderContentParser( pdf );
	  for (int i = 1; i <= pdf.getNumberOfPages(); i++) {
	    //output( filename, "page"+i, "pagesize", pdf.getPageSize( i ) );
	    PdfImageCheckRender listener = new PdfImageCheckRender( filename, i, pdf.getPageSize(i), exceptionsList );
		parser.processContent(i, listener);
	    //output( filename, "page"+i, "text length", listener.getResultantText().length() );
	  }

	  printResults(exceptionsList);
    }

    public static void printResults(Map<PdfKey, Vector<PageDPI>> exceptionsList) {
		for(PdfKey key: exceptionsList.keySet()) {
			System.out.println("Filename: " + key.getFilename());
			System.out.println("Number of pages: " + key.getNumberOfPages());

			Vector<PageDPI> vector = exceptionsList.get(key);
            if (vector.isEmpty()) {
              System.out.println("All pages have correct DPI.");
			  continue;
			}

			System.out.println("There are pages with low DPI. See below.");

			Iterator<PageDPI> itr = vector.iterator();

            while (itr.hasNext()) {
				PageDPI value = itr.next();
				System.out.println("Page number "+value.getPageNumber()+" has low DPI "+value.getDpiX()+" "+value.getDpiY());
			}
		}
	}

    static void output( String filename, String name, Object value ) {
	StringBuffer buf = new StringBuffer();
	if ( filename != null ) {
	    buf.append( filename + "\t" );
	}
	buf.append( name + "\t" + value );
	System.out.println( buf );
    }
    public static void output( String filename, String name, char value ) {
	output( filename, name, String.valueOf( value ) );
    }
    public static void output( String filename, String name, int value ) {
	output( filename, name, String.valueOf( value ) );
    }
    public static void output( String filename, String name, float value ) {
	output( filename, name, String.valueOf( value ) );
    }
    public static void output( String filename, String name, boolean value ) {
	output( filename, name, String.valueOf( value ) );
    }
    public static void output( String filename, String name1, String name2, Object value ) {
	output( filename, name1 + "\t" + name2, value );
    }
    public static void output( String filename, String name1, String name2, int value ) {
	output( filename, name1 + "\t" + name2, value );
    }
    public static void output( String filename, String name1, String name2, float value ) {
	output( filename, name1 + "\t" + name2, value );
    }
}
