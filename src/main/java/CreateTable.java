import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class CreateTable {

    public static void main(String[] args) throws IOException {

        /* Create a namespace, a table and add attributes */
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("code").build();
        TableName name = TableName.valueOf("code", "test");
        HTableDescriptor tableDescriptor = new HTableDescriptor(name);
        tableDescriptor.addFamily(new HColumnDescriptor("marks"));

        /* Create the connection*/
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);

        /* Instantiate the Admin class and Create the Table*/
        Admin admin = connection.getAdmin();

        try {
            admin.createNamespace(namespaceDescriptor);
            admin.createTable(tableDescriptor);

            System.out.println("Table available: " + admin.isTableAvailable(name));

        } finally {
            admin.close();
            connection.close();
        }

    }

}
