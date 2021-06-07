package ua.denitdao.servlet.shop.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContextUtilTest {

    @Mock
    private ServletContextEvent contextEvent;
    @Mock
    private HttpServletRequest req;
    @Mock
    private ServletContext context;

    @Test
    void When_createActiveUserStorage_Expect_CreatedSet() {
        when(contextEvent.getServletContext()).thenReturn(context);
        ContextUtil.createActiveUserStorage(contextEvent);

        verify(contextEvent).getServletContext();
        verify(context).setAttribute(eq(ContextUtil.ACTIVE_USERS), any(HashSet.class));
    }

    @Test
    void When_addUserToContext_Expect_AddedObject() {
        HashSet<Long> testSet = new HashSet<>();
        Long userId = 1L;
        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute(ContextUtil.ACTIVE_USERS)).thenReturn(testSet);

        ContextUtil.addUserToContext(req, userId);

        verify(req, times(2)).getServletContext();
        verify(context).getAttribute(ContextUtil.ACTIVE_USERS);
        verify(context).setAttribute(ContextUtil.ACTIVE_USERS, testSet);
        assertTrue(testSet.contains(userId));
    }

    @Test
    void When_removeUserFromContext_Expect_RemovedObject() {
        HashSet<Long> testSet = new HashSet<>();
        Long userId = 1L;
        testSet.add(userId);
        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute(ContextUtil.ACTIVE_USERS)).thenReturn(testSet);

        ContextUtil.removeUserFromContext(req, userId);

        verify(req, times(2)).getServletContext();
        verify(context).getAttribute(ContextUtil.ACTIVE_USERS);
        verify(context).setAttribute(ContextUtil.ACTIVE_USERS, testSet);
        assertFalse(testSet.contains(userId));
    }

    @Test
    void When_findUserInContextContains_Expect_True() {
        HashSet<Long> testSet = new HashSet<>();
        Long userId = 1L;
        testSet.add(userId);
        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute(ContextUtil.ACTIVE_USERS)).thenReturn(testSet);

        assertTrue(ContextUtil.findUserInContext(req, userId));

        verify(req).getServletContext();
        verify(context).getAttribute(ContextUtil.ACTIVE_USERS);
    }

    @Test
    void When_findUserInContextEmpty_Expect_False() {
        HashSet<Long> testSet = new HashSet<>();
        Long userId = 1L;
        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute(ContextUtil.ACTIVE_USERS)).thenReturn(testSet);

        assertFalse(ContextUtil.findUserInContext(req, userId));

        verify(req).getServletContext();
        verify(context).getAttribute(ContextUtil.ACTIVE_USERS);
    }
}