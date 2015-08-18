/**
 * This class is a Contact class
 */

package com.edsoft.modal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by edsoft on 8/15/15.
 *
 * @author Yusuf ONDER
 * @version 1.0
 */
@Entity
@Table(name = "CONTACT")
@NamedQueries({
        @NamedQuery(name = "Contact.findAll", query = "select c from Contact c"),
        @NamedQuery(name = "Contact.findById", query = "select c from Contact c where c.id =:id"),
        @NamedQuery(name = "Contact.findByEmail", query = "select c from Contact c where c.email =:email")
})
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_surname")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private String nameSurname;

    @Column(name = "phone")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private String phone;

    @Column(name = "address")
    @Basic(fetch = FetchType.EAGER)
    private String address;

    @Column(name = "email")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private String email;

    @Column(name = "create_date")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "last_modifier")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date lastModifier;

    @Column(name = "is_active")
    @Basic(fetch = FetchType.EAGER)
    @NotNull
    private Boolean isActive;

    @Column(name = "photo")
    @Basic(fetch = FetchType.EAGER)
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Contact() {

    }

    /**
     * Required field Constructor
     *
     * @param nameSurname
     * @param phone
     * @param email
     * @param createdDate
     * @param lastModifier
     * @param isActive
     */
    public Contact(String nameSurname, String phone, String email, Date createdDate, Date lastModifier, Boolean isActive) {
        this.nameSurname = nameSurname;
        this.phone = phone;
        this.email = email;
        this.createdDate = createdDate;
        this.lastModifier = lastModifier;
        this.isActive = isActive;
    }

    /**
     * All field constructor
     *
     * @param nameSurname
     * @param phone
     * @param address
     * @param email
     * @param createdDate
     * @param lastModifier
     * @param isActive
     * @param photo
     * @param user
     */
    public Contact(String nameSurname, String phone, String address, String email, Date createdDate, Date lastModifier, Boolean isActive, String photo, User user) {
        this.nameSurname = nameSurname;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.createdDate = createdDate;
        this.lastModifier = lastModifier;
        this.isActive = isActive;
        this.photo = photo;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(Date lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
