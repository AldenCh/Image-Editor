package Panels;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TooltipPanel extends JPanel{
	JLabel header = new JLabel("Paint Brush");
	JLabel description = new JLabel("<html><p>Hold down left click on the canvas to draw pixels</p></html>");
	ArrayList<String> descriptions = new ArrayList<String>();
	
	public TooltipPanel() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\resources\\descriptions.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				descriptions.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setLayout(null);
		this.setBackground(new Color(50, 40, 56));
		
		header.setBounds(0, 0, 320, 50);
		header.setBorder(new LineBorder(Color.white, 1));
		header.setForeground(Color.white);
		header.setVisible(true);
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setVerticalAlignment(JLabel.CENTER);
		header.setFont(new Font("Calibri", Font.BOLD,  24));
		
		description.setBounds(5, 50, 320, 235);
		description.setForeground(Color.white);
		description.setVisible(true);
		description.setHorizontalAlignment(JLabel.CENTER);
		description.setVerticalAlignment(JLabel.TOP);
		
		this.add(header);
		this.add(description);
	}
	
	public void setTooltip(int index) {
		String[] pair = descriptions.get(index).split("-");
		header.setText(pair[0]);
		description.setText("<html><p>"+pair[1]+"</p></html>");
	}
}
