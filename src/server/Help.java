package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class Help extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help frame = new Help();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Help() {
		setTitle("A little about our Discussion...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 285);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(0, 0, 192, 246);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCrewmates = new JLabel("CREWMATEs <3");
		lblCrewmates.setBounds(10, 196, 172, 27);
		lblCrewmates.setHorizontalAlignment(SwingConstants.CENTER);
		lblCrewmates.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		panel.add(lblCrewmates);
		
		JLabel lblImage = new JLabel("New label");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setIcon(new ImageIcon(Help.class.getResource("/image/among-shhh.jpg")));
		lblImage.setBounds(0, 0, 192, 158);
		panel.add(lblImage);
		
		JLabel lblWeAre = new JLabel("We are..");
		lblWeAre.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeAre.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		lblWeAre.setBounds(10, 164, 172, 38);
		panel.add(lblWeAre);
		
		JTextArea txtrIntro = new JTextArea();
		txtrIntro.setEditable(false);
		txtrIntro.setLineWrap(true);
		txtrIntro.setWrapStyleWord(true);
		txtrIntro.setTabSize(4);
		txtrIntro.setRows(3);
		txtrIntro.setText("Huong dan su dung!!");
		txtrIntro.setBounds(215, 11, 185, 171);
		contentPane.add(txtrIntro);
		
		JLabel lblIntroImage = new JLabel("Insert image ");
		lblIntroImage.setBounds(259, 193, 77, 14);
		contentPane.add(lblIntroImage);
	}
}
