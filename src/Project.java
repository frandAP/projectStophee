import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Project extends JFrame implements ActionListener,MouseListener{
		
		Connect connection = new Connect();	
	
		 JInternalFrame Buyproduct = new JInternalFrame("Buy Menu", true,true,true,true);
		 JInternalFrame TransactionInternalFrame = new JInternalFrame("Transaction Menu", true,true,true,true);
		 JInternalFrame ManageProductInternalFrame = new JInternalFrame("ManageProduct", true,true,true,true);
		 JInternalFrame ManageProductTypeInternalFrame = new JInternalFrame("ManageProductType", true,true,true,true);
		 
		//VariableNO123
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		JTextField Email, Name, Email2, PhoneNumber;
		JButton LoginButton, CreateAccount, Submit;
		JPasswordField Password, Password2;
		ButtonGroup group1;
		JRadioButton Male,Female;
		JMenuItem Logout, Logout2, Product, ProductType;
		JMenu Transaction, Buy;
		JFrame frame1, frame2, Menu, AdminMenu;

		//VARIABLE NO 4
		JLabel title,qtyLbl,paymentLbl,productLbl,cartLbl;
		JRadioButton cashRadio,debitRadio;
		JButton addCart,checkout;
		JSpinner qtySpinner;	
		JPanel paymentPanel,midPanel,downPanel;
		JTable pTable,cTable;
		DefaultTableModel productDtm,cartDtm;
		JScrollPane pScroll,cScroll;
		ButtonGroup paymentGroup;
		
		//VARIABLE NO 5
		JLabel jLabel2,jLabel1,jLabel3,jLabel4;
        JScrollPane jScrollPane1,jScrollPane2;
        JTable tblDetail,tblHeader;
        JTextField txtTotal;
        JButton btnCheck;
        DefaultTableModel DftTblModel_header;

		
		//VARIABLENO6
		JFrame frameProduct = new JFrame();
		JLabel ProductName, ProductType2, ProductPrice, ProductQuantity, ID;
		JTextField PName, idField;
		JComboBox PType;
		JSpinner PriceSpinner, QuantitySpinner;
		
		JTable ProductTable;
		DefaultTableModel dtm;
		JScrollPane userTableScrollPane;
		
		JButton insertBtn, updateBtn, deleteBtn;
		JPanel buttonPanel = new JPanel();
		
		JPanel Middle = new JPanel(new BorderLayout(0,20));
		JPanel bottomPanel = new JPanel(new BorderLayout(0,20));
		JPanel formPanel = new JPanel(new GridLayout(4,2,0,5));
		JComboBox<String> cb = new JComboBox<>();
		

		
		//VARIABLENO07
		JFrame frameProductType = new JFrame();
		JLabel ManageTitle, Type7;
		JTextField ManageType, idField7;

		JTable ManageProductTable;
		DefaultTableModel dtm7;
		JScrollPane userTableScrollPane7;

		JButton insertBtn7, updateBtn7, deleteBtn7;
		JPanel buttonPanel7 = new JPanel();

		JPanel Middle7 = new JPanel(new BorderLayout(0,20));
		JPanel Inside = new JPanel(new GridLayout(1,2,0,5));
		JPanel bottomPanel7 = new JPanel(new BorderLayout(0,20));
		

	
	public Project() {
		
		No123();
		No4();
		No5();
		No6();
		No7();
		frame1.setVisible(true);
				
			
	}
	
	//OVERRIDE	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == LoginButton) {
			Login();
			
		}
		else if (e.getSource() == CreateAccount){
			frame1.setVisible(false);
			frame2.setVisible(true);
		}
		else if (e.getSource() == Submit) {
			String NameValue = Name.getText();
			String EmailValue2 = Email2.getText();
			String PasswordValue2 = new String(Password2.getPassword());
			String PhoneNumberValue = PhoneNumber.getText();
			String Role = "Member";
			String query = "SELECT * FROM users WHERE Email =?";
			String Email4 ="";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(EmailValue2);
			
			ResultSet rs = connection.executeQuery("SELECT * FROM users");
			String lastid ="";
			try {
				while(rs.next()) {
					lastid = rs.getString("Id");
				}
				rs.close();
			}catch (SQLException a) {
				a.printStackTrace();
			}
				int lastidint = Integer.parseInt(lastid);
				int lastidint2 = lastidint + 1;
				String ID2 = Integer.toString(lastidint2);
				
			try {
				PreparedStatement st = connection.prepareStatement(query); 
				st.setString(1,EmailValue2);
				ResultSet AS = st.executeQuery();
				while(AS.next()) {
				Email4 = AS.getString("Email");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			
			if (NameValue.length() <3 || NameValue.length() > 30) {
				 JOptionPane.showMessageDialog(frame2,"Name Length must be between 3-30");  
			}else if (EmailValue2.length() < 1) {
				 JOptionPane.showMessageDialog(frame2,"Email field must be filled");  
			}else if (Email4.equals(EmailValue2)) {
				 JOptionPane.showMessageDialog(frame2,"Email has been taken");  
			}else if (matcher.matches() == false) {
				JOptionPane.showMessageDialog(frame2,"Email must contain @ and ends with.com");  	
			}else if (PasswordValue2.length() < 5 || PasswordValue2.length() > 20) {
				 JOptionPane.showMessageDialog(frame2,"Password length must be between 5-20 Character or digit");  
			}else if (PhoneNumberValue == null || PhoneNumberValue.length() < 1 || PhoneNumberValue.isEmpty()) {
				JOptionPane.showMessageDialog(frame2,"Phone number cant be empty");  
			}else if (!PhoneNumberValue.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(frame2,"Phone number must be Numeric");  
			}else if (PhoneNumberValue.length() < 12 || PhoneNumberValue.length() > 15) {
				JOptionPane.showMessageDialog(frame2,"Phone length must be between 12-15 digits only");
			}else if (group1.getSelection() == null ) {
				JOptionPane.showMessageDialog(frame2,"Gender must be chosen");
			}else {
				String RadioButtonValue = group1.getSelection().getActionCommand();
				Register(ID2,NameValue, EmailValue2, PasswordValue2, PhoneNumberValue, RadioButtonValue, Role);
				JOptionPane.showMessageDialog(frame2,"Account Created");
				frame2.setVisible(false);
				frame1.setVisible(true);
			}
			
			
		}
		else if (e.getSource() == Product) {
			AdminMenu.add(ManageProductInternalFrame);
			ManageProductTypeInternalFrame.setVisible(false);
			ManageProductInternalFrame.setVisible(true);
		}
		else if (e.getSource() == ProductType) {
			AdminMenu.add(ManageProductTypeInternalFrame);
			ManageProductInternalFrame.setVisible(false);
			ManageProductTypeInternalFrame.setVisible(true);
		}
		else if(e.getSource() == Logout) {
			frame1.setVisible(true);
			Menu.setVisible(false);
		}
		else if(e.getSource() == Logout2) {
			frame1.setVisible(true);
			AdminMenu.setVisible(false);
	
		}
		else if (e.getSource() == insertBtn) {
			String Name = PName.getText();
			String Type = String.valueOf(PType.getSelectedItem());
			int Price = (int) PriceSpinner.getValue();
			int Quantity = (int) QuantitySpinner.getValue();
			
			ResultSet rs = connection.executeQuery("SELECT * FROM product");
			String lastid ="";
			try {
				while(rs.next()) {
					lastid = rs.getString("Id");
				}
				rs.close();
			}catch (SQLException a) {
				a.printStackTrace();
			}
				int lastidint = Integer.parseInt(lastid);
				int lastidint2 = lastidint + 1;
				String ID = Integer.toString(lastidint2);
				
				if (Name.length() < 5 || Name.length() > 15) {
					JOptionPane.showMessageDialog(AdminMenu,"Name length must be between 5 and 15");  
				}else if (Price == 0 ) {
					JOptionPane.showMessageDialog(AdminMenu,"Price must be more than 0");  
				}else if (Quantity == 0) {
					JOptionPane.showMessageDialog(AdminMenu,"Quantity must be more than 0");  
				}else {
					insertProduct(ID, Name, Type,  Price, Quantity);	
					JOptionPane.showMessageDialog(AdminMenu,"Product added");  
				}
			
					
			}
			else if (e.getSource() == deleteBtn) {
			String id = idField.getText();
			if (id.length() < 1 || id == null) {
				JOptionPane.showMessageDialog(AdminMenu,"Choose a product");  
				
			}else {
				deleteProduct(id);
				JOptionPane.showMessageDialog(AdminMenu,"Product deleted");
			}

			
			}
			else if (e.getSource() == updateBtn) {
				String id = idField.getText();
				String Name = PName.getText();
				String Type = String.valueOf(PType.getSelectedItem());
				int Price = (int) PriceSpinner.getValue();
				int Quantity = (int) QuantitySpinner.getValue();
				
				if (id.length() < 1 || id == null) {
					JOptionPane.showMessageDialog(frameProduct,"Choose a product");  
					
				}else if  (Name.length() < 5 || Name.length() > 15) {
					JOptionPane.showMessageDialog(AdminMenu,"Name length must be between 5 and 15");  
				}
				else if (Price == 0) {
					JOptionPane.showMessageDialog(AdminMenu,"Price must be more than 0"); 
				}
				else if (Quantity == 0) {
					JOptionPane.showMessageDialog(AdminMenu,"Quantity must be more than 0");  
				}
				else {
					updateProduct(id,  Name, Type, Price, Quantity);	
					JOptionPane.showMessageDialog(AdminMenu,"Product updated"); 
				}
			}
		
			else if (e.getSource() == insertBtn7) {
				String Type = ManageType.getText();
				ResultSet rs = connection.executeQuery("SELECT * FROM producttype");
				String lastid ="";
				try {
					while(rs.next()) {
						lastid = rs.getString("ID");
					}
					rs.close();
				}catch (SQLException a) {
					a.printStackTrace();
				}
					int lastidint = Integer.parseInt(lastid);
					int lastidint2 = lastidint + 1;
					String ID = Integer.toString(lastidint2);
					
					if (Type.length() < 3) {
						JOptionPane.showMessageDialog(AdminMenu,"Name length must be between 5 and 15");  
	  
					}else {
						insertProductType(ID, Type);	
						JOptionPane.showMessageDialog(AdminMenu,"Product added");  
					}
			}
			else if (e.getSource() == deleteBtn7) {
				String id = idField7.getText();
				if (id.length() < 1 || id == null) {
					JOptionPane.showMessageDialog(AdminMenu,"Choose a product");  
					
				}else {
					deleteProductType(id);
					JOptionPane.showMessageDialog(AdminMenu,"Product deleted");
				}
			}
			else if (e.getSource() == updateBtn7) {
				String id = idField7.getText();
				String Type = ManageType.getText();
				
				if (id.length() < 1 || id == null) {
					JOptionPane.showMessageDialog(AdminMenu,"Choose a product");  
					
				}else if (Type.length() < 3) {
					JOptionPane.showMessageDialog(AdminMenu,"Product Type length must be between 5 and 15");  
				}
				else {
					updateProductType(id,  Type);	
					JOptionPane.showMessageDialog(AdminMenu,"Product updated"); 
				}
			}
			else if (e.getSource() == addCart) {
				if (pTable.getSelectionModel().isSelectionEmpty()==true) {
				JOptionPane.showMessageDialog(null, "You must choose a row first");
				} else {
				int qty = (int) qtySpinner.getValue();
				int ProductQty = (int) pTable.getValueAt(pTable.getSelectedRow(),4);
				if (qty > ProductQty) {
					JOptionPane.showMessageDialog(null, "Not enough item to be bought");
				} else {
					insertCart();
					}
				}
			}
			else if (e.getSource() == checkout) {
			if ((cashRadio.isSelected()==false)&&(debitRadio.isSelected()==false)) {
				JOptionPane.showMessageDialog(null, "Payment type must be chosen");
			} else if (cTable.getRowCount()==0) {
				JOptionPane.showMessageDialog(null, "Cart is empty");
			}
		        else {
				int b = JOptionPane.showConfirmDialog(null, "Are you sure you want to checkout?");
				if (b == 0) {
					//update stock
					updateQty();
					
					
					
					//increment transaction ID
					ResultSet rs = connection.executeQuery("SELECT * FROM headertransaction");
					int lastid = 0;
						try {
							while(rs.next()) {
								lastid=rs.getInt("ID");
							} rs.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					int idvalue=lastid+1;
					String transID = Integer.toString(idvalue);
					
					//get user id
					int userID =0;
					String emailvalue = Email.getText();
					try {
						PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE Email = ?");
						ps.setString(1, emailvalue);
						ResultSet as = ps.executeQuery();
						while(as.next()) {
							userID = as.getInt("ID");
						} 
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					
					//insert transaction header
					int productID = 0;
					java.util.Date date=new java.util.Date();
					java.sql.Date transDate=new java.sql.Date(date.getTime());
					String payType = paymentGroup.getSelection().getActionCommand();
					insertHeaderTransaction(transID,userID,transDate,payType);
					
					//insert transaction detail
					int qty = 0;
					insertDetailTransaction(transID,productID,qty);
					
					//delete cart
					clearCart();
					JOptionPane.showMessageDialog(null, "Transaction Completed");
				}
			}
		}
		else if(e.getSource() == btnCheck) {
				int row = tblHeader.getSelectedRow();
		        if(row!=-1){
		            txtTotal.setText(String.valueOf(fetchUserData52(DftTblModel_header.getValueAt(row , 0).toString())));
		        }
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == Buy) {
			Menu.add(Buyproduct);
			Buyproduct.setVisible(true);
			TransactionInternalFrame.setVisible(false);
		}
		else if (e.getSource() == Transaction) {
			Menu.add(TransactionInternalFrame);
			Buyproduct.setVisible(false);
			TransactionInternalFrame.setVisible(true);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void No123() {
		//frame
		frame1 = new JFrame ("Stophee");
		frame1.setSize(450,450);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setLocationRelativeTo(null);
		
		//Login panel
		JPanel Login=new JPanel();  
		Login.setLayout(new GridLayout(3, 1, 0, 0));
		JLabel Title = new JLabel("LOGIN", SwingConstants.CENTER);
		Title.setFont(new Font("Serif", Font.BOLD, 30));
		Login.add(Title);
		
		
		//LoginMiddle panel
		JPanel Middle = new JPanel();
	  	Middle.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
		Middle.setLayout(new GridLayout(2,2,20,20));
		Middle.add(new JLabel("Email :"));
		Email = new JTextField();
		Middle.add(Email);
		Middle.add(new JLabel("Password :"));
		Password = new JPasswordField();
		Middle.add(Password);
		
		//Login Bottom
		JPanel Bottom = new JPanel();
		Bottom.setLayout(new GridLayout(1,2,1,10));
		Bottom.setBorder(BorderFactory.createEmptyBorder(40, 60, 60, 60));
		JPanel Bottom1 = new JPanel();
		LoginButton = new JButton("Login");
		CreateAccount = new JButton("Create Account");		
		Bottom1.add(LoginButton);
		Bottom1.add(CreateAccount);
		Bottom.add(Bottom1);
		LoginButton.addActionListener(this);
		CreateAccount.addActionListener(this);
		
		//Whole Login
		Login.add(Middle);
		Login.add(Bottom);
		
		frame1.add(Login);
		
		//fRAME Register Form
		frame2 = new JFrame ("Stophee");
		frame2.setSize(400,600);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setLocationRelativeTo(null);
		
		//Panel RegisteR Form
		JPanel Register=new JPanel();  
		Register.setLayout(new GridLayout(3, 1, 0, 0));
		JLabel Title2 = new JLabel("REGISTER", SwingConstants.CENTER);
		Title2.setFont(new Font("Serif", Font.BOLD, 25));
		Register.add(Title2);
		
		//Middle Panel
		JPanel RegisterMiddle = new JPanel();
		RegisterMiddle.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		RegisterMiddle.setLayout(new GridLayout(5,2,20,20));
		Name = new JTextField ();
		Email2 = new JTextField ();
		Password2 = new JPasswordField ();
		PhoneNumber = new JTextField ();
		RegisterMiddle.add(new JLabel("Name :"));
		RegisterMiddle.add(Name);
		RegisterMiddle.add(new JLabel("Email :"));
		RegisterMiddle.add(Email2);
		RegisterMiddle.add(new JLabel("Password :"));
		RegisterMiddle.add(Password2);
		RegisterMiddle.add(new JLabel("Phone Number :"));
		RegisterMiddle.add(PhoneNumber);
		RegisterMiddle.add(new JLabel("Gender :"));
		
		
		//Radio Button
		JPanel RadioButton = new JPanel();
		RadioButton.setLayout(new GridLayout(1,2));
		group1 = new ButtonGroup();
		Male = new JRadioButton("Male");
		Female = new JRadioButton("Female");
		Male.setActionCommand("Male");
		Female.setActionCommand("Female");
		group1.add(Male);
		group1.add(Female);
		RadioButton.add(Male);
		RadioButton.add(Female);
		RegisterMiddle.add(RadioButton);
		Male.addActionListener(this);
		Female.addActionListener(this);
		

		//Bottom Panel
		JPanel RegisterBottom = new JPanel();
		RegisterBottom.setLayout(new GridLayout(1,2,1,10));
		RegisterBottom.setBorder(BorderFactory.createEmptyBorder(80, 35, 00, 40));
		JPanel Bottom3 = new JPanel();
		Submit = new JButton("Submit");
		Bottom3.add(Submit);
		RegisterBottom.add(Bottom3);
		Submit.addActionListener(this);
		
		
		//Whole Register
		Register.add(RegisterMiddle);
		Register.add(RegisterBottom);
		
		frame2.add(Register);
		
		//MenuFrame
		Menu = new JFrame ("Stophee");
		Menu.setSize(900,600);
		Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Menu.setLocationRelativeTo(null);
		
		//UserMenu
		JMenuBar User = new JMenuBar();
		Menu.setJMenuBar(User);
		
		//MenuAccount
		JMenu Account = new JMenu("Account");
		Logout = new JMenuItem("Log Out");
		Account.add(Logout);
		User.add(Account);
		Logout.addActionListener(this);
				
		
		//MenuBuy
		Buy = new JMenu("Buy");
		User.add(Buy);
		Buy.addMouseListener(this);
				
		//MenuTransaction
		Transaction = new JMenu("Transaction");
		User.add(Transaction);
		Transaction.addMouseListener(this);
		
		//Admin Frame
		AdminMenu = new JFrame ("Stophee");
		AdminMenu.setSize(900,600);
		AdminMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AdminMenu.setLocationRelativeTo(null);
		
		//Admin Menu
		JMenuBar Admin = new JMenuBar();
		AdminMenu.setJMenuBar(Admin);
		
		//MenuAccount
		JMenu Account2 = new JMenu("Account");
		Logout2 = new JMenuItem("Log Out");
		Account2.add(Logout2);
		Admin.add(Account2);
		Logout2.addActionListener(this);
		
		//MenuManage
		JMenu Manage = new JMenu("Manage");
		Admin.add(Manage);
		Product = new JMenuItem("Product");
		ProductType = new JMenuItem("Product Type");
		 Manage.add(Product);
		 Manage.add(ProductType);
		Product.addActionListener(this);
		ProductType.addActionListener(this);
		 
		 Menu.add(Buyproduct);
		 Menu.add(TransactionInternalFrame);


		 
		 
	}
	void Login() {
		String query = "SELECT * FROM users WHERE Email =? AND Password =?";  
	    String email3 = "";
	    String role = "";
	    String password3 = "";
	    String EmailValue = Email.getText();
	    String PasswordValue = new String(Password.getPassword());
		try {
			PreparedStatement st = connection.prepareStatement(query); 
			st.setString(1,EmailValue);
			st.setString(2,PasswordValue);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				email3 = rs.getString("Email");
				role = rs.getString("Role");
				password3 = rs.getString("Password");
			}	
				if (EmailValue.length() < 1 || PasswordValue.length() < 1) {
					   JOptionPane.showMessageDialog(frame1,"Email and Password field must no be empty");  
				}else if (email3.equals(EmailValue) && password3.equals(PasswordValue)){
					if (role.equals("Admin")) {
						 frame1.setVisible(false);
						 AdminMenu.setVisible(true);
					}else {
						frame1.setVisible(false);
						Menu.setVisible(true);
					}
				}else if (!(email3.equals(EmailValue) && password3.equals(PasswordValue))){
					 JOptionPane.showMessageDialog(frame1,"Wrong Email or Password"); 
				}
				rs.close();
				st.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	void No6() {
		
		String col[] = {"ProductID", "ProductName", "ProductTypeID", "ProductPrice", "ProductQuantity"};
		dtm = new DefaultTableModel(col,0);
		ProductTable = new JTable(dtm);
		ProductTable.addMouseListener(this);
		userTableScrollPane = new JScrollPane(ProductTable);
		
		ProductName = new JLabel("Product Name:");
		ProductType2 = new JLabel("Product Type :");
		ProductPrice = new JLabel("Product Price : ");
		ProductQuantity = new JLabel("Product Quantity :");
		
		PName = new JTextField();
		idField = new JTextField();
		PType = new JComboBox();
		PriceSpinner = new JSpinner(new SpinnerNumberModel(0,0,1000000000,1));
		QuantitySpinner = new JSpinner(new SpinnerNumberModel(0,0,1000000000,1));
		
		insertBtn = new JButton("Add");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		
		buttonPanel.add(insertBtn);
		buttonPanel.add(updateBtn);
		buttonPanel.add(deleteBtn);
		
		JLabel Title = new JLabel("Manage Prodcut", SwingConstants.CENTER);
		Title.setFont(new Font("Serif", Font.BOLD, 30));
		Title.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

		formPanel.add(ProductName );
		formPanel.add(PName);
		formPanel.add(ProductType2);
		formPanel.add(PType);
		formPanel.add(ProductPrice);
		formPanel.add(PriceSpinner);
		formPanel.add(ProductQuantity);
		formPanel.add(QuantitySpinner);
		
		
		formPanel.setPreferredSize(new Dimension(100, 130));
		Middle.add(formPanel, BorderLayout.SOUTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		bottomPanel.setPreferredSize(new Dimension(0, 150));
		
		insertBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		
		Title.setBorder(BorderFactory.createEmptyBorder(10, 40, 50, 0));
		Middle.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));
		
		
		 ManageProductInternalFrame.add(Middle, BorderLayout.CENTER);
		 Middle.add(userTableScrollPane, BorderLayout.CENTER);
		 ManageProductInternalFrame.add(Title, BorderLayout.NORTH);
		 ManageProductInternalFrame.add(bottomPanel, BorderLayout.SOUTH);
		
		 
		//ComboBoxItem
		ResultSet rs = connection.executeQuery("SELECT * FROM product");
		try {
			while(rs.next()) {

				String ProductTypeName = rs.getString("ProductTypeID");
				PType.addItem(ProductTypeName);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		
		ProductTable.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				idField.setText(ProductTable.getValueAt(ProductTable.getSelectedRow(), 0). toString());
				PName.setText(ProductTable.getValueAt(ProductTable.getSelectedRow(), 1). toString());
				PType.setSelectedItem(ProductTable.getValueAt(ProductTable.getSelectedRow(), 2). toString());
				PriceSpinner.setValue(Integer.parseInt(ProductTable.getValueAt(ProductTable.getSelectedRow(), 3). toString()));
				QuantitySpinner.setValue(Integer.parseInt(ProductTable.getValueAt(ProductTable.getSelectedRow(), 4). toString()));		
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		AdminMenu.add(ManageProductInternalFrame);
		fetchUserData6();

	
		
	}
	void No7() {
		
		idField7 = new JTextField();
		frameProductType.setSize(900,600);
		frameProductType.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frameProductType.setLocationRelativeTo(null);

		String col2[] = {"ProductTypeID","ProductTypeName"};
		dtm7 = new DefaultTableModel(col2,0);
		ManageProductTable = new JTable(dtm7);
		ManageProductTable.addMouseListener(this);
		userTableScrollPane7 = new JScrollPane(ManageProductTable);
		
		Type7 = new JLabel("Type : ");
		
		ManageType = new JTextField();
		
		insertBtn7 = new JButton("Add");
		updateBtn7 = new JButton("Update");
		deleteBtn7= new JButton("Delete");
		
		buttonPanel7.add(insertBtn7);
		buttonPanel7.add(updateBtn7);
		buttonPanel7.add(deleteBtn7);
		
		ManageTitle = new JLabel("Manage Prodcut", SwingConstants.CENTER);
		ManageTitle.setFont(new Font("Serif", Font.BOLD, 30));
		ManageTitle.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
		
		Inside.add(Type7);
		Inside.add(ManageType);
		
		Middle7.add(Inside, BorderLayout.SOUTH);
		Middle7.setPreferredSize(new Dimension(100, 130));
		bottomPanel7.add(buttonPanel7, BorderLayout.SOUTH);
		bottomPanel7.setPreferredSize(new Dimension(0, 150));
		
		insertBtn7.addActionListener(this);
		updateBtn7.addActionListener(this);
		deleteBtn7.addActionListener(this);
		
		ManageTitle.setBorder(BorderFactory.createEmptyBorder(00, 40, 20, 40));		
		bottomPanel7.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
		Middle7.setBorder(BorderFactory.createEmptyBorder(0, 50, 00, 50));
		
		ManageProductTypeInternalFrame.add(Middle7, BorderLayout.CENTER);
		Middle7.add(userTableScrollPane7, BorderLayout.CENTER);
		ManageProductTypeInternalFrame.add(ManageTitle, BorderLayout.NORTH);
		ManageProductTypeInternalFrame.add(bottomPanel7, BorderLayout.SOUTH);
		
		ManageProductTable.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				idField7.setText(ManageProductTable.getValueAt(ManageProductTable.getSelectedRow(), 0). toString());
				ManageType.setText(ManageProductTable.getValueAt(ManageProductTable.getSelectedRow(), 1). toString());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		AdminMenu.add(ManageProductTypeInternalFrame);
		fetchUserData7();
		
	}
	void fetchUserData6() {

		ResultSet rs = connection.executeQuery("SELECT * FROM product");
		try {
			while(rs.next()) {
				String id = rs.getString("ID");
				String Name = rs.getString("ProductName");
				String Type = rs.getString("ProductTypeID");
				int Price = rs.getInt("ProductPrice");
				int Quantity = rs.getInt("ProductQuantity");
				String data[] = {id, Name, Type, String.valueOf(Price), String.valueOf(Quantity)};
				dtm.addRow(data);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	void fetchUserData7() {

		ResultSet rs = connection.executeQuery("SELECT * FROM producttype");
		try {
			while(rs.next()) {
				String id = rs.getString("ID");
				String typeName = rs.getString("ProductTypeName");
				String data[] = {id, typeName};
				dtm7.addRow(data);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	void deleteProduct(String id) {
		String query = "DELETE FROM product WHERE ID =" + id;
		if(connection.executeUpdate(query)) {
			dtm.setRowCount(0);
			fetchUserData6();
		}
	}
	void deleteProductType(String id) {
		String query = "DELETE FROM producttype WHERE ID =" + id;
		if(connection.executeUpdate(query)) {
			dtm7.setRowCount(0);
			fetchUserData7();
		}
	}
	void insertProduct(String ID, String Name, String Type, int Price, int Quantity ) {
		
		String query = "INSERT INTO product VALUES (?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(query); 
		try {
			ps.setString(1,ID);
			ps.setString(2,Type);
			ps.setString(3,Name);
			ps.setInt(4, Price);
			ps.setInt(5, Quantity);
			ps.executeUpdate();
			dtm.setRowCount(0);
			fetchUserData6();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void insertProductType(String ID, String Name ) {
		
		String query = "INSERT INTO producttype VALUES (?,?)";
		PreparedStatement ps = connection.prepareStatement(query); 
		try {
			ps.setString(1,ID);
			ps.setString(2,Name);
			ps.executeUpdate();
			dtm7.setRowCount(0);
			fetchUserData7();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void updateProduct(String id, String Name, String Type, int Price, int Quantity) {
		String query = "UPDATE product SET ProductName = '"+Name+"',ProductTypeID = '"+Type+"', ProductPrice = "+Price+", 	ProductQuantity = "+Quantity+" WHERE id = " + id;
		if (connection.executeUpdate(query)) {
			dtm.setRowCount(0);
			fetchUserData6();
		}
	}
	void updateProductType(String id, String Name) {
		String query = "UPDATE producttype SET ProductTypeName = '"+Name+"' WHERE ID = " + id;
		if (connection.executeUpdate(query)) {
			dtm7.setRowCount(0);
			fetchUserData7();
		}
	}
	void No4() {
		
		title = new JLabel("Buy Product", SwingConstants.CENTER);
		qtyLbl = new JLabel("Quantity"+" :");
		paymentLbl = new JLabel("Payment Type"+" :");
		productLbl = new JLabel("Product");
		cartLbl = new JLabel("Cart");
		title.setFont(new Font("Serif", Font.BOLD,30));
		
		//button
		addCart = new JButton("Add to Cart");
		checkout = new JButton("Check Out");
		addCart.addActionListener(this);
		checkout.addActionListener(this);
				
		//radioButton
		cashRadio = new JRadioButton("Cash");
		debitRadio = new JRadioButton("Debit/Credit");
		cashRadio.setHorizontalAlignment(SwingConstants.CENTER);
		debitRadio.setHorizontalAlignment(SwingConstants.CENTER);
		cashRadio.setActionCommand("Cash");
		debitRadio.setActionCommand("Non-Cash");
		
		//spinner
		SpinnerModel spinmodel = new SpinnerNumberModel(1,1,99,1);
		qtySpinner = new JSpinner(spinmodel);
		
		//table
		String [] columns = {"ProductID","ProductName","ProductType","ProductPrice","ProductQty"};
		Object data[][] = new Object[0][0];
		productDtm = new DefaultTableModel(data,columns) {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
		};
		cartDtm = new DefaultTableModel(null,columns){
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
		};
		insertTable();
		pTable = new JTable(productDtm);
		cTable = new JTable(cartDtm);
		
		
		//scroll
		pScroll = new JScrollPane(pTable);
		cScroll = new JScrollPane(cTable);
		pScroll.setPreferredSize(new Dimension(800,400));
		cScroll.setPreferredSize(new Dimension(800,400));
		
		
		//panel
		midPanel = new JPanel(new GridLayout(5,2));
		downPanel = new JPanel(new BorderLayout());
		paymentPanel = new JPanel(new GridLayout(1,2));
		
		//group
		paymentGroup = new ButtonGroup();
		paymentGroup.add(cashRadio);
		paymentGroup.add(debitRadio);
		
		paymentPanel.add(cashRadio);
		paymentPanel.add(debitRadio);
		
//		middle Panel
		midPanel.add(qtyLbl);
		midPanel.add(qtySpinner);
		midPanel.add(paymentLbl);
		midPanel.add(paymentPanel);
		midPanel.add(productLbl);
		midPanel.add(cartLbl);
		midPanel.add(pScroll);
		midPanel.add(cScroll);
		
//		down Panel
		downPanel.add(addCart, BorderLayout.WEST);
		downPanel.add(checkout, BorderLayout.EAST);
		
//		border
		midPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 0, 60));
		downPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 60, 60));
		
//		internal frame
		Buyproduct.setLayout(new BorderLayout());
		Buyproduct.add(title, BorderLayout.NORTH);
		Buyproduct.add(midPanel, BorderLayout.CENTER);
		Buyproduct.add(downPanel, BorderLayout.SOUTH);
		
	}
	void insertTable() {
		ResultSet rs = connection.executeQuery("SELECT * FROM product");
		try {
			while (rs.next()) {
				int id = rs.getInt("ID");
				String pname = rs.getString("ProductName");
				int pprice = rs.getInt("ProductPrice");
				int typeid = rs.getInt("ProductTypeID");
				String ptype = null;
				if (typeid == 1) {
					ptype = "Food";
				} else if (typeid == 2) {
					ptype = "Drinks";
				}
				int pqty = rs.getInt("ProductQuantity");
				Object data[] = {String.valueOf(id),pname,ptype,String.valueOf(pprice),pqty};
				productDtm.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void updateDB(int productID, int productQty) {
		String query = "UPDATE product SET ProductQuantity = " + productQty + " WHERE ID = "+productID;
		if (connection.executeUpdate(query)) {
			productDtm.setRowCount(0);
			insertTable();
		}
	}
	void updateQty() {
		int i = 0;
		int row = pTable.getSelectedRow();
		int idxr = cTable.getRowCount();
		while (i<idxr) {
			String ID = (String) cTable.getValueAt(i, 0);
			int productID = Integer.parseInt(ID);
			int a = (int) pTable.getValueAt(row, 4);
			int b = (int) cTable.getValueAt(i, 4);
			int productQty = a - b;
			updateDB(productID, productQty);
			i++;	
		}	
	}
	void insertCart() {
		int[] idxr =pTable.getSelectedRows();
		int spinnerValue = (int) qtySpinner.getValue();
		Object[] row = new Object[5];
		DefaultTableModel copyModel = (DefaultTableModel) cTable.getModel();
		for (int i = 0; i < idxr.length; i++) {
			row[0] = pTable.getValueAt(idxr[i], 0);
			row[1] = pTable.getValueAt(idxr[i], 1);
            row[2] = pTable.getValueAt(idxr[i], 2);
            row[3] = pTable.getValueAt(idxr[i], 3);
            row[4] = spinnerValue;
            copyModel.addRow(row);
		}
	}
	void clearCart() {
		DefaultTableModel dm = (DefaultTableModel) cTable.getModel();
		int rowCount = dm.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
	}
	void insertHeaderTransaction(String transID,int userID,Date transDate,String payType) {
		String query = "INSERT INTO headertransaction VALUES (?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(query);
		int idxr =cTable.getRowCount();
		try {
			ps.setString(1, transID);
			for (int i = 0; i < idxr; i++) {
			ps.setInt(2, userID);
			ps.setDate(3, (java.sql.Date) transDate);
			ps.setString(4, payType);
			ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	
	void insertDetailTransaction (String transID,int productID,int qty) {
		String query = "INSERT INTO detailtransaction VALUES (?,?,?)";
		PreparedStatement ps = connection.prepareStatement(query);
		int idxr =cTable.getRowCount();
		try {
			ps.setString(1, transID);
			for (int i = 0; i < idxr; i++) {
			productID = Integer.parseInt((String) cTable.getValueAt(i, 0));
			ps.setInt(2, productID);
			qty =  (int) cTable.getValueAt(i, 4);
			ps.setInt(3, qty);
			ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void No5() {

		jLabel2 = new JLabel();
        jScrollPane1 = new JScrollPane();
        tblDetail = new JTable();
        jLabel1 = new JLabel();
        jLabel3 = new JLabel();
        jScrollPane2 = new JScrollPane();
        tblHeader = new JTable();
        jLabel4 = new JLabel();
        txtTotal = new JTextField();
        btnCheck = new JButton();
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel2.setText("TRANSACTION");
        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {},
                    {},
                    {},
                    {}
                },
                new String [] {

                }
            ));
        jScrollPane1.setViewportView(tblDetail);
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Header Transaction");
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Detail Transaction");
        tblHeader.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {},
                    {},
                    {},
                    {}
                },
                new String [] {

                }
            ));
        jScrollPane2.setViewportView(tblHeader);
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Total");
        txtTotal.setEditable(false);
        btnCheck.setText("Check");
        
        btnCheck.addActionListener(this);
        
        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(TransactionInternalFrame.getContentPane());
        TransactionInternalFrame.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
                jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jInternalFrame1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(jInternalFrame1Layout.createSequentialGroup()
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel3))
                                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                    .addGap(267, 267, 267)
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnCheck))
                                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                    .addGap(290, 290, 290)
                                    .addComponent(jLabel2)))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                        .addContainerGap()))
            );
        jInternalFrame1Layout.setVerticalGroup(
                jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addGap(17, 17, 17)
                    .addComponent(jLabel1)
                    .addGap(136, 136, 136)
                    .addComponent(jLabel3)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCheck))
                    .addContainerGap(16, Short.MAX_VALUE))
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(328, Short.MAX_VALUE)))
            );
        fetchUserData51();
	}
	public void fetchUserData51() {
        DftTblModel_header = new DefaultTableModel();
        DftTblModel_header.addColumn("TransactionID");
        DftTblModel_header.addColumn("Date of Transactions");
        DftTblModel_header.addColumn("Payment Type");
        tblHeader.setModel(DftTblModel_header);
        java.sql.ResultSet res = connection.executeQuery("SELECT ID, TransactionDate, PaymentType FROM headertransaction");
        try {
            while (res.next()) {
                DftTblModel_header.addRow(new Object[]{
                    res.getString("ID"),
                    res.getString("TransactionDate"),
                    res.getString("PaymentType")
                });
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
	public int fetchUserData52(String id){
        DefaultTableModel DftTblModel_detail = new DefaultTableModel();
        DftTblModel_detail.addColumn("TransactionID");
        DftTblModel_detail.addColumn("ProductName");
        DftTblModel_detail.addColumn("ProductType");
        DftTblModel_detail.addColumn("Quantity");
        tblDetail.setModel(DftTblModel_detail);
        int total=0;
        java.sql.ResultSet res = connection.executeQuery("SELECT detailtransaction.TransactionID, product.ProductName,product.ProductPrice, producttype.ProductTypeName, detailtransaction.Quantity FROM detailtransaction LEFT JOIN product ON detailtransaction.ProductID = product.ID LEFT JOIN producttype ON product.ProductTypeID = producttype.ID WHERE detailtransaction.TransactionID = "+id);
        try {    
            while (res.next()) {
                DftTblModel_detail.addRow(new Object[]{
                    res.getString("TransactionID"),
                    res.getString("ProductName"),
                    res.getString("ProductTypeName"),
                    res.getInt("Quantity")
                });
                total+=res.getInt("Quantity")*res.getInt("ProductPrice");
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return total;
    }
	
	
	void Register(String ID2,String NameValue, String EmailValue2, String PasswordValue2, String PhoneNumberValue, String RadioButtonValue, String Role ) {
		String query = "INSERT INTO users VALUES (?,?,?,?,?,?,?)";
			PreparedStatement st = connection.prepareStatement(query); 
			try {
				st.setString(1,ID2);
				st.setString(2,NameValue);
				st.setString(3,EmailValue2);
				st.setString(4,PasswordValue2);
				st.setString(5,PhoneNumberValue);
				st.setString(6,RadioButtonValue);
				st.setString(7,Role);
				st.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

	public static void main(String[] args) {
		new Project();	

		}
	}




		
		
	


