package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionLoginAlreadyExisting;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUserLogin;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUserPassword;
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
    public void modifyUserLogin() throws Exception{
        RequestModifyUserLogin r = new RequestModifyUserLogin("JOOJ", "Jooj");
        r.execute(s);
        User newUser = new User("Jooj", "ahhh");
        assert (s.getUserList().authenticate(newUser.getLogin(), newUser.getPassword()));
    }

    @Test
    public void modifyUserPassword() throws Exception{
        RequestModifyUserPassword r = new RequestModifyUserPassword("JOOJ", "ahhh", "ah");
        r.execute(s);
        User newUser = new User("JOOJ", "ah");
        assert (s.getUserList().authenticate(newUser.getLogin(),newUser.getPassword() ));
    }

    @Test(expected = ExceptionLoginAlreadyExisting.class)
    public void existingLogin() throws Exception{
        RequestModifyUserLogin r = new RequestModifyUserLogin("JOOJ", "JAAJ");
        r.execute(s);
    }

    @Test(expected = ExceptionUserUnknown.class)
    public void unexistingOldUser() throws Exception{
        RequestModifyUserLogin r = new RequestModifyUserLogin("J55432OJ", "ULA");
        r.execute(s);
    }
    
    @Test(expected = ExceptionUserUnknown.class)
    public void modifyUserPasswordWrongPassword() throws Exception{
        RequestModifyUserPassword r = new RequestModifyUserPassword("JOOJ", "f", "ah");
        r.execute(s);

    }
}
