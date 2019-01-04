package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAuthentication;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        new RequestAddUser("Jooj", "ah");
    }

    @Test
    public void authenticateExistingUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "ah");
        r.execute(s);
        assert (s.isAuthenticated());
    }

    @Test
    public void authenticateUnknowLoginUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "ah");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
    @Test
    public void authenticateWrongPasswordUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "aha");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
    @Test
    public void authenticateUnknowUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "aheaf");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
}
