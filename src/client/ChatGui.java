package client;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Label;
import java.awt.SystemColor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import tags.Decode;
import tags.Encode;
import tags.Tags;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import data.DataFile;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatGui {
	private static String URL_DIR = System.getProperty("user.dir");
	private static String TEMP = "/temp/";
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

	public ChatGui(String user,String guest, Socket socket, int port, int a) throws Exception {
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
//		frame.getContentPane().setBackground(new Color(255, 248, 220));
		frame.getContentPane().setBackground(new Color(255, 248, 220));
		textPane.setBackground(Color.WHITE);
		text.setBackground(new Color(255, 222, 173));
		btn2.setBackground(new Color(255, 222, 173));
		btn1.setBackground(new Color(255, 222, 173));
		panel.setBackground(new Color(255, 222, 173));
		mess.setBackground(new Color(255, 255, 255));	
		
	}

	public void setDarkGui(JFrame frame, JTextPane textPane, JTextField text, JButton btn1, JButton btn2, JPanel panel, JTextField mess) {
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		textPane.setBackground(Color.GRAY);
		text.setBackground(Color.LIGHT_GRAY);
		btn2.setBackground(SystemColor.controlHighlight);
		btn1.setBackground(SystemColor.controlHighlight);
		panel.setBackground(Color.BLACK);
		mess.setBackground(Color.LIGHT_GRAY);
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
				int result = Tags.show(frameChatGui, "Are you sure to close chat with account with your crewmate? " , true);
				if (result == 0) {
					try {
						isStop = true;
						frameChatGui.dispose();
						chat.sendMessage(Tags.CHAT_CLOSE_TAG);
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
		scrollPane.setBounds(6, 59, 646, 373);
		frameChatGui.getContentPane().add(scrollPane);

		// Message + Path + Emotions
		panelMessage = new JPanel();

		panelMessage.setBounds(6, 447, 646, 117);
		panelMessage.setBorder(null);
		frameChatGui.getContentPane().add(panelMessage);
		panelMessage.setLayout(null);

		// enter mess here
		txtMessage = new JTextField("");
		txtMessage.setToolTipText("");
		txtMessage.setBounds(10, 11, 468, 39);
		panelMessage.add(txtMessage);
		txtMessage.setColumns(10);

		// Send btn
		btnSend = new JButton("");
		btnSend.setBounds(576, 11, 44, 39);
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
		panelEmoji.setBounds(253, 54, 272, 46);
		panelEmoji.setVisible(false);
		panelMessage.add(panelEmoji);
		panelMessage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelEmoji.setVisible(false);
			}
		});
						
								btnHeartEyeIcon = new JButton("");
								btnHeartEyeIcon.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										panelEmoji.setVisible(false);
									}
								});
								panelEmoji.add(btnHeartEyeIcon);
								btnHeartEyeIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
								btnHeartEyeIcon.setContentAreaFilled(false);
								btnHeartEyeIcon.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_028-unicorn.png") + "'></img>";
										try {
											chat.sendMessage(Encode.sendMessage(msg));
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										updateChat_send_Symbol(msg);
									}
								});
								btnHeartEyeIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/028-unicorn.png")));
								
										btnSmileCryingIcon = new JButton("");
										btnSmileCryingIcon.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseClicked(MouseEvent e) {
												panelEmoji.setVisible(false);
											}
										});
										panelEmoji.add(btnSmileCryingIcon);
										btnSmileCryingIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
										btnSmileCryingIcon.setContentAreaFilled(false);
										btnSmileCryingIcon.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_023-unicorn.png") + "'></img>";
												try {
													chat.sendMessage(Encode.sendMessage(msg));
												} catch (Exception e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												updateChat_send_Symbol(msg);
											}
										});
										btnSmileCryingIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/023-unicorn.png")));
																		btnCryingIcon = new JButton("");
																		btnCryingIcon.addMouseListener(new MouseAdapter() {
																			@Override
																			public void mouseClicked(MouseEvent e) {
																				panelEmoji.setVisible(false);
																			}
																		});
																		panelEmoji.add(btnCryingIcon);
																		btnCryingIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
																		btnCryingIcon.setContentAreaFilled(false);
																		btnCryingIcon.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent arg0) {
																				String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_005-unicorn.png") + "'></img>";
																				try {
																					chat.sendMessage(Encode.sendMessage(msg));
																				} catch (Exception e1) {
																					// TODO Auto-generated catch block
																					e1.printStackTrace();
																				}
																				updateChat_send_Symbol(msg);
																			}
																		});
																		btnCryingIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/005-unicorn.png")));
																
																		buttonSadIcon = new JButton("");
																		buttonSadIcon.addMouseListener(new MouseAdapter() {
																			@Override
																			public void mouseClicked(MouseEvent e) {
																				panelEmoji.setVisible(false);	}
																		});
																		panelEmoji.add(buttonSadIcon);
																		buttonSadIcon.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_014-unicorn.png") + "'></img>";
																				try {
																					chat.sendMessage(Encode.sendMessage(msg));
																				} catch (Exception e1) {
																					// TODO Auto-generated catch block
																					e1.printStackTrace();
																				}
																				updateChat_send_Symbol(msg);
																			}
																		});
																		buttonSadIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/014-unicorn.png")));
																		buttonSadIcon.setContentAreaFilled(false);
																		buttonSadIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
														
														//Emotions --> Huong
																JButton btnSmileIcon = new JButton("");
																btnSmileIcon.addMouseListener(new MouseAdapter() {
																	@Override
																	public void mouseClicked(MouseEvent e) {
																		panelEmoji.setVisible(false);
																	}
																});
																panelEmoji.add(btnSmileIcon);
																btnSmileIcon.addActionListener(new ActionListener() {
																	public void actionPerformed(ActionEvent arg0) {
																		String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_035-unicorn.png") + "'></img>";
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
																btnSmileIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/035-unicorn.png")));
												
														buttonScaredIcon = new JButton("");
														buttonScaredIcon.addMouseListener(new MouseAdapter() {
															@Override
															public void mouseClicked(MouseEvent e) {
																panelEmoji.setVisible(false);
															}
														});
														panelEmoji.add(buttonScaredIcon);
														buttonScaredIcon.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent e) {
																String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_012-unicorn.png") + "'></img>";
																try {
																	chat.sendMessage(Encode.sendMessage(msg));
																} catch (Exception e1) {
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																}
																updateChat_send_Symbol(msg);
															}
														});
														buttonScaredIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/012-unicorn.png")));
														buttonScaredIcon.setContentAreaFilled(false);
														buttonScaredIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
										
												btnSmileBigIcon = new JButton("");
												btnSmileBigIcon.addMouseListener(new MouseAdapter() {
													@Override
													public void mouseClicked(MouseEvent e) {
														panelEmoji.setVisible(false);
													}
												});
												panelEmoji.add(btnSmileBigIcon);
												btnSmileBigIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
												btnSmileBigIcon.setContentAreaFilled(false);
												btnSmileBigIcon.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														String msg = "<img src='" + ChatGui.class.getResource("/image/icons/emoji_016-unicorn.png") + "'></img>";
														try {
															chat.sendMessage(Encode.sendMessage(msg));
														} catch (Exception e1) {
															// TODO Auto-generated catch block
															e1.printStackTrace();
														}
														updateChat_send_Symbol(msg);
													}
												});
												btnSmileBigIcon.setIcon(new ImageIcon(ChatGui.class.getResource("/image/icons/016-unicorn.png")));
		

		JButton btnSendLike = new JButton("");
		btnSendLike.setBounds(528, 11, 52, 39);
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
		
		setDarkGui(frameChatGui, txtDisplayChat, textName, btnChangeTheme, btnDisConnect, panelMessage, txtMessage);

		JButton btnEmoji = new JButton("");
		btnEmoji.setForeground(Color.WHITE);
		btnEmoji.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			panelEmoji.setVisible(true);
			}
		});
		btnEmoji.setIcon(new ImageIcon(ChatGui.class.getResource("/image/more.png")));
		btnEmoji.setContentAreaFilled(false);
		btnEmoji.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnEmoji.setBackground(UIManager.getColor("TabbedPane.selectedTabTitlePressedColor"));
		btnEmoji.setBounds(447, 6, 117, 52);
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
		private ObjectInputStream inPeer;
		private boolean continueSendFile = true, finishReceive = false;
		private int sizeOfSend = 0, sizeOfData = 0, sizeFile = 0,
				sizeReceive = 0;
		private String nameFileReceive = "";
		private InputStream inFileSend;
		private DataFile dataFile;

		public ChatRoom(Socket connection, String name, String guest)
				throws Exception {
			connect = new Socket();
			connect = connection;
			nameGuest = guest;
		}

		@Override
		public void run() {
			super.run();
			OutputStream out = null;
			while (!isStop) {
				try {
					inPeer = new ObjectInputStream(connect.getInputStream());
					Object obj = inPeer.readObject();
					if (obj instanceof String) {
						String msgObj = obj.toString();
						if (msgObj.equals(Tags.CHAT_CLOSE_TAG)) {
							isStop = true;
							Tags.show(frameChatGui,
									"The discussion was cancelled! This windows will also be closed.", false);
							try {	
								isStop = true;
								frameChatGui.dispose();
								chat.sendMessage(Tags.CHAT_CLOSE_TAG);
								chat.stopChat();
								System.gc();
							} catch (Exception e) {
//								e.printStackTrace();
								System.out.println("");
							}
							connect.close();
							break;
						}
						if (Decode.checkFile(msgObj)) {
							isReceiveFile = true;
							nameFileReceive = msgObj.substring(10,
									msgObj.length() - 11);
							int result = Tags.show(frameChatGui, nameGuest
									+ " send file " + nameFileReceive
									+ " for you", true);
							if (result == 0) {
								File fileReceive = new File(URL_DIR + TEMP
										+ "/" + nameFileReceive);
								if (!fileReceive.exists()) {
									fileReceive.createNewFile();
								}
								String msg = Tags.FILE_REQ_ACK_OPEN_TAG
										+ Integer.toBinaryString(portServer)
										+ Tags.FILE_REQ_ACK_CLOSE_TAG;
								sendMessage(msg);
							} else {
								sendMessage(Tags.FILE_REQ_NOACK_TAG);
							}
					
							new Thread(new Runnable() {
								public void run() {
									try {
										sendMessage(Tags.FILE_DATA_BEGIN_TAG);
										updateChat_notify("You are sending file: " + nameFile);
										isSendFile = false;
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).start();
						} else if (msgObj.equals(Tags.FILE_REQ_NOACK_TAG)) {
							Tags.show(frameChatGui, nameGuest
									+ " don't want receive file", false);
						} else if (msgObj.equals(Tags.FILE_DATA_BEGIN_TAG)) {
							finishReceive = false;
							lblReceive.setVisible(true);
							out = new FileOutputStream(URL_DIR + TEMP
									+ nameFileReceive);
						} else if (msgObj.equals(Tags.FILE_DATA_CLOSE_TAG)) {
							updateChat_receive("You receive file: " + nameFileReceive + " with size " + sizeReceive + " KB");
							sizeReceive = 0;
							out.flush();
							out.close();
							lblReceive.setVisible(false);
							new Thread(new Runnable() {

								@Override
								public void run() {
									showSaveFile();
								}
							}).start();
							finishReceive = true;
//						} else if (msgObj.equals(Tags.FILE_DATA_CLOSE_TAG) && isFileLarge == true) {
//							updateChat_receive("File " + nameFileReceive + " too large to receive");
//							sizeReceive = 0;
//							out.flush();
//							out.close();
//							lblReceive.setVisible(false);
//							finishReceive = true;
						} else {
							String message = Decode.getMessage(msgObj);
							updateChat_receive(message);
						}
					} else if (obj instanceof DataFile) {
						DataFile data = (DataFile) obj;
						++sizeReceive;
						out.write(data.data);
					}
				} catch (Exception e) {
					File fileTemp = new File(URL_DIR + TEMP + nameFileReceive);
					if (fileTemp.exists() && !finishReceive) {
						fileTemp.delete();
					}
				}
			}
		}

		
		private void getData(String path) throws Exception {
			File fileData = new File(path);
			if (fileData.exists()) {
				sizeOfSend = 0;
				dataFile = new DataFile();
				sizeFile = (int) fileData.length();
				sizeOfData = sizeFile % 1024 == 0 ? (int) (fileData.length() / 1024)
						: (int) (fileData.length() / 1024) + 1;
				inFileSend = new FileInputStream(fileData);
			}
		}

		public void sendFile(String path) throws Exception {
			getData(path);
			textState.setVisible(true);
			if (sizeOfData > Tags.MAX_MSG_SIZE/1024) {
				textState.setText("File is too large...");
				inFileSend.close();
//				isFileLarge = true;
//				sendMessage(Tags.FILE_DATA_CLOSE_TAG);
		
				isSendFile = false;
				inFileSend.close();
				return;
			}
			
			progressSendFile.setVisible(true);
			progressSendFile.setValue(0);
			
			textState.setText("Sending ...");
			do {
				System.out.println("sizeOfSend : " + sizeOfSend);
				if (continueSendFile) {
					continueSendFile = false;
//					updateChat_notify("If duoc thuc thi: " + String.valueOf(continueSendFile));
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								inFileSend.read(dataFile.data);
								sendMessage(dataFile);
								sizeOfSend++;
								if (sizeOfSend == sizeOfData - 1) {
									int size = sizeFile - sizeOfSend * 1024;
									dataFile = new DataFile(size);
								}
								progressSendFile
										.setValue((int) (sizeOfSend * 100 / sizeOfData));
								if (sizeOfSend >= sizeOfData) {
									inFileSend.close();
									isSendFile = true;
									sendMessage(Tags.FILE_DATA_CLOSE_TAG);
									progressSendFile.setVisible(false);
									textState.setVisible(false);
									isSendFile = false;
							
									updateChat_notify("File sent complete");
									inFileSend.close();
								}
								continueSendFile = true;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			} while (sizeOfSend < sizeOfData);
		}

		private void showSaveFile() {
			while (true) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog(frameChatGui);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile()
							.getAbsolutePath() + "/" + nameFileReceive );
					if (!file.exists()) {
						try {
							file.createNewFile();
							Thread.sleep(1000);
							InputStream input = new FileInputStream(URL_DIR
									+ TEMP + nameFileReceive);
							OutputStream output = new FileOutputStream(
									file.getAbsolutePath());
							copyFileReceive(input, output, URL_DIR + TEMP
									+ nameFileReceive);
						} catch (Exception e) {
							Tags.show(frameChatGui, "Your file receive has error!!!",
									false);
						}
						break;
					} else {
						int resultContinue = Tags.show(frameChatGui,
								"File is exists. You want save file?", true);
						if (resultContinue == 0)
							continue;
						else
							break;
					}
				}
			}
		}
		
		
		//void send Message
		public synchronized void sendMessage(Object obj) throws Exception {
			outPeer = new ObjectOutputStream(connect.getOutputStream());
			// only send text
			if (obj instanceof String) {
				String message = obj.toString();
				outPeer.writeObject(message);
				outPeer.flush();
				if (isReceiveFile)
					isReceiveFile = false;
			} 
			// send attach file
			else if (obj instanceof DataFile) {
				outPeer.writeObject(obj);
				outPeer.flush();
			}
		}

		public void stopChat() {
			try {
				connect.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copyFileReceive(InputStream inputStr, OutputStream outputStr,
			String path) throws IOException {
		byte[] buffer = new byte[1024];
		int lenght;
		while ((lenght = inputStr.read(buffer)) > 0) {
			outputStr.write(buffer, 0, lenght);
		}
		inputStr.close();
		outputStr.close();
		File fileTemp = new File(path);
		if (fileTemp.exists()) {
			fileTemp.delete();
		}
	}
	
	// send html to pane
	  private void appendToPane(JTextPane tp, String msg){
	    HTMLDocument doc = (HTMLDocument)tp.getDocument();
	    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
	    try {
	    	
	      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
	      tp.setCaretPosition(doc.getLength());
	      
	    } catch(Exception e){
	      e.printStackTrace();
	    }
	  }
}
