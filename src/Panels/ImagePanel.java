package Panels;

import Frames.EditorFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ImagePanel extends JPanel implements MouseMotionListener, MouseListener{
	EditorFrame parent;
	JScrollPane scroll;
	JLayeredPane layeredPane;
	String currentImageName;
	ImageIcon image;
	JLabel imageLabel;
	int imageXCoord = 0;
	int imageYCoord = 0;
	Color currentColour;
	String currentCursor;
	boolean drawn = false;
	ArrayList<String> coords = new ArrayList<String>();
	boolean canSave = false;
	int lastXCoord = -20;
	int lastYCoord = -20;
	
	public ImagePanel(EditorFrame parent, String imageName) {
		this.parent = parent;
		currentImageName = imageName;
		image = new ImageIcon(imageName);
		imageLabel = new JLabel(image);
		currentColour = Color.black;
		currentCursor = "paint";
		
		layeredPane = new JLayeredPane();
		
		imageXCoord = 800 - image.getIconWidth()/2;
		imageYCoord = 375 - image.getIconHeight()/2;
		this.setBounds(imageXCoord, imageYCoord, image.getIconWidth(), image.getIconHeight());
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(50, 40, 56));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		
		scroll = new JScrollPane(this);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		scroll.setBounds(0, 0, 1600, 750);		
		
		this.add(imageLabel, BorderLayout.CENTER);
		layeredPane.add(scroll, Integer.valueOf(0));
		
		try {
			FileWriter writer = new FileWriter(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
			writer.write("0");
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public JScrollPane getScroll() {
		return scroll;
	}
	
	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}
	
	public void newImage() {
		try {
			// Run the C++ Program
			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd.exe /c start wsl ./newImage.out", null, new File(System.getProperty("user.dir")+"\\resources"));
			coords.clear();
			this.repaint();
			
			// Update currentImageName
			currentImageName = System.getProperty("user.dir")+"\\resources\\temp\\"+"untitled.jpg";
			
			// Update ImageLabel
			while(!canSave) {
				try {
					FileReader reader = new FileReader(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
					int data = reader.read();
					if ((char)data == '1') {
						reader.close();
						canSave = true;
					}
				} catch (FileNotFoundException e) {
					System.out.println("Problem opening update file");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Problem reading update file");
					e.printStackTrace();
				}
			}
			update(false);
			FileWriter updateWriter = new FileWriter(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
			updateWriter.write("0");
			updateWriter.close();
			canSave = false;
		} catch(IOException e) {
			System.out.println("Error creating new file");
		}
	}
	
	public void saveAsImage(String newName, boolean justSave, boolean isTemp) {
		if (coords.size() == 0) {
			return;
		}
		else {
			try {
				// Write to file the list of changes in coords
				FileWriter writer = new FileWriter(System.getProperty("user.dir")+"\\resources\\changes.txt");
				String finalLine = "";
				
				String temp = "";
				for (int i = currentImageName.length()-1; i > 0; i--) {
					if (currentImageName.charAt(i) == '\\' || currentImageName.charAt(i) == '/') {
						if (isTemp) {
							finalLine += "temp/" + temp + "\n";
						}
						else {
							finalLine += "images/" + temp + "\n";
						}
						break;
					}
					else {
						temp = currentImageName.charAt(i) + temp;
					}
				}
				temp = "";
				for (int i = newName.length()-1; i > 0; i--) {
					if (newName.charAt(i) == '\\' || newName.charAt(i) == '/') {
						finalLine += "images/" + temp + "\n";
						break;
					}
					else {
						temp = newName.charAt(i) + temp;
					}
				}
				finalLine += coords.size() + "\n";
				
				for (String coord : coords) {
					finalLine += coord + "\n";
				}
				writer.write(finalLine);
				writer.close();
				
				// Run the C++ Program
				Runtime rt = Runtime.getRuntime();
				if (justSave) {
					rt.exec("cmd.exe /c start wsl ./saveImage.out", null, new File(System.getProperty("user.dir")+"\\resources"));
				}
				else {
					rt.exec("cmd.exe /c start wsl ./saveAs.out", null, new File(System.getProperty("user.dir")+"\\resources"));
				}
				coords.clear();
				this.repaint();
				
				// Update currentImageName
				currentImageName = newName;
				
				// Update ImageLabel
				while(!canSave) {
					try {
						FileReader reader = new FileReader(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
						int data = reader.read();
						if ((char)data == '1') {
							reader.close();
							canSave = true;
						}
					} catch (FileNotFoundException e) {
						System.out.println("Problem opening update file");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("Problem reading update file");
						e.printStackTrace();
					}
				}
				update(justSave);
				FileWriter updateWriter = new FileWriter(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
				updateWriter.write("0");
				updateWriter.close();
				canSave = false;
				parent.setTemp(false);
			} catch (IOException e) {
				System.out.println("Error writing to change file or updating update file");
			}
		}
	}
	
	public void update(boolean justSave) {
		ImageIcon temp = null;
		if (justSave) {
			for (int i = currentImageName.length()-1; i > 0; i--) {
				if (currentImageName.charAt(i) == '\\' || currentImageName.charAt(i) == '/'){
					temp = new ImageIcon(currentImageName.substring(0, i-6)+"\\temp\\"+currentImageName.substring(i));
					break;
				}
			}
		}
		else {
			temp = new ImageIcon(currentImageName);
		}
		image = new ImageIcon(currentImageName);
		imageLabel.setVisible(false);
		this.remove(imageLabel);
		imageLabel = new JLabel(temp);
		imageLabel.setVisible(true);
		this.add(imageLabel, BorderLayout.CENTER);
		image = temp;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (coords.size() != 0) {
			for (String coord : coords) {
				String[] currentCoords = coord.split("[ ]", 5);
				g.setColor(new Color(Integer.parseInt(currentCoords[2]), Integer.parseInt(currentCoords[3]), Integer.parseInt(currentCoords[4])));
				g.fillRect(Integer.parseInt(currentCoords[0]), Integer.parseInt(currentCoords[1]), 1, 1);
			}
		}
	}
	
	public void updateColor(Color colour) {
		currentColour = colour;
	}
	
	public void updateCursor(String newCursor) {
		currentCursor = newCursor;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		parent.setSaved(false);
		if (currentCursor == "paint") {
			for (int i = e.getX()-2; i <= e.getX()+2; i++) {
				for (int j = e.getY()-2; j <= e.getY()+2; j++){
					coords.add(Integer.toString(i)+" "+Integer.toString(j)+" "+currentColour.getRed()+" "+currentColour.getGreen()+" "+currentColour.getBlue());
				}
			}
			this.repaint();
		}
		else if (currentCursor == "drag") {
			// TODO
		}
		else if (currentCursor == "select") {
			// TODO
		}
		else if (currentCursor == "eraser") {
			// TODO
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO
		if (currentCursor == "eyedropper") {
			// TODO
			if (coords.size() != 0) {
				for (int i = 0; i < coords.size(); i++) {
					String[] currentCoords = coords.get(i).split("[ ]", 2);
					if (Integer.parseInt(currentCoords[0]) == e.getX() && Integer.parseInt(currentCoords[1]) == e.getY()) {
						
					}
				}
			}
			else {
				try {
					// Write the image name and coordinates to 
					FileWriter writer = new FileWriter(System.getProperty("user.dir")+"\\resources\\changes.txt");
					String finalLine = "";
					String temp = "";
					for (int i = currentImageName.length()-1; i > 0; i--) {
						if (currentImageName.charAt(i) == '\\' || currentImageName.charAt(i) == '/') {
							if (parent.getTemp()) {
								finalLine += "temp/" + temp + "\n";
							}
							else {
								finalLine += "images/" + temp + "\n";
							}
							break;
						}
						else {
							temp = currentImageName.charAt(i) + temp;
						}
					}
					finalLine += Integer.toString(e.getX()) + " " + Integer.toString(e.getY());
					writer.write(finalLine);
					writer.close();
					
					// If the place you are clicking is not part of the current edit then run the eyedropper program to find it
					Runtime rt = Runtime.getRuntime();
					rt.exec("cmd.exe /c start wsl ./eyeDropper.out", null, new File(System.getProperty("user.dir")+"\\resources"));
					
					while(!canSave) {
						try {
							FileReader reader = new FileReader(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
							int data = reader.read();
							if ((char)data == '1') {
								reader.close();
								canSave = true;
							}
						} catch (FileNotFoundException e1) {
							System.out.println("Problem opening update file");
							e1.printStackTrace();
						} 
					}
					
					// Get the rgb value of the colour from changes.txt
					BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\resources\\changes.txt"));
					String rgbValues = br.readLine();
					br.close();
					String[] rgb = rgbValues.split("[ ]", 3);
					parent.updateColour(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
					
					FileWriter updateWriter = new FileWriter(System.getProperty("user.dir")+"\\resources\\checkUpdate.txt");
					updateWriter.write("0");
					updateWriter.close();
				} catch (IOException e1) {
					System.out.println("Error writing to file");
					e1.printStackTrace();
				}
				
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//TODO
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
