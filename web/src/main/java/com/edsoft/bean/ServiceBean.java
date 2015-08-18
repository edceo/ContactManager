package com.edsoft.bean;

import com.edsoft.ejb.BasicOperations;
import com.edsoft.escape.Escape;
import com.edsoft.modal.Contact;
import com.edsoft.util.GenerateResource;
import com.textmagic.sms.TextMagicMessageService;
import com.textmagic.sms.exception.ServiceException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created by edsoft on 8/16/15.
 */
@ManagedBean(name = "serviceBean")
@ViewScoped
public class ServiceBean {

    @EJB(beanName = "ContactEJB")
    private BasicOperations basicOperations;

    @ManagedProperty("#{userBean}")
    private UserBean user;

    @ManagedProperty("#{loginBean}")
    private LoginBean login;

    private Contact contact;

    @PostConstruct
    public void init() {
        contact = Escape.contact;
    }

    private String smsText;
    private String mailText;
    private String mailTitle;

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public LoginBean getLogin() {
        return login;
    }

    public void setLogin(LoginBean login) {
        this.login = login;
    }

    public void sendSMS() {
        String dummyPhone = user.getContact().getPhone();
        TextMagicMessageService service =
                new TextMagicMessageService("l1112035@std.yildiz.edu.tr", "123456");
        try {
            service.send(smsText, dummyPhone);
        } catch (ServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("HATA"));
        }
    }

    public void sendMail() {
        final String username = login.getUser().getEmail();
        final String password = login.getUser().getGoogleActivation();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("abc@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getContact().getEmail()));
            message.setSubject(mailTitle);
            message.setText(mailText);

            Transport.send(message);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("DONE"));

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteContact() {
        if (!contact.getPhoto().equals("placeholder.png")) {
            deleteFile();
        }
        basicOperations.deleteOperation(contact.getId());
        user.getUser().getContactList().clear();
        user.getUser().getContactList().addAll(basicOperations.readOperation());
        return "user.xhtml?faces-redirect=true";
    }

    public String updateContact() {
        contact.setAddress(user.getContact().getAddress());
        contact.setEmail(user.getContact().getEmail());
        contact.setNameSurname(user.getContact().getNameSurname());
        contact.setPhone(user.getContact().getPhone());
        basicOperations.updateOperation(contact);
        user.getUser().getContactList().clear();
        user.getUser().getContactList().addAll(basicOperations.readOperation());
        return "user.xhtml?faces-redirect=true";
    }

    private void deleteFile() {
        File file = new File(GenerateResource.mainPath + File.separator + contact.getPhoto());
        file.delete();
    }
}
