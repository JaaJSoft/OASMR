package dev.jaaj.oasmr.supervisor.auth;

import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.auth.request.RequestAddUser;
import dev.jaaj.oasmr.supervisor.auth.request.RequestGetLoginList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetUsersListTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221);
        new RequestAddUser("JOOJ", "ahhh").execute(s);
        new RequestAddUser("JAAJ", "ahhha").execute(s);
    }

    @Test
    public void deleteUser() throws Exception{
        User u = new User("Jooj","ah");
        RequestGetLoginList r = new RequestGetLoginList();
        List<String> l1 = (List<String>) r.execute(s);
        List<String> l2 = new ArrayList<>();
        l2.add("JOOJ");
        l2.add("JAAJ");
        assert (l1.equals(l2));
    }


}
