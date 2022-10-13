package com.example.ActiveMQ_JWT.controller;

import java.util.*;

import com.example.ActiveMQ_JWT.data.Person;
import com.example.ActiveMQ_JWT.entity.User;
import com.example.ActiveMQ_JWT.helper.XMLConvert;
import com.example.ActiveMQ_JWT.service.RoleService;
import com.example.ActiveMQ_JWT.service.TokenService;
import com.example.ActiveMQ_JWT.service.UserService;
import com.example.ActiveMQ_JWT.util.JwtUtil;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.ActiveMQ_JWT.authen.UserPrincipal;
import com.example.ActiveMQ_JWT.entity.Role;
import com.example.ActiveMQ_JWT.entity.RoleToUserFrom;
import com.example.ActiveMQ_JWT.entity.Token;

import java.util.Date;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.server.PathParam;

import com.example.ActiveMQ_JWT.data.Person;
import com.example.ActiveMQ_JWT.helper.XMLConvert;
import org.apache.log4j.BasicConfigurator;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleService roleService;
    
    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userService.createUser(user);
    }
    
    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody List<RoleToUserFrom> roleForms){
    	List<User> users = new ArrayList<>();
    	for (RoleToUserFrom roleForm : roleForms) {
    		User user = userService.getUserByUsername(roleForm.getUsername());
        	if (user != null) {
        		Set<Role> roles = user.getRoles();
        		roleService.addRole(new Role(0l, "", null, null, 0l, 0l, roleForm.getRoleName(), roleForm.getRoleKey(), null));
        		roles.add(roleService.findRoleByRoleName(roleForm.getRoleName()));
        		user.setRoles(roles);
        		users.add(userService.saveUser(user));
        	}
		}
    	return ResponseEntity.ok(users);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        UserPrincipal userPrincipal =
                userService.findByUsername(user.getUsername());

        if (null == user || !new BCryptPasswordEncoder()
                .matches(user.getPassword(), userPrincipal.getPassword())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Account or password is not valid!");
        }

        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));

        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);

        return ResponseEntity.ok(token.getToken());
    }

    
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity hello(){
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/onlyReadPage")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity onlyRead(){
        return ResponseEntity.ok("only USER_READ");
    }

    @GetMapping("/crudPage")
    @PreAuthorize("hasAnyAuthority('USER_CRUD')")
    public ResponseEntity onlyCRUD(){
        return ResponseEntity.ok("only USER_CRUD");
    }

    @PostMapping("/send")
    public String sendMessage(@PathParam("mess") String mess) throws NamingException {
        try {
            //config environment for JMS
            BasicConfigurator.configure();
            //config environment for JNDI
            Properties settings=new Properties();
            settings.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            //create context
            Context ctx=new InitialContext(settings);
            //lookup JMS connection factory
            ConnectionFactory factory=
                    (ConnectionFactory)ctx.lookup("ConnectionFactory");
            //lookup destination. (If not exist-->ActiveMQ create once)
            Destination destination=
                    (Destination) ctx.lookup("dynamicQueues/thanthidet");
            //get connection using credential
            Connection con=factory.createConnection("admin","admin");
            //connect to MOM
            con.start();
            //create session
            Session session=con.createSession(
                    /*transaction*/false,
                    /*ACK*/Session.AUTO_ACKNOWLEDGE
            );
            //create producer
            MessageProducer producer = session.createProducer(destination);
            //create text message
            Message msg=session.createTextMessage(mess);
            producer.send(msg);
            Person p=new Person(1001, "Thân Thị Đẹt", new Date());
            String xml=new XMLConvert<Person>(p).object2XML(p);
            msg=session.createTextMessage(xml);
            producer.send(msg);
            //shutdown connection
            session.close();con.close();
            System.out.println("Finished...");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Sended";
    }
//    Object principal = SecurityContextHolder
//            .getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//        UserPrincipal userPrincipal = (UserPrincipal) principal;
//    }

}
