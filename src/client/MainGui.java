package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import tags.Tags;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.awt.Color;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class MainGui {

	private Client clientNode;
	private static String IPClient = "", nameUser = "", dataUser = "";
	private static int portClient = 0;
	private JFrame frameMainGui;
	private JTextField txtNameFriend;
	private JButton btnChat, btnExit;
	private JLabel lblLogo;
	private JLabel lblActiveNow;
	private static JList<String> listActive;
	
	static DefaultListModel<String> model = new DefaultListModel<>();
	private JLabel lblUsername;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frameMainGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGui(String arg, int arg1, String name, String msg) throws Exception {
		IPClient = arg;
		portClient = arg1;
		nameUser = name;
		dataUser = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frameMainGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGui() throws Exception {
		initialize();
		clientNode = new Client(IPClient, portClient, nameUser, dataUser);
	}

	public static void updateFriendMainGui(String msg) {
		model.addElement(msg);
	}

	public static void resetList() {
		model.clear();
	} 
	
	private void initialize() {
		frameMainGui = new JFrame();
		frameMainGui.setTitle("Menu Chat");
		frameMainGui.setResizable(false);
		frameMainGui.setBounds(100, 100, 500, 560);
		frameMainGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMainGui.getContentPane().setLayout(null);

		JLabel lblHello = new JLabel("Welcome");
		lblHello.setFont(new Font("OCR A Extended", Font.ITALIC, 14));
		lblHello.setBounds(198, 65, 69, 16);
		frameMainGui.getContentPane().add(lblHello);


		JLabel lblFriendsName = new JLabel("Crewmate name");
		lblFriendsName.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
		lblFriendsName.setBounds(20, 423, 133, 16);
		frameMainGui.getContentPane().add(lblFriendsName);
		
		txtNameFriend = new JTextField("");
		txtNameFriend.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
		txtNameFriend.setColumns(10);
		txtNameFriend.setBounds(142, 418, 327, 28);
		frameMainGui.getContentPane().add(txtNameFriend);

		btnChat = new JButton("Discussion");
		btnChat.setFont(new Font("OCR A Extended", Font.PLAIN, 13));

		btnChat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name = txtNameFriend.getText();
				if (name.equals("") || Client.clientarray == null) {
					Tags.show(frameMainGui, "Invaild username", false);
					return;
				}
				if (name.equals(nameUser)) {
					Tags.show(frameMainGui, "This software doesn't support chat yourself function", false);
					return;
				}
				int size = Client.clientarray.size();
				for (int i = 0; i < size; i++) {
					if (name.equals(Client.clientarray.get(i).getName())) {
						try {
							clientNode.intialNewChat(Client.clientarray.get(i).getHost(),Client.clientarray.get(i).getPort(), name);
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				Tags.show(frameMainGui, "Friend is not found. Please wait to update your list friend", false);
			}
		});
		btnChat.setBounds(25, 461, 158, 44);
		frameMainGui.getContentPane().add(btnChat);
		btnChat.setIcon(new ImageIcon(MainGui.class.getResource("/image/Chat icon.png")));
		btnExit = new JButton("Leave");
		btnExit.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Tags.show(frameMainGui, "Are you sure ?", true);
				if (result == 0) {
					try {
						clientNode.exit();
						frameMainGui.dispose();
					} catch (Exception e) {
						frameMainGui.dispose();
					}
				}
			}
		});
		btnExit.setBounds(323, 461, 146, 44);
		btnExit.setIcon(new ImageIcon(MainGui.class.getResource("/image/icons8-multiply-32.png")));
		frameMainGui.getContentPane().add(btnExit);
		
		lblLogo = new JLabel("CONNECT WITH CREWMATES IN THE SHIP");
		lblLogo.setForeground(new Color(0, 0, 205));
		lblLogo.setIcon(null);
		lblLogo.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		lblLogo.setBounds(90, 16, 374, 79);
		frameMainGui.getContentPane().add(lblLogo);
		
		lblActiveNow = new JLabel("Crewmates showed up:");
		lblActiveNow.setForeground(new Color(100, 149, 237));
		lblActiveNow.setFont(new Font("OCR A Extended", Font.PLAIN, 19));
		lblActiveNow.setBounds(20, 198, 351, 16);
		frameMainGui.getContentPane().add(lblActiveNow);
		
		listActive = new JList<>(model);
		listActive.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
		listActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String value = (String)listActive.getModel().getElementAt(listActive.locationToIndex(arg0.getPoint()));
				txtNameFriend.setText(value);
			}
		});
		listActive.setBounds(20, 215, 449, 197);
		frameMainGui.getContentPane().add(listActive);
		
		lblUsername = new JLabel(nameUser);
		lblUsername.setForeground(Color.RED);
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblUsername.setBounds(259, 65, 146, 16);
		frameMainGui.getContentPane().add(lblUsername);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MainGui.class.getResource("/image/crewmate.png")));
		lblNewLabel.setBounds(20, 92, 449, 99);
		frameMainGui.getContentPane().add(lblNewLabel);
	
			
	}
		

	public static int request(String msg, boolean type) {
		JFrame frameMessage = new JFrame();
		return Tags.show(frameMessage, msg, type);
	}
}
