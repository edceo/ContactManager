package com.edsoft.bean;

import com.edsoft.ejb.BasicOperations;
import com.edsoft.escape.Escape;
import com.edsoft.modal.Contact;
import com.edsoft.modal.User;
import com.edsoft.util.GenerateResource;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import java.io.File;

/**
 * Created by edsoft on 8/16/15.
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    private String username;
    private String password;
    private User user;

    @EJB(beanName = "UserEJB")
    private BasicOperations userOperations;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String loginControl() {
        user = userOperations.findOperation(username);
        if (null == user) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATALI GİRİŞ", "Böyle bir kullanıcı yok"));
            return null;
        }

        if (!user.getPassword().equals(GenerateResource.generateMD5(password))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATALI GİRİŞ", "Böyle bir kullanıcı şifre yok"));
            return null;
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(String.valueOf(user.getId()), user);
        Escape.user = user;
        return "user.xhtml?faces-redirect=true";
    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(String.valueOf(user.getId()));
        return "index.xhtml?faces-redirect=true";
    }

    public String deleteAccount() {
        userOperations.deleteOperation(user.getId());
        deleteFiles();
        return logOut();
    }

    private void deleteFiles() {
        File file = new File(GenerateResource.mainPath + File.separator + user.getPhoto());
        file.delete();
        for (Contact contact : user.getContactList()) {
            file = new File(GenerateResource.mainPath + File.separator + contact.getPhoto());
            file.delete();
        }
    }


}
