package com.atroshonok.dao.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

/**
 * @author Ivan Atroshonok
 *
 */
@javax.persistence.Entity
@Table(name = "users")
@Proxy(lazy = false)
public class User implements Serializable, Entity {

    private static final long serialVersionUID = -6275039642563625669L;

    private Long id;
    @Id
    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
	return id;
    }

    private Date registrDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "registrDate")
    public Date getRegistrDate() {
	return registrDate;
    }

    private String login;
    @Column(name = "login")
    public String getLogin() {
	return login;
    }

    private String password;
    @Column(name = "password")
    public String getPassword() {
	return password;
    }

    private String email;
    @Column(name = "email")
    public String getEmail() {
	return email;
    }

    private String firstname;
    @Column(name = "firstName")
    public String getFirstname() {
	return firstname;
    }

    private String lastname;
    @Column(name = "lastName")
    public String getLastname() {
	return lastname;
    }

    private String shippingAddress;
    @Column(name = "shippingAddress")
    public String getShippingAddress() {
	return shippingAddress;
    }

    private Integer age;
    @Column(name = "age")
    public Integer getAge() {
	return age;
    }

    private UserType userType;
    @Enumerated(EnumType.STRING)
    @Column(name = "userType", columnDefinition = "enum('ADMIN', 'CLIENT')")
    public UserType getUserType() {
	return userType;
    }

    private Boolean isInBlackList;
    @Column(name = "isInBlackList")
    @Type(type = "yes_no")
    public Boolean getIsInBlackList() {
	return isInBlackList;
    }

    public User() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "User [id=" + id + ", registrDate=" + registrDate + ", login=" + login + ", password=" + password
		+ ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + ", shippingAddress="
		+ shippingAddress + ", age=" + age + ", userType=" + userType + ", isInBlackList=" + isInBlackList
		+ "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((age == null) ? 0 : age.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((isInBlackList == null) ? 0 : isInBlackList.hashCode());
	result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((shippingAddress == null) ? 0 : shippingAddress.hashCode());
	result = prime * result + ((userType == null) ? 0 : userType.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	User other = (User) obj;
	if (age == null) {
	    if (other.age != null)
		return false;
	} else if (!age.equals(other.age))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (firstname == null) {
	    if (other.firstname != null)
		return false;
	} else if (!firstname.equals(other.firstname))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (isInBlackList == null) {
	    if (other.isInBlackList != null)
		return false;
	} else if (!isInBlackList.equals(other.isInBlackList))
	    return false;
	if (lastname == null) {
	    if (other.lastname != null)
		return false;
	} else if (!lastname.equals(other.lastname))
	    return false;
	if (login == null) {
	    if (other.login != null)
		return false;
	} else if (!login.equals(other.login))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (shippingAddress == null) {
	    if (other.shippingAddress != null)
		return false;
	} else if (!shippingAddress.equals(other.shippingAddress))
	    return false;
	if (userType != other.userType)
	    return false;
	return true;
    }

    /**
     * @param isInBlackList
     *            the isInBlackList to set
     */
    public void setIsInBlackList(Boolean isInBlackList) {
	this.isInBlackList = isInBlackList;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @param registrDate
     *            the registrDate to set
     */
    public void setRegistrDate(Date registrDate) {
	this.registrDate = registrDate;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
	this.login = login;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    /**
     * @param shippingAddress
     *            the shippingAddress to set
     */
    public void setShippingAddress(String shippingAddress) {
	this.shippingAddress = shippingAddress;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(Integer age) {
	this.age = age;
    }

    /**
     * @param userType
     *            the userType to set
     */
    public void setUserType(UserType userType) {
	this.userType = userType;
    }

}
