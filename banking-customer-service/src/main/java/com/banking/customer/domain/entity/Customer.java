package com.banking.customer.domain.entity;

import com.banking.common.constant.CustomerType;
import com.banking.core.persistence.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "customers", indexes = @Index(name = "idx_customers_email", columnList = "email"))
public class Customer extends BaseEntity {

    @Column(name = "customer_number", nullable = false, unique = true, length = 20)
    private String customerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false, length = 32)
    private CustomerType customerType;

    @Column(name = "legal_name", nullable = false, length = 256)
    private String legalName;

    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "phone", length = 32)
    private String phone;

    @Column(name = "kyc_verified", nullable = false)
    private boolean kycVerified;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isKycVerified() {
        return kycVerified;
    }

    public void setKycVerified(boolean kycVerified) {
        this.kycVerified = kycVerified;
    }
}
