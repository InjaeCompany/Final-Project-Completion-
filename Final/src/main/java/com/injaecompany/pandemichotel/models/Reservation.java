package com.injaecompany.pandemichotel.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String contactNumber;
    
    private String roomNumber;
    
    private String child;
    
    private String adult;

    private LocalDate dob;

    private LocalDate dob1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
    
    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getDob1() {
        return dob1;
    }

    public void setDob1(LocalDate dob1) {
        this.dob1 = dob1;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        LocalDate localDate = LocalDate.now();
        this.createdAt = localDate;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.updatedAt = localDateTime;
    }
    
    
    @Override
    public String toString() {
        return "Reservation [id=" + id + ", contactNumber=" + contactNumber + ", roomNumber=" + roomNumber + ", child=" + child + ", adult=" + adult + ", dob=" + dob + ", dob1=" + dob1 
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

}
