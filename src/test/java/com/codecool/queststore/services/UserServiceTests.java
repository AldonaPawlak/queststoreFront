package com.codecool.queststore.services;

import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.models.Session;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTests {

    @Test
    void checkUserSessionID() {

        //Arrange
        Session session = new Session("9395885b-d27e-489f-8a3a-363aa7d93603", 1);
        UserPostgreSQLDAO userPostgreSQLDAO = Mockito.mock(UserPostgreSQLDAO.class);
        SessionPostgreSQLDAO sessionPostgreSQLDAO = Mockito.mock(SessionPostgreSQLDAO.class);
        Mockito.when(sessionPostgreSQLDAO.getBySessionId(session.getUuid())).thenReturn(session);
        UserService userService = new UserService(userPostgreSQLDAO, sessionPostgreSQLDAO);

        //Act
       userService.getBySessionId(session.getUuid());

        //Assert
       Mockito.verify(userPostgreSQLDAO).get(session.getUserId());
    }

}
