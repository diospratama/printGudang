/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printgudang;

/**
 *
 * @author MOBILEDEV
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;


import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import net.vibrac.escpospi.image.Image;

public class PrinterService implements Printable {
	
	public List<String> getPrinters(){
		
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		
		PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		
		List<String> printerList = new ArrayList<String>();
		for(PrintService printerService: printServices){
			printerList.add( printerService.getName());
		}
		
		return printerList;
	}
        



	public void printString(String printerName, String text) {
		
		// find the printService of name printerName
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		PrintService service = findPrintService(printerName, printService);

		DocPrintJob job = service.createPrintJob();
                 byte[] barCode = {0x1d,0x6b,0x07,0x6e,0x61,0x72}; 
               
		try {

			byte[] bytes;

			// important for umlaut chars
			bytes = text.getBytes("CP437");
			Doc doc = new SimpleDoc(barCode, flavor, null);
			job.print(doc, null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void printBytes(String printerName, byte[] bytes) {
		
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		PrintService service = findPrintService(printerName, printService);
		DocPrintJob job = service.createPrintJob();

		try {

			Doc doc = new SimpleDoc(bytes, flavor, null);

			job.print(doc, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private PrintService findPrintService(String printerName,
			PrintService[] services) {
		for (PrintService service : services) {
			if (service.getName().equalsIgnoreCase(printerName)) {
				return service;
			}
		}

		return null;
	}
        




 

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = NO_SUCH_PAGE;
                String qrCodeData = "Hello World!";
		String filePath = "QRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                BufferedImage image = null;
                
                try {
            BitMatrix matrik=createQRCode(qrCodeData, charset, hintMap, 80, 80);
            image = MatrixToImageWriter.toBufferedImage(matrik);
                } catch (WriterException ex) {
                    java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    
            double width = pageFormat.getImageableWidth();                    
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());
            BufferedImage bi = new BufferedImage(20, 20,BufferedImage.TYPE_INT_RGB);
          
  
  
            ////////// code by alqama//////////////
            FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,5));
        //    int idLength=metrics.stringWidth("000000");
            int idLength2=metrics.stringWidth("00");
            int idLength=metrics.stringWidth("000");
            int amtLength=metrics.stringWidth("000000");
            int qtyLength=metrics.stringWidth("00000");
            int priceLength=metrics.stringWidth("000000");
            int prodLength=(int)width - idLength - amtLength - qtyLength - priceLength-17;

        //    int idPosition=0;
        //    int productPosition=idPosition + idLength + 2;
        //    int pricePosition=productPosition + prodLength +10;
        //    int qtyPosition=pricePosition + priceLength + 2;
        //    int amtPosition=qtyPosition + qtyLength + 2;
            
            int productPosition = 0;
            int discountPosition= prodLength+5;
            int pricePosition = discountPosition +idLength+10;
            int qtyPosition=pricePosition + priceLength + 4;
            int amtPosition=qtyPosition + qtyLength;
            
            
              
        try{
            /*Draw Header*/
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;
            int headerRectHeighta=40;
            
            
                
            g2d.setFont(new Font("Roman",Font.PLAIN,7));
            g2d.drawImage(image, 0, 0, Color.BLACK, null);
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("",10,y);y+=headerRectHeight;
            
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("SALES QUOTATION        ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
            g2d.drawString("UNTUK GUDANG G16",10,y);y+=headerRectHeight;
            
          
            String sales="AFIFAH SALAMAH (SC LUXMENNNNNNNNNNNNNNNNNNNN)";
            if(sales.length()>16){
                  for (int i = 0; i < sales.length(); i += 16) {
                    System.out.println("cek_string "+sales.substring(i, Math.min(i + 16, sales.length())));
                    g2d.drawString(sales.substring(i, Math.min(i + 16, sales.length())),10,y);y+=yShift;
                }
                
            }
          
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("#SQ211782891   21-09-2021",10,y);y+=yShift;
            g2d.drawString("Cetakan ke 1   11:00",10,y);y+=yShift;
            g2d.drawString("Ket BWL DPN",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("00139031 PANASONIC",10,y);y+=yShift;
            g2d.drawString("NLP NEW 71310/COOL WHITE DOWN",10,y);y+=yShift;
            g2d.drawString("BWL : G16 PB DEPAN",10,y);y+=yShift;
            g2d.drawString("SERI :             4 UNIT",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("          ToTAL QTY:         4",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
                   
           
             
           
            

          

    }
    catch(Exception r){
    r.printStackTrace();
    }
        result = PAGE_EXISTS;    
    }    
          return result;    
    }//To change body of generated methods, choose Tools | Templates.
    
    
    
  public PageFormat getPageFormat(PrinterJob pj){
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double middleHeight =8.0;  
    double headerHeight = 4.0;                  
    double footerHeight = 4.0;                  
    double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
    double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(                    
        0,
        20,
        width,            
        height - convert_CM_To_PPI(1)
    );   //define boarder size    after that print area width is about 180 points
            
    pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
    pf.setPaper(paper);    

    return pf;
}
     
protected static double convert_CM_To_PPI(double cm) {            
	        return toPPI(cm * 0.293600787);            
}
 
protected static double toPPI(double inch) {            
	        return inch * 72d;            
}


public static BitMatrix createQRCode(String qrCodeData,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            return matrix;
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
				hintMap);
		return qrCodeResult.getText();
	}
   


 void drawString(Graphics g, String text, int x, int y) {
    for (String line : text.split("\n"))
        g.drawString(line, x, y += g.getFontMetrics().getHeight());
}
 
 
 
 
 
 public static void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
    FontMetrics m = g.getFontMetrics();
    if(m.stringWidth(text) < lineWidth) {
        g.drawString(text, x, y);
    } else {
        String[] words = text.split(" ");
        String currentLine = words[0];
        for(int i = 1; i < words.length; i++) {
            if(m.stringWidth(currentLine) < lineWidth) {
                currentLine += " "+words[i];
                System.out.println("CEK_STRING1 "+currentLine);
            } else {
                g.drawString(currentLine, x, y);
                System.out.println("CEK_STRING2 "+currentLine);
                y += m.getHeight();
                currentLine = words[i];
            }
        }
        if(currentLine.trim().length() > 0) {
            g.drawString(currentLine, x, y);
             System.out.println("CEK_STRING3 "+currentLine);
        }
    }
}


}
