package com.kg.convert.convert.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedWriter;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Document.*;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.kg.convert.convert.model.Convert;
import com.kg.convert.convert.model.User;
import com.kg.convert.convert.service.convertservice;

import org.dom4j.io.SAXReader;
import org.h2.mvstore.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
//import java.net.URLConnection;




@RestController
@RequestMapping("/convert")
@CrossOrigin(origins = "*")
public class convertcontroller  {

private static final Session PdfWriter = null;
@Autowired
  private convertservice cs;
  private JavaMailSender sender;
  @RequestMapping(value="/save")
  public ResponseEntity<?> saveFlight(@RequestBody Convert r,UriComponentsBuilder builder){
    
  //  System.out.println(r.getCategory()+" "+r.getCode()+" "+r.getOptions());
        return new ResponseEntity<>(cs.saveRegister(r),HttpStatus.OK);
  }


  @GetMapping("/getall")
  public @ResponseBody ResponseEntity<List<Convert>> all() {

      return new ResponseEntity<List<Convert>>(cs.getAll(), HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Convert getRegister(@PathVariable long id) {
      return cs.findOne(id);
  }

  @RequestMapping(value="/savemail",method=RequestMethod.POST)
  public ResponseEntity<?> saveMail(@RequestBody User register,UriComponentsBuilder builder){
    User reg=register;     
    String cat=String.valueOf(reg);
    System.out.println(cat);
    //  System.out.println(r.getCategory()+" "+r.getCode()+" "+r.getOptions());
        return new ResponseEntity<>(cat,HttpStatus.OK);
  }

@RequestMapping(value="/sendmail/{marks}", method=RequestMethod.POST)
    public ResponseEntity<?> sendmail(@PathVariable String marks,@RequestBody User register)
    {   
        System.out.println("++++++++++++++"+marks);  
        System.out.println("++++++++++++sendmail+++++++++++");
        User reg=register;           
          String toMail= reg.getEmail();
             //static String  recipientAddress[];
    
        try {
            convertcontroller javaEmail = new convertcontroller();
           
         javaEmail.sendEmail(toMail,marks); 
            System.out.println("Process Completed\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

      System. out.println("event registered successfully");
    

        return new ResponseEntity<User>(register,HttpStatus.OK);
    } 

      static String  recipientAddress[];   

    public  void sendEmail(String to,String marks)
    {
    final String username = "baraneetharan.ramasamy@kgfsl.com";
    final String password = "Barani@1234";
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.smtp.host", "webmail.kggroup.com");
    props.put("mail.smtp.port", "25");
    Session session = Session.getInstance(props,
    new javax.mail.Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(username, password);
    }
    });
    try {        
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("baraneetharan.ramasamy@kgfsl.com"));
    message.setRecipients(Message.RecipientType.TO,
    InternetAddress.parse(to));
    message.setSubject("Quiz results");
    message.setText("Dear candidate,"
    + "Your mark is"+marks);

    Transport.send(message);
    System.out.println("Done");
    }
    catch (MessagingException e) 
    {
    throw new RuntimeException(e);
    }
    }
    @RequestMapping(value = "/sendpdf", method = RequestMethod.POST)
    public ResponseEntity<?> sendpdf(@RequestBody User register){
        User reg=register;     
        String toMail= reg.getEmail();
        System.out.println(toMail);
        try {
            convertcontroller javaEmail = new convertcontroller();

         javaEmail.sendPdf(toMail); 
            System.out.println("Process Completed\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(reg);
        return new ResponseEntity<>(reg,HttpStatus.OK);
    }

    public String sendPdf( String to){

    final String username = "baraneetharan.ramasamy@kgfsl.com";
    final String password = "Barani@1234";
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.smtp.host", "webmail.kggroup.com");
    props.put("mail.smtp.port", "25");
    Session session = Session.getInstance(props,
    new javax.mail.Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(username, password);
    }
    });
    try{
        MimeMessage message = new MimeMessage(session);
        // MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        try {
            helper.setTo(to);
            helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");
            helper.setSubject("Mail From Spring Boot");
            ClassPathResource file = new ClassPathResource("/Downloads");
            helper.addAttachment("new.pdf", file);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
}
catch (MessagingException e) 
    {
    throw new RuntimeException(e);
    }
}
@RequestMapping(value="/getpdf", method=RequestMethod.POST)
public ResponseEntity<byte[]> getPDF(@RequestBody String json) {
    // convert JSON to Employee 
    Convert emp = convertSomehow(json);

    // generate the file
    PdfUtil.showHelp(emp);

    // retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
    byte[] contents = ();
    Document document = new Document();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("application/pdf"));
    String filename = "output.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
}
}

// }
