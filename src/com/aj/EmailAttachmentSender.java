package com.aj;

import java.io.File;
import java.io.IOException;

import java.util.Date;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;


public class EmailAttachmentSender {

	public static void sendEmailWithAttachments(String host, String port,
			final String userName,String password, String toAddress,
			String subject, String message, String attachFiles, String ssltlsEnable)
			throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", ssltlsEnable);
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName,password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);
		String[] str = {""};
		if(toAddress.contains(",")){
			str = toAddress.split(",");
		}
		else {
				str[0] = toAddress;
		} 
		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = new InternetAddress[str.length];
		for (int i = 0; i < str.length; i++)
        {
			toAddresses[i] = new InternetAddress(str[i]);
        }
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		//if (attachFiles != null && attachFiles.length > 0) {
			//for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(attachFiles);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.out.println("");
					System.out.println("");
					System.out.println("file doesnot exist");
				}

				multipart.addBodyPart(attachPart);
			//}
		//}

		// sets the multi-part as e-mail's content

			msg.setContent(multipart);
	
			// sends the e-mail
			Transport.send(msg);

	}

	/**
	 * Test sending e-mail with attachments
	 */
	public static void main(String[] args) {
		// SMTP info
		
			String host = ""; //args 0
			String port= "";//"587";//args 1
			String mailFrom = ""; //args 2
			String password = "";
	
			// message info
			String mailTo = ""; // args 3
			String subject = "MBS Dashboard Automation Test Report";
			String message = "Please find the attached Automation Test Report";
			
			String attachFiles = ""; //= new String[1]; 
			String ssltlsEnable = "";
			try {
				
				/*String multihtml = new String(Files.readAllBytes(Paths.get(args[5])));
		        String [] htmlParts = multihtml.split("(?<=</html>)");
		        Document doc; 
		        
		        
		        
		        String msg ="";
		        for(String part : htmlParts){
		            doc = Jsoup.parse(part);
	
		            msg = msg + doc.body().outerHtml();
		           
		        }    
		        List<String> l = new ArrayList<String>();
		        l = Arrays.asList(msg.split("\n"));
				int spec = 0,failed = 0;
				for(int i =0; i< l.size();i++){
					if(l.get(i).contains("li title")){
						spec+=1;
					}
					if(l.get(i).contains("<li>Failed")){
						failed+=1;
					}
				
				}
				
				String change = "",str="";
				for(int i =0; i< l.size();i++){
					str = l.get(i).toString();
					if(str.contains("Total specs")){
						//change = str;
						
						change = str.replace("Total specs tested:", "Total specs tested:"+spec);
						l.set(i, change);
					
						//System.out.println(l.get(i));
					}
					if(str.contains("Total failed")){
						//change = str;
						change = str.replace("Total failed:", "Total failed:"+failed);
						l.set(i, change);
						//System.out.println(l.get(i));
					}
				
				}
				msg = String.join("\n", l);
				for (String s : l)
				{
				    msg += s + "\n";
				}
		        
		
	//			message = msg;

*/
				 File inputFile = new File("credentials.xml");
		         DocumentBuilderFactory dbFactory 
		            = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();
		         //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		         NodeList nList = doc.getElementsByTagName("credential");
		         System.out.println("----------------------------");
		         for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               host = eElement.getElementsByTagName("hostserver").item(0).getTextContent().toString();
		               port = eElement.getElementsByTagName("portno").item(0).getTextContent().toString();
		               mailFrom = eElement.getElementsByTagName("username").item(0).getTextContent().toString();
		               password = eElement.getElementsByTagName("password").item(0).getTextContent().toString();
		               mailTo = eElement.getElementsByTagName("toemailid").item(0).getTextContent().toString();
		               attachFiles = eElement.getElementsByTagName("pathtofile").item(0).getTextContent().toString();
		               ssltlsEnable = eElement.getElementsByTagName("ssltlsenable").item(0).getTextContent().toString();
		            }
		         }
				sendEmailWithAttachments(host, port, mailFrom, password ,mailTo,
					subject, message, attachFiles, ssltlsEnable);
				System.out.println("");
				System.out.println("");
				System.out.println("Email sent.");
				System.out.println("");
				System.out.println("");
			} catch (Exception ex) {
					
					String error = ex.toString();
					if(error.contains("Unknown SMTP")){
						System.out.println("");
						System.out.println("");
						System.out.println("Unknown SMTP HOST");
						System.out.println("");
						System.out.println("");
					}else if(error.contains("Authentication Failed")){
						System.out.println("");
						System.out.println("");
						System.out.println("Wrong user name or password");
						System.out.println("");
						System.out.println("");
					}
					System.out.println("");
					System.out.println("");
					System.out.println(ex.toString());
					System.out.println("");
					System.out.println("");
				
			}
		
	}
	 
}