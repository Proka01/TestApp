package paket_test;

import paket_specification.*;

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
            System.out.println("14. ls_by_extension");
            System.out.println("15. is_containing_file");
            System.out.println("16. is_containing_files");
            System.out.println("17. grep");
            //System.out.println("18. sort_search_result");
            //System.out.println("19. filter_result");

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
            else if(option_args[0].equals("8."))
            {
                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.ls();
                else list = storage.ls(option_args[1]);

                storage.sortSearchResult(list,SortBy.NAME,true);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("9."))
            {
                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.deepLs("");
                else list = storage.deepLs(option_args[1]);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("10."))
            {
                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.lsChildren("");
                else list = storage.lsChildren(option_args[1]);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("11."))
            {
                System.out.println("Enter substring");
                String substr = sc.nextLine();
                System.out.println("Choose substring options: (For example, entre BEGIN_WITH)");
                System.out.println("1. BEGIN_WITH");
                System.out.println("2. END_WITH");
                System.out.println("3. CONTAIN");
                SubstringOption so = SubstringOption.valueOf(sc.nextLine());
                System.out.println("Choose ls options: (For example enter 2)");
                System.out.println("1. ls");
                System.out.println("2. lsChildren");
                System.out.println("3. deepLs");
                int lso = Integer.parseInt(sc.nextLine());


                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.lsBySubstr("",substr,so,lso);
                else list = storage.lsBySubstr(option_args[1],substr,so,lso);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("12."))
            {
                System.out.println("Enter start date, as long number");
                long start = Long.parseLong(sc.nextLine());

                System.out.println("Enter end date, as long number");
                long end = Long.parseLong(sc.nextLine());

                System.out.println("Choose ls options: (For example enter 2)");
                System.out.println("1. ls");
                System.out.println("2. lsChildren");
                System.out.println("3. deepLs");
                int lso = Integer.parseInt(sc.nextLine());


                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.lsByDateCreated(start,end,lso);
                else list = storage.lsByDateCreated(option_args[1],start,end,lso);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("13."))
            {
                System.out.println("Enter start date, as long number");
                long start = Long.parseLong(sc.nextLine());

                System.out.println("Enter end date, as long number");
                long end = Long.parseLong(sc.nextLine());

                System.out.println("Choose ls options: (For example enter 2)");
                System.out.println("1. ls");
                System.out.println("2. lsChildren");
                System.out.println("3. deepLs");
                int lso = Integer.parseInt(sc.nextLine());


                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.lsByDateModified(start,end,lso);
                else list = storage.lsByDateModified(option_args[1],start,end,lso);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("14."))
            {
                System.out.println("Enter extension");
                String ext = sc.nextLine();

                System.out.println("Choose ls options: (For example enter 2)");
                System.out.println("1. ls");
                System.out.println("2. lsChildren");
                System.out.println("3. deepLs");
                int lso = Integer.parseInt(sc.nextLine());


                List<MyFile> list = new ArrayList<>();
                if(option_args[1].equals(".")) list = storage.lsByExtension(lso,ext);
                else list = storage.lsByExtension(option_args[1],lso,ext);

                System.out.println(storage.filterResult(list,new FilterOptions(true,false,false,false,false,false)));
            }
            else if(option_args[0].equals("15."))
            {
                System.out.println(storage.isContainingFile(option_args[1],option_args[2]));
            }
            else if(option_args[0].equals("16."))
            {
                System.out.println("Enter file names with spaces:");
                String names = sc.nextLine();
                String[] fileList = names.split(" ");
                System.out.println("Enter path in storage:");
                String pathInStorage = sc.nextLine();

                System.out.println(storage.isContainingFiles(pathInStorage, Arrays.asList(fileList)));
            }
            else if(option_args[0].equals("17."))
            {
                List<String> list = storage.grep(option_args[1]);
                for (String s : list)
                    System.out.println(s);
            }

        }

    }
}
