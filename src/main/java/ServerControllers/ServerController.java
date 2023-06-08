package ServerControllers;

import DB.DBAccess;
import DataStructures.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController
{
    public DBAccess dbAccess;

    public ServerController()
    {
        try
        {
            dbAccess = new DBAccess();
        }
        catch (Exception e) {}
    }

    @GetMapping("/ping")
    public String ping()
    {
        return "200";
    }

    @PostMapping("/insertBean")
    public void insertBean(@RequestBody Bean bean) throws Exception {
        dbAccess.insert(bean);
    }

    @GetMapping("/queryBeans")
    public String queryBeans()
    {
        return dbAccess.queryAll();
    }

    @GetMapping("/queryBeanByID")
    public String queryBeanByID(@RequestBody int id)
    {
        return dbAccess.queryByID(id);
    }
}
