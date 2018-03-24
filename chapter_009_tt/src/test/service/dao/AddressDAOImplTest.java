package service.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DBConnectionPool;
import service.dao.models.Address;
import service.rep.CitiesRep;

import static org.junit.jupiter.api.Assertions.*;

class AddressDAOImplTest {
    private AddressDAOImpl addressDAO = new AddressDAOImpl();
    private CitiesRep citiesRep = new CitiesRep();

    @BeforeEach
    private void setUp() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
    }

    @AfterAll
    private static void close() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
        db.close();
    }

    @Test
    void createAndRead() {
        Address address = new Address();
        this.setAddress(address);
        int id = addressDAO.create(address);
        assertNotEquals(-1, id);
        address.setId(id);
        Address testAddress = addressDAO.read(id);
        int cityID = citiesRep.getCityId(address.getCountry(), address.getCity());
        Address testAddress2 = addressDAO.readAddressModel(cityID, address.getStreet(), address.getHome(), address.getApart());
        assertEquals(address, testAddress);
        assertEquals(address, testAddress2);
    }

    @Test
    void update() {
        Address address = new Address();
        this.setAddress(address);
        int id = addressDAO.create(address);
        assertNotEquals(-1, id);
        address.setId(id);
        address.setCountry("Россия");
        address.setCity("Тула");
        address.setStreet("Тест");
        address.setHome(0);
        address.setApart(0);
        addressDAO.update(address);
        assertEquals(address, addressDAO.read(id));
    }

    @Test
    void delete() {
        Address address = new Address();
        this.setAddress(address);
        int id = addressDAO.create(address);
        assertNotEquals(-1, id);
        assertNotNull(addressDAO.read(id));
        addressDAO.delete(id);
        assertNull(addressDAO.read(id));
    }

    private void setAddress(Address address) {
        address.setCountry("Россия");
        address.setCity("Москва");
        address.setStreet("Гагарина");
        address.setHome(1);
        address.setApart(1);
    }

}