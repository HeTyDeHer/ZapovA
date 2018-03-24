package service.dao;

import service.dao.models.Address;

public interface AddressDAO extends AbstractDAO <Address, Integer>  {
    Address readAddressModel(int city_id, String street, int home, int apart);
}
