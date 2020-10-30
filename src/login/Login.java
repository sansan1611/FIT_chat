package login;

import java.awt.EventQueue;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import client.MainGui;
import tags.Encode;
import tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.Font;
import javax.swing.UIManager;
//import javax.swing.JList;
//import javax.swing.ImageIcon;
import java.awt.Color;

public class Login {
 private static String NAME_FAILED = "THIS NAME CONTAINS INVALID CHARACTER. PLEASE TRY AGAIN";
 private static String NAME_EXSIST = "THIS NAME IS ALREADY USED. PLEASE TRY AGAIN";
 private static String SERVER_NOT_START = "TURN ON SERVER BEFORE START";

 private Pattern checkName = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");

 private JFrame frameLoginForm;
 private JLabel lblError;
 private String name = "", IP = "";
 private JTextField txtIP;	
 private JTextField txtUsername;
 private JButton btnLogin;
 private JLabel lblNewLabel_5;

 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     Login window = new Login();
     window.frameLoginForm.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 public Login() {
  initialize();
 }

 private void initialize() {
  frameLoginForm = new JFrame();
  frameLoginForm.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
  frameLoginForm.getContentPane().setBackground(new Color(245, 245, 245));
  frameLoginForm.getContentPane().setFont(new Font("OCR A Extended", Font.PLAIN, 14));
  frameLoginForm.setTitle("Crewmates Gate");
  frameLoginForm.setResizable(false);
  frameLoginForm.setBounds(100, 100, 517, 343);
  frameLoginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frameLoginForm.getContentPane().setLayout(null);

  JLabel lblWelcome = new JLabel("Crewmates Login\r\n");
  lblWelcome.setForeground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
  lblWelcome.setFont(new Font("OCR A Extended", Font.BOLD, 21));
  lblWelcome.setBounds(268, 24, 215, 38);
  frameLoginForm.getContentPane().add(lblWelcome);

  JLabel lblHostServer = new JLabel("IP Server");
  lblHostServer.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
  lblHostServer.setBounds(199, 132, 86, 20);
  frameLoginForm.getContentPane().add(lblHostServer);

  JLabel lblUserName = new JLabel("Name");
  lblUserName.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
  lblUserName.setBounds(235, 69, 60, 38);
  frameLoginForm.getContentPane().add(lblUserName);
  lblUserName.setIcon(new javax.swing.ImageIcon(Login.class.getResource("")));

  lblError = new JLabel("");
  lblError.setBounds(66, 287, 399, 20);
  frameLoginForm.getContentPane().add(lblError);

  txtIP = new JTextField();
  txtIP.setBounds(286, 129, 201, 28);
  frameLoginForm.getContentPane().add(txtIP);
  txtIP.setColumns(10);

  txtUsername = new JTextField();
  txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtUsername.setColumns(10);
  txtUsername.setBounds(286, 73, 201, 30);
  frameLoginForm.getContentPane().add(txtUsername);

  btnLogin = new JButton("Get in the ship");
  btnLogin.setForeground(new Color(70, 130, 180));
  btnLogin.setFont(new Font("OCR A Extended", Font.ITALIC, 12));
 // btnLogin.setIcon(new javax.swing.ImageIcon(Login.class.getResource("https://img.icons8.com/clouds/2x/login-rounded-right.png")));
  btnLogin.addActionListener(new ActionListener() {

   public void actionPerformed(ActionEvent arg0) {
    name = txtUsername.getText();
    lblError.setVisible(false);
    IP = txtIP.getText();


    //must edit here
    if (checkName.matcher(name).matches() && !IP.equals("")) {
     try {
      Random rd = new Random();
      int portPeer = 10000 + rd.nextInt() % 1000;
      InetAddress ipServer = InetAddress.getByName(IP);
      int portServer = Integer.parseInt("8080");
      Socket socketClient = new Socket(ipServer, portServer);

      String msg = Encode.getCreateAccount(name, Integer.toString(portPeer));
      ObjectOutputStream serverOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
      serverOutputStream.writeObject(msg);
      serverOutputStream.flush();
      ObjectInputStream serverInputStream = new ObjectInputStream(socketClient.getInputStream());
      msg = (String) serverInputStream.readObject();

      socketClient.close();
      if (msg.equals(Tags.SESSION_DENY_TAG)) {
       lblError.setText(NAME_EXSIST);
       lblError.setVisible(true);
       return;
      }
      new MainGui(IP, portPeer, name, msg);
      //						new menuGUI(IP, portPeer, "toan", msg);
      frameLoginForm.dispose();
     } catch (Exception e) {
      lblError.setText(SERVER_NOT_START);
      lblError.setVisible(true);
      e.printStackTrace();
     }
    }
    else {
     lblError.setText(NAME_FAILED);
     lblError.setVisible(true);
     lblError.setText(NAME_FAILED);
    }
   }
  });
  
  btnLogin.setBounds(297, 185, 179, 48);
  frameLoginForm.getContentPane().add(btnLogin);
  
  JLabel lblNewLabel = new JLabel("New label");
  lblNewLabel.setBounds(288, 82, 46, 14);
  frameLoginForm.getContentPane().add(lblNewLabel);
  
  lblNewLabel_5 = new JLabel("");
  lblNewLabel_5.setBackground(new Color(230, 230, 250));
  //lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\kagam\\OneDrive\\M\u00E1y t\u00EDnh\\st,small,507x507-pad,600x600,f8f8f8 (2).jpg"));
  lblNewLabel_5.setBounds(26, 11, 179, 249);
  frameLoginForm.getContentPane().add(lblNewLabel_5);
  lblError.setVisible(false);


 }
}