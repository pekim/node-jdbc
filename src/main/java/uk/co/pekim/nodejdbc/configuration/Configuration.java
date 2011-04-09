package uk.co.pekim.nodejdbc.configuration;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.pekim.nodejdbc.NodeJdbcException;

public class Configuration {
    private Node node;
    private Jdbc jdbc;
    private static final ObjectMapper jsonMapper;
    
    static {
        jsonMapper = new ObjectMapper();
    }
    
    public static Configuration parseJson(String json)  {
        try {
            return jsonMapper.readValue(json, Configuration.class);
        } catch (JsonParseException exception) {
            throw new NodeJdbcException("Failed to parse JSON " + json, exception);
        } catch (JsonMappingException exception) {
            throw new NodeJdbcException("Failed to parse JSON " + json, exception);
        } catch (IOException exception) {
            throw new NodeJdbcException("Failed to parse JSON " + json, exception);
        }
    }
    
    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setJdbc(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    public Jdbc getJdbc() {
        return jdbc;
    }
}
