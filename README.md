Spring boot
===========

What is spring boot?
--------------------

[Spring boot][boot] is an opinionated setup of different frameworks (including
the spring framework) to give you a simple environment that just runs.

Start a simple project
----------------------

Create a project by configuring and downloading it from
[start.spring.io][start].

The main class that will be executed looks like this:
```java
package javacasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
```

When this `BootApplication` is startedm it calls `SpringApplication.run` with
the class itself and the call-arguments. Spring now decides what to do. As the
`BootApplication` is annotated with `SpringBootApplication`, spring now makes
some "magic".  
Take a look at the [SpringBootApplication][sapp]-annotation, it itself is
annotated with `@Configuration`, `@EnableAutoConfiguration` and
`@ComponentScan`. We will have a deeper look at these Annotations in the
[next javacasts episode][jc0002].

Simple Console Application
--------------------------

The [console application][cmd] is like the easiest way to write an
application. As it's inevitable to write a `hello world`-application, this
will be the first application we create.  
There are multiple ways to have a commandline application run by spring,
here's one solution to this.

```java
package javacasts;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication implements ApplicationRunner
{

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

     @Override
     public void run(ApplicationArguments arg0) throws Exception
     {
         System.out.println("hello world");
     }
}
```

We have the class implement a `ApplicationRunner`. You can also implement a
`CommandLineRunner`, they are nearly the same, the difference is the treatment
of the arguments.  
Now the `run`-method needs to be implemented. In here, we simply write `hello
world` to the systems output.

Simple Rest-API
---------------

The next step is to change the application to be a [Rest-API][api]. The first
thing that we need to change is to add the dependencies to have all in place
what we need. So we change the dependency of `spring-boot-starter` to
`spring-boot-starter-web`. The `BootApplication` now does not need any more to
implement an `ApplicationRunner`, but we mark it that it's a
`@RestController`. Now we need to tell spring-boot which URLs can be answered
by this `@RestController`. We create a new method that we mark with the
annotation `@RequestMapping`, and include a value `"/"` what means that this
controller-method will handle the root-URL.  
The response will be mapped to JSON automaticly. For this simple example, we
just return the string `hello world`.

```java
package javacasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BootApplication
{

   public static void main(String[] args)
   {
       SpringApplication.run(BootApplication.class, args);
   }

   @RequestMapping("/")
   public String index()
   {
       return "hello world";
   }
}
```

Simple Web Application
----------------------

But a user usually does not want to receive just strings or JSON. So this is
how to change the application to [return html pages][web].  
First we need to decide od the template engine. Spring-boot provides different
engines you can use. I now pick the [thymeleaf template engine][thymeleaf].
For this the first thing I need is the dependency. As
`spring-boot-starter-web` is a dependency of the
`spring-boot-starter-thymeleaf`-package, it's fine to just use the
thymeleaf-dependency.

The templates can be provided in the `src/main/resources/templates`-directory.
You can change all these directories, but it's a good idea to stick with the
default unless you really need to change something. We create a simple
`index.html`-template. It's almost a simple html-page, there's only one
non-standard html-tag (`th:text`). Thymeleaf uses this tag to replace the
contents of the html-tag.

```html
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">

  <head>
    <title>spring-boot - hello world</title>
  </head>

  <body>
    <p th:text="'hello ' + ${target}">hello world</p>
  </body>

</html>
```

The `@RestController` also needs some change. First we only mark it as
`@Controller`. Then we change the `index`-method to take a parameter marked
with `@RequestPara`, and we return a `ModelAndView`. The `ModelAndView`
contains the name of the view (`index.html`) and some variables that can be
used by the template (`target` in this example).

```java
package javacasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@SpringBootApplication
public class BootApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(BootApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(name = "to", defaultValue = "world") String recipient)
    {
        ModelAndView result = new ModelAndView("index");
        result.getModel().put("target", recipient);
        return result;
    }
}
```

Now you can call the application and receive a `hello world`. You even can
give a parameter `to` that will translate the message to `hello <to>`.

[boot]:      http://projects.spring.io/spring-boot/  "Spring boot"
[start]:     http://start.spring.io/                 "Spring boot Initializr"
[cmd]:       https://github.com/javacasts/jc0001-spring-boot/tree/cmd "Simple console application"
[api]:       https://github.com/javacasts/jc0001-spring-boot/tree/api "Simple Rest-API"
[web]:       https://github.com/javacasts/jc0001-spring-boot/tree/web "Simple web application"
[sapp]:      https://github.com/spring-projects/spring-boot/blob/v1.3.1.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/SpringBootApplication.java "@SpringBootApplication"
[jc0002]:    https://javacasts.net/                  "JavaCasts Episode 2"
[thymeleaf]: http://www.thymeleaf.org/               "Thymeleaf"
