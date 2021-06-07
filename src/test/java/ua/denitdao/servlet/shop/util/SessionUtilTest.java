package ua.denitdao.servlet.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionUtilTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession sess;

    @Test
    void When_addRequestParametersToSession() {
        Map<String, String[]> testGivenMap = new HashMap<>();
        testGivenMap.put("key", new String[]{"val1", "val2"});
        when(req.getParameterMap()).thenReturn(testGivenMap);
        Map<String, String> testResultMap = new HashMap<>();
        testResultMap.put("key", "val1");

        SessionUtil.addRequestParametersToSession(sess, req, "someName");

        verify(req).getParameterMap();
        verify(sess).setAttribute("someName", testResultMap);
    }

}