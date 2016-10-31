import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.table.*;

public class simpleq extends JFrame implements ActionListener , ItemListener , KeyListener
 {
  List dlist , tlist , clist , dalist , ctlist ;
  JButton run , q , openconnection , reset;
  String y , z , ser , port , user , pass;
  Label ld , lt , lc , lda , lct , ml, lfdn , lfdv , server , portno , username , password , xtra , developer; 
  JTextArea ta , tde , serve , portn , usernam , passwor , tafpn , tafpv;
  JScrollPane servet , portnt, usernamt, passwort , stde , sp;
  
public simpleq()
  {

JFrame fr = new JFrame();
JPanel panel = new JPanel();
Color colr = new Color(0,255,255);
panel.setBackground(colr);
panel.setLayout(new GridLayout(5, 2));

server=new Label("Server");
panel.add(server);

serve = new JTextArea();
servet = new JScrollPane(serve);
panel.add(servet);
servet.setVisible(true);

portno=new Label("Port :");
panel.add(portno);

portn = new JTextArea("3306");
portnt = new JScrollPane(portn);
panel.add(portnt);
portnt.setVisible(true);

username=new Label("User ");
panel.add(username);

usernam = new JTextArea("root");
usernamt = new JScrollPane(usernam);
panel.add(usernamt);
usernamt.setVisible(true);

password=new Label("Password :");
panel.add(password);

passwor = new JTextArea();
panel.add(passwor);
passwor.setVisible(true);

xtra=new Label();
panel.add(xtra);

openconnection=new JButton("Open Connection");
panel.add(openconnection);
openconnection.addActionListener(this);

fr.add(panel);
fr.setSize(300,200);
fr.setTitle("Open Connection");
fr.setVisible(true);


Container c = getContentPane();
c.setLayout(null);

Font a = new Font("Bell MT", Font.ITALIC,16);

lfdn=new Label("Currently You Are Using  :-  ");
lfdn.setFont(a);
add(lfdn);
lfdn.setBounds(10,10,200,20);

tafpn = new JTextArea();
add(tafpn);
tafpn.setEditable(false);
tafpn.setVisible(true);
tafpn.setBounds(230,10,200,20);

lfdv=new Label("Database Version is :-  ");
lfdv.setFont(a);
add(lfdv);
lfdv.setBounds(10,40,200,20);

tafpv = new JTextArea();
add(tafpv);
tafpv.setEditable(false);
tafpv.setVisible(true);
tafpv.setBounds(230,40,200,20);

ml=new Label("  Query Will Be Fetched And Fired On Selected Database ");
ml.setFont(a);
add(ml);
ml.setBounds(10,70,420,20);


dlist = new List();
c.add(dlist);
dlist.setBounds(10,140,150,150);
dlist.addItemListener(this);

ld=new Label("Databases");
add(ld);
ld.setBounds(10,120,70,20);

tlist = new List();
c.add(tlist);
tlist.setBounds(180,140,150,150);
tlist.addItemListener(this);

lt=new Label("Tables");
add(lt);
lt.setBounds(180,120,50,20);

clist = new List();
c.add(clist);
clist.setBounds(350,140,150,150);
clist.addItemListener(this);

lc=new Label("Columns");
add(lc);
lc.setBounds(350,120,60,20);

dalist = new List();
c.add(dalist);
dalist.setBounds(520,140,150,150);

lda=new Label("Data");
add(lda);
lda.setBounds(520,120,40,20);

ctlist = new List();
c.add(ctlist);
ctlist.setBounds(690,140,150,150);

lct=new Label("Column Type");
add(lct);
lct.setBounds(700,120,85,20);


reset=new JButton("Reset");
add(reset);
reset.setBounds(30,310,100,40);
reset.addActionListener(this);

ta = new JTextArea();
sp = new JScrollPane(ta);
c.add(sp);
sp.setVisible(true);
sp.setBounds(180,300,320,80);
ta.addKeyListener(this);
ta.requestFocus();

run=new JButton("Run Query");
add(run);
run.setBounds(520,300,100,40);
run.addActionListener(this);

q=new JButton("Quite");
add(q);
q.setBounds(630,300,80,40);
q.addActionListener(this);

tde = new JTextArea();
tde.setEditable(false);
stde = new JScrollPane(tde);
c.add(stde);
stde.setVisible(true);
stde.setBounds(250,390,120,40);

developer=new Label(" Heera babu , YGI");
add(developer);
developer.setBounds(650,400,125,20);
}


public void keyReleased(KeyEvent ke)
{
}



public void keyTyped(KeyEvent ke)
{
}

public void keyPressed(KeyEvent ke)
{
int key = ke.getKeyCode();

if(key==KeyEvent.VK_ENTER)
{
StringTokenizer st = new StringTokenizer(ta.getText() , " ");

if(st.hasMoreTokens())
  {

String ks=st.nextToken();
if(ks.equalsIgnoreCase("select") || ks.equalsIgnoreCase("select*"))
{
y=dlist.getSelectedItem();

JFrame frame = new JFrame("Result Is");
JPanel panel = new JPanel();

Vector columnNames = new Vector();
Vector data = new Vector();
JPanel p=new JPanel();

try 
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery(ta.getText());
tde.setText("Query Executed");
ResultSetMetaData md = rs.getMetaData();
int columns = md.getColumnCount();

for (int i = 1; i <= columns; i++) 
{
columnNames.addElement( md.getColumnName(i) );
}

while (rs.next()) 
{
Vector row = new Vector(columns);

for (int i = 1; i <= columns; i++)
{
row.addElement( rs.getObject(i) );
}

data.addElement( row );
}

rs.close();
stmt.close();
ta.requestFocus();
}

catch(Exception e)
{
dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();
tde.setText("Error");
dlist.add("Error :");
dlist.add("");
dlist.add("Something");
dlist.add("Went");
dlist.add("Wrong");
}


JTable table = new JTable(data, columnNames);
JScrollPane pane = new JScrollPane(table);	
panel.add(pane);
frame.add(panel);
frame.setSize(500,200);
frame.setLocation(350,200);
frame.setUndecorated(true);
frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
frame.setVisible(true);
}


else
{
y=dlist.getSelectedItem();

try
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
Statement stmt = con.createStatement();
stmt.executeUpdate(ta.getText());
ta.setText("");
tde.setText("Query Executed");

dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();

Statement stmt1=con.createStatement();
ResultSet rs=stmt1.executeQuery("show databases");

while(rs.next())
{
dlist.add(rs.getString(1));
}
dlist.select(0);
ta.requestFocus();
}

catch(Exception e)
{
dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();
tde.setText("Error");
dlist.add("Error :");
dlist.add("");
dlist.add("Something");
dlist.add("Went");
dlist.add("Wrong");
}
}


}
}
}

