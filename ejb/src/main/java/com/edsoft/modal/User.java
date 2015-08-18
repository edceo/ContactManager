/**
 * This class a User entity class.
 */

package com.edsoft.modal;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by edsoft on 8/15/15.
 *
 * @author Yusuf ONDER
 * @version 1.0
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findById", query = "select u from User u where u.id =:id"),
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username =:username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private String username;

    @Column(name = "password")
    @Basic(fetch = FetchType.LAZY)
    @NotNull
    private String password;

    @Column(name = "photo")
    @Basic(fetch = FetchType.EAGER)
    private String photo;

    @Column(name = "name_surname")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private String nameSurname;

    @Column(name = "email")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private String email;

    @Column(name = "google_activation")
    @Basic(fetch = FetchType.LAZY)
    private String googleActivation;

    @Column(name = "enable_service")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private Boolean enableService;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contactList;

    public User() {

    }

    /**
     * Requirement field Constructor
     *
     * @param username
     * @param password
     * @param nameSurname
     * @param email
     * @param enableService
     */
    public User(String username, String password, String nameSurname, String email, Boolean enableService) {
        this.username = username;
        this.password = password;
        this.nameSurname = nameSurname;
        this.email = email;
        this.enableService = enableService;
    }

    /**
     * All field constructor
     *
     * @param username
     * @param password
     * @param photo
     * @param nameSurname
     * @param email
     * @param googleActivation
     * @param contactList
     */
    public User(String username, String password, String photo, String nameSurname, String email, String googleActivation, List<Contact> contactList) {
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.nameSurname = nameSurname;
        this.email = email;
        this.googleActivation = googleActivation;
        this.contactList = contactList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleActivation() {
        return googleActivation;
    }

    public void setGoogleActivation(String googleActivation) {
        this.googleActivation = googleActivation;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public Boolean getEnableService() {
        return enableService;
    }

    public void setEnableService(Boolean enableService) {
        this.enableService = enableService;
    }
}
