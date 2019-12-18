package pl.raptors.raptorsRobotsApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import pl.raptors.raptorsRobotsApp.domain.accounts.Role;
import pl.raptors.raptorsRobotsApp.domain.accounts.User;
import pl.raptors.raptorsRobotsApp.domain.graphs.Edge;
import pl.raptors.raptorsRobotsApp.domain.graphs.Graph;
import pl.raptors.raptorsRobotsApp.domain.graphs.Vertex;
import pl.raptors.raptorsRobotsApp.domain.movement.*;
import pl.raptors.raptorsRobotsApp.domain.movement.MovementPathPoint;
import pl.raptors.raptorsRobotsApp.domain.robots.*;
import pl.raptors.raptorsRobotsApp.domain.type.*;
import pl.raptors.raptorsRobotsApp.repository.accounts.RoleRepository;
import pl.raptors.raptorsRobotsApp.repository.accounts.UserRepository;
import pl.raptors.raptorsRobotsApp.repository.graphs.EdgeRepository;
import pl.raptors.raptorsRobotsApp.repository.graphs.GraphRepository;
import pl.raptors.raptorsRobotsApp.repository.graphs.VertexRepository;
import pl.raptors.raptorsRobotsApp.repository.movement.*;
import pl.raptors.raptorsRobotsApp.repository.robots.*;
import pl.raptors.raptorsRobotsApp.repository.type.*;
import pl.raptors.raptorsRobotsApp.service.graphs.GraphService;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//klasa wstawiająca do bazy wstepne przykladowe dane
@Component
public class DbSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AreaPointRepository areaPointRepository;
    @Autowired
    private CorridorPointRepository corridorPointRepository;
    @Autowired
    private CorridorRepository corridorRepository;
    @Autowired
    private MapAreaRepository mapAreaRepository;
    @Autowired
    private MovementMapRepository movementMapRepository;
    @Autowired
    private MovementPathRepository movementPathRepository;
    @Autowired
    private StandRepository standRepository;
    @Autowired
    private BatteryTypeRepository batteryTypeRepository;
    @Autowired
    private ExtraRobotElementRepository extraRobotElementRepository;
    @Autowired
    private RobotBatteryRepository robotBatteryRepository;
    @Autowired
    private RobotModelRepository robotModelRepository;
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private RobotReviewRepository robotReviewRepository;
    @Autowired
    private MovementPathPointRepository movementPathPointRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private BehaviourRepository behaviourRepository;
    @Autowired
    private RobotTaskRepository robotTaskRepository;
    @Autowired
    private GraphRepository graphRepository;
    @Autowired
    private EdgeRepository edgeRepository;
    @Autowired
    private VertexRepository vertexRepository;
    @Autowired
    private AreaTypeRepository areaTypeRepository;
    @Autowired
    private ParkingTypeRepository parkingTypeRepository;
    @Autowired
    private PropulsionTypeRepository propulsionTypeRepository;
    @Autowired
    private ReviewTypeRepository reviewTypeRepository;
    @Autowired
    private RobotStatusRepository robotStatusRepository;
    @Autowired
    private RoutePriorityRepository routePriorityRepository;
    @Autowired
    private StandStatusRepository standStatusRepository;
    @Autowired
    private StandTypeRepository standTypeRepository;
    @Autowired
    private TaskPriorityRepository taskPriorityRepository;
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private GraphService graphService;


    @Autowired
    private GridFsOperations gridFsOperations;


    @Override
    public void run(String... strings) throws IOException {

        Role regularUser = new Role("regularUser");

        User testowyUser1 = new User("testowy@gmail.com", "test",
                //jeśli więcej niż 1 rola, to Array.asList()
                Collections.singletonList(regularUser)

        );

        User testowyUser2 = new User("kowalski@gmail.com", "test2",
                //jeśli więcej niż 1 rola, to Array.asList()
                Collections.singletonList(regularUser)

        );

        //wywalanie wszystkich userów i ról
        this.roleRepository.deleteAll();
        this.userRepository.deleteAll();

        //wrzucanie utworzonych userów do bazy
        List<User> usersToAdd = Arrays.asList(testowyUser1, testowyUser2);
        this.roleRepository.save(regularUser);
        this.userRepository.saveAll(usersToAdd);

        //GRAFY
        Vertex vertex1 = new Vertex(17.5, 25.0, "A");
        Vertex vertex2 = new Vertex(15.0, 20.0, "B");
        Vertex vertex3 = new Vertex(20.0, 20.0, "C");
        Vertex vertex4 = new Vertex(15.0, 15.0, "D");
        Vertex vertex5 = new Vertex(20.0, 15.0, "E");

        Edge edge1 = new Edge(vertex1, vertex2, false);
        Edge edge2 = new Edge(vertex1, vertex3, false);
        Edge edge3 = new Edge(vertex2, vertex4, false);
        Edge edge4 = new Edge(vertex3, vertex5, false);

        List<Vertex> verticesToAdd = Arrays.asList(vertex1, vertex2, vertex3, vertex4, vertex5);
        List<Edge> edgesToAdd = Arrays.asList(edge1, edge2, edge3, edge4);

        Graph graph = new Graph(edgesToAdd);

        this.vertexRepository.deleteAll();
        this.edgeRepository.deleteAll();
        this.graphRepository.deleteAll();

        //this.vertexRepository.saveAll(verticesToAdd);
        //this.edgeRepository.saveAll(edgesToAdd);
        //this.graphRepository.save(graph);
        this.graphService.addOne(graph);


        //KOLEJNOSC JEST WAZNA

        MovementMap movementMap = new MovementMap("mapkaNazwa", null);

        AreaType areaType = new AreaType("magazyn");

        MapArea mapArea = new MapArea("hala A", movementMap, areaType);


        AreaPoint areaPoint = new AreaPoint(mapArea, 3, 221.40, -30.67);

        MovementPath movementPath = new MovementPath("droga glówna B");

        Corridor corridor = new Corridor("pomost", movementPath);

        CorridorPoint corridorPoint = new CorridorPoint(corridor, 11, 99.8, 111.2);

        ExtraRobotElement extraRobotElement = new ExtraRobotElement("jakas dostawka", "100cm x 350cm");

        PropulsionType propulsionType = new PropulsionType("mechaniczny");

        BatteryType batteryType = new BatteryType("litowo-jonowa", "3200", "2.1", "9.0");

        RobotModel robotmModel = new RobotModel("CP-300", "500kg", "30km/h", "200cm", "120cm", "200cm", "30 deg", propulsionType, batteryType);

        ParkingType parkingType = new ParkingType("parking 1");
        StandType standType = new StandType("stanowisko 1");

        Pose pose1=new Pose();
        pose1.setOrientation(new Pose.Orientation(98.0, 76.4, 34.34, 11.0));
        pose1.setPosition(new Pose.Position(33.21, 123.54, 0.0));
        Stand stand = new Stand("miejsce ładowania baterii", pose1, parkingType, standType);

        Pose pose2=new Pose();
        pose2.setOrientation(new Pose.Orientation(88.0, 72.4, 86.34, 33.0));
        pose2.setPosition(new Pose.Position(55.21, 133.54, 1.0));
        Stand stand2 = new Stand("mnagazyn",pose2, parkingType, standType);

        RobotStatus robotStatus = new RobotStatus("zajety");
        RobotStatus robotStatus1=new RobotStatus("potrzezbuje ladownia");
        List<RobotStatus> robotStatuses=new ArrayList<>();
        robotStatuses.add(robotStatus);
        robotStatuses.add(robotStatus1);

        //format czasu
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Robot robot = new Robot("192.15.0.1", true, extraRobotElement, robotmModel, pose1, formatter.format(new Date()), 77.4, robotStatuses);

        Log log= new Log(robot,formatter.format(new Date()),robotStatus);

        RobotBattery robotBattery = new RobotBattery("2016-9-22", batteryType);

        ReviewType reviewType = new ReviewType("service call");

        RobotReview robotReview = new RobotReview(robot, "2019-3-30", "2016-4-25", reviewType);

        StandStatus standStatus = new StandStatus("free");


        MovementPathPoint movementPathPoint = new MovementPathPoint(movementPath, 20, 43.2, 50.2);

        RoutePriority routePriority = new RoutePriority("ważne", 1);

        Route route = new Route(movementMap, movementPath, corridor, "najszybsza główna", stand2, stand, routePriority);

        Behaviour behaviour = new Behaviour("WAIT", "* WILL BE JSON *");

        Behaviour behaviour2 = new Behaviour("GO_TO", "* WILL BE JSON *");

        Behaviour behaviour3 = new Behaviour("DOCKAGE", "* WILL BE JSON *");


        List<Behaviour> behaviours = new ArrayList();
        behaviours.add(behaviour);
        behaviours.add(behaviour2);
        behaviours.add(behaviour3);

        TaskPriority taskPriority = new TaskPriority("wazne", 1);

        RobotTask robotTask = new RobotTask("transport tools", behaviours, "2019-6-21 16:00", taskPriority);

       // TempParameters tempParameters = new TempParameters(pose1, 77.4, robotStatuses);


        //czyść baze
/*        this.areaPointRepository.deleteAll();
        this.corridorRepository.deleteAll();
        this.corridorPointRepository.deleteAll();
        this.mapAreaRepository.deleteAll();
        //this.movementMapRepository.deleteAll();
        this.movementPathRepository.deleteAll();
        this.standRepository.deleteAll();
        this.batteryTypeRepository.deleteAll();
        this.extraRobotElementRepository.deleteAll();
        this.robotBatteryRepository.deleteAll();
        this.robotModelRepository.deleteAll();
        this.robotRepository.deleteAll();
        this.robotReviewRepository.deleteAll();
        this.movementPathPointRepository.deleteAll();
        this.routeRepository.deleteAll();
        this.behaviourRepository.deleteAll();
        this.robotTaskRepository.deleteAll();
        this.tempParametersRepository.deleteAll();
        this.logRepository.deleteAll();
        //type
        this.areaTypeRepository.deleteAll();
        this.parkingTypeRepository.deleteAll();
        this.propulsionTypeRepository.deleteAll();
        this.reviewTypeRepository.deleteAll();
        this.robotStatusRepository.deleteAll();
        this.routePriorityRepository.deleteAll();
        this.standStatusRepository.deleteAll();
        this.standTypeRepository.deleteAll();
        this.taskPriorityRepository.deleteAll();*/

        //dodaj do bazy dane
       /* this.movementMapRepository.save(movementMap);
        this.mapAreaRepository.save(mapArea);
        this.areaPointRepository.save(areaPoint);
        this.movementPathRepository.save(movementPath);
        this.corridorRepository.save(corridor);
        this.corridorPointRepository.save(corridorPoint);
        this.extraRobotElementRepository.save(extraRobotElement);
        this.robotModelRepository.save(robotmModel);
        this.robotRepository.save(robot);
        this.logRepository.save(log);
        this.batteryTypeRepository.save(batteryType);
        this.robotBatteryRepository.save(robotBattery);
        this.robotReviewRepository.save(robotReview);
        this.standRepository.save(stand);
        this.standRepository.save(stand2);
        this.movementPathPointRepository.save(movementPathPoint);
        this.routeRepository.save(route);
        this.behaviourRepository.save(behaviour);
        this.behaviourRepository.save(behaviour2);
        this.behaviourRepository.save(behaviour3);
        this.robotTaskRepository.save(robotTask);
        //type
        this.areaTypeRepository.save(areaType);
        this.parkingTypeRepository.save(parkingType);
        this.propulsionTypeRepository.save(propulsionType);
        this.reviewTypeRepository.save(reviewType);
        this.robotStatusRepository.save(robotStatus);
        this.routePriorityRepository.save(routePriority);
        this.standStatusRepository.save(standStatus);
        this.standTypeRepository.save(standType);
        this.taskPriorityRepository.save(taskPriority);*/

        //this.graphService.deleteOne(graph); //just for testing purpose - keep commented
    }

}
