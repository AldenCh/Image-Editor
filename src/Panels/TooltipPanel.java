package Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class TooltipPanel extends JPanel{
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
		
		this.setBackground(new Color(50, 40, 56));
		this.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Paint Brush", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Calibri", Font.BOLD, 24), Color.white));
		this.setLayout(new BorderLayout());
		
		description.setForeground(Color.white);
		description.setVisible(true);
		description.setHorizontalAlignment(JLabel.CENTER);
		description.setVerticalAlignment(JLabel.TOP);
		
		this.add(description, BorderLayout.CENTER);
	}
	
	public void setTooltip(int index) {
		String[] pair = descriptions.get(index).split("-");
		this.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), pair[0], TitledBorder.LEFT, TitledBorder.CENTER, new Font("Calibri", Font.BOLD, 24), Color.white));
		description.setText("<html><p>"+pair[1]+"</p></html>");
	}
}
