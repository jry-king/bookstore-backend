package orm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CartManager")
public class CartManager extends HttpServlet{
    public CartManager()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Begin unit of work
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            // Write HTML header
            PrintWriter out = response.getWriter();
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");

            @SuppressWarnings({ "unchecked" })
            List<Cart> result = HibernateUtil.getSessionFactory()
                    .getCurrentSession().createQuery("from Cart").list();
            Iterator<Cart> it = result.iterator();
            JSONArray cartsJson = new JSONArray();
            while (it.hasNext()) {
                Cart cart = it.next();
                JSONObject cartjson = JSONObject.fromObject(cart);
                if(1 == cart.getUser())
                {
                    cartsJson.add(cartjson);
                }
            }
            out.print(cartsJson);
            out.flush();
            out.close();
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        }
        catch (Exception ex) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if ( ServletException.class.isInstance( ex ) ) {
                throw ( ServletException ) ex;
            }
            else {
                throw new ServletException( ex );
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Begin unit of work
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            // Write HTML header
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");

            String operation = request.getParameter("operation");
            if(operation.equals("add"))
            {
                String user = request.getParameter("userid");
                String book = request.getParameter("book");
                String price = request.getParameter("price");
                boolean exist = false;
                @SuppressWarnings({ "unchecked" })
                List<Cart> result = HibernateUtil.getSessionFactory()
                        .getCurrentSession().createQuery("from Cart").list();
                Iterator<Cart> it = result.iterator();
                while (it.hasNext()) {
                    Cart cart = it.next();
                    if(book.equals(cart.getBook()))
                    {
                        exist = true;
                        int number = cart.getNumber() + 1;
                        cart.setNumber(number);
                        HibernateUtil.getSessionFactory().getCurrentSession().update(cart);
                    }
                }
                if(!exist)
                {
                    Cart newcart = new Cart();
                    newcart.setUser(Integer.parseInt(user));
                    newcart.setBook(book);
                    newcart.setPrice(Double.parseDouble(price));
                    newcart.setNumber(1);
                    HibernateUtil.getSessionFactory().getCurrentSession().save(newcart);
                }
            }
            else if(operation.equals("removeall"))
            {
                HibernateUtil.getSessionFactory()
                        .getCurrentSession().createQuery("delete from Cart").executeUpdate();
            }

            out.print("success");
            out.flush();
            out.close();
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        }
        catch (Exception ex) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if ( ServletException.class.isInstance( ex ) ) {
                throw ( ServletException ) ex;
            }
            else {
                throw new ServletException( ex );
            }
        }
    }
}
