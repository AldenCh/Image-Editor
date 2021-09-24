package Panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Frames.EditorFrame;

public class ColourPanel extends JPanel implements ActionListener{
	EditorFrame parent;
	JPanel buttonPanel = new JPanel();
	JColorChooser colourChooser;
	ImageIcon drag = new ImageIcon("./resources/cursors/drag.jpg");
	ImageIcon paint = new ImageIcon("./resources/cursors/paintbrush.png");
	ImageIcon select = new ImageIcon("./resources/cursors/select.jpg");
	ImageIcon erase = new ImageIcon("./resources/cursors/eraser.png");
	ImageIcon dropper = new ImageIcon("./resources/cursors/eyedropper.png");
	JButton colourButton, paintBrush, dragCursor, selectTool, eraser, eyeDropper;
	
	public ColourPanel(EditorFrame parent) {
		this.parent = parent;
		this.setBackground(new Color(50, 40, 56));
		this.setLayout(null);
		
		colourChooser = new JColorChooser();
		
		paintBrush = new JButton(paint);
		paintBrush.setVisible(true);
		paintBrush.setFocusable(false);
		paintBrush.setBorder(new LineBorder(Color.orange, 1));
		paintBrush.addActionListener(this);
		
		dragCursor = new JButton(drag);
		dragCursor.setVisible(true);
		dragCursor.setFocusable(false);
		dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		dragCursor.addActionListener(this);

		selectTool = new JButton(select);
		selectTool.setVisible(true);
		selectTool.setFocusable(false);
		selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		selectTool.addActionListener(this);
		
		eraser = new JButton(erase);
		eraser.setVisible(true);
		eraser.setOpaque(true);
		eraser.setFocusable(false);
		eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		eraser.addActionListener(this);
		
		eyeDropper = new JButton(dropper);
		eyeDropper.setVisible(true);
		eyeDropper.setOpaque(true);
		eyeDropper.setFocusable(false);
		eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		eyeDropper.addActionListener(this);
		
		colourButton = new JButton();
		colourButton.setBackground(Color.black);
		colourButton.setBounds(135, 225, 50, 50);
		colourButton.setVisible(true);
		colourButton.setOpaque(true);
		colourButton.setFocusable(false);
		colourButton.setBorder(new LineBorder(Color.white, 5));
		colourButton.addActionListener(this);
		
		buttonPanel.setLayout(new GridLayout(5,5,5,5));
		buttonPanel.setBounds(75, 50, 170, 160);
		buttonPanel.setBackground(new Color(50, 40, 56));
		buttonPanel.setBorder(new LineBorder(Color.white, 1));
		
		buttonPanel.add(paintBrush);
		buttonPanel.add(dragCursor);
		buttonPanel.add(selectTool);
		buttonPanel.add(eraser);
		buttonPanel.add(eyeDropper);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				JButton temp = new JButton();
				temp.setEnabled(false);
				temp.setVisible(false);
				temp.setFocusable(false);
				buttonPanel.add(temp);
			}
		}
		this.add(buttonPanel);
		this.add(colourButton);
	}
	
	public void updateColour(Color newColor) {
		colourButton.setBackground(newColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == colourButton) {
			Color colour = JColorChooser.showDialog(null, "Pick a colour", colourButton.getBackground());
			parent.updateColour(colour);
		}
		
		else if (e.getSource() == paintBrush) {
			parent.updateCursor("paint");
			paintBrush.setBorder(new LineBorder(Color.orange, 2));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == dragCursor) {
			parent.updateCursor("drag");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(Color.orange, 2));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == selectTool) {
			parent.updateCursor("select");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(Color.orange, 2));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == eraser) {
			parent.updateCursor("eraser");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(Color.orange, 2));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == eyeDropper) {
			parent.updateCursor("eyedropper");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(Color.orange, 2));
		}
		
	}
	
}