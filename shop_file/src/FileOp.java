import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileOp {

    //用户文件---------------------------------------------------
    public String getLineUsers(String account){//返回account当前行的所有数据
        String line=null;
        boolean judge=true;
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] parts=line.split("\\|");
                if(!parts[0].equals(account))
                    judge=false;
                if(judge){
                    return line;
                }
                judge=true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return null;
    }

    public int getPasswordLocation(String line){//返回当前line密码的起始位置
        for(int i=0;i<line.length();i++){
            if(line.charAt(i)=='|')
                return i+1;
        }
        return 0;
    }

    public String getPassword(String account){//返回account的密码
        String password="",line;
        line= getLineUsers(account);
        for(int i=getPasswordLocation(line);;i++){
            if(line.charAt(i)=='|')
                break;
            password=password.concat(Character.toString(line.charAt(i)));
        }
        return password;
    }

    public int getTypeLocation(String line){//返回当前line用户类型的起始位置
        for(int i=getPasswordLocation(line);;i++){
            if(line.charAt(i)=='|')
                return i+1;
        }
    }

    public char getType(String account){
        String type=null,line;
        line= getLineUsers(account);
        int location=getTypeLocation(line);
        return line.charAt(location);
    }

    public void writeAccount(String account,String password,String phone,String email){//将用户数据写入文件
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        String data =account+"|"+password+"|"+"0"+"|"+Integer.toString(UserFunction.RecNum++)+"|"+"0"+"|"+formattedDateTime+"|"+"0"+"|"+phone+"|"+email+"\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void writeAccount(String account,String password,String id,String level,String time,String sum,String phone,String email){//将用户数据写入文件
        String data =account+"|"+password+"|"+"0"+"|"+id+"|"+level+"|"+time+"|"+sum+"|"+phone+"|"+email+"\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void addManager(String account,String password){//将管理员数据写入文件
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        String data =account+"|"+password+"|"+"1"+"|"+"1"+"|"+"0"+"|"+formattedDateTime+"|"+"0"+"|"+"13379834673"+"|"+"676864824@qq.com"+"\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public boolean judgeExistUsers(String account){//判断账户是否存在,存在返回false，不存在返回true
        boolean judge=true;
        String line= getLineUsers(account);
        if(line!=null)
            judge=false;
        return judge;
    }

    public void writeCounter(int count){
        String data =Integer.toString(count);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("counter.txt", false))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
    public void modifyPassword(String account,String password){
        String line=getLineUsers(account);
        if(line==null){
            System.out.println("用户不存在");
            return;
        }
        deleteAccountUsers(account);
        if(account.equals("admin")){
            addManager(account,password);
        }
        else{
            String[] parts=line.split("\\|");
            writeAccount(account,password,parts[3],parts[4],parts[5],parts[6],parts[7],parts[8]);
        }
    }

    public void deleteAccountUsers(String account){//删除用户
        try {
            File inputFile = new File("users.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\|");
                if (parts[0].equals(account)) {
                    continue; // 跳过指定账号的行
                }
                writer.write(currentLine + "\n");
            }
            writer.close();
            reader.close();
            if (!inputFile.delete()) {
                System.out.println("An error occurred while deleting the original file.");
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("An error occurred while renaming the temp file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the user.");
            e.printStackTrace();
        }
    }

    public void modifyLevel(String account,int level){
        String line=getLineUsers(account);
        deleteAccountUsers(account);
        String[] parts=line.split("\\|");
        writeAccount(account,parts[1],parts[3],Integer.toString(level),parts[5],parts[6],parts[7],parts[8]);
    }

    public void modifySum(String account,float sum){
        String line=getLineUsers(account);
        deleteAccountUsers(account);
        String[] parts=line.split("\\|");
        writeAccount(account,parts[1],parts[3],parts[4],parts[5],Float.toString(sum),parts[7],parts[8]);
    }
    //商品文件----------------------------------------
    public void writeCommodity(String name,double price,String factory,String time,int num){//将商品数据写入文件
        String data =name+"|"+price+"|"+factory+"|"+time+"|"+num+"\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("commodity.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public String getLineCommodity(String name){//返回name当前行的所有数据
        String line=null;
        boolean judge=true;
        try (BufferedReader reader = new BufferedReader(new FileReader("commodity.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] parts=line.split("\\|");
                if(!parts[0].equals(name))
                    judge=false;
                if(judge){
                    return line;
                }
                judge=true;
            }
        } catch (IOException e) {
            System.out.println("商品不存在！");
        }
        return null;
    }

    public void modifyCommodity(String name,float price,String factory,String time,int num){
        deleteCommodity(name);
        writeCommodity(name,price,factory,time,num);
    }

    public void deleteCommodity(String name){//删除商品
        try {
            File inputFile = new File("commodity.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\|");
                if (parts[0].equals(name)) {
                    continue; // 跳过指定账号的行
                }
                writer.write(currentLine + "\n");
            }
            writer.close();
            reader.close();
            if (!inputFile.delete()) {
                System.out.println("An error occurred while deleting the original file.");
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("An error occurred while renaming the temp file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the user.");
            e.printStackTrace();
        }
    }

    public void modifyCommodityNum(String name,int num){
        String line=getLineCommodity(name);
        String[] parts=line.split("\\|");
        modifyCommodity(parts[0],Float.parseFloat(parts[1]),parts[2],parts[3],num);
    }
    //shoppingHistroy---------------------------
    public void writeShoppingHistory(String account,String goods,String time){
        String data =account+"|"+goods+"|"+time+"\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("shoppingHistory.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public List<String> getLinesShoppingHistory(String account){
        List<String> lines=new ArrayList<>();
        String line=null;
        boolean judge=true;
        try (BufferedReader reader = new BufferedReader(new FileReader("shoppingHistory.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] parts=line.split("\\|");
                if(!parts[0].equals(account))
                    judge=false;
                if(judge){
                    lines.add(line);
                }
                judge=true;
            }
        } catch (IOException e) {
        }
        return lines;
    }
    //shoppingCart-------------------------------
    public void writeShoppingCart(String account,String name,float price){
        String data =account+"|"+name+"|"+price+"\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("shoppingCart.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public List<String> getLinesShoppingCart(String account){
        List<String> lines=new ArrayList<>();
        String line=null;
        boolean judge=true;
        try (BufferedReader reader = new BufferedReader(new FileReader("shoppingCart.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] parts=line.split("\\|");
                if(!parts[0].equals(account))
                    judge=false;
                if(judge){
                    lines.add(line);
                }
                judge=true;
            }
        } catch (IOException e) {
        }
        return lines;
    }

    public void deleteShoppingCart(String account,String goods){//删除商品
        try {
            File inputFile = new File("shoppingCart.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String[] productList = goods.split(" ");
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\|");
                if (parts[0].equals(account)&&judgeGoodsAndName(goods,parts[1])) {
                    continue; // 跳过指定账号的行
                }
                writer.write(currentLine + "\n");
            }
            writer.close();
            reader.close();
            if (!inputFile.delete()) {
                System.out.println("An error occurred while deleting the original file.");
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("An error occurred while renaming the temp file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the user.");
            e.printStackTrace();
        }
    }

    public boolean judgeGoodsAndName(String goods,String name){//name若有goods中一个商品同名就返回true
        String[] productList = goods.split(" ");
        for(int i=0;i<productList.length;i++){
            if(name.equals(productList[i]))
                return true;
        }
        return false;
    }

    public boolean judgeExistShoppingCart(String name){
        FileOp fileOp=new FileOp();
        boolean judge=false;
        List<String> linesList = fileOp.getLinesShoppingCart(UserFunction.account);
        String[] lines = linesList.toArray(new String[linesList.size()]);
        for(int i=0;i<lines.length;i++){
            String[] parts=lines[i].split("\\|");
            if(parts[1].equals(name)){
                judge=true;
                break;
            }
        }
        return judge;
    }
}

