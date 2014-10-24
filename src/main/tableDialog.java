package main;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import options.Options;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class tableDialog extends JDialog {

	private static tableDialog dialog;
	private static Double[] extraAgrs = null;

	private final String[] columnNames = { "Parameter", "Value" };
	private String[][] mockupdata = null;

	private JTable table;

	public static Double[] showDialog(Frame superFrame, Component locationComp, String[] data) {
		dialog = new tableDialog(superFrame, locationComp, data);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(230, 290);
		dialog.setLocation(500, 200);
		dialog.setVisible(true);
		return extraAgrs;
	}

	/**
	 * Create the frame.
	 */
	private tableDialog(Frame superFrame, Component locationComp, String[] data) {
		
		super(superFrame, "Exrtra Parameters", true);
		
		mockupdata = new String[data.length][2];
		extraAgrs = new Double[data.length];

		for (int i = 0; i < data.length; i++) {
			mockupdata[i][0] = data[i];
			mockupdata[i][1] = "";
		}

		setBounds(100, 100, 215, 250);
		setLayout(null);

		table = new JTable(mockupdata, columnNames);

		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new continueHandler());
		btnContinue.setBounds(111, 218, 93, 23);
		add(btnContinue);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 194, 202);
		add(scrollPane);
		
		setLocationRelativeTo(this);

	}

	public void exitWindow() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.dispose();
	}

	public void popupDialog() {
		JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(this), "You need to provide values or finish editing value.", "Uncomplete error", JOptionPane.ERROR_MESSAGE);
	}

	private class continueHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean isFormat = true;

			for (int i = 0; i < table.getRowCount(); i++) {
				try {
					extraAgrs[i] = Double.parseDouble((String) table.getValueAt(i, 1));
				} catch (NumberFormatException e1) {
					isFormat = false;
					table.setValueAt("", i, 1);
				}
			}

			if (isFormat) {
				dispose();
			} else {
				popupDialog();
			}
		}

	}
}
