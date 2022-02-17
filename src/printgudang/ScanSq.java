/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printgudang;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import static printgudang.Home.st;
import static printgudang.PrintServices.data_tabel;
import static printgudang.PrintServices.gudang;
import static printgudang.PrintServices.jumlah;
import static printgudang.PrintServices.noSQ;
import static printgudang.PrintServices.printNow;
import static printgudang.PrintServices.qty;
import static printgudang.PrintServices.result;
import static printgudang.PrintServices.setWaktu;
import static printgudang.PrintServices.thread;


/**
 *
 * @author MOBILEDEV
 */
public class ScanSq extends javax.swing.JFrame {
    
    
    private static final DefaultTableModel  tabelModel=getDefaultTabelModel();
    
     static String PBs="PB DEPAN";
     static String PBTEMP="";
     static String barcode="";
     static String gudang="";
     static String nosq="";
     static ResultSet results;
     static String waktuDurasi="";
 

    /**
     * Creates new form ScanSq
     */
    public ScanSq() {
        initComponents();
        
       setDefaultCloseOperation(server.DISPOSE_ON_CLOSE);
        tableSQ.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableSQ.getTableHeader().setOpaque(false);
        tableSQ.getTableHeader().setBackground(new Color(220,20,60));
        tableSQ.getTableHeader().setForeground(new Color(255,255,255));
        tableSQ.setRowHeight(40);
       
        initListener();
        tableSQ.setDragEnabled(false);
        
          
       

       
         
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
        new String[] {"Nama Cust","No Transaksi","No SQ","Nama Barang","Status Barang","Gudang","Tim Gudang","Nilai Ketepatan","Waktu"}){ 
        boolean[] canEdit=new boolean[]{
        false,false,false
        };
        
        public boolean isCellEdittable(int rowIndex,int coloumnIndex){
            return canEdit[coloumnIndex];
	}
     };
                
   }
       
       
   
       
       
       
        public static void data_tabel(){
        tableSQ.setModel(tabelModel);
        int i=0;
        try{
            results=st.executeQuery("SELECT b.SOCsNm AS nama_cust,b.RefSO AS noTransaksi,a.SOANo AS nosq,d.PdNm AS namaProduk,a.status_customer as status_barang,a.DSOGdg AS gudang,a.status_group_gudang AS team_gudang,a.nilai_gudang AS nilai_ketepatan,CONCAT(a.waktu,\" \",\"(\",TIMESTAMPDIFF(MINUTE,a.createAt,a.waktu),\" Menit)\")AS waktu FROM detsq a \n" +
            "LEFT JOIN trsq b ON a.SOANo=b.SOANo\n" +
            "LEFT JOIN trso c ON b.RefSO=c.SOANo\n" +
            "LEFT JOIN msprod d ON a.PdANo=d.PdANo\n" +
            "WHERE a.pb='PB DEPAN'\n" +
            "GROUP BY a.SOANo DESC");
            String [] kolom={"No","Nama Cust","No Transaksi","No SQ","Nama Barang","Status Barang","Gudang","Tim Gudang","Nilai Ketepatan","Waktu"};
            tabelModel.setDataVector(null, kolom);
              tableSQ.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
              tableSQ.getColumnModel().getColumn(0).setPreferredWidth(50);
              tableSQ.getColumnModel().getColumn(1).setPreferredWidth(150);
              tableSQ.getColumnModel().getColumn(2).setPreferredWidth(150);
              tableSQ.getColumnModel().getColumn(3).setPreferredWidth(150);
              tableSQ.getColumnModel().getColumn(4).setPreferredWidth(150);
              tableSQ.getColumnModel().getColumn(5).setPreferredWidth(150);
              tableSQ.getColumnModel().getColumn(6).setPreferredWidth(100);
              tableSQ.getColumnModel().getColumn(7).setPreferredWidth(100);
              tableSQ.getColumnModel().getColumn(8).setPreferredWidth(100);
              tableSQ.getColumnModel().getColumn(9).setPreferredWidth(190);
            
             while(results.next()){
                 i++;
                 tabelModel.addRow(new Object[] {
                 i,
                 results.getString("nama_cust"),
                 results.getString("noTransaksi"), 
                 results.getString("nosq"),   
                 results.getString("namaProduk"),
                 "<html><b>"+results.getString("status_barang")+"</b></html>",
                 results.getString("gudang"),
                 results.getString("team_gudang"),
                 results.getString("nilai_ketepatan"),
                 results.getString("waktu"),
                 });
             }
              
             
        }catch(Exception e){
                JOptionPane.showMessageDialog(null,"DATA_TABEL"+e);
                System.out.println("cek_value: "+e);
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        labelSQ = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelBarcode = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSQ = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("No SQ     :");

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField1InputMethodTextChanged(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField1PropertyChange(evt);
            }
        });

        jButton1.setText("simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("NO SQ     :");

        labelSQ.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        labelSQ.setText("----------");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Barcode :");

        labelBarcode.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        labelBarcode.setText("----------");

        jCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox1.setText("input");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Barcode :");

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pengambilan Barang" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("PB            :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("STATUS PENGAMBILAN BARANG");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setAutoscrolls(true);

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
        tableSQ.setSelectionBackground(new java.awt.Color(204, 0, 102));
        tableSQ.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tableSQ);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBarcode)
                            .addComponent(labelSQ))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20)
                                .addComponent(jTextField1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addContainerGap(871, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(295, 295, 295)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelSQ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBarcode)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        
        
        if(!jCheckBox1.isSelected()){
            String txSQ=jTextField1.getText().toString();
            String[] split = txSQ.split("_");
       
        
        
        if(split[0].length()==11){
            labelSQ.setText(split[0]);
            labelBarcode.setText(split[1]);
            nosq=split[0];
            barcode=split[1];
            gudang=split[2];
            
            jTextField1.setText("");
            
            updateSQ(nosq);
            data_tabel();
        }
            
        }
        
        
        
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox1.isSelected()){
            nosq=jTextField1.getText();
            barcode=jTextField2.getText();
            labelSQ.setText(nosq);
            labelBarcode.setText(barcode);
            if(nosq.length()==11){
           
            updateSQ(nosq);
                data_tabel();
             }
            
        
        
        }
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1InputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField1InputMethodTextChanged

    private void jTextField1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField1PropertyChange
 
    }//GEN-LAST:event_jTextField1PropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        jTextField2.setText("");
        labelSQ.setText("----------");
        labelBarcode.setText("----------");
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        jTextField2.setVisible(false);
        jLabel3.setVisible(false);
        jButton1.setVisible(false);
        data_tabel();
        
        try {
            getPb();
            getWaktu();
            getTempPb();
            if(!PBTEMP.equals("Pengambilan Barang")){
                
                data_tabel();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ScanSq.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_formWindowOpened

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        Boolean input=jCheckBox1.isSelected();
        
        if(input){
            jTextField2.setVisible(true);
            jLabel3.setVisible(true);
            jButton1.setVisible(true);
            jTextField2.setEditable(true);
        }else{
            jTextField2.setVisible(false);
            jLabel3.setVisible(false);
            jButton1.setVisible(false);
            jTextField2.setEditable(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
          Object selectedItem = jComboBox1.getSelectedItem();
         PBTEMP = selectedItem.toString();
         if(!PBTEMP.equals("Pengambilan Barang")){
             saveTempPB();
         }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    /**
     * @param args the command line arguments
     */
  
    
 
    
        private static void updateSQ(String transaksi ){
             try{
                     String defauts="0000-00-00 00:00:00";
                     st.executeUpdate("UPDATE  detsq  SET   status_customer='siap',nilai_gudang=IF(NOW()<createAt+ INTERVAL 10 MINUTE,'BAIK','KURANG'),waktu=NOW() WHERE pb='PB DEPAN' AND SOANo='"+transaksi+"' AND print_by='"+gudang+"' AND waktu='"+defauts+"';");
                     System.out.println("update berhasil ");
                     labelSQ.setText("----------");
                     labelBarcode.setText("----------");
                 
              } catch(Exception e){
                System.out.println("error: "+e);
                JOptionPane.showMessageDialog(null,"Gagal menyimpan pengaturan"+e,"",JOptionPane.WARNING_MESSAGE);
              }
        }
        
     void getPb() throws SQLException {
       String key="PB";
       String query = "SELECT setchar as pb FROM mssetprog WHERE setchar LIKE '%"+key+"%'";
       st.clearBatch();
       results =st.executeQuery(query);
       try {           
           int i=0;
           while ((!results.isClosed())&&(results.next())) {
              jComboBox1.addItem(results.getString("pb"));
              System.out.println("cek "+results.getString("pb"));
              
           }
       }  catch (SQLException e) { 
            e.printStackTrace();
       }      

    } 
     
       public  void saveTempPB(){
                Properties properties = new Properties();
                properties.setProperty("pb",PBTEMP);
        try {
            properties.store(new FileOutputStream("pb.properties"), "Database Konfigurasi MySQL");
            System.out.println("Pengaturan Berhasil Di simpan");
            
           
            
            
          }catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Gagal menyimpan pengaturan","",JOptionPane.WARNING_MESSAGE);
          }
          
        }
         public  void getTempPb(){
          Properties properties = new Properties();
          String pbTemp="";
        
            try {
            //load file database.properties
                properties.load(new FileInputStream("pb.properties"));
                pbTemp = properties.getProperty("pb");
            JOptionPane.showMessageDialog(null,"Gagal load "+pbTemp);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Gagal load file database.properties");
            
            }
        
         
          
            if(!pbTemp.equals("")){
                jComboBox1.setSelectedItem(pbTemp);
            }
        }
         
         
    
      
   
        static Thread thread = new Thread() {
        @Override
        public void run() {
            try {
               
                while (!thread.isInterrupted()) {
                   try {
                     data_tabel();
                   } catch (NullPointerException e) {
                    System.out.println("Cek Eroor"+e);
                   }
                    Thread.sleep(1800);
                    
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                
            }
        }
    };
      
        
void getWaktu() throws SQLException {
       String query = "SELECT setchar FROM mssetprog WHERE setano='wkt_gdg'";

       try {
           results=st.executeQuery(query);
           while ((!results.isClosed())&&(result.next())) {
             waktuDurasi=results.getString("setchar")+" menit";
            
           }
       }  catch (SQLException e) { 
            e.printStackTrace();
       }      

    } 
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private static javax.swing.JLabel labelBarcode;
    private static javax.swing.JLabel labelSQ;
    private static javax.swing.JTable tableSQ;
    // End of variables declaration//GEN-END:variables
}
