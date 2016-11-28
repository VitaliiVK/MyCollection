package myCollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import myCollectionService.dataBaseService.DbService;
import myCollectionService.dataBaseEntitys.Collector;
import myCollectionService.dataBaseEntitys.Instance;
import myCollectionService.dataBaseEntitys.MyCollection;
import myCollectionService.dataBaseEntitys.UserRole;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
//extends SpringBootServletInitializer for war packing and deployment into the tomcat
public class Application extends SpringBootServletInitializer {

    @Autowired
    private PhotoService photoService;

    @Override//SpringApplicationBuilder for war packing and deployment into the tomcat
    public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    //SpringBoot Application start point
    public static void main(String[] args) { //стандартная строка для запуска
        SpringApplication.run(Application.class, args);
    }

    @Bean //initialize on application start
    public CommandLineRunner onApplicationStart(final DbService dbService) { //передаем сервис для работы с бозой данных
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {


                Collector admin = new Collector("admin", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.ADMIN);
                admin.setName("Vitalii");
                admin.setEmail("myCollectionService@gmail.com");
                admin.setCity("Kiev");
                admin.setCountry("Ukraine");
                admin.setPhone("+380634100000");
                admin.setSpecialization("Entomology Lepidoptera");
                admin.setInformation("Collector-amateur Lepidoptera of Ukraine");
                admin.setPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/profile.jpg"));
                dbService.addCollector(admin);

                MyCollection adminCollection = new MyCollection("Butterflies of Ukraine", "butterflies", admin);
                adminCollection.setPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/cover.jpg"));
                adminCollection.setInformation("The collection contains about 500 species and more than 1000 copies of Lepidoptera from Ukraine and some foreign species.");
                dbService.addCollection(adminCollection);


                Instance box1 = new Instance("Papilionidae", adminCollection);
                box1.setSubtype("Collection box #1");
                box1.setInformation("species composition:");
                box1.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/box1.jpg")));
                dbService.addInstance(box1);

                Instance box2 = new Instance("Papilionidae", adminCollection);
                box2.setSubtype("Collection box #2");
                box2.setInformation("species composition:");
                box2.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/box2.jpg")));
                dbService.addInstance(box2);

                Instance box3 = new Instance("Papilionidae", adminCollection);
                box3.setSubtype("Collection box #3");
                box3.setInformation("species composition:");
                box3.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/box3.jpg")));
                dbService.addInstance(box3);

                Instance box4 = new Instance("Papilionidae", adminCollection);
                box4.setSubtype("Collection box #4");
                box4.setInformation("species composition:");
                box4.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/box4.jpg")));
                dbService.addInstance(box4);

                Instance box5 = new Instance("Papilionidae", adminCollection);
                box5.setSubtype("Collection box #5");
                box5.setInformation("species composition:");
                box5.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/admin/box5.jpg")));
                dbService.addInstance(box5);



                Collector user = new Collector("user", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER);
                user.setName("Vasilii");
                user.setSurname("Ivanov");
                user.setEmail("vasilii@gmail.com");
                user.setCity("Dnepr");
                user.setCountry("Ukraine");
                user.setPhone("+38067100000");
                user.setSpecialization("Entomology");
                user.setInformation("Entomology collector-pro");
                user.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/user/profile.jpg")));
                dbService.addCollector(user);

                MyCollection userCollection1 = new MyCollection("Butterflies world", "Butterflies", user);
                userCollection1.setInformation("The collection contains about 4500 species and more than 10000 copies of Lepidoptera from all world.");
                userCollection1.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/user/butterflyCover.jpg")));
                dbService.addCollection(userCollection1);

                File folder = new File("webapps/MyCollection/public/photo/user/butterfly");
                String[] fileList = folder.list();

                for(int i = 0; i < fileList.length; i++){
                    Instance instance = new Instance("collection box"+(i+1), userCollection1);
                    instance.setInformation("species composition:");
                    instance.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/user/butterfly/"+fileList[i])));
                    dbService.addInstance(instance);
                }

                MyCollection userCollection2 = new MyCollection("Bugs world", "Bugs", user);
                userCollection2.setInformation("The collection contains about 7000 species bugs and other insects and more than 50000 instances from all world.");
                userCollection2.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/user/bugsCover.jpg")));
                dbService.addCollection(userCollection2);

                File folder2 = new File("webapps/MyCollection/public/photo/user/bugs");
                String[] fileList2 = folder2.list();

                for(int i = 0; i < fileList2.length; i++){
                    Instance instance = new Instance("collection box"+(i+1), userCollection2);
                    instance.setInformation("species composition:");
                    instance.setPhoto(photoService.cropPhoto(loadPhoto("webapps/MyCollection/public/photo/user/bugs/"+fileList2[i])));
                    dbService.addInstance(instance);
                }

            }
        };
    }

    private byte[] loadPhoto(String adres){
        byte[] b = null;
        try(FileInputStream f= new FileInputStream(adres)){
            b = new byte[f.available()];
            f.read(b);
        } catch(IOException e){
            System.out.println("PHOTO FILE READ ERROR");
            System.out.println(e.toString());
        }
        return b;
    }
}