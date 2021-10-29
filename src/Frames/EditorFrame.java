package Frames;

import Panels.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorFrame extends JFrame implements ActionListener, ComponentListener{
	ImageIcon logo = new ImageIcon("resources/images/orange.jpg");
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu;
	JMenuItem newItem;
	JMenuItem openItem;
	JMenuItem saveAsItem;
	JMenuItem saveItem;
	ImagePanel image;
	ColourPanel colourPalette;
	FilterPanel filters;
	TooltipPanel tooltip;
	int windowWidth;
	int windowHeight;
	boolean saved = true;
	boolean isTemp = false;
	String originalImagePath;
	Color currentColour;
	JFrame saveWarning = new JFrame();
	JFileChooser fc = new JFileChooser();
	File lastDirectory = new File(System.getProperty("user.dir")+"\\resources\\images");
	FileNameExtensionFilter jpgOnly = new FileNameExtensionFilter("Only .jpg files", "jpg");
	
	public EditorFrame(String imageName){
		currentColour = Color.black;
		originalImagePath = imageName;
		createMenuBar();
		
		this.setJMenuBar(menuBar);
		this.setLayout(null);
		this.setBounds(-10, -1, 1940, 1085);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setIconImage(logo.getImage());
		for (int i = imageName.length()-1; i > 0; i--) {
			if (imageName.charAt(i) == '\\' || imageName.charAt(i) == '/') {
				this.setTitle("Editing: " + imageName.substring(i+1));
				break;
			}
		}
		
		image = new ImagePanel(this, originalImagePath);
		image.setOpaque(true);
		image.setVisible(true);
		image.getScroll().setOpaque(true);
		image.getScroll().setVisible(true);
		image.getLayeredPane().setOpaque(true);
		image.getLayeredPane().setVisible(true);
		image.getLayeredPane().setBounds(0, 0, 1600, 750);
		
		colourPalette = new ColourPanel(this);
		colourPalette.setOpaque(true);
		colourPalette.setVisible(true);
		colourPalette.setBounds(1605, 5, 310, 745);
		colourPalette.setForeground(Color.white);
		colourPalette.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		filters = new FilterPanel(this);
		filters.setOpaque(true);
		filters.setVisible(true);
		filters.getScroll().setOpaque(true);
		filters.getScroll().setVisible(true);
		filters.getScroll().setBounds(0, 750, 1600, 285);
		
		tooltip = new TooltipPanel();
		tooltip.setOpaque(true);
		tooltip.setVisible(true);
		tooltip.setBounds(1600, 750, 320, 285);
		
		this.add(image.getLayeredPane());
		this.add(colourPalette);
		this.add(filters.getScroll());
		this.add(tooltip);
		
		this.addComponentListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(50, 40, 56));
		
		Timer timer = new Timer();
		TimerTask checkSaved = new TimerTask() {
			@Override
			public void run() {
				updateTitle(saved);
			}
		};
		timer.schedule(checkSaved, 0, 100);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (saved == true) {
					System.exit(0);
				}
				else {
					int result = JOptionPane.showConfirmDialog(saveWarning, "Do you want to save the current project before exiting?", "Save Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						// TODO Prompt for save name, save the file
						if (isTemp) {
							new SaveAsFrame(returnSelf());
						}
						else {
							saved = true;
							image.saveAsImage(originalImagePath, true, isTemp);
						}
						
						System.exit(0);
					}
					else if (result == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}
			}
		});
	}
	
	EditorFrame returnSelf() {
		return this;
	}
	
	public void updateCursor(String newCursor)
	{
		image.updateCursor(newCursor);
	}
	
	public void updateTooltip(int newTooltip) {
		tooltip.setTooltip(newTooltip);
	}
	
	void updateTitle(boolean isSaved) {
		if (!isSaved) {
			if (this.getTitle().charAt(0) != '*') {
				this.setTitle("*"+this.getTitle());
			}
		}
		else {
			if (this.getTitle().charAt(0) == '*') {
				this.setTitle(this.getTitle().substring(1));
			}
		}
	}
	
	public void updatePixelSize(int newSize) {
		image.setPixelSize(newSize);
	}
	
	public void updateColour(Color colour) {
		currentColour = colour;
		colourPalette.updateColour(colour);
		image.updateColor(currentColour);
	 }
	
	public void flipHorizontal() {
		image.flipHorizontal(isTemp);
		updateTitle(saved);
	}
	
	public void flipVertical() {
		image.flipVertical(isTemp);
	}
	
	void createMenuBar() {
		fileMenu = new JMenu("File");
		newItem = new JMenuItem("New Image");
		openItem = new JMenuItem("Open Image");
		saveItem = new JMenuItem("Save Image");
		saveAsItem = new JMenuItem("Save Image As");
		
		newItem.addActionListener(this);
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		saveAsItem.addActionListener(this);
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newItem.setMnemonic(KeyEvent.VK_N);
		openItem.setMnemonic(KeyEvent.VK_O);
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveAsItem.setMnemonic(KeyEvent.VK_A);
		
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		menuBar.add(fileMenu);
	}
	 
	public void setSaved(boolean state) {
		saved = state;
	}
	
	public boolean getTemp() {
		return isTemp;
	}
	
	public void setTemp(boolean state) {
		isTemp = state;
	}
	
	public void updateComponents() {
		// TODO
	}
	
	public void openImage() {
		fc.setCurrentDirectory(lastDirectory);
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(jpgOnly);
		int response = fc.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			File file = new File(fc.getSelectedFile().getAbsolutePath());
			lastDirectory = file;
			try {
				originalImagePath = file.getCanonicalPath();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		for (int i = originalImagePath.length()-1; i > 0; i--) {
			if (originalImagePath.charAt(i) == '\\' || originalImagePath.charAt(i) == '/') {
				this.setTitle("Editing: " + originalImagePath.substring(i+1));
				break;
			}
		}
		
		image.setOpaque(false);
		image.setVisible(false);
		image.getScroll().setOpaque(false);
		image.getScroll().setVisible(false);
		image.getLayeredPane().setOpaque(false);
		image.getLayeredPane().setVisible(false);
		
		this.remove(image.getLayeredPane());
		image = new ImagePanel(this, originalImagePath);
		image.setOpaque(true);
		image.setVisible(true);
		image.getScroll().setOpaque(true);
		image.getScroll().setVisible(true);
		image.getLayeredPane().setOpaque(true);
		image.getLayeredPane().setVisible(true);
		image.getLayeredPane().setBounds(0, 0, 1600, 750);
		this.add(image.getLayeredPane());
	}
	
	public void saveAsImage(String name) {
		saved = true;
		originalImagePath = System.getProperty("user.dir")+"\\resources\\images\\"+name;
		image.saveAsImage(originalImagePath, false, isTemp);
		for (int i = originalImagePath.length()-1; i > 0; i--) {
			if (originalImagePath.charAt(i) == '\\' || originalImagePath.charAt(i) == '/') {
				this.setTitle("Editing: " + originalImagePath.substring(i+1));
				break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newItem) {
			saved = true;
			this.setTitle("Editing: Untitled Unsaved Project");
			originalImagePath = System.getProperty("user.dir")+"\\resources\\temp\\untitled.jpg";
			image.newImage();
			isTemp = true;
		}
		else if (e.getSource() == openItem) {
			saved = true;
			openImage();
		}
		else if (e.getSource() == saveItem) {
			if (isTemp) {
				new SaveAsFrame(this);
			}
			else {
				saved = true;
				image.saveAsImage(originalImagePath, true, isTemp);
			}
		}
		else if (e.getSource() == saveAsItem) {
			new SaveAsFrame(this);
		} 
	}

	@Override
	public void componentResized(ComponentEvent e) {
		windowWidth = this.getWidth();
		windowHeight = this.getHeight();
		updateComponents();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}
