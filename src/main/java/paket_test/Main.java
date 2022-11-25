package paket_test;

import paket_specification.Specification;
import paket_specification.StorageManager;

public class Main {
    public static void main(String[] args) {
        //String path = args[0];
        //System.out.println("Path: " + path);

        try {
            Class.forName("paket_local.LocalStorageImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Specification storage = StorageManager.getStorage();
        storage.openRootDir("C:\\Users\\HP\\Desktop\\SK_projekat\\testing\\aleksa");
        storage.rename("prokic\\shop\\bass","gitara");
    }
}
