import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class ScanTable {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();
        try (Connection connection = ConnectionFactory.createConnection(configuration)) {
            try (Table table = connection.getTable(TableName.valueOf("test"))) {
                Scan scan = new Scan();
                scan.addColumn("grades".getBytes(), "algebra".getBytes());
                scan.getFamilyMap();
                try (ResultScanner results = table.getScanner(scan)) {
                    for (Result result : results)
                        for (Cell cell : result.rawCells())
                            System.out.println("Row: " +
                                    Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength()) +
                                    " Column: " +
                                    Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) +
                                    ":" +
                                    Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()) +
                                    " Value: " +
                                    Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                }
            }
        }
    }
}
