/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printgudang;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.jdi.connect.spi.Connection;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import static java.lang.Thread.sleep;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.sql.DataSource;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import static printgudang.Home.st;


/**
 *
 * @author MOBILEDEV
 */
public final class PrintServices extends javax.swing.JFrame   {
     static ResultSet result;
     static String noSQ="";
     static ArrayList noProduk;
     static ArrayList namaProduk;
     static ArrayList qty;
     static ArrayList bwl;
     static ArrayList seri;
     static String namaSales="";
     static String namaGudang="";
     static String ket="";
     static String tgl="";
     static String jam="";
     static int jumlah = 0;
     static String  gudang;
     static String kelompokGudang="";
     static String waktu="";
     static String setWaktu="";
   
     static int jumlahLine=0;
     static  int initialSeconds=320;
     static  int row;
     static int column;
     
    
    
     DataSource dataSource ;
     CutrePool poll;
     Connection conn;
     private static final DefaultTableModel  tabelModel=getDefaultTabelModel();

    /**
     * Creates new form home
     */
    public  PrintServices   () {
         initComponents();
         setDefaultCloseOperation(server.DISPOSE_ON_CLOSE);
         initListener();
         this.setLocationRelativeTo(null);

    }
    
    
    
    private void initListener(){//berfungsi membaca event close frame
        this.addWindowListener(new WindowAdapter(){
        
            @Override
            public void windowClosing(WindowEvent e){
                 thread.interrupt();
                 exit();
               
            
            }
        
        
        });
    
    }
      private void exit(){
        this.dispose();
    }
    
    private static javax.swing.table.DefaultTableModel getDefaultTabelModel(){
        
        return new javax.swing.table.DefaultTableModel(
        new Object [][]{},
        new String[] {"No ","NO SQ","WAKTU"}){ 
        boolean[] canEdit=new boolean[]{
        false,false,false
        };
        
        public boolean isCellEdittable(int rowIndex,int coloumnIndex){
            return canEdit[coloumnIndex];
	}
     };
                
   }
    
    

    public static void data_tabel(){
       // SQ210922042
        tableSQ.removeAll();
        tableSQ.setModel(tabelModel);
        tableSQ.setRowHeight(17);
         tableSQ.getColumnModel().getColumn(0).setPreferredWidth(4);
                  tableSQ.getColumnModel().getColumn(2).setPreferredWidth(15);
        int i=0;
        try{
            result=st.executeQuery("SELECT a.SOANo AS noSQ,IF(NOW()>a.createAt+ INTERVAL '"+setWaktu+"' MINUTE,\"0000-00-00 00:00:00\",CONCAT(TIMEDIFF(a.createAt+ INTERVAL 10 MINUTE,NOW()),\":\",TIMESTAMPDIFF(SECOND,NOW(),a.createAt+ INTERVAL 10 MINUTE))) AS waktu  FROM detsq a\n" +
            "LEFT JOIN mssales b ON a.DSOSales=b.SaANo\n" +
            "LEFT JOIN msprod c ON a.PdaNo=c.PdaNo\n" +
            "LEFT JOIN trsq d ON a.SOANo=d.SOANo\n" +
            "WHERE DSOGdg='"+gudang+"' AND DATE_FORMAT(NOW(),'%Y%m%d')=d.SOTgl ORDER BY d.SOANo DESC");
            String [] kolom={"nomor","noSQ","waktu"};
            tabelModel.setDataVector(null, kolom);
            
            try {
                    while(result.next()){
                     i++;
                     tabelModel.addRow(new Object[] {i,
                     result.getString("noSQ"),    
                     result.getString("waktu"),
                     });
                 }
                
                
            } catch (IndexOutOfBoundsException e) {
                System.out.println("cek_value: "+e);
            }
             
             
        }catch(Exception e){
                JOptionPane.showMessageDialog(null,"koneksi ke server gagal hidupkan lagi aplikasi");
                System.exit(0);
                System.out.println("cek_value: "+e);
        }
    }
    
