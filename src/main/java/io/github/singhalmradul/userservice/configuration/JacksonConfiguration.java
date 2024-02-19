// package io.github.singhalmradul.userservice.configuration;

// import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.fasterxml.jackson.databind.ser.FilterProvider;
// import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
// import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

// @Configuration
// public class JacksonConfiguration {

//     @Bean
//     Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
//         return builder -> {
//             FilterProvider filters = new SimpleFilterProvider()
//                 .addFilter(
//                     "minimal",
//                     SimpleBeanPropertyFilter.filterOutAllExcept(
//                             "id",
//                             "username",
//                             "profilePicute"
//                         )
//                 )
//                 .addFilter(
//                     "full",
//                     SimpleBeanPropertyFilter.serializeAll()
//                 );

//             builder.filters(filters);

//         };
//     }
// }