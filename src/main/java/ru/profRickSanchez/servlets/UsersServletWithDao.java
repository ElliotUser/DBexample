package ru.profRickSanchez.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.profRickSanchez.dao.UsersDao;
import ru.profRickSanchez.dao.Users_Dao_JDBC_Impl;
import ru.profRickSanchez.db.models.User;

public class UsersServletWithDao extends HttpServlet {
    private UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUserName = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            dataSource.setUsername(dbUserName);
            dataSource.setPassword(dbPassword);
            dataSource.setUrl(dbUrl);
            dataSource.setDriverClassName(driverClassName);

            usersDao = new Users_Dao_JDBC_Impl(dataSource);

        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = usersDao.findAll();
        req.setAttribute("usersFromServer", users);
        req.getServletContext().getRequestDispatcher("/jsp/users.jsp").forward(req,resp);
    }
}
