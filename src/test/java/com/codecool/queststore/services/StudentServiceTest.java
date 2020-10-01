package com.codecool.queststore.services;

import com.codecool.queststore.dao.QuestDAO;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.dao.StudentDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    void checkingGetStudentById() {

        //Arrange
        int studentID = 1;
        StudentDAO studentDAO = Mockito.mock(StudentDAO.class);
        RewardDAO rewardDAO = Mockito.mock(RewardDAO.class);
        QuestDAO questDAO = Mockito.mock(QuestDAO.class);
        List<Reward> rewards = new ArrayList<>();
        List<Quest> quests = new ArrayList<>();
        Student student = new Student();
        Mockito.when(studentDAO.get(studentID)).thenReturn(student);
        Mockito.when(rewardDAO.getStudentRewards(student)).thenReturn(rewards);
        Mockito.when(questDAO.getStudentQuests(student)).thenReturn(quests);
        StudentService studentService = new StudentService(studentDAO, null, rewardDAO, questDAO);


        //Act
        studentService.getStudent(studentID);


        //Assert
        Assertions.assertAll(
                ()-> assertSame(rewards, student.getRewardList()),
                ()-> assertSame(quests, student.getQuestList())
        );
    }

}