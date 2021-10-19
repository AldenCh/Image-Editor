package Panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Frames.EditorFrame;

public class ColourPanel extends JPanel implements ActionListener, ChangeListener{
	EditorFrame parent;
	JPanel buttonPanel, recentColoursPanel, shapePanel;
	JColorChooser colourChooser;
	ImageIcon drag = new ImageIcon("./resources/cursors/drag.jpg");
	ImageIcon paint = new ImageIcon("./resources/cursors/paintbrush.png");
	ImageIcon select = new ImageIcon("./resources/cursors/select.jpg");
	ImageIcon erase = new ImageIcon("./resources/cursors/eraser.png");
	ImageIcon dropper = new ImageIcon("./resources/cursors/eyedropper.png");
	JButton colourButton, paintBrush, dragCursor, selectTool, eraser, eyeDropper;
	JLabel sliderLabel;
	JSlider pixelSlider;
	ArrayList<JButton> recentColours = new ArrayList<JButton>();
	JButton recent1 = new JButton();
	JButton recent2 = new JButton();
	JButton recent3 = new JButton();
	JButton recent4 = new JButton();
	JButton recent5 = new JButton();
	
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
		colourButton.setBounds(135, 600, 50, 50);
		colourButton.setVisible(true);
		colourButton.setOpaque(true);
		colourButton.setFocusable(false);
		colourButton.setBorder(new LineBorder(Color.white, 5));
		colourButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,5,5,5));
		buttonPanel.setBounds(70, 30, 180, 50);
		buttonPanel.setBackground(new Color(50, 40, 56));
		buttonPanel.setBorder(new TitledBorder(new LineBorder(Color.white, 1), "Cursors", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Calibri", Font.BOLD, 12), Color.white));
		
		buttonPanel.add(paintBrush);
		buttonPanel.add(dragCursor);
		buttonPanel.add(selectTool);
		buttonPanel.add(eraser);
		buttonPanel.add(eyeDropper);
		
		shapePanel = new JPanel();
		shapePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Shapes", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Calibri", Font.BOLD, 12), Color.white));
		shapePanel.setBounds(60, 80, 200, 450);
		shapePanel.setLayout(new GridLayout(5, 5, 5, 5));
		shapePanel.setVisible(true);
		shapePanel.setOpaque(false);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				JButton temp = new JButton();
				temp.setEnabled(false);
				temp.setVisible(false);
				temp.setFocusable(false);
				shapePanel.add(temp);
			}
		}
		
		sliderLabel = new JLabel("Paintbrush Size");
		sliderLabel.setBounds(115, 540, 100, 10);
		sliderLabel.setForeground(Color.white);
		sliderLabel.setVisible(true);
		sliderLabel.setOpaque(false);
		
		pixelSlider = new JSlider(1, 5, 3);
		pixelSlider.setBounds(85, 555, 150, 30);
		pixelSlider.setFocusable(false);
		pixelSlider.setMajorTickSpacing(1);
		pixelSlider.setPaintLabels(true);
		pixelSlider.setSnapToTicks(true);
		pixelSlider.setForeground(Color.white);
		pixelSlider.setOpaque(false);
		pixelSlider.setVisible(true);
		pixelSlider.addChangeListener(this);
		
		recentColoursPanel = new JPanel();
		recentColoursPanel.setLayout(new GridLayout(1, 5, 5, 5));
		recentColoursPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Recent Colours", TitledBorder.LEFT, TitledBorder.CENTER, new Font("Calibri", Font.BOLD, 12), Color.white));
		
		recent1.addActionListener(this);
		recent1.setVisible(true);
		recent1.setOpaque(true);
		recent1.setBackground(Color.black);
		recentColoursPanel.add(recent1);
		
		recent2.addActionListener(this);
		recent2.setVisible(true);
		recent2.setOpaque(true);
		recent2.setBackground(Color.black);
		recentColoursPanel.add(recent2);
		
		recent3.addActionListener(this);
		recent3.setVisible(true);
		recent3.setOpaque(true);
		recent3.setBackground(Color.black);
		recentColoursPanel.add(recent3);
		
		recent4.addActionListener(this);
		recent4.setVisible(true);
		recent4.setOpaque(true);
		recent4.setBackground(Color.black);
		recentColoursPanel.add(recent4);
		
		recent5.addActionListener(this);
		recent5.setVisible(true);
		recent5.setOpaque(true);
		recent5.setBackground(Color.black);
		recentColoursPanel.add(recent5);
		
		recentColoursPanel.setBounds(10, 660, 290, 70);
		recentColoursPanel.setVisible(true);
		recentColoursPanel.setOpaque(false);
		
		this.add(buttonPanel);
		this.add(shapePanel);
		this.add(colourButton);
		this.add(sliderLabel);
		this.add(pixelSlider);
		this.add(recentColoursPanel);
	}
	
	public void updateColour(Color newColor) {
		if (newColor == Color.white) {
			colourButton.setBackground(newColor);
			colourButton.setBorder(new LineBorder(Color.black, 1));
		}
		else {
			colourButton.setBackground(newColor);
			if (colourButton.getBorder() == new LineBorder(Color.black, 1)) {
				colourButton.setBorder(new LineBorder(Color.white, 1));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Recent Colour Button Functionality
		
		if (e.getSource() == colourButton) {
			Color colour = JColorChooser.showDialog(null, "Pick a colour", colourButton.getBackground());
			if (colour != null) {
				parent.updateColour(colour);
			}
		}
		
		else if (e.getSource() == paintBrush) {
			parent.updateTooltip(0);
			parent.updateCursor("paint");
			paintBrush.setBorder(new LineBorder(Color.orange, 2));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == dragCursor) {
			parent.updateTooltip(1);
			parent.updateCursor("drag");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(Color.orange, 2));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == selectTool) {
			parent.updateTooltip(2);
			parent.updateCursor("select");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(Color.orange, 2));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == eraser) {
			parent.updateTooltip(3);
			parent.updateCursor("eraser");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(Color.orange, 2));
			eyeDropper.setBorder(new LineBorder(new Color(50, 40, 56), 1));
		}
		
		else if (e.getSource() == eyeDropper) {
			parent.updateTooltip(4);
			parent.updateCursor("eyedropper");
			paintBrush.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			dragCursor.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			selectTool.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eraser.setBorder(new LineBorder(new Color(50, 40, 56), 1));
			eyeDropper.setBorder(new LineBorder(Color.orange, 2));
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == pixelSlider) {
			parent.updatePixelSize(pixelSlider.getValue());
		}
	}
	
}