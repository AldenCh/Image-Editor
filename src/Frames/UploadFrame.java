package Frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UploadFrame extends JFrame implements ActionListener{
	ImageIcon logo = new ImageIcon("resources/images/orange.jpg");
	JLabel prompt = new JLabel("<html><style>p{text-align: center}</style><p>Make sure your image is in the \"resources/images\" folder before selecting it</p></html>");
	JButton uploadButton = new JButton("Choose an Image");
	FileNameExtensionFilter jpgOnly = new FileNameExtensionFilter("Only .jpg files", "jpg");
	
	public UploadFrame() {
		this.setIconImage(logo.getImage());
		this.setTitle("Image Editor");
		this.setSize(new Dimension(400, 250));
		this.setLayout(null);
		this.getContentPane().setBackground(new Color(50, 40, 56));
		
		prompt.setHorizontalAlignment(JLabel.CENTER);
		prompt.setForeground(Color.white);
		prompt.setBounds(50,35,300,50);
		this.add(prompt);
		
		uploadButton.addActionListener(this);
		uploadButton.setFocusable(false);
		uploadButton.setBounds(100,100, 200, 50);
		this.add(uploadButton);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == uploadButton) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("./resources/images"));
			fc.setAcceptAllFileFilterUsed(false);
			fc.addChoosableFileFilter(jpgOnly);
			int response = fc.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fc.getSelectedFile().getAbsolutePath());
				if (file.getAbsolutePath().contains("Image Editor GUI\\resources\\images")) {
					new EditorFrame(file.getAbsolutePath());
					this.setVisible(false);
					this.dispose();
				}
			}
		}
	}
}
