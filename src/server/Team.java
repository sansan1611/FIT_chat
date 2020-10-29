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

public class Team extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Team frame = new Team();
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
	public Team() {
		setTitle("About our Team!!!");
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
		lblImage.setIcon(new ImageIcon(Team.class.getResource("/image/among-shhh.jpg")));
		lblImage.setBounds(0, 0, 192, 158);
		panel.add(lblImage);
		
		JLabel lblWeAre = new JLabel("We are..");
		lblWeAre.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeAre.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		lblWeAre.setBounds(10, 164, 172, 38);
		panel.add(lblWeAre);
		
		JLabel lblTrngNgcQunh = new JLabel("TRUONG NGOC QUYNH...\r\n\r\n");
		lblTrngNgcQunh.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrngNgcQunh.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lblTrngNgcQunh.setBounds(202, 67, 216, 15);
		contentPane.add(lblTrngNgcQunh);
		
		JLabel lblTranThiMai = new JLabel("TRAN THI MAI HUONG...");
		lblTranThiMai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTranThiMai.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lblTranThiMai.setBounds(202, 136, 216, 15);
		contentPane.add(lblTranThiMai);
		
		JLabel lblLamThiThuong = new JLabel("LAM THI THUONG HUYEN... ");
		lblLamThiThuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblLamThiThuong.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		lblLamThiThuong.setBounds(203, 207, 215, 15);
		contentPane.add(lblLamThiThuong);
		
		JLabel lblQuynhImage = new JLabel("Insert image");
		lblQuynhImage.setBounds(290, 27, 46, 14);
		contentPane.add(lblQuynhImage);
		
		JLabel lblHuongImage = new JLabel("Insert image");
		lblHuongImage.setBounds(290, 111, 46, 14);
		contentPane.add(lblHuongImage);
		
		JLabel lblHuyenImage = new JLabel("Insert image");
		lblHuyenImage.setBounds(290, 171, 46, 14);
		contentPane.add(lblHuyenImage);
	}
}
