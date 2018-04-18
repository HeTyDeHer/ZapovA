package cars.dao;

import cars.models.CarOffer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class CarOfferDAO extends HibernateDAO<Integer, CarOffer> {

    public List<CarOffer> readByMake(int make_id) {
        List<CarOffer> result;
        try(Session session = DBConnection.getSession()) {
            Query query = session.createQuery("from CarOffer where model_id IN (from Model where make_id = :make_id)");
            query.setParameter("make_id", make_id);
            result = query.getResultList();
        }
        return result;
    }

    public List<CarOffer> readLastDays(int days) {
        List<CarOffer> result;
        try(Session session = DBConnection.getSession()) {
            Date date = new Date();
            date.setTime(System.currentTimeMillis() - days * 24 * 60 * 60 * 1000);
            Query query = session.createQuery("from CarOffer where created > :from");
            query.setParameter("from", date);
            result = query.getResultList();
        }
        return result;
    }




}
