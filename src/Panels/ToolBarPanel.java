package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import Frames.EditorFrame;

public class ToolBarPanel extends JPanel implements ActionListener{
	JScrollPane scroll;
	EditorFrame parent;
	JPanel layoutPanel;
	
	public ToolBarPanel(EditorFrame parent) {
		this.setBackground(new Color(50, 40, 56));
		this.parent = parent;
		this.setLayout(null);
		
		layoutPanel = new JPanel();
		layoutPanel.setBackground(new Color(50, 40, 56));
		layoutPanel.setLayout(new GridLayout(9, 5, 3, 3));
		layoutPanel.setVisible(true);
		layoutPanel.setOpaque(true);
		layoutPanel.setBounds(0, 0, 300, 1000);
		
		for (int i = 0; i < 45; i++) {
			JButton temp = new JButton("0");
			temp.setBackground(new Color(50, 40, 56));
			temp.setForeground(Color.white);
			temp.setBorder(new LineBorder(Color.white));
			temp.setVisible(true);
			temp.setOpaque(false);
			temp.setFocusable(false);
			layoutPanel.add(temp);
		}
		
		scroll = new JScrollPane(layoutPanel);
		scroll.setBounds(10, 0, 300, 350);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setBackground(new Color(50, 40, 56));
		scroll.setVisible(true);
		scroll.setOpaque(false);
		this.add(scroll);
	}
	
	public JScrollPane getScroll() {
		return scroll;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO
	}
}
