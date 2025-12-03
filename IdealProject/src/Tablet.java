import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Tablet extends JFrame implements ActionListener{
    JLabel label, phone,name,serial,imei;
    JTextField namefield,numberfield,serialfield,imeifield;
    JButton save;
    public Tablet(){
        label=new JLabel("User Registration");
        label.setBounds(100,10,400,50);
        label.setFont(new Font("Arial",Font.BOLD,18));
        add(label);

        // Name
        name=new JLabel("Name");
        name.setBounds(50,70,100,50);
        name.setFont(new Font("Arial",Font.BOLD,18));
        add(name);
        namefield=new JTextField();
        namefield.setBounds(50,115,150,25);
        namefield.setFont(new Font("Arial", Font.PLAIN, 14));
        add(namefield);
        // Imei
        imei=new JLabel("IMEI");
        imei.setBounds(230,70,100,50);
        imei.setFont(new Font("Arial",Font.BOLD,18));
        add(imei);
        imeifield=new JTextField();
        imeifield.setBounds(230,115,150,25);
        imeifield.setFont(new Font("Arial", Font.PLAIN, 14));
        add(imeifield);

        // serial
        serial=new JLabel("Serial");
        serial.setBounds(50,135,100,50);
        serial.setFont(new Font("Arial",Font.BOLD,18));
        add(serial);
        serialfield=new JTextField();
        serialfield.setBounds(50,180,150,25);
        serialfield.setFont(new Font("Arial", Font.PLAIN, 14));
        add(serialfield);
        // Phone
        phone=new JLabel("Phone");
        phone.setBounds(230,135,100,50);
        phone.setFont(new Font("Arial",Font.BOLD,18));
        add(phone);
        numberfield=new JTextField();
        numberfield.setBounds(230,180,150,25);
        numberfield.setFont(new Font("Arial", Font.PLAIN, 14));
        add(numberfield);
        // Button
        save=new JButton("Save");
        save.setBounds(150,230,100,30);
        save.setForeground(Color.white);
        save.setBackground(Color.gray);
        add(save);
        // Action Event
        save.addActionListener(this);
        setLocation(450, 100);
        setTitle("REGISTRATION FORM");
        setLayout(null);
        setSize(400,350);
        getContentPane().setBackground(Color.white);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public void actionPerformed(ActionEvent ae){
        
            String Name=namefield.getText();
            String Imei=imeifield.getText();
            String Serial=serialfield.getText();
            String Phone=numberfield.getText();
            if(Name.equals("")){
                JOptionPane.showMessageDialog(rootPane, "Name is Required");
            }
            else if(Imei.equals("")){
                JOptionPane.showMessageDialog(rootPane, "IMEI Number is Required");
            }
            else if(Serial.equals("")){
                JOptionPane.showMessageDialog(rootPane, "Serial Number is Required");
            }
            else if(Phone.equals("")){
                JOptionPane.showMessageDialog(rootPane, "Phone number is Required");
            }
            else{
                try{
                    Database conn=new Database();
                    String query="Insert into Room4 values('"+Name+"','"+Imei+"','"+Serial+"','"+Phone+"')";
                    conn.statem.executeUpdate(query);
                    JOptionPane.showMessageDialog(rootPane,"Data Stored Successfully");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

        }
    
    public static void main(String[] args) throws Exception {
       new Tablet();
    }
}
