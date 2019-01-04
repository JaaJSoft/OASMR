package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.HashUtil;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionLoginAlreadyExisting;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUser;
import org.junit.Before;
import org.junit.Test;

public class ModifyUserTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        new RequestAddUser("JOOJ", "ahhh").execute(s);
        new RequestAddUser("JAAJ", "ahhha").execute(s);
    }

    @Test
    public void modifyUser() throws Exception{
        RequestModifyUser r = new RequestModifyUser("JOOJ", "ahhh", "Jooj", "ah");
        r.execute(s);
        User newUser = new User("Jooj", "ah");
        assert (s.getUserList().authenticate(newUser.getLogin(),newUser.getPassword() ));
    }
    @Test(expected = ExceptionLoginAlreadyExisting.class)
    public void existingLogin() throws Exception{
        RequestModifyUser r = new RequestModifyUser("JOOJ", "ahhh", "JAAJ", "ah");
        r.execute(s);
    }

    @Test(expected = ExceptionUserUnknown.class)
    public void unexistingOldUser() throws Exception{
        RequestModifyUser r = new RequestModifyUser("J55432OJ", "ahhh", "ULA", "ah");
        r.execute(s);
    }
}
