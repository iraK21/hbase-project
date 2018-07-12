import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class UpdateTable {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();
        try (Connection connection = ConnectionFactory.createConnection(configuration)) {
            try (Table table = connection.getTable(TableName.valueOf("test"))) {

                // Change the grades
                Put put = new Put("ira".getBytes());
                put.addColumn("grades".getBytes(), "algebra".getBytes(), "25".getBytes());
                put.addColumn("grades".getBytes(), "geometry".getBytes(), "26".getBytes());

                table.put(put);
            }
        }

    }

}
/*
Scan before operation:
ROW                   COLUMN+CELL
 amrita               column=grades:algebra, timestamp=1531291862909, value=49
 amrita               column=grades:geometry, timestamp=1531291862909, value=50
 ira                  column=grades:algebra, timestamp=1531291862899, value=20
 ira                  column=grades:geometry, timestamp=1531291862899, value=22
 isha                 column=grades:algebra, timestamp=1531291862899, value=49
 isha                 column=grades:geometry, timestamp=1531291862899, value=49

Scan after operation:
ROW                   COLUMN+CELL
 amrita               column=grades:algebra, timestamp=1531291862909, value=49
 amrita               column=grades:geometry, timestamp=1531291862909, value=50
 ira                  column=grades:algebra, timestamp=1531291930846, value=25
 ira                  column=grades:geometry, timestamp=1531291930846, value=26
 isha                 column=grades:algebra, timestamp=1531291862899, value=49
 isha                 column=grades:geometry, timestamp=1531291862899, value=49\

 */
