package dev.jaaj.oasmr.supervisor.auth;

import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import dev.jaaj.oasmr.supervisor.auth.request.RequestAddUser;
import dev.jaaj.oasmr.supervisor.auth.request.RequestGetAdmin;
import dev.jaaj.oasmr.supervisor.auth.request.RequestSetAdmin;
import org.junit.Before;
import org.junit.Test;

public class RequestAdminTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception{
        s = new Supervisor(5221);
        new RequestAddUser("JOOJ", "ahhh").execute(s);
        new RequestAddUser("JAAJ", "ahhha").execute(s);
        new RequestSetAdmin("JOOJ", true).execute(s);
    }

    @Test
    public void getAdmin() throws Exception{
        RequestGetAdmin r1 = new RequestGetAdmin("JOOJ");
        RequestGetAdmin r2 = new RequestGetAdmin("JAAJ");
        boolean adminJOOJ = (boolean) r1.execute(s);
        boolean adminJAAJ = (boolean) r2.execute(s);
        System.out.println(adminJAAJ);
        System.out.println(adminJOOJ);
        assert (!adminJAAJ);
        assert (adminJOOJ);
    }


    @Test(expected = ExceptionUserUnknown.class)
    public void getAdminUnvalid() throws Exception{
        RequestGetAdmin r1 = new RequestGetAdmin("JEEJ");
        r1.execute(s);
    }

    @Test
    public void setAdmin() throws Exception{
        RequestSetAdmin r = new RequestSetAdmin("JOOJ", false);
        RequestSetAdmin r0 = new RequestSetAdmin("JAAJ", true);
        r.execute(s);
        r0.execute(s);
        RequestGetAdmin r1 = new RequestGetAdmin("JOOJ");
        RequestGetAdmin r2 = new RequestGetAdmin("JAAJ");
        boolean adminJOOJ = (boolean) r1.execute(s);
        boolean adminJAAJ = (boolean) r2.execute(s);

        assert (adminJAAJ);
        assert (!adminJOOJ);
    }


    @Test(expected = ExceptionUserUnknown.class)
    public void setAdminUnvalid() throws Exception{
        RequestSetAdmin r1 = new RequestSetAdmin("JEEJ", true);
        r1.execute(s);
    }



}
