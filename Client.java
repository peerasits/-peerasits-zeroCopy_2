import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Client extends JFrame implements Runnable {

    Socket socket;
    DataInputStream daIn;
    DataOutputStream daOut;

    JScrollPane sp;
    JPanel panel;
    JList<String> list;
    JLabel helloLabel, fileNameLabel, status;
    DefaultListModel<String> dfList;
    JButton listFileBtn, downloadBtn;
    String fileName;
    JRadioButton r1, r2;
    ButtonGroup btnGroup;
    JTextArea textArea;
    int result = 0, percent = 0;
    long fileSize, now;

    public Client() {
        panel = new JPanel();
        helloLabel = new JLabel();

        dfList = new DefaultListModel<>();
        list = new JList<>(dfList);
        sp = new JScrollPane(list);
        sp.setPreferredSize(new Dimension(600, 400));

        listFileBtn = new JButton("List File from server");

        fileNameLabel = new JLabel();
        fileNameLabel.setText("None select file");

        r1 = new JRadioButton("Normal Mode", true);
        r2 = new JRadioButton("Zero copy");
        btnGroup = new ButtonGroup();
        btnGroup.add(r1);
        btnGroup.add(r2);
        downloadBtn = new JButton("Download");
        downloadBtn.setBackground(Color.YELLOW);
        status = new JLabel();
        if (result == 0) {
            status.setText("----------");
        }

        panel.add(sp);
        panel.add(listFileBtn);
        panel.add(fileNameLabel);
        panel.add(r1);
        panel.add(r2);
        panel.add(downloadBtn);
        panel.add(status);

        add(panel);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        listFileBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    daOut.writeUTF("list");
                    dfList.clear();
                    int size = daIn.readInt();
                    for (int i = 0; i < size; i++) {
                        dfList.addElement(daIn.readUTF().toString());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });

        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                fileName = list.getSelectedValue();
                fileNameLabel.setText("Select : " + fileName);
                try {
                    daOut.writeUTF("have file");
                    daOut.writeUTF(fileName);
                    daOut.writeUTF("size");
                    fileSize = daIn.readLong();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });

        downloadBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("----------");
                int n = 1;
                if (fileName == null) {
                    JOptionPane.showMessageDialog(panel, "Please select file in the list to download",
                            "File name error", JOptionPane.ERROR_MESSAGE);
                } else {
                    n = JOptionPane.showConfirmDialog(panel, "Do you want to download (" + fileName + ") ?", "Notice",
                            JOptionPane.YES_NO_OPTION);
                    // System.out.println(n);
                }
                if (n == 0) {
                    try {
                        if (r1.isSelected()) {
                            daOut.writeUTF("download normal");
                            result = getfile_copy(socket, fileName);
                        } else if (r2.isSelected()) {
                            daOut.writeUTF("download zero");
                            SocketChannel socketChannel = SocketChannel.open();
                            socketChannel.configureBlocking(true);
                            SocketAddress sockAddr = new InetSocketAddress("192.168.8.80", 2222);
                            socketChannel.connect(sockAddr);
                            result = getFileZeroCopy(socketChannel, fileName);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (result == 1) {
                    status.setText("Completed");
                }

            }
        });
    }
    


    public int getfile_copy(Socket socket, String t) throws IOException {
        long start = System.currentTimeMillis();
        InputStream in = socket.getInputStream();
        String locate = "E:\\" + t;
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
        System.out.println(stop - start);
        return 1;

    }

    public int getFileZeroCopy(SocketChannel socket, String name) {
        long start = System.currentTimeMillis();
        Path location = Paths.get("E:\\" + name);
        FileChannel destination = null;
        try {
        	FileChannel newch = FileChannel.open(location, EnumSet.of(StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE));
			ByteBuffer buff = ByteBuffer.allocate(1024);
			while (socket.read(buff) > 0) {
				buff.flip();
				newch.write(buff);
				buff.clear();
			}
			newch.close();
		} catch (IOException er) {
			System.out.print(er);
		}
        long stop = System.currentTimeMillis();
        System.out.println(stop - start);
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

    public static void main(String[] args) {
        Client c = new Client();
        c.run();
    }

}