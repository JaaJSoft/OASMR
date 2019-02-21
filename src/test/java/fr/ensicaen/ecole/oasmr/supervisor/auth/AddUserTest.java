package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionLoginAlreadyExisting;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import org.junit.Before;
import org.junit.Test;

public class AddUserTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5852);
        new RequestAddUser("Oui", "aeaf").execute(s);
    }

    @Test
    public void addUser() throws Exception{
        RequestAddUser r = new RequestAddUser("JOOJ", "ahhh");
        r.execute(s);
        User u = new User("JOOJ", "ahhh");
        assert (s.getUserList().authenticate(u.getLogin(), u.getPassword()));
    }

    @Test(expected = ExceptionLoginAlreadyExisting.class)
    public void addAlreadyExistingUser() throws Exception{
        RequestAddUser r = new RequestAddUser("Oui", "aeaf");
        r.execute(s);
    }


}
