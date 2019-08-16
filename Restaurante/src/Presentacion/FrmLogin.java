package Presentacion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import Entidad.Usuario;
import Negocio.NUsuario;

public class FrmLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLogin frame = new FrmLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 262, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JButton btnNewButton = new JButton("INICIAR SESI\u00D3N");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// showMessageDialog(null, "Correo no valido");
				Usuario usuario = new Usuario();
		        usuario.Id = 40;
		        usuario.Nombre = "Wslark";
		        usuario.Clave = "drogo@hotail.com";
		        usuario.Correo = "123";

		        System.out.println("ID -> " + Autenticacion.Login(usuario));*/
		        usuario.Correo = "lucas@hotmail.com";
		        System.out.println("Retorno agregar: "+NUsuario.Agregar(usuario));
		        System.out.println("Retorno modificar: "+NUsuario.Modificar(usuario));
		        System.out.println("Retorno eliminar: "+NUsuario.Eliminar(usuario));
		        List<Usuario> Lista = NUsuario.Listar(usuario);
		        int i = 0;
		        for(Usuario u : Lista) {
		            i++;
		            System.out.println("("+i+"): ["+u.Id+"]["+u.Nombre+"]["+u.Activo+"]");
		        }
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JLabel lblContrasea = new JLabel("Contrase\u00F1a :");
		lblContrasea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textField_1.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(29)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblContrasea).addContainerGap(141,
								Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(textField_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														181, Short.MAX_VALUE)
												.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														181, Short.MAX_VALUE)
												.addComponent(lblNewLabel, Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														181, Short.MAX_VALUE))
										.addGap(25)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(52).addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(39).addComponent(lblContrasea).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(43)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(35, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
