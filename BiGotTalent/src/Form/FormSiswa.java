package Form;

import koneksi.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;

public class FormSiswa extends javax.swing.JFrame {
    int idLoginSiswa;

    public FormSiswa(int idUser) {
        initComponents();
        setLocationRelativeTo(null);
        this.idLoginSiswa = idUser;
        loadPilihanLomba();
        cekStatusSiswa();
    }

    private FormSiswa() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void loadPilihanLomba() {
        cmbLomba.removeAllItems();
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM lomba");
            while (r.next()) {
                cmbLomba.addItem(r.getString("id") + " - " + r.getString("nama_lomba"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat lomba: " + e.getMessage());
        }
    }

    private void cekStatusSiswa() {
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM pendaftaran WHERE user_id='" + idLoginSiswa + "'");
            if (r.next()) {
                String status = r.getString("status");
                lblStatus.setText("Status Tahapan Anda: " + status);
            } else {
                lblStatus.setText("Status Tahapan Anda: Belum Mendaftarkan Diri");
            }
        } catch (Exception e) {
            lblStatus.setText("Status: Gagal memuat data");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbLomba = new javax.swing.JComboBox<>();
        btnDaftar = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("DASHBOARD SISWA - BI GOT TALENT");

        cmbLomba.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnDaftar.setText("Daftar");
        btnDaftar.addActionListener(this::btnDaftarActionPerformed);

        lblStatus.setText("Status");

        jLabel2.setText("Pilih Mata Lomba:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(140, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStatus)
                            .addComponent(btnDaftar)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel2)
                        .addGap(51, 51, 51)
                        .addComponent(cmbLomba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbLomba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40)
                .addComponent(btnDaftar)
                .addGap(49, 49, 49)
                .addComponent(lblStatus)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
                try {
            String selectedItem = cmbLomba.getSelectedItem().toString();
            String idLomba = selectedItem.split(" - ")[0]; // Ambil ID lomba di depan
            
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            
            // Cek apakah sudah pernah daftar
            ResultSet r = s.executeQuery("SELECT * FROM pendaftaran WHERE user_id='" + idLoginSiswa + "'");
            if (r.next()) {
                JOptionPane.showMessageDialog(this, "Anda sudah terdaftar di lomba!");
                return;
            }
            
            String sql = "INSERT INTO pendaftaran (user_id, lomba_id, status) VALUES ('" 
                    + idLoginSiswa + "', '" + idLomba + "', 'Dokumen dalam Tinjauan')";
            s.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Pendaftaran Berhasil! Menunggu verifikasi admin.");
            cekStatusSiswa();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mendaftar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDaftarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftar;
    private javax.swing.JComboBox<String> cmbLomba;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
