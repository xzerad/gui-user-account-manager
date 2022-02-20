import org.jetbrains.annotations.NotNull;

import javax.imageio.IIOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FormulairePanel extends JPanel implements ActionListener {
	JComboBox<String> com;
	JCheckBox[] check;
	JCheckBox check1, check2, check3, check4;
	TitledBorder border;
	JButton btn;
	String pseudo;
	HashMap<String, String> data;
	int option_nb;
	public FormulairePanel(String ps, @NotNull HashMap<String, String> data){
		pseudo = ps;
		this.data = data;
		setLayout(new BorderLayout());
		JPanel pn = new JPanel();
		pn.setBackground(Color.GREEN);
		pn.setLayout(new FlowLayout());
		JLabel lb_bien = new JLabel("Bienvenue " + data.get("prenom"));
		lb_bien.setOpaque(true);
		lb_bien.setBackground(Color.GREEN);
		JPanel center_pan = new JPanel();
		center_pan.setLayout(new FlowLayout());
		JPanel pan_dif = new JPanel();
		pan_dif.setLayout(new FlowLayout(FlowLayout.CENTER));

		com = new JComboBox<>();
		com.addItem("Débutant");
		com.addItem("Intermédaire");
		com.addItem("avancée");
		com.setPreferredSize(new Dimension(100, 30));
		com.setSelectedIndex(1);
		com.addActionListener(this);
		pan_dif.add(com);
		pan_dif.setBorder(BorderFactory.createTitledBorder("Difficulté"));
		JPanel checkBoxGroup = new JPanel();
		JLabel cat = new JLabel("Choisir la/les catégorie(s):");
		checkBoxGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
		check = new JCheckBox[6];
		for(int i = 0; i<check.length; i++){
			check[i] = new JCheckBox(String.valueOf(i+1));
		}
		checkBoxGroup.add(cat);
		for (JCheckBox jCheckBox : check) {
			checkBoxGroup.add(jCheckBox);
		}
		pan_dif.add(checkBoxGroup);
		select_int();
		pan_dif.setPreferredSize(new Dimension(450, 100));
		center_pan.add(pan_dif);

		check1 = new JCheckBox("Emettre son");
		check1.addActionListener(this);
		check2 = new JCheckBox("Afficher score");
		check2.addActionListener(this);
		check3 = new JCheckBox("Plein ecran");
		check3.addActionListener(this);
		check4 = new JCheckBox("Ombre");
		check4.addActionListener(this);

		JPanel pan_b = new JPanel();
		pan_b.add(check1);
		pan_b.add(check2);
		pan_b.add(check3);
		pan_b.add(check4);
		border = BorderFactory.createTitledBorder("option");
		pan_b.setBorder(border);
		pan_b.setPreferredSize(new Dimension(420, 70));
		center_pan.add(pan_b);

		add(center_pan);
		lb_bien.setFont(new Font("Arial", Font.BOLD, 36));
		pn.add(lb_bien);
		add(pn, BorderLayout.NORTH);
		btn = new JButton("Valider");
		btn.addActionListener(this);
		JPanel pn1 = new JPanel();
		pn1.add(btn);
		add(pn1, BorderLayout.SOUTH);

	}
	private void select_deb(){
		for(int i=0; i<check.length; i++){
			if(i == 0 || i == 1){
				check[i].setEnabled(true);
				continue;
			}
			check[i].setEnabled(false);
			check[i].setSelected(false);
		}
	}

	private void select_int(){
		for(int i=0; i<check.length; i++){
			if(i == 0 || i == 1){
				check[i].setSelected(true);
				check[i].setEnabled(false);
				continue;
			}
			if(i== 2 || i == 3){
				check[i].setEnabled(true);
				continue;
			}
			check[i].setEnabled(false);
			check[i].setSelected(false);

		}
	}

	private void select_av(){
		for(int i=0; i<check.length; i++){
			if(i == 4 || i == 5){
				check[i].setEnabled(true);
				continue;
			}
			check[i].setSelected(true);
			check[i].setEnabled(false);

		}
	}

	private void setTitleOfOption(@NotNull JCheckBox c){
		if(c.isSelected()){
			option_nb++;
		}else {
			option_nb--;
		}
		if(option_nb != 0) {
			border.setTitle("option " + option_nb);
		}else {
			border.setTitle("option");
		}
		repaint();
	}
	@Override
	public void actionPerformed(@NotNull ActionEvent e) {
		if(e.getSource() == com){
			int ind = com.getSelectedIndex();
			if(ind == 0){
				select_deb();
			}else if(ind == 1){
				select_int();
			}else{
				select_av();
			}

		}
		else if(check1 == e.getSource()){
			setTitleOfOption(check1);
		}else if(check2 == e.getSource()){
			setTitleOfOption(check2);
		}else if(check3 == e.getSource()){
			setTitleOfOption(check3);
		}else if(check4 == e.getSource()){
			setTitleOfOption(check4);
		}else if(btn == e.getSource()){
			String cat = "";
			for(JCheckBox jCheckBox : check){
				if(jCheckBox.isSelected()){
					cat += "<p>Catégorie "+jCheckBox.getText()+"</p>";
				}
			}
			String option = "";
			JCheckBox[] ch = {check1, check2, check3, check4};
			for(JCheckBox j: ch ) {
				if (j.isSelected()) {
					option += "<p>" + j.getText()+  ": true</p>";
				}else{
					option += "<p>" + j.getText()+  ": false</p>";

				}
			}
			String html = "<!Doctype>"
						  +"<html>"
					      +"<head>"
						  +"<title>"
					      +"config"
						  +"</title>"
						  +"<body style=\"margin:50px 150px\">"
						  +"<div>\n" +
							"  <fieldset>\n" +
							"    <legend>Information personnel:</legend>\n" +
							"      <p>Nom: "+data.get("nom")+"</p>\n" +
							"      <p>Prenom: "+data.get("prenom")+"</p>\n" +
							"      <p>Pseudo: "+pseudo+"</p>\n" +
							"  </fieldset>\n" +
							"  <fieldset>\n" +
							"    <legend>Configuration:</legend>\n" +
									"  <fieldset>\n" +
									"    <legend>Difficulté:"+com.getSelectedItem()+"</legend>\n" +
										cat+
									"  </fieldset>\n" +
									"  <fieldset>\n" +
									"    <legend>Option:</legend>\n" +
										option+
									"  </fieldset>\n" +
							"  </fieldset>\n" +
							"</div>"
						  +"</body>"
						  +"</html>";

			html = html.replace("é", "e&#769;");

			try{
				FileWriter fw = new FileWriter("config.html");
				fw.write( html);
				fw.close();
				JOptionPane.showMessageDialog(this, "config.html has been created");
			} catch (IOException ioException) {
				System.err.println("An error occurred");
				JOptionPane.showMessageDialog(this, "An error occurred while creating config.html", "Error", JOptionPane.ERROR_MESSAGE);
				ioException.printStackTrace();
			}
		}
	}
}
