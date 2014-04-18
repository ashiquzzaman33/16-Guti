package com.application.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.application.model.GeneralAttribute;

public class PlayerAvaterManager extends JDialog {
	private static final long serialVersionUID = 1L;
	private PlayerAvaterManager playerAvaterManager;
	private JToggleButton button[];
	private String images[];
	private ButtonGroup buttonGroup;
	private ButtonGroup buttonGroupCheckBox;
	private JTextField textFieldLoadExternal = new JTextField("");

	private JPanel internalAvaterPanel = new JPanel();
	private JCheckBox checkBoxInternalImage = new JCheckBox("");
	private JCheckBox checkBoxExternalFile = new JCheckBox("");
	private JButton buttonLoadExternal = new JButton("");
	private String internalImagesAbsolutePath = "";
	private String externalAbsolutePath = "";
	private int pId;
	private GeneralAttribute generalAttribute;

	public PlayerAvaterManager(Window owner,GeneralAttribute ga, URL url, int id) {
		super(owner, ModalityType.APPLICATION_MODAL);
		pId = id;
		playerAvaterManager = this;
		generalAttribute = ga; 
		int counter = 0;
		String path = url.getFile();
		setTitle("Select Avater");

		Pattern pat = Pattern.compile("[%20]+");
		Matcher mat = pat.matcher(path);
		path = mat.replaceAll(" ");
		pat = Pattern.compile("file:");
		mat = pat.matcher(path);
		path = mat.replaceAll("");
		getContentPane().setLayout(null);
		ItemHandeler itemHandeler = new ItemHandeler();
		buttonGroupCheckBox = new ButtonGroup();
		try {
			File file = new File(path);
			String filse[] = file.list();
			images = new String[filse.length];
			for (int i = 0; i < filse.length; i++) {
				File n = new File(path + "/" + filse[i]);
				if (n.isFile()
						&& (Pattern.matches(".+[.]jpg", n.getName())
								|| Pattern.matches(".+[.]png", n.getName()) || Pattern
									.matches(".+[.]jpeg", n.getName()))) {
					images[counter] = n.getAbsolutePath();
					counter++;
				}

			}
			button = new JToggleButton[counter];
			buttonGroup = new ButtonGroup();

			internalAvaterPanel.setLayout(new GridLayout(0, 4, 0, 0));
			internalAvaterPanel.setBounds(7, 15, 480, 240);
			ActionHandeler handeler = new ActionHandeler();
			for (int i = 0; i < counter; i++) {
				button[i] = new JToggleButton(new ImageIcon(images[i]));
				button[i].setActionCommand(images[i]);
				button[i].setBorderPainted(false);
				button[i].setContentAreaFilled(false);
				button[i].addActionListener(handeler);
				internalAvaterPanel.add(button[i]);
				buttonGroup.add(button[i]);
			}
			checkBoxInternalImage.setOpaque(false);
			checkBoxInternalImage.setBounds(202, 7, 121, 23);
			getContentPane().add(checkBoxInternalImage);
			buttonGroupCheckBox.add(checkBoxInternalImage);
			checkBoxInternalImage.addItemListener(itemHandeler);
			checkBoxInternalImage.setSelected(true);
		} catch (Exception e) {
			checkBoxExternalFile.setSelected(true);
			checkBoxInternalImage.setEnabled(false);
			JOptionPane.showMessageDialog(null,
					"Internal Image Not Supported!!!",
					"Internal Image Not Supported!!!",
					JOptionPane.ERROR_MESSAGE);
		}

		JPanel panelSelectAvater = new JPanel();
		panelSelectAvater.setBackground(Color.WHITE);
		panelSelectAvater.setBorder(new TitledBorder(new MatteBorder(2, 2, 2,
				2, (Color) new Color(128, 128, 128)), "Select An Avater",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0,
						255)));
		panelSelectAvater.setBounds(10, 10, 514, 270);
		getContentPane().add(panelSelectAvater);
		panelSelectAvater.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(internalAvaterPanel);
		panelSelectAvater.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_bottom = new JPanel();
		panel_bottom.setBounds(10, 280, 514, 146);
		getContentPane().add(panel_bottom);
		panel_bottom.setLayout(null);

