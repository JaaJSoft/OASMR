package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestDeleteUser;
import org.junit.Before;
import org.junit.Test;

public class DeleteUserTest {

    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        new RequestAddUser("Jooj", "ah").execute(s);
    }

    @Test
    public void deleteUser() throws Exception{
        User u = new User("Jooj","ah");
        RequestDeleteUser r = new RequestDeleteUser( "Jooj");
        r.execute(s);
        assert (!s.getUserList().authenticate(u.getLogin(), u.getPassword()));
    }

    @Test(expected = ExceptionUserUnknown.class)
    public void deleteUnexistingUser() throws Exception{
        new RequestDeleteUser( "Jofzafeaoj").execute(s);
    }

}
