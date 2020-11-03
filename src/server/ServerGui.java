package server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ServerGui {

	public static int port = 8080;
	private JFrame frmServerMangement;
	private JTextField txtIP;
	private JLabel lblStatus;
	private static TextArea txtMessage;
	public static JLabel lblUserOnline;
	ServerCore server;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui window = new ServerGui();
					window.frmServerMangement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerGui() {
		initialize();
	}
	
	public static String getLabelUserOnline() {
		return lblUserOnline.getText();
	}
	public static void updateMessage(String msg) {
		txtMessage.append(msg + "\n");
	}

	public static void updateNumberClient() {
		int number = Integer.parseInt(lblUserOnline.getText());
		lblUserOnline.setText(Integer.toString(number + 1));
	}
	
	public static void decreaseNumberClient() {
		int number = Integer.parseInt(lblUserOnline.getText());
		lblUserOnline.setText(Integer.toString(number - 1));

	}

	private void initialize() {
		frmServerMangement = new JFrame();
		frmServerMangement.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
		frmServerMangement.setForeground(UIManager.getColor("RadioButtonMenuItem.foreground"));
		frmServerMangement.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 13));
		frmServerMangement.getContentPane().setForeground(UIManager.getColor("RadioButtonMenuItem.acceleratorSelectionForeground"));
		frmServerMangement.setTitle("Crewmate Server Mangement");
		frmServerMangement.setResizable(false);
		frmServerMangement.setBounds(200, 200, 730, 686);
		frmServerMangement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerMangement.getContentPane().setLayout(null);
		frmServerMangement.setBackground(Color.ORANGE);

		JLabel lblIP = new JLabel("IP ADDRESS");
		lblIP.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lblIP.setBounds(26, 120, 89, 16);					////// Vi tri lbl IP
		frmServerMangement.getContentPane().add(lblIP);

		txtIP = new JTextField();
		txtIP.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtIP.setEditable(false);
		txtIP.setBounds(127, 114, 176, 28);				////// Vi tri text Ip
		frmServerMangement.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		JButton btnStart = new JButton("");
		btnStart.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		btnStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		btnStart.setBounds(36, 148, 89, 43);			/////// Vi tri button START
		frmServerMangement.getContentPane().add(btnStart);
		btnStart.setIcon(new ImageIcon(ServerGui.class.getResource("/image/start.png")));
		
//		BufferedImage img = null;
//		try {
//		    img = ImageIO.read(new File(ServerGui.class.getResource("/image/server.png").getFile()));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//		Image dimg = img.getScaledInstance(64, 64,
//		        Image.SCALE_SMOOTH);
//		ImageIcon imageIcon = new ImageIcon(dimg);

		
		JLabel lblNhom = new JLabel("Crewmate Server");
	
		lblNhom.setFont(new Font("OCR A Extended", Font.PLAIN, 24));
		lblNhom.setBounds(219, 10, 422, 92);
		lblNhom.setIcon(new ImageIcon(ServerGui.class.getResource("/image/among-us.png")));
		frmServerMangement.getContentPane().add(lblNhom);

		txtMessage = new TextArea();					
		txtMessage.setBackground(Color.BLACK);
		txtMessage.setForeground(Color.GREEN);
		txtMessage.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtMessage.setEditable(false);
		txtMessage.setBounds(26, 271, 681, 354);		////// Vi tri textArea
		frmServerMangement.getContentPane().add(txtMessage);

		JButton btnStop = new JButton("");
		btnStop.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblUserOnline.setText("0");
				try {
					server.stopserver();
					ServerGui.updateMessage("STOP SERVER");
					lblStatus.setText("<html><font color='red'>OFF</font></html>");
				} catch (Exception e) {
					e.printStackTrace();
					ServerGui.updateMessage("STOP SERVER");
					lblStatus.setText("<html><font color='red'>OFF</font></html>");
				}
			}
		});
		btnStop.setBounds(176, 148, 89, 43);						//// Vi tri button Stop
		frmServerMangement.getContentPane().add(btnStop);
		btnStop.setIcon(new ImageIcon(ServerGui.class.getResource("/image/off.png")));
		
		JLabel lblnew111 = new JLabel("STATUS");
		lblnew111.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lblnew111.setBounds(416, 120, 89, 16);
		frmServerMangement.getContentPane().add(lblnew111);
		
		lblStatus = new JLabel("New label");
		lblStatus.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lblStatus.setBounds(516, 120, 98, 16);


		frmServerMangement.getContentPane().add(lblStatus);
		lblStatus.setText("<html><font color='red'>OFF</font></html>");
		
		JLabel lbllabelUserOnline = new JLabel("ONLINE");
		lbllabelUserOnline.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lbllabelUserOnline.setBounds(416, 164, 89, 16);
		frmServerMangement.getContentPane().add(lbllabelUserOnline);
		
		lblUserOnline = new JLabel("0");
		lblUserOnline.setForeground(Color.BLUE);
		lblUserOnline.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblUserOnline.setBounds(516, 164, 56, 16);
		frmServerMangement.getContentPane().add(lblUserOnline);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ServerGui.class.getResource("/image/kisspng-grizzly-bear-desktop-wallpaper-we-bare-bears-sea-bears-5ac5e745ca62c9.727267041522919237829.png")));
		lblNewLabel.setBounds(325, 157, 304, 163);
		frmServerMangement.getContentPane().add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		frmServerMangement.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		mnNewMenu.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmTeam = new JMenuItem("Team");
		mntmTeam.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		mnNewMenu.add(mntmTeam);
		mntmTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			new TeamGui().setVisible(true);
				}
			});
		
		JMenuItem mntmSoftware = new JMenuItem("Software");
		mntmSoftware.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		mnNewMenu.add(mntmSoftware);
		mntmSoftware.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			new SoftwareGui().setVisible(true);
				}
			});
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		mnNewMenu.add(mntmHelp);
		mntmHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			new HelpGui().setVisible(true);
				}
			});
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server = new ServerCore(8080);
					ServerGui.updateMessage("START SERVER");
					lblStatus.setText("<html><font color='green'>RUNNING...</font></html>");
				} catch (Exception e) {
					ServerGui.updateMessage("START ERROR");
					e.printStackTrace();
				}
			}
		});
	}
}

