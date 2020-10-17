
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PoromE
 */
public class Client extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    public Client() {
        initComponents();
        dfList = new javax.swing.DefaultListModel<>();
        btnGroup = new javax.swing.ButtonGroup();
        btnGroup.add(r1);
        btnGroup.add(r2);
        list.setModel(dfList);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        listFileBtn = new javax.swing.JButton();
        r1 = new javax.swing.JRadioButton();
        r2 = new javax.swing.JRadioButton();
        downloadBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        locatonFileDownloadBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        size = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 45, 688, 142));

        listFileBtn.setText("List File from server");
        getContentPane().add(listFileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 202, -1, -1));

        r1.setSelected(true);
        r1.setText("Normal Mode");
        r1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1ActionPerformed(evt);
            }
        });
        getContentPane().add(r1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 263, -1, -1));

        r2.setText("Zero copy");
        getContentPane().add(r2, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 286, -1, -1));

        downloadBtn.setText("Download");
        downloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadBtnActionPerformed(evt);
            }
        });
        getContentPane().add(downloadBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 273, -1, -1));

        jLabel1.setText("File name : none select");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        jLabel2.setText("Time");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 402, -1, -1));

        jLabel3.setText(" Time :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(466, 402, -1, -1));

        jLabel4.setText("none");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 402, -1, -1));

        jLabel5.setText("none");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(516, 402, -1, -1));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 263, 10, 89));

        jLabel6.setText("Normal Mode");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 382, -1, -1));

        jLabel7.setText("Zero copy");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 382, -1, -1));

        locatonFileDownloadBtn.setText("Save as");
        locatonFileDownloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locatonFileDownloadBtnActionPerformed(evt);
            }
        });
        getContentPane().add(locatonFileDownloadBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 273, -1, -1));

        jLabel8.setText("File info");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 264, -1, -1));

        size.setText("File size : unkhow");
        getContentPane().add(size, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, -1, -1));

        pack();
    }// </editor-fold>                        

    private void downloadBtnActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void r1ActionPerformed(java.awt.event.ActionEvent evt) {                                   
        // TODO add your handling code here:
    }                                  

    private void locatonFileDownloadBtnActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        // TODO add your handling code here:
    }                                                      

    void set(JFrame f) {
        this.f = f;
    }

    javax.swing.DefaultListModel<String> dfList;
    String fileName;
    javax.swing.ButtonGroup btnGroup;
    int result = 0, percent = 0;
    long fileSize, now;
    JFrame f;

    // Variables declaration - do not modify                     
    public javax.swing.JButton downloadBtn;
    javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JList<String> list;
    public javax.swing.JButton listFileBtn;
    public javax.swing.JButton locatonFileDownloadBtn;
    public javax.swing.JRadioButton r1;
    public javax.swing.JRadioButton r2;
    public javax.swing.JLabel size;
    // End of variables declaration                   
    public static void main(String args[]) {
        Client c = new Client();
        Download d = new Download(c, c.listFileBtn, c.downloadBtn, c.list, c.dfList, c.r1, c.r2, c.jLabel4, c.jLabel5, c.jLabel1, c.locatonFileDownloadBtn, c.size);
        d.run();
    }

    static class Download {

        Socket socket;
        DataInputStream daIn;
        DataOutputStream daOut;

        int now;
        int result = 0;
        JButton listFileBtn, downloadBtn, fileBtn;
        JList<String> list;
        DefaultListModel<String> dfList;
        JRadioButton r1, r2;
        JLabel timeNormal, timeZero, fileNameLabel, size;
        String fileName;
        long fileSize;
        JFrame f;
        JFileChooser j;
        String path = "C:";

        Download(JFrame f, JButton listFileBtn, JButton downloadBtn, JList<String> list,
                DefaultListModel<String> dfList, JRadioButton r1, JRadioButton r2, JLabel timeNormal, JLabel timeZero,
                JLabel fileNameLabel, JButton fileBtn, JLabel size) {
            this.f = f;
            this.listFileBtn = listFileBtn;
            this.downloadBtn = downloadBtn;
            this.list = list;
            this.dfList = dfList;
            this.r1 = r1;
            this.r2 = r2;
            this.timeNormal = timeNormal;
            this.timeZero = timeZero;
            this.fileNameLabel = fileNameLabel;
            this.fileBtn = fileBtn;
            this.size = size;

            listFileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        fileName = "";
                        daOut.writeUTF("list");
                        dfList.clear();
                        int size = daIn.readInt();
                        for (int i = 0; i < size; i++) {
                            dfList.addElement(daIn.readUTF().toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    fileName = list.getSelectedValue();
                    try {
                        daOut.writeUTF("have file");
                        daOut.writeUTF(fileName);
                        daOut.writeUTF("size");
                        fileSize = daIn.readLong();
                        fileNameLabel.setText("File name : " + fileName);
                        size.setText("File size : " + String.format("%.2f", fileSize * 0.00000095367432) + " MB");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            });

            downloadBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //status.setText("----------");
                    int n = 1;
                    if (fileName == null) {
                        JOptionPane.showMessageDialog(f, "Please select file in the list to download",
                                "File name error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        n = JOptionPane.showConfirmDialog(f, "Do you want to download (" + fileName + ") ?", "Notice",
                                JOptionPane.YES_NO_OPTION);
                        // System.out.println(n);
                    }
                    if (n == 0) {
                        try {
                            if (r1.isSelected()) {
                                daOut.writeUTF("download normal");
                                result = getfile_copy(socket, path, fileName);
                            } else if (r2.isSelected()) {
                                daOut.writeUTF("download zero");
                                SocketChannel socketChannel = SocketChannel.open();
                                socketChannel.configureBlocking(true);
                                SocketAddress sockAddr = new InetSocketAddress("192.168.8.80", 2222);
                                socketChannel.connect(sockAddr);
                                result = getFileZeroCopy(socketChannel, path, fileName);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                }
            });

            fileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    j = new JFileChooser("c:");
                    j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    j.setDialogTitle("Browse to save location");
                    j.showSaveDialog(null);
                    path = j.getSelectedFile().getAbsolutePath();
                    System.out.println(path);
                }
            });
        }

        public int getfile_copy(Socket socket, String path, String t) {
            try {
                long start = System.currentTimeMillis();
                InputStream in = socket.getInputStream();
                String locate = (path + "\\" + t);
                OutputStream fos = new FileOutputStream(locate);
                byte[] data = new byte[1024];
                int count;
                now = 0;
                System.out.println("Getting file...");
                while ((count = in.read(data)) >= 0) {
                    now += 1024;
                    fos.write(data, 0, count);

                }

                System.out.println("Receving file successfully!");
                in.close();
                fos.close();
                socket.close();
                long stop = System.currentTimeMillis();
                long time = (stop - start);
                timeNormal.setText(String.valueOf(time) + " milisec");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return 1;

        }

        public int getFileZeroCopy(SocketChannel socket, String path, String name) {
            long start = System.currentTimeMillis();
            Path location = Paths.get(path + "\\" + name);
            FileChannel destination = null;
            try {
                FileChannel newch = FileChannel.open(location, EnumSet.of(StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE));
                ByteBuffer buff = ByteBuffer.allocate(WIDTH).allocate(1024);
                while (socket.read(buff) > 0) {
                    buff.flip();
                    newch.write(buff);
                    buff.clear();
                }
                newch.close();
            } catch (Exception er) {
                System.out.print(er);
            }
            long stop = System.currentTimeMillis();
            long time = (stop - start);
            timeZero.setText(String.valueOf(time) + " milisec");
            return 1;
        }

        public void run() {
            while (true) {

                if (socket == null || socket.isClosed()) {
                    try {
                        socket = new Socket("192.168.8.80", 12345);
                        daIn = new DataInputStream(socket.getInputStream());
                        daOut = new DataOutputStream(socket.getOutputStream());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }

            }
        }
    }
}