    public static PageFormat getPageFormat(PrinterJob pj){
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double middleHeight = 8.0; 
    
     
    if(noProduk.size()>=3){
        middleHeight = 5*noProduk.size(); 
    }

    double headerHeight = 4.0;                  
    double footerHeight = 4.0;                  
    double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
    double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
    
    paper.setSize(width,height );
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
	throws WriterException, IOException,IllegalArgumentException {
	BitMatrix matrix = new MultiFormatWriter().encode(
        new String(qrCodeData.getBytes(charset), charset),
        BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
        return matrix;
}

public static String readQRCode(String filePath, String charset, Map hintMap)throws FileNotFoundException, IOException, NotFoundException {
	BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
	new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
	Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,hintMap);
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

  
    //fungsi format kertas yang dicetak
    public static class setPrint implements Printable {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) throws PrinterException {
                int result = NO_SUCH_PAGE;
                 String qrCodeData="";
                if(noSQ.equals(null)){
                      qrCodeData="test";
                }else{
                     qrCodeData = noSQ+"_"+noProduk+"_"+gudang;
                }
            
                        
		
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
                 catch (IllegalArgumentException ex) {
                    java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    
            double width = pageFormat.getImageableWidth();                    
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());
            BufferedImage bi = new BufferedImage(20, 20,BufferedImage.TYPE_INT_RGB);
  
        try{
            /*Draw Header*/
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;

                            

            if(noProduk.size()>=5){
                g2d.drawImage(image, 0, -20,130,130, Color.BLACK, null);
            
            }else{
                g2d.drawImage(image, 0, 0, Color.BLACK, null);
            }
            
            g2d.setFont(new Font("Roman",Font.PLAIN,7));
        
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.drawString("",10,y);y+=headerRectHeight;
            
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
             g2d.setFont(new Font("Roman",Font.BOLD,8));
            g2d.drawString("QHOMEPRO        ",10,y);y+=yShift;
            g2d.setFont(new Font("Roman",Font.PLAIN,7));
            g2d.drawString("SALES QUOTATION        ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
            g2d.drawString("UNTUK GUDANG "+namaGudang,10,y);y+=headerRectHeight;
            
          
            String sales=namaSales;
            try {
                        if(sales.length()>16){
                          for (int i = 0; i < sales.length(); i += 16) {
                            System.out.println("cek_string "+sales.substring(i, Math.min(i + 16, sales.length())));
                            g2d.drawString(sales.substring(i, Math.min(i + 16, sales.length())),10,y);y+=yShift;
                        }
                       }
                        else{
                            g2d.drawString(sales,10,y);y+=yShift;
                        }
            } catch (NullPointerException e) {
                System.out.println(""+e);
            }
            
            g2d.drawString("",10,y);y+=headerRectHeight;
            g2d.setFont(new Font("Roman",Font.BOLD,8));
            g2d.drawString("#"+noSQ+"   "+tgl,10,y);y+=yShift;
            g2d.setFont(new Font("Roman",Font.PLAIN,7));
            g2d.drawString("Cetakan ke 1   "+jam,10,y);y+=yShift;
            g2d.drawString("Ket : "+ket,10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            for(int i=0; i<noProduk.size();i++){
                g2d.setFont(new Font("Roman",Font.BOLD,7));
                g2d.drawString(noProduk.get(i)+"",10,y);y+=yShift;
                g2d.setFont(new Font("Roman",Font.PLAIN,7));
                g2d.drawString((String) namaProduk.get(i),10,y);y+=yShift;
                g2d.drawString("BWL  : "+bwl.get(i),10,y);y+=yShift;
                g2d.setFont(new Font("Roman",Font.BOLD,7));
                g2d.drawString("SERI : "+seri.get(i)+"        "+qty.get(i)+" PCS",10,y);y+=yShift;
                g2d.setFont(new Font("Roman",Font.PLAIN,7));
                g2d.drawString("-------------------------------------",10,y);y+=yShift;
            }
            
          
            g2d.drawString("Kelompok Gudang :             "+kelompokGudang,10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.setFont(new Font("Roman",Font.BOLD,9));
            g2d.drawString("          TOTAL QTY:         "+noProduk.size(),10,y);y+=yShift;
            g2d.setFont(new Font("Roman",Font.PLAIN,7));
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
         
            
                   
           
             
           
            

          

    }
    catch(Exception r){
    r.printStackTrace();
    }
        result = PAGE_EXISTS;    
    }    
          return result;     //To change body of generated methods, choose Tools | Templates.
        }
    
    
    }
    
    
  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_print = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSQ = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        label_timer = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btn_print.setText("START");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        tableSQ.setBackground(new java.awt.Color(255, 255, 255));
        tableSQ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableSQ.setSelectionBackground(new java.awt.Color(204, 0, 51));
        jScrollPane1.setViewportView(tableSQ);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Gudang" }));

        jLabel1.setText("Gudang :");

        label_timer.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        label_timer.setText("0");
        label_timer.setToolTipText("");
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(64, 64, 64)
                        .addComponent(label_timer)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_print)
                        .addGap(41, 41, 41)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_timer))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
          Object selectedItem = jComboBox1.getSelectedItem();
        if (selectedItem != null)
        {
           
            gudang = selectedItem.toString();
             data_tabel();
            if(gudang.equals("Pilih Gudang")){
                JOptionPane.showMessageDialog(null,"pilih gudang dahulu..!!");
            }else{
               saveTempGudang();
               if (!thread.isAlive()){
                    thread.start();
                  
                }
            }
            // Some method that takes a string parameter.
        }
           
            
      
