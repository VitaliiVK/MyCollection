package myCollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import myCollectionService.dataBaseService.DbService;
import myCollectionService.dataBaseEntitys.*;

import java.io.*;
import java.util.List;

@Controller
public class MyController {

    @Autowired
    private DbService dbService; //обьект содержащий методлы для работы с базой данных
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder; //хеш шифратор для паролей
    @Autowired
    private PhotoService photoService;

    //root URL
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    //запрос страницы login
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    //URL /registration - if user reload page
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return "registration";
    }

    //URL /registration - form for user registration
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationForm(Model model,
                          @RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String password_confirm,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String surname,
                          @RequestParam(required = false) String email,
                          @RequestParam(required = false) String country,
                          @RequestParam(required = false) String city,
                          @RequestParam(required = false) String phone,
                          @RequestParam(required = false) String specialization,
                          @RequestParam(required = false) String information) {
        if(login.isEmpty() || password.isEmpty() || password_confirm.isEmpty()){
            model.addAttribute("error", "wrong login or password!");
            return "registration";
        }
        if(!password.equals(password_confirm)){
            model.addAttribute("error", "wrong password confirm!");
            return "registration";
        }
        if(dbService.loginIsExist(login)){
            model.addAttribute("error", "login busy!");
            return "registration";
        }
        String pass = shaPasswordEncoder.encodePassword(password, null);
        Collector collector = new Collector(login, pass, UserRole.USER);
        collector.setName(name);
        collector.setSurname(surname);
        collector.setEmail(email);
        collector.setCountry(country);
        collector.setCity(city);
        collector.setPhone(phone);
        collector.setSpecialization(specialization);
        collector.setInformation(information);
        dbService.addCollector(collector);
        model.addAttribute("login", login);
        model.addAttribute("password", password);
        model.addAttribute("registration", "registration");
        return "login";
    }

    //URL /view_collectors - for view collectors list
    @RequestMapping("/view_collectors")
    public String viewCollectors(Model model) {
        List<Collector> collectorsList = dbService.getCollectorsList();
        model.addAttribute("action", "view_collectors");
        model.addAttribute("collectorsList", collectorsList);
        return "index";
    }

    //URL /view_collections - for view collections list
    @RequestMapping("/view_collections")
    public String viewCollections(Model model) {
        List<MyCollection> collectionsList = dbService.getCollectionsList();
        model.addAttribute("action", "view_collections");
        model.addAttribute("collectionsList", collectionsList);
        return "index";
    }

    //URL /my_profile - for view my profile
    @RequestMapping("/my_profile")
    public String myProfile(Model model) {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o==null){ //дублируем защиту SpringSecurity
            return "login";
        }
        User user = (User)o;
        String login = user.getUsername();
        Collector collector = dbService.getCollectorByLogin(login);
        model.addAttribute("action", "my_profile");
        model.addAttribute("collector", collector);
        return "index";
    }

    //URL /my_profile - form for update profile information
    @RequestMapping(value = "/my_profile", method = RequestMethod.POST)
    public String update_my_profile(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String surname,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) String phone,
                                    @RequestParam(required = false) String country,
                                    @RequestParam(required = false) String city,
                                    @RequestParam(required = false) String specialization,
                                    @RequestParam(required = false) String information,
                                    @RequestParam(required = false) MultipartFile photo) {
        //get User user from Spring for current account
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername(); //из обьекта достаем логин
        Collector collector = dbService.getCollectorByLogin(login);
        collector.setName(name);
        collector.setSurname(surname);
        collector.setEmail(email);
        collector.setPhone(phone);
        collector.setCountry(country);
        collector.setCity(city);
        collector.setSpecialization(specialization);
        collector.setInformation(information);
        if(!photo.isEmpty()){
            try {
                collector.setPhoto(photoService.cropPhoto(photo.getBytes()));
            } catch (IOException e) {
                System.out.println("Photo read error"); //PhotoErrorException() in developing
            }
        }
        dbService.updateCollector(collector); //с помощью обьекта collectorService обновляем запись в базе данных
        return "redirect:/my_profile"; //перенаправляем
    }

    //URL /my_collections - for view current user collections
    @RequestMapping("/my_collections")
    public String viewMyCollections(Model model) {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o==null){ //дублируем защиту SpringSecurity
            return "login";
        }
        User user = (User)o;
        String login = user.getUsername();
        Collector collector = dbService.getCollectorByLogin(login);

        List<MyCollection> collectionsList = collector.getCollectionsList();
        model.addAttribute("action", "my_collections");

        model.addAttribute("collectionsList", collectionsList);
        return "index";
    }

    //URL /add_collection, if user reload page
    @RequestMapping(value = "/add_collection", method = RequestMethod.GET)
    public String addCollectionPage() {
        return "add_collection";
    }

    //URL add_collection - form for add new collection
    @RequestMapping(value = "/add_collection", method = RequestMethod.POST)
    public String addCollectionForm(@RequestParam String name,
                                    @RequestParam String type,
                                    @RequestParam(required = false) MultipartFile photo,
                                    @RequestParam (required = false) String information) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        Collector collector = dbService.getCollectorByLogin(login);

        MyCollection myCollection = new MyCollection(name, type, collector);
        myCollection.setInformation(information);
        if(!photo.isEmpty()){
            try {
                myCollection.setPhoto(photoService.cropPhoto(photo.getBytes()));
            } catch (IOException e) {
                System.out.println("Photo read error"); //PhotoErrorException() in developing
            }
        }
        collector.addCollection(myCollection);
        dbService.addCollection(myCollection);
        return "redirect:/my_collections";
    }

    //URL /update_remove_collection, if user reload page
    @RequestMapping(value = "/update_remove_collection", method = RequestMethod.GET)
    public String updateRemoveCollectionGet() {
        return "redirect:/my_collections";
    }

    //URL /update_remove_collection - form for edit or remove collection(depend action)
    @RequestMapping(value = "/update_remove_collection", method = RequestMethod.POST)
    public String updateRemoveCollectionForm(@RequestParam long collection_id,
                                           @RequestParam(required = false) String action,
                                           @RequestParam(required = false) MultipartFile photo,
                                           @RequestParam(required = false) String name,
                                           @RequestParam (required = false) String type,
                                           @RequestParam (required = false) String info) {
        MyCollection collection = dbService.getCollectionById(collection_id);
        if("remove".equals(action)){
            dbService.removeCollection(collection);
            return "redirect:/my_collections";
        }
        if(!photo.isEmpty()){
            try {
                collection.setPhoto(photoService.cropPhoto(photo.getBytes()));
            } catch (IOException e) {
                System.out.println("Photo read error"); //PhotoErrorException() in developing
            }
        }
        collection.setName(name);
        collection.setType(type);
        collection.setInformation(info);
        dbService.updateCollection(collection);
        return "redirect:/my_collections";
    }

    //для get запроса по адресу add_instance, чтоб не выводило ошибки
    @RequestMapping(value = "/add_instance", method = RequestMethod.GET)
    public String addInstanceGet() {
        return "redirect:/my_collections";
    }

    //обработка формы для создания нового экзеспляра add_instance
    @RequestMapping(value = "/add_instance", method = RequestMethod.POST)
    public String addInstanceForm(@RequestParam(required = false) MultipartFile photo,
                                  @RequestParam String name,
                                  @RequestParam long collection_id,
                                  @RequestParam (required = false) String information,
                                  @RequestParam (required = false) String subtype) {
        Instance instance = new Instance(name, dbService.getCollectionById(collection_id));
        System.out.println("information = " + information);
        instance.setSubtype(subtype);
        instance.setInformation(information);
        if(!photo.isEmpty()){
            try {
                instance.setPhoto(photoService.cropPhoto(photo.getBytes()));
            } catch (IOException e) {
                //throw new PhotoErrorException(); //если что-то не так бросаем исключение
            }
        }
        dbService.addInstance(instance);
        return "redirect:/view_collection/id_" + collection_id;
    }

    //для get запроса по /update_remove_instance, чтоб не выводило ошибки
    @RequestMapping(value = "/update_remove_instance", method = RequestMethod.GET)
    public String updateRemoveInstanceGet() {
        return "redirect:/my_collections";
    }

    //обработка формы для редактирования/удаления экзеспляра /update_remove_instance
    @RequestMapping(value = "/update_remove_instance", method = RequestMethod.POST)
        public String updateRemoveInstanceForm(@RequestParam long instance_id,
                                               @RequestParam(required = false) String action,
                                               @RequestParam(required = false) MultipartFile photo,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String subtype,
                                               @RequestParam (required = false) String info) {
        Instance instance = dbService.getInstanceById(instance_id);
        long collectionId = instance.getMyCollection().getId();
        if("remove".equals(action)){
            dbService.removeInstance(instance);
            return "redirect:/view_collection/id_" + collectionId;
        }
        if(!photo.isEmpty()){
            try {
                instance.setPhoto(photoService.cropPhoto(photo.getBytes()));
            } catch (IOException e) {
                //throw new PhotoErrorException(); //если что-то не так бросаем исключение
            }
        }
        instance.setName(name);
        instance.setSubtype(subtype);
        instance.setInformation(info);
        dbService.updateInstance(instance);
        return "redirect:/view_collection/id_" + collectionId;
    }

    //URL /search - for search collectors/collections/instances depend from page action
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchForm(Model model,
                             @RequestParam String search,
                             @RequestParam String action,
                             @RequestParam(required = false) Long collection_id){
        switch (action){
            case "view_collectors":
                model.addAttribute("collectorsList", dbService.findCollectorsBySearchLogin(search));
                break;
            case "view_collections":
                model.addAttribute("collectionsList", dbService.findCollectionsBySearchName(search));
                break;
            case "my_collections":
                User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String login = user.getUsername();
                Collector dbCollector = dbService.getCollectorByLogin(login);
                Long id = dbCollector.getId();
                model.addAttribute("collectionsList", dbService.findCurrentUserCollectionsBySearchName(search,id));
                break;
            case "view_collection":
                MyCollection collection = dbService.getCollectionById(collection_id);
                List<Instance> instancesList = dbService.findCurrentCollectionsInstancesBySearchName(search, collection.getId());
                model.addAttribute("collection", collection);
                model.addAttribute("instancesList", instancesList);
                break;
        }
        model.addAttribute("action", action);
        model.addAttribute("search", search);
        return "index";
    }

    //URL view_collection/id_{id} - for view collection by id
    @RequestMapping("view_collection/id_{id}")
    public String viewCollection(Model model, @PathVariable(value = "id") long collectionId) {
        MyCollection collection = dbService.getCollectionById(collectionId);
        List<Instance> instancesList = collection.getInstances();
        model.addAttribute("collection", collection);
        model.addAttribute("instancesList", instancesList);
        model.addAttribute("action", "view_collection");
        return "index";
    }

    //URL /photo/{entity}/{id} - for read photo from data base by id and entity type(instance/collection/collector)
    @RequestMapping("/photo/{entity}/{id}")
    public ResponseEntity<byte[]> photo(@PathVariable("id") long id,
                                        @PathVariable("entity") String entity) {
        byte[] bytes = null;
        switch (entity){
            case "instance":
                bytes = dbService.getInstanceById(id).getPhoto();
                break;
            case "collection":
                bytes = dbService.getCollectionById(id).getPhoto();
                break;
            case "collector":
                bytes = dbService.getCollectorById(id).getPhoto();
                break;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    //URL /add_comment - for add comment
    @RequestMapping(value = "/add_comment", method = RequestMethod.POST)
    public ResponseEntity<Void> addComment(@RequestParam String comment_text,
                                           @RequestParam String instance_or_collection,
                                           @RequestParam long id,
                                           @RequestParam String user_login){
        EntityForLikeAndComment entity = null;
        if(instance_or_collection.equals("instance")){
            entity = dbService.getInstanceById(id);
        }
        else{
            entity = dbService.getCollectionById(id);
        }
        Collector collector = dbService.getCollectorByLogin(user_login);
        Comment comment = new Comment(collector,comment_text,entity);
        dbService.addComment(comment);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //URL /add_remove_like - for add or remove lice(depend from like_action)
    @RequestMapping(value = "/add_remove_like", method = RequestMethod.POST)
    public ResponseEntity<Void> addRemoveLice(@RequestParam String instance_or_collection,
                                              @RequestParam long id,
                                              @RequestParam String user_login,
                                              @RequestParam String like_action){
        EntityForLikeAndComment entity = null;
        if(instance_or_collection.equals("instance")){
            entity = dbService.getInstanceById(id);
        }
        else{
            entity = dbService.getCollectionById(id);
        }
        Collector collector = dbService.getCollectorByLogin(user_login);
        if(like_action.equals("add_like")){
            DbLike like = new DbLike(collector,entity);
            dbService.addLike(like);
        }
        else{
            dbService.removeLike(collector,instance_or_collection,id);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // URL /admin - secret admin page
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    //URL /unauthorized - if user is logged, but this page is hidden for user roll
    @RequestMapping("/unauthorized")
    public String unauthorized(Model model){
        //с помощью обьекта collectorService вытаскиваем из базы учетную запись нашего класса Collector
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //добавляем атрибут к ответу
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }


    /* deprecate, cause static resources was add
    //URL /image/{image}.{format} for read photo from file
    @RequestMapping("/image/{image}.{format}")
    public ResponseEntity<byte[]> image(@PathVariable("image") String image,
                                        @PathVariable("format") String format) {
        File file = new File("src/main/webapp/resources/images/" + image+"."+format);
        byte[] bytes = null;
        if(file.canRead()){
            try(FileInputStream fis= new FileInputStream(file)){
                bytes = new byte[fis.available()];
                fis.read(bytes);
            } catch(IOException e){
                System.out.println("FILE READ ERROR");
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); //указываем тип содержимого
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
    */
}
