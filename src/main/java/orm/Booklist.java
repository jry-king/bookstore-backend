package orm;

import org.hibernate.Session;
import java.util.*;

public class Booklist {

    public static void main(String[] args) {
        Booklist mgr = new Booklist();

        List users = mgr.listUsers();
        for (int i = 0; i < users.size(); i++) {
            Book user = (Book) users.get(i);
            System.out.println(
                    "ID: " + user.getId() + " Book: " + user.getBookname() + " Author: "
                            + user.getAuthor() + " Language: " + user.getLanguage() +
                            " Published: " + user.getPublished()
            );
        }
        HibernateUtil.getSessionFactory().close();
    }

    @SuppressWarnings("unchecked")
    private List<Book> listUsers() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Book> result = session.createQuery("from Book").list();
        session.getTransaction().commit();
        return result;
    }
}
