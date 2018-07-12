import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PutTable {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();
        try (Connection connection = ConnectionFactory.createConnection(configuration)) {
            try (Table table = connection.getTable(TableName.valueOf("test"))) {

                Put put1 = new Put("ira".getBytes());
                put1.addColumn("grades".getBytes(), "algebra".getBytes(), "20".getBytes());
                put1.addColumn("grades".getBytes(), "geometry".getBytes(), "22".getBytes());

                Put put2 = new Put("isha".getBytes());
                put2.addColumn("grades".getBytes(), "algebra".getBytes(), "49".getBytes());
                put2.addColumn("grades".getBytes(), "geometry".getBytes(), "49".getBytes());

                Put put3 = new Put("amrita".getBytes());
                put3.addColumn("grades".getBytes(), "algebra".getBytes(), "49".getBytes());
                put3.addColumn("grades".getBytes(), "geometry".getBytes(), "50".getBytes());

                List<Put> putList = new ArrayList<>();
                putList.add(put1);
                putList.add(put2);

                table.put(putList);
                table.put(put3);
            }
        }

    }
}
