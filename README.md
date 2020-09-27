# ApiGatewaySpringCloudGateway


Patrón API Gateway(+ Spring Cloud Gateway)

Para mostrarles como usar Spring Cloud Gateway vamos a implementar un pequeño ejemplo de la integración de esta tecnología en un proyecto Spring Boot, la arquitectura propuesta se basa en la implementación de un servicio cloud-gateway y dos microservicios (micro1 y micro2).

Comenzamos implementando micro1, en este solo añadiremos una pequeña lógica para probar el funcionamiento del patrón abordado:

      @RestController
      @RequestMapping("/micro1")
      public class microservice1Controller {
        @GetMapping("/message")
        public String test() {

          return "Hello from microservice 1";
        }
      }

En el fichero application.properties configuramos el puerto donde se va a ejecutar nuestro microservicio:

    server.port=8081

El próximo paso es la implementación del segundo microservicio(micro2), en este solo añadiremos una pequeña lógica para probar el funcionamiento del patrón abordado:

Definición del controlador para mostrar un mensaje desde el primer microservicio:

    @RestController
    @RequestMapping("/micro2")
    public class microservice1Controller {
      @GetMapping("/message")
      public String test() {

        return "Hello from microservice 2";
      }
    }

En el fichero application.properties configuramos el puerto donde va a ejecutarse el microservicio:

    server.port=8082

Finalmente definimos el microservicio(cloud-gateway) que servirá como Gateway en nuestra arquitectura:

Las dependencias son las siguientes:

      <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>2.1.7.RELEASE</version>
       <relativePath /> <!-- lookup parent from repository -->
      </parent>
      <properties>
       <java.version>1.8</java.version>
       <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
      </properties>
      <dependencies>
       <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
      </dependency>
      </dependencies>
      <dependencyManagement>
       <dependencies>
        <dependency>
         <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
         </dependency>
       </dependencies>
      </dependencyManagement>
      <repositories>
       <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestone</url>
       </repository>
      </repositories>
      <build>
       <plugins>
        <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-  plugin</artifactId>
        </plugin>
      </plugins>
      </build>

Definimos el arranque del servico en la clase Main.java

    @SpringBootApplication
    public class Main {

      public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
        System.out.println("from gateway");
      }
    }

Y finalmente registramos nuestros servicios en application.yml:

      server:
        port: 8080

      spring:
        cloud:
          gateway:
            routes:
            - id: micro1
              uri: http://localhost:8081/
              predicates:
              - Path=/micro1/**
            - id: micro2
              uri: http://localhost:8082/
              predicates:
              - Path=/micro2/*

Ejecutamos los tres microservicios:

si accedemos a: http://localhost:8081/micro1/message obtenemos la siguiente respuesta:

    Hello from microservice 1

si accedemos a: http://localhost:8082/micro2/message obtenemos la siguiente respuesta:

    Hello from microservice 2

Esto comprueba que los microservicios (micro1 y micro2) funcionan correctamente, para probar que nuestro gateway está funcionando podemos hacer las peticiones a nuestros microservicios a traves la url base de nuestro microservicio cloud-gateway:

si accedemos a: http://localhost:8080/micro1/message obtenemos la siguiente respuesta desde micro1:

    Hello from microservice 1

si accedemos a: http://localhost:8080/micro2/message obtenemos la siguiente respuesta desde micro2:

    Hello from microservice 2

Esto prueba de forma concreta que el patrón API Gateway funciona correctamente
