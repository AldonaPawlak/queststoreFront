package com.codecool.queststore.dao;

import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserPostgreSQLDAOTest {

    @Test
    public void testIsUserAdd() {

        //Arrange
        UserPostgreSQLDAO userDao = mock(UserPostgreSQLDAO.class);
        User user = new Mentor();

        //Act
        userDao.add(user);

        //Assert
        verify(userDao).add(user);
    }

}