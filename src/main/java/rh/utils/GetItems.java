/*
package rh.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.Leaf;

*/
/**
 * 获取所有group,item
 *//*

public class GetItems {
//    public static void main(String[] args) {
//
//        try {
////            FileWriter fw = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\items.txt"), true);
////            BufferedWriter bw = new BufferedWriter(fw);
//            ArrayList<String> arr = dumpFlat();
//            for(String str:arr){
////                bw.write(str + "\r\n");
//                System.out.println(str);
//            }
//            //System.out.println(arr.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static ArrayList dumpFlat()
            throws IllegalArgumentException, UnknownHostException, Exception {
        ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("localhost");
        ci.setDomain("");
        ci.setUser("administrator");
        ci.setPassword("ts.1234");
        ci.setClsid("04524449-C6B2-4d62-8471-C64FA1DDF64F");

        Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        server.connect();


        ArrayList arr = new ArrayList<String>();
        for (String name : server.getFlatBrowser().browse()) {
//            System.out.println(name);
            arr.add(name);
        }
//        System.out.println("count:" + arr.size());

        server.disconnect();

        return arr;
    }

    public static void dumpTree(final Branch branch, final int level) {

        for (final Leaf leaf : branch.getLeaves()) {
            dumpLeaf(leaf, level);
        }
        for (final Branch subBranch : branch.getBranches()) {
            dumpBranch(subBranch, level);
            dumpTree(subBranch, level + 1);
        }
    }

    private static String printTab(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    private static void dumpLeaf(final Leaf leaf, final int level) {
        System.out.println(printTab(level) + "Leaf: " + leaf.getName() + ":"
                + leaf.getItemId());
    }

    private static void dumpBranch(final Branch branch, final int level) {
        System.out.println(printTab(level) + "Branch: " + branch.getName());
    }
}
*/