		// cut that paper!
//		byte[] cutP = new byte[] { 0x1d, 'V', 1 };
//		printerService.printBytes("POS-80C (copy 1)", cutP);


 
                
        
          
       
        
    }//GEN-LAST:event_btn_printActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //</editor-fold>

        /* Create and display the form */
        //  UIManager.put( "control", new Color( 128, 128, 128) );
  UIManager.put( "info", new Color(128,128,128) );
  UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
  UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
  UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
  UIManager.put( "nimbusFocus", new Color(115,164,209) );
  UIManager.put( "nimbusGreen", new Color(176,179,50) );
  UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
  UIManager.put( "nimbusLightBackground", new Color( 96, 96, 96) );//
  UIManager.put( "nimbusOrange", new Color(191,98,4) );
  UIManager.put( "nimbusRed", new Color(169,46,34) );
  UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
  UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
  UIManager.put( "text", new Color( 0, 0, 0) );
  try {
    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
      if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
      }
    }
  } catch (ClassNotFoundException e) {
    e.printStackTrace();
  } catch (InstantiationException e) {
    e.printStackTrace();
  } catch (IllegalAccessException e) {
    e.printStackTrace();
  } catch (javax.swing.UnsupportedLookAndFeelException e) {
    e.printStackTrace();
  } catch (Exception e) {
    e.printStackTrace();
  }
         try {
              getGudang();
              getWaktu();
             
         } catch (SQLException ex) {
             Logger.getLogger(PrintServices.class.getName()).log(Level.SEVERE, null, ex);
         
         }
        getTempGudang();
        getRandom();
        noProduk=new ArrayList();
        namaProduk=new ArrayList();
        seri=new ArrayList();
        qty=new ArrayList();
        bwl=new ArrayList();
        

    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JLabel label_timer;
    private static javax.swing.JTable tableSQ;
    // End of variables declaration//GEN-END:variables
        private static void getSQ() throws SQLException{
            
            try {
                
                  
                    result=st.executeQuery("SELECT a.SOANo AS noSQ,a.PdANo AS noProduk,b.PdNm AS namaProduk,d.SaNm AS namaSales,a.DSOGdg AS namaGudang,a.pb as bwl,a.PdSeri AS seri,a.DSOQty AS qty,SOKet AS ket,DATE_FORMAT(c.SOTgl, '%Y-%m-%d') AS tgl,SOJam AS jam  FROM detsq a\n" +
                    "LEFT JOIN msprod b ON a.PdaNo=b.PdaNo\n" +
                    "LEFT JOIN trsq c ON a.SOANo=c.SOANo\n" +
                    "LEFT JOIN mssales d ON c.SOSales=d.SaANo\n" +
                    "WHERE DSOGdg='"+gudang+"' AND a.print_by='' AND a.DSOKirim!='K' ORDER BY c.SOANo,c.SOTgl,c.SOJam DESC");
                    if(result==null){
                        JOptionPane.showMessageDialog(null,"cek query anda");
                        
                    }else{
                         while (result.next()){
                            noSQ=result.getString("noSQ");
                            noProduk.add(result.getString("noProduk"));
                            namaProduk.add(result.getString("namaProduk"));
                            seri.add(result.getString("seri"));
                            qty.add(result.getString("qty").substring(0, result.getString("qty").indexOf(".")));
                            bwl.add(result.getString("bwl"));
                            namaSales=result.getString("namaSales");
                            namaGudang=result.getString("namaGudang");
                          
                            ket=result.getString("ket");
                            tgl=result.getString("tgl");
                            jam=result.getString("jam");
                            
                            
                        
                        }
                    }
                   
                     //JOptionPane.showMessageDialog(null,""+noSQ);
                
            } catch (Exception e) {
                 System.out.println("error"+e.getMessage());
            }
            

            
        }
        
        
        public static void printNow(){
            		
		//print some stuff
		//printerService.printString("POS-58C", "\n\n TELOS \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                //fungsi untuk mencetak data dari dokumen
                 PrinterJob pj = PrinterJob.getPrinterJob();        
                 pj.setPrintable(new setPrint(),getPageFormat(pj));
                try {
                    pj.print();
                    updateSQ();
                    noSQ="";
                    noProduk.clear();
                    namaProduk.clear();
                    namaSales="";
                    namaGudang="";
                    seri.clear();
                    ket="";
                    tgl="";
                    jam="";
                    qty.clear();
                    bwl.clear();
                }
                catch (PrinterException ex) {
                 ex.printStackTrace();
                }
        }
        
       
            private static void updateSQ(){
                String status_cust="dikemas";
                 try{
                    st.executeUpdate("UPDATE  detsq  SET  print_by='"+gudang+"',status_group_gudang='"+kelompokGudang+"',status_customer='"+status_cust+"' where SOANo='"+noSQ+"' and DSOGdg='"+gudang+"';");
                   System.out.println("update berhasil ");
                    } catch(Exception e){
                      System.out.println("error: "+e);
                    }
        }
            
            
            
    
    //thread dipanggil berulang kali sebanyak 1000 mili second untuk cek database
 static Thread thread = new Thread() {
        @Override
        public void run() {
            try {
               
                while (!thread.isInterrupted()) {
                   try {
                         jumlah++;
                         label_timer.setText(""+jumlah);
                          getSQ();
                          data_tabel();
                          

                         System.out.println("running "+noSQ);
                         if(!noSQ.equals("")){
                            printNow();
                            noSQ="";
                             
                         }
                         
                   } catch (NullPointerException e) {
                    System.out.println("Cek Eroor"+e);
                   } catch (SQLException ex) {
                        Logger.getLogger(PrintServices.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Thread.sleep(1000);
                    
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                
            }
        }
    };
  
   
            
            
            
       void getGudang() throws SQLException {
       String query = "SELECT GdKd AS gudang FROM msgud";
       try {
           result=st.executeQuery(query);
           while ((!result.isClosed())&&(result.next())) {
            jComboBox1.addItem(result.getString(1));
           }
       }  catch (SQLException e) { 
            e.printStackTrace();
       }      

    }  
       
       
               
       void getWaktu() throws SQLException {
       String query = "SELECT setchar FROM mssetprog WHERE setano='wkt_gdg'";

       try {
           result=st.executeQuery(query);
           while ((!result.isClosed())&&(result.next())) {
             setWaktu=result.getString("setchar");
            
           }
       }  catch (SQLException e) { 
            e.printStackTrace();
       }      

    } 
       
       
       
  
       
        public static DataSource setupDataSource(String connectURI) {
            BasicDataSource ds = new BasicDataSource();
             ds.setDriverClassName("org.h2.Driver");
             ds.setUrl(connectURI);
             return ds;
         }
        
         public static void printDataSourceStats(DataSource ds) {
             BasicDataSource bds = (BasicDataSource) ds;
             System.out.println("NumActive: " + bds.getNumActive());
             System.out.println("NumIdle: " + bds.getNumIdle());
         }
         
        public static void shutdownDataSource(DataSource ds) throws SQLException {
             BasicDataSource bds = (BasicDataSource) ds;
             bds.close();
        }
        
        public  void saveTempGudang(){
                Properties properties = new Properties();
                properties.setProperty("gudang",gudang);
        try {
            properties.store(new FileOutputStream("gudang.properties"), "Database Konfigurasi MySQL");
            System.out.println("Pengaturan Berhasil Di simpan");
           
            
            
          }catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Gagal menyimpan pengaturan","",JOptionPane.WARNING_MESSAGE);
          }
          
        }
        
        public  void getTempGudang(){
          Properties properties = new Properties();
        
            try {
            //load file database.properties
                properties.load(new FileInputStream("gudang.properties"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Gagal load file database.properties");
            
            }
        
            String gudangTemp = properties.getProperty("gudang");
            
            if(!gudangTemp.equals("")){
                jComboBox1.setSelectedItem(gudangTemp);
            }
            
        
        
        }
  
        
      private void getRandom (){
            int min = 1;
            int max = 3;
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        if(random_int==1){
            kelompokGudang="A";
        }
        else if(random_int==2){
            kelompokGudang="B";
        }
        else{
            kelompokGudang="C";
        }
        
      }
      public static String getTime(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }
      
      public void PassTheTimes(int rows, int columns, int times) {
          row=rows;
          column=columns;
          initialSeconds=times;
          
        
    }
      
      public static int convertMinutetoSecond(int minute){
          int sec=0;
          sec=minute*60;
          return sec;
      }
      
      
        
        

       
}
