package client;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Label;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import tags.Encode;
import tags.Tags;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatGui {

	private static String URL_DIR = System.getProperty("user.dir");
	private ChatRoom chat;
	private Socket socketChat;
	private String nameUser = "", nameGuest = "", nameFile = "";
	private JFrame frameChatGui;
	private JTextField textName;
	private JPanel panelMessage;
	private JTextPane txtDisplayChat;
	private Label textState, lblReceive;
	private JButton btnDisConnect, btnSend;
	public boolean isStop = false, isSendFile = false, isReceiveFile = false;
	private JProgressBar progressSendFile;
	private int portServer = 0;
	private JTextField txtMessage;
	private JScrollPane scrollPane;
	private JButton btnSmileBigIcon;
	private JButton btnCryingIcon;
	private JButton btnSmileCryingIcon;
	private JButton btnHeartEyeIcon;
	private JButton buttonScaredIcon;
	private JButton buttonSadIcon;
	private JButton btnChangeTheme;

	public ChatGui(String user, String guest, Socket socket, int port) {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui window = new ChatGui(nameUser, nameGuest, socketChat, portServer, 0);
					window.frameChatGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui window = new ChatGui();
					window.frameChatGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateChat_receive(String msg) {
		if (msg.contains("img")) {
			appendToPane(txtDisplayChat, "<div class='left' style='width: 40%; padding: 2px 5px;'>" + msg + "</div>");
		} else {
			appendToPane(txtDisplayChat,
					"<div class='left' style='width: 40%; background-color: #E8E8E8; padding: 2px 5px; margin-top: 2px; border-radius: 10px;'>"
							+ msg + "</div>");
		}
	}

	public void updateChat_send(String msg) {
		appendToPane(txtDisplayChat,
				"<table class='bang' style='color: white; clear:both; width: 100%; padding: 2px 5px; border-radius: 10px;'>"
						+ "<tr align='right'>" + "<td style='width: 59%; '></td>"
						+ "<td style='width: 40%; background-color: #8B8878;'>" + msg + "</td> </tr>" + "</table>");
	}

	public void updateChat_notify(String msg) {
		appendToPane(txtDisplayChat,
				"<table class='bang' style='color: white; clear:both; width: 100%;'>" + "<tr align='right'>"
						+ "<td style='width: 59%; '></td>" + "<td style='width: 40%; background-color: #FFE4B5;'>" + msg
						+ "</td> </tr>" + "</table>");
	}

	public void updateChat_send_Symbol(String msg) {
		appendToPane(txtDisplayChat, "<table style='width: 100%;'>" + "<tr align='right'>"
				+ "<td style='width: 59%;'></td>" + "<td style='width: 40%;'>" + msg + "</td> </tr>" + "</table>");
	}

	public ChatGui() {
		initialize();
	}

	public ChatGui(String user, String guest, Socket socket, int port, int a) throws Exception {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		initialize();
		chat = new ChatRoom(socketChat, nameUser, nameGuest);
		chat.start();
	}

	public boolean isLighted(JTextPane txtDisplay) {
		return txtDisplay.getBackground() == Color.WHITE;
	}
	
	public void setLightGui(JFrame frame, JTextPane textPane, JTextField text, JButton btn1, JButton btn2, JPanel panel, JTextField mess) {
		frame.getContentPane().setBackground(SystemColor.info);
		textPane.setBackground(Color.WHITE);
		text.setBackground(new Color(255, 222, 173));
		btn2.setBackground(new Color(255, 222, 173));
		btn1.setBackground(new Color(255, 222, 173));
		panel.setBackground(new Color(255, 222, 173));
		mess.setBackground(Color.WHITE);	
		
	}

	public void setDarkGui(JFrame frame, JTextPane textPane, JTextField text, JButton btn1, JButton btn2, JPanel panel, JTextField mess) {
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		textPane.setBackground(SystemColor.textInactiveText);
		text.setBackground(SystemColor.controlHighlight);
		btn2.setBackground(SystemColor.controlHighlight);
		btn1.setBackground(SystemColor.controlHighlight);
		panel.setBackground(SystemColor.controlShadow);
		mess.setBackground(SystemColor.controlHighlight);
	}

	private void initialize() {
		File fileTemp = new File(URL_DIR + "/temp");
		if (!fileTemp.exists()) {
			fileTemp.mkdirs();
		}

		// frame
		frameChatGui = new JFrame();
		frameChatGui.setTitle("Hello Crewmate!!!");
		frameChatGui.setResizable(false);
		frameChatGui.setBounds(200, 200, 673, 645);
		frameChatGui.getContentPane().setLayout(null);
		frameChatGui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Label user
		JLabel lblClientIP = new JLabel("");
		lblClientIP.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblClientIP.setBounds(30, 8, 41, 40);
		lblClientIP.setIcon(new ImageIcon(ChatGui.class.getResource("/image/crewmate-avatar.png")));
		frameChatGui.getContentPane().add(lblClientIP);

		textName = new JTextField(nameUser);
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setForeground(new Color(220, 20, 60));
		textName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		textName.setEditable(false);
		textName.setBounds(81, 11, 137, 35);
		frameChatGui.getContentPane().add(textName);
		textName.setText(nameGuest);
		textName.setColumns(10);

		// Leave Chat btn
		btnDisConnect = new JButton("");
		btnDisConnect.setIcon(new ImageIcon(ChatGui.class.getResource("/image/exit.png")));
		btnDisConnect.setBounds(602, 6, 50, 46);
		frameChatGui.getContentPane().add(btnDisConnect);
		btnDisConnect.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnDisConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Tags.show(frameChatGui, "Are you sure to close chat with account: " + nameGuest, true);
				if (result == 0) {
					try {
						isStop = true;
						frameChatGui.dispose();
//						chat.sendMessage(Tags.CHAT_CLOSE_TAG);
						chat.stopChat();
						System.gc();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		textState = new Label("");
		textState.setBounds(6, 570, 158, 22);
		textState.setVisible(false);
		frameChatGui.getContentPane().add(textState);

		lblReceive = new Label("Receiving ...");
		lblReceive.setBounds(564, 577, 83, 14);
		lblReceive.setVisible(false);
		frameChatGui.getContentPane().add(lblReceive);

		// Chat Gui
		txtDisplayChat = new JTextPane();
		txtDisplayChat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtDisplayChat.setEditable(false);
		txtDisplayChat.setContentType("text/html");
		txtDisplayChat.setMargin(new Insets(6, 6, 6, 6));
		txtDisplayChat.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		txtDisplayChat.setBounds(6, 59, 670, 291);
		appendToPane(txtDisplayChat, "<div class='clear' style='background-color:white'></div>");

		frameChatGui.getContentPane().add(txtDisplayChat);

		scrollPane = new JScrollPane(txtDisplayChat);
		scrollPane.setBounds(6, 59, 649, 291);
		frameChatGui.getContentPane().add(scrollPane);

		// Message + Path + Emotions
		panelMessage = new JPanel();

		panelMessage.setBounds(6, 363, 649, 201);
		panelMessage.setBorder(null);
		frameChatGui.getContentPane().add(panelMessage);
		panelMessage.setLayout(null);

		// enter mess here
		txtMessage = new JTextField("");
		txtMessage.setToolTipText("");
		txtMessage.setBounds(10, 11, 479, 39);
		panelMessage.add(txtMessage);
		txtMessage.setColumns(10);

		// Send btn
		btnSend = new JButton("");
		btnSend.setBounds(492, 11, 44, 39);
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnSend.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSend.setContentAreaFilled(false);
		panelMessage.add(btnSend);
		btnSend.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/send.png")));

		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (isSendFile)
					try {
						chat.sendMessage(Encode.sendFile(nameFile));
					} catch (Exception e) {
						e.printStackTrace();
					}

				if (isStop) {
					updateChat_send(txtMessage.getText().toString());
					txtMessage.setText(""); // reset text Send
					return;

				}
				String msg = txtMessage.getText();
				if (msg.equals(""))
					return;
				try {
					chat.sendMessage(Encode.sendMessage(msg));
					updateChat_send(msg);
					txtMessage.setText("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		txtMessage.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String msg = txtMessage.getText();
					if (isStop) {
						updateChat_send(txtMessage.getText().toString());
						txtMessage.setText("");
						return;
					}
					if (msg.equals("")) {
						txtMessage.setText("");
						txtMessage.setCaretPosition(0);
						return;
					}
					try {
						chat.sendMessage(Encode.sendMessage(msg));
						updateChat_send(msg);
						txtMessage.setText("");
						txtMessage.setCaretPosition(0);
					} catch (Exception e) {
						txtMessage.setText("");
						txtMessage.setCaretPosition(0);
					}
				}
			}
		});


		JPanel panelEmoji = new JPanel();
		panelEmoji.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelEmoji.setForeground(Color.PINK);
		panelEmoji.setBounds(300, 51, 320, 51);
		panelEmoji.setVisible(false);
		panelMessage.add(panelEmoji);
		panelMessage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelEmoji.setVisible(false);
			}
		});
								btnCryingIcon = new JButton("");
								panelEmoji.add(btnCryingIcon);
								btnCryingIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
								btnCryingIcon.setContentAreaFilled(false);
								btnCryingIcon.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										String msg = "<img src='" + ChatGui.class.getResource("/image/crying.png") + "'></img>";
										try {
											chat.sendMessage(Encode.sendMessage(msg));
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										updateChat_send_Symbol(msg);
									}
								});
								btnCryingIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/crying.png")));
				
						buttonSadIcon = new JButton("");
						panelEmoji.add(buttonSadIcon);
						buttonSadIcon.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String msg = "<img src='" + ChatGui.class.getResource("/image/sad.png") + "'></img>";
								try {
									chat.sendMessage(Encode.sendMessage(msg));
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								updateChat_send_Symbol(msg);
							}
						});
						buttonSadIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/sad.png")));
						buttonSadIcon.setContentAreaFilled(false);
						buttonSadIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		//Emotions --> Huong
				JButton btnSmileIcon = new JButton("");
				panelEmoji.add(btnSmileIcon);
				btnSmileIcon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String msg = "<img src='" + ChatGui.class.getResource("/image/smile.png") + "'></img>";
						System.out.println("Origin Mess: " + msg);
						System.out.println("Encoded Mess: " + Encode.sendMessage(msg));
						try {
							chat.sendMessage(Encode.sendMessage(msg));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						updateChat_send_Symbol(msg);
					}
				});
				btnSmileIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
				btnSmileIcon.setContentAreaFilled(false);
				btnSmileIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/smile.png")));
				
						buttonScaredIcon = new JButton("");
						panelEmoji.add(buttonScaredIcon);
						buttonScaredIcon.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String msg = "<img src='" + ChatGui.class.getResource("/image/scared.png") + "'></img>";
								try {
									chat.sendMessage(Encode.sendMessage(msg));
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								updateChat_send_Symbol(msg);
							}
						});
						buttonScaredIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/scared.png")));
						buttonScaredIcon.setContentAreaFilled(false);
						buttonScaredIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
						
								btnHeartEyeIcon = new JButton("");
								panelEmoji.add(btnHeartEyeIcon);
								btnHeartEyeIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
								btnHeartEyeIcon.setContentAreaFilled(false);
								btnHeartEyeIcon.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										String msg = "<img src='" + ChatGui.class.getResource("/image/heart_eye.png") + "'></img>";
										try {
											chat.sendMessage(Encode.sendMessage(msg));
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										updateChat_send_Symbol(msg);
									}
								});
								btnHeartEyeIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/heart_eye.png")));
								
										btnSmileCryingIcon = new JButton("");
										panelEmoji.add(btnSmileCryingIcon);
										btnSmileCryingIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
										btnSmileCryingIcon.setContentAreaFilled(false);
										btnSmileCryingIcon.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												String msg = "<img src='" + ChatGui.class.getResource("/image/smile_cry.png") + "'></img>";
												try {
													chat.sendMessage(Encode.sendMessage(msg));
												} catch (Exception e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												updateChat_send_Symbol(msg);
											}
										});
										btnSmileCryingIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/smile_cry.png")));
										
												btnSmileBigIcon = new JButton("");
												panelEmoji.add(btnSmileBigIcon);
												btnSmileBigIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
												btnSmileBigIcon.setContentAreaFilled(false);
												btnSmileBigIcon.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														String msg = "<img src='" + ChatGui.class.getResource("/image/smile_big.png") + "'></img>";
														try {
															chat.sendMessage(Encode.sendMessage(msg));
														} catch (Exception e1) {
															// TODO Auto-generated catch block
															e1.printStackTrace();
														}
														updateChat_send_Symbol(msg);
													}
												});
												btnSmileBigIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/smile_big.png")));
		

		JButton btnSendLike = new JButton("");
		btnSendLike.setBounds(534, 11, 52, 39);
		btnSendLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/like.png") + "'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		btnSendLike.setBackground(new Color(240, 240, 240));
		btnSendLike.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/like.png")));
		// transparent button
		btnSendLike.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSendLike.setContentAreaFilled(false);
		panelMessage.add(btnSendLike);

		
		// Change Theme btn
		btnChangeTheme = new JButton("");
		btnChangeTheme.setIcon(new ImageIcon(ChatGui.class.getResource("/image/change.png")));
		btnChangeTheme.setBackground(SystemColor.controlShadow);
		btnChangeTheme.setBounds(542, 6, 52, 46);
		frameChatGui.getContentPane().add(btnChangeTheme);
		
		setLightGui(frameChatGui, txtDisplayChat, textName, btnChangeTheme, btnDisConnect, panelMessage, txtMessage);

		JButton btnEmoji = new JButton("");
		btnEmoji.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			panelEmoji.setVisible(true);
			}
		});
		btnEmoji.setIcon(new ImageIcon(ChatGui.class.getResource("/image/start.png")));
		btnEmoji.setContentAreaFilled(false);
		btnEmoji.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnEmoji.setBackground(UIManager.getColor("TabbedPane.selectedTabTitlePressedColor"));
		btnEmoji.setBounds(582, 6, 50, 51);
		panelMessage.add(btnEmoji);
		
				progressSendFile = new JProgressBar(0, 100);
				progressSendFile.setForeground(Color.BLACK);
				progressSendFile.setBounds(30, 28, 405, 25);
				panelMessage.add(progressSendFile);
				progressSendFile.setStringPainted(true);
				progressSendFile.setVisible(false);
		
		btnChangeTheme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isLighted(txtDisplayChat)) {
					setDarkGui(frameChatGui, txtDisplayChat, textName, btnChangeTheme, btnDisConnect, panelMessage, txtMessage);
				} else {
					setLightGui(frameChatGui, txtDisplayChat, textName, btnChangeTheme, btnDisConnect, panelMessage, txtMessage);
				}

			}
		});

	}

	public class ChatRoom extends Thread {

		private Socket connect;
		private ObjectOutputStream outPeer;
		public ChatRoom(Socket connection, String name, String guest) throws Exception {
			connect = new Socket();
			connect = connection;
			nameGuest = guest;
		}

		// void send Message
		public synchronized void sendMessage(Object obj) throws Exception {
			outPeer = new ObjectOutputStream(connect.getOutputStream());
			// only send text
			
				String message = obj.toString();
				outPeer.writeObject(message);
				outPeer.flush();
				if (isReceiveFile)
					isReceiveFile = false;
		}

		public void stopChat() {
			try {
				connect.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void appendToPane(JTextPane tp, String msg) {
		HTMLDocument doc = (HTMLDocument) tp.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
		try {

			editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
			tp.setCaretPosition(doc.getLength());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
