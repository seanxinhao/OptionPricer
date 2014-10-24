package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.JTextField;

import options.Options;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionPricer {

	/*
	 * Initialize this program
	 */
	static private ArrayList<String> optionNameList = new ArrayList<String>();
	static private ArrayList<String> algorithmNameList = new ArrayList<String>();

	static {
		/*
		 * Read list from file.
		 */

		File optionConfig = new File("optionConfig.ini");
		File algorithmConfig = new File("algorithmConfig.ini");

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(optionConfig));
			String line;

			while ((line = fileReader.readLine()) != null) {
				optionNameList.add(line);
			}
			fileReader.close();

			fileReader = new BufferedReader(new FileReader(algorithmConfig));
			while ((line = fileReader.readLine()) != null) {
				algorithmNameList.add(line);
			}
			fileReader.close();
		} catch (FileNotFoundException e1) {
			PrintWriter writer;
			try {
				writer = new PrintWriter("optionConfig.ini", "UTF-8");
				writer.println("AmericanCallOption");
				writer.close();
				writer = new PrintWriter("algorithmConfig.ini", "UTF-8");
				writer.println("BSformula");
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < optionNameList.size(); i++) {
				Class.forName("options." + optionNameList.get(i));
			}
			for (int i = 0; i < algorithmNameList.size(); i++) {
				Class.forName("algorithms." + algorithmNameList.get(i));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private final int DATASET_SIZE = 7;

	private String selectedOption = new String();
	private String selectedAlgorithm = new String();

	private JFrame frame;
	private JList optionList;
	private JList algorithmList;
	private JTextField textField_S0;
	private JTextField textField_T;
	private JTextField textField_Sigma;
	private JTextField textField_K;
	private JTextField textField_r;
	private JTextArea output;
	private JButton btnCalculate;
	private JButton btnGenerateVs;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionPricer window = new OptionPricer();
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
	public OptionPricer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 180, 500, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel_result = new JPanel();
		panel_result.setBorder(new TitledBorder(null, "Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_result.setBounds(10, 10, 464, 112);
		frame.getContentPane().add(panel_result);
		panel_result.setLayout(new GridLayout(1, 0, 0, 0));

		output = new JTextArea();
		JScrollPane outputPane = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_result.add(outputPane);

		JPanel panel_optionList = new JPanel();
		panel_optionList.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_optionList.setBounds(10, 134, 149, 183);
		frame.getContentPane().add(panel_optionList);
		panel_optionList.setLayout(new GridLayout(1, 0, 0, 0));

		optionList = new JList(optionNameList.toArray());
		panel_optionList.add(optionList);
		ListSelectionModel lsm = optionList.getSelectionModel();
		lsm.addListSelectionListener(new optionListHandler());

		JPanel panel_algorithmList = new JPanel();
		panel_algorithmList.setBorder(new TitledBorder(null, "Algorithm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_algorithmList.setBounds(169, 132, 149, 185);
		frame.getContentPane().add(panel_algorithmList);
		panel_algorithmList.setLayout(new GridLayout(1, 0, 0, 0));

		algorithmList = new JList(algorithmNameList.toArray());
		panel_algorithmList.add(algorithmList);
		lsm = algorithmList.getSelectionModel();
		lsm.addListSelectionListener(new algorithmListHandler());
		algorithmList.setEnabled(false);

		JPanel panel_parameters = new JPanel();
		panel_parameters.setBorder(new TitledBorder(null, "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_parameters.setBounds(328, 132, 146, 185);
		frame.getContentPane().add(panel_parameters);
		panel_parameters.setLayout(null);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Double.class);
		formatter.setMaximum(Double.MAX_VALUE);
		formatter.setCommitsOnValidEdit(true);

		textField_S0 = new JFormattedTextField(formatter);
		textField_S0.setBounds(60, 22, 64, 21);
		panel_parameters.add(textField_S0);
		textField_S0.setColumns(10);
		textField_S0.setEnabled(false);

		textField_T = new JFormattedTextField(formatter);
		textField_T.setBounds(60, 53, 64, 21);
		panel_parameters.add(textField_T);
		textField_T.setColumns(10);
		textField_T.setEnabled(false);

		textField_Sigma = new JFormattedTextField(formatter);
		textField_Sigma.setBounds(60, 84, 64, 21);
		panel_parameters.add(textField_Sigma);
		textField_Sigma.setColumns(10);
		textField_Sigma.setEnabled(false);

		textField_K = new JFormattedTextField(formatter);
		textField_K.setBounds(60, 115, 64, 21);
		panel_parameters.add(textField_K);
		textField_K.setColumns(10);
		textField_K.setEnabled(false);

		textField_r = new JFormattedTextField(formatter);
		textField_r.setBounds(60, 150, 64, 21);
		panel_parameters.add(textField_r);
		textField_r.setColumns(10);
		textField_r.setEnabled(false);

		JLabel lblS = new JLabel("S0");
		lblS.setBounds(10, 25, 40, 15);
		panel_parameters.add(lblS);

		JLabel lblT = new JLabel("T");
		lblT.setBounds(10, 56, 40, 15);
		panel_parameters.add(lblT);

		JLabel lblSigma = new JLabel("Sigma");
		lblSigma.setBounds(10, 84, 40, 15);
		panel_parameters.add(lblSigma);

		JLabel lblK = new JLabel("K");
		lblK.setBounds(10, 118, 40, 15);
		panel_parameters.add(lblK);

		JLabel lblR = new JLabel("r");
		lblR.setBounds(10, 153, 40, 15);
		panel_parameters.add(lblR);

		btnGenerateVs = new JButton("VSmile");
		btnGenerateVs.addActionListener(new VSmileHandler());
		btnGenerateVs.setBounds(10, 325, 93, 23);
		frame.getContentPane().add(btnGenerateVs);
		btnGenerateVs.setEnabled(false);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(381, 327, 93, 23);
		frame.getContentPane().add(btnExit);

		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new calculationHandler());
		btnCalculate.setBounds(281, 327, 93, 23);
		frame.getContentPane().add(btnCalculate);
		btnCalculate.setEnabled(false);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new clearHandler());
		btnClear.setBounds(179, 327, 93, 23);
		frame.getContentPane().add(btnClear);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmAddOption = new JMenuItem("Add Option...");
		mntmAddOption.addActionListener(new loadOptionHandler());
		mnFile.add(mntmAddOption);

		JMenuItem mntmAddAlgorithm = new JMenuItem("Add Algorithm...");
		mntmAddAlgorithm.addActionListener(new loadAlgorithmHandler());
		mnFile.add(mntmAddAlgorithm);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmHelp = new JMenuItem("Help");
		mnAbout.add(mntmHelp);

		JSeparator separator_1 = new JSeparator();
		mnAbout.add(separator_1);

		JMenuItem mntmAboutUs = new JMenuItem("About us");
		mnAbout.add(mntmAboutUs);
	}

	private class optionListHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				selectedOption = optionList.getSelectedValue().toString();
				output.setText("Select Option: " + selectedOption + "\n");
				OptionsPricerFactory opf = OptionsPricerFactory.getInstance();
				try {
					Options op = opf.getOption(selectedOption);
					ArrayList<String> aglist = op.getAlgorithms();
					algorithmList.setListData(aglist.toArray());
					algorithmList.setEnabled(true);
				} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class algorithmListHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				selectedAlgorithm = algorithmList.getSelectedValue().toString();
				output.append("Select Algorithm: " + selectedAlgorithm + "\n");
				textField_S0.setEnabled(true);
				textField_T.setEnabled(true);
				textField_Sigma.setEnabled(true);
				textField_K.setEnabled(true);
				textField_r.setEnabled(true);
				btnCalculate.setEnabled(true);
				btnGenerateVs.setEnabled(true);
				optionList.setEnabled(false);
			}
		}
	}

	private class calculationHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsPricerFactory opf = OptionsPricerFactory.getInstance();
			try {
				Options op = opf.getOption(selectedOption);

				String S0 = textField_S0.getText();
				String T = textField_T.getText();
				String Sigma = textField_Sigma.getText();
				String K = textField_K.getText();
				String r = textField_r.getText();

				if (!(S0.isEmpty() || T.isEmpty() || Sigma.isEmpty() || K.isEmpty() || r.isEmpty())) {
					op.setCurrentStockPrice(Double.parseDouble(S0));
					op.setStrikePrice(Double.parseDouble(K));
					op.setTermInYears(Double.parseDouble(T));
					op.setInterestRate(Double.parseDouble(r));
					op.setVolatility(Double.parseDouble(Sigma));

					output.append("Parameters Approved\n");
					output.append("S0:" + S0 + "    T:" + T + "    Sigma:" + Sigma + "    K:" + K + "    r:" + r + "\n");

					String[] argsNames = op.getAlgorithmArgsNames(selectedAlgorithm);
					Double[] extraAgrs =  null;
					Double price = 0.0;
					
					if (argsNames != null && argsNames.length != 0) {
						extraAgrs = tableDialog.showDialog(frame, (JButton)e.getSource(), argsNames);
						price = op.getPrice(selectedAlgorithm, extraAgrs);
					} else {
						price = op.getPrice(selectedAlgorithm, null);
					}
					
					output.append("Result is: " + price);
				} else {
					output.append("Need More Parameters!\n");
				}

			} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	private class VSmileHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsPricerFactory opf = OptionsPricerFactory.getInstance();
			try {
				Options op = opf.getOption(selectedOption);
				
				String S0 = textField_S0.getText();
				String T = textField_T.getText();
				String Sigma = textField_Sigma.getText();
				String K = textField_K.getText();
				String r = textField_r.getText();

				if (!(S0.isEmpty() || T.isEmpty() || Sigma.isEmpty() || K.isEmpty() || r.isEmpty())) {
					output.append("Parameters Approved\n");
					output.append("S0:" + S0 + "    T:" + T + "    Sigma:" + Sigma + "    K:" + K + "    r:" + r + "\n");
					
					op.setCurrentStockPrice(Double.parseDouble(S0));
					op.setStrikePrice(Double.parseDouble(K));
					op.setTermInYears(Double.parseDouble(T));
					op.setInterestRate(Double.parseDouble(r));
					
					Double doubleSigma = Double.parseDouble(Sigma);

					String[] argsNames = op.getAlgorithmArgsNames(selectedAlgorithm);
					Double[] extraAgrs =  null;		
					Double[] dataset = new Double[DATASET_SIZE];
					
					if (argsNames != null && argsNames.length != 0) {
						extraAgrs = tableDialog.showDialog(frame, (JButton)e.getSource(), argsNames);
						for (int i = 0; i < DATASET_SIZE; i++) {
							//op.setVolatility(Sigma[i]);
							dataset[i] = op.getPrice(selectedAlgorithm, extraAgrs);
						}
					} else {
						for (int i = 0; i < DATASET_SIZE; i++) {
							dataset[i] = op.getPrice(selectedAlgorithm, null);
						}
					}
				}
				else {
					output.append("Need More Parameters!\n");
				}
			} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	private class clearHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			algorithmList.clearSelection();
			optionList.clearSelection();
			selectedOption = new String();
			selectedAlgorithm = new String();
			output.setText("");
			textField_S0.setEnabled(false);
			textField_T.setEnabled(false);
			textField_Sigma.setEnabled(false);
			textField_K.setEnabled(false);
			textField_r.setEnabled(false);
			btnCalculate.setEnabled(false);
			btnGenerateVs.setEnabled(false);
			optionList.setEnabled(true);
		}

	}

	private class loadOptionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter JARfilter = new FileNameExtensionFilter("JAR files (*.jar)", "jar");
			fileChooser.addChoosableFileFilter(JARfilter);
			fileChooser.setFileFilter(JARfilter);
			fileChooser.setAcceptAllFileFilterUsed(false);

			int ret = fileChooser.showDialog(null, "Open file");

			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println(file);
			}
		}

	}

	private class loadAlgorithmHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter JARfilter = new FileNameExtensionFilter("JAR files (*.jar)", "jar");
			fileChooser.addChoosableFileFilter(JARfilter);
			fileChooser.setFileFilter(JARfilter);
			fileChooser.setAcceptAllFileFilterUsed(false);

			int ret = fileChooser.showDialog(null, "Open file");

			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println(file);
			}
		}

	}
}
