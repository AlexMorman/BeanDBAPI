package DataStructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Bean
{
    public int id;
    public String type;
    public int mass; // mg
    public int value; // cents

    public Bean()
    {

    }

    @JsonIgnoreProperties
    public Bean(int id, String type, int mass, int value)
    {
        this.id = id;
        this.type = type;
        this.mass = mass;
        this.value = value;
    }

    /**
     * converts the Virtue into the format necessary for JDBC
     * @return
     */
    public String toString()
    {
        return "VALUES (" + id + ", '"  + type + "', " + mass + ", " + value + ");";
    }
}
