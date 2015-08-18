package com.edsoft.bean;

import com.edsoft.ejb.BasicOperations;
import com.edsoft.modal.User;
import com.edsoft.util.GenerateResource;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.transaction.SystemException;
import java.io.*;

/**
 * Created by edsoft on 8/16/15.
 */
@ManagedBean(name = "signIn")
@ViewScoped
public class SignInBean {

    private User user;
    @EJB(beanName = "UserEJB")
    private BasicOperations userOperations;

    @PostConstruct
    public void init() {
        user = new User();
        user.setEnableService(false);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String signUp() {
        User tmpUser = userOperations.findOperation(user.getUsername());
        if (null != tmpUser) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATALI KAYIT", "Böyle bir kullanıcı var"));
            return null;
        }
        initializeUser();
        if (user.getEnableService() && user.getGoogleActivation() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATALI KAYIT", "Kayıt Hatası yaptınız."));
            return null;
        }
        userOperations.createOperation(user);
        return "index.xhtml?faces-redirect=true";
    }

    private void initializeUser() {
        user.setPassword(GenerateResource.generateMD5(user.getPassword()));
        if (null == user.getPhoto()) {
            user.setPhoto("placeholder.png");
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
        user.setPhoto(filename);
        try {
            input = file.getInputstream();
            output = new FileOutputStream(new File(GenerateResource.mainPath + File.separator + user.getPhoto()));
            int reader;
            byte[] bytes = new byte[(int) file.getSize()];
            while ((reader = input.read(bytes)) != -1) {
                output.write(bytes, 0, reader);
            }
            input.close();
            output.flush();
            output.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, user.getPhoto(), e.getMessage()));

            return false;
        }

        return true;
    }

    /*public void oncapture(CaptureEvent captureEvent) {
        String filename = "notWork";
        byte[] data = captureEvent.getData();

        photo = mainPath + File.separator + filename + ".jpeg";
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(photo));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATA", "Dosya Transferinde bir sıkıntı oluştu."));
        }
    }*/
}
