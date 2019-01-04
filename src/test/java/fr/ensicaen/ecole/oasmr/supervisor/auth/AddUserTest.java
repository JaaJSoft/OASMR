package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import org.junit.Before;
import org.junit.Test;

public class AddUserTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
    }

    @Test
    public void addUser() throws Exception{
        RequestAddUser r = new RequestAddUser("JOOJ", "ahhh");
        r.execute(s);
        assert (s.getUserList().authenticate("JOOJ", "ahhh"));
    }

}
