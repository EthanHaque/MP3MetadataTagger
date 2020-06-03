package com.ethanhaque.lyric.UpdateLyrics;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * GUI for adding lyrics to mp3 files in directory or to single files.
 * 
 * @author Ethan_H_Laptop
 * @version 05/27/2020
 *
 */
public class LyricGUI {
	private JFrame frame;
	private AddLyricsToMP3 addLyricsToMP3;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private boolean replace;
	private boolean single;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LyricGUI window = new LyricGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LyricGUI() {
		initialize();
		addLyricsToMP3 = new AddLyricsToMP3();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 501, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JCheckBox replaceExistingLyrics = new JCheckBox("Replace Existing Lyrics");
		replaceExistingLyrics.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				replace = replaceExistingLyrics.isSelected();
			}
		});
		replaceExistingLyrics.setBounds(6, 95, 158, 23);
		replace = replaceExistingLyrics.isSelected();
		frame.getContentPane().add(replaceExistingLyrics);

		JRadioButton singleFile = new JRadioButton("Single File");
		singleFile.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				single = singleFile.isSelected();
			}
		});
		singleFile.setSelected(true);
		buttonGroup.add(singleFile);
		singleFile.setBounds(6, 121, 158, 23);
		frame.getContentPane().add(singleFile);

		JRadioButton wholeFolder = new JRadioButton("Whole Folder");
		buttonGroup.add(wholeFolder);
		wholeFolder.setBounds(6, 147, 158, 23);
		frame.getContentPane().add(wholeFolder);

		JLabel currentAction = new JLabel("Make selection");
		currentAction.setHorizontalAlignment(SwingConstants.CENTER);
		currentAction.setBounds(0, 207, 485, 14);
		frame.getContentPane().add(currentAction);

		JButton openExplorer = new JButton("Open Explorer");
		openExplorer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (single) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3", "mp3");
					chooser.setFileFilter(filter);
					int returnVal = chooser.showOpenDialog(frame);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File chosenFile = chooser.getSelectedFile();
						String path = chosenFile.getAbsolutePath();
						currentAction.setText("Adding lyrics to: " + chosenFile.getName());
						boolean success = false;
						try {
							if (addLyricsToMP3.addLyrics(path, replace)) {
								success = true;
							} else {
								success = false;
							}
						} catch (Exception e) {
							success = false;
						}
						if (success) {
							currentAction.setText("Finished adding lyrics to: " + chosenFile.getName() + ".");
//							System.out.println("Finished adding lyrics to: " + chosenFile.getName());
						} else {
							currentAction.setText("Did not add lyrics to: " + chosenFile.getName() + ".");
//							System.out.println("Did not add lyrics to: " + chosenFile.getName());
						}

					}
				} else {
					currentAction.setText("Adding lyrics to files in directory...");
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = chooser.showOpenDialog(frame);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						String path = chooser.getSelectedFile().getAbsolutePath();
						for (File file : traverseDirectoryForMP3(path)) {
							String subPath = file.getAbsolutePath();
							try {
								if (addLyricsToMP3.addLyrics(subPath, replace)) {
								} else {
								}
							} catch (Exception e) {
							}
						}
						currentAction.setText("Finished adding lyrics in directory.");
					}
				}

			}
		});
		openExplorer.setBounds(300, 121, 115, 23);
		frame.getContentPane().add(openExplorer);

		JLabel title = new JLabel("Add Lyrics to MP3s");
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 11, 485, 36);
		frame.getContentPane().add(title);

	}

	/**
	 * Finds mp3s in directory
	 * 
	 * @param path
	 * @return array of files in directory.
	 */
	private File[] traverseDirectoryForMP3(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles((d, name) -> name.endsWith(".mp3"));
		return files;
	}
}

