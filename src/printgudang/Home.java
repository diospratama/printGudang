/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printgudang;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import koneksi.Koneksi;
import static printgudang.PrintServices.gudang;

/**
 *
 * @author MOBILEDEV
 */
public class Home extends javax.swing.JFrame {
     static Statement st;
     String menu;

    /** Creates new form Home */
    public Home() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButton1.setText("Print Service");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Scan SQ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jMenu1.setText("Settings");

        jMenuItem1.setText("koneksi");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
         server serv= new server();
         serv.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         saveTempGudang("PrintServices");//simpan temporari konfigurasi pada local 
         PrintServices serv= new PrintServices();//memanggil menu menu print gudang
         serv.setVisible(true);
         this.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         saveTempGudang("ScanSq");//simpan temporari konfigurasi pada local 
         ScanSq serv= new ScanSq();//memanggil menu scan untuk pb
         serv.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        getTempGudang();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws SQLException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
//        UIManager.put( "info", new Color(128,128,128) );
//            UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
//            UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
//            UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
//            UIManager.put( "nimbusFocus", new Color(115,164,209) );
//            UIManager.put( "nimbusGreen", new Color(176,179,50) );
//            UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
//            UIManager.put( "nimbusLightBackground", new Color( 96, 96, 96) );//
//            UIManager.put( "nimbusOrange", new Color(191,98,4) );
//            UIManager.put( "nimbusRed", new Color(169,46,34) );
//            UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
//            UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
//            UIManager.put( "text", new Color( 0, 0, 0) );
//            try {
//              for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//              }
//            } catch (ClassNotFoundException e) {
//              e.printStackTrace();
//            } catch (InstantiationException e) {
//              e.printStackTrace();
//            } catch (IllegalAccessException e) {
//              e.printStackTrace();
//            } catch (javax.swing.UnsupportedLookAndFeelException e) {
//              e.printStackTrace();
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
            st=(Statement)Koneksi.koneksi().createStatement();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }
    
    
      public  void saveTempGudang(String menuPilih){
                Properties properties = new Properties();
                properties.setProperty("menu",menuPilih);
        try {
            properties.store(new FileOutputStream("menu.properties"), "Konfigurasi Menu Printer Gudang");
            System.out.println("Pengaturan Berhasil Di simpan");
           
            
            
          }catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Gagal menyimpan pengaturan","",JOptionPane.WARNING_MESSAGE);
          }
          
        }
        
        public  void getTempGudang(){
          Properties properties = new Properties();
        
            try {
            //load file database.properties
                properties.load(new FileInputStream("menu.properties"));
                  menu = properties.getProperty("menu");
            
                    if(menu.equals("PrintServices")){
                         PrintServices serv= new PrintServices();
                         serv.setVisible(true);
                         this.dispose();
                    }else if(menu.equals("ScanSq")){
                         ScanSq serv= new ScanSq();
                         serv.setVisible(true);
                         this.dispose();

                    }
            } catch (IOException ex) {
                System.out.println("Gagal load file menu.properties");
                 
            
            }
        
           
         
        
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables

}
