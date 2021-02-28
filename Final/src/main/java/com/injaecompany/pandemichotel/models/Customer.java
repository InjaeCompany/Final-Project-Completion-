/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.injaecompany.pandemichotel.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String firstName;
	
	private String lastName;
          
          private String age;
        
//        private String gender;
//	
//	private LocalDate dob;
//        
//        private LocalDate dob1;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
          
          public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//        
//        public LocalDate getDob() {
//		return dob;
//	}
//
//	public void setDob(LocalDate dob) {
//		this.dob = dob;
//	}
//        
//        public LocalDate getDob1() {
//		return dob1;
//	}
//
//	public void setDob1(LocalDate dob1) {
//		this.dob1 = dob1;
//	}
	

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age /*+ ", dob=" + dob + ", dob1=" + dob1 + */ + "]";
	}

	
}
