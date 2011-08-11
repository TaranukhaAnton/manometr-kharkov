package application.hibernate.generic;

import java.util.List;
public interface GenericDAO<T> {

    T findById(Long id);

    List<T> findAll();

 //   List<T> findByExample(T exampleInstance);

    T makePersistent(T entity);
//    T save(T entity);
//    T update(T entity);

    void delete(T entity);
}
