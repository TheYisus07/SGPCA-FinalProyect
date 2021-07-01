/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.DiscussionDAO;
import domain.Discussion;
import java.time.LocalDateTime;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Domínguez García
 */
public class DiscussionDAOTest {
    
    @Test
    public void testMeetingByRegisterDiscussion() throws BusinessException {

        DiscussionDAO discussionDAO = new DiscussionDAO();
        int discussionResult;        
        Discussion discussionObject = new Discussion(10, 1, 1, "Terminar DAO's", "Antonio de Jesús Domínguez García");
        discussionResult = discussionDAO.registerDiscussion(discussionObject);
        org.junit.Assert.assertEquals(1, discussionResult);
        
    }
    
    @Test
    public void testMeetingByUpdateDiscussion() throws BusinessException {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        String time = hour + " : " + minute;
        DiscussionDAO discussionDAO = new DiscussionDAO();
        int discussionResult;        
        Discussion discussionObject = new Discussion(20, 2, 1, "Terminar proyecto", "Antonio de Jesús Domínguez García", time, time);
        discussionResult = discussionDAO.registerDiscussion(discussionObject);
        org.junit.Assert.assertEquals(1, discussionResult);
        
    }
    
    @Test
    public void testMeetingByConsultDiscussions() throws BusinessException {
        
        DiscussionDAO discussionDAO = new DiscussionDAO();
        ArrayList<Discussion> arrayListDiscussion;
        arrayListDiscussion = discussionDAO.consultDiscussions(1);
        Assert.assertFalse(arrayListDiscussion.isEmpty());
        
    }
    
     @Test
    public void testMeetingByConsultDiscussionsUpdated() throws BusinessException {
        
        DiscussionDAO discussionDAO = new DiscussionDAO();
        ArrayList<Discussion> arrayListDiscussion;
        arrayListDiscussion = discussionDAO.consultDiscussions(1);
        Assert.assertFalse(arrayListDiscussion.isEmpty());
        
    }
    
    
    @Test
    public void testMeetingByDeleteDiscussions() throws BusinessException {

        DiscussionDAO discussionDAO = new DiscussionDAO();
        int discussionResult;
        discussionResult = discussionDAO.deleteDiscussions(1);
        org.junit.Assert.assertEquals(1, discussionResult);
    }
    
}
