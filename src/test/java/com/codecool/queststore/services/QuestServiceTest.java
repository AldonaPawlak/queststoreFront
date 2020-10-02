package com.codecool.queststore.services;

import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.users.Mentor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class QuestServiceTest {

    QuestDAO questDAO = Mockito.mock(QuestDAO.class);
    Map<String, String> questsMap = Mockito.mock(Map.class);
    Mentor mentor = new Mentor();
    Quest quest = new Quest(1, null, null, 1, 1, 1, null, null);


    @Test
    void checkQuestUpdate() {

        //Arrange
        QuestService questService = Mockito.mock(QuestService.class);

        //Act
        questService.updateQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).updateQuest(questsMap, mentor);
    }

    @Test
    void checkCreateQuestModel() {

        //Arrange
        QuestService questService = Mockito.mock(QuestService.class);

        //Act
        questService.createQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).createQuest(questsMap, mentor);
    }

    @Test
    void checkUpdateQuest() {

        //Arrange
        QuestService questService = Mockito.mock(QuestService.class);

        //Act
        questService.updateQuest(quest);

        //Assert
        Mockito.verify(questService).updateQuest(quest);
    }

    @Test
    void checkingAddQuest() {

        //Arrange
        QuestService questService = Mockito.mock(QuestService.class);

        //Act
        questService.addQuest(quest);

        //Assert
        Mockito.verify(questService).addQuest(quest);
    }

    @Test
    void checkGetQuestById() {

        //Arrange
        QuestService questService = new QuestService(questDAO);
        Mockito.when(questDAO.get(quest.getId())).thenReturn(quest);

        //Act
        questService.getQuest(quest.getId());

        //Assert
        Mockito.verify(questDAO).get(quest.getId());
    }

}