import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

  public static void main(String args[]) throws IOException {

    int howMany = 20;

    ByteArrayOutputStream bout = new ByteArrayOutputStream(howMany * 4);
    DataOutputStream dout = new DataOutputStream(bout);

    int m = 13;
    int n = 15;
    int[][] map = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,1,3,3,3,1,0,0,0,1,3,3,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,1,3,3,3,1,0,0,0,1,3,3,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            dout.writeInt(map[i][j]);
        }
    }
    
    // player
    dout.writeInt(0); dout.writeInt(0);

    // 4 monster1
    dout.writeInt(4);
    dout.writeInt(4); dout.writeInt(10);
    dout.writeInt(7); dout.writeInt(6);
    dout.writeInt(10); dout.writeInt(4);
    dout.writeInt(12); dout.writeInt(0);
    
    // 3 monster2
    dout.writeInt(3);
    dout.writeInt(3); dout.writeInt(3);
    dout.writeInt(6); dout.writeInt(11);
    dout.writeInt(10); dout.writeInt(14);
    
    FileOutputStream fout = new FileOutputStream("map01.dat");
    try {
      bout.writeTo(fout);
      fout.flush();
    } finally {
      fout.close();
    }
  }
}
