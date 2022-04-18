package com.api.pagamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    @SpringBootApplication = @Configuration + @ComponentScan + @EnableAutoConfiguration
        A anotação @SpringBootApplication é uma combinação das seguintes três anotações Spring e fornece a funcionalidade
        de todas as três com apenas uma linha de código.
    @Configuration
        Esta anotação marca uma classe como uma classe de configuração para configuração baseada em Java. Isso é particularmente
        importante se você prefere a configuração baseada em Java em vez da configuração XML.
    @ComponentScan
        Esta anotação permite a varredura de componentes para que as classes do controlador da web e outros componentes
        que você criar sejam automaticamente descobertos e registrados como beans no contexto de aplicativo do Spring.
    @EnableAutoConfiguration
        Esta anotação ativa o recurso mágico de autoconfiguração do Spring Boot, que pode configurar muitas coisas
        automaticamente para você.
        Por exemplo, se você estiver escrevendo um aplicativo Spring MVC e tiver arquivos JAR do Thymeleaf no classpath do
        aplicativo, a configuração automática do Spring Boot pode configurar automaticamente o resolvedor de modelo
        Thymeleaf, resolvedor de visualização e outras configurações automaticamente.
        Portanto, você pode dizer que @SpringBootApplication é uma anotação 3 em 1 que combina a funcionalidade
        de @Configuration, @ComponentScan e @EnableAutoConfiguration.
        Ele também marca a classe como uma classe BootStrap, o que significa que você pode executá-la como uma classe
        Java normal, por exemplo, executando seu arquivo JAR no prompt de comando, conforme mostrado aqui,
        ou apenas clicando com o botão direito e executando-o como um programa Java no Eclipse IDE.
        Isso iniciará o servidor integrado que acompanha o Spring Boot e executará seu aplicativo da web dentro dele.
        Depois de ver o log sem erros, você pode ir para o navegador e abrir o localhost com a porta do servidor para
        acessar seu aplicativo Spring Boot.
*/
@SpringBootApplication
public class ApiPagamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPagamentoApplication.class, args);
    }

}
