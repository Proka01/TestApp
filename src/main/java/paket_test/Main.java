package paket_test;

import paket_specification.Specification;
import paket_specification.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = args[0];
        System.out.println("Path: " + path);

        try {
            Class.forName("paket_local.LocalStorageImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Specification storage = StorageManager.getStorage();
        boolean succesfull_opening = storage.openRootDir(path);

        Scanner sc = new Scanner(System.in);
        String option = "";

        if(!succesfull_opening)
        {
            System.out.println("Do you want to create new storage? y/n");
            option = sc.nextLine();
        }

        if(option.equals("y"))
        {
            String storage_name = path.substring(path.lastIndexOf("/"));
            storage.createRootDir(storage_name,path);
        }

        while (true)
        {
            System.out.println();
            System.out.println("Choose action: ");
            System.out.println("0. Exit");
            System.out.println("1. create_folder");
            System.out.println("2. create_file");
            System.out.println("3. delete_folder");
            System.out.println("4. delete_file");
            System.out.println("5. move_file");
            System.out.println("6. download_file");
            System.out.println("7. rename");
            System.out.println();
            System.out.println("8. ls");
            System.out.println("9. deep_ls");
            System.out.println("10. children_ls");
            System.out.println("11. ls_by_substr");
            System.out.println("12. ls_by_date_created");
            System.out.println("13. ls_by_date_modified");
            System.out.println("14. is_containing_file");
            System.out.println("15. is_containing_files");
            System.out.println("16. grep");
            //System.out.println("17. sort_search_result");
            //System.out.println("18. filter_result");

            option = sc.nextLine();
            String[] option_args = option.split(" ");

            if(option.equals("0.")) break;

            if(option_args[0].equals("1."))
            {
                if(option_args.length == 2) storage.createDir(option_args[1]);
                else storage.createDir(option_args[1],option_args[2]);
            }
            else if(option.equals("2."))
            {
                System.out.println("Enter file names with spaces:");
                String names = sc.nextLine();
                String[] fileList = names.split(" ");
                System.out.println("Enter path in storage:");
                String pathInStorage = sc.nextLine();

                if(pathInStorage.equals(".")) storage.createFile(Arrays.asList(fileList));
                else storage.createFile(Arrays.asList(fileList),pathInStorage);
            }
            else if(option_args[0].equals("3."))
            {
                storage.deleteDir(option_args[1]);
            }
            else if(option_args[0].equals("4."))
            {
                storage.deleteFile(option_args[1]);
            }
            else if(option_args[0].equals("5."))
            {
                storage.moveFile(option_args[1],option_args[2]);
            }
            else if(option_args[0].equals("6."))
            {
                storage.download(option_args[1],option_args[2]);
            }
            else if(option_args[0].equals("7."))
            {
                storage.rename(option_args[1],option_args[2]);
            }


        }

    }
}
