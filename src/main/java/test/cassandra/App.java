package test.cassandra;

import com.datastax.driver.core.*;

import java.util.Set;

public class App {
    public static void main(String ... args) {
        final Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
        final Session session = cluster.connect("test");
        final ResultSet result = session.execute("SELECT * FROM zones WHERE zoneName='foo.com'");

        System.out.println("Result:");
        for (Row row : result) {
            final String zoneName = row.getString("zoneName");
            final Set<UDTValue> records = row.getSet("records", UDTValue.class);
            System.out.println("ZoneName = " + zoneName);
            System.out.println("Records = {");
            for (UDTValue udtRecord : records) {
                System.out.println("\tRecord = {");
                final String name = udtRecord.getString("name");
                final String type = udtRecord.getString("type");
                final String clazz = udtRecord.getString("clazz");
                final String data = udtRecord.getString("data");

                System.out.println("\t\tname:  " + name);
                System.out.println("\t\ttype:  " + type);
                System.out.println("\t\tclazz: " + clazz);
                System.out.println("\t\tdata:  " + data);
                System.out.println("\t}");
            }
            System.out.println("}");
        }
    }
}
