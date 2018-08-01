package pizzaco.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private String name;
    private String street;
    private Integer number;
    private Integer postCode;
    private String municipality;
    private String phoneNumber;
    private Integer doorBell;
    private Integer floor;
    private Integer block;
    private Integer apartment;
    private Integer entrance;

    public Address() {
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "street_district")
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "number")
    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "post_code")
    public Integer getPostCode() {
        return this.postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    @Column(name = "municipality")
    public String getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "door_bell")
    public Integer getDoorBell() {
        return this.doorBell;
    }

    public void setDoorBell(Integer doorBell) {
        this.doorBell = doorBell;
    }

    @Column(name = "floor")
    public Integer getFloor() {
        return this.floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Column(name = "block")
    public Integer getBlock() {
        return this.block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    @Column(name = "apartment")
    public Integer getApartment() {
        return this.apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    @Column(name = "entrance")
    public Integer getEntrance() {
        return this.entrance;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }
}
