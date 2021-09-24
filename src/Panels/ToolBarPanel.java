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
	
	public ToolBarPanel(EditorFrame parent) {
		this.parent = parent;
		this.setLayout(new GridLayout(20, 1, 0, 0));
		this.setBounds(0, 0, 240, 700);
		
		for (int i = 0; i < 20; i++) {
			JButton temp = new JButton("Tool");
			temp.setBackground(Color.gray.darker());
			temp.setForeground(Color.white);
			temp.setBorder(new LineBorder(Color.white));
			temp.setPreferredSize(new Dimension(240,50));
			temp.setVisible(true);
			temp.setFocusable(false);
			this.add(temp);
		}
		
		scroll = new JScrollPane(this);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
	}
	
	public JScrollPane getScroll() {
		return scroll;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO
	}
}
