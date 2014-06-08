package com.jason.usedcar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Reseller extends User {

    private String resellerName;

    private String address;

    private int resellerType;

    protected Reseller(Builder builder) {
        super(builder);
        setResellerName(builder.resellerName);
        setResellerType(builder.resellerType);
        setAddress(builder.address);
    }

    public static class Builder extends User.Builder {

        private String resellerName;

        private int resellerType;

        private String address;

        public Builder() {}

        public Builder resellerName(String resellerName) {
            this.resellerName = resellerName;
            return this;
        }

        public Builder resellerType(int resellerType) {
            this.resellerType = resellerType;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Reseller build() {
            return new Reseller(this);
        }
    }
}
