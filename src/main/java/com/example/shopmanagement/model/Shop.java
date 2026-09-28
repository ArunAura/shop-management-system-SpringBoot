package com.example.shopmanagement.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Enter Owner name")
    private String ownername;
    @NotBlank(message = "Enter Shop type")
    private String shoptype;
    @NotBlank(message = "Enter shop address")
    private String adress;
    @NotBlank(message = "enter Country")
    private String country;


    public  Shop(){

    }



    public Shop(long id, String ownername, String shoptype, String adress, String country) {
        this.id = id;
        this.ownername = ownername;
        this.shoptype = shoptype;
        this.adress = adress;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
