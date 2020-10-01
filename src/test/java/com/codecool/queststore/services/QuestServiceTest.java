package com.codecool.queststore.services;

import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.users.Mentor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class QuestServiceTest {

    QuestService questService = Mockito.mock(QuestService.class);
    Map<String, String> questsMap = Mockito.mock(Map.class);
    Mentor mentor = new Mentor();
    Quest quest = new Quest();

    @Test
    void checkQuestUpdate() {

        //Arrange
        //Everything prepared up

        //Act
        questService.updateQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).updateQuest(questsMap, mentor);
    }

    @Test
    void checkCreateQuestModel() {

        //Arrange
        //Everything prepared up

        //Act
        questService.createQuest(questsMap, mentor);

        //Assert
        Mockito.verify(questService).createQuest(questsMap, mentor);
    }

    @Test
    void checkUpdateQuest() {

        //Arrange
        //Everything prepared up

        //Act
        questService.updateQuest(quest);

        //Assert
        Mockito.verify(questService).updateQuest(quest);
    }

    @Test
    void checkingAddQuest() {

        //Arrange
        //Everything prepared up

        //Act
        questService.addQuest(quest);

        //Assert
        Mockito.verify(questService).addQuest(quest);
    }

    @Test
    void checkGetQuestById() {

        //Arrange
        int questId = 1;
        QuestDAO questDAO = Mockito.mock(QuestDAO.class);
        Mockito.when(questDAO.get(questId)).thenReturn(quest);

        //Act
        questService.getQuest(questId);

        //Assert
        Mockito.verify(questService).getQuest(questId);
    }

}