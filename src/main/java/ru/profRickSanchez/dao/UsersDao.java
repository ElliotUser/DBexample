package ru.profRickSanchez.dao;

import java.util.List;

import ru.profRickSanchez.db.models.User;

//Crud - create update delete
//Data Access Object (DAO). Объект доступа к данным
public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}
