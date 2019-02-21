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
        s = new Supervisor(5852);
        new RequestAddUser("Jooj", "ah").execute(s);
        new RequestAddUser("jefa", "ah").execute(s);

    }

    @Test
    public void authenticateExistingUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "ah");
        r.execute(s);
        assert (s.getUserList().isAuthenticate("Jooj"));
    }

    @Test
    public void authenticateUnknowLoginUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "ah");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Joefaj"));
    }

    @Test
    public void authenticateWrongPasswordUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "aha");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Jooj"));
    }
    @Test
    public void authenticateUnknowUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "aheaf");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Joefaj"));
    }

    @Test
    public void dontAuthenticateWrongUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "jefa", "ah");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Jooj"));
    }
}
