package com.feng.dao;

import com.feng.entity.Address;

import java.util.List;

public interface AddressDao {
    int saveAddress(Address address);

    List<Address> findAddressByCity(String address);

    List<Address> findAddressByCity(Address address);
}
