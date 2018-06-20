
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Dimension;

/**
 *
 * @author DharshikaSingarathnam
 */
public class MonitorGUI extends javax.swing.JFrame {

    private MonitorController monitor;
    /**
     * Creates new form monitorGUI
     */
    public MonitorGUI(MonitorController monitor) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.monitor = monitor;  
    }
    
    /**
     * Adds a new monitor
     * @param test 
     */
    public void addMonitor(SensorMonitorGUI test){
        jPanel1.add(test);
        test.setVisible(true);
    }
    
    /**
     * Sets monitor count
     * @param monitorCount 
     */
    public void setMonitorCount(int monitorCount){
        this.monitorCount.setText(Integer.toString(monitorCount));
    }
    
    /**
     * Set sensor count
     * @param sensorCount 
     */
    public void setSensorCount(int sensorCount){
        this.sensorCount.setText(Integer.toString(sensorCount));
    }
    
    /**
     * Fill combo box with the current locations where sensors are available
     * @param location 
     */
    public void fillLocations(String id){
        this.id.addItem(id);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monitorCount = new javax.swing.JLabel();
        sensorCount = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        jButton2 = new javax.swing.JButton();
        jButton2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        id = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
       

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1060, 600));
        setMinimumSize(new java.awt.Dimension(1060, 600));
        setPreferredSize(new java.awt.Dimension(1060, 600));
        setSize(new Dimension(1003, 427));
        getContentPane().setLayout(null);

        jPanel1.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        jScrollPane1.setRowHeaderView(jPanel1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 143, 1001, 321);

        jLabel1.setFont(new Font("Arial", Font.PLAIN, 18)); // NOI18N
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setText("Monitors Connected ");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(745, 60, 170, 21);

        jLabel2.setFont(new Font("Arial", Font.PLAIN, 18)); // NOI18N
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setText("Sensors Connected");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(745, 88, 190, 21);

        monitorCount.setFont(new Font("Arial", Font.PLAIN, 18)); // NOI18N
        monitorCount.setForeground(SystemColor.window);
        monitorCount.setText("0");
        getContentPane().add(monitorCount);
        monitorCount.setBounds(924, 60, 30, 21);

        sensorCount.setFont(new Font("Arial", Font.PLAIN, 18)); // NOI18N
        sensorCount.setForeground(SystemColor.window);
        sensorCount.setText("0");
        getContentPane().add(sensorCount);
        sensorCount.setBounds(924, 88, 20, 21);

        jButton1.setText("Update Readings");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(822, 490, 164, 29);

        jButton2.setText("Get Reading");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(648, 490, 164, 29);

        getContentPane().add(id);
        id.setBounds(200, 87, 190, 27);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel3.setForeground(SystemColor.window);
        jLabel3.setText("Select Sensor ID");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(36, 87, 130, 21);
        
        label = new JLabel("");
        label.setIcon(new ImageIcon("C:\\Users\\Dell\\Desktop\\it16001244\\Assignment1\\src\\red-wallpaper-29.jpg"));
        label.setBounds(0, 0, 1053, 762);
        getContentPane().add(label);

        

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        monitor.updateAllReadings();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String location = id.getSelectedItem().toString();
        monitor.getLatestReadingsByid(location);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> id;
    private javax.swing.JLabel monitorCount;
    private javax.swing.JLabel sensorCount;
    private JLabel label;
    // End of variables declaration
}