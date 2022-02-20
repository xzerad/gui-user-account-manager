import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Auth extends JFrame implements ActionListener {
	JPasswordField pass;
	boolean test;
	Auth(){
		test = false;
		setTitle("Authentication");
		setSize(500, 130);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		JLabel lab = new JLabel("Password");
		lab.setBorder(new EmptyBorder(0, 0, 0, 100));
		add(lab);
		pass = new JPasswordField("", 16);
		pass.setBorder(new EmptyBorder(0, 0, 0, 100));
		add(pass);
		JButton btn = new JButton("Submit");
		btn.setBorder(new EmptyBorder(10, 70, 10, 70));
		btn.addActionListener(this);
		add(btn);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Auth();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(pass.getText().equals("issatso")){
			test = true;
		}else {
			JOptionPane.showMessageDialog(getContentPane(), "invalid password", "Error", JOptionPane.ERROR_MESSAGE);
			test = false;
		}
	}
	public boolean isAuth(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return test;
	}

	public void destroy(){
		dispose();
	}


}
