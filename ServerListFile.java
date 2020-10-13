import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerListFile extends Thread {
	ServerSocket server;
	Socket s;
	DataInputStream daIn;
	DataOutputStream daOut;
	String fileName;
	File file;

	public ServerListFile(Socket s, DataInputStream DaIn, DataOutputStream DaOut) {
		this.s = s;
		this.daIn = DaIn;
		this.daOut = DaOut;
	}

	public boolean avilible(File locationFile, String path) throws Exception {
		String listFile[] = locationFile.list();
		StringBuilder strShow = new StringBuilder();
		for (String str : listFile) {
			if (str.equals(path)) {
				return true;
			}
		}
		return false;
	}

	public StringBuilder listFile(File locationFile) throws Exception {
		String listFile[] = locationFile.list();
		StringBuilder strShow = new StringBuilder();
		for (String str : listFile) {
			strShow.append(str);
		}

		return strShow;
	}

	class CreateAndStand {
		public void normalCopy(Socket socket, String path) {
			InputStream fIn = null;
			OutputStream fOut = null;
			try {
				byte[] buffer = new byte[1024];
				path = "/home/serverfiletest/Desktop/List/" + path;
				fIn = new FileInputStream(path);
				fOut = socket.getOutputStream();
				int c;
				while ((c = fIn.read(buffer)) > 0) {
					fOut.write(buffer, 0, c);
				}
				System.out.println("File was sent");
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}finally {
				try {
				fIn.close();
				fOut.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		public void zeroCopy(SocketChannel con,String path) {
			path = "/home/serverfiletest/Desktop/List/"+path;
			Path file = Paths.get(path);
			FileChannel source;
			try {
				source = FileChannel.open(file);
				ByteBuffer buff = ByteBuffer.allocate(1024);
				while(source.read(buff)>0) {
					buff.flip();
					con.write(buff);
					buff.clear();
				}
				source.close();
				con.close();
				System.out.println("File was sent");
			}catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	public void run() {
		String msgFromClient;
        try {
            while (true) {
                msgFromClient = daIn.readUTF();
                if (msgFromClient.equals("list")) {
                    file = new File("/home/serverfiletest/Desktop/List/");
                    String[] filesFromList = file.list();
                    daOut.writeInt(filesFromList.length);
                    for (String s : filesFromList) {
                        daOut.writeUTF(s);
                    }
                } else if (msgFromClient.equals("download normal")) {
                    if (fileName != null) {
                    	CreateAndStand c = new CreateAndStand();
                        c.normalCopy(s, fileName);
                    }
                }else if (msgFromClient.equals("download zero")) {
                    if (fileName != null) {
                    	CreateAndStand c = new CreateAndStand();
                    	ServerSocketChannel server;
                		SocketChannel channel;
                		server = ServerSocketChannel.open();
                		server.socket().bind(new InetSocketAddress(2222));
                		channel = server.accept();
                		server.close();
                        c.zeroCopy(channel,fileName);
                    }
                } 
                else if (msgFromClient.equals("have file")) {
                    fileName = daIn.readUTF();
                }
                else if(msgFromClient.equals("size")){
                	file = new File("/home/serverfiletest/Desktop/List/"+fileName);
                	daOut.writeLong(file.length());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

	}

	public static void main(String args[]) throws Exception {
		ServerSocket server = new ServerSocket(12345);
		try {
			while (true) {

				Socket s = server.accept();
				DataInputStream DaIn = new DataInputStream(s.getInputStream());
				DataOutputStream DaOut = new DataOutputStream(s.getOutputStream());

				ServerListFile sv = new ServerListFile(s, DaIn, DaOut);
				sv.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
