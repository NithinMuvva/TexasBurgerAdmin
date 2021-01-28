package com.example.demo;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.egen.texasBurger.TexasBurgerAdminApplication;
import com.egen.texasBurger.model.Address;
import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.OpenHours;
import com.egen.texasBurger.repository.LocationRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TexasBurgerAdminApplication.class)
public class LocationControllerTest {

	 @Autowired
	 private MockMvc mvc;
	 
	 @MockBean
	 private LocationRepository locRepository;
	 
	 @Autowired
	 private JacksonTester<Location> jsonSuperHero;
	 
	 @Test
     public void cannotPerformApiWhenUserNotAuthorized() throws Exception {
		 
        // given
		 
		 Optional<Location> loc = Optional.of(new Location());
		 Address a = new Address("street", "city", "state", "country", "zipcode", "phone",10.00,11.00);
		 loc.get().setAddress(a);
		 OpenHours oh = new OpenHours(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(19, 30));
		 Set<OpenHours> ohs = new HashSet<OpenHours>();
		 ohs.add(oh);
         loc.get().setOpenHours(ohs);
		 given(locRepository.findById("1"))
                .willReturn(loc);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/locations/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
     
     }

}
