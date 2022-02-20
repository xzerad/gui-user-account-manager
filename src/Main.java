import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Main extends JFrame {
	JPanel north_pan, pan;
	JTextField nomt, prenomt, pseudot;
	JButton valid;
	DefaultListModel<String> m;
	JTabbedPane jtp;
	JList<String> l;
	JLabel help;
	HashMap<String, Component> tab;
	HashMap<String, HashMap<String, String>> users;
	JMenuItem item_renommer, item_supp, item_supp_all;
	JLabel nom, prenom, pseudo;
	JTable t;

	JFrame f;
	public Main(){
		north_pan = new JPanel();
		north_pan.setLayout(new FlowLayout());
		users = new HashMap<>();
		tab = new HashMap<>();
		valid = new JButton("Valider");
		valid.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
			if(nomt.getText().equals("Tapper votre nom") || prenomt.getText().equals("Tapper votre prenom") ||
					pseudot.getText().equals("Tapper votre pseudo")){
				JOptionPane.showMessageDialog(getContentPane(), "invalid option");
			}else {
				boolean found = false;
				if(users.get(pseudot.getText()) != null){
						found = true;

				}
				if (! found) {
					HashMap<String, String> credential = new HashMap<>();
					credential.put("nom", nomt.getText());
					credential.put("prenom", prenomt.getText());
					users.put(pseudot.getText(), credential);
					m.addElement(pseudot.getText());
					l.setPreferredSize(new Dimension(pan.getSize().width, l.getSize().height + 18));
					if(f != null){
						t.setModel(new TableModel(users.size(), 3, getData()));
						f.revalidate();
					}
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "pseudo déjà utilise");
				}
			}
			}
		});


		JLabel astrix = new JLabel("*");
		JLabel astrix1 = new JLabel("*");
		JLabel astrix2 = new JLabel("*");
		astrix.setForeground(Color.RED);
		astrix1.setForeground(Color.RED);
		astrix2.setForeground(Color.RED);
		nomt = new JTextField("Tapper votre nom",12);
		nomt.setHorizontalAlignment(SwingConstants.CENTER);
		nomt.addFocusListener(new EcouteurFocus());
		prenomt = new JTextField("Tapper votre prenom",12);
		prenomt.setHorizontalAlignment(SwingConstants.CENTER);
		prenomt.addFocusListener(new EcouteurFocus());
		pseudot = new JTextField("Tapper votre pseudo",12);
		pseudot.setHorizontalAlignment(SwingConstants.CENTER);
		pseudot.addFocusListener(new EcouteurFocus());
		nomt.addMouseListener(new EcouteurEnter());
		prenomt.addMouseListener(new EcouteurEnter());
		pseudot.addMouseListener(new EcouteurEnter());
		nom = new JLabel("Nom");
		nom.addMouseListener(new EcouteurEnter());
		north_pan.add(nom);
		north_pan.add(nomt);
		prenom = new JLabel("Prénom");
		prenom.addMouseListener(new EcouteurEnter());
		north_pan.add(astrix);
		north_pan.add(prenom);
		north_pan.add(prenomt);
		pseudo = new JLabel("pseudo");
		pseudo.addMouseListener(new EcouteurEnter());
		north_pan.add(astrix1);
		north_pan.add(pseudo);
		north_pan.add(pseudot);
		north_pan.add(astrix2);
		north_pan.add(valid);
		add(north_pan, BorderLayout.NORTH);
		JSplitPane jsp = new JSplitPane();
		m = new DefaultListModel<>();
		l = new JList<>(m){
			public String getToolTipText(MouseEvent e){
				int ind = locationToIndex(e.getPoint());
				if(ind > -1) {
					String ps = m.getElementAt(ind);
					return users.get(ps).get("nom") + " " + users.get(ps).get("prenom");
				}
					return null;
			}
		};
		l.addMouseListener(new EcouteurList());
		l.clearSelection();
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		pan.setPreferredSize(new Dimension(250, 0));
		pan.add(l);
		JButton table_btn = new JButton("Show Table");
		table_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(f == null){
					f = new JFrame();
					f.setTitle("user's table");
					t = new JTable();
					f.add(new JScrollPane(t));
					f.setSize(300, 300);
					f.setVisible(true);
					f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					f.setLocationRelativeTo(null);
					f.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							super.windowClosing(e);
							f =null;
						}
					});
				}
				t.setModel(new TableModel(users.size(), 3, getData()));
				f.revalidate();
			}
		});
		pan.add(table_btn, BorderLayout.SOUTH);
		jsp.setLeftComponent(pan);
		jtp = new JTabbedPane();
		jsp.setRightComponent(jtp);
		add(jsp, BorderLayout.CENTER);
		help = new JLabel("Help :");
		add(help, BorderLayout.SOUTH);

		setTitle("Test Evenement");
		setVisible(true);
		setSize(750, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

	}
	public String[][] getData(){
		String[][] data;
		data = new String[users.size()][3];
		int i = 0;
		for(String k: users.keySet()){
			data[i][2] = k;
			data[i][1] = users.get(k).get("prenom");
			data[i][0] = users.get(k).get("nom");
			i += 1;
		}
		return data;
	}
	public static void main(String[] args) {
		Auth a = new Auth();
		while (true){
			if(a.isAuth()){
			a.destroy();
			break;
			}
		}
		new Main();
	}

	private class EcouteurFocus implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
			if(e.getSource() == nomt){
				if(nomt.getText().equals("Tapper votre nom")){
					nomt.setText("");
				}
			}
			if(e.getSource() == prenomt){
				if(prenomt.getText().equals("Tapper votre prenom")){
					prenomt.setText("");
				}
			}
			if(e.getSource() == pseudot){
				if(pseudot.getText().equals("Tapper votre pseudo")){
					pseudot.setText("");
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(e.getSource() == nomt){
				help.setText("Help: ");
				if(nomt.getText().equals("")){
					nomt.setText("Tapper votre nom");
				}
			}
			if(e.getSource() == prenomt){
				help.setText("Help: ");
				if(prenomt.getText().equals("")){
					prenomt.setText("Tapper votre prenom");
				}
			}
			if(e.getSource() == pseudot){
				help.setText("Help: ");
				if(pseudot.getText().equals("")){
					pseudot.setText("Tapper votre pseudo");
				}
			}
		}
	}
	private class EcouteurEnter implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}
		@Override
		public void mousePressed(MouseEvent e) {

		}
		@Override
		public void mouseReleased(MouseEvent e) {

		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() == nomt) {
				help.setText("Help: saisir votre nom");
			}else if(e.getSource() == prenomt){
				help.setText("Help: saisir votre prenom");
			}else if(e.getSource() == pseudot){
				help.setText("Help: saisir votre pseudo-nom");
			}else {
				JLabel lb = (JLabel) e.getSource();
				lb.setForeground(Color.PINK);
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if(e.getSource() == nomt){
				help.setText("Help: ");
			}
			else if(e.getSource() == prenomt){
				help.setText("Help: ");
			}
			else if(e.getSource() == pseudot){
				help.setText("Help: ");
			}else{
			JLabel lb = (JLabel) e.getSource();
			lb.setForeground(Color.BLACK);
		}}
	}
	private class EcouteurList extends MouseAdapter {
		public void mouseClicked( MouseEvent e){
			if(e.getClickCount() == 2){
				int ind = l.locationToIndex(new Point(e.getX(), e.getY()));
				if(ind>= 0){

				String ps = m.getElementAt(ind);
					if(tab.get(ps) == null){
						Component c = jtp.add(ps, new FormulairePanel(ps, users.get(ps)));
						jtp.setSelectedIndex(jtp.indexOfComponent(c));
						tab.put(ps, c);
					}else {
						jtp.setSelectedIndex(jtp.indexOfComponent(tab.get(ps)));
					}
			}
			}
			if(e.getButton() == MouseEvent.BUTTON3){

				int ind = l.getSelectedIndex();
				if(ind >= 0){
					JPopupMenu pop = new JPopupMenu();
					item_renommer = new JMenuItem("Renommer");
					item_supp = new JMenuItem("Supprimer");
					item_supp_all = new JMenuItem("Supprimer tous");
					item_renommer.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String s = JOptionPane.showInputDialog(Tp4.this, "entrer la nouvelle valeur");
							if(s != null){
							if(!s.equals("")) {
								String ps = m.getElementAt(ind);
								HashMap<String, String> dataS = users.get(ps);
								if (users.get(s) == null) {
									Component c = tab.get(ps);
									int index = jtp.indexOfComponent(c);
									if (index != -1) {
										jtp.setTitleAt(index, s);
									}
									users.remove(ps);
									tab.remove(ps);
									users.put(s, dataS);
									tab.put(s, c);
									m.setElementAt(s, ind);
									l.clearSelection();
									t.setModel(new TableModel(users.size(), 3, getData()));
									f.revalidate();
								}else {
									JOptionPane.showMessageDialog(Tp4.this.getContentPane(), "already exist","Error", JOptionPane.ERROR_MESSAGE);
								}
							}else{
								JOptionPane.showMessageDialog(Tp4.this.getContentPane(), "empty string is invalid pseudo name","Error", JOptionPane.ERROR_MESSAGE);
							}
						}}
					});
					item_supp_all.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							users.clear();
							m.clear();
							l.clearSelection();
							jtp.removeAll();
							tab.clear();
							l.setPreferredSize(new Dimension(pan.getSize().width, 0));
							t.setModel(new TableModel(0, 3, null));
							f.revalidate();
						}
					});
					item_supp.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String ps = m.getElementAt(ind);

							users.remove(ps);
							m.removeElementAt(l.getSelectedIndex());
							Component item = tab.get(ps);
							if(item != null) {
								jtp.remove(item);
							}
							l.clearSelection();
							tab.remove(ps);
							l.setPreferredSize(new Dimension(pan.getSize().width, l.getSize().height-18));
							t.setModel(new TableModel(users.size(), 3, getData()));
							f.revalidate();
						}
					});
					pop.add(item_renommer);
					pop.add(item_supp);
					pop.add(item_supp_all);
					pop.show(l, e.getX(), e.getY());
				}
			}
		}
	}
}