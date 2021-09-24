package Frames;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveAsFrame extends JFrame implements ActionListener{
	ImageIcon logo = new ImageIcon("resources/images/orange.jpg");
	JButton submit = new JButton("Save");
	JLabel prompt = new JLabel("<html><style>p{text-align: center;}</style><p>What do you want to name this image?<br>(Do not include \".jpg\")</p></html>");
	JTextField textField = new JTextField();
	JPanel textPanel = new JPanel();
	EditorFrame parent;
	
	public SaveAsFrame(EditorFrame parent) {
		this.setTitle("Save Image");
		this.setIconImage(logo.getImage());
		this.setLayout(new GridLayout(3,1,10,10));
		this.parent = parent;
		
		prompt.setHorizontalAlignment(JLabel.CENTER);
		
		textField.setPreferredSize(new Dimension(300, 25));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setOpaque(true);
		textField.setVisible(true);
		textField.addActionListener(this);
		textPanel.add(textField);
		
		
		submit.addActionListener(this);
		submit.setFocusable(false);
		
		this.add(prompt);
		this.add(textPanel);
		this.add(submit);
		
		this.setSize(400,200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				close();
			}
		});
	}
	
	void close() {
		this.setVisible(false);
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			this.setVisible(false);
			parent.saveAsImage(textField.getText()+".jpg");
			this.dispose();
		}
		else if (e.getSource() == textField) {
			this.setVisible(false);
			parent.saveAsImage(textField.getText()+".jpg");
			this.dispose();
		}
	}
	
}