		checkBoxExternalFile.setOpaque(false);
		checkBoxExternalFile.setBounds(153, 7, 189, 23);
		buttonGroupCheckBox.add(checkBoxExternalFile);
		checkBoxExternalFile.addItemListener(itemHandeler);
		panel_bottom.add(checkBoxExternalFile);

		JPanel panelLoadExternal = new JPanel();
		panelLoadExternal.setBorder(new TitledBorder(new MatteBorder(2, 2, 2,
				2, (Color) new Color(128, 128, 128)),
				"Load Avater From External File", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 255)));
		panelLoadExternal.setBounds(0, 11, 514, 78);
		panel_bottom.add(panelLoadExternal);
		panelLoadExternal.setLayout(null);

		textFieldLoadExternal.setBounds(31, 25, 407, 30);
		panelLoadExternal.add(textFieldLoadExternal);
		textFieldLoadExternal.setColumns(10);

		buttonLoadExternal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = getExternalFileName();
				if (path != null)
					externalAbsolutePath = path;
				textFieldLoadExternal.setText(externalAbsolutePath);
			}
		});
		buttonLoadExternal.setIcon(new ImageIcon(PlayerAvaterManager.class
				.getResource("/resource/images/Folder-files.png")));
		buttonLoadExternal.setBounds(447, 22, 57, 33);
		panelLoadExternal.add(buttonLoadExternal);

		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerAvaterManager.dispose();
			}
		});
		buttonCancel.setBounds(413, 100, 80, 25);
		panel_bottom.add(buttonCancel);

		JButton buttonOk = new JButton("Ok");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (internalImagesAbsolutePath == ""
						&& externalAbsolutePath == "") {
					generalAttribute.setImage(null, pId);
				} else if (internalImagesAbsolutePath == "") {
					generalAttribute.setImage(new ImageIcon(
							externalAbsolutePath).getImage(), pId);
				} else {
					generalAttribute.setImage(new ImageIcon(
							internalImagesAbsolutePath).getImage(), pId);
				}
				playerAvaterManager.dispose();
			}
		});
		buttonOk.setBounds(294, 100, 80, 25);
		panel_bottom.add(buttonOk);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(539, 452);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private String getExternalFileName() {
		FileDialog fileChooser = new FileDialog(this, "Select Avater", FileDialog.LOAD);
		fileChooser.setVisible(true);
		if(fileChooser.getFile()!=null){
			String fn = fileChooser.getFile();
			if(fn.endsWith(".jpg")||fn.endsWith(".jpeg")||fn.endsWith(".png")||fn.endsWith(".bmp"))
				externalAbsolutePath = fileChooser.getDirectory()+fileChooser.getFile();
			else{
				return null;
			}
		}
		return externalAbsolutePath;
	}

	private class ActionHandeler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (JToggleButton btn : button) {
				if (e.getSource() == btn) {
					btn.setEnabled(false);
					internalImagesAbsolutePath = btn.getActionCommand();
				} else
					btn.setEnabled(true);
			}
		}

	}

	private class ItemHandeler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (checkBoxExternalFile.isSelected()) {
				for (JToggleButton btn : button) {
					btn.setSelected(false);
					btn.setEnabled(false);

				}
				textFieldLoadExternal.setEnabled(true);
				buttonLoadExternal.setEnabled(true);
				internalImagesAbsolutePath = "";
			} else if (checkBoxInternalImage.isSelected()) {
				for (JToggleButton btn : button) {
					btn.setEnabled(true);
				}
				textFieldLoadExternal.setEnabled(false);
				buttonLoadExternal.setEnabled(false);
				externalAbsolutePath = "";
				textFieldLoadExternal.setText("");
			}
		}
	}
}
