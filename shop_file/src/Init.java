import java.io.*;

public class Init {
    public void initialization(){
        String filename = "counter.txt";
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("无法创建文件");
                e.printStackTrace();
            }
        }
        filename = "users.txt";
        file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("无法创建文件");
                e.printStackTrace();
            }
        }
        filename = "commodity.txt";
        file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("无法创建文件");
                e.printStackTrace();
            }
        }
        filename = "shoppingHistory.txt";
        file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("无法创建文件");
                e.printStackTrace();
            }
        }
        filename = "shoppingCart.txt";
        file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("无法创建文件");
                e.printStackTrace();
            }
        }
        UserFunction userFunction=new UserFunction();
        FileOp fileOp=new FileOp();
        String line=fileOp.getLineUsers("admin");
        if(line==null){
            fileOp.addManager("admin","ynuinfo#777");
            fileOp.writeCounter(2);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("counter.txt"))) {
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                userFunction.RecNum=Integer.parseInt(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}
