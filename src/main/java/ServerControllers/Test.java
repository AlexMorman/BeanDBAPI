package ServerControllers;

import DB.DBAccess;
import DataStructures.Bean;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        DBAccess dbAccess = new DBAccess();

        Bean bean = new Bean(1, "green", 5000, 5);
        //dbAccess.insert(bean);

        System.out.println(dbAccess.queryByID(1));
        //System.out.println(dbAccess.queryAll());
    }
}