public void actionPerformed(ActionEvent ae)
{
String s = ae.getActionCommand();

if(s=="Open Connection" || s=="Reset" )
{
ta.requestFocus();
ser= serve.getText();
port= portn.getText();
user= usernam.getText();
pass= passwor.getText(); 

dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();
tde.setText("");

try
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port,user,pass);
String query="show databases";
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery(query);

while(rs.next())
{
dlist.add(rs.getString(1));
}
dlist.select(0);

DatabaseMetaData databaseMetaData = con.getMetaData();

String productName    = databaseMetaData.getDatabaseProductName();
String productVersion = databaseMetaData.getDatabaseProductVersion();

tafpn.setText(productName + "  Database");
tafpv.setText(productVersion);

ta.setText("");
tde.setText("");
}

catch(Exception e1)
{
System.out.println(e1);
}

}

if(s=="Quite")
{
System.exit(0);
}

if(s=="Run Query")
{

StringTokenizer st = new StringTokenizer(ta.getText() , " ");

if(st.hasMoreTokens())
  {

String bs=st.nextToken();
if(bs.equalsIgnoreCase("select") || bs.equalsIgnoreCase("select*"))
{
  
y=dlist.getSelectedItem();

JFrame frame = new JFrame("Result Is");
		JPanel panel = new JPanel();

Vector columnNames = new Vector();
Vector data = new Vector();
JPanel p=new JPanel();

try 
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery(ta.getText());

ta.setText("");

ResultSetMetaData md = rs.getMetaData();
int columns = md.getColumnCount();

for (int i = 1; i <= columns; i++) 
{
columnNames.addElement( md.getColumnName(i) );
}

while (rs.next()) 
{
Vector row = new Vector(columns);

for (int i = 1; i <= columns; i++)
{
row.addElement( rs.getObject(i) );
}

data.addElement( row );
}

rs.close();
stmt.close();
ta.requestFocus();
}

catch(Exception e)
{
dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();
tde.setText("Error");
dlist.add("Error :");
dlist.add("");
dlist.add("Something");
dlist.add("Went");
dlist.add("Wrong");
}


JTable table = new JTable(data, columnNames);

		JScrollPane pane = new JScrollPane(table);	
		panel.add(pane);
		frame.add(panel);
		frame.setSize(500,200);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setVisible(true);
}


else
{
y=dlist.getSelectedItem();

try
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
Statement stmt = con.createStatement();
stmt.executeUpdate(ta.getText());
ta.setText("");
tde.setText("Query Executed");

dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();

Statement stmt1=con.createStatement();
ResultSet rs=stmt1.executeQuery("show databases");

while(rs.next())
{
dlist.add(rs.getString(1));
}
dlist.select(0);
ta.requestFocus();
}

catch(Exception e)
{
dlist.removeAll();
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();
tde.setText("Error");
dlist.add("Error :");
dlist.add("");
dlist.add("Something");
dlist.add("Went");
dlist.add("Wrong");
}

}


}
}
}

public void itemStateChanged(ItemEvent e) 
{
Object obj = e.getSource();

if (obj == dlist)
{
tlist.removeAll();
clist.removeAll();
dalist.removeAll();
ctlist.removeAll();

y=dlist.getSelectedItem();	

try
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
String query="show tables";
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery(query);

while(rs.next())
{
tlist.add(rs.getString(1));
}

}
catch(Exception e2){}

}
  
if (obj == tlist)
{
clist.removeAll();
dalist.removeAll();
z=tlist.getSelectedItem();	

try
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
String query="select * from "+z;
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery(query);

ResultSetMetaData md = rs.getMetaData();
int colu = md.getColumnCount();

for(int i=0 ; i<colu ; i++)
{
clist.add(md.getColumnName(i+1).toString());
}

}
catch(Exception e2){}
}

if (obj == clist)
{
dalist.removeAll();
ctlist.removeAll();
String x=clist.getSelectedItem();	

try
{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+ser+":"+port+"/" + y,user,pass);
String query="select " + x + " from " + z ;
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery(query);

while(rs.next())
{
dalist.add(rs.getString(x));
}

ResultSetMetaData md = rs.getMetaData();
int colu = md.getColumnCount();

for(int i=0 ; i<colu ; i++)
{
ctlist.add(md.getColumnTypeName(i+1).toString());
}

}
catch(Exception e2){}
}

}

public static void main(String s[])
{
simpleq frame = new simpleq();

JLabel labelImage = new JLabel(new ImageIcon("image.jpg"));
labelImage.setBounds(0, 0, 870, 500);
frame.add(labelImage);

frame.setSize(880,500);
frame.setLocation(200, 100);
frame.setTitle("MySQL GUI Query Builder");
frame.setVisible(true);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}

}