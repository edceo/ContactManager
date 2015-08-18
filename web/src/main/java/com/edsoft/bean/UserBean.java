package com.edsoft.bean;

import com.edsoft.ejb.BasicOperations;
import com.edsoft.escape.Escape;
import com.edsoft.modal.Contact;
import com.edsoft.modal.User;
import com.edsoft.util.GenerateResource;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by edsoft on 8/17/15.
 */
@ManagedBean
@ViewScoped
public class UserBean {
    private Contact contact;
    private User user;


    @EJB(beanName = "ContactEJB")
    private BasicOperations basicOperations;

    @ManagedProperty("#{loginBean}")
    private LoginBean login;

    @PostConstruct
    public void init() {
        user = Escape.user;
        contact = new Contact();
        Map<String, String> parameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameters.get("contact") != null) {
            contact = basicOperations.readOperation(Integer.parseInt(parameters.get("contact")));
            Escape.contact = contact;
        }

    }

    public LoginBean getLogin() {
        return login;
    }

    public void setLogin(LoginBean login) {
        this.login = login;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addContact() {
        Contact con = basicOperations.findOperation(contact.getEmail());
        if (null != con) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BU KİŞİ VAR"));
            return;
        }
        contact.setCreatedDate(new Date());
        contact.setIsActive(true);
        contact.setLastModifier(new Date());
        contact.setUser(login.getUser());
        initializeUser();
        basicOperations.createOperation(contact);
        Escape.user.getContactList().add(contact);
    }


    private void initializeUser() {
        if (null == contact.getPhoto()) {
            contact.setPhoto("placeholder.png");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (null == event.getFile()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATA", "Dosya Seçin"));
            return;
        }
        if (!transferFile(event.getFile())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATA", "Dosya yüklenirken hata oluştu"));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BAŞARILI", "Dosya yükleme işi tamamlandı"));
    }

    private boolean transferFile(UploadedFile file) {
        OutputStream output;
        InputStream input;
        String filename = file.getFileName();
        contact.setPhoto(login.getUser().getUsername() + "__" + filename);
        try {
            input = file.getInputstream();
            output = new FileOutputStream(new File(GenerateResource.mainPath + File.separator + contact.getPhoto()));
            int reader;
            byte[] bytes = new byte[(int) file.getSize()];
            while ((reader = input.read(bytes)) != -1) {
                output.write(bytes, 0, reader);
            }
            input.close();
            output.flush();
            output.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));

            return false;
        }

        return true;
    }


}
